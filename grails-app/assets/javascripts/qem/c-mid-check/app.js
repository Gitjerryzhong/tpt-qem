var cCheckApp = angular.module('cCheckApp', ['ui.router','ui.bootstrap','mine.filter','mine.constant']);
cCheckApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
//    $urlRouterProvider.otherwise('/default');
    $stateProvider
    .state('taskList', {
        url: '/taskList',
        templateUrl: 'qem-taskList.html'
    })
    .state('stageDetail', {
        url: '/stageDetail',
        templateUrl: 'qem-taskDetail.html'
    });
}]);

cCheckApp.filter('stageStatus',function(){
	return function(value){
		var TITLE = {
    			"0": "未提交",
    			"1": "已提交",
    			"2": "已确认",
    			"30": "评审中",
    			"31": "评审结束",
    			"41": "学院审核通过",
    			"42": "学院审核不通过"
    		};
    	return TITLE[value];
	}
});