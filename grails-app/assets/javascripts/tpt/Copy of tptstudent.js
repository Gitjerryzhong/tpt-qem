var tptApp = angular.module('tptApp', ['ui.router','ui.bootstrap','angularFileUpload']);
tptApp.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/newRequest');
    $stateProvider
        .state('newRequest', {
            url: '/newRequest',
            templateUrl: 'tpt-newrequest.html'
        })    
    .state('flow', {
        url: '/requestFlow',
        templateUrl: 'tpt-flow.html'
    })
    .state('myMessage', {
        url: '/myMessage',
        templateUrl: 'tpt-message.html'
    });
});
tptApp.controller('NewCtrl',function($rootScope,$scope,$http,$location){ //申请
	$scope.inTime=false;
	$scope.agreeShow=false;	
	var status=-1;
    $scope.showNotice = function(){ //显示通知
    	$http({
			 method:'GET',
				url:"/tms/tpt/showNotice"
		 }).success(function(data) {
			 if(data!=null && data.notice!=null){
				 $scope.notice=data.notice;	
				 $scope.inTime=data.inTime;	
				 $rootScope.bn=$scope.notice.bn;
			 }else {				
				 $scope.notice={}	;
				 $scope.notice.content="您好！目前不是学位申请期间，请留意通知，谢谢！";
			 }
		 });
//    	$scope.buttonName="继续>>>";
    };
    $scope.showRequest = function(){ //显示申请单
    	$http({
			 method:'GET',
				url:"/tms/tpt/showRequest"
		 }).success(function(data) {
			 if(data!=null && data.form!=null){				 
				 status= data.form.status;
				  switch(status){ //根据状态跳转页面
					case 0:		
					case 1:
						 $location.url('/requestFlow');
						break;
					default:
						$location.url('/newRequest');
					}
				 $rootScope.form=data.form;	
			 }else {				
				 $scope.showNotice();
			 }
		 });
    };
    $scope.showRequest();  
    
    $scope.next= function(){
    	$scope.agreeShow=true;    	
    	$scope.inTime=false;
    	$scope.notice.content="    学生在申请2016届北师大珠海分校本科毕业证与学位证之前,请务必仔细阅读本声明。\n"+
"    本人承诺此次申请为自愿行为，上传的所有资料（联系电话、邮件、个人照片、国外学位证书、成绩单）均真实、有效、合法，在申请期间自行查看学院公告通知，在学校规定的时间内提交相关材料。如有特殊情况中途退出，务必通知学部教务老师。如因申请材料伪造、无效或长期无法联系导致未能获得本校证书，学校不承担任何相关责任。";
    }
});
tptApp.controller('modalFlow',function($rootScope,$scope,$http,$modal,$log){ //
	$scope.bn1=
	$scope.bn2=
	$scope.bn3=
	$scope.bn4="btn btn-default";
	$scope.index=1;
	$scope.initial=function(){
		switch($scope.index){
		case 1:
			$scope.bn1="btn btn-danger";
			$scope.bn2=$scope.bn3=$scope.bn4="btn btn-default";
			break;
		case 2:
			$scope.bn2="btn btn-danger";
			$scope.bn1=$scope.bn3=$scope.bn4="btn btn-default";
			break;
		case 3:
			$scope.bn3="btn btn-danger";
			$scope.bn2=$scope.bn1=$scope.bn4="btn btn-default";
			break;
		case 4:
			$scope.bn4="btn btn-danger";
			$scope.bn2=$scope.bn3=$scope.bn1="btn btn-default";
			break;
		}
	}
	$scope.initial();
	 $scope.open1 = function(){
		 
		 $modal.open({
	            templateUrl : 'contact.html',  //创建紧急联系人录入视图
	            controller : 'ContactCtrl',// 初始化模态范围
	            backdrop : "static"
	        }) 
	 };
    $scope.open2 = function(){  //打开模态     
		var modalInstance1 = $modal.open({
	            templateUrl : 'photo.html',  //指向上面创建的视图
	            controller : 'PhotoCtrl',// 初始化模态范围
	            backdrop : "static"
	     })  
 
    };
    $scope.open3 = function(){

		 $modal.open({
	            templateUrl : 'recognition.html',  //创建负责人资料录入视图
	            controller : 'RecognitionCtrl',// 初始化模态范围
	            
	        }) 
	 }
    
    
});
tptApp.controller('ContactCtrl',function($rootScope,$scope,$http,$modalInstance){ //依赖于modalInstance
	$scope.contact={}
    $scope.ok = function(){  	
		$scope.contact.bn=$rootScope.bn;
    	$http({
			method:'POST',
			url:"/tms/tpt/saveContact",
			data:JSON.stringify($scope.contact),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $rootScope.requestForm = data.tptForm;
			  $rootScope.errors = data.errors
			}).error(function(data){
				$rootScope.errors=data;
			});
        $modalInstance.close(); //关闭并返回当前选项
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel'); // 退出
    }
})