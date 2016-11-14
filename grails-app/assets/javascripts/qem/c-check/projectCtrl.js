cCheckApp.controller('defaultCtrl',['$rootScope','$scope','$http','$location','config',function($rootScope,$scope,$http,$location,config){ 
		$scope.offset =0;
		$scope.max=10;
		var index=1;
		var checkStatus=$scope.checkStatus=0;
		$scope.requests={}
		$scope.project={}
		$scope.audits={}
		$scope.trial={}
		$scope.pager={}
		$scope.pagerT={}
		$scope.taskList={}
		$scope.taskCounts={}
		$scope.action={};
		$scope.trial={};
		$scope.trial.status='10';
		$scope.action.type='1';
		$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
		$scope.projectStatus=[{"id":"10","name":"在研"},{"id":"20","name":"结题"},{"id":"32","name":"终止"},{"id":"33","name":"中止"}];
		 $scope.requests=config.requests;	
		 $scope.pager = config.pager;
		 $scope.bn=config.bn;
		$scope.showRequests = function(){ 
			console.log($scope.bn);
	    	$http({
				 method:'GET',
					url:"/tms/qemCollegeCheck/showRequests",
					params:{
						type : checkStatus,
						offset: $scope.offset,
						max: $scope.max,
						bn:	$scope.bn
					}
			 }).success(function(data) {
				 if(data!=null ){		
					 $scope.requests=data.requests;	
//					 console.info('requests',data);
					 $scope.pager = data.pager;	
					 $location.url('/default');
				 }
			 });  
	    	 
	    };

	    $scope.disabled_prev =function(){
	    	if($scope.offset<$scope.max){
	    		return 'disabled';
	    	}else return '';
	    }
	    $scope.disabled_next =function(){
	    	if($scope.pager.total==null ){return 'disabled';}
	    	else if($scope.offset+$scope.max> $scope.pager.total[checkStatus]){
	    		return 'disabled';
	    	}else return '';
	    }
	    $scope.disabled_p1 =function(){
	    	if($scope.pager.prevId==null){
	    		return 'disabled';
	    	}else return '';
	    }
	    $scope.disabled_n1 =function(){
	    	if($scope.pager.nextId==null){
	    		return 'disabled';
	    	}else return '';
	    }
	   
	    $scope.next = function(){
	    	$scope.offset =$scope.offset+$scope.max;
	    	$scope.showRequests()  	
	    };
	    $scope.previous = function(){
	    	$scope.offset =$scope.offset-$scope.max;
	    	$scope.showRequests()

	    	
	    };
	    $scope.details = function(itemId){
	    	console.info("projectId",itemId);
	    	$http({
				 method:'GET',
					url:"/tms/qemCollegeCheck/getRequestDetail",
					params:{
						form_id: itemId 
					}
			 }).success(function(data) {
				 if(data!=null ){	
					 $scope.project=data.form;
//					 $scope.audits=data.audits;
					 $scope.fileList = data.fileList;
					 $scope.pager = data.pager;
					 console.info($scope.project);
					 if($scope.auditAble())
						 $location.url('/details');
					 else 
						 $location.url('/search');
				 }
			 });  
	    }

	    $scope.dateFormat = function(jsondate){
	    	return new Date(jsondate);
	    }
	    
	    $scope.statusText = function(status){
	    	var STATUS = {
	    			"0": "未提交",
	    			"1": "已提交",
	    			"201": "学院同意",
	    			"202": "学院不同意",
	    			"203": "学院退回",
	    			"2" : "学校同意",
	    			"3" : "项目终止",
	    			"4" : "学校退回"
	    		};
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
//	    $scope.showRequests();  
	    $scope.goTrial = function(type){
	    	checkStatus=type;
	    	$scope.checkStatus=type
	    	$scope.showRequests();
	    	$location.url('/default');
	    }

	    $scope.auditAble=function(){
	    	return checkStatus==0;
	    }
		$scope.getFileName = function(src){
			return src.slice(src.lastIndexOf('/') + 1);
		}
		$scope.clearNull= function(str){
			return str=='null'?'':str;
		}
		$scope.ok = function(act,formId,prevId,nextId){ 
			$scope.trial.form_id=formId
			$scope.trial.check = act;
			$scope.trial.prevId = prevId;
			$scope.trial.nextId = nextId;			
			$http({
				method:'POST',
				url:"/tms/qemCollegeCheck/auditSave",
				data:JSON.stringify($scope.trial),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
				  if(data!=null ){
					  if(data.none){
						  $scope.requests={};
//						  $scope.pager.total=0;
						  $location.url('/default')
					  }else{
						 $scope.project=data.form;
//						 $scope.audits=data.audits;
						 $scope.fileList = data.fileList;
						 $scope.pager = data.pager;
						 $scope.trial={};					
					  }
					  $scope.pager=data.pager;
					 }
				}).error(function(data){
					
				});
		};
		$scope.cancel=function(id){
			$http({
				 method:'GET',
					url:"/tms/qemCollegeCheck/cancel",
					params:{
						type : checkStatus,
						offset: $scope.offset,
						max: $scope.max,
						cancelId: id
					}
			 }).success(function(data) {
				 if(data!=null ){		
					 $scope.requests=data.requests;	
//					 console.info('requests',data);
					 $scope.pager = data.pager;				
				 }
			 }); 
		}
		//合同审核
		$scope.contractList= function(){
			$http({
		   		 method:'GET',
		   			url:"/tms/qemCollegeCheck/contractList",
		   			params:{
		   				bn:	$scope.bn
		   			}
		   	 	}).success(function(data) {
		   		 if(data!=null){			 
		   			 $scope.taskList= data.taskList;
		   			$scope.taskCounts=data.taskCounts;
		   			 console.info("",$scope.taskList);
		   		 }	
		   		$location.url('/contractList') 
		   	 	});
		}
		$scope.historyTaskList= function(){
			$http({
		   		 method:'GET',
		   			url:"/tms/qemCollegeCheck/contractList"
		   	 	}).success(function(data) {
		   		 if(data!=null){			 
		   			 $scope.taskList= data.taskList;
		   			$scope.taskCounts=data.taskCounts;
		   			 console.info("",$scope.taskList);
		   		 }	
		   		$location.url('/historyList') 
		   	 	});
		}
		//任务书详情
	    $scope.taskDetail=function(id){
	    	console.log(id);
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
//	      			 console.info("",$scope.task);
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
				   		  $scope.taskCounts=data.taskCounts;
						  $location.url('/contractList')
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
		$scope.filterTask = function(item){
			var result=true;
			if($scope.action.type!=null && $scope.action.type!='2'){
				var type=parseInt($scope.action.type)
				result=result && (item.runStatus==type);
			}
			if($scope.action.type=='2'){
				result=result && (item.status>0);
			}
			return result;
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
	    //排序
		$scope.orderBy = function(col){
//			console.info(col,$scope.order);
			if($scope.order==col)
				$scope.order = "-"+col;
			else 
				$scope.order = col;		
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
		//全选
	    $scope.doselectAllTask= function(){		
//	    	console.log($scope.taskList);
			angular.forEach($scope.taskList,function(data){				
				if($scope.listConditions(data)) 
					data.selected=$scope.action.selectAll;					
			});
		}
	}]);