taskApp.controller('updateCtrl',['$scope','$http','$location','FileUploader',function($scope,$http,$location,FileUploader){ 
	    $scope.commitAction=false;
	    $scope.dateFormat = function(jsondate){
	    	return new Date(jsondate);
	    }
//	    console.log($scope.task);
	    getFiles =function(){
	    	$http({
		   		 method:'GET',
		   			url:"/tms/qemTaskUpdate/fileList",
		   			params:{
		   				taskId:  $scope.task.id
		   			}
		   	 	}).success(function(data) {
		   	 		$scope.uploadQueue=[]
				  angular.forEach(data.fileList,function(item){
					  var filename = item.slice(item.lastIndexOf('___') + 3);
					  var dir = item.slice(0,item.lastIndexOf('___'));
					  var file={'file':{'name':filename},'formData':[{'isDeclaration':'申报书'}]};
//					  console.log(file);
					  $scope.uploadQueue.push(file);
				  })
		   	 	});
	    }
	    getFiles();
	   
	  //上传附件
	     var uploader = $scope.uploader3 = new FileUploader({
	         url: '/tms/qemTaskUpdate/uploadFiles'
	     });
	     uploader.onBeforeUploadItem = function(item) {
	         formData = [{
	             taskId:  $scope.task.id,
	             isDeclaration: '申报书'
	         }];
	         Array.prototype.push.apply(item.formData, formData);
	     };
	     uploader.onSuccessItem = function(fileItem, response, status, headers) {
	         $scope.fileList= response.fileList;        
	     };
	     uploader.onAfterAddingFile = function(fileItem) {
	    	 if(fileItem.file.size/1024/1024>100) alert("附件不能超过100M！");
	    	 else	{
	    		 $scope.uploadQueue.push(fileItem);
	    		 fileItem.upload();
	    	 }
	     };
	     $scope.updateTypeChange = function(item){
	    	 if(!item.selected){
	    		 switch(item.id){
	    		 case 2: delete $scope.newData.expectedMid;
	    		 		delete $scope.newData.expectedEnd;
		 		 		break;
	    		 case 3: delete $scope.newData.projectName;
	    		 		break;
	    		 case 4: delete $scope.newData.projectContent;
		 		 		break;
	    		 case 6: delete $scope.newData.expectedGain;
		 		 		break;
//	    		 case 7: $scope.members=[];
//		 		 		break;
	    		 case 8: delete $scope.newData.memo;
	 		 			break;
	    		 }
	    	 }
	     }
	     $scope.commit=function(isValid){
	    	 $scope.commitAction=true;
	    	 if(isValid){
	    		 $scope.newData.taskId=$scope.task.id;
	    		 $scope.newData.sn=$scope.task.sn;
	    		 $scope.newData.projectId=$scope.task.projectId;
	    		 $scope.newData.updateTypes="";
	    		 //处理变更内容选择项
	    		 angular.forEach($scope.updateTypes,function(item){
	    			 if(item.selected)
	    				 $scope.newData.updateTypes+=item.id+";";
		    	 });
	    		 //处理参与人列表
	    		 if($scope.updateTypes[6].selected){
	    			 $scope.washMembers();
	    			 var memberstr="";
	    	    	 angular.forEach($scope.members,function(member){
	    	    		 if(member.name && member.name!='')	 {
	    	    			 memberstr+=member.name+";";
	    	    		 }
	    				  });
	    			 $scope.newData.members=memberstr;	    			 
	    		 }
	    		 $scope.getUpdateQueue(uploader.queue);
	    		 $http({
						method:'POST',
						url:"/tms/qemTaskUpdate/updateCommit",
						data:JSON.stringify($scope.newData),
						headers:{ 'Content-Type': 'application/json' } 
					  }).success(function(data) {
						  $location.url('/updateView');
						}).error(function(data){
						});
	    	 }
	     }
	     $scope.noChecked =function(){
	    	 var isSelected=false;
	    	 angular.forEach($scope.updateTypes,function(item){
	    		 isSelected = isSelected || item.selected;
	    	 });
	    	 return !isSelected;  
	     }
	     $scope.remove = function(item){
	    	 var dir=item.formData?item.formData[0].isDeclaration:null;
	    	 $http({
	   			method:'GET',
	   			url:"/tms/qemTaskUpdate/delAttach",
	   			params:{
	   				filename: item.file.name,
	   				taskId:  $scope.task.id,
	                isDeclaration: dir?dir:uploadType()
	   			}
	   		  }).success(function(data) {
	   			    if(item.size)
	   			    	item.remove();
	   			    else {
	   			    	var queue=[]
	   	  			  angular.forEach($scope.uploadQueue,function(file){
	   	  				 if(file !=item){
	   	  					 queue.push(file);
	   	  				 }
	   	  			  })
	   	  			$scope.uploadQueue=queue;
	   			    }
	   			}).error(function(data){
	   				alert("无法撤销！")				
	   			});
	     }
	}]);