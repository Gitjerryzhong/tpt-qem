var app = angular.module('cUpdateCheckApp', ['ui.router','ui.bootstrap','angularFileUpload','mine.filter','mine.constant']);
app.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
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
    .state('updateView', {
        url: '/updateView',
        templateUrl: 'qem-updateView.html'
    })
    .state('updateRequest', {
        url: '/updateRequest',
        templateUrl: 'qem-updateRequest.html'
    })
    .state('editRequest', {
        url: '/editRequest',
        templateUrl: 'qem-editRequest.html'
    });
}]);
app.filter("groups", function () {                       
    return function (items) {
    	var groups =[['未提交或其他','未知','未知','已审'], ['未审','未知','已审','未审'],['已审','已审','已审','未知']]; 
    	angular.forEach(items, function (item) {
    		item.groups=groups[item.flow][item.auditStatus];
    	})
    return items;    
    }
});
app.controller('defaultCtrl',['$scope','$http','$filter','$state',function($scope,$http,$filter$state,$state){ //项目信息	
	$scope.tabs =[{title:'变更审批',active:false,link:'tab1'},{title:'负责人变更申请',active:false,link:'tab2'},{title:'负责人变更申请列表',active:false,link:'tab3'}]
	$scope.updateView={};
	$scope.taskView={};
	$scope.fileList ={};
	$scope.declarations ={};
	$scope.teachers ={};
	$scope.qemTypes ={};
	 $scope.go=function(link){
    	$state.go(link);
    }
	$scope.getUpdateView=function(data){
		$scope.taskView=data.task;
		$scope.taskView.teacherName = data.taskLeader;
		$scope.updateView=data.updateView;
		$scope.updateView.teacherName = data.newLeader;
		$scope.fileList =data.fileList;
		$scope.declarations = data.declarations;

	}
	$scope.getTaskView=function(data){
		console.log(data);
		$scope.taskView= data.task;
 		$scope.qemTypes = data.qemTypes;
 		$scope.declarations= data.fileList;
 		$scope.teachers=data.teachers;
	}
	$scope.getFileName = function(item){
		return item.slice(item.lastIndexOf('___') + 3);
	}
}]);