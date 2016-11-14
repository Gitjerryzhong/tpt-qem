adminApp.controller('ExpertsCtrl',['$scope','$http','$modal',function($scope,$http,$modal){ 
	$scope.teachers={}
	$scope.condition={}
	$scope.majorTypes={}
	$scope.expertList={}
	$scope.showList=false;
	$scope.loadTeacher=function(){
		$http({
			 method:'GET',
				url:"/tms/qemAdmin/loadTeachers"
		 }).success(function(data) {
			 if(data!=null){
				 $scope.teachers=data.teachers;	
				 $scope.majorTypes = data.majorTypes;
				 $scope.majorTypes.push('ALL');
			 }			
		 });
	}
	$scope.conditions = function(item){
		var result=true;		
		result=result && (item.departmentName==$scope.condition.department);
		if($scope.condition.expertName)
			result=result && (item.name.indexOf($scope.condition.expertName)!=-1);
		return result;
	}
	$scope.loadTeacher();
	$scope.add= function(){
		var options=[]
		angular.forEach($scope.teachers,function(data){
			if(data.selected) options.push(data);
		});
		 var modalInstance=$modal.open({
	            templateUrl : 'expert_majorType.html',  //创建文件上传视图
	            controller : 'MajorTypeCtrl',
	            backdrop : "static",
	            resolve : {
                 experts : function(){
                     return options;
                 },
		 		 majorTypes: function(){
		 			 return $scope.majorTypes;
		 		 }
		 		
             }
	        }) 
	}

	$scope.list= function(){
		$http({
			 method:'GET',
				url:"/tms/qemAdmin/listExperts"
		 }).success(function(data) {
			 if(data!=null){
				 $scope.expertList=data.experts;	
				 $scope.showList=true;
			 }			
		 });
	}
	$scope.back = function(){
		$scope.showList=false;
	}
	$scope.deleteItem = function(item){
		var r=confirm("确定要删除“"+item.name+"”吗？");
		if (r==true)
		 {
			$http({
				 method:'GET',
					url:"/tms/qemAdmin/deleteExpert",
					params:{
						expertId: item.id
		 			}
			 }).success(function(data) {
				 if(data!=null){
					 $scope.expertList=data.experts;	
					 $scope.showList=true;
				 }			
			 });
		 }		
	}
}]);
adminApp.controller('MajorTypeCtrl',['$rootScope','$scope','$http','$modalInstance','experts','majorTypes',function($rootScope,$scope,$http,$modalInstance,experts,majorTypes){ //依赖于modalInstance
    $scope.expertsSelected = experts;
//    console.log(majorTypes);
    $scope.majorTypes = majorTypes;
    $scope.majortypeSelected=[];
    var newExperts=[];
    var types="";
    $scope.ok = function(){
    	for(var i=0;i<$scope.majortypeSelected.length;i++){
    		if($scope.majorTypes[i]=='ALL'){
    			types="ALL";
    			break;
    		}
    		if($scope.majortypeSelected[i])types=types+$scope.majorTypes[i]+";";
    	}
    	angular.forEach($scope.expertsSelected,function(data){
    		var expert={};
    		expert.id=data.id;
    		expert.name = data.name;
    		expert.types =types;
    		newExperts.push(expert);
		});
    	$http({
			method:'POST',
			url:"/tms/qemAdmin/addExperts",
			data:JSON.stringify({experts:newExperts}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			alert("数据添加成功！");
		  }).error(function(data){
			  alert("数据添加失败！");
		  });
    	$modalInstance.close()
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel'); // 退出
    }
}]);