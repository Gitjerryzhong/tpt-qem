var cCheckApp = angular.module('cCheckApp', ['ui.router','ui.bootstrap','angularFileUpload','mine.filter']);
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
//        //        倒序排序
//        out.sort( function(b,a){
//				if(typeof(a)=="number") 
//					return a>b;
//				return a.localeCompare(b);
//				});
//        return out;
//	}
//});
