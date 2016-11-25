app.controller('updateViewCtrl',['$scope','$http','$location','$filter','aboutUpdate',function($scope,$http,$location,$filter,aboutUpdate){ //任务列表
	$scope.trial={};
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
	
	$scope.okT = function(act,formId){
		$http({
			method:'POST',
			url:"/tms/qemUpdateCollegeCheck/audit",
			data:JSON.stringify({
						form_id:formId,
						check:act,
						content:$scope.trial.content}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $location.url("/tab1");
		  })
	}
	
}]);