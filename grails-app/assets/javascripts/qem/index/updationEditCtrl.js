qemApp.controller('updationEditCtrl',['$scope','$http','$location','$filter','aboutUpdate','FileUploader',function($scope,$http,$location,$filter,aboutUpdate,FileUploader){ //任务列表
	$scope.updateTypes = aboutUpdate.updateTypes;
	var items=$scope.updateView.updateTypes.split(";");
	$scope.typesText ="";
	angular.forEach(items,function(item){
		if(item){
			$filter('filter')($scope.updateTypes,{'id':item})[0].selected=true;
//			console.log($scope.updateTypes);
		}
			
	})
	$scope.members=[];
	if($scope.taskView.memberstr!=null){
		 var items=$scope.taskView.memberstr.split(";");				 
		 angular.forEach(items,function(item){
			 if(item!=null && item!="") {
				 var member ={'name':item};
				 $scope.members.push(member);
			 }
		 });		 
	 }
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
	$scope.getFileName = function(item){
		return item.slice(item.lastIndexOf('___') + 3);
	}
	 //上传附件
    var uploader = $scope.uploader = new FileUploader({
        url: '/tms/qemTaskUpdate/uploadFiles'
    });
    uploader.onBeforeUploadItem = function(item) {
        formData = [{
            taskId:  $scope.updateView.taskId,
            updateId: $scope.updateView.id,
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
//    	console.log($scope.updateView);
   	 $http({
  			method:'GET',
  			url:"/tms/qemTaskUpdate/delAttach",
  			params:{
  				filename: item.file.name,
  				updateId: $scope.updateView.id,
  				taskId:  $scope.updateView.taskId,
               isDeclaration: '申报书'
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
    $scope.washMembers = function(){
   	 var options=[];
   	 angular.forEach($scope.members,function(item){
   		 if(item.name && item.name!='')	 {
   			 options.push(item);
   		 }
			  });
   	 $scope.members=options;
    }
    $scope.commit=function(isValid){
	   	 $scope.commitAction=true;
	   	 if(isValid){
	   		 $scope.updateView.updateTypes="";
	   		 //处理变更内容选择项
	   		 angular.forEach($scope.updateTypes,function(item){
	   			 if(item.selected)
	   				 $scope.updateView.updateTypes+=item.id+";";
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
	   			 $scope.updateView.members=memberstr;	    			 
	   		 }
//	   		 $scope.getUpdateQueue(uploader.queue);
//	   		 console.log($scope.updateView);
	   		 $http({
						method:'POST',
						url:"/tms/qemTaskUpdate/editCommit",
						data:JSON.stringify($scope.updateView),
						headers:{ 'Content-Type': 'application/json' } 
					  }).success(function(data) {
						  $location.url('/updateView');
						}).error(function(data){
						});
	   	 }
	   }
}]);