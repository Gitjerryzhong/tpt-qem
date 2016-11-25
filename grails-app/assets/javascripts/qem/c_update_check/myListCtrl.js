app.controller('myListCtrl',['$scope','$http','$location','$filter','aboutUpdate',function($scope,$http,$location,$filter,aboutUpdate){ //任务列表
	$scope.requestList =[];
	$scope.checkStatus ="0";
	$http({
			method:'GET',
			url:"/tms/qemCollegeUpdate/requestList"
		  }).success(function(data) {
			  $scope.requestList = data.updateList;
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
		if(flow==2 && auditStatus==0) return "学校未审";
		return aboutUpdate.updateStatus[flow][auditStatus];
	}
	$scope.view = function(id){
		$http({
   			method:'GET',
   			url:"/tms/qemUpdateCollegeCheck/updateDetail",
   			params:{
   				id:id,
   				isMine:true
   			}
   		  }).success(function(data) {
   			  console.log(data);
   			  $scope.getUpdateView(data);
   			$location.url("/updateView");
   			});
	}
}]);