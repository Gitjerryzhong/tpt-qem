adminApp.controller('AttentionCtrl',['$rootScope','$scope','$http','$filter','$location','FileUploader',function($rootScope,$scope,$http,$filter,$location,FileUploader){ //通知管理
	$scope.attentions={};
	var fileList=$scope.fileList=[];
	$scope.newAttention = function(){
    	$scope.attention={}
		$scope.editAble=true;
    	$scope.showDetail=false;
    }
	$scope.saveAttention = function(){
		$scope.editAble=false;
		$scope.showDetail=false;
		$http({
			method:'POST',
			url:"/tms/qemAdmin/saveAttention",
			data:JSON.stringify($scope.attention),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $scope.attentions = data.attentions;
			})
	}
	$scope.goback = function(){
		$scope.editAble=false;
    	$scope.showDetail=false;
	}
	$scope.showAttention = function(id){ //显示通知
    	$scope.attention={}
    	$http({
			 method:'GET',
				url:"/tms/qemAdmin/showAttention",
				params:{
					id:id
				}
		 }).success(function(data) {
			 if(data!=null && data.attention!=null){
				 $scope.attention = data.attention;
				 $scope.fileList=data.fileList;
				 $scope.showDetail=true;
			 }else {				
				 $scope.attention={}	
			 }
		 });
    };
    $scope.edit = function(item){
    	$scope.attention={}
    	$scope.attention.id = item.id;
    	$scope.attention.title = item.title;
    	$scope.attention.content = item.content;
    	$scope.attention.publishDate = item.publishDate;
    	console.log($scope.attention);
    	$scope.editAble=true;
    }
//    $scope.showNotice();
    $scope.attentionList = function(){
    	$scope.editAble=false;
    	$scope.showDetail=false;
    	$http({
			 method:'GET',
				url:"/tms/qemAdmin/attentionList"
		 }).success(function(data) {
			 if(data!=null && data.attentions!=null){
				 $scope.attentions=data.attentions;	
			 }else {				
				 $scope.attentions={}
			 }
		 });
    }
    $scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
    var uploader = $scope.uploader = new FileUploader({
        url: 'uploadAttentionPack',
    });
    uploader.onBeforeUploadItem = function(item) {
    	var dir;
    	if($scope.attention.publishDate) {
    		var publishDate = new Date($scope.attention.publishDate);
    		dir=$filter('date')(publishDate,'yyyyMMdd');
    	}    		
    	else dir=$filter('date')(new Date(),'yyyyMMdd')
        formData = [{
            dir: dir
        }];
        Array.prototype.push.apply(item.formData, formData);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        $scope.fileList.push(response.filename);        
    };
    $scope.attentionList();
}]);