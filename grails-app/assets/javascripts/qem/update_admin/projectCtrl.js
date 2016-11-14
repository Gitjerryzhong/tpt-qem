updateAdminApp.controller('defaultCtrl',['config','$scope','$http','$location',function(config,$scope,$http,$location){ 
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
		$scope.trial.status=0;
		$scope.pager={}
		$scope.teachers={}
		$scope.taskList=config.taskList;
		$scope.taskCounts={}
		$scope.projectLevels=[{"id":1,"name":"校级"},{"id":2,"name":"省级"},{"id":3,"name":"国家级"}];
		$scope.titles=['教授', '副教授', '讲师', '助教', '其他正高级', '其他副高级', '其他中级', '其他初级', '未评级'];
		$scope.positions=['校长', '副校长', '处长', '副处长', '院长/部长/主任', '副院长/副部长/副主任', '系主任/专业负责人', '院长助理/部长助理', '实验室负责人','其他','无'];
		$scope.degrees=['大专','本科','硕士','博士'];
		$scope.updateTypes=[{"id":1,"name":"变更项目负责人"},{"id":2,"name":"延期"},{"id":3,"name":"改变项目名称"},{"id":4,"name":"研究内容重大调整"},{"id":5,"name":"自行中止项目"},{"id":6,"name":"改变成果形式"},{"id":7,"name":"其他"}];
		$scope.statuses=[{"id":0,"name":"未审"},{"id":1,"name":"同意变更"},{"id":2,"name":"不同意变更"},{"id":3,"name":"退回"}];
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
		$scope.historyRequestList= function(){
			$scope.trial={}
			$http({
		   		 method:'GET',
		   			url:"/tms/qemUpdateAdmin/historyRequestList"
		   	 	}).success(function(data) {
		   		 if(data!=null){			 
		   			 $scope.taskList= data.taskList;
		   			$scope.taskCounts=data.taskCounts;
		   			 console.info("",$scope.taskList);
		   		 }	
		   		$location.url('/historyList') 
		   	 	});
		}
		//初始化显示申请列表————————————————————改成grails index方法直接传值
//		$scope.historyRequestList();
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
			if($scope.trial.status){
				result=result && (item.status==$scope.trial.status);
			}
			return result;
		}
	    //变更申请表详情
	    $scope.updateDetail=function(id){
	    	$http({
	      		 method:'GET',
	      			url:"/tms/qemUpdateAdmin/updateDetail",
	      			params:{
	      				id:id
	      			}
	      	 	}).success(function(data) {
	      		 if(data!=null){
	      			 console.log(data);
	      			$scope.form=data.form;
	      			$scope.form.teacherName=data.teacherName;
	      			$scope.form.type=data.type;
	      			$scope.form.commitDate=data.commitDate;
	      			$scope.task= data.task;
	      			$scope.task.origType=data.origType;
	      			$scope.task.origTeacherName = data.origTeacherName;
	      			$scope.fileList= data.fileList;
	      			$scope.pager=data.pager;
	      		 }	
	      		$location.url('/updateDetail') 
	      	 });
	    }
	  //审批
	    $scope.okT = function(act,formId,prevId,nextId){
			$http({
				method:'POST',
				url:"/tms/qemUpdateAdmin/auditUpdateSave",
				data:JSON.stringify({
							form_id:formId,
							check:act,
							prevId:prevId,
							nextId:nextId,
							content:$scope.trial.content}),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
				  if(data!=null ){
					  if(data.none){
						  $scope.historyRequestList();
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
	}]);