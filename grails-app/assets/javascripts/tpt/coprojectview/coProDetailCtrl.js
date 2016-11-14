coProjectApp.controller('CoProDetailCtrl',['$rootScope','$scope','$http','$filter','$state',function($rootScope,$scope,$http,$filter,$state){ //国外大学管理
	$scope.projectItems={}
	$scope.majors ={}
	$scope.projectItem={}
	$scope.yearList=[]
	$scope.effetiveYears=[]
	var now = $scope.projectItem.beginYear = new Date().getFullYear();
	var start =2002;
	var foreach=angular.forEach;
	var inputId="none";
	$scope.editYearShow=false;
//	Get后台projectItem数据
	$scope.getProjectItems = function(){
		$http({
			 method:'GET',
				url:"/tms/tptCoPrjView/getProjectItems",
				params:{
					coProjectId:$rootScope.projectDetail.id
				}
		 }).success(function(data) {
			 if(data!=null){
				 $scope.projectItems=data.projectItems;
				 $scope.majors=data.majors;
			 }else {				
				 $scope.projectItems={}
				 $scope.majors={}
			 }
		 });
	}
	$scope.getProjectItems();

	
}]);
