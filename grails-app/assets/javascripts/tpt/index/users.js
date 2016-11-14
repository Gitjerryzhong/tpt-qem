adminApp.controller('UsersCtrl',['$rootScope','$scope','$http','$modalInstance','$filter',function($rootScope,$scope,$http,$modalInstance,$filter){
	$rootScope.dataStatus=1;
	var forEach = angular.forEach;
	$scope.ok = function(){ 
//		$rootScope.userList={}
//		$rootScope.errors={}
		$http({
			method:'POST',
			url:"/tms/tptAdmin/createUsers",
			data:JSON.stringify({users:this.users,
						projectId:this.project==null?0:this.project.id,
						projectName:this.project==null?null:this.project.name}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
//			  $rootScope.userList = data.userList;
			  if(data !=null && data.errors)
			  $scope.errors = data.errors.error;
			  $scope.results = data.errors.results;
			});
//		$modalInstance.close(); //关闭并返回当前选项
		this.users="";
    };
    $scope.cancel = function(){    	
    	$modalInstance.dismiss('cancel'); // 退出
    }
//    $scope.showCourse = function(project){
//    	$scope.courseList=[];
//    	var pro_course_sum = $filter('filter')($rootScope.pro_Course_List,{'LBMC':project.name});
//    	forEach(pro_course_sum,function(data){				
//			var major = $filter('filter')($rootScope.majors,{'gmId':data.JXJHH})[0];
//			if(major){
//				var item ={};
//				item.grade = major.grade;
//				item.major = major.majorName;
//				item.courseCount = data.C;
//				$scope.courseList.push(item);
//			}			
//		});
//    }
}]);