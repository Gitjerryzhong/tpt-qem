app.controller('editCtrl',['$rootScope','$scope','$http','$location','FileUploader','aboutLeaders','aboutUpdate',function($rootScope,$scope,$http,$location,FileUploader,aboutLeaders,aboutUpdate){ 
	$scope.trial={};
	$scope.projectLevels=aboutLeaders.projectLevels;
	$scope.titles=aboutLeaders.titles;
	$scope.positions=aboutLeaders.positions;
	$scope.degrees=aboutLeaders.degrees;
	$scope.updateTypes=aboutUpdate.updateTypes;
	var items=$scope.updateView.updateTypes.split(";");
	$scope.typesText ="";
	angular.forEach(items,function(item){
		if(item){
			$scope.typesText +=aboutUpdate.updateTypeText[item]+"；"
		}
			
	})
//	处理附件列表
	$scope.uploadQueue=[]
		  angular.forEach($scope.fileList,function(item){
			  var filename = item.slice(item.lastIndexOf('___') + 3);
			  var dir = item.slice(0,item.lastIndexOf('___'));
			  var file={'file':{'name':filename},'formData':[{'isDeclaration':dir}]};
//			  console.log(file);
			  $scope.uploadQueue.push(file);
		  })
		  
	$scope.hasUpdateType = function(type){
		return $scope.updateView.updateTypes.indexOf(type+";") !=-1;
	}
	    //异步查找老师
	    $scope.findTeacher=function(teacherName){
	    	$http({
       		 method:'GET',
       			url:"/tms/qemCollegeUpdate/getTeachers",
       			params:{
       				teacherName:teacherName
       			}
       	 	}).success(function(data) {
       	 		$scope.teachers=data.teachers;
       	 	}); 
	    }
	  //上传附件
	     var uploader = $scope.uploader = new FileUploader({
	         url: '/tms/qemCollegeUpdate/uploadFiles'
	     });
	     uploader.onBeforeUploadItem = function(item) {
	         formData = [{
	             taskId:  $scope.updateView.taskId,
	             isDeclaration: '申报书'
	         }];
	         Array.prototype.push.apply(item.formData, formData);
	     };
	     uploader.onSuccessItem = function(fileItem, response, status, headers) {
	         $scope.fileList1= response.fileList;        
	     };
	     uploader.onAfterAddingFile = function(fileItem) {
	    	 if(fileItem.file.size/1024/1024>100) alert("附件不能超过100M！");
	    	 else	{
	    		 $scope.uploadQueue.push(fileItem);
	    		 fileItem.upload();
	    	 }
	     };
	     $scope.remove = function(item){
	    	 $http({
	   			method:'GET',
	   			url:"/tms/qemCollegeUpdate/delAttach",
	   			params:{
	   				filename: item.file.name,
	   				taskId:  $scope.updateView.taskId,
	                isDeclaration: '申报书'
	   			}
	   		  }).success(function(data) {
	   			if(item.size)
   			    	item.remove();
   			    else {
   			    	var queue=[]
   	  			  	angular.forEach($scope.uploadQueue,function(file){
	   	  				 if(file !=item) queue.push(file);
   	  			  	})
   	  			  	$scope.uploadQueue=queue;
   			    }
	   			}).error(function(data){
	   				alert("无法撤销！")				
	   			});
	     }
	   //提交申请
	$scope.commit=function(isInvalid){
    	if( isInvalid || $scope.updateView.teacherName==null ){
    		$scope.commitAction=true;
    	}else{
    		$http({
				method:'POST',
				url:"/tms/qemCollegeUpdate/editCommit",
				data:JSON.stringify($scope.updateView),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
				  alert("OK");
				  $scope.tabs[2].active=true;
				  $scope.go("tab3");
				}).error(function(data){
					$scope.commitAction=true;
				});
    	}
    }
//	初始化教师工号选择框
     $scope.findTeacher($scope.updateView.teacherName);
	}]);