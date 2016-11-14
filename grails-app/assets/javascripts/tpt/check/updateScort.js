checkApp.controller('updateScortCtrl',['$scope','$http','$modalInstance','$filter','id',function($scope,$http,$modalInstance,$filter,id){
	$scope.paper={};
//	console.log(id);
	$http({
		 method:'GET',
			url:"/tms/tptAdmin/showPaperMtg",
			params:{
				id:id
			}
	 }).success(function(data) {
		 if(data!=null)
			 $scope.paper=data.paper;
		 console.log($scope.paper);
	 });
	$scope.ok = function(){ 
		$http({
			method:'POST',
			url:"/tms/tptAdmin/updatePaperMtg",
			params:{
				id:$scope.paper.id,
				projectId: id,
				scort:$scope.paper.master_result
			}
		  }).success(function(data) {
			  $modalInstance.close(true);
		  })
    };
    $scope.cancel = function(){    	
    	$modalInstance.dismiss('cancel'); // 退出
    }
}]);