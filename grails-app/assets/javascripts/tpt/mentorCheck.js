var checkApp = angular.module('checkApp', ['ui.router','ui.bootstrap']);
checkApp.config(['$stateProvider','$urlRouterProvider','$httpProvider', function($stateProvider, $urlRouterProvider,$httpProvider) {
//	禁止IE11缓存数据
	if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
	$urlRouterProvider.otherwise('/trial')
    $stateProvider
    .state('trial', {
            url: '/trial',
            templateUrl: 'tpt-trial.html'           
        }) ;
}]);
checkApp.controller('TrialCtrl',['$rootScope','$scope','$http','$modal','$location','$filter',function($rootScope,$scope,$http,$modal,$location,$filter){ 
	var vm = $scope;
	$scope.offset =0;
	$scope.max=15;
	var index=1;
	$scope.checkStatus=4;
	$scope.requests={}
	$scope.contact={}
	$scope.imgs={}
	$scope.audits={}
	$scope.trial={}
	$rootScope.pager={}
		
	vm.showRequests = function(){ 
    	$http({
			 method:'GET',
				url:"/tms/tptMentorCheck/showRequests",
				params:{
					type : $scope.checkStatus,
					offset: $scope.offset,
					max: $scope.max
				}
		 }).success(function(data) {
			 if(data!=null ){		
				 $scope.requests=data.requests;	
//				 console.info('requests',data);
				 $rootScope.pager = data.pager;	
			 }
		 });  
    	 
    };
    $scope.disabled_prev =function(){
    	if($scope.offset<$scope.max){
    		return 'disabled';
    	}else return '';
    }
    $scope.disabled_next =function(){
    	if($rootScope.pager.total==null ){return 'disabled';}
    	else if($scope.offset+$scope.max> $rootScope.pager.total[$scope.checkStatus]){
    		return 'disabled';
    	}else return '';
    }
    
   
    $scope.next = function(){
    	$scope.offset =$scope.offset+$scope.max;
    	$scope.showRequests()  	
    };
    $scope.previous = function(){
    	$scope.offset =$scope.offset-$scope.max;
    	$scope.showRequests()

    	
    };
    
    $scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
    $scope.actionText = function(action){
    	var ACTIONS = {
    			"10": "提交申请",
    			"11": "撤销申请",
    			"12": "修改申请",
    			"20": "审核通过",
    			"21": "审核不通过",
    			"30": "论文审批通过",
    			"31": "论文审批不通过",
    			"40": "关闭申请",
    			"41": "回收申请",
    			"51": "论文上传",
    		};
    	return ACTIONS[action];
    }
    $scope.statusText = function(status){
    	var STATUS = {
    			"0": "未提交",
    			"1": "申请中",
    			"2": "已通过审核照片、证书、成绩",
    			"3": "初审不通过",
    			"4": "论文审核中",
    			"5": "论文审核通过",
    			"6": "已关闭",  
    			"7": "论文审批不通过",
    		};
    	return STATUS[status];
    }
    $scope.showRequests();  
    $scope.goTrial = function(type){
    	$scope.checkStatus=type;
    	$scope.showRequests();
    	$location.url('/trial');
    }
   
    $scope.auditAble=function(){
    	return $scope.checkStatus==1 || $scope.checkStatus == 4;
    }
    $scope.checkSelected = function(){
    	return $filter('filter')($scope.requests,{'selected':true}).length>0;
    }
    $scope.open = function(item){
//    	var requestSelected=$filter('filter')($scope.requests,{'selected':true});
		 var modalInstance=$modal.open({
	            templateUrl : 'tpt-audit.html',  //创建负责人资料录入视图
	            controller : 'auditCtrl',// 初始化模态范围
	            backdrop : "static",
//	            size : "lg",
	            resolve : {
	            	myRequest : function(){
                        		return item;
	            			},
	            	paper : function(){
                				return $rootScope.pager;
        					}
                }
	        }) ;
		 modalInstance.result.then(function(requests){  
//			 alert("here");
			 vm.showRequests(); 
//			$scope.requests=requests;
//			$rootScope.paper = pager;
        },function(){      });
	        
	 }
}]);
checkApp.controller('auditCtrl',['$scope','$http','$modalInstance','$filter','myRequest','paper',function($scope,$http,$modalInstance,$filter,myRequest,paper){
	$scope.contact=myRequest;
	$scope.trial={}
	$scope.ok = function(act){ 
		$scope.trial.form_id=myRequest.id;
		$scope.trial.nextId=paper.offset;
		$scope.trial.prevId = paper.max;
		$scope.trial.check=act;
		$http({
			method:'POST',
			url:"/tms/tptMentorCheck/auditSave",
			data:JSON.stringify($scope.trial),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $modalInstance.close(myRequest);
			})
			 
    };
    $scope.cancel = function(){    	
    	$modalInstance.dismiss('cancel'); // 退出
    }
}]);
