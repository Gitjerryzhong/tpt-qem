checkApp.controller('TrialCtrl',['$rootScope','$scope','$http','$modal','$location','$filter',function($rootScope,$scope,$http,$modal,$location,$filter){ 
	$scope.offset =0;
	$scope.max=15;
	var index=1;
	$scope.checkStatus=1;
	$scope.requests={}
	$scope.contact={}
	$scope.imgs={}
	$scope.audits={}
	$scope.trial={}
	$rootScope.pager={}
	var vm = $scope;	
	$scope.showRequests = function(){ 
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/showRequests",
				params:{
					type : $scope.checkStatus,
					offset: $scope.offset,
					max: $scope.max
				}
		 }).success(function(data) {
			 if(data!=null ){		
				 $scope.requests=data.requests;	
//				 console.info('requests',data);
				 $rootScope.pager = data.pager;				
			 }
		 });  
    	 
    };
    $scope.disabled_prev =function(){
    	if($scope.offset<$scope.max){
    		return 'disabled';
    	}else return '';
    }
    $scope.disabled_next =function(){
    	if($rootScope.pager.total==null ){return 'disabled';}
    	else if($scope.offset+$scope.max> $rootScope.pager.total[$scope.checkStatus]){
    		return 'disabled';
    	}else return '';
    }
    $scope.disabled_p1 =function(){
    	if($scope.contact.prevId==null){
    		return 'disabled';
    	}else return '';
    }
    $scope.disabled_n1 =function(){
    	if($scope.contact.nextId==null){
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

    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/getRequestDetail",
				params:{
					form_id: itemId 
				}
		 }).success(function(data) {
			 if(data!=null ){	
				 $scope.contact=data.form;
				 $scope.audits=data.audits;
				 $scope.imgs=data.imgSrc;
				 $scope.paperFile=data.paperFile;
				 $scope.paperExchFile=data.paperExchFile;
				 if($scope.auditAble())
					 $location.url('/details');
				 else 
					 $location.url('/search');
			 }
		 });  
    }
    $scope.nextItem = function(){
    	
    }
	$scope.ok = function(act,formId,prevId,nextId){ 
//		console.info('auditSave',$scope.requests);
//		console.info('index',index);
		$scope.trial.form_id=formId
		$scope.trial.check = act;
		$scope.trial.prevId = prevId;
		$scope.trial.nextId = nextId;
		if($scope.contact.status=="4"){
			if(act=="20") $scope.trial.check="30";
			else if (act=="21") $scope.trial.check="31";
				
		}
		$http({
			method:'POST',
			url:"/tms/tptAdmin/auditSave",
			data:JSON.stringify($scope.trial),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  if(data!=null ){
				  if(data.none){
					  $scope.requests={};
//					  $rootScope.pager.total=0;
					  $location.url('/trial')
				  }else{
					 $scope.contact=data.form;
					 $scope.audits=data.audits;
					 $scope.imgs=data.imgSrc;
					 $scope.trial={};					
				  }
				  $rootScope.pager=data.pager;
				 }
			}).error(function(data){
				
			});
	};
	$scope.cancel = function(id){
		$http({
			 method:'GET',
				url:"/tms/tptAdmin/cancel",
				params:{
					formId: id 
				}
		 }).success(function(data) {
			 if(data!=null ){	
				 $scope.audits=data.audits;
				 $scope.contact.status=data.status;
			 }
		 });
	}
    $scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
    $scope.actionText = function(action){
    	var ACTIONS = {
    			"10": "提交申请",
    			"11": "撤销申请",
    			"12": "修改申请",
    			"20": "审核通过",
    			"21": "审核不通过",
    			"30": "论文审批通过",
    			"31": "论文审批不通过",
    			"32": "审核撤销",
    			"40": "关闭申请",
    			"41": "回收申请",
    			"51": "论文上传",
    			"52": "更正原论文成绩",
    		};
    	return ACTIONS[action];
    }
    $scope.statusText = function(status){
    	var STATUS = {
    			"0": "未提交",
    			"1": "申请中",
    			"2": "已通过审核照片、证书、成绩",
    			"3": "初审不通过",
    			"4": "论文审核中",
    			"5": "论文审核通过",
    			"6": "已关闭",  
    			"7": "论文审批不通过",
    		};
    	return STATUS[status];
    }
    $scope.showRequests();  
    $scope.goTrial = function(type){
    	$scope.checkStatus=type;
    	$scope.showRequests();
    	$location.url('/trial');
    }
    $scope.search = function(){
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/searchDetail",
				params:{
					studentId: $scope.studentId 
				}
		 }).success(function(data) {
			 if(data!=null ){	
				 $scope.contact=data.form;
				 $scope.contact.formId=data.form.id;
				 $scope.audits=data.audits;
				 $scope.imgs=data.imgSrc;
				 $scope.paperFile=data.paperFile;
				 $scope.paperExchFile=data.paperExchFile;
				 $location.url('/search')
			 }
		 });
    }
    $scope.search1 = function(){
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/searchName",
				params:{
					studentName: $scope.studentName
				}
		 }).success(function(data) {
			 if(data!=null ){		
				 $scope.requests=data.requests;	
//				 console.info('requests',data);
				 $rootScope.pager = data.pager;	
				 $location.url('/trial');
			 }
		 });  
    }
    $scope.downloadPhoto = function(){
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/downloadPhotoByStatus"
		 }).success(function(data) {
			 if(data!=null ){	
				 $scope.downloadUrl=data.download
				 $scope.downloadUrlEnd=data.downloadEnd
				 $location.url('/download-photo');
			 }
		 });
    }
    $scope.downloadPaper = function(){
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/downloadPaper"
		 }).success(function(data) {
			 if(data!=null ){	
				 $scope.downloadUrl=data.download
				 $location.url('/download-paper');
			 }
		 });
    }
    $scope.auditAble=function(){
    	return $scope.checkStatus==1 || $scope.checkStatus == 4;
    }
    $scope.checkSelected = function(){
    	return $filter('filter')($scope.requests,{'selected':true}).length>0;
    }
    $scope.open = function(){
    	var requestSelected=$filter('filter')($scope.requests,{'selected':true});
		 var modalInstance=$modal.open({
	            templateUrl : 'set-mentor.html',  //创建负责人资料录入视图
	            controller : 'setMentorCtrl',// 初始化模态范围
	            backdrop : "static",
//	            size : "lg",
	            resolve : {
	            	myRequest : function(){
                        return requestSelected;
                    }
                }
	        }) ;
		 modalInstance.result.then(function(mentorName){  
			 angular.forEach(requestSelected,function(data){
				 data.mentorName=mentorName;
				 data.selected=false;
			 })
        },function(){      });
	        
	 }
    $scope.updateScort=function(id){
    	var modalInstance=$modal.open({
            templateUrl : 'update-scort.html',  //创建负责人资料录入视图
            controller : 'updateScortCtrl',// 初始化模态范围
            backdrop : "static",
//            size : "lg",
            resolve : {
            	id : function(){
                    return id;
                }
            }
        }) ;
    	modalInstance.result.then(function(result){  
    		if(result)	vm.details(id);
       },function(){      });
    }
}]);