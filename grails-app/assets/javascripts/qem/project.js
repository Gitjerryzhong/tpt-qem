
angular.module('myApp',['ui.bootstrap','angularFileUpload']).controller('modalDemo',function($rootScope,$scope,$http,$modal,$log){ //

	$scope.isHead=false
	$rootScope.project={}
	$rootScope.projects ={}

//    $scope.names=[ {"Name":"Alfreds Futterkiste","City":"Berlin","Country":"Germany"}, {"Name":"Ana Trujillo Emparedados y helados","City":"M茅xico D.F.","Country":"Mexico"}, {"Name":"Antonio Moreno Taquer铆a","City":"M茅xico D.F.","Country":"Mexico"}, {"Name":"Around the Horn","City":"London","Country":"UK"}, {"Name":"B's Beverages","City":"London","Country":"UK"}, {"Name":"Berglunds snabbk枚p","City":"Lule氓","Country":"Sweden"}, {"Name":"Blauer See Delikatessen","City":"Mannheim","Country":"Germany"}, {"Name":"Blondel p猫re et fils","City":"Strasbourg","Country":"France"}, {"Name":"B贸lido Comidas preparadas","City":"Madrid","Country":"Spain"}, {"Name":"Bon app'","City":"Marseille","Country":"France"}, {"Name":"Bottom-Dollar Marketse","City":"Tsawassen","Country":"Canada"}, {"Name":"Cactus Comidas para llevar","City":"Buenos Aires","Country":"Argentina"}, {"Name":"Centro comercial Moctezuma","City":"M茅xico D.F.","Country":"Mexico"}, {"Name":"Chop-suey Chinese","City":"Bern","Country":"Switzerland"}, {"Name":"Com茅rcio Mineiro","City":"S茫o Paulo","Country":"Brazil"} ];
    $http.get("/tms/qemProject/listProject").success(function(data) {
    	$rootScope.projects = data;
	});
	 $scope.delete2= function(index){
		 $rootScope.project.index=index
		 $http({
				method:'POST',
				url:"/tms/qemProject/deleteProject",
				data:$.param($rootScope.project),
				headers:{ 'Content-Type': 'application/x-www-form-urlencoded;charset=GBK','x-camnpr-uid': '1000' }  // set the headers so angular passing info as form data (not request payload)
			  }).success(function(data) {
				  $rootScope.projects = data;
				});
	 }
	 $scope.open1 = function(notice){
		 
//		 $scope.head.noticeId=notice;
		 $modal.open({
	            templateUrl : 'head.html',  //创建负责人资料录入视图
	            controller : 'HeadInstanceCtrl',// 初始化模态范围
	            backdrop : "static"
	        }) 
	 };
    $scope.open2 = function(notice){  //打开模态     
//    	if($scope.types.length>0) alert($scope.types[0]);
//    	$http.get("/tms/qemProject/types?noticeId=" + notice).success(function(data) {
//			$scope.types = data;
//			if($scope.types.length>0) alert($scope.types[0].name);
//		});    	
    	$http.get("/tms/qemProject/isHead").success(function(data) {
    		if(data.isHead=="No"){

    			var headInstance = $modal.open({
    	            templateUrl : 'head.html',  //创建负责人资料录入视图
    	            controller : 'HeadInstanceCtrl',// 初始化模态范围
    	            backdrop : "static"
    	        }) 
    		}else{
    			if(notice!=null){ //编辑指定的项目信息
    				
    				$rootScope.project=$rootScope.projects[notice]
    				$rootScope.project.index=notice //将当前List序号传回controller
//    				alert($scope.project.projectName)
    			}
    			var modalInstance1 = $modal.open({
    	            templateUrl : 'myModelContent.html',  //指向上面创建的视图
    	            controller : 'ModalInstanceCtrl',// 初始化模态范围
    	            backdrop : "static"
    	        })  
    		}
    		});
    	
 
    };
    $scope.open3 = function(notice){
		 
//		 $scope.head.noticeId=notice;
		 $modal.open({
	            templateUrl : 'attachment.html',  //创建负责人资料录入视图
	            controller : 'AttachmentCtrl',// 初始化模态范围
	            resolve : {
	                items : function(){
	                    return $scope.items;
	                }
	            }
	        }) 
	 }
    
    
})
angular.module('myApp').controller('ModalInstanceCtrl',function($rootScope,$scope,$http,$modalInstance){ //依赖于modalInstance
	
    $scope.ok = function(){  
		$http({
			method:'POST',
			url:"/tms/qemProject/createProject",
			data:$.param($rootScope.project),
			headers:{ 'Content-Type': 'application/x-www-form-urlencoded;charset=GBK','x-camnpr-uid': '1000' }  // set the headers so angular passing info as form data (not request payload)
		  }).success(function(data) {
			  $rootScope.projects = data;
			});
		
        $modalInstance.close(); //关闭并返回当前选项
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel'); // 退出
    }
})
angular.module('myApp').controller('HeadInstanceCtrl',function($scope,$http,$modalInstance){ //依赖于modalInstance
	$scope.head={}
	$http.get("/tms/qemProject/getHead").success(function(data) {
	    		if(data!=null){
	    			$scope.head=data;
	    		}
	 });
	$scope.ok = function(){  
		$http({
			method:'POST',
			url:"/tms/qemProject/createHead",
			data:$.param($scope.head),
			headers:{ 'Content-Type': 'application/x-www-form-urlencoded;charset=GBK','x-camnpr-uid': '1000' }  // set the headers so angular passing info as form data (not request payload)
		  }).success(function(data) {
			  if(!data.success){console.log("保存失败！")}
			});
		$modalInstance.close(); //关闭并返回当前选项
    };
    $scope.cancel = function(){
    	$modalInstance.dismiss('cancel'); // 退出
    }
})
angular.module('myApp').controller('AttachmentCtrl',function($scope,$http,$modalInstance,FileUploader){ //依赖于modalInstance
	var uploader = $scope.uploader = new FileUploader({
        url: '../upload'
    });

    // FILTERS

    uploader.filters.push({
        name: 'customFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 10;
        }
    });


    console.info('uploader', uploader);
	$scope.ok = function(){  
		$http({
			method:'POST',
			url:"/tms/qemProject/createHead",
			data:$.param($scope.head),
			headers:{ 'Content-Type': 'application/x-www-form-urlencoded;charset=GBK','x-camnpr-uid': '1000' }  // set the headers so angular passing info as form data (not request payload)
		  }).success(function(data) {
			  if(!data.success){console.log("保存失败！")}
			});
		$modalInstance.close(); //关闭并返回当前选项
    };
    $scope.cancel = function(){
    	$modalInstance.dismiss('cancel'); // 退出
    }
})