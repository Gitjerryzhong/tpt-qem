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
//cCheckApp.filter('uniKey',function(){
//	return function(items,key){
//		var out = [];
////		console.info(items);
//		angular.forEach(items, function (item) {
//			var exists=false;
//            for (var i = 0; i < out.length; i++) {
//                if (item[key] == out[i]) { 
//                	exists=true;
//                    break;
//                }
//            }
//            if(!exists) {
//            	out.push(item[key]);            	
//            }
//        })
////        倒序排序
//        out.sort( function(b,a){
//				if(typeof(a)=="number") 
//					return a>b;
//				return a.localeCompare(b);
//				});        
//        return out;
//	}
//})
cCheckApp.filter('stageStatus',function(){
	return function(value){
		var TITLE = {
    			"0": "未提交",
    			"1": "已提交",
    			"2": "已确认",
    			"30": "评审中",
    			"31": "评审结束"
    		};
    	return TITLE[value];
	}
});