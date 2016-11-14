qemApp.controller('myDetailCtrl',['$scope','$http','FileUploader','$location','bnudisciplines',function($scope,$http,FileUploader,$location,bnudisciplines){ //项目申请
	$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
	$scope.qemTypes={}
	$scope.departments={}
	$scope.project={}
	$scope.projectList={}
//	$scope.project.projectLevel='1';
	$scope.titles=['教授', '副教授', '讲师', '助教', '其他正高级', '其他副高级', '其他中级', '其他初级', '未评级'];
	$scope.degrees=['大专','本科','硕士','博士'];
	$scope.disciplines=bnudisciplines;
//	$scope.project.currentTitle='教授';
//	$scope.project.currentDegree='博士'
	$scope.showAble = false;
	
	
	$scope.showDetail = function(id){
		$http({
			 method:'GET',
				url:"/tms/qem/projectDetail",
				params:{
					id:id
				}
		 }).success(function(data) {
			 if(data!=null){
				 if(data.project!=null){
					 $scope.project=data.project;	
					 console.info($scope.project);
					 $scope.showAble = true;
				 }				
				 $scope.fileList = data.fileList;				 
			 }			 
		 }).error(function(data){
			 $scope.showAble = false;
		 });
	}

	$scope.getFileName = function(src){
		return src.slice(src.lastIndexOf('/') + 1);
	}
	
	$scope.showDetail();	
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
    $scope.progress=function(){
    	if($scope.project==null || !$scope.project.commit) return '工程质量项目申请';
    	if($scope.project.taskStatus>0){
    		var STATUS = {
           			"1": "任务书已上传！等待管理员确认！",
        			"10": "在研",
        			
        			"20": "结题",
	    		};
    		return "当前进度："+STATUS[$scope.project.taskStatus]
    	}
    	if($scope.project.status>0){
    		var STATUS = {
    				"0": "未评审",
        			"1": "已指定专家",
        			"2": "专家已评审",
        			"3": "已上会",
        			"4": "同意立项，请提交任务书",
        			"5": "不予立项",
        			"6": "立项需修改"
	    		};
    		return "当前进度："+STATUS[$scope.project.status]
    	}
    	if($scope.project.commit){
    		var COLLEGESTATUS = {
        			"0": "学院未审",
        			"1": "学院审核通过，等待学校专家评审",
        			"2": "学院审核不通过"    			
        		};
        	return "当前进度："+COLLEGESTATUS[$scope.project.collegeStatus];
    	}
    }
    $scope.addTask=function(){
    	$location.url("/qemTask");
    }
    $scope.createProject=function(){
    	$location.url("/qemRequest");
    }
    $scope.createEdit=function(){
    	if(!$scope.project.userId) return "新建项目";
    	else return "编辑项目";
    }
    $scope.selectItem1=function(index){
//    	alert("here");
    	$scope.attSelected=index;
    }
	
}]);