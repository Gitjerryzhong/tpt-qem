var checkApp = angular.module('checkApp', ['ui.router','ui.bootstrap']);
checkApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
	$urlRouterProvider.otherwise('/trial')
    $stateProvider
    .state('trial', {
            url: '/trial',
            templateUrl: 'tpt-trial.html'           
        })    
    .state('check-paper', {
        url: '/check-paper',
        templateUrl: 'tpt-check-paper.html'
    })    
    .state('details', {
        url: '/details',
        templateUrl: 'tpt-details.html'
    })
    .state('search', {
        url: '/search',
        templateUrl: 'tpt-search.html'
    })
	.state('downloadPhoto', {
        url: '/download-photo',
        templateUrl: 'download-photo.html'
    })
    .state('downloadPaper', {
        url: '/download-paper',
        templateUrl: 'download-paper.html'
    })
    .state('export', {
        url: '/export',
        templateUrl: 'export.html'
    });
}]);