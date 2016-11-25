taskApp.controller('baseInfoCtrl',['$scope','$http','FileUploader', '$modal','$filter','config','runStatusText',function($scope,$http,FileUploader,$modal,$filter,config,runStatusText){ //项目信息
//	$scope.task=config.task
//	console.log($scope.task);
	$scope.notice=config.notice;
//	$scope.fileList = config.fileList;
	$scope.statusText = function(status){
		var STATUS = runStatusText;
    	return STATUS[status];
    }
	$scope.levelText = function(level){
     	var LEVEL = {
     			"1": "校级",
     			"2": "省级",
     			"3": "国家级"    			
     		};
     	return LEVEL[level];
     }
//	if(!($scope.task.projectContent && $scope.task.expectedGain) ){
	if(false){ //暂时不再弹窗提示补充材料
		 var modalInstance=$modal.open({
	         templateUrl : 'warning.html',  //创建负责人资料录入视图
	         controller : 'warningCtrl',// 初始化模态范围
	         backdrop : "static",
	         resolve : {
	            	username : function(){
	                    return $scope.task.userName;
	                }
	            }
	     }) 
		 modalInstance.result.then(function(){  
			 $scope.edit();
         },function(){      });
	}
}]);
taskApp.controller('warningCtrl',['$scope','$http','$modalInstance','$filter','username',function($scope,$http,$modalInstance,$filter,username){
	$scope.username=username;
	$scope.ok = function(){ 
		$modalInstance.close();
    };
    $scope.cancel = function(){    	
    	$modalInstance.dismiss('cancel'); // 退出
    }
}]);