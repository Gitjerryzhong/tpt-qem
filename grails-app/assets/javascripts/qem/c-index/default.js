cIndexApp.controller('defaultCtrl',['$scope','$http','$filter','config','$modal','$state','$location','runStatusText',function($scope,$http,$filter,config,$modal,$state,$location,runStatusText){ //项目信息	
	$scope.notices=config.notices
	$scope.tabs =[{title:'校发通知',active:false,link:'tab1'},{title:'已立项项目汇总',active:false,link:'tab2'},{title:'合同审核',active:false,link:'tab3'}]
	$scope.trial={};
	$scope.action={};
	$scope.trial.status='10';
	$scope.action.type='1';
	$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
	$scope.projectStatus=[{"id":"10","name":"在研"},{"id":"20","name":"结题"},{"id":"32","name":"终止"},{"id":"33","name":"中止"}];
	$scope.detailShow=false;

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
	$scope.showNotice =function(item){
    	var modalInstance=$modal.open({
            templateUrl : 'notice_detail.html',  //创建文件上传视图
            controller : 'noticeCtrl',
            resolve : {
                item : function(){
                    return item;
                }
            }
        }) 
    }
	$scope.historyTaskList= function(){
		$scope.detailShow=false;
		$http({
	   		 method:'GET',
	   			url:"/tms/qemCollegeCheck/taskList"
	   	 	}).success(function(data) {
	   		 if(data!=null){			 
	   			 $scope.taskList= data.taskList;
	   		 }	
	   	 });
		$state.go('tab2')
	}
	//全选
    $scope.doselectAllTask= function(){		
		angular.forEach($scope.taskList,function(data){				
			if($scope.listConditions(data)) 
				data.selected=$scope.action.selectAll;					
		});
	}
  //已立项项目汇总筛选
	$scope.listConditions = function(item){
		var result=true;
		result=result && (item.status!=0); //首先过滤掉未立项的
		if($scope.trial.discipline){
			result=result && (item.discipline===$scope.trial.discipline);
		}
		if($scope.trial.level){				
			result=result && (item.projectLevel==$scope.trial.level);
		}
		if($scope.trial.departmentName){				
			result=result && (item.departmentName==$scope.trial.departmentName);
		}
		if($scope.trial.userName){				
			result=result && (item.userName==$scope.trial.userName);
		}
		if($scope.trial.typeName){				
			result=result && (item.type==$scope.trial.typeName);
		}
		if($scope.trial.bn){				
			result=result && (item.bn==$scope.trial.bn);
		}
		if($scope.trial.status){
			result=result && (item.status==$scope.trial.status);
		}
		if($scope.trial.beginYear){
			result=result && (item.beginYear==$scope.trial.beginYear);
		}
		if($scope.trial.expectedEnd){
			result=result && (item.expectedEnd==$scope.trial.expectedEnd);
		}
		return result;
	}
	//排序
	$scope.orderBy = function(col){		
		if($scope.order==col){
			$scope.taskList.sort( function(b,a){
				if(typeof(a[col])=="number") 
					return a[col]>b[col];
				return a[col].localeCompare(b[col]);
				});
			$scope.order ="";
		}	
		else {
			$scope.order = col;				
			 $scope.taskList.sort( function(a,b){
				 if(typeof(a[col])=="number") 
						return a[col]>b[col];
				 return a[col].localeCompare(b[col]);
			});
		}
			
    }
	$scope.levelText = function(level){
    	var LEVEL = {
    			"1": "校级",
    			"2": "省级",
    			"3": "国家级"	    		
    		};
    	return LEVEL[level];
    }
	 //建设情况注释
    $scope.statusTT = function(status){
    	var STATUS = {
    			"10" : "在研",
    			
    			"20" : "结题",
    			"32" : "终止",
    			"33" : "中止"
    		};
    	return STATUS[status];
    }
    $scope.go=function(link){
    	$state.go(link);
    }
  //任务书详情
    $scope.taskDetail=function(id){
//    	console.log(id);
    	$http({
      		 method:'GET',
      			url:"/tms/qemCollegeCheck/taskDetail",
      			params:{
      				id:id
      			}
      	 	}).success(function(data) {
      		 if(data!=null){			 
      			 $scope.task= data.task;
      			 $scope.task.typeName=data.taskType;
      			 $scope.task.userName=data.userName;
      			 $scope.pagerT = data.pager;
      			 $scope.fileList= data.fileList;
      			 console.log($scope.fileList);
      		 }	
      		$location.url('/taskDetail') 
      	 });
    }
    //前一个和后一个任务书按钮
    $scope.disabled_p1T =function(){
    	if($scope.pagerT.prevId==null){
    		return 'disabled';
    	}else return '';
    }
    $scope.disabled_n1T =function(){
    	if($scope.pagerT.nextId==null){
    		return 'disabled';
    	}else return '';
    }
    //审批
    $scope.okT = function(act,formId,prevId,nextId){ 
		$scope.trial.form_id=formId;
		$scope.trial.check = act;
		$scope.trial.prevId = prevId;
		$scope.trial.nextId = nextId;		
		$http({
			method:'POST',
			url:"/tms/qemCollegeCheck/auditTaskSave",
			data:JSON.stringify($scope.trial),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  if(data!=null ){
				  if(data.none){
					  $scope.taskList= data.taskList;
					  $scope.taskList=$filter('groups')($scope.taskList);
//			   		  $scope.taskCounts=data.taskCounts;
					  $location.url('/tab3')
				  }else{
					  $scope.task= data.task;
	      			 $scope.task.typeName=data.taskType;
	      			 $scope.task.userName=data.userName;
	      			 $scope.pagerT = data.pager;
	      			 $scope.fileList= data.fileList;
					 $scope.trial={};					
				  }
				 }
			});
	};
	$scope.getFileName1 = function(item){
		return item.slice(item.lastIndexOf('___') + 3);
	}
	$scope.statusText = function(status){
 		var STATUS = runStatusText;
     	return STATUS[status];
     }
}]);