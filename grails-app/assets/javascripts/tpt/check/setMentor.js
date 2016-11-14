checkApp.controller('setMentorCtrl',['$scope','$http','$modalInstance','$filter','myRequest',function($scope,$http,$modalInstance,$filter,myRequest){
	$scope.requests=myRequest;
//	console.log($scope.requests);
	$scope.mentor={};
	$http({
		 method:'GET',
			url:"/tms/tptAdmin/showMentor"
	 }).success(function(data) {
		 if(data!=null)
			 $scope.mentorList=data.mentorList;
	 });
	$scope.ok = function(){ 
		$http({
			method:'POST',
			url:"/tms/tptAdmin/setMentor",
			data:JSON.stringify({requests:myRequest,mentorId:$scope.mentor.selected}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  var mentorSelected=$filter('filter')($scope.mentorList,{'id':$scope.mentor.selected},true)[0].name;
			  console.log(mentorSelected);
			  $modalInstance.close(mentorSelected);
		  })
    };
    $scope.cancel = function(){    	
    	$modalInstance.dismiss('cancel'); // 退出
    }
}]);