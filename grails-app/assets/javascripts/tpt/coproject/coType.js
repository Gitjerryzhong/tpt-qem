coProjectApp.controller('CoTypeCtrl',['$rootScope','$scope','$http',function($rootScope,$scope,$http){ //国外大学管理
	$scope.coTypeId=0;
   $scope.coType={}
   $scope.coTypes ={}
    $scope.saveCoType = function(){ //保存    	
    	$http({
			method:'POST',
			url:"/tms/tptCoPrjAdmin/saveCoType",
			params:{coTypeId:$scope.coTypeId},
			data:JSON.stringify($scope.coType),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {			  
			  if(data!=null && data.coTypes!=null){
					 $scope.coTypes=data.coTypes;					 
				 }else {				
					 $scope.coTypes={}
				 }
			  $scope.coTypeId=0;
			  $scope.coType={}
			  $scope.rmErrors=null
			}).error(function(data) {
				alert("保存出错！");
			})
    }
    $scope.actionTitle = function(){
    	if($scope.coTypeId) return '更名'
    	else return '添加'
    }
    $scope.showCoType = function(){ //显示类型
    	$http({
			 method:'GET',
				url:"/tms/tptCoPrjAdmin/showCoType"
		 }).success(function(data) {
			 if(data!=null && data.coTypes!=null){
				 $scope.coTypes=data.coTypes;					 
			 }else {				
				 $scope.coTypes={}
			 }
			 $scope.rmErrors=null
		 });
    };
    $scope.rmCoType = function(id){
    	$http({
    		method:'GET',
			url:"/tms/tptCoPrjAdmin/rmCoType",
			params:{
				coTypeId:id
			}
    	}).success(function(data) {
			 if(data!=null && data.coTypes!=null){
				 $scope.coTypes=data.coTypes;					 
			 }else {				
				 $scope.coTypes={}
			 }
		 }).error(function(data){
			$scope.rmErrors='删除出错！可能该项目分类已被使用，可改用更名功能！';
			alert($scope.rmErrors);
				});;
    };

    $scope.editCoType = function(item){
    	$scope.coTypeId=item.id;
    	$scope.coType.name=item.name;
    	$scope.rmErrors=null
    }
}]);
