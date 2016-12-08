app.controller('checkCtrl',['$scope','$http','$filter','$location','aboutUpdate',function($scope,$http,$filter,$location,aboutUpdate){ //项目信息	
	$scope.requestList =[];
	$scope.checkStatus ="0";
	$http({
			method:'GET',
			url:"/tms/qemUpdateCollegeCheck/requestList"
		  }).success(function(data) {
			  $scope.requestList = data.updateList;
			  $scope.requestList = $filter('groups')($scope.requestList);
			  console.log($scope.requestList);
			});
	$scope.updateTypesText = function(updateTypes){
		var items=updateTypes.split(";");
		var typesText ="";
		angular.forEach(items,function(item){
			if(item){
				typesText +=aboutUpdate.updateTypeText[item]+"；"
			}				
		})
		return typesText;
	}
	$scope.levelText = function(level){
     	var LEVEL = {
     			"1": "校级",
     			"2": "省级",
     			"3": "国家级"    			
     		};
     	return LEVEL[level];
     }
	$scope.updateStatusText = function(flow,auditStatus){
		return aboutUpdate.updateStatus[flow][auditStatus];
	}
	$scope.view = function(id){
		$http({
   			method:'GET',
   			url:"/tms/qemUpdateCollegeCheck/updateDetail",
   			params:{
   				id:id
   			}
   		  }).success(function(data) {
//   			  console.log(data);
   			  $scope.getUpdateView(data);
   			$location.url("/updateView");
   			});
	}
}]);