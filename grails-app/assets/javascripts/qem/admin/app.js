var adminApp = angular.module('adminApp', ['ui.router','ui.bootstrap','angularFileUpload','mine.directive']);
adminApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider){
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
    $urlRouterProvider.otherwise('/notice');
    $stateProvider
    .state('parentTypes', {
        url: '/parentTypes',
        templateUrl: 'qem-parenttype.html'
    })
    .state('types', {
            url: '/types',
            templateUrl: 'qem-types.html'
     })
    
    .state('notice', {
        url: '/notice',
        templateUrl: 'qem-notice.html'
    })
    .state('attention', {
        url: '/attention',
        templateUrl: 'qem-attention.html'
    })
    .state('experts', {
        url: '/experts',
        templateUrl: 'qem-experts.html'
    })
    .state('expertlist', {
        url: '/expertlist',
        templateUrl: 'expert_list.html'
    });
}]);
adminApp.filter('uniDept',function(){
	return function(items){
		var out = [];
//		console.info(items);
		angular.forEach(items, function (item) {
			var exists=false;
            for (var i = 0; i < out.length; i++) {
                if (item.departmentName == out[i]) { 
                	exists=true;
                    break;
                }
            }
            if(!exists) {
            	out.push(item.departmentName);            	
            }
        })
        return out;
	}
})
.filter('nullFilter',function(){
	return function(item){
		if(item==null || item=='null' || item=='ALL')
        return "不限";
		else return item;
	}
});