var taskApp = angular.module('taskApp', ['ui.router','ui.bootstrap','angularFileUpload','mine.constant']);
taskApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
//    $urlRouterProvider.otherwise('/default');
    $stateProvider
    .state('default', {
        url: '/default',
        templateUrl: 'qem-default.html'
    })
    .state('edit', {
        url: '/edit',
        templateUrl: 'qem-editTask.html'
    })
    .state('stage', {
        url: '/stage',
        templateUrl: 'qem-stageReport.html'
    })
    .state('contract', {
        url: '/contract',
        templateUrl: 'qem-contract.html'
    })
    .state('details', {
        url: '/details',
        templateUrl: 'qem-stageDetail.html'
    });
}]);

