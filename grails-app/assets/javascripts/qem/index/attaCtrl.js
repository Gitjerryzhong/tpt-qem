qemApp.controller('AttaCtrl',['$rootScope','$scope','$http','$modalInstance','FileUploader','me',function($rootScope,$scope,$http,$modalInstance,FileUploader,me){ //依赖于modalInstance
	
	var uploader = $scope.uploader = new FileUploader({
        url: 'qem/uploadFiles'
    });

    // FILTERS

    uploader.filters.push({
    	name: 'wordFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
        	 var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
             return '|exe|com|bat|'.indexOf(type) == -1 ;
        }
    });
	uploader.onBeforeUploadItem = function(item) {
        formData = [{
            projectId:  me           
        }];
        Array.prototype.push.apply(item.formData, formData);
    };
    
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
    	$rootScope.fileList=response.fileList;
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