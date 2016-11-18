cCheckApp.controller('defaultCtrl',['$rootScope','$scope','$http','$location','FileUploader','aboutLeaders',function($rootScope,$scope,$http,$location,FileUploader,aboutLeaders){ 
		$scope.offset =0;
		$scope.show1=true;
		$scope.max=10;
		var index=1;
		$scope.commitAction=false;
		$scope.audits={}
		$scope.qemTypes={}
		$scope.trial={}
		$scope.trial.show1=true;
		$scope.trial.show2=true;
		$scope.trial.show3=true;
		$scope.pagerT={}
		$scope.teachers={}
		$scope.taskList={}
		$scope.taskCounts={}
		$scope.projectLevels=aboutLeaders.projectLevels;
		$scope.titles=aboutLeaders.titles;
		$scope.positions=aboutLeaders.positions;
		$scope.degrees=aboutLeaders.degrees;
		$scope.updateTypes=aboutLeaders.updateTypes;
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
		//学院项目列表
		$scope.contractList= function(){
			$scope.menu_status=1;
			$scope.trial={}
			$http({
		   		 method:'GET',
		   			url:"/tms/qemTaskUpdate/contractList"
		   	 	}).success(function(data) {
		   		 if(data!=null){			 
		   			 $scope.taskList= data.taskList;
		   			$scope.taskCounts=data.taskCounts;
//		   			 console.info("",$scope.taskList);
		   		 }	
		   		$location.url('/contractList') 
		   	 	});
		}
		$scope.contractList();
		$scope.historyRequestList= function(){
			$scope.menu_status=2;
			$scope.trial={}
			$http({
		   		 method:'GET',
		   			url:"/tms/qemCollegeUpdate/historyRequestList"
		   	 	}).success(function(data) {
		   		 if(data!=null){			 
		   			 $scope.taskList= data.taskList;
		   			$scope.taskCounts=data.taskCounts;
		   			 console.info("",$scope.taskList);
		   		 }	
		   		$location.url('/historyList') 
		   	 	});
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
	    //审核状态注释
	    $scope.statusUp = function(status){
	    	var STATUS = {
	    			"0" : "未审",
	    			"1" : "同意变更",
	    			"2" : "不同意变更",
	    			"3" : "退回"
	    		};
	    	return STATUS[status];
	    }
	    //变更内容注释
	    $scope.updateText = function(id){
	    	var UPDATECONTENT = {
	    			"1" : "变更项目负责人",
	    			"2" : "延期",
	    			"3" : "改变项目名称",
	    			"4" : "研究内容重大调整",
	    			"5" : "自行中止项目",
	    			"6" : "改变成果形式",
	    			"7" : "其他"
	    		};
	    	return UPDATECONTENT[id];
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
			result=result && (item.status==10); //首先过滤掉未立项的
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
		//已立项项目汇总筛选
		$scope.updateListConditions = function(item){
			var result=true;
			if($scope.trial.level){				
				result=result && (item.projectLevel==$scope.trial.level);
			}
			if($scope.trial.typeName){				
				result=result && (item.type==$scope.trial.typeName);
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
			if($scope.trial.updateType){
				result=result && (item.updateType==$scope.trial.updateType);
			}
			return result;
		}
		//变更申请表
	    $scope.updateRequest= function(id){	
	    	$scope.task={}
	    	$scope.updateSuccess=false;					//打开详情页面时先初始化
	    	$http({
	      		 method:'GET',
	      			url:"/tms/qemCollegeUpdate/updateForm",
	      			params:{
	      				id:id
	      			}
	      	 	}).success(function(data) {
	      		 if(data!=null){
	      			 $scope.task= data.task;
		      		 $scope.qemTypes = data.qemTypes;
		      		 $scope.fileList= data.fileList;
		      		 $scope.teachers=data.teachers;
	      			 console.log($scope.task);
	      		 }	
	      		$location.url('/updateRequest') 
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
	      			 $scope.fileList= data.fileList;
	      		 }	
	      		$location.url('/taskDetail') 
	      	 });
	    }
	    //变更申请表详情
	    $scope.updateDetail=function(id){
	    	$http({
	      		 method:'GET',
	      			url:"/tms/qemCollegeUpdate/updateDetail",
	      			params:{
	      				id:id
	      			}
	      	 	}).success(function(data) {
	      		 if(data!=null){
	      			$scope.form=data.form;
	      			$scope.form.teacherName=data.teacherName;
	      			$scope.form.type=data.type;
	      			$scope.form.commitDate=data.commitDate;
	      			$scope.task= data.task;
	      			$scope.task.origType=data.origType;
	      			$scope.task.origTeacherName = data.origTeacherName;
	      			$scope.fileList= data.fileList;
	      		 }	
	      		$location.url('/updateDetail') 
	      	 });
	    }
	    //回车添加教师
	    $scope.myKeyup = function(e){		
	        var keycode = window.event?e.keyCode:e.which;
	        if(keycode==13){ 
	        	$http({
	        		 method:'GET',
	        			url:"/tms/qemCollegeUpdate/teacherName",
	        			params:{
	        				id:$scope.task.teacherId
	        			}
	        	 	}).success(function(data) {
	        	 		if(data.name!=null)
	        	 			$scope.task.userName=data.name; 
	        	 		else $scope.task.userName="无此教师";
	        	 });        	
	        }
	    };
	    //选择变更内容后自动弹出相关填写表格
	    $scope.updateContentChanged=function(item1,item2){
	    	console.log($scope.task.updateType)
	    	if($scope.task.updateType==1) {item1.open=true;item2.open=false;}
	    	else if($scope.task.updateType>1 && $scope.task.updateType<5) {item2.open=true;item1.open=false}
	    }
	    //提交申请
	    $scope.commit=function(isInvalid,item1,item2){
//	    	console.log(isInvalid)
	    	if( isInvalid || $scope.task.userName==null || $scope.task.userName=='无此教师'){
	    		$scope.commitAction=true;
	    		item1.open=true;
	    		item2.open=true;
	    	}else{
	    		$http({
					method:'POST',
					url:"/tms/qemCollegeUpdate/updateCommit",
					data:JSON.stringify($scope.task),
					headers:{ 'Content-Type': 'application/json' } 
				  }).success(function(data) {
					  $scope.taskCounts=data.taskCounts;
					  $location.url('/contractList');
					}).error(function(data){
						$scope.commitAction=true;
						if(data.userName)
							$scope.task.userName="无此教师";
						item1.open=true;
						item2.open=true;
					});
	    	}
	    }
	    //异步查找老师
	    $scope.findTeacher=function(teacherName){
	    	$http({
       		 method:'GET',
       			url:"/tms/qemCollegeUpdate/getTeachers",
       			params:{
       				teacherName:teacherName
       			}
       	 	}).success(function(data) {
       	 		$scope.teachers=data.teachers;
       	 	}); 
	    }
	  //上传附件
	     var uploader = $scope.uploader = new FileUploader({
	         url: '/tms/qemCollegeUpdate/uploadFiles'
	     });
	     uploader.onBeforeUploadItem = function(item) {
	         formData = [{
	             taskId:  $scope.task.taskId,
	             isDeclaration: '申报书'
	         }];
	         Array.prototype.push.apply(item.formData, formData);
	     };
	     uploader.onSuccessItem = function(fileItem, response, status, headers) {
	         $scope.fileList= response.fileList;        
	     };
	}]);