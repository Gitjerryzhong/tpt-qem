qemApp.controller('updateViewCtrl',['$scope','$http','$location','$filter','aboutUpdate',function($scope,$http,$location,$filter,aboutUpdate){ //任务列表
	var items=$scope.updateView.updateTypes.split(";");
	$scope.typesText ="";
	angular.forEach(items,function(item){
		if(item){
			$scope.typesText +=aboutUpdate.updateTypeText[item]+"；"
//			console.log(item);
		}
			
	})
	$scope.members=[];
	if($scope.taskView.memberstr!=null){
		 var items=$scope.taskView.memberstr.split(";");				 
		 angular.forEach(items,function(item){
			 if(item!=null && item!="") {
				 var member ={'name':item};
				 $scope.members.push(member);
			 }
		 });		 
	 }
	$scope.hasUpdateType = function(type){
		return $scope.updateView.updateTypes.indexOf(type+";") !=-1;
	}
	$scope.getFileName = function(item){
		return item.slice(item.lastIndexOf('___') + 3);
	}
}]);