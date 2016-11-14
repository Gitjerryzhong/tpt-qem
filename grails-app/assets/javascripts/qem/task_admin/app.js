var tAdminApp = angular.module('tAdminApp', ['ui.router','ui.bootstrap','ngAnimate','mine.filter','agGrid']);
tAdminApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
    $urlRouterProvider.otherwise('/taskList');
    $stateProvider    
    .state('taskSummary', {
        url: '/taskSummary',
        templateUrl: 'qem-taskSummary.html'
    })
    .state('taskList', {
        url: '/taskList',
        templateUrl: 'qem-taskList.html'
    })
    .state('annualList', {
        url: '/annualList',
        templateUrl: 'qem-annualList.html'
    })
    .state('confirmedList', {
        url: '/confirmedList',
        templateUrl: 'qem-confirmedList.html'
    })
    .state('taskDetail', {
        url: '/taskDetail',
        templateUrl: 'qem-taskDetail.html'
    })
    .state('stageDetail', {
        url: '/stageDetail',
        templateUrl: 'qem-stageDetail.html'
    })
    .state('stageAudit', {
        url: '/stageAudit',
        templateUrl: 'qem-stageAudit.html'
    })
    .state('newtask', {
        url: '/newtask',
        templateUrl: 'qem-newtask.html'
    })
    .state('expertview', {
        url: '/expertview',
        templateUrl: 'qem-expertView.html'
    });
}]);
tAdminApp.filter('unique',function(){
	return function(items,key){
		var out = [];
//		console.info(items);
		angular.forEach(items, function (item) {
			var exists=false;			
            for (var i = 0; i < out.length; i++) {
                if (item[key] == out[i]) { 
                	exists=true;
                    break;
                }
            }
            if(!exists) {
            	out.push(item[key]);            	
            }
        })
        return out;
	}
})
.filter('uniDept',function(){
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
.filter('uniType',function(){
	return function(items){
		var out = [];
//		console.info(items);
		angular.forEach(items, function (item) {
			var exists=false;
            for (var i = 0; i < out.length; i++) {
                if (item.type == out[i]) { 
                	exists=true;
                    break;
                }
            }
            if(!exists) {
            	out.push(item.type);            	
            }
        })
        return out;
	}
})
.filter('stageText',function(){
	return function(value){
		var TITLE = {
    			"0": "合",
    			"1": "年",
    			"2": "中",
    			"3": "结"
    		};
    	return TITLE[value];
	}
})
.filter('stageStatus',function(){
	return function(value){
		var TITLE = {
    			"0": "未提交",
    			"1": "已提交",
    			"2": "确认进行评审",
    			"30": "评审中",
    			"31": "评审通过",
    			"32": "评审不通过"
    		};
    	return TITLE[value];
	}
})
.filter('memoFilter',function(){
	return function(item){
		if(item!=null && item!='' && item.length>4)
        return item.substr(0,3)+"...";
		else return item;
	}
})
.filter('nullFilter',function(){
	return function(item){
		if(item==null || item=='null' || item=='ALL')
        return "不限";
		else return item;
	}
});
tAdminApp.animation('.repeat-animation', function () {
	 return {
	 enter : function(element, done) {
	  console.log("entering...");
	  var width = element.width();
	  element.css({
	  position: 'relative',
	  left: -10,
	  opacity: 0
	  });
	  element.animate({
	  left: 0,
	  opacity: 1
	  }, done);
	 },
	 leave : function(element, done) {
	  element.css({
	  position: 'relative',
	  left: 0,
	  opacity: 1
	  });
	  element.animate({
	  left: -10,
	  opacity: 0
	  }, done);
	 },
	 move : function(element, done) {
	  element.css({
	  left: "2px",
	  opacity: 0.5
	  });
	  element.animate({
	  left: "0px",
	  opacity: 1
	  }, done);
	 }
	 };
	});
