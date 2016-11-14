adminApp.controller('NoticeCtrl',['$rootScope','$scope','$http','$filter',function($rootScope,$scope,$http,$filter){ //通知管理
	$scope.editAble=false;
	$scope.notices={};
	Date.prototype.toJSON = function() { //重载toJSON方法，以解决中国时区转换不准的情况
		return $filter('date')(this,'yyyy-MM-dd');
//		return  this.getFullYear() + '-' + (this.getMonth()+1 >9 )?"0":"" + (this.getMonth()+1) + '-' + this.getDate()>9?"0":"" +this.getDate();
		};	
    $scope.open = function($event,id){   //日期下拉选择
        $event.preventDefault();
        $event.stopPropagation();
        if(id==0)
        	$scope.startid = true;
        else
        	$scope.endid = true;
    }
    $scope.disabled = function(date , mode){ 
//        return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6))
    	return false;
    }
    $scope.newNotice = function(){
    	$scope.notice={}
    	var myDate= new Date();
		$scope.notice.start=myDate;
		$scope.notice.end=myDate;
		$scope.editAble=true;
    }
    $scope.saveNotice = function(){ //保存通知    	
    	$scope.editAble=false;
    	$http({
			method:'POST',
			url:"/tms/tptAdmin/saveNotice",
			data:JSON.stringify($scope.notice),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {			  
			  $rootScope.errors=data.errors;
			  $scope.notices=data.notices;
			}).error(function(data){
			});
    }
    $scope.showNotice = function(){ //显示通知
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/showNotice"
		 }).success(function(data) {
			 if(data!=null && data.notice!=null){
				 $scope.notice=data.notice;		
				 $scope.notice.start=new Date(data.notice.start);
				 $scope.notice.end=new Date(data.notice.end);
			 }else {				
				 $scope.notice={}	
				 var myDate= new Date();
				$scope.notice.start=myDate;
				$scope.notice.end=myDate;
//				$scope.notice.bn=myDate.getFullYear();
			 }
		 });
    };
    $scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
    $scope.goDetail = function(index){
    	$scope.notice={};
    	var temp=$scope.notices[index];
    	$scope.notice.id = temp.id;
    	$scope.notice.title= temp.title;
    	$scope.notice.content= temp.content;
    	$scope.notice.start = temp.start;
    	$scope.notice.end = temp.end;
    	$scope.editAble=true;
    	console.log($scope.notice);
    }
    $scope.noticeList = function(){
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/noticeList"
		 }).success(function(data) {
			 if(data!=null && data.notices!=null){
				 $scope.notices=data.notices;	
//					 console.log('parent',$scope.parentTypes)				
			 }else {				
				 $scope.notices={}
			 }
		 });
    }
    $scope.delNotice = function(id){
    	$http({
			 method:'GET',
				url:"/tms/tptAdmin/deleteNotice",
				params:{
					id: id
				}
		 }).success(function(data) {
			 if(data!=null && data.notices!=null){
				 $scope.notices=data.notices;	
			 }else {				
				 $scope.notices={}
			 }
		 });
    }
    $scope.noticeList();
}]);