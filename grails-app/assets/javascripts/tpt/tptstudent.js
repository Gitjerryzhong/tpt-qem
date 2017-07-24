var tptApp = angular.module('tptApp', ['ui.router','ui.bootstrap','angularFileUpload']);
tptApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
    $stateProvider
        .state('declareInfo', {
            url: '/declareInfo',
            templateUrl: 'tpt-declareInfo.html',
            controller:['$rootScope','$scope','$http',function($rootScope,$scope,$http){
            $scope.noticeDetail=false;
            $scope.agreeShow=false;
            $scope.showNotice=function(id){
            	$scope.notice={};	
 				 $scope.inTime=true;	
	           	$http({
	      			 method:'GET',
	      				url:"/tms/tpt/showNotice",
	      				params:{id:id}
	      		 }).success(function(data) {
	      			 if(data!=null && data.notice!=null){
	      				 $scope.notice=data.notice;	
	      				 $scope.inTime=data.inTime;	
	      				 $scope.myRequest = data.myRequest;
	      				 $scope.succeed = data.succeed;
	      				 $rootScope.bn=$scope.notice.bn;
	      			 }else {				
	      				 $scope.notice={}	;
	      				 $scope.notice.content="您好！目前不是学位申请期间，请留意通知，谢谢！";
	      			 }
	      		 });
            }
            $scope.noticeList=function(){
            	$http({
       			 method:'GET',
       				url:"/tms/tpt/noticeList"
       		 }).success(function(data) {
       			 if(data!=null && data.notices!=null){
       				 $scope.notices=data.notices;	
       			 }else {				
       				 $scope.notices={}
       			 }
       		 });
            }
            $scope.noticeList();
            
        	 $scope.next= function(){
        	    	$scope.agreeShow=true;    	
        	    	$scope.inTime=false;
        	    	$scope.notice.content="    学生在申请北师大珠海分校本科毕业证与学位证之前,请务必仔细阅读本声明。\n"+
        	"    本人承诺此次申请为自愿行为，上传的所有资料（联系电话、邮件、个人照片、国外学位证书、成绩单）均真实、有效、合法，在申请期间自行查看学院公告通知，在学校规定的时间内提交相关材料。如有特殊情况中途退出，务必通知学部教务老师。如因申请材料伪造、无效或长期无法联系导致未能获得本校证书，学校不承担任何相关责任。";
        	    };
        	    
        	$scope.goDetail = function(id){
        	    	$scope.showNotice(id);
        	    	$scope.noticeDetail=true;
        	    }
            }]
        })    
    .state('firstStep', {
        url: '/firstStep',
        templateUrl: 'tpt-firstStep.html'
    })
    .state('subStep', {
        url: '/subStep',
        templateUrl: 'tpt_subStep.html'
    })
    .state('ultimate', {
        url: '/ultimate',
        templateUrl: 'tpt_finally.html'
    })
    .state('paperAudit', {
        url: '/paperAudit',
        templateUrl: 'tpt_paperAudit.html'
    })
    .state('paperAudit1', {
        url: '/paperMaster',
        templateUrl: 'tpt_paperAudit1.html'
    })
    .state('paperAudit2', {
        url: '/paperCourse',
        templateUrl: 'tpt_paperAudit2.html'
    })
    .state('paperAudit3', {
        url: '/paperUpdate',
        templateUrl: 'tpt_paperAudit3.html'
    })
    .state('myMessage', {
        url: '/myMessage',
        templateUrl: 'tpt-message.html'
    });
}]);
tptApp.controller('ReqCtrl',['$rootScope','$scope','$http','$location','$modal','FileUploader','$state','$filter',function($rootScope,$scope,$http,$location,$modal,FileUploader,$state,$filter){ //申请
	$scope.contact={}
	$scope.colleges={}
	$rootScope.imgs={}
	$scope.audits={}
	$scope.paper={}
	$scope.bn1=
	$scope.bn2=
	$scope.bn3=
	$scope.bn4=
	$scope.bn5="btn btn-default";
	$scope.saved=false;
	$scope.end = null;
	$scope.paperEnd = null;
	$scope.modifyEnding = null;
//	改变了显示流程，统一先显示历史通知清单2016-09-05
	$state.go('declareInfo');
	$scope.showStatus=function(status){
		switch(status){
		case -1:
			$scope.bn1="btn btn-danger";
			$scope.bn2=$scope.bn3=$scope.bn4=$scope.bn5="btn btn-default";
			break;
		case 0:
			$scope.bn2="btn btn-danger";
			$scope.bn1=$scope.bn3=$scope.bn4=$scope.bn5="btn btn-default";
			break;
		case 1:
		case 2:
		case 3:
			$scope.bn3="btn btn-danger";
			$scope.bn2=$scope.bn1=$scope.bn4=$scope.bn5="btn btn-default";
			break;
		case 4:
		case 5:
		case 6:
		case 7:		
			$scope.bn5="btn btn-danger";
			$scope.bn2=$scope.bn3=$scope.bn1=$scope.bn4="btn btn-default";
			break;
		}
	}
	$scope.showRequest = function(){ //显示申请单
		var status=-1;
    	$http({
			 method:'GET',
				url:"/tms/tpt/showRequest",
		 }).success(function(data) {
			 if(data!=null ){		
				 if(data.form!=null){
					 status= data.form.status;
					 console.log(status);
					 $scope.contact=data.form;
					 $scope.saved=true;
				 }
				 $scope.colleges=data.colleges;
				 console.log(data);
				 $scope.username=data.username;
				 $rootScope.imgs=data.imgSrc;
				 $scope.audits=data.audits;
				 $scope.paperFile=data.paperFile;
				 $scope.paperExchFile=data.paperExchFile;
				 if(data.end!=null) $scope.end=new Date(data.end);
				 if(data.paperEnd!=null) $scope.paperEnd=new Date(data.paperEnd);
				 if(data.paperModifyEnd!=null) $scope.modifyEnding=new Date(data.paperModifyEnd);
				 /*因为这是异步，程序不等待返回结果就会往下执行
				  * 因此必须页面调整必须放在这里才能保证*/
				 $scope.showStatus(status);
				 switch(status){ //根据状态跳转页面
					case -1:
						$location.url('/declareInfo');
						break;
					case 0:						
						 $location.url('/firstStep');
						break;
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
						 $location.url('/subStep');
						break;						
					default:
						$location.url('/declareInfo');
				}
			 }
		 });  
    	 
    };
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
    $scope.statusTitle = function(status){
    	var endText ="审核结束，系统关闭！";
    	var title =null;
    	switch(status){
    	case 1:
    	case 2:
    	case 3: if ($scope.paperEnd < new Date()) title =endText; break;
    	case 6:
    	case 7: if ($scope.modifyEnding < new Date()) {title =endText; ;break;} 
    	}
    	var TITLE = {    			
    			"1": "申请单详情",
    			"2": "初审通过",
    			"3": "初审不通过——请更正相关信息和上传材料",
    			"4": "论文已上传",
    			"5": "终审通过",
    			"6": "终审不通过", 
    			"7": "论文审核不通过——请更正相关信息和重新上传论文", 
    		};
    	var commonText = TITLE[status];
    	if(status == "2") commonText = commonText + ",请在"+$filter('date')($scope.paperEnd,'yyyy-MM-dd')+"前上传论文！预期不收！";
    	return !title? commonText:title;
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
    $scope.showWarning = function(status){
    	return status=="2" || status=="3" || status=="7";
    }
    $scope.apply = function(){
    	var status=0;
    	$http({
			 method:'GET',
				url:"/tms/tpt/apply"
		 }).success(function(data) {
			 if(data.form!=null){
				 status= data.form.status;				  
				 $scope.contact=data.form;
				 $scope.audits=data.audits;
			 }
			 $scope.colleges=data.colleges;
			 $scope.showStatus(status);
			  $location.url('/subStep');
			}).error(function(data){
				alert("请先确认已填好所有必填信息并上传所有的材料！")				
			});
    }
    $scope.getCollegeName=function(cId){
    	var collegeName="";
    	angular.forEach($scope.colleges,function(item){   
    		if(item.id==cId) collegeName= item.name;
    	})
    	return collegeName;
    }
    $scope.gofirstStep= function(){
    	var status=0;
    	$http({
			 method:'GET',
				url:"/tms/tpt/getColleges"
		 }).success(function(data) {
			 $scope.colleges=data.colleges;
			 console.log($scope.colleges);
			 $scope.username=data.username;
		 });
    	$scope.showStatus(status);
    	 $location.url('/firstStep');
    }
//    $scope.showRequest();
    //保存联系人信息
    $scope.save = function(){  	
		$scope.contact.bn=$rootScope.bn;
		$scope.saved=true;
//		alert($scope.contact.foreignCollege);
    	$http({
			method:'POST',
			url:"/tms/tpt/saveContact",
			data:JSON.stringify($scope.contact),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $scope.contact = data.tptForm;
			}).error(function(data){
				alert("联系人信息保存失败！")
				$rootScope.errors=data;
			});
      
    };
    
//	$scope.showStatus(1);
    $scope.upload = function(){
    	
		 var modalInstance=$modal.open({
	            templateUrl : 'attachment.html',  //创建文件上传视图
	            controller : 'AttaCtrl',
	            size: 'lg',
	            backdrop : "static"
	        }) 
	 }
    $scope.uploadPhoto = function(who){
    	
		 var modalInstance=$modal.open({
	            templateUrl : 'attachment.html',  //创建文件上传视图
	            controller : 'AttaCtrl',
	            backdrop : "static",
	            resolve : {
                    me : function(){
                        return who;
                    }
                }
	        }) 
	 }
    
    $scope.allowUpdate= function(){
    	if($scope.contact.status==3 && $scope.end > new Date()) return true;
    	else return false;
    }
    $scope.allowPaper= function(){
    	if($scope.contact.status==2 && $scope.paperEnd > new Date()) return true;
    	else return false;
    }
    $scope.allowPaperUpdate= function(){
    	if($scope.contact.status==7 && $scope.modifyEnding > new Date()) return true;
    	else return false;
    }
    $scope.goUpdate = function (){
    	$location.url('/firstStep');
    }
    //上传论文
    var uploader = $scope.uploader = new FileUploader({
        url: 'tpt/uploadPaper'
    });
    uploader.onCompleteAll = function() {
    	$http({
			 method:'GET',
				url:"/tms/tpt/finishPaper"
		 }).success(function(data) {
			if(data.form!=null){
				 status= data.form.status;
				 $scope.contact=data.form;
				 $scope.audits=data.audits;
			 }
			 $scope.colleges=data.colleges;
			 $scope.paperFile=data.paperFile;
			 $scope.paperExchFile=data.paperExchFile;
			 $scope.showStatus(4);
			 $location.url('/ultimate');
		 }).error(function(data){
				alert(data.error);
			});
    };
//    uploader.filters.push({
//    	name: 'wordFilter',
//        fn: function(item /*{File|FileLikeObject}*/, options) {
//            var type = item.type.slice(item.type.lastIndexOf('/') + 1);
//            return type.indexOf('word') !== -1;
//        }
//    });
//    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
//        alert("请上传Word或Pdf文档！");
//    };
    $scope.paperSubmit= function(type){    	
    	$scope.paper.type=type;
    	$http({
			method:'POST',
			url:"/tms/tpt/writePaperForm",
			data:JSON.stringify($scope.paper),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
				 uploader.uploadAll();
			}).error(function(data){
				alert(data.error);
				
			});
    }
      
}]);

tptApp.controller('AttaCtrl',['$rootScope','$scope','$http','$modalInstance','FileUploader','me',function($rootScope,$scope,$http,$modalInstance,FileUploader,me){ //依赖于modalInstance
	
	switch(me){
	case 1: 
		$scope.picName="照片";
		$scope.picLimit=['相片必须是蓝底免冠数码照片，不得采用扫描照片。','照片尺寸：不小于413×626 ','因相片不符合，要求导致学历、学位证书网上查询结果不准确，一切后果由学生本人承担。'];
		break;
	case 2:
		$scope.picName="证书";
		break;
	case 3:
	case 4:
	case 5:
		$scope.picName="成绩单";
		break;
	}
	
	$scope.showHine=false;

	var uploader = $scope.uploader = new FileUploader({
        url: 'tpt/uploadPic'+me,
    });

    // FILTERS

    uploader.filters.push({
        name: 'customFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 2;
        }
    });
   
    uploader.onAfterAddingFile = function(fileItem) {
    	if(fileItem.file.size/1024/1024 >1.5){
    		fileItem.remove();
    		$scope.picLimit=['上传文件不要超过1.5M！']
    	}else if(me==1){
        	var reader = new FileReader();        	
            reader.onload = function(event){
            	var img = new Image();
                img.onload = function () {
                	//可以在这里判断尺寸问题
                	var proportion=this.width/this.height;
                	var reference = 0.6597444;
                    if(!(this.width>=413 && this.height>=626)&& !(this.width>=626 && this.height>=413)){
                    	fileItem.remove();
                    	$scope.showHine=true;
                    }else if(Math.abs(proportion-reference)/reference>0.05){
                    	alert(Math.abs(proportion-reference)/reference);
                    	fileItem.remove();
                    	$scope.picLimit=['您的相片比例不正确，宽/高比例应在2/3左右！请剪裁成合适比例！']
                    	$scope.showHine=true;                    	
                    } else{
                    	$scope.showHine=false;
                    }           

                };
                img.src = event.target.result;
            };
            reader.readAsDataURL(fileItem._file);
            $scope.picLimit=[]
    	}
    };    
    
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
    	$rootScope.imgs=response.imgSrc
    };
    
    $scope.startUpload = function(){
        if(uploader.getNotUploadedItems().length){
        	uploader.uploadAll();
        }
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel'); // 退出
    }
}]);
