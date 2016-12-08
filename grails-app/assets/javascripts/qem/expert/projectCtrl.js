expertApp.controller('defaultCtrl',['$rootScope','$scope','$http','$location','$filter','$modal',function($rootScope,$scope,$http,$location,$filter,$modal){ 
		
		$scope.offset =0;
		$scope.max=10;
		var index=1;
		$scope.status=0;
		$scope.requests={}
		$scope.review={}
		$scope.pager={}
		$scope.score=[]
		$scope.order=-1;
		$scope.projectName="";
		$scope.attention={};
		$scope.fileList=[];
			
		$scope.showRequests = function(){ 
	    	$http({
				 method:'GET',
					url:"/tms/QemExpertCheck/projectsForCheck",
					params:{
						type: $scope.status
					}
			 }).success(function(data) {				 
				 if(data!=null && data.requests!=null){		
					 $scope.requests=data.requests;	
					 $scope.pager = data.pager;	
				 }
			 });  
	    	 
	    };

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
	    
//	    $scope.actionText = function(action){
//	    	var ACTIONS = {
//	    			"10": "提交申请",
//	    			"11": "撤销申请",
//	    			"12": "修改申请",
//	    			"20": "审核通过",
//	    			"21": "审核不通过",
//	    			"30": "论文审批通过",
//	    			"31": "论文审批不通过",
//	    			"40": "关闭申请",
//	    			"41": "回收申请",
//	    			"51": "论文上传",
//	    		};
//	    	return ACTIONS[action];
//	    }

	    $scope.levelText = function(level){
	    	var LEVEL = {
	    			"1": "校级",
	    			"2": "省级",
	    			"3": "国家级"	    		
	    		};
	    	return LEVEL[level];
	    }
	    $scope.resultText = function(result){
	    	var RESULT = {
	    			"0": "通过",
	    			"1": "不通过",
	    			"2": "弃权"	    		
	    		};
	    	return RESULT[result];
	    }
//	    排序
	    $scope.orderBy=function(col){
	    	$scope.requests=$filter('sort')($scope.requests,col,$scope.order);
	    	$scope.order=-$scope.order;
//	    	if($scope.order==col)
//				$scope.order = "-"+col;
//			else 
//				$scope.order = col;
	    }
//	    获取注意事项
	    $scope.showAttention = function(){
	    	$http({
				 method:'GET',
					url:"/tms/QemExpertCheck/showAttention"
			 }).success(function(data) {				 
				 if(data!=null){		
					 $scope.attention = data.attention;
					 $scope.fileList=data.fileList;
					 $scope.pager = data.pager;	
				 }
			 });  
	    }
	    $scope.showAttention();
//	    $scope.showRequests();  
	    $location.url('/attention');
	    $scope.goTrial = function(type){
	    	$scope.status=type;
	    	$scope.showRequests();
	    	$location.url('/default');
//	    	switch(type){
//	    	case 0: $location.url('/default');
//	    			break;
//	    	case 1: $scope.trial.majorTypeChecked=false;
//					$scope.trial.levelChecked=false;
//					$scope.trial.majorType="文学";
//					$scope.trial.level="1";
//					$scope.trial.groupId=null
//					$scope.trial.groupChecked=true;
//	    			$location.url('/default');
//					break;
//	    	}
	    	
	    	
	    }

		
		$scope.save = function(){ 
			var scorelist="";
			var totalscore=0;	
			angular.forEach($scope.score,function(data){
				scorelist+=data+";"
				totalscore+=data;
			});
			
			$scope.review.scoreList=scorelist;
			$scope.review.totalScore=totalscore
			console.info("review",$scope.review);
			$http({
				method:'POST',
				url:"/tms/QemExpertCheck/reviewSave",
				data:JSON.stringify($scope.review),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
//				  console.info("review",data.expertReview);
				  $scope.review=data.expertReview;
				  if(data!=null && data.expertReview){					  
					  if(data.expertReview.commit){
						  $scope.showRequests();  
						  $location.url('/default');
					  }else{
						  alert("已保存！");
					  }						  
				  }
				}).error(function(data){
					alert("保存失败！");
				});
		};
		$scope.commit=function(){
			var r=confirm("确定提交评审结果吗？");
			if(r){
				$scope.review.commit=true;
				$scope.save();
				$scope.review={};
				$scope.score=[];
			}			
		}
		$scope.action=function(){
			return $scope.status==0?"操作":"我的意见";
		}
		$scope.details=function(item){
			$scope.projectName=item.projectName;
//			console.log(item);
			$http({
				 method:'GET',
					url:"/tms/QemExpertCheck/getReview"	,
					params:{
						id : item.id
					}
			 }).success(function(data) {
				 if(data!=null && data.expertReview!=null ){
					 $scope.review=data.expertReview;
					 $scope.collegeAudit = data.collegeAudit;
					 if($scope.review.scoreList){
						 var tempscore=$scope.review.scoreList.split(";");
						 angular.forEach(tempscore,function(data){
							 	if(data!="")
							 		$scope.score.push(parseInt(data));
							});
						 console.info("review",$scope.review) 
					 }
					 
				 }else{
					 $scope.review={};
					 $scope.collegeAudit = data.collegeAudit;
				 }
				 $scope.review.projectId=item.id;
			 }); 
			$location.url('/reviewed');
		}
		$scope.goTrialTask=function(type){
			$scope.status =type
			$http({
				 method:'GET',
					url:"/tms/QemExpertCheck/taskForCheck",
					params:{
						type: type
					}
			 }).success(function(data) {
				 if(data!=null && data.requests!=null){		
					 $scope.requests=data.requests;	
					 $scope.pager = data.pager;	
				 }
			 }); 
	    	$location.url('/taskview');
			
		}

		$scope.expertViewByStage=function(item){	
			$http({
				 method:'GET',
					url:"/tms/QemExpertCheck/getReviewByStage"	,
					params:{
						id : item.stageId
					}
			 }).success(function(data) {
				 if(data!=null && data.expertReview!=null ){
					 $scope.review=data.expertReview;
//					 if($scope.review.scoreList){
//						 var tempscore=$scope.review.scoreList.split(";");
//						 angular.forEach(tempscore,function(data){
//							 	if(data!="")
//							 		$scope.score.push(parseInt(data));
//							});
//						 console.info("review",$scope.review)
//					 }
					 
				 }else{
					 $scope.review={};
					 $scope.collegeAudit = data.collegeAudit;
				 }
				 $scope.projectName = item.projectName;
				 $scope.review.projectId=item.id;
				 $scope.review.stageId=item.stageId;
				 console.log($scope.review);
			 }); 
			$location.url('/stageReviewed');
		}
		$scope.saveTask = function(){ 
			var scorelist="";
			var totalscore=0;	
			angular.forEach($scope.score,function(data){
				scorelist+=data+";"
				totalscore+=data;
			});
			
			$scope.review.scoreList=scorelist;
			$scope.review.totalScore=totalscore
			console.info("review",$scope.review);
			$http({
				method:'POST',
				url:"/tms/QemExpertCheck/reviewTaskSave",
				data:JSON.stringify($scope.review),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
				  console.info("review",data.expertReview);
				  if(data!=null && data.expertReview){					  
					  if(data.expertReview.commit){
						  $scope.goTrialTask(0);  
						  $location.url('/taskview');
					  }						  
				  }
				}).error(function(data){
					
				});
		};
		$scope.commitTask=function(){
			$scope.review.commit=true;
			$scope.saveTask();
			$scope.review={};
			$scope.score=[];
		}
		$scope.related = function(item){
			var modalInstance=$modal.open({
	            templateUrl : 'qem-related.html', 
	            controller : 'relatedTaskCtrl',
	            resolve : {
	            	item : function(){
	                    return item;
	                }
	            }
	        }) 
		}
	}]);