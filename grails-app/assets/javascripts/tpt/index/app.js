var adminApp = angular.module('adminApp', ['ui.router','ui.bootstrap','mine.filter']);
adminApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
    $urlRouterProvider.otherwise('/notice');
    $stateProvider
        .state('users', {
            url: '/users',
            templateUrl: 'tpt-users.html'
        })
    
    .state('notice', {
        url: '/notice',
        templateUrl: 'tpt-notice.html'
    })
    .state('college', {
        url: '/college',
        templateUrl: 'tpt-college.html'
    })
    .state('mentor', {
        url: '/mentor',
        templateUrl: 'mentor-list.html'
    });
}]);
adminApp.controller('parentCtrl',['$rootScope','$scope','$http',function($rootScope,$scope,$http){
	$scope.dataStatus=2;	
}]);