pAdminApp.controller('ExpertGroupCtrl',['$scope','$http','$modalInstance',function($scope,$http,$modalInstance){ //依赖于modalInstance
	$scope.expertList={}
	$scope.trial={}
	$scope.condition={}
	$http({
		 method:'GET',
			url:"/tms/qemProjectAdmin/listExperts"
	 }).success(function(data) {
		 if(data!=null){
			 $scope.expertList=data.experts;	
		 }			
	 });
    var newExperts=[];
    $scope.ok = function(){
    	var experts={ids:"",names:""}
		angular.forEach($scope.expertList,function(data){
			if(data.selected) {
				experts.ids+=";"+data.id;
				experts.names+=data.name+"、";
			}
		});
    	experts.names="【"+experts.names.substr(0,experts.names.length-1)+"】";
    	$modalInstance.close(experts); //关闭并返回当前选项
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel'); // 退出
    }
    $scope.doselectAll= function(){			
		angular.forEach($scope.expertList,function(data){	
			data.selected=$scope.trial.selectAll;								
		});
	}
}]);