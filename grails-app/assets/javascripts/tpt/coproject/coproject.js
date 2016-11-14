coProjectApp.controller('CoProjectCtrl',['$rootScope','$scope','$http','$filter','$state',function($rootScope,$scope,$http,$filter,$state){ //国外大学管理
	$scope.coTypes={}
	$scope.coCountrys={}
	$rootScope.projectDetail ={}
	$scope.coProject={}
	
	$scope.getBase = function(){
		$http({
			 method:'GET',
				url:"/tms/tptCoPrjAdmin/getBase"
		 }).success(function(data) {
			 if(data!=null){
				 $scope.coTypes=data.coTypes;
				 $scope.coCountrys=data.coCountrys;
			 }else {				
				 $scope.coTypes={}
				 $scope.coCountrys={}
			 }
		 });
	}
	$scope.getBase();
	
	$scope.save = function(){
		$http({
			method:'POST',
			url:"/tms/tptCoPrjAdmin/saveProject",
			data:JSON.stringify($scope.coProject),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  console.log(data.tptCoProject);
			  $rootScope.projectDetail = data.tptCoProject;
			}).error(function(data){
				
			});
		$state.go('copro-detail');
	}
//	2016-7-7 起始年份和有效年份只跟具体专业有关，不需要在整个协议上记录
//	$scope.yearList=[]
//	$scope.effetiveYears=[]
//	$scope.coProject.beginYear = new Date().getFullYear();
//	var start =2002;
//	var inputId="none";
//	var foreach=angular.forEach;
//	$scope.editYearShow=false;
//	$scope.newYearList = function(startYear){//获得新一页年份列表
//		$scope.yearList=[]
//		start=startYear;
//		for(var i=0;i<12;i++){
//			$scope.yearList.push(start+i);
//		}
//	}
//	$scope.newYearList(2002);
	
//	$scope.editYear = function(id){
//		$scope.newYearList($scope.coProject.beginYear);
//		inputId=id;
//		if(inputId=="beginYear"){ //起始年变化，则清空所有有效年份
//			$scope.effetiveYears=[];
//			$scope.coProject.effeYears="";
//		}
//		var obj=window.document.getElementById(id);
//		var obj2=window.document.getElementById("effeYear");
//		obj2.style.top = obj.offsetTop+36+"px";
//		obj2.style.left = obj.offsetLeft+10+"px";
//		$scope.editYearShow=true;
//	}
//	$scope.selectYear=function(item){
//		if(inputId=="beginYear"){
//			$scope.coProject.beginYear=item;
//		}else if(inputId=="editYear"){
//			//如果数组中没有则添加
//			var result=false;
//			var temp=[];
//			foreach($scope.effetiveYears,function(data){
//				if(data!=item) temp.push(data);
//				result = result || data==item;
//			})
//			if(!result)
//				$scope.effetiveYears.push(item);
//			else $scope.effetiveYears=temp;
////			console.log($scope.effetiveYears);
//		}
//	}
//	$scope.yearBtnClass = function(item){
//		if(inputId=="beginYear"){
//			return $scope.coProject.beginYear==item;
//		}else if(inputId=="editYear"){
//			var result=false;
//			foreach($scope.effetiveYears,function(data){
//				result = result || data==item;
//			})
//			return result;
//		}
//	}
//	$scope.nextPage = function(){
//		$scope.newYearList(start+12);
//	}
//	$scope.prePage = function(){
//		$scope.newYearList(start-12);
//	}
//	$scope.yearConfirm = function(){
//		if(inputId=="beginYear"){
//			$scope.editYearShow=false;
//		}else if(inputId=="editYear"){
//			var years= $scope.effetiveYears;
//			var result="";
//			var beginYear=$scope.coProject.beginYear;
//			if(years.length>0){
//				years.sort();
////				console.log(years);
//				var a1=a0=years[0];
//				result = a0.toString();
//				var sn = 1<<(a0-beginYear);
//				for(var i=1;i<years.length;i++){
//					if(years[i]-a1>1) {
//						if(a0==a1)result+="，";  //如果还在起步点
//						else{
//							result = result +"至"+ a1+"，"	//标明截至点						
//						} 
//						if(i < years.length){
//							a1 = a0 = years[i];
//							result += a0;
//						}
//					}else if(years[i]-a1==1){ //如果是连续年份
//						a1=years[i];
//					}
//					sn = sn | 1<<(years[i]-beginYear) //生成有效年份2进制串
//				}
////				console.log(sn);
//				if(a1!=a0) result = result +"至"+ a1 ; //如果最后一个点不是起步点
//				$scope.coProject.effeYears = sn;	
//				$scope.coProject.effeYearStr=result;
//			}	
//			$scope.editYearShow=false;
//		}
//	}
	
}]);
