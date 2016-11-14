adminApp.controller('modal',['$rootScope','$scope','$http','$modal','$log','$filter',function($rootScope,$scope,$http,$modal,$log,$filter){ //
	$rootScope.proList={};
//	$rootScope.majors={};
//	$rootScope.pro_Course_List={};
	$scope.trial={};
	$scope.studentList=[];
	$scope.marjorStudent=[]
	$http({
		 method:'GET',
			url:"/tms/tptAdmin/getProList"
	 }).success(function(data) {
		 if(data!=null){
			 $rootScope.proList=data.proList;
//			 $rootScope.pro_Course_List=data.course;
//			 $rootScope.majors=data.majors;
		 }
	 });
	 $scope.open = function(){
		 var modalInstance=$modal.open({
	            templateUrl : 'import-user.html',  //创建负责人资料录入视图
	            controller : 'UsersCtrl',// 初始化模态范围
	            backdrop : "static"
	        }) 
	 }
//	 $scope.clear = function(){
//		 var r=confirm("警告：您确定要清空本学院所有用户吗？");
//		 if (r==true){
//			 $rootScope.deleteCount=""
//			 $http({
//				 method:'GET',
//					url:"/tms/tptAdmin/deleteUsers"
//			 }).success(function(data) {
//				 if(data!=null)
//					 $rootScope.deleteCount="删除本学院人数："+data.count;
//			 });
//			 $rootScope.userList.splice(0,$rootScope.userList.length);
//		 }
//	 }
	 $scope.list = function(){
		 $rootScope.deleteCount=""
		 $http({
			 method:'GET',
				url:"/tms/tptAdmin/show"
		 }).success(function(data) {
			 if(data!=null)
				 $scope.userList=data.userList;
		 });		 
	 }
//	 $scope.search = function(){
//		 var userList = $rootScope.userList;
//		 if( userList==null || userList=={}){
//			 alert('empty')
//		 }
//	 }
	 $scope.majorChange = function(){
		 $scope.userList;
		 $scope.trial.adminClassName =null;
		 $scope.trial.projectName=null;
		 $scope.trial.studentId = null;
		 $scope.studentList=$filter('filter2_0')($scope.userList,'majorName',$scope.trial.majorName);
		 $scope.marjorStudent=$scope.studentList;
	 }
	 $scope.classChange = function(){
		 $scope.studentList=$filter('filter2_0')($scope.marjorStudent,'adminClassName',$scope.trial.adminClassName);
	 }
	 $scope.projectChange = function(){
		 $scope.studentList=$filter('filter2_0')($scope.marjorStudent,'projectName',$scope.trial.projectName); 
	 }
	 $scope.stdChange = function(){
		 $scope.studentList=$filter('filter2_0')($scope.marjorStudent,'id',$scope.trial.studentId);
	 }
	 $scope.list();
}]) ;