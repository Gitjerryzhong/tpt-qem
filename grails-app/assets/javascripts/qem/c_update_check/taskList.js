app.controller('taskListCtrl',['$scope','$http','$location','$filter','aboutUpdate',function($scope,$http,$location,$filter,aboutUpdate){ //任务列表
	$scope.taskList={};
	$scope.trial={}
	$http({
   		 method:'GET',
   			url:"/tms/qemCollegeUpdate/contractList"
   	 	}).success(function(data) {
   		 if(data!=null){			 
   			 $scope.taskList= data.taskList;
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
	$scope.updateRequest = function(id){
		$scope.task={}
    	$http({
      		 method:'GET',
      			url:"/tms/qemCollegeUpdate/updateForm",
      			params:{
      				id:id
      			}
      	 	}).success(function(data) {
      		 if(data!=null){
      			 $scope.getTaskView(data);
      		 }	
      		$location.url('/updateRequest') 
      	 });
	}
}]);