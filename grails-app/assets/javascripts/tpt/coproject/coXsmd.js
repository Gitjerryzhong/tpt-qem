coProjectApp.controller('CoXsmdCtrl',['$rootScope','$scope','$http','$state',function($rootScope,$scope,$http,$state){ //学生名单导入管理
	$scope.myfilter = {};
	$scope.selected={} //项目列表
	$scope.proList={}
	$scope.search = function(){
		$http({
			 method:'GET',
				url:"/tms/tptCoPrjAdmin/studentList",
				params:{
					studentId:$scope.myfilter.studentId,
					co_Country:$scope.myfilter.co_Country
				}
		 }).success(function(data) {
			 if(data!=null){
				 $scope.userList = data.studnetList;
//				 $scope.proList = data.proList;
			 }
		 });
	}
	$scope.dateFormat = function(jsondate){
    	return jsondate?new Date(jsondate):null;
    }
	$scope.usersAdd = function(){
		$state.go('usersAdd');
	}
	$scope.span_click=function(item){
		item.clicked=true;
		$http({
			 method:'GET',
				url:"/tms/tptCoPrjAdmin/getProjects",
				params:{
					studentId:item.XH,
				}
		 }).success(function(data) {
			 if(data!=null){
				 $scope.proList = data.proList;
				 console.log(data.proList);
			 }
		 });
	}
	$scope.kindChange = function(item,kind){
		var oldKind=item.KIND;
		item.KIND= kind;
		item.clicked=false;
		$http({
			 method:'POST',
				url:"/tms/tptCoPrjAdmin/editStudent",
				data:JSON.stringify({
					id:			item.MDID,
					xh:			item.XH,
					xm:			item.XM,
					kind:		item.KIND,
					oldKind:	oldKind
				}),
				headers:{ 'Content-Type': 'application/json' }
		 }).success(function(data) {
		 });
	}
	$scope.delItem = function(item){
		console.log(item);
		var r=confirm("确定要删除“"+item.XM+"("+item.XH+")”吗？");
		if (r==true)
		 {
			$http({
				 method:'GET',
					url:"/tms/tptCoPrjAdmin/deleteStudent",
					params:{
						id: item.MDID,
						xh: item.XH,
						studentId:$scope.myfilter.studentId,
						co_Country:$scope.myfilter.co_Country
		 			}
			 }).success(function(data) {
				 if(data!=null){
					 $scope.userList = data.studnetList;
				 }			
			 });
		 }	
	}
}]);
