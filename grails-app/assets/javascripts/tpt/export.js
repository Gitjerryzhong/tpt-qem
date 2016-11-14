var checkApp = angular.module('checkApp', ['ui.router','ui.bootstrap']);
checkApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
	$urlRouterProvider.otherwise('/download-photo')
    $stateProvider    
	.state('downloadPhoto', {
        url: '/download-photo',
        templateUrl: 'download-photo.html'
    })
    .state('downloadPaper', {
        url: '/download-paper',
        templateUrl: 'download-paper.html'
    })
    .state('downloadALl', {
        url: '/download-all',
        templateUrl: 'download-all.html'
    })
    .state('exportRequest', {
        url: '/exportRequest',
        templateUrl: 'export.html'
    })
    .state('exportMtlRgn', {
        url: '/exportMtlRgn',
        templateUrl: 'export-mtlRgn.html'
    });
}]);
checkApp.controller('TrialCtrl',['$scope','$http','$location',function($scope,$http,$location){ 
	$scope.bns={}
		
	$scope.getBns = function(){ 
    	$http({
			 method:'GET',
				url:"/tms/tptExport/getBns"				
		 }).success(function(data) {
			 if(data!=null ){		
				 $scope.bns=data.bns;	
//				 console.info($scope.bns)
				 $scope.bn=$scope.bns[0]
			 }
		 });  
    	 
    };

    $scope.getBns();  
   
    $scope.downloadPhoto = function(){
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/downloadPhotoByStatus"
		 }).success(function(data) {
			 if(data!=null ){	
				 $scope.downloadUrl=data.download
				 $scope.downloadUrlEnd=data.downloadEnd
				 $location.url('/download-photo');
			 }
		 });
    }
    $scope.downloadPaper = function(){
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/downloadPaper"
		 }).success(function(data) {
			 if(data!=null ){	
				 $scope.downloadUrl=data.download
				 $location.url('/download-paper');
			 }
		 });
    }
    $scope.auditAble=function(){
    	return checkStatus==1 || checkStatus == 4;
    }
}]);
