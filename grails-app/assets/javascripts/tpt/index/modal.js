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
//		 $scope.userList;
		 $scope.trial.adminClassName =null;
		 $scope.trial.projectName=null;
		 $scope.trial.studentId = null;
		 if($scope.trial.majorName)
			 $scope.studentList=$filter('filter2_0')($scope.userList,'majorName',$scope.trial.majorName);
		 $scope.marjorStudent=$scope.studentList;
	 }
	 $scope.classChange = function(){
		 if($scope.trial.adminClassName)
			 $scope.studentList=$filter('filter2_0')($scope.marjorStudent,'adminClassName',$scope.trial.adminClassName);
	 }
	 $scope.projectChange = function(){
		 if($scope.trial.projectName && $scope.trial.projectName!='null')
			 $scope.studentList=$filter('filter2_0')($scope.marjorStudent,'projectName',$scope.trial.projectName); 
	 }
	 $scope.stdChange = function(){
		 if($scope.trial.studentId)
			 $scope.studentList=$filter('filter2_0')($scope.userList,'id',$scope.trial.studentId);
	 }
	 $scope.list();
//	 增加项目变更功能2017-03-02
	 $scope.span_click=function(item){
			item.clicked=true;
			$http({
				 method:'GET',
					url:"/tms/tptAdmin/getProjects",
					params:{
						studentId:item.id,
					}
			 }).success(function(data) {
				 if(data!=null){
					 $scope.proList = data.proList;
					 console.log(data.proList);
				 }
			 });
		}
		$scope.kindChange = function(item,kind){			
			item.clicked=false;
			$http({
				 method:'POST',
					url:"/tms/tptAdmin/editStudent",
					data:JSON.stringify({
						xh:			item.id,
						xm:			item.name,
						kind:		kind,
						oldKind:	item.projectName
					}),
					headers:{ 'Content-Type': 'application/json' }
			 }).success(function(data) {
				 item.projectName=kind;
			 });
		}
		$scope.delItem = function(item){			
			var r=confirm("确定要删除“"+item.name+"("+item.id+")”吗？");
			if (r==true)
			 {
				$http({
					 method:'GET',
						url:"/tms/tptAdmin/deleteStudent",
						params:{							
							xh: item.id
			 			}
				 }).success(function(data) {
					 if(data!=null){						 
						 $scope.userList = data.userList;
						 if($scope.trial.majorName)
							 $scope.marjorStudent=$filter('filter2_0')($scope.userList,'majorName',$scope.trial.majorName);
						 else $scope.marjorStudent=$scope.userList;
						 $scope.classChange();
						 $scope.projectChange();
						 $scope.stdChange();
					 }			
				 });
			 }	
		}
}]) ;