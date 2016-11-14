coProjectApp.controller('CoXsmdAddCtrl',['$rootScope','$scope','$http','$filter',function($rootScope,$scope,$http,$filter){ //学生名单导入管理
	var forEach = angular.forEach;
	$scope.myfilter = {};
	$scope.proList={};
	$scope.majors={};
	$scope.pro_Course_List={};
	$http({
		 method:'GET',
			url:"/tms/tptCoPrjAdmin/getProList"
	 }).success(function(data) {
		 if(data!=null){
			 $scope.proList=data.proList;
			 $scope.pro_Course_List=data.course;
			 $scope.majors=data.majors;
		 }
	 });
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
			 }
		 });
	}
	$scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
	$scope.showCourse = function(project){
    	$scope.courseList=[];
    	var pro_course_sum = $filter('filter')($scope.pro_Course_List,{'LBMC':project.name});
    	console.log($scope.majors);
    	forEach(pro_course_sum,function(data){				
			var major = $filter('filter')($scope.majors,{'gmId':data.JXJHH})[0];
			if(major){
				var item ={};
				item.grade = major.grade;
				item.major = major.majorName;
				item.courseCount = data.C;
				$scope.courseList.push(item);
			}			
		});
    	console.log($scope.courseList);
    }
	$scope.save = function(){ 
		$scope.results={};
		$http({
			method:'POST',
			url:"/tms/tptCoPrjAdmin/createUsers",
			data:JSON.stringify({users:this.users,projectName:this.project.name}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
//			  console.log(data.results)
			  $scope.results=data.results;
			}).error(function(data){
			});
		this.users=null;
    };
}]);
