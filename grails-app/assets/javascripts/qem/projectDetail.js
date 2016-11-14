//= require common/mine-constant
var qemApp = angular.module('qemApp', ['ui.router','ui.bootstrap','angularFileUpload','mine.constant']);
qemApp.controller('myDetailCtrl',['$scope','$http','FileUploader','$location','$filter','$window','config','bnudisciplines',function($scope,$http,FileUploader,$location,$filter,$window,config,bnudisciplines){ //项目申请
	$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
	$scope.qemTypes={}
	$scope.departments={}
	$scope.project={}
	$scope.projectList={}
//	$scope.project.projectLevel='1';
	$scope.titles=['教授', '副教授', '讲师', '助教', '其他正高级', '其他副高级', '其他中级', '其他初级', '未评级'];
	$scope.degrees=['大专','本科','硕士','博士'];
	$scope.positions=['校长', '副校长', '处长', '副处长', '院长/部长/主任', '副院长/副部长/副主任', '系主任/专业负责人', '院长助理/部长助理', '实验室负责人','其他','无'];
	$scope.disciplines=bnudisciplines;
	$scope.discipline={};
	$scope.showAble = true;
	$scope.projectId=0;
	$scope.editAble=false;
	$scope.audits={}
	$scope.project=config.project;	
	 $scope.notice = config.notice;
	 $scope.fileList=config.fileList;
	 $scope.discipline=$filter('filter')($scope.disciplines,{'title':$scope.project.discipline})[0];
	
	 //不再用2016-5-11
	 $scope.showDetail = function(id){
//		console.log(id);
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
					 console.info("project",$scope.project);
					 $scope.discipline=$filter('filter')($scope.disciplines,{'title':$scope.project.discipline})[0];
//					 console.info("discipline",$scope.discipline);
					 $scope.showAble = true;
				 }				
				 $scope.fileList = data.fileList;
				 $scope.audits = data.audits;
			 }			 
		 }).error(function(data){
			 $scope.showAble = false;
		 });
	}

	 $scope.checkEditAble=function(){
		 var end= new Date($scope.notice.end)
//		 console.log(end);
		 return end < new Date() ||( $scope.project.collegeStatus!=2 && $scope.project.commit)
	 }
	$scope.getFileName = function(src){
		return src.slice(src.lastIndexOf('/') + 1);
	}
	
//	$scope.showDetail();	
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
    $scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
    
    $scope.actionText = function(action){
    	var ACTIONS = {
    			"10": "提交申请",
    			"11": "撤回申请",
    			"12": "修改申请",
    			"20": "审核通过",
    			"21": "审核不通过",
    			"22": "专家评审通过",
    			"23": "专家评审不通过",
    			"24": "审核撤回",
    			"30": "会评通过",
    			"31": "会评不通过",
    			"40": "关闭申请",
    			"41": "回收申请",
    			"50": "提交任务书",
    			"51": "撤回任务书",
    			"52": "任务书确认",
    		};
    	return ACTIONS[action];
    }
    $scope.progress=function(){
    	if($scope.project==null || !$scope.project.commit) return '工程质量项目申请';
    	if($scope.project.taskStatus>0){
    		var STATUS = {
           			"1": "任务书已上传！等待管理员确认！",
        			"10": "在研",
        			"20": "结题",
        			
        			"32": "终止"
	    		};
    		return "当前进度: "+STATUS[$scope.project.taskStatus]
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
    		return "当前进度: "+STATUS[$scope.project.status]
    	}
    	if($scope.project.commit){
    		var COLLEGESTATUS = {
        			"0": "学院未审",
        			"1": "学院审核通过，等待学校专家评审",
        			"2": "学院审核不通过"    			
        		};
        	return "当前进度: "+COLLEGESTATUS[$scope.project.collegeStatus];
    	}
    }
    $scope.cancel=function(){
    	$http({
			method:'GET',
			url:"/tms/qem/cancel",
			params:{
				projectId: $scope.project.id
			}
		  }).success(function(data) {
			  $scope.project.commit = false;
			}).error(function(data){
				alert("无法撤销申请！")				
			});
    }
    $scope.apply=function(){
//    	如果用户没有编辑直接提交
    	if(!$scope.editAble){
    		$http({
    			method:'POST',
    			url:"/tms/qem/apply",
    			params:{
    				projectId: $scope.project.id
    			}
    		  }).success(function(data) {
    			  $scope.project.commit = true;
    			  $scope.project.collegeStatus = 0;
    			}).error(function(data){
    				alert("无法提交申请！")				
    			});
    	}else{
    		$http({
    			method:'POST',
    			url:"/tms/qem/apply",
    			data:JSON.stringify($scope.project),
    			headers:{ 'Content-Type': 'application/json' } 
    		  }).success(function(data) {
    			  $scope.project.commit = true;
    			  $scope.project.collegeStatus = 0;
    			}).error(function(data){
    				alert("无法提交申请！")				
    			});
    	}
    	
    }
    $scope.edit=function(){
    	$scope.editAble=true;    	
    	$http({
			 method:'GET',
				url:"/tms/qem/editProject",
				params:{
					projectId: $scope.project.id
				}
		 }).success(function(data) {
			 if(data!=null){
				 if(data.project!=null){
					 $scope.project={}
					 $scope.project=data.project;
					 $scope.projectId = $scope.project.id;
				 }
//				 $scope.project.qemTypeId=4;
				 console.log(data.project);
				 $scope.departments= data.departments;
//				 console.log(data.departments);
				 $scope.qemTypes= data.qemTypes;
//				 console.log(data.qemTypes);
				 $scope.majors = data.majors;	
//				 console.log(data.majors);
//				 $scope.project.qemTypeId = $scope.qemTypes[0].id;
				 if($scope.project.departmentId==null)
					 $scope.project.departmentId=data.myDepartmentId;					
			 }			 
		 });
    }

	$scope.save = function(){  	
//		console.info($scope.project);
		var project=$scope.project;
	    	$http({
				method:'POST',
				url:"/tms/qem/saveProject",
				data:JSON.stringify($scope.project),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
				  $scope.project.id = data.projectId;
				}).error(function(data){
					alert("信息保存失败！")
				});
	      
	    };
	 //上传附件
    var uploader = $scope.uploader = new FileUploader({
        url: '/tms/qem/uploadFiles'        
    });

    $scope.upload = function(){    	
    	uploader.uploadAll();
    }
    uploader.onBeforeUploadItem = function(item) {
        formData = [{
            projectId:  $scope.project.id,
            isDeclaration: '申报书'
        }];
        Array.prototype.push.apply(item.formData, formData);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        $scope.fileList= response.fileList;        
    };
    $scope.selectItem1=function(index){
    	$scope.attSelected=index;
    }
    //删除附件
    $scope.remove=function(filename){
    	$http({
			 method:'GET',
				url:"/tms/qem/removeAtt",
				params:{
					filename:filename,
					projectId: $scope.project.id
				}
		 }).success(function(data) {
			 if(data!=null){
				 $scope.fileList=data.fileList;
			 }			 
		 });
    }
    $scope.goBack = function(){
    	$window.location.replace("/tms/qem#/qemSearch");  	
    }
	
}]);