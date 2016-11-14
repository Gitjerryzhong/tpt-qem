pAdminApp.controller('defaultCtrl',['$rootScope','$scope','$http','$location','$filter','$modal','config','bnudisciplines',function($rootScope,$scope,$http,$location,$filter,$modal,config,bnudisciplines){ 
		$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
		$scope.maxExperts=["3","4","5","6","7","8","9"];
		$scope.projectStatus=[{"id":"1","name":"未评审"},{"id":"2","name":"已立项"},{"id":"3","name":"不立项"}];
		$scope.offset =0;
		$scope.max=8;
		var index=1;
		var status=0;
		var defaultNum=6;  //如果不到这个数，留空位以便手动添加专家
		$scope.requests={}
//		$scope.project={}
//		$scope.audits={}
		$scope.trial={}
		$scope.rules={}
		$scope.pager={}
		$scope.groups=[]
		$scope.allexperts={}
		$scope.clicked={}  //点击标题
		$scope.selected={} //筛选条件
		var item_id=0;
		$scope.taskList={};
		$scope.task={};
		$scope.taskCounts={};
		$scope.action={};
		$scope.action.type=1;
		$scope.bns=config.bns;
		$scope.disciplines=bnudisciplines;
		$scope.showRequests = function(){ 
	    	$http({
				 method:'GET',
					url:"/tms/QemProjectAdmin/showRequests",
					params:{
						type: status,
						offset: $scope.offset,
						max: $scope.max
					}
			 }).success(function(data) {
				 if(data!=null ){		
					 $scope.requests=data.requests;	
//					 console.log(data.requests);
					 $scope.pager = data.pager;	
					 $scope.majorTypes=data.majorTypes;
					 $scope.groups=data.groups;
				 }
			 });  
	    	 
	    };

	    $scope.details = function(itemId){
//	    	console.info("projectId",itemId);
	    	$http({
				 method:'GET',
					url:"/tms/QemProjectAdmin/getRequestDetail",
					params:{
						form_id: itemId 
					}
			 }).success(function(data) {
				 if(data!=null ){	
					 $scope.project=data.form;
					 $scope.audits=data.audits;
					 $scope.fileList = data.fileList;
//					 $scope.pager = data.pager;
//					 console.info($scope.pager);
					 $location.url('/details');
				 }
			 });  
	    }
	    $scope.meetingView=function(id){	
	    	if(id!=item_id) $scope.review={};
	    	item_id=id;
	    	$scope.review.projectId=id;	
	    	
	    }	    

	    $scope.dateFormat = function(jsondate){
	    	return new Date(jsondate);
	    }
	    
	    $scope.actionText = function(action){
	    	var ACTIONS = {
	    			"10": "提交申请",
	    			"11": "撤销申请",
	    			"12": "修改申请",
	    			"20": "学院审核通过",
	    			"21": "学院审核不通过",
	    			"22": "专家建议立项",
	    			"23": "专家建议不予立项",
	    			"24": "学院审核撤销",
	    			"30": "终审立项",
	    			"31": "终审不立项",
	    			"40": "关闭申请"
	    		};
	    	return ACTIONS[action];
	    }

	    $scope.levelText = function(level){
	    	var LEVEL = {
	    			"1": "校级",
	    			"2": "省级",
	    			"3": "国家级"	    		
	    		};
	    	return LEVEL[level];
	    }
	    
	    $scope.statusText = function(status){
	    	var STATUS = {
	    			"0": "新申请",
	    			"1": "已分配专家",
	    			"2": "专家已评审",
	    			"4": "立项",
	    			"5": "不予立项",
	    			"6": "须修改"	    		
	    		};
	    	return STATUS[status];
	    }
	    $scope.reviewLeft=function(item){
	    	if(item.experts){
		    	var experts=item.experts.split(";");
//		    	console.info("experts",experts)
		    	return experts.length-1-item.pass-item.ng-item.waiver;	    		
	    	}else{
	    		return 0;
	    	}

	    }
	   
	    $scope.goTrial = function(type){
	    	status=type;
	    	$scope.selected={} //筛选条件
	    	$scope.requests={};
	    	$scope.trial={};
	    	$scope.showRequests();
	    	switch(type){
	    	case 0: $location.url('/default');
	    			break;
	    	case 1: $scope.trial.majorTypeChecked=false;
					$scope.trial.levelChecked=false;
					$scope.trial.majorType="文学";
					$scope.trial.level="1";
					$scope.trial.groupId=null
					$scope.trial.groupChecked=true;
	    			$location.url('/groups');
					break;
	    	case 3: $location.url('/expertview');
					break;
	    	}
	    	
	    	
	    }

		$scope.getFileName = function(src){
			return src.slice(src.lastIndexOf('/') + 1);
		}
		$scope.clearNull= function(str){
			return str=='null'?'':str;
		}
		
		$scope.conditions = function(item){
			var result=true;
			if($scope.trial.majorTypeChecked){
				result=result && (item.majorTypeName===$scope.trial.majorType);
			}
			if($scope.trial.levelChecked){				
				result=result && (item.projectLevel==$scope.trial.level);
			}
			if($scope.trial.groupChecked){				
				result=result && (item.groupId==$scope.trial.groupId);
			}
			return result;
		}
		$scope.createGroup=function(){
			var options=[]
			angular.forEach($scope.requests,function(data){
				if(data.selected) options.push(data.id);
			});
			$http({
				method:'POST',
				url:"/tms/QemProjectAdmin/createGroup",
				data:JSON.stringify({ids:options}),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
				  $scope.requests=data.requests;	
//					 console.info('groups',data.groups);
					 $scope.pager = data.pager;	
					 $scope.majorTypes=data.majorTypes;
					 $scope.groups=data.groups;
			  });
		}
		$scope.groupText = function(str){			
			return str?str:'未分组';
		}
		$scope.doselectAll= function(){	
			angular.forEach($scope.requests,function(data){				
				if($scope.conditions(data)) 
					data.selected=$scope.trial.selectAll;					
			});
		}
		$scope.doselectAllNew= function(){	
//			$scope.requests=$filter('filter')($scope.requests,object);
			angular.forEach($scope.requests,function(data){				
				if($scope.filterCondition(data)) 
					data.selected=$scope.trial.selectAll;					
			});
		}
		$scope.filterCondition=function(item){
			var result=true;
			if($scope.selected.type){
				result=result && (item.type===$scope.selected.type);
			}
			if($scope.selected.groupId){
//				console.log($scope.selected.groupId=='未分组');
				if($scope.selected.groupId=='未分组') {
					result=result && (item.groupId===null);
				}else{
					result=result && (item.groupId===$scope.selected.groupId);
				}				
			}
			if($scope.selected.departmentName){				
				result=result && (item.departmentName==$scope.selected.departmentName);
			}
			if($scope.selected.discipline){				
				result=result && (item.discipline==$scope.selected.discipline);
			}
			return result;
		}
		$scope.moveOut =function(){
			var options=[]
			angular.forEach($scope.requests,function(data){
				if(data.selected) options.push(data.id);
			});
			$http({
				method:'POST',
				url:"/tms/QemProjectAdmin/moveOut",
				data:JSON.stringify({ids:options}),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
				  $scope.requests=data.requests;	
//					 console.info('groups',data.groups);
					 $scope.pager = data.pager;	
					 $scope.majorTypes=data.majorTypes;
					 $scope.groups=data.groups;
			  });
		}
		$scope.assigned = function(bn){
			$scope.selected={} //筛选条件
			$scope.trial={} 
			$http({
				 method:'GET',
					url:"/tms/QemProjectAdmin/expertRequest",
					params:{
						bn:bn
					}
			 }).success(function(data) {
				 if(data!=null ){		
					 $scope.requests=data.requests;
					 $scope.allexperts = data.experts;
					 $scope.pager.total = data.total;
					 $scope.showExperts();
					 $scope.rules.value2=3;
					 $scope.rules.value3=3;
					 $location.url('/assigned')
				 }
			 });  
		}
		$scope.showExperts=function(){
//			console.log($scope.requests);
			 angular.forEach($scope.requests,function(item){
				 item.exp=[]
				 if(item.experts){
					 var experts=item.experts.split(",");							 
					angular.forEach(experts,function(data){
						var exp_item={};
						exp_item.name=data;
						exp_item.clicked=false;
						item.exp.push(exp_item);
					});
				 }
//				 for(var i=item.exp.length ;i< defaultNum;i++){
//				 在最后增加一个空
					 var exp_item={};
					exp_item.name='空';
					exp_item.clicked=false;
					item.exp.push(exp_item);
//				 }
					 
			 });
		}
//		 $scope.assigned();  
//		    $location.url('/default');
		$scope.doAssigned=function(){
			
			$http({
				method:'POST',
				url:"/tms/QemProjectAdmin/doAssigned",
				data:JSON.stringify($scope.rules),
				headers:{ 'Content-Type': 'application/json' }
			 }).success(function(data) {
				 $scope.requests=data.requests;
				 $scope.showExperts();
			 });  
		}
		$scope.span_click=function(item){
			item.id=item.name;
//			console.log(item);
			item.clicked=true;
		}
		$scope.onClick=function(fields){
			$scope.clicked[fields]=true;
		}
		$scope.expChange = function(item,index){
//			console.info(item.exp[index].id.split("|")[0])
			var tempstr="";
			var tempexp="";
			var i=0;
			angular.forEach(item.exp,function(data){
				if(i==index) {
					if(data.id==null || data.id==""){
						data.name='空';
					}else{
						tempexp+=";"+data.id;
						tempstr+=";"+data.id.split("|")[0];
						data.name=data.id;
					}					
					data.clicked=false;
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
				url:"/tms/QemProjectAdmin/doUpdateExpert",
				data:JSON.stringify({id:item.id,experts:tempstr}),
				headers:{ 'Content-Type': 'application/json' }
			 }).success(function(data) {
//				 $scope.requests=data.requests;
//				 $scope.showExperts();
			 }); 
		}
		$scope.onChange=function(fields){
			$scope.clicked[fields]=false;
		}
		$scope.commit=function(item){			
			$http({
				method:'POST',
				url:"/tms/QemProjectAdmin/finalView",
				data:JSON.stringify(item),
				headers:{ 'Content-Type': 'application/json' }
			 }).success(function(data) {
				 item.status=data.isReviewed;
				 $scope.pager.total = data.total;
			 }); 
		}
		$scope.orderBy = function(col){
//			console.info(col,$scope.order);
			if($scope.order==col)
				$scope.order = "-"+col;
			else 
				$scope.order = col;		
	    }

	    $scope.disabled_prev =function(){
    	if($scope.offset<$scope.max){
    		return 'disabled';
    	}else return '';
    }
    $scope.disabled_next =function(){
    	if($scope.pager.total==null ){return 'disabled';}
    	else if($scope.offset+$scope.max>= $scope.pager.total.total){
    		return 'disabled';
    	}else return '';
    }
//    $scope.disabled_p1 =function(){
//    	if($scope.pager.prevId==null){
//    		return 'disabled';
//    	}else return '';
//    }
//    $scope.disabled_n1 =function(){
//    	if($scope.pager.nextId==null){
//    		return 'disabled';
//    	}else return '';
//    }
//   
    $scope.next = function(){
    	$scope.offset =$scope.offset+$scope.max;
    	$scope.showRequests()  	
    };
    $scope.previous = function(){
    	$scope.offset =$scope.offset-$scope.max;
    	$scope.showRequests()
    };
    $scope.tasks=function(){
    	$http({
   		 method:'GET',
   			url:"/tms/qemProjectAdmin/showTasks"
   	 	}).success(function(data) {
   		 if(data!=null){			 
   			 $scope.taskList= data.taskList;
   			$scope.pager.total = data.total;
   			$scope.taskCounts=data.taskCounts;
//   			 console.info("",$scope.taskList);
   		 }	
   		$location.url('/taskList') 
   	 });
    }
    $scope.taskDetail=function(id){
    	$http({
      		 method:'GET',
      			url:"/tms/qemProjectAdmin/taskDetail",
      			params:{
      				id:id
      			}
      	 	}).success(function(data) {
      		 if(data!=null){			 
      			 $scope.task= data.task;
      			 $scope.fileList= data.fileList;
//      			 console.info("",$scope.fileList);
      		 }	
      		$location.url('/taskDetail') 
      	 });
    }
    $scope.getFileName = function(src){
		return src.slice(src.lastIndexOf('/') + 1);
	}
    $scope.confirmTask= function(id){
    	$http({
      		 method:'GET',
      			url:"/tms/qemProjectAdmin/confirmTask",
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
    $scope.doselectAllTask= function(){		
//    	console.log($scope.taskList);
		angular.forEach($scope.taskList,function(data){				
			if(data.runStatus==$scope.action.type) 
				data.selected=$scope.action.selectAll;					
		});
	}
    $scope.confirmAll=function(){
    	var options=[]
		angular.forEach($scope.taskList,function(data){
			if(data.selected) options.push(data.id);
		});
//    	console.log(options);
    	$scope.action.selectALl=false
		$http({
			method:'POST',
			url:"/tms/QemProjectAdmin/confirmAll",
			data:JSON.stringify({ids:options}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $scope.taskList= data.taskList;
	   			$scope.pager.total = data.total;
	   			$scope.taskCounts=data.taskCounts;
		  });
    }
    $scope.requestList=function(isCurrent,status){
    	$http({
     		 method:'GET',
     			url:"/tms/qemProjectAdmin/requestListAll"
     	 	}).success(function(data) {
     	 		$scope.trial={}
     	 		$scope.trial.status=status;
     	 		if(isCurrent){
     	 			$scope.trial.isCurrent=true;
     	 			$scope.trial.bn=data.bn;
     	 		}
     	 		$scope.requests = data.requestList;
     	 		$location.url("/requestList")
     	 	});
    }
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
			if($scope.trial.status==1)
				return result && (item.status<4);
			else if($scope.trial.status==2)
				return result && (item.status==4 || item.status==6);
			else return result && (item.status==5);
		}
		return result;
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
			angular.forEach($scope.requests,function(data){
				if(data.selected) options.push(data.id);
			});
	    	if(options.length>0){
	    		$http({
					method:'POST',
					url:"/tms/QemProjectAdmin/applyGroup",
					data:JSON.stringify({ids:options,expertGroup:$scope.selectedExperts.ids}),
					headers:{ 'Content-Type': 'application/json' } 
				  }).success(function(data) {
					  $scope.requests=data.requests;
					  $scope.showExperts();
						 $scope.rules.value2=3;
						 $scope.rules.value3=3;
				  });
	    	}
		 }
    }
    $scope.showTaskInfo = function(item){
//    	console.log(item);
    	if(item.id!=item_id) $scope.review={};
    	item_id=item.id;
    	$scope.review.projectId=item.id;	
    	$http({
    		 method:'GET',
    			url:"/tms/qemProjectAdmin/getTaskInfo",
    			params:{
    				projectId:item.id
      			}
    	 	}).success(function(data) {
    	 		if(data.task!=null){
    	 			item.sn=data.task.sn;
    	 			item.budget0 = data.task.fundingProvince;
    	 			item.budget1 = data.task.fundingUniversity;
    	 			item.budget2 = data.task.fundingCollege;
    	 		}
    	 	});
    }
	}]);