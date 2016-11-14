adminApp.controller('CollegeCtrl',['$rootScope','$scope','$http','$modal',function($rootScope,$scope,$http,$modal){ //国外大学管理
	
   $scope.colleges ={}
   $scope.list = function(){ //显示通知
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/showCollege"
		 }).success(function(data) {
			 if(data!=null && data.colleges!=null){
				 $scope.colleges=data.colleges;	
				 console.log($scope.colleges);
			 }else {				
				 $scope.colleges={}
			 }
		 });
    };
    $scope.open = function(){
		 var modalInstance=$modal.open({
	            templateUrl : 'add-college.html',  
	            controller : 'CollegeAddCtrl',
	            backdrop : "static"
	        }) 
	      modalInstance.result.then(function(colleges){  
	    	  $scope.colleges = colleges;
            },function(){
                $log.info('Modal dismissed at: ' + new Date())
            })
	 }
    $scope.rmCollege = function(index){
    	$http({
    		method:'GET',
			url:"/tms/tptAdmin/rmCollege",
			params:{
				collegeId:index
			}
    	}).success(function(data) {
			 if(data!=null && data.colleges!=null){
				 $scope.colleges=data.colleges;					 
			 }else {				
				 $scope.colleges={}
			 }
		 }).error(function(data){
			$scope.rmErrors='删除出错！可能该大学已被申请，可改用更名功能！';
			alert($scope.rmErrors);
				});;
    };
    $scope.editCollege = function(index){
    	$scope.collegeId=$scope.colleges[index].id;
    	$scope.college.name=$scope.colleges[index].name;
    	$scope.rmErrors=null
    }
    $scope.list();
}]);
adminApp.controller('CollegeAddCtrl',['$scope','$http','$modalInstance','$filter',function($scope,$http,$modalInstance,$filter){
	$scope.collegeId=0;
	$scope.college={}
	$scope.saveCollege = function(){ //保存    	
    	$http({
			method:'POST',
			url:"/tms/tptAdmin/saveCollege",
			params:{collegeId:$scope.collegeId},
			data:JSON.stringify($scope.college),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {			  
			  if(data!=null && data.colleges!=null){
					 $scope.colleges=data.colleges;	
					 $modalInstance.close($scope.colleges);
				 }else {				
					 $scope.colleges={}
				 }
			  $scope.collegeId=0;
			  $scope.college={}
			}).error(function(data){
				$modalInstance.dismiss('cancel');
			})
			
			
    }
}]);