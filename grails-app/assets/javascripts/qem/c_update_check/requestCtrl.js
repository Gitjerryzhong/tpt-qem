app.controller('requestCtrl',['$rootScope','$scope','$http','$location','FileUploader','aboutLeaders','aboutUpdate',function($rootScope,$scope,$http,$location,FileUploader,aboutLeaders,aboutUpdate){ 
		$scope.offset =0;
		$scope.max=10;
		var index=1;
		$scope.commitAction=false;
		$scope.audits={}
		$scope.trial={}
		$scope.updateForm={}
		$scope.projectLevels=aboutLeaders.projectLevels;
		$scope.titles=aboutLeaders.titles;
		$scope.positions=aboutLeaders.positions;
		$scope.degrees=aboutLeaders.degrees;
		$scope.updateTypes=aboutUpdate.updateTypes;
	    $scope.disabled_prev =function(){
	    	if($scope.offset<$scope.max){
	    		return 'disabled';
	    	}else return '';
	    }
	    $scope.disabled_next =function(){
	    	if($scope.pager.total==null ){return 'disabled';}
	    	else if($scope.max> $scope.taskList.length){
	    		return 'disabled';
	    	}else return '';
	    }
	    	   

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
		
		
		//变更申请表
	    $scope.updateRequest= function(id){	
	    	$scope.task={}
	    	$scope.updateSuccess=false;					//打开详情页面时先初始化
	    	$http({
	      		 method:'GET',
	      			url:"/tms/qemCollegeUpdate/updateForm",
	      			params:{
	      				id:id
	      			}
	      	 	}).success(function(data) {
	      		 if(data!=null){
	      			 $scope.task= data.task;
		      		 $scope.qemTypes = data.qemTypes;
		      		 $scope.fileList= data.fileList;
		      		 $scope.teachers=data.teachers;
	      			 console.log($scope.task);
	      		 }	
	      		$location.url('/updateRequest') 
	      	 });
		}
	  
	    //变更申请表详情
	    $scope.updateDetail=function(id){
	    	$http({
	      		 method:'GET',
	      			url:"/tms/qemCollegeUpdate/updateDetail",
	      			params:{
	      				id:id
	      			}
	      	 	}).success(function(data) {
	      		 if(data!=null){
	      			$scope.form=data.form;
	      			$scope.form.teacherName=data.teacherName;
	      			$scope.form.type=data.type;
	      			$scope.form.commitDate=data.commitDate;
	      			$scope.task= data.task;
	      			$scope.task.origType=data.origType;
	      			$scope.task.origTeacherName = data.origTeacherName;
	      			$scope.fileList= data.fileList;
	      		 }	
	      		$location.url('/updateDetail') 
	      	 });
	    }
	   
	    //提交申请
	    $scope.commit=function(isInvalid){
//	    	console.log(isInvalid)
	    	if( isInvalid || $scope.updateForm.userName==null || $scope.updateForm.userName=='无此教师'){
	    		$scope.commitAction=true;
	    	}else{
	    		$scope.updateForm.taskId=$scope.taskView.taskId;
	    		 $scope.updateForm.sn=$scope.taskView.sn;
	    		 $scope.updateForm.updateTypes="1;";
	    		$http({
					method:'POST',
					url:"/tms/qemCollegeUpdate/updateCommit",
					data:JSON.stringify($scope.updateForm),
					headers:{ 'Content-Type': 'application/json' } 
				  }).success(function(data) {
					  alert("OK");
//					  $scope.taskCounts=data.taskCounts;
					  $scope.tabs[2].active=true;
					  $scope.go("tab3");
					}).error(function(data){
						$scope.commitAction=true;
						if(data.userName)
							$scope.updateForm.userName="无此教师";
						item1.open=true;
						item2.open=true;
					});
	    	}
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
	             taskId:  $scope.taskView.taskId,
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
	    		 fileItem.upload();
	    	 }
	     };
	     $scope.remove = function(item){
	    	 $http({
	   			method:'GET',
	   			url:"/tms/qemCollegeUpdate/delAttach",
	   			params:{
	   				filename: item.file.name,
	   				taskId:  $scope.taskView.taskId,
	                isDeclaration: '申报书'
	   			}
	   		  }).success(function(data) {
	   			    	item.remove();
	   			}).error(function(data){
	   				alert("无法撤销！")				
	   			});
	     }
	}]);