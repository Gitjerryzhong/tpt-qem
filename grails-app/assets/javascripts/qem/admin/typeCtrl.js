adminApp.controller('TypeCtrl',['$rootScope','$scope','$http','FileUploader','$filter',function($rootScope,$scope,$http,FileUploader,$filter){ //类别管理
	$scope.parentTypes ={}
	$scope.type ={}
	$scope.type.parentTypeId =0;
	$scope.order="id";
	$scope.getParentTypes = function(){ //显示通知
    	$http({
			 method:'GET',
				url:"/tms/qemAdmin/showParentType"
		 }).success(function(data) {
			 if(data!=null && data.parentTypes!=null){
				 $scope.parentTypes=data.parentTypes;	
				 $scope.type.parentTypeId=$scope.parentTypes[0].id;
//				 console.log('parent',$scope.parentTypes)				
			 }else {				
				 $scope.parentTypes={}
			 }			 
		 });
    };
	$scope.addType = function(){
		$http({
			method:'POST',
			url:"/tms/qemAdmin/saveType",
			params:{typeId:$scope.typeId},
			data:JSON.stringify($scope.type),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $scope.typeId=0;
			  $scope.type={}
			  uploader.clearQueue();
			  if(data!=null && data.types!=null){
					 $scope.types=data.types;	
					  $scope.type.parentTypeId=$scope.parentTypes[0].id;
				 }else {				
					 $scope.types={}
				 }			 
			  $scope.rmErrors=null;	
			  if(data!=null && data.errors!=null){
				  $scope.rmErrors=data.errors.errors;
//				  console.info('errors',$scope.rmErrors[0]);
			  }
			}).error(function(data){
				$scope.rmErrors='数据保存失败！';
			});	
	}
	$scope.editType = function(item){
//		console.log($scope.types);
//		console.log(index);
//		var type = item;
		$scope.type.parentTypeId=item.parentTypeId;
		$scope.type.name=item.name;
		$scope.type.cycle=item.cycle;
		$scope.typeId = item.id;
		$scope.type.actived = item.actived;
		$scope.type.downLoadUrl=item.downLoadUrl;
	}
	$scope.shielding = function(index){
		$http({
    		method:'GET',
			url:"/tms/qemAdmin/shielding",
			params:{
				typeId:index
			}
    	}).success(function(data) {
    		
    	});
	}
	$scope.rmType = function(index){
		$http({
    		method:'GET',
			url:"/tms/qemAdmin/rmType",
			params:{
				typeId:index
			}
    	}).success(function(data) {
    		if(data!=null && data.types!=null){
				 $scope.types=data.types;					 
			 }else {				
				 $scope.types={}
			 }
		 }).error(function(data){
			$scope.rmErrors='删除出错！该大类下有子类别，可改用更名功能！';
			alert($scope.rmErrors);
				});;
	}
	$scope.orderBy = function(col){		
		console.info(col,$scope.order);
		if($scope.order==col)
			$scope.order = "-"+col;
		else 
			$scope.order = col;			
    }
	$scope.showType = function(index){
		$http({
			 method:'GET',
				url:"/tms/qemAdmin/showType"
		 }).success(function(data) {
			 if(data!=null && data.types!=null){					
				 $scope.types=data.types;
			 }else {				
				 $scope.types={}
			 }			 
		 });
	}	
	var uploader = $scope.uploader = new FileUploader({
        url: 'uploadTemplate',
    });
	
    // FILTERS

    uploader.filters.push({
        name: 'customFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 2;
        }
    });
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        $scope.type.downLoadUrl= response.filename;        
    };
    $scope.getParentTypes();
}]);
adminApp.controller('ParentCtrl',['$rootScope','$scope','$http','$filter',function($rootScope,$scope,$http,$filter){ //大类管理
	$scope.parentTypeId=0;
   $scope.type={}
   $scope.parentTypes ={}
    $scope.saveParentType = function(){ //保存    	
    	$http({
			method:'POST',
			url:"/tms/qemAdmin/saveParentType",
			params:{parentTypeId:$scope.parentTypeId},
			data:JSON.stringify($scope.type),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {			  
			  if(data!=null && data.parentTypes!=null){
					 $scope.parentTypes=data.parentTypes;					 
				 }else {				
					 $scope.parentTypes={}
				 }
			  $scope.parentTypeId=0;
			  $scope.type={}
			  $scope.rmErrors=null
			})
    }
    $scope.actionTitle = function(){
    	if($scope.parentTypeId) return '更名'
    	else return '添加'
    }
    $scope.showType = function(){ //显示通知
    	$http({
			 method:'GET',
				url:"/tms/qemAdmin/showParentType"
		 }).success(function(data) {
			 if(data!=null && data.parentTypes!=null){
				 $scope.parentTypes=data.parentTypes;					 
			 }else {				
				 $scope.parentTypes={}
			 }
			 $scope.rmErrors=null
		 });
    };
    $scope.rmParentType = function(index){
    	$http({
    		method:'GET',
			url:"/tms/qemAdmin/rmParentType",
			params:{
				parentTypeId:index
			}
    	}).success(function(data) {
    		if(data!=null && data.parentTypes!=null){
				 $scope.parentTypes=data.parentTypes;					 
			 }else {				
				 $scope.parentTypes={}
			 }
		 }).error(function(data){
			$scope.rmErrors='删除出错！该大类下有子类别，可改用更名功能！';
			alert($scope.rmErrors);
				});;
    };
    $scope.editParentType = function(item){
//    	var parentType = $filter('filter')($scope.parentTypes,{'id':id})[0];
    	$scope.parentTypeId=item.id;
    	$scope.type.parentTypeName=item.parentTypeName;
    	$scope.rmErrors=null
    }
    $scope.orderBy = function(col){
    	$scope.order = col;
    }
}]);