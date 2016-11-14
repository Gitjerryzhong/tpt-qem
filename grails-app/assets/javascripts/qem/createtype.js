angular.module('formApp',[])
.controller("typeCtrl",function($scope,$http){
	$scope.formData={};
	$scope.processForm=function(){
		alert("here");
		$http({
			method:'POST',
			url:"./create",
			data:$.param($scope.formData),
			headers:{ 'Content-Type': 'application/x-www-form-urlencoded;charset=GBK','x-camnpr-uid': '1000' }  // set the headers so angular passing info as form data (not request payload)
		  }).success(function(data) {
			  if(!data.success){console.log("保存失败！")}
			});
		};
	
});
//var formApp = angular.module('formApp', []);
//
//		  // create angular controller and pass in $scope and $http
//		  function typeCtrl($scope, $http) {
//
//			  // create a blank object to hold our form information
//			  // $scope will allow this to pass between controller and view
//			  $scope.formData = {};
//			  // process the form
//			  $scope.processForm = function() {//
//				  alert("here");
//				  $http({
//					  method  : 'POST',
//					  url     : './dudao.do?method=saveTimes',
//					  data    : $.param($scope.formData),  // pass in data as strings
//					  headers : { 'Content-Type': 'application/x-www-form-urlencoded;charset=GBK','x-camnpr-uid': '1000' }  // set the headers so angular passing info as form data (not request payload)
//				  }).success(function(data) {
//						  if (!data.success) {
//							  // if not successful, bind errors to error variables
//							  $scope.formData.courseName = '错误！';
//						  }
//						  });
//			  };	
//
//		  }

