var qemApp = angular.module('qemApp', ['ui.router','ui.bootstrap','angularFileUpload','mine.constant']);
qemApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
	$urlRouterProvider.otherwise('/default');
    $stateProvider
    .state('default', {
        url: '/default',
        templateUrl: 'qem-default.html'
    })
    .state('qemRequest', {
        url: '/qemRequest',
        templateUrl: 'qem-project.html'
    })
    .state('qemSearch', {
        url: '/qemSearch',
        templateUrl: 'qem-mylist.html'
    })
    .state('qemDetail', {
        url: '/qemDetail',
        templateUrl: 'qem-detail.html'
    })
    .state('qemTask', {
        url: '/qemTask',
        templateUrl: 'qem-task.html'
    })
    .state('qemUpdate', {
        url: '/qemUpdate',
        templateUrl: 'qem-updateList.html'
    })
    .state('updateView', {
        url: '/updateView',
        templateUrl: 'qem-updateView.html'
    })
    .state('qemTemplates', {
        url: '/qemTemplates',
        templateUrl: 'qem-template.html'
    })
    .state('qemPublic', {
        url: '/qemPublic',
        templateUrl: 'qem-public.html'
    });
}])
.filter("sort", function () {                       
            return function (items, key, value) {
                angular.forEach(items,function(item){
                    
                })
//                console.log(typeof(items[0][key]));
                
                if(typeof(items[0][key])=="number")
                {return items;
                }
                else{
            items.sort(function (a, b) {
                return a[key].localeCompare(b[key]);
            });
            return items;}
            }
        });

qemApp.controller('defaultCtrl',['$rootScope','$scope','$http','$state','$filter',function($rootScope,$scope,$http,$state,$filter){ //通知列表	
	$scope.detailShow=false;
	$scope.notices={};
	$scope.notice={};
	$scope.fileList={};
	$http({
		 method:'GET',
			url:"/tms/qem/showNotice"
	 }).success(function(data) {
		 if(data!=null && data.notices!=null){
			 $scope.notices=data.notices;	
			 $scope.ifNotEnding= data.ifNotEnding;
//				 console.log('parent',$scope.parentTypes)				
		 }else {				
			 $scope.notices={}
		 }			 
	 });
	$scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
	$scope.orderBy = function(col){
//		console.info(col,$scope.order);
		if($scope.order==col)
			$scope.order = "-"+col;
		else 
			$scope.order = col;		
    }
	$scope.workTypeText = function(workType){
    	var WORKTYPES = {
    			"REQ": "项目申请",
    			"CHE": "项目检查"
    		};
    	return WORKTYPES[workType];
    }
	$scope.goDetail = function(index){
		$scope.notice = $scope.notices[index];
		$scope.detailShow=true;
		$scope.fileList={};
		var publishDate=$filter('date')($scope.notice.publishDate,'yyyyMMdd');
		$http({
			 method:'GET',
				url:"/tms/qem/getNoticeAtt",
				params:{
					publishDate:publishDate
				}
		 }).success(function(data) {
			 $scope.fileList=data.fileList;		
		 });
	}
	$scope.goBack = function(){		
		$scope.detailShow=false;
	}
	$scope.createAble = function(item){
//		console.log(item);
//		console.log($scope.ifNotEnding);
		var today=new Date();
		var start= new Date(item.start);
		var end =new Date(item.end);
		var noEndingProject=false;	
		if($scope.ifNotEnding)
			noEndingProject=(item.noTowLine=='1' && $scope.ifNotEnding.t0) ||
							(item.noTowLine=='2' && $scope.ifNotEnding.t1);
		return (item.workType=="REQ") && (start<=today) && (today<=end) && !noEndingProject
	}
	$scope.createProject=function(noticeId){
		$rootScope.noticeId=noticeId;
    	$state.go("qemRequest");
    }
}]);

qemApp.controller('parentCtrl',['$rootScope','$scope','$http',function($rootScope,$scope,$http){
	$scope.updateView={};
	$scope.taskView={};
	$scope.fileList ={};
	$scope.declarations ={};
	$scope.remind=function(){
		$rootScope.isRemind=true;
	}
	$scope.unRemind=function(){
		$rootScope.isRemind=false;
		$rootScope.isTaskRemind=0;
	}
	$scope.taskRemind=function(type){
		$rootScope.isTaskRemind=type;
	}
	$scope.getUpdateView=function(data){
		$scope.taskView=data.task;
		$scope.updateView=data.updateView;
		$scope.fileList =data.fileList;
		$scope.declarations = data.declarations;
		
	}
	
	
}]);