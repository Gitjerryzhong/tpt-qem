cIndexApp.controller('contractCtrl',['$scope','$http','$location','$filter',function($scope,$http,$location,$filter){
	//合同审核
		$http({
	   		 method:'GET',
	   			url:"/tms/qemCollegeCheck/contractList",
	   			params:{
	   				bn:	$scope.bn
	   			}
	   	 	}).success(function(data) {
	   		 if(data!=null){			 
	   			 $scope.taskList= data.taskList;
	   			$scope.taskList=$filter('groups')($scope.taskList);
//	   			 console.log($scope.taskList);
	   		 }	
	   	 	});
		
}]);