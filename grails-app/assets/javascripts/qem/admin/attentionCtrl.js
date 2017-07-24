adminApp.controller('AttentionCtrl',['$rootScope','$scope','$http','$filter','$location','$modal','FileUploader',function($rootScope,$scope,$http,$filter,$location,$modal,FileUploader){ //通知管理
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
		var experList="";
		angular.forEach($scope.selectedExperts,function(data){
			if(data.selected) experList+=data.id+";";
		});
		$scope.attention.experList=experList;
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
    	$scope.selectedExperts=[];
		 if(item.experList){
			 var experts=item.experList.split(";");							 
			angular.forEach(experts,function(data){
				if(data!=''){
				var myjson=$filter('filter')($scope.allexperts,{'id':data})[0];
				$scope.selectedExperts.push(myjson);
				}
			});
		 }
//    	$scope.attention.publishDate = item.publishDate;
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
				 $scope.allexperts= data.experts;
				 console.log($scope.allexperts);
			 }else {				
				 $scope.attentions={}
			 }
		 });
    }
    $scope.addExpert=function(){
    	var modalInstance=$modal.open({
            templateUrl : 'expert_attention.html',  //创建文件上传视图
            controller : 'addExpertCtrl',
            backdrop : "static",
            resolve : {
            	selectedExperts: function(){
                    return $scope.selectedExperts;
                }
            }
        });
    	modalInstance.result.then(function(selectedExperts){  
            $scope.selectedExperts = selectedExperts;
        },function(){
            console.log('Modal dismissed at: ' + new Date())
        })
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

adminApp.controller('addExpertCtrl',['$scope','$http','$modalInstance','$filter','selectedExperts',function($scope,$http,$modalInstance,$filter,selectedExperts){ //依赖于modalInstance
	$http({
		 method:'GET',
			url:"/tms/qemAdmin/listExperts"
	 }).success(function(data) {
		 if(data!=null){
			 $scope.expertList=data.experts;
			 angular.forEach(selectedExperts,function(data){
					var item=$filter('filter')($scope.expertList,{'id':data.id},true)[0];
					if(item) item.selected=true;
				});
		 }			
	 });
    $scope.ok = function(){
    	var options=[]
		angular.forEach($scope.expertList,function(data){
			if(data.selected) options.push(data);
		});
    	$modalInstance.close(options)
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel'); // 退出
    }
}]);