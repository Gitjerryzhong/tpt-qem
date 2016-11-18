var cCheckApp = angular.module('cCheckApp', ['ui.router','ui.bootstrap','angularFileUpload','mine.filter','mine.constant']);
cCheckApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
    $stateProvider
    .state('contractList', {
        url: '/contractList',
        templateUrl: 'qem-taskList.html'
    })
    .state('historyList', {
        url: '/historyList',
        templateUrl: 'qem-historyUpdateList.html'
    })
    .state('taskDetail', {
        url: '/taskDetail',
        templateUrl: 'qem-taskDetail.html'
    })
    .state('updateDetail', {
        url: '/updateDetail',
        templateUrl: 'qem-updateDetail.html'
    })
    .state('updateRequest', {
        url: '/updateRequest',
        templateUrl: 'qem-updateForm.html'
    });
}]);
