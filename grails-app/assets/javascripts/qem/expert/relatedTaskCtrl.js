expertApp.controller('relatedTaskCtrl',['$scope','$http','item',function($scope,$http,item){ 
	$scope.taskList ={};
	$http({
		 method:'GET',
			url:"/tms/QemExpertCheck/relatedTask",
			params:{
				id: item.id
			}
	 }).success(function(data) {
		 if(data!=null && data.taskList!=null){		
			 $scope.taskList=data.taskList;	
		 }
	 }); 
	$scope.levelText = function(level){
    	var LEVEL = {
    			"1": "校级",
    			"2": "省级",
    			"3": "国家级"	    		
    		};
    	return LEVEL[level];
    }
	$scope.statusTT = function(status){
    	var STATUS = {
    			"10": "在研",
    			"20": "结项",
    			"32": "终止"	,  
    			"33": "中止"	, 
    		};
    	return STATUS[status];
    	}
	}]);