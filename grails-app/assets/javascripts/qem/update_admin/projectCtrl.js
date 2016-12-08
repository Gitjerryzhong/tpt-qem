updateAdminApp.controller('defaultCtrl',['config','$scope','$http','$filter','$location','aboutUpdate',function(config,$scope,$http,$filter,$location,aboutUpdate){ 
		$scope.updateView={};
		$scope.taskView={};
		$scope.fileList ={};
		$scope.declarations ={};
		$scope.teachers ={};
		$scope.qemTypes ={};
		$scope.commitAction=false;
		$scope.audits={}
		$scope.qemTypes={}
		$scope.trial={}
		$scope.trial.status=0;
		$scope.teachers={}
		$scope.taskList=config.taskList;
		$scope.taskList=$filter('groups')($scope.taskList);
	    	   

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
	    $scope.updateText = function(updateTypes){
	    	var items=updateTypes.split(";");
	    	var typesText ="";
	    	angular.forEach(items,function(item){
	    		if(item){
	    			typesText +=aboutUpdate.updateTypeText[item]+"；"
//	    			console.log(item);
	    		}
	    			
	    	})
	    	return typesText;
	    }
	    $scope.updateStatusText = function(flow,auditStatus){
			if(flow==2 && auditStatus==0) return "学校未审";
			return aboutUpdate.updateStatus[flow][auditStatus];
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
	    $scope.updateDetail=function(item){
	    	$http({
	      		 method:'GET',
	      			url:"/tms/qemUpdateAdmin/updateDetail",
	      			params:{
	      				id:item.id
	      			}
	      	 	}).success(function(data) {
	      		 if(data!=null){
	      			$scope.getUpdateView(data);
	      			$scope.updateView.groups=item.groups;
	      		 }	
	      		$location.url('/updateDetail') ;
	      	 });
	    }
	    
	  //刷新
	    $scope.refresh = function(data){
	    	$scope.taskList=data.taskList;
	    	$scope.taskList=$filter('groups')($scope.taskList);
	    }

		$scope.getUpdateView=function(data){
//			console.log(data);
			$scope.taskView=data.task;
			$scope.taskView.teacherName = data.origTeacherName;
			$scope.taskView.type = data.type;
			$scope.updateView=data.updateView;
			$scope.updateView.teacherName = data.teacherName;
			$scope.fileList =data.fileList;
			$scope.auditList=data.audits;
//			处理审批通过后的情况
			if($scope.updateView.flow==2 && $scope.updateView.auditStatus==1){
				$scope.auditList = $filter('orderBy')($scope.auditList,'date',true);
				var content=$scope.auditList[0].content;
				content=content.slice(content.lastIndexOf(';{"')+1);
				$scope.taskView=angular.fromJson(content);
				console.log(content);
			}
			$scope.declarations = data.declarations;

		}
		$scope.getFileName = function(item){
			return item.slice(item.lastIndexOf('___') + 3);
		}
	}]);