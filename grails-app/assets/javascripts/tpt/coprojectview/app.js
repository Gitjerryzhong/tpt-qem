var coProjectApp = angular.module('coProjectApp', ['ui.router','ui.bootstrap','mine.filter','agGrid']);
coProjectApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
    $urlRouterProvider.otherwise('/prosummary');
    $stateProvider       
    .state('copro-detail', {
        url: '/copro-detail',
        templateUrl: 'tpt-co-pro-detail.html'
    })
    .state('prosummary', {
        url: '/prosummary',
        templateUrl: 'tpt-co-pro-summary.html'
    });
}]);
coProjectApp.filter("effeCurrently",function(){
	return function(items){
		var out = [];
		angular.forEach(items, function (item) {
			var currentYear = new Date().getFullYear();
			var dif = currentYear-item.beginYear;
			var effe = (item.effeYears >> dif) & 1 ;
			if(effe) out.push(item);
        })
        return out;
	}
})
.filter("ifUneffective",function(){
	return function(items){
		var out = [];
		angular.forEach(items, function (item) {
			item.isUneffective=false;
			var currentYear = new Date().getFullYear();
			var dif = currentYear-item.beginYear;
			var effe = (item.effeYears >> dif) & 1 ;
			if(!effe) item.isUneffective = true;
			out.push(item);
        })
        return out;
	}
})
.filter("uniName",function(){
	return function(items){
		var out = [];
		angular.forEach(items, function (item) {
			var exists=false;
			var obj = {};
            for (var i = 0; i < out.length; i++) {
                if (item.projectName == out[i].projectName) { 
                	exists=true;
                    break;
                }
            }
            if(!exists) {
            	obj.coProjectId = item.coProjectId;
            	obj.projectName = item.projectName;
            	obj.coTypeName = item.coTypeName;
            	obj.coProTypeName = item.coProTypeName;
            	obj.effective = item.effective;
            	obj.ifTowDegree = item.ifTowDegree;
            	out.push(obj);            	
            }
        })
        return out;
	}
});
coProjectApp.controller('parentCtrl',['$rootScope','$scope','$http',function($rootScope,$scope,$http){
		
}]);