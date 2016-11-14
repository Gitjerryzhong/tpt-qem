coProjectApp.controller('CoProDetailCtrl',['$rootScope','$scope','$http','$filter','$state',function($rootScope,$scope,$http,$filter,$state){ //国外大学管理
	$scope.projectItems={}
	$scope.majors ={}
	$scope.projectItem={}
	$scope.yearList=[]
	$scope.effetiveYears=[]
	var now = $scope.projectItem.beginYear = new Date().getFullYear();
	var start =2002;
	var foreach=angular.forEach;
	var inputId="none";
	$scope.editYearShow=false;
//	Get后台projectItem数据
	$scope.getProjectItems = function(){
		$http({
			 method:'GET',
				url:"/tms/tptCoPrjAdmin/getProjectItems",
				params:{
					coProjectId:$rootScope.projectDetail.id
				}
		 }).success(function(data) {
			 if(data!=null){
				 $scope.projectItems=data.projectItems;
				 $scope.majors=data.majors;
			 }else {				
				 $scope.projectItems={}
				 $scope.majors={}
			 }
		 });
	}
	$scope.getProjectItems();
//	获得新一页年份列表
	$scope.newYearList = function(startYear){
		$scope.yearList=[]
		start=startYear;
		for(var i=0;i<12;i++){
			$scope.yearList.push(start+i);
		}
	}
//	避免选择有效年份翻页到起始年份之前
	$scope.preDisabled = function(){
		return inputId=="editYear" && start<=$scope.projectItem.beginYear;
	}
//	编辑、选择年份
	$scope.editYear = function(id){
		$scope.newYearList($scope.projectItem.beginYear);
		inputId=id;
		if(inputId=="beginYear"){ //起始年变化，则清空所有有效年份
			$scope.effetiveYears=[];
			$scope.projectItem.effeYears="";
		}
		var obj=window.document.getElementById(id);
		var obj2=window.document.getElementById("effeYear");
		obj2.style.top = obj.offsetTop+36+"px";
		obj2.style.left = obj.offsetLeft+10+"px";
		$scope.editYearShow=true;
	}
//	点击年份按钮
	$scope.selectYear=function(item){
		if(inputId=="beginYear"){
			$scope.projectItem.beginYear=item;
		}else if(inputId=="editYear"){
			//如果数组中没有则添加
			var result=false;
			var temp=[];
			foreach($scope.effetiveYears,function(data){
				if(data!=item) temp.push(data);
				result = result || data==item;
			})
			if(!result)
				$scope.effetiveYears.push(item);
			else $scope.effetiveYears=temp;
//			console.log($scope.effetiveYears);
		}
	}
//	年份按钮的样式
	$scope.yearBtnClass = function(item){
		if(inputId=="beginYear"){
			return $scope.projectItem.beginYear==item;
		}else if(inputId=="editYear"){
			var result=false;
			foreach($scope.effetiveYears,function(data){
				result = result || data==item;
			})
			return result;
		}
	}
//	禁止无效年份
	$scope.disabledYear = function(item){
		if(inputId=="editYear"){
//			console.log($filter('filter')($scope.majors,{'id':$scope.projectItem.majorsId}));
			return $filter('filter')($scope.majors,{'id':$scope.projectItem.majorsId,'grade':item})[0]?false:true;
		}else {
			return false;
		}
	}
//	年份向后翻页
	$scope.nextPage = function(){
		$scope.newYearList(start+12);
	}
//	年份向前翻页
	$scope.prePage = function(){
		$scope.newYearList(start-12);
	}
	
//	处理$scope.effetiveYears;用文字描述；并生成2进制串
	yearsListToStr = function(years,item){
		var result="";
		var beginYear=item.beginYear;
		if(years.length>0){
			years.sort();
//			console.log(years);
			var a1=a0=years[0];
			result = a0.toString();
			var sn = 1<<(a0-beginYear);
			for(var i=1;i<years.length;i++){
				if(years[i]-a1>1) {
					if(a0==a1)result+="，";  //如果还在起步点
					else{
						result = result +"至"+ a1+"，"	//标明截至点						
					} 
					if(i < years.length){
						a1 = a0 = years[i];
						result += a0;
					}
				}else if(years[i]-a1==1){ //如果是连续年份
					a1=years[i];
				}
				sn = sn | 1<<(years[i]-beginYear) //生成有效年份2进制串
			}
//			console.log(sn);
			if(a1!=a0) result = result +"至"+ a1 ; //如果最后一个点不是起步点
			item.effeYears = sn;	
			item.effeYearStr=result;
		}
	}
	
//	年份选择确认
	$scope.yearConfirm = function(){
		if(inputId=="beginYear"){
			$scope.editYearShow=false;
			$scope.projectItem.effeYears = 0;	
			$scope.projectItem.effeYearStr=null;
		}else if(inputId=="editYear"){
			yearsListToStr($scope.effetiveYears,$scope.projectItem);
			$scope.editYearShow=false;
		}
	}

//	将projectItem POST到后台
	$scope.save = function(){		
		console.log($rootScope.projectDetail);
		$scope.projectItem.projectId = $rootScope.projectDetail.id;
		$http({
			method:'POST',
			url:"/tms/tptCoPrjAdmin/saveProjectItem",
			data:JSON.stringify($scope.projectItem),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $scope.getProjectItems();
			  $scope.projectItem={}
			  $scope.projectItem.beginYear = now;
			}).error(function(data){
				
			});
	}
//	编辑projectItem
	$scope.edit=function(item){
		$scope.projectItem={};
		$scope.effetiveYears=[];
		$scope.effetiveYears.push(item.beginYear);
		var major=$filter('filter')($scope.majors,{'id':item.majorsId})[0];
		console.log(item);
		console.log(major);
		$scope.projectItem.id = item.id;
		$scope.projectItem.departmentName = major.departmentName;
		$scope.projectItem.majorsId=major.id;
		$scope.projectItem.beginYear = item.beginYear;
		$scope.projectItem.effeYears = item.effeYears;
		$scope.projectItem.effeYearStr = item.effeYearStr;
		$scope.projectItem.collegeNameCn = item.collegeNameCn;
		$scope.projectItem.collegeNameEn = item.collegeNameEn;
		$scope.projectItem.coDegreeOrMajor = item.coDegreeOrMajor;
		$scope.projectItem.memo = item.memo;
		var i=1;
		while((item.effeYears>>i) >0){
			if((item.effeYears>>i) & 1) $scope.effetiveYears.push(item.beginYear+i) ;
			i++;
		}
	}
//	check是否可以续期
	$scope.canGoOn = function(item){
		var yes= $filter('filter')($scope.majors,{'id':item.majorsId,'grade':now})[0];
		var hasChecked = (item.effeYears>>(now - item.beginYear)) & 1;
		return yes && !hasChecked;
	}
//	续约更新数据的有效期
	$scope.goOn = function(item){
		item.effeYears =item.effeYears | 1<<( now - item.beginYear);
		$scope.effetiveYears=[];
		$scope.effetiveYears.push(item.beginYear);
		var i=1;
		while((item.effeYears>>i) >0){
			if((item.effeYears>>i) & 1) $scope.effetiveYears.push(item.beginYear+i) ;
			i++;
		}
		yearsListToStr($scope.effetiveYears,item);
		delete item.$$hashKey;
		var isUneffective = item.isUneffective; //删除数据库不需要的属性，但现实又需要，所以先暂存起来
		var majorName = item.majorName;
		delete item.isUneffective; 
		delete item.majorName;
		$http({
			method:'POST',
			url:"/tms/tptCoPrjAdmin/saveProjectItem",
			data:JSON.stringify(item),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			}).error(function(data){
				
			});
		item.isUneffective = isUneffective;
		item.majorName = majorName;
	}
}]);
