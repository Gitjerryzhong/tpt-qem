qemApp.controller('projectCtrl',['$rootScope','$scope','$http','FileUploader','$location','$modal','$state','$filter','bnudisciplines',function($rootScope,$scope,$http,FileUploader,$location,$modal,$state,$filter,bnudisciplines){ //项目申请
	$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
	$scope.qemTypes={}
	$scope.departments={}
	$scope.project={}
	$scope.projectList={}
//	$scope.project.projectLevel='1';
	$scope.titles=['教授', '副教授', '讲师', '助教', '其他正高级', '其他副高级', '其他中级', '其他初级', '未评级'];
	$scope.positions=['校长', '副校长', '处长', '副处长', '院长/部长/主任', '副院长/副部长/副主任', '系主任/专业负责人', '院长助理/部长助理', '实验室负责人','其他','无'];
	$scope.degrees=['大专','本科','硕士','博士'];
	$scope.disciplines=bnudisciplines;


//	$scope.project.currentTitle='教授';
//	$scope.project.currentDegree='博士'
	$scope.creatAble = true;
	$scope.discipline={};
	$scope.commitAction=false;
	$scope.projectCreate = function(){
		$http({
			 method:'GET',
				url:"/tms/qem/getSelections"
		 }).success(function(data) {
			 if(data!=null){
				 if(data.project!=null){
					 $scope.project=data.project;
					 $scope.discipline=$filter('filter')($scope.disciplines,{'title':$scope.project.discipline})[0];
					 console.info($scope.project);
				 }
				 $scope.departments= data.departments;
				 $scope.qemTypes= data.qemTypes;
//				 $scope.majors = data.majors;				 
//				 $scope.project.qemTypeId = $scope.qemTypes[0].id;
				 if($scope.project.departmentId==null)
					 $scope.project.departmentId=data.myDepartmentId;					
				 $scope.creatAble = data.createAble;
				 console.info("id",$scope.myDepartmentId);
			 }			 
		 });
	}

	$scope.getFileName = function(src){
		return src.slice(src.lastIndexOf('/') + 1);
	}
	$scope.commit = function(){
		$scope.project.commit =true;
		$scope.project.collegeStatus =0;
		$scope.save();
//		if($scope.project.id) {
//			alert("提交成功！");
//			$state.go('qemSearch');
//		}
//		var departmentid=$scope.project.departmentId;
//		$scope.project ={}
//		$scope.project.qemTypeId = $scope.qemTypes[0].id;
//		$scope.project.departmentId=departmentid;	
	}
	$scope.projectCreate();
//	$scope.showTimeOut = function(){
//		if(!$scope.creatAble) return '目前不是项目立项申请时段！请注意通知，谢谢！';
//	}
	$scope.disableAction = function(){
		if(!$scope.fileList || $scope.project.commit) return true
		return false;
	}
	
	$scope.save = function(){  	
		$scope.project.discipline=$scope.discipline.title;
		$scope.project.noticeId=$rootScope.noticeId; //从defaultCtrl传过来的
//		console.info($scope.project);
		$scope.commitAction=true;
		var p = $scope.project;
		if(p.currentTitle && p.currentDegree && p.position && p.phoneNum && p.specailEmail && p.discipline && p.majorId && p.projectName){
	    	$http({
			method:'POST',
			url:"/tms/qem/saveProject",
			data:JSON.stringify($scope.project),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $scope.project.id = data.projectId;
				if($scope.project.commit) {
					$state.go('qemSearch');
				}  
			}).error(function(data){
				alert("信息保存失败！")
			});
		}else{
			alert("还有红色框的内容没填！请填完成再保存！")
		}
	      
    };

	 //上传附件
    var uploader = $scope.uploader = new FileUploader({
        url: 'qem/uploadFiles'        
    });
    uploader.filters.push({
    	name: 'wordFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
//            var type = item.type.slice(item.type.lastIndexOf('/') + 1);
//            return type.indexOf('word') !== -1;
        	 var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
             return '|exe|com|bat|'.indexOf(type) == -1 && this.queue.length < 2;
        }
    });
//    uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
//        alert("请上传Word文档！");
//    };
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
    //上传其他附件
    $scope.uploadAttch = function(who){
    	
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
}]);
