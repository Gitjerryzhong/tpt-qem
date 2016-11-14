var pAdminApp = angular.module('pAdminApp', ['ui.router','ui.bootstrap','ngAnimate','mine.filter','mine.constant','agGrid']);
pAdminApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
//    $urlRouterProvider.otherwise('/default');
    $stateProvider
    .state('default', {
        url: '/default',
        templateUrl: 'qem-default.html'
    })
    .state('details', {
        url: '/details',
        templateUrl: 'qem-details.html'
    })
    .state('groups', {
        url: '/groups',
        templateUrl: 'qem-groups.html'
    })
    .state('assigned', {
        url: '/assigned',
        templateUrl: 'qem-project-expert.html'
    })
    .state('reviewed', {
        url: '/reviewed',
        templateUrl: 'qem-reviewed.html'
    })
    .state('expertView', {
        url: '/expertview',
        templateUrl: 'qem-expertview.html'
    })
    .state('taskList', {
        url: '/taskList',
        templateUrl: 'qem-taskList.html'
    })
    .state('taskDetail', {
        url: '/taskDetail',
        templateUrl: 'qem-taskDetail.html'
    })
    .state('requestList', {
        url: '/requestList',
        templateUrl: 'qem-requestList.html'
    });
}]);
pAdminApp.filter('unique',function(){
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
.filter('expFilter',function(){
	return function(item){
		if(item==null || item=='null')
			return item;
		else{
			var replaceStr = "###";
//			var exps=item.split("$$$");
			var expsv=item.replace(new RegExp(replaceStr,'gm'),"：");
////			console.log(exps.length)
//			for(var i=0;i<exps.length;i++)
//			 {
//				if(exps[i]!="")	expsv+="专家"+i+"："+exps[i];
//			 }
			return expsv;
		}
	}
})
.filter('statusFilter',function(){
	return function(item){
		if(item==4 || item==6)
			return '是';
		else if(item == 5){
			return '否';
		}else {
			return '未'
		}			
	}
})
//.filter('uniDept',function(){
//	return function(items){
//		var out = [];
////		console.info(items);
//		angular.forEach(items, function (item) {
//			var exists=false;
//            for (var i = 0; i < out.length; i++) {
//                if (item.departmentName == out[i]) { 
//                	exists=true;
//                    break;
//                }
//            }
//            if(!exists) {
//            	out.push(item.departmentName);            	
//            }
//        })
//        return out;
//	}
//})
//.filter('uniType',function(){
//	return function(items){
//		var out = [];
////		console.info(items);
//		angular.forEach(items, function (item) {
//			var exists=false;
//            for (var i = 0; i < out.length; i++) {
//                if (item.type == out[i]) { 
//                	exists=true;
//                    break;
//                }
//            }
//            if(!exists) {
//            	out.push(item.type);            	
//            }
//        })
//        return out;
//	}
//})
//.filter('uniKey',function(){
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
.filter('nullFilter',function(){
	return function(item){
		if(item==null || item=='null' || item=='ALL')
        return "不限";
		else return item;
	}
});
pAdminApp.animation('.view-slide-in', function () {
	 return {
	 enter: function(element, done) {
	  element.css({
	  opacity: 0.5,
	  position: "relative",
	  top: "10px",
	  left: "20px"
	  })
	  .animate({
	  top: 0,
	  left: 0,
	  opacity: 1
	  }, 1000, done);
	 }
	 };
	});
pAdminApp.animation('.repeat-animation', function () {
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
