qemApp.controller('templateCtrl',['$scope','$http',function($scope,$http){ //模版下载
	$scope.qemTypes={}
	$scope.parentTypes={}	
	$http({
		 method:'GET',
			url:"/tms/qem/getTypes"
	 }).success(function(data) {
		 if(data!=null){			 
			 $scope.qemTypes= data.qemTypes;
			 $scope.qemParentTypes= data.qemParentTypes;
			 $scope.parentTypeId = $scope.qemParentTypes[0].id;	
			 
		 }			 
	 });
	
}]);