qemApp.controller('myListCtrl',['$rootScope','$scope','$http','$location',function($rootScope,$scope,$http,$location){ //我的申请单
	$scope.myProjects={}
	$scope.project={}
	$scope.createAble=true;
	$http({
		 method:'GET',
			url:"/tms/qem/showMyProjects"
	 }).success(function(data) {
		 if(data!=null){			 
			 $scope.myProjects= data.projects;	
			 $scope.createAble=data.createAble;
			 console.info($scope.myProjects);
		 }			 
	 });
	$scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
	$scope.orderBy = function(col){
		console.info(col,$scope.order);
		if($scope.order==col)
			$scope.order = "-"+col;
		else 
			$scope.order = col;		
    }
	$scope.reviewStatusText = function(status){
    	var REVIEWSTATUS = {
    			"0": "未评审",
    			"1": "已指定专家",
    			"2": "专家已评审",
    			"3": "已上会",
    			"4": "立项",
    			"5": "不予立项",
    			"6": "立项需修改"
    		};
    	return REVIEWSTATUS[status];
    }
	$scope.levelText = function(level){
    	var LEVEL = {
    			"1": "校级",
    			"2": "省级",
    			"3": "国家级"    			
    		};
    	return LEVEL[level];
    }
	$scope.collegeStatusText = function(status){
    	var COLLEGESTATUS = {
    			"0": "未审",
    			"1": "通过",
    			"2": "不通过"    			
    		};
    	return COLLEGESTATUS[status];
    }

	$scope.clearNull= function(str){
		return str=='null'?'':str;
	}	
	$scope.getStatus=function(item){
		var REVIEWSTATUS = {
    			"1": "已指定专家",
    			"2": "专家已评审",
    			"3": "已上会",
    			"4": "同意立项",
    			"5": "不予立项",
    			"6": "须修改"
    		};
    	
		if(item.reviewStatus){
			return REVIEWSTATUS[item.reviewStatus];
		}
		if(item.collegeStatus){
			switch(item.collegeStatus){
			case 1: return "学院已同意";
			case 2: return "学院退回";
			case 3: return "学校退回";
			case 6: return "学院关闭";
			}
		} 
		return item.commit?'我已提交':'我未提交';
	}
//	$scope.createProject=function(bn){
//		$rootScope.createBn=bn;
//    	$location.url("/qemRequest");
//    }
    $scope.deleteItem = function(id){
		$http({
			 method:'GET',
				url:"/tms/qem/deleteItem",
				params:{
					projectId:id
				}
		 }).success(function(data) {
			 if(data!=null){
				 $scope.myProjects= data.projects;	
				 $scope.createAble=data.createAble;
			 }			 
		 });
	} 
    $scope.remindFilter = function(item){
    	var result=true;
    	if($rootScope.isRemind){
    		result=result && (item.collegeStatus==2 || item.reviewStatus==6);
    	}
    	return result;
    }
	$scope.showAudit=function(form_id){//暂时不让看日志
//		$http({
//			 method:'GET',
//				url:"/tms/qem/getAudit",
//				params:{
//					form_id: form_id 
//				}
//		 }).success(function(data) {
//			 if(data!=null ){	
//				 $scope.audits=data.audits;
//				 console.info($scope.audits);
//			 }
//		 }); 
	}
//	$scope.project={}
//	$scope.showDetail= function(form_id){	
//		$scope.detailable=true;
//		$http({
//			 method:'GET',
//				url:"/tms/qem/getDetail",
//				params:{
//					form_id: form_id 
//				}
//		 }).success(function(data) {
//			 if(data!=null ){	
//				 $scope.project=data.form;
//				 $scope.audits=data.audits;
//				 $scope.fileList = data.fileList;
//				 $location.url('/qemSearch');
//			 }
//		 }); 
//		
//	}
}]);