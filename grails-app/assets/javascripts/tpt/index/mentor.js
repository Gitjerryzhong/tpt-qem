adminApp.controller('mentorCtrl',['$scope','$http','$modal','$filter','$location',function($scope,$http,$modal,$filter,$location){ //
	$scope.condition={};
	 $scope.open = function(){
		 var modalInstance=$modal.open({
	            templateUrl : 'mentor-add.html',  //创建负责人资料录入视图
	            controller : 'teachersCtrl',// 初始化模态范围
	            size : 'lg',
	            backdrop : "static"
	        }) ;
		 modalInstance.result.then(function(mentorList){  
             $scope.mentorList = mentorList;
             $location.url('/mentor');
         },function(){      });
	        
	 }
	 $scope.list = function(){
		 $http({
			 method:'GET',
				url:"/tms/tptAdmin/showMentor"
		 }).success(function(data) {
			 if(data!=null)
				 $scope.mentorList=data.mentorList;
		 });		 
	 }
	 $scope.deleteItem=function(item){
		 var r=confirm("确定要删除“"+item.name+"”吗？");
			if (r==true)
			 {
				$http({
					 method:'GET',
						url:"/tms/tptAdmin/deleteMentor",
						params:{
							mentorId: item.id
			 			}
				 }).success(function(data) {
					 if(data!=null){
						 $scope.mentorList=data.mentorList;	
					 }			
				 });
			 }		
	 }
	 $scope.list();
}]) ;

adminApp.controller('teachersCtrl',['$scope','$http','$modalInstance','$filter',function($scope,$http,$modalInstance,$filter){
	var forEach = angular.forEach;
	$scope.offset =0;
	$scope.max=15;
	
	$scope.list=function(){
		$http({
			method:'GET',
			url:"/tms/tptAdmin/loadTeachers",
			params:{
				offset: $scope.offset,
				max: $scope.max
			}
	 }).success(function(data) {
		 if(data!=null)
			 $scope.teachers=data.teachers;
	 });
	}
	$scope.list();	 
	$scope.add = function(){ 
		var options=[]
		forEach($scope.teachers,function(data){
			if(data.selected) options.push(data.id);
		});
		$http({
			method:'POST',
			url:"/tms/tptAdmin/saveMentors",
			data:JSON.stringify(options),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $modalInstance.close(data.mentorList); //关闭并返回当前选项
			});
		
    };
    $scope.cancel = function(){    	
    	$modalInstance.dismiss('cancel'); // 退出
    }
    $scope.disabled_prev =function(){
    	if($scope.offset<$scope.max){
    		return 'disabled';
    	}else return '';
    }
    $scope.disabled_next =function(){
    	if(!$scope.teachers){return 'disabled';}
    	else if($scope.teachers.length<$scope.max){return 'disabled';}
    	else return '';
    }
    $scope.next = function(){
    	$scope.offset =$scope.offset+$scope.max;
    	$scope.list() ; 	
    };
    $scope.previous = function(){
    	$scope.offset =$scope.offset-$scope.max;
    	$scope.list();
    };
    $scope.search =function(teachername){
    	$http({
			method:'GET',
			url:"/tms/tptAdmin/loadTeachers",
			params:{
				teachername:teachername
			}
	 }).success(function(data) {
		 if(data!=null)
			 $scope.teachers=data.teachers;
	 });
    }
}]);