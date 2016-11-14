//= require common/mine-filter
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
    });
}]);
//cIndexApp.filter('uniKey',function(){
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
cIndexApp.controller('defaultCtrl',['$scope','$http','$filter','config','$modal','$state',function($scope,$http,$filter,config,$modal,$state){ //项目信息	
	$scope.notices=config.notices
	$scope.tabs =[{title:'校发通知',active:false,link:'tab1'},{title:'已立项项目汇总',active:false,link:'tab2'}]
	$scope.trial={};
	$scope.action={};
	$scope.trial.status='10';
	$scope.action.type='1';
	$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
	$scope.projectStatus=[{"id":"10","name":"在研"},{"id":"20","name":"结题"},{"id":"32","name":"终止"},{"id":"33","name":"中止"}];
	$scope.detailShow=false;

	 $scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
	$scope.workTypeText = function(workType){
    	var WORKTYPES = {
    			"REQ": "项目申请",
    			"CHE": "项目检查"
    		};
    	return WORKTYPES[workType];
    }
	$scope.showNotice =function(item){
    	var modalInstance=$modal.open({
            templateUrl : 'notice_detail.html',  //创建文件上传视图
            controller : 'noticeCtrl',
            resolve : {
                item : function(){
                    return item;
                }
            }
        }) 
    }
	$scope.historyTaskList= function(){
		$scope.detailShow=false;
		$http({
	   		 method:'GET',
	   			url:"/tms/qemCollegeCheck/contractList"
	   	 	}).success(function(data) {
	   		 if(data!=null){			 
	   			 $scope.taskList= data.taskList;
	   		 }	
	   	 });
		$state.go('tab2')
	}
	//全选
    $scope.doselectAllTask= function(){		
		angular.forEach($scope.taskList,function(data){				
			if($scope.listConditions(data)) 
				data.selected=$scope.action.selectAll;					
		});
	}
  //已立项项目汇总筛选
	$scope.listConditions = function(item){
		var result=true;
		result=result && (item.status!=0); //首先过滤掉未立项的
		if($scope.trial.discipline){
			result=result && (item.discipline===$scope.trial.discipline);
		}
		if($scope.trial.level){				
			result=result && (item.projectLevel==$scope.trial.level);
		}
		if($scope.trial.departmentName){				
			result=result && (item.departmentName==$scope.trial.departmentName);
		}
		if($scope.trial.userName){				
			result=result && (item.userName==$scope.trial.userName);
		}
		if($scope.trial.typeName){				
			result=result && (item.type==$scope.trial.typeName);
		}
		if($scope.trial.bn){				
			result=result && (item.bn==$scope.trial.bn);
		}
		if($scope.trial.status){
			result=result && (item.status==$scope.trial.status);
		}
		if($scope.trial.beginYear){
			result=result && (item.beginYear==$scope.trial.beginYear);
		}
		if($scope.trial.expectedEnd){
			result=result && (item.expectedEnd==$scope.trial.expectedEnd);
		}
		return result;
	}
	//排序
	$scope.orderBy = function(col){		
		if($scope.order==col){
			$scope.taskList.sort( function(b,a){
				if(typeof(a[col])=="number") 
					return a[col]>b[col];
				return a[col].localeCompare(b[col]);
				});
			$scope.order ="";
		}	
		else {
			$scope.order = col;				
			 $scope.taskList.sort( function(a,b){
				 if(typeof(a[col])=="number") 
						return a[col]>b[col];
				 return a[col].localeCompare(b[col]);
			});
		}
			
    }
	$scope.levelText = function(level){
    	var LEVEL = {
    			"1": "校级",
    			"2": "省级",
    			"3": "国家级"	    		
    		};
    	return LEVEL[level];
    }
	 //建设情况注释
    $scope.statusTT = function(status){
    	var STATUS = {
    			"10" : "在研",
    			
    			"20" : "结题",
    			"32" : "终止",
    			"33" : "中止"
    		};
    	return STATUS[status];
    }
    $scope.go=function(link){
    	$state.go(link);
    }
  //任务书详情
    $scope.taskDetail=function(id){
    	$scope.detailShow=true;
    	$http({
      		 method:'GET',
      			url:"/tms/qemCollegeCheck/taskDetail",
      			params:{
      				id:id
      			}
      	 	}).success(function(data) {
      		 if(data!=null){			 
      			 $scope.task= data.task;
      			 $scope.task.typeName=data.taskType;
      			 $scope.task.userName=data.userName;
      			 $scope.pagerT = data.pager;
      			 $scope.fileList= data.fileList;
      		 }	
      	 });
    }
}]);
cIndexApp.controller('noticeCtrl',['$scope','$http','$modalInstance','item',function($scope,$http,$modalInstance,item){ //依赖于modalInstance
	$scope.notice=item;
    $scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
	$scope.workTypeText = function(workType){
    	var WORKTYPES = {
    			"REQ": "项目申请",
    			"CHE": "项目检查"
    		};
    	return WORKTYPES[workType];
    }
}]);
