cIndexApp.controller('noticeCtrl',['$scope','$http','$modalInstance','item',function($scope,$http,$modalInstance,item){ //依赖于modalInstance
	$scope.notice=item;
    $scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
	$scope.workTypeText = function(workType){
    	var WORKTYPES = {
    			"REQ": "项目申请",
    			"CHE": "项目检查"
    		};
    	return WORKTYPES[workType];
    }
}]);