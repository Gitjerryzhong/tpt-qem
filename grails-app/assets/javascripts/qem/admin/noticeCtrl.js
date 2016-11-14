adminApp.controller('NoticeCtrl',['$rootScope','$scope','$http','$filter','$location','FileUploader',function($rootScope,$scope,$http,$filter,$location,FileUploader){ //通知管理
	$scope.editAble=false;
	$scope.notices={};
	var fileList=$scope.fileList=[];
	Date.prototype.toJSON = function() { //重载toJSON方法，以解决中国时区转换不准的情况
		return  $filter('date')(this,'yyyy-MM-dd');//this.getFullYear() + '-' + (this.getMonth()+1) + '-' + this.getDate();
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
        return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6))
    }
    $scope.newNotice = function(){
    	$scope.notice={}
    	$scope.notice.workType="REQ";
    	$scope.notice.noTowLine="0";
    	var myDate= new Date();
		$scope.notice.start=myDate;
		$scope.notice.end=myDate;
		$scope.editAble=true;
    }
    $scope.saveNotice = function(){ //保存通知    	
//    	alert(JSON.stringify($scope.notice))
    	$scope.editAble=false;
    	$http({
			method:'POST',
			url:"/tms/qemAdmin/saveNotice",
			data:JSON.stringify(
					{id: $scope.notice.id,
						title: $scope.notice.title,
						content:$scope.notice.content,
						workType:$scope.notice.workType,
						noTowLine:$scope.notice.noTowLine,
						start:$scope.notice.start,
						end:$scope.notice.end}),
			headers:{ 'Content-Type': 'application/json' } 
		  }).success(function(data) {
			  $rootScope.errors=data.errors;
			  $scope.notices=data.notices;
			}).error(function(data){
			});
    }
    $scope.showNotice = function(){ //显示通知
    	$scope.notice={}
    	$http({
			 method:'GET',
				url:"/tms/qemAdmin/showNotice"
		 }).success(function(data) {
			 if(data!=null && data.notice!=null){
//				 $scope.notice= data.notice;
				 var temp=data.notice;	
				 $scope.notice.title=temp.title;
				 $scope.notice.id=temp.id;
				 $scope.notice.content=temp.content;
				 $scope.notice.start=new Date(temp.start);
				 $scope.notice.end=new Date(temp.end);
				 $scope.notice.workType=temp.workType;
				 $scope.notice.noTowLine=temp.noTowLine;
			 }else {				
				 $scope.notice={}	
				 var myDate= new Date();
				$scope.notice.start=myDate;
				$scope.notice.end=myDate;
			 }
		 });
    };
//    $scope.showNotice();
    $scope.noticeList = function(){
    	$http({
			 method:'GET',
				url:"/tms/qemAdmin/noticeList"
		 }).success(function(data) {
			 if(data!=null && data.notices!=null){
				 $scope.notices=data.notices;	
//					 console.log('parent',$scope.parentTypes)				
			 }else {				
				 $scope.notices={}
			 }
		 });
    }
    $scope.workTypeText = function(workType){
    	var WORKTYPES = {
    			"REQ": "项目申请",
    			"CHE": "项目检查"
    		};
    	return WORKTYPES[workType];
    }
    $scope.dateFormat = function(jsondate){
    	return new Date(jsondate);
    }
    $scope.deleteItem = function(item){
    	$scope.deleteItem = function(item){
    		var r=confirm("确定要删除“"+item.title+"”吗？");
    		if (r==true)
    		 {
    			$http({
    				 method:'GET',
    					url:"/tms/qemAdmin/deleteNotice",
    					params:{
    						noticeId: item.id
    		 			}
    			 }).success(function(data) {
    				 if(data!=null){
    					 $scope.notices=data.notices;
    				 }			
    			 });
    		 }		
    	}
    }
    $scope.goDetail = function(index){
    	$scope.notice= $scope.notices[index];
    	$scope.editAble=true;
    	console.log($scope.notice);
    }
    var uploader = $scope.uploader = new FileUploader({
        url: 'uploadNoticePack',
    });
    uploader.onBeforeUploadItem = function(item) {
    	var dir;
    	if($scope.notice.publishDate) 
    		dir=$filter('date')($scope.notice.publishDate,'yyyyMMdd');
    	else dir=$filter('date')(new Date(),'yyyyMMdd')
        formData = [{
            dir: dir
        }];
        Array.prototype.push.apply(item.formData, formData);
    };
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        $scope.fileList.push(response.filename);        
    };
    $scope.noticeList();
}]);