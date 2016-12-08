var updateAdminApp = angular.module('updateAdminApp', ['ui.router','ui.bootstrap','mine.filter','mine.constant']);
updateAdminApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
	 $urlRouterProvider.otherwise('/historyList');
	$stateProvider
    .state('historyList', {
        url: '/historyList',
        templateUrl: 'qem-historyUpdateList.html'
    })
    .state('updateDetail', {
        url: '/updateDetail',
        templateUrl: 'qem-updateView.html'
    });
}]);
updateAdminApp.filter("groups", function () {                       
    return function (items) {
    	var groups =[['未提交或其他','未知','未知','未提交或其他'], ['未提交或其他','未提交或其他','未提交或其他','未提交或其他'],['未审','已审','已审','未知']]; 
    	angular.forEach(items, function (item) {
    		item.groups=groups[item.flow][item.auditStatus];
    	})
    return items;    
    }
});
