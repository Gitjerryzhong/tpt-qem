cCheckApp.controller('defaultCtrl',['$rootScope','$scope','$http','$location','$filter',function($rootScope,$scope,$http,$location,$filter){ 
		$scope.offset =0;
		$scope.max=10;
		$scope.audits={}
		$scope.pagerT={}
		$scope.taskList={}
		$scope.taskCounts={}
		$scope.action={};
		$scope.trial={};
		$scope.trial.status='10';
		$scope.action.type='1';
		$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
		$scope.projectStatus=[{"id":"10","name":"年检报告提交"},
		                      {"id":"1101","name":"学院通过年检"},{"id":"1102","name":"学院不通过年检"},{"id":"1103","name":"学院退回年检"},
		                      {"id":"20","name":"中检报告提交"},{"id":"2101","name":"学院通过中报"},
		                      {"id":"2102","name":"学院不通过中报"},{"id":"2103","name":"学院退回中报"}];

		
	    $scope.dateFormat = function(jsondate){
	    	return new Date(jsondate);
	    }
	    	   
	    $scope.levelText = function(level){
	    	var LEVEL = {
	    			"1": "校级",
	    			"2": "省级",
	    			"3": "国家级"	    		
	    		};
	    	return LEVEL[level];
	    }
	    
		$scope.clearNull= function(str){
			return str=='null'?'':str;
		}
		
		//年度检查
		$scope.annual= function(){
			$http({
		   		 method:'GET',
		   			url:"/tms/qemCollegeCheck/annualTasks"
		   	 	}).success(function(data) {
		   		 if(data!=null){			 
		   			 $scope.taskList= data.taskList;
		   			$scope.taskCounts=data.taskCounts;
//		   			 console.info("",$scope.taskList);
		   		 }	
		   		$location.url('/taskList') 
		   	 	});
		}
		//中期检查
		$scope.mid= function(){
			$scope.trial.status='20';
			$http({
		   		 method:'GET',
		   			url:"/tms/qemCollegeCheck/midTasks"
		   	 	}).success(function(data) {
		   		 if(data!=null){			 
		   			 $scope.taskList= data.taskList;
		   			$scope.taskCounts=data.taskCounts;
//		   			 console.info("",$scope.taskList);
		   		 }	
		   		$location.url('/taskList') 
		   	 	});
		}
		$scope.annual();
		
		//任务书详情
	    $scope.taskDetail=function(id){
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
			$http({
				method:'POST',
				url:"/tms/qemCollegeCheck/auditTaskSave",
				data:JSON.stringify({
							form_id:formId,
							check:act,
							prevId:prevId,
							nextId:nextId,
							content:$scope.trial.content,
							currentStage:$scope.currentStage()}),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
				  if(data!=null ){
					  if(data.none){
						  $scope.mid();
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
			if($scope.trial.typeName){				
				result=result && (item.type==$scope.trial.typeName);
			}
			if($scope.trial.bn){				
				result=result && (item.bn==$scope.trial.bn);
			}
			if($scope.trial.status){
				result=result && (item.runStatus==$scope.trial.status);
			}
			if($scope.trial.beginYear){
				result=result && (item.beginYear==$scope.trial.beginYear);
			}
			if($scope.trial.expectedEnd){
				result=result && (item.expectedEnd==$scope.trial.expectedEnd);
			}
			return result;
		}
		$scope.stageDetail=function(id){
	    	$http({
	     		 method:'GET',
	     			url:"/tms/qemCollegeCheck/stageDetail",
	     			params:{
	     				id:id
	     			}
	     	 	}).success(function(data) {
	     		 if(data!=null){			 
	     			 $scope.task= data.task;
	     			 $scope.task.qemTypeName=data.qemTypeName;
	     			$scope.task.buget=data.buget;
	      			 $scope.task.userName=data.userName;
	     			 $scope.fileList= data.fileList;
	     			 $scope.stages= data.stages;
	     			 $scope.pagerT = data.pager;
//	     			 console.info("stage",data.fileList);
	     		 }	
	     		$location.url('/stageDetail') 
	     	 });
	    }
	    $scope.stageTitle=function(index){
	    	var TITLE = {
	    			"1": "年度报告",
	    			"2": "中期报告",
	    			"3": "结题报告"	    		
	    		};
	    	return TITLE[index];
	    }
	    //全选
	    $scope.doselectAllTask= function(){		
//	    	console.log($scope.taskList);
			angular.forEach($scope.taskList,function(data){				
				if($scope.listConditions(data)) 
					data.selected=$scope.action.selectAll;					
			});
		}
	    var currentYear=$filter('date')(new Date(),'yyyy');
	    $scope.isMid=function(){
	    	 if( $scope.task.expectedMid==currentYear ){
	    		 return true;
	    	 }
	    	 return false;
	     }
	     $scope.isEnd=function(){
	    	 if( $scope.task.expectedEnd==currentYear ){
	    		 return true;
	    	 }
	    	 return false;
	     }
	     $scope.beforeMid=function(){
	    	 if( $scope.task.expectedMid>currentYear && $scope.task.beginYear< currentYear){
	    		 return true;
	    	 }
	    	 return false;
	     }
	    $scope.currentStage=function(){
	    	 var id=0;
	    	 if($scope.task.status==10){
	    		 if($scope.beforeMid()) id=1;
	    		 else if($scope.isMid()) id=2;
	    		 else if($scope.isEnd()) id=3;
	    		 else if($scope.task.runStatus>=10){
	    			 var runstatus=$scope.task.runStatus.toString();
	    			 id=runstatus.left(1)
	    		 }
	    	 }else if($scope.task.status==20){
	    		 id=3;
	    	 }
	    	 return id;
	     }
	    $scope.auditAble=function(item){
	    	console.log(item.runStatus);
	    	if(item.runStatus==10 || item.runStatus==20 ||item.runStatus==30) return true;
	    	else return false;
	    }
	}]);