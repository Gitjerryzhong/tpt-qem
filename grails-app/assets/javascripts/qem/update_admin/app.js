var updateAdminApp = angular.module('updateAdminApp', ['ui.router','ui.bootstrap','mine.filter']);
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
        templateUrl: 'qem-updateDetail.html'
    });
}]);
//updateAdminApp.filter('uniKey',function(){
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
//});
