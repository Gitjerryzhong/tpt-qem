var cIndexApp = angular.module('cIndexApp', ['ui.router','ui.bootstrap','mine.filter']);
cIndexApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
    $urlRouterProvider.otherwise('/tab1');
    $stateProvider
    .state('tab1', {
        url: '/tab1',
        templateUrl: 'tab1.html'
    })
    .state('tab2', {
        url: '/tab2',
        templateUrl: 'tab2.html'
    })
    .state('tab3', {
        url: '/tab3',
        templateUrl: 'tab3.html'
    })
    .state('taskDetail', {
        url: '/taskDetail',
        templateUrl: 'qem-taskDetail.html'
    });
}]);
cIndexApp.filter("groups", function () {                       
	return function (items) {
//    	console.log(items);
		var index1=1;
    	var index2=1;
    	var index3=1;
    	var groups={"0":"未提交或其他","1":"未审","2":"已审","3":"已审","4":"未审","5":"已审",
    				"201":"已审","202":"已审","203":"已审"}
    	angular.forEach(items, function (item) {
    		if(groups[item.runStatus]==undefined)item.groups="未提交或其他";
    		else item.groups=groups[item.runStatus];
    		switch(item.groups){
    		case "未审": item.index=index1++; break;
    		case "已审": item.index=index2++; break;
    		case "未提交或其他": item.index=index3++; break;
    		}
    	})
    return items;    
    }
});