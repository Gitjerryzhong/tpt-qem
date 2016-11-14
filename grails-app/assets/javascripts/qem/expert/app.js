var expertApp = angular.module('expertApp', ['ui.router','ui.bootstrap','mine.filter']);
expertApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
//    $urlRouterProvider.otherwise('/attention');
    $stateProvider
    .state('default', {
        url: '/default',
        templateUrl: 'qem-beginning.html'
    })
    .state('taskview', {
        url: '/taskview',
        templateUrl: 'qem-taskview.html'
    })
    .state('reviewed', {
        url: '/reviewed',
        templateUrl: 'qem-reviewed.html'
    })
    .state('stageReviewed', {
        url: '/stageReviewed',
        templateUrl: 'qem-stageReviewed.html'
    })
    .state('attention', {
        url: '/attention',
        templateUrl: 'qem-attention.html'
    });
}]);
