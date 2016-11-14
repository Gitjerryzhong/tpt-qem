tAdminApp.controller('defaultCtrl',['$rootScope','$scope','$http','$location','$filter','config','$modal',function($rootScope,$scope,$http,$location,$filter,config,$modal){ 
		$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
		$scope.maxExperts=["3","4","5","6","7","8","9"];
		$scope.offset =0;
		$scope.max=8;
		var index=1;
		var status=0;
		var defaultNum=6;  //如果不到这个数，留空位以便手动添加专家
		$scope.requests={}
		$scope.rules={}
		$scope.rules.value2=5;
		$scope.rules.value3=3;
		$scope.pager={}	
		$scope.pagerT={}
		$scope.allexperts={}
		$scope.clicked={}  //点击标题
		$scope.selected={} //筛选条件
		$rootScope.actived=[1,0,0,0,0,0,0,0,0]; //当前活动菜单
		var item_id=0;
		$scope.taskList=config.taskList;
		$scope.trial={};
		$scope.trial.status='10';
		$scope.task={};
		$scope.taskCounts=config.taskCounts;
		$scope.action={};
		$scope.action.type='201';
		$scope.action.stage='0';
	    $scope.action.hasexpert = '0';
	    $location.url('/taskList');
	    $scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
		$scope.projectStatus=[{"id":"10","name":"在研"},{"id":"20","name":"结题"},{"id":"32","name":"终止"},{"id":"33","name":"中止"}];
		$scope.taskStatus=[
		                      {"id":"1101","name":"学院通过年检"},{"id":"1102","name":"学院不通过年检"},{"id":"1103","name":"学院退回年检"},{"id":"11","name":"年检确定评审"},
		                      {"id":"2101","name":"学院通过中报"},{"id":"2102","name":"学院不通过中报"},{"id":"2103","name":"学院退回中报"},{"id":"21","name":"中检确定评审"},
		                      {"id":"3101","name":"学院通过结项"},{"id":"3102","name":"学院不通过结项"},{"id":"2103","name":"学院退回结项"},{"id":"31","name":"结项确定评审"}];
		var upper_vm=$scope;
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
	    
		$scope.getFileName = function(src){
			return src.slice(src.lastIndexOf('/') + 1);
		}
		$scope.clearNull= function(str){
			return str=='null'?'':str;
		}		

		$scope.span_click=function(item){
			item.clicked=true;
		}
		$scope.onClick=function(fields){
			$scope.clicked[fields]=true;
		}
		//调换专家
		$scope.expChange = function(item,index){
//			console.info(item.exp[index].id.split("|")[0])
			var tempstr="";
			var tempexp="";
			var i=0;
			angular.forEach(item.exp,function(data){
				if(i==index) {
					tempexp+=";"+data.id;
					tempstr+=";"+data.id.split("|")[0];
					data.clicked=false;
					data.name=data.id;
				}
				else if(data.name!='空'){
					tempexp+=";"+data.name;
					tempstr+=";"+data.name.split("|")[0];
				}
				i++;
			});
			item.experts=tempexp;			
//			console.info(tempstr,item);
			$http({
				method:'POST',
				url:"/tms/qemTaskAdmin/doUpdateExpert",
				data:JSON.stringify({id:item.reviewId,experts:tempstr}),
				headers:{ 'Content-Type': 'application/json' }
			 }).success(function(data) {
			 }); 
		}
		$scope.onChange=function(fields){
			$scope.clicked[fields]=false;
			console.log($scope.selected.type);
		}
		
		$scope.orderBy = function(col){
//			console.info(col,$scope.order);
			if($scope.order==col)
				$scope.order = "-"+col;
			else 
				$scope.order = col;		
	    }
		//任务书列表
	$scope.tasks=function(){
	    	$http({
	   		 method:'GET',
	   			url:"/tms/qemTaskAdmin/showTasks"
	   	 	}).success(function(data) {
	   		 if(data!=null){			 
	   			 $scope.taskList= data.taskList;
	   			$scope.taskCounts=data.taskCounts;
	   		 }	
	   		$scope.action.selectAll=false;
	   		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
	   		$rootScope.actived[0]=1;
	   		$location.url('/taskList') 
	   	 });
	 }
	//已立项项目汇总
	$scope.tasksummary=function(){
		$scope.trial.runStatus =null;
		$http({
	   		 method:'GET',
	   			url:"/tms/qemTaskAdmin/showTasks"
	   	 	}).success(function(data) {
	   		 if(data!=null){			 
	   			 $scope.taskList= data.taskList;
	   			$scope.taskCounts=data.taskCounts;
	   		 }	
	   		$scope.action.selectAll=false;
	   		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
	   		$rootScope.actived[7]=1;
	   		$location.url('/taskSummary') 
	   	 });
	}
	//已立项项目汇总筛选
	$scope.listConditions = function(item){
		var result=true;
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
		if($scope.trial.runStatus){
			result=result && (item.runStatus==$scope.trial.runStatus);
		}
		if($scope.trial.beginYear){
			result=result && (item.beginYear==$scope.trial.beginYear);
		}
		if($scope.trial.expectedEnd){
			result=result && (item.expectedEnd==$scope.trial.expectedEnd);
		}
		return result;
	}
	//任务书详情
    $scope.taskDetail=function(id){
    	$scope.task={}
    	$scope.updateSuccess=false;					//打开详情页面时先初始化
    	$http({
      		 method:'GET',
      			url:"/tms/qemTaskAdmin/taskDetail",
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
      			 console.log($scope.task);
      		 }	
      		$location.url('/taskDetail') 
      	 });
    }
    $scope.getFileName = function(src){
		return src.slice(src.lastIndexOf('/') + 1);
	}
    //已确认阶段检查列表，分配专家
    $scope.confirmTask= function(id){
    	$http({
      		 method:'GET',
      			url:"/tms/qemTaskAdmin/confirmTask",
      			params:{
      				id:id
      			}
      	 	}).success(function(data) {
      		 if(data!=null){			 
      			 $scope.taskList= data.taskList;
      			$scope.pager.total = data.total;	
      			$scope.taskCounts=data.taskCounts;
      			$location.url('/taskList')
      		 }
      	 	}).error(function(data){
      	 		alert("确认失败！")
      	 	}); 
      		 
    }
    //专家评论汇总
    $scope.recorded = function(){
    	$http({
      		 method:'GET',
      			url:"/tms/qemTaskAdmin/reviewedTaskList"
      	 	}).success(function(data) {
      		 if(data!=null){			 
      			 $scope.requests= data.taskList;
      			 console.info("object",$scope.requests)
      		 }
      		$scope.action.selectAll=false;
      		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
      		$rootScope.actived[5]=1;
      		$location.url('/expertview')
      	 	});    	
    	
    }
    //未评论人数
    $scope.reviewLeft=function(item){
    	if(item.experts){
	    	var experts=item.experts.split(";");
//	    	console.info("experts",experts)
	    	return experts.length-1-item.pass-item.ng-item.waiver;	    		
    	}else{
    		return 0;
    	}

    }
    //全选
    $scope.doselectAllTask= function(){		
//    	console.log($scope.taskList);
		angular.forEach($scope.taskList,function(data){				
			if(data.runStatus==$scope.action.type) 
				data.selected=$scope.action.selectAll;					
		});
	}
    //确认选中的任务
    $scope.confirmAll=function(type){
    	var options=[]
		angular.forEach($scope.taskList,function(data){
			if(data.selected) options.push(data.id);
		});
//    	console.log(type);
    	$scope.action.selectALl=false
		$http({
			method:'POST',
			url:"/tms/QemTaskAdmin/confirmAll",
			params:{type:type},
			data:JSON.stringify({ids:options}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $scope.taskList= data.taskList;
//	   			$scope.pager.total = data.total;
	   			$scope.taskCounts=data.taskCounts;
		  });
    }
    //需要年度检查的列表
    $scope.annual=function(){
    	$scope.action.stage='1';
    	$scope.trial.runStatus='1101';
    	$http({
   		 method:'GET',
   			url:"/tms/qemTaskAdmin/annualTasks"
   	 	}).success(function(data) {
   		 if(data!=null){			 
   			 $scope.taskList= data.taskList;   			
   			$scope.taskCounts=data.taskCounts;
   		 }	
   		 $scope.action.selectAll=false;
   		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
   		$rootScope.actived[1]=1;
   		$location.url('/annualList') 
   	 });
    }
  //需要中期检查的列表
    $scope.mid=function(){
    	$scope.action.stage='2';
    	$scope.trial.runStatus='2101';
    	$http({
   		 method:'GET',
   			url:"/tms/qemTaskAdmin/midTasks"
   	 	}).success(function(data) {
   		 if(data!=null){			 
   			 $scope.taskList= data.taskList;   			
   			$scope.taskCounts=data.taskCounts;
//   			 console.info("",$scope.taskList);
   		 }	
   		 $scope.action.selectAll=false;
   		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
   		$rootScope.actived[2]=1;
   		$location.url('/annualList') 
   	 });
    }
  //需要结题检查的列表
    $scope.end=function(){
    	$scope.action.stage='3';
    	$http({
   		 method:'GET',
   			url:"/tms/qemTaskAdmin/endTasks"
   	 	}).success(function(data) {
   		 if(data!=null){			 
   			 $scope.taskList= data.taskList;   			
   			$scope.taskCounts=data.taskCounts;
//   			 console.info("",$scope.taskList);
   		 }	
   		 $scope.action.selectAll=false;
   		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
   		$rootScope.actived[3]=1;
   		$location.url('/annualList') 
   	 });
    }
    //处理专家的格式化显示
    $scope.showExperts=function(){
		angular.forEach($scope.taskList,function(item){
			 item.exp=[]
			 if(item.experts){
				 var experts=item.experts.split(";");							 
				angular.forEach(experts,function(data){
					if(data!=''){
					var exp_item={};
					var myjson=$filter('filter')($scope.allexperts,{'id':data})[0];
					exp_item.name=myjson.id+"|"+myjson.name;
					exp_item.clicked=false;
					item.exp.push(exp_item);
					}
				});
			 }
				 var exp_item={};
				exp_item.name='空';
				exp_item.clicked=false;
				item.exp.push(exp_item);
		 });
	}
    //确认任务书
    $scope.confirmed=function(){
    	$http({
   		 method:'GET',
   			url:"/tms/qemTaskAdmin/taskForExperts"
   	 	}).success(function(data) {
   		 if(data!=null){			 
   			 $scope.taskList= data.taskList;   
   			 console.log(data.taskList);
   			 $scope.allexperts = data.experts;
   			$scope.taskCounts=data.taskCounts;
   			$scope.showExperts();
   		 }	
   		 $scope.action.selectAll=false;
   		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
   		$rootScope.actived[4]=1;
   		$location.url('/confirmedList') 
   	 });
    }

    $scope.conditions = function(item){
		var result=true;
		
		switch($scope.action.type){
		case '0': return item.runStatus<10;
		case '1': return item.runStatus==10;
		case '2': return item.runStatus>10;
		}
		return result;
	}
    //是否已分配专家过滤器
    $scope.hasexperts = function(item){
		var result=true;
		
		switch($scope.action.hasexpert){
		case '0': return item.experts==null;
		case '1': return item.experts!=null;
		}
		return result;
	}
    
    $scope.stageDetail=function(id){
    	$http({
     		 method:'GET',
     			url:"/tms/qemTaskAdmin/stageDetail",
     			params:{
     				id:id
     			}
     	 	}).success(function(data) {
     		 if(data!=null){			 
     			 $scope.task= data.task;
     			 $scope.fileList= data.fileList;
     			 $scope.stages= data.stages;
//     			 console.info("stage",data.stages);
     		 }	
     		$location.url('/stageDetail') 
     	 });
    }
    $scope.stageAudit=function(id){
    	$http({
     		 method:'GET',
     			url:"/tms/qemTaskAdmin/stageDetail",
     			params:{
     				id:id
     			}
     	 	}).success(function(data) {
     		 if(data!=null){			 
     			 $scope.task= data.task;
     			 $scope.fileList= data.fileList;
     			 $scope.stages= data.stages;
//     			 console.info("stage",data.stages);
     		 }	
     		$location.url('/stageAudit') 
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
    //全选年度检查
    $scope.doselectAllAnnual=function(){
    	angular.forEach($scope.taskList,function(data){				
			if( $scope.listConditions(data)) 
				data.selected=$scope.action.selectAll;					
		});
    }
    //全选已确认任务
    $scope.doselectAllConfirmed=function(){
    	angular.forEach($scope.taskList,function(data){				
			if( $scope.hasexperts(data)) 
				data.selected=$scope.action.selectAll;					
		});
    }
    //分配专家
    $scope.doAssign=function(){
    	var options=[]
		angular.forEach($scope.taskList,function(data){
			if(data.selected) options.push(data.id);
		});
//    	console.log(type);
    	$scope.action.selectALl=false
		$http({
			method:'POST',
			url:"/tms/QemTaskAdmin/doAssigned",
			data:JSON.stringify({projectIds:{ids:options},
				rules:$scope.rules}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  	$scope.taskList= data.taskList;
	   			$scope.taskCounts=data.taskCounts;
	   			$scope.showExperts();
		  });
    }  
    //新建任务界面
    $scope.newtask =function(){
    	$scope.header="未校验";
    	$scope.task={};
    	$http({
   		 method:'GET',
   			url:"/tms/qemTaskAdmin/selections",
   	 	}).success(function(data) {
   	 		$scope.departments=data.departments; 
   	 		$scope.qemTypes = data.qemTypes;
   	 })
    	$location.url('/newtask')
    }
    //回车添加教师
    $scope.myKeyup = function(e){		
        var keycode = window.event?e.keyCode:e.which;
        if(keycode==13){ 
        	$http({
        		 method:'GET',
        			url:"/tms/qemTaskAdmin/teacherName",
        			params:{
        				id:$scope.task.teacherId
        			}
        	 	}).success(function(data) {
        	 		if(data.name!=null)
        	 			$scope.header=data.name; 
        	 		else $scope.header="无此教师";
        	 });        	
        }
    };
    //保存新建任务
    $scope.createTask=  function(){
    	$http({
			method:'POST',
			url:"/tms/QemTaskAdmin/createTask",
			data:JSON.stringify($scope.task),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  alert("创建任务成功！");
		  }).error(function(data){
			  alert("创建任务失败！");
     	 });
    };
    //过滤器方法
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
    //状态注释
    $scope.statusText = function(status){
    	var STATUS = {
    			"0": "未提交",
    			"1": "已提交",
    			"201": "学院确认合同",
    			"202": "学院不同意",
    			"203": "学院退回",
    			"2" : "学校确认合同",
    			"3" : "项目终止",
    			"4" : "学校退回合同",
    			"5" : "项目中止",
    			"10" : "年检报告提交",
    			"11" : "年检确定评审",
    			"1101" : "学院通过年检",
    			"1102" : "学院不通过年检",
    			"1103" : "学院退回年检",
    			"12" : "正安排专家",
    			"13" : "专家评审中",
    			"14" : "年检评审通过",
    			"15" : "年检评审不通过",
    			"16" : "年检退回学院",
    			"20" : "中检报告提交",
    			"21" : "中检确定评审",
    			"2101" : "学院通过中报",
    			"2102" : "学院不通过中报",
    			"2103" : "学院退回中报",
    			"22" : "正安排专家",
    			"23" : "专家评审中",
    			"24" : "中报评审通过",
    			"25" : "中报评审不通过",
    			"26" : "中报退回学院",
    			"30" : "结题报告提交",
    			"31" : "结题确定评审",
    			"3101" : "学院通过结题",
    			"3102" : "学院不通过结题",
    			"3103" : "学院退回结题",
    			"32" : "正安排专家",
    			"33" : "专家评审中",
    			"34" : "结题评审通过",
    			"35" : "结题评审不通过",
    			"36" : "结题退回学院"
    		};
    	return STATUS[status];
    }
  //建设情况注释
    $scope.statusTT = function(status){
    	var STATUS = {
    			"0" : "合同未审",
    			"10" : "在研",
    			
    			"20" : "结题",
    			"32" : "终止",
    			"33" : "中止"
    		};
    	return STATUS[status];
    }
  //审批
    $scope.okT = function(act,formId,prevId,nextId){ 
    	$scope.trial={}
		$scope.trial.form_id=formId
		$scope.trial.check = act;
		$scope.trial.prevId = prevId;
		$scope.trial.nextId = nextId;			
		$http({
			method:'POST',
			url:"/tms/QemTaskAdmin/auditTaskSave",
			data:JSON.stringify($scope.trial),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  if(data!=null ){
				  if(data.none){
					  $scope.taskList= data.taskList;
			   		  $scope.taskCounts=data.taskCounts;
			   		  $location.url('/taskList')
				  }else{
					  $scope.task= data.task;
	      			 $scope.task.typeName=data.taskType;
	      			 $scope.task.userName=data.userName;
	      			 $scope.pagerT = data.pager;
	      			 $scope.fileList= data.fileList;
					 $scope.trial={};					
				  }
				  	$scope.action.selectAll=false;
			   		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
			   		$rootScope.actived[0]=1;
				 }
			});
	};
	 //阶段检查审批
    $scope.okS = function(act,formId,prevId,nextId){ 
    	$scope.trial={}
		$scope.trial.form_id=formId
		$scope.trial.check = act;
		$scope.trial.prevId = prevId;
		$scope.trial.nextId = nextId;			
		$http({
			method:'POST',
			url:"/tms/QemTaskAdmin/auditStageSave",
			data:JSON.stringify($scope.trial),
			params:{type:$scope.action.stage},
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  if(data!=null ){
				  if(!data.task){
					  $scope.taskList= data.taskList;
			   		  $scope.taskCounts=data.taskCounts;
			   		  $location.url('/annualList');
				  }else{
					  $scope.task= data.task;
	      			 $scope.task.typeName=data.taskType;
	      			 $scope.task.userName=data.userName;
	      			 $scope.pagerT = data.pager;
	      			 $scope.fileList= data.fileList;
					 $scope.trial={};					
				  }
				  	$scope.action.selectAll=false;
//			   		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
//			   		$rootScope.actived[0]=1;
				 }
			});
	};
	//备注
	$scope.updateMemo=function(item){
		$http({
			method:'POST',
			url:"/tms/QemTaskAdmin/updateMemo",
			data:JSON.stringify({id:item.id,memo:item.memo}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  item.updateSuccess=true;
			});
	}
	$scope.expertGroup =function(){
    	var modalInstance=$modal.open({
            templateUrl : 'expert_group.html',  //创建文件上传视图
            controller : 'ExpertGroupCtrl',
            backdrop : "static"
        }) 
        modalInstance.result.then(function(selectedExperts){  
                $scope.selectedExperts = selectedExperts;
            },function(){
                $log.info('Modal dismissed at: ' + new Date())
            })
    }
    $scope.applyGroup = function(){
    	var r=confirm("确定要给这些项目指定专家组：“"+$scope.selectedExperts.names+"”吗？");
		if (r==true)
		 {
	    	var options=[]
			angular.forEach($scope.taskList,function(data){
				if(data.selected) options.push(data.id);
			});
	    	if(options.length>0){
	    		$http({
					method:'POST',
					url:"/tms/QemTaskAdmin/applyGroup",
					data:JSON.stringify({ids:options,expertGroup:$scope.selectedExperts.ids}),
					headers:{ 'Content-Type': 'application/json' } 
				  }).success(function(data) {
					  $scope.taskList=data.taskList;
					  $scope.showExperts();
						 $scope.rules.value2=3;
						 $scope.rules.value3=3;
				  });
	    	}
		 }
    }
	}]);