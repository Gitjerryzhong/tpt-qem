qemApp.controller('publicCtrl',['$scope','$http',function($scope,$http){ //立项公示
	$scope.qemPublic={};
	$scope.bn="";
	$scope.bns={};
	$scope.order='projectName';
	
	$http({
		 method:'GET',
			url:"/tms/qem/getBns"				
	 }).success(function(data) {
		 if(data!=null ){		
			 $scope.bns=data.bns;	
//				 console.info($scope.bns)
			 $scope.bn=$scope.bns[0]
		 }
	 });  
    	 
	$scope.getPublics=function(){
		$http({
			 method:'GET',
				url:"/tms/qem/qemPublic",
				params:{
					bn:$scope.bn
				}
		 }).success(function(data) {
			 if(data!=null){			 
				 $scope.qemPublic= data.qemPublic;
//				 console.info("",$scope.qemPublic);
			 }
//			var data = [{name:'脚本',value:2},{name:'中文',value:0},{name:'拼音',value:1},{name:'安卓',value:1}];
//			console.log( $scope.qemPublic);
//			 $scope.qemPublic.sort( function(a,b){
//			return a.projectName.localeCompare(b.projectName);
//			});
//			console.log(data);
		 });
	}
	$scope.statusText = function(status){
    	var REVIEWSTATUS = {    			
    			"4": "立项",
    			"5": "不予立项",
    			"6": "立项需修改"
    		};
    	return REVIEWSTATUS[status];
    }
	$scope.levelText = function(level){
    	var LEVEL = {
    			"1": "校级",
    			"2": "省级",
    			"3": "国家级"    			
    		};
    	return LEVEL[level];
    }
	$scope.orderBy = function(col){		
//		console.info(col,$scope.order);		
		if($scope.order==col){
			$scope.qemPublic.sort( function(b,a){
				if(typeof(a[col])=="number") 
					return a[col]>b[col];
				return a[col].localeCompare(b[col]);
				});
			$scope.order ="";
		}	
		else {
			$scope.order = col;				
			 $scope.qemPublic.sort( function(a,b){
				 if(typeof(a[col])=="number") 
						return a[col]>b[col];
				 return a[col].localeCompare(b[col]);
			});
		}
			
    }
	
}]);