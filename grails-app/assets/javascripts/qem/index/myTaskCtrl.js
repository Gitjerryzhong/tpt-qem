qemApp.controller('myTaskCtrl',['$rootScope','$scope','$http','FileUploader','$location','$filter','runStatusText',function($rootScope,$scope,$http,FileUploader,$location,$filter,runStatusText){ //任务列表
	$scope.myProjects={}
	$scope.project={}
	$http({
		 method:'GET',
			url:"/tms/qem/showMyTasks"
	 }).success(function(data) {
		 if(data!=null){			 
			 $scope.myProjects= data.taskList;	
			 console.info($scope.myProjects);
		 }			 
	 });
	$scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
	$scope.orderBy = function(col){
		console.info(col,$scope.order);
		if($scope.order==col)
			$scope.order = "-"+col;
		else 
			$scope.order = col;		
    }
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
	$scope.collegeStatusText = function(status){
    	var COLLEGESTATUS = {
    			"0": "未审",
    			"1": "通过",
    			"2": "不通过"    			
    		};
    	return COLLEGESTATUS[status];
    }

	$scope.clearNull= function(str){
		return str=='null'?'':str;
	}	
//	$scope.getStatus=function(item){
//		var REVIEWSTATUS = {
//    			"1": "已指定专家",
//    			"2": "专家已评审",
//    			"3": "已上会",
//    			"4": "同意立项",
//    			"5": "不予立项",
//    			"6": "立项需修改"
//    		};
//    	
//		if(item.reviewStatus){
//			return REVIEWSTATUS[item.reviewStatus];
//		}
//		if(item.collegeStatus) return "学院已通过";
//		return item.commit?'我已提交':'我未提交';
//	}
	$scope.createProject=function(){
    	$location.url("/qemRequest");
    }
    $scope.deleteItem = function(id){
		$http({
			 method:'GET',
				url:"/tms/qem/deleteItem",
				params:{
					projectId:id
				}
		 }).success(function(data) {
			 if(data!=null){
				 $scope.myProjects= data.projects;	
				 $scope.createAble=data.createAble;
			 }			 
		 });
	}
    $scope.currentTask=function(status){
     		var STATUS = {
            		"0": "合同未确认！",
         			"10": "在研",
         			"20": "结题",
         			
         			"32": "终止"
 	    		};
     		return STATUS[status]
    }
    $scope.myWork =function(item){
    	var currentYear=$filter('date')(new Date(),'yyyy');
	   	 if( item.expectedMid==currentYear ) return "中";
	   	 if( item.expectedEnd==currentYear )	 return "结";
	   	 if( item.expectedMid>currentYear && item.beginYear< currentYear) return "年";
	   	 return null;
    }
    $scope.remindFilter = function(item){
    	var currentYear=$filter('date')(new Date(),'yyyy');
    	var result=true;
    	if($rootScope.isTaskRemind==1){
    		result=result && (item.runStatus==0 || item.runStatus==203);
    	}
    	if($rootScope.isTaskRemind==2){
    		result=result && ( item.expectedMid>=currentYear && item.beginYear< currentYear );
    	}
    	if($rootScope.isTaskRemind==3){
    		result=result && ( item.expectedEnd==currentYear );
    	}
    	return result;
    }
//	$scope.task={}
//	$scope.task.status=0;
//	$scope.member="";
//	$scope.members=[];
//	$scope.clicked=false;
//	$scope.taskCreate = function(){
//		$http({
//			 method:'GET',
//				url:"/tms/qem/getTaskList"
//		 }).success(function(data) {
//			 if(data!=null){
//				 $scope.task=data.task;
//				 var mytask=$scope.task;
//				 mytask.expectedMid=(mytask.beginYear==null)?null:+parseInt(mytask.beginYear)+mytask.cycle/2;
//				 mytask.expectedEnd=(mytask.beginYear==null)?null:+parseInt(mytask.beginYear)+mytask.cycle;
//				 if(mytask.memberstr!=null){
//					 var items=mytask.memberstr.split(";");				 
//					 angular.forEach(items,function(item){
//						 if(item!=null && item!="") $scope.members.push(item)
//					 });
//				 }
//			 }			 
//		 });
//	}
//	
//	$scope.taskCreate();
//	
//	$scope.changeBegin=function(){
//		var mytask=$scope.task;
//		 mytask.expectedMid=(mytask.beginYear==null)?null:+parseInt(mytask.beginYear)+mytask.cycle/2;
//		 mytask.expectedEnd=(mytask.beginYear==null)?null:+parseInt(mytask.beginYear)+mytask.cycle;
//	}
//	
//	$scope.span_click=function(){
//		$scope.clicked=true;
//	}
//	$scope.addMember=function(){
//		var mytask=$scope.task;
////		if(mytask.members==null){
////			mytask.members=[]
////		}
//		$scope.members.push($scope.member);
//		//暂时当作一个姓名串处理，留下接口以后可以对参与人的身份进行校验，以便获取参与人更多信息
//		if(mytask.memberstr==null) mytask.memberstr=$scope.member+";"
//		else mytask.memberstr+=$scope.member+";"
//		$scope.member="";
//		$scope.clicked=false;
//		
//	}
//	 $scope.myKeyup = function(e){		
//         var keycode = window.event?e.keyCode:e.which;
//         if(keycode==13){        	 
//        	 $scope.addMember();
//         }
//     };
//   //上传附件
//     var uploader = $scope.uploader = new FileUploader({
//         url: 'qem/uploadFiles'
//     });
//     
//     $scope.save=function(){
//    	 console.info("task",$scope.task)
//    	 $http({
//				method:'POST',
//				url:"/tms/qem/saveTask",
//				data:JSON.stringify($scope.task),
//				headers:{ 'Content-Type': 'application/json' } 
//			  }).success(function(data) {
//				  $scope.task.status=10;
//				}).error(function(data){
//					alert("信息保存失败！");					
//				});
//     }
//     $scope.showProject=function(){
//    	 $location.url("/qemRequest");
//     }
//     $scope.progress=function(){     	
//     	if($scope.task.status>0){
//     		var STATUS = {
//            		"1": "任务书已上传！等待管理员确认！",
//         			"10": "在研",
//         			"20": "结题",
//         			"31": "【终止】免结题",
//         			"32": "【终止】结题异常"
// 	    		};
//     		return "当前状态："+STATUS[$scope.task.status]
//     	}     	
//     }
}]);