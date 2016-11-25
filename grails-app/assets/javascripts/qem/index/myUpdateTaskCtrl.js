qemApp.controller('myUpdateCtrl',['$scope','$http','$location','$filter','runStatusText','aboutUpdate',function($scope,$http,$location,$filter,runStatusText,aboutUpdate){ //任务列表
	$scope.updateList={};
	$scope.getList = function(){
		$http({
   			method:'GET',
   			url:"/tms/qemTaskUpdate/getUpdateList"
   		  }).success(function(data) {
   			  $scope.updateList=data.updateList;
   			  console.log($scope.udpateList);
   			});
	}
	$scope.getList();
	$scope.updateStatusText = function(flow,auditStatus){
		return aboutUpdate.updateStatus[flow][auditStatus];
	}
	$scope.updateTypesText = function(updateTypes){
		var items=updateTypes.split(";");
		var typesText ="";
		angular.forEach(items,function(item){
			if(item)
				typesText +=aboutUpdate.updateTypeText[item]+"；"
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
	$scope.view = function(id){
		$http({
   			method:'GET',
   			url:"/tms/qemTaskUpdate/updateDetail",
   			params:{
   				id:id
   			}
   		  }).success(function(data) {
   			  console.log(data);
   			  $scope.getUpdateView(data);
   			$location.url("/updateView");
   			});
	}
}]);