taskApp.controller('defaultCtrl',['$scope','$http','FileUploader', '$location','$filter','config',function($scope,$http,FileUploader,$location,$filter,config){ //项目信息	
	$scope.task=config.task
	var uploadStage =0;
	$scope.member={};
	$scope.members=[];
	$scope.uploadQueue=[];
	$scope.clicked=false;
	$scope.editAble=false;
	$scope.notice=config.notice;
	$scope.fileList = config.fileList;
	$scope.stage={}
	$scope.stageNum=0;
	$scope.menuSelected=0;
	$scope.flows=[{"id":"0","statuses":[{"title":"提交合同","runStatusCheck":{"no":["0","203"],"done":["1","2","3","4","5","201","202"]}},
	                              {"title":"学院审核","runStatusCheck":{"no":["0","1","203"],"done":["2","3","4","5","201","202"]}},
	                              {"title":"学校审核","runStatusCheck":{"no":["0","1","4","201","202","203"],"done":["2","3","5"]}}]},
                  {"id":"1","statuses":[{"title":"提交年检报告","runStatusCheck":{"no":["2","1103","9"],"done":["10","11","12","13","14","15","16","1101","1102"]}},
	                              {"title":"学院审核","runStatusCheck":{"no":["2","9","10","16","1103"],"done":["11","12","13","14","15","1101","1102"]}},
	                              {"title":"学校审核","runStatusCheck":{"no":["2","9","10","16","1101","1102","1103"],"done":["11","12","13","14","15"]}}]},
				  {"id":"2","statuses":[{"title":"提交中检报告","runStatusCheck":{"no":["2","19","14","2103"],"done":["20","21","22","23","24","25","26","2101","2102"]}},
				                 {"title":"学院审核","runStatusCheck":{"no":["2","19","14","26","20","2103"],"done":["21","22","23","24","25","2101","2102"]}},
				                 {"title":"学校审核","runStatusCheck":{"no":["2","19","14","26","20","2101","2102","2103"],"done":["21","22","23","24","25"]}}]},
                 {"id":"3","statuses":[{"title":"提交结题报告","runStatusCheck":{"no":["2","29","14","21","3103"],"done":["30","31","32","33","34","35","36","3101","3102"]}},
				                 {"title":"学院审核","runStatusCheck":{"no":["2","29","14","21","26","30","3103"],"done":["32","33","34","35","3101","3102"]}},
				                 {"title":"学校审核","runStatusCheck":{"no":["2","29","14","21","26","30","3101","3102","3103"],"done":["31","32","33","34","35"]}}]}]
		
	$scope.goDefault =function(){
		$scope.menuSelected=0;
		$http({
 			method:'GET',
 			url:"/tms/qemTask/getTask",
 			params:{
 				id: $scope.task.id
 			}
 		  }).success(function(data) {
 			 $scope.fileList = data.fileList;
 			 $scope.task = data.task;
 			groupFiles();
 	     	$location.url('/default');
 			});
	}
	$scope.getFileName = function(src){
		return src.slice(src.lastIndexOf('/') + 1);
	}
	
	groupFiles = function(){
		 $scope.declarations=[];
			 $scope.contracts =[];
//			 $scope.stageAtt =[];
			 angular.forEach($scope.fileList,function(item){
				var dir = item.slice(0,item.lastIndexOf('___'));
				var filename = item.slice(item.lastIndexOf('___') + 3);
				if(dir=='申报书') $scope.declarations.push(filename);
				else if(dir=='合同') $scope.contracts.push(filename);
//				else $scope.stageAtt.push(filename);
			});

	}
	
	$scope.uploadType=uploadType = function(){
   	 var type="合同";
   	 switch(uploadStage){
   	 case 0: type="合同"; break;
   	 case 1: type="年度"; break;
   	 case 2: type="中期"; break;
   	 case 3: type="结题"; break;
   	 }
        return type
    }
//	初始化，将memberstr转换为数组，将文件名列表分类
	if($scope.task.memberstr!=null){
		 var items=$scope.task.memberstr.split(";");				 
		 angular.forEach(items,function(item){
			 if(item!=null && item!="") {
				 var member ={'name':item};
				 $scope.members.push(member);
			 }
		 });		 
	 }
	$location.url('/default');
	groupFiles();
	$scope.changeBegin=function(){
		var mytask=$scope.task;
		 mytask.expectedMid=(mytask.beginYear==null)?null:+parseInt(mytask.beginYear)+parseInt(mytask.cycle/2);
		 mytask.expectedEnd=(mytask.beginYear==null)?null:+parseInt(mytask.beginYear)+parseInt(mytask.cycle);
	}
	
	$scope.span_click=function(){
		$scope.clicked=true;
	}
	$scope.member_click = function(member){
		member.clicked=true;
	}
	$scope.mouseleave = function(member){
		member.clicked=false;
	}
	$scope.oneMore=function(){
		var member ={'name':''};
		member.clicked=true;
		 $scope.members.push(member);
	}
	
   //上传附件
     var uploader = $scope.uploader = new FileUploader({
         url: '/tms/qemTask/uploadFiles'
     });
     
     
     uploader.onBeforeUploadItem = function(item) {
         formData = [{
             taskId:  $scope.task.id,
             isDeclaration: uploadType()
         }];
         Array.prototype.push.apply(item.formData, formData);
     };
     uploader.onAfterAddingFile = function(fileItem) {
    	 if(fileItem.file.size/1024/1024>100) alert("附件不能超过100M！");
    	 else	{
    		 $scope.uploadQueue.push(fileItem);
    		 fileItem.upload();
    	 }
     };
     //上传申报书
     var uploader1 = $scope.uploader1 = new FileUploader({
         url: '/tms/qemTask/uploadFiles'
     });
     uploader1.onBeforeUploadItem = function(item) {
         formData = [{
             taskId:  $scope.task.id,
             isDeclaration: '申报书'
         }];
         Array.prototype.push.apply(item.formData, formData);
     };
     uploader1.onAfterAddingFile = function(fileItem) {
    	 if(fileItem.file.size/1024/1024>100) alert("附件不能超过100M！");
    	 else	{
    		 $scope.uploadQueue.push(fileItem);
    		 fileItem.upload();
    	 }
     };
     $scope.save=function(){
//    	 console.info("task",$scope.task)
    	 $scope.task.memberstr="";
    	 var options=[];
    	 angular.forEach($scope.members,function(item){
    		 if(item.name && item.name!='')	 {
    			 $scope.task.memberstr+=item.name+";";
    			 options.push(item);
    		 }
			  });
    	 $scope.members=options;
    	 $http({
				method:'POST',
				url:"/tms/qemTask/saveTask",
				data:JSON.stringify({
					id: $scope.task.id,
					projectContent: $scope.task.projectContent,
					expectedGain: $scope.task.expectedGain,
					memberstr: $scope.task.memberstr
					}),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
//				  $scope.task.status=10;
				  $scope.fileList = data.fileList;
//				  $scope.task = data.task;
//				  console.log($scope.task);
				  $location.url('/default');
				}).error(function(data){
					alert("信息保存失败！");					
				});
     }
     $scope.levelText = function(level){
     	var LEVEL = {
     			"1": "校级",
     			"2": "省级",
     			"3": "国家级"    			
     		};
     	return LEVEL[level];
     }
     $scope.progress=function(status){
     		return "已废除该方法"
     }
     $scope.statusText = function(status){
 		var STATUS = runStatusText;
     	return STATUS[status];
     }
     $scope.apply=function(){
    	 $scope.save();
     	$http({
 			method:'GET',
 			url:"/tms/qemTask/applyTask",
 			params:{
 				id: $scope.task.id
 			}
 		  }).success(function(data) {
 			 $scope.fileList = data.fileList;
 			  $scope.task = data.task;
// 			 console.log($scope.task);
 			 $scope.editAble=false;
 	     	$location.url('/default');
 			}).error(function(data){
 				alert("无法提交任务书！")				
 			});
     }
     $scope.edit=function(){
    	 uploadStage =0;
     	$scope.editAble=true;
     	$scope.uploadQueue=[]
		  angular.forEach($scope.fileList,function(item){
			  var filename = item.slice(item.lastIndexOf('___') + 3);
			  var dir = item.slice(0,item.lastIndexOf('___'));
			  var file={'file':{'name':filename},'formData':[{'isDeclaration':dir}]};
//			  console.log(file);
			  $scope.uploadQueue.push(file);
		  })
     	$location.url('/edit');
     	
     }
     $scope.cancel=function(){
     	$http({
 			method:'GET',
 			url:"/tms/qemTask/cancelTask",
 			params:{
 				id: $scope.task.id
 			}
 		  }).success(function(data) {
			  $scope.task = data.task;
//			  console.log($scope.task);
 			}).error(function(data){
 				alert("无法撤销申请！")				
 			});
     }
     $scope.isMid=function(){
    	 var currentYear=$filter('date')(new Date(),'yyyy');
    	 if( $scope.task.expectedMid==currentYear ){
    		 return true;
    	 }
    	 return false;
     }
     $scope.isEnd=function(){
    	 var currentYear=$filter('date')(new Date(),'yyyy');
    	 if( $scope.task.expectedEnd==currentYear ){
    		 return true;
    	 }
    	 return false;
     }
     $scope.beforeMid=function(){
    	 var currentYear=$filter('date')(new Date(),'yyyy');
    	 if( $scope.task.expectedMid>currentYear && $scope.task.beginYear< currentYear){
    		 return true;
    	 }
    	 return false;
     }
     $scope.goStage=function(index){
    	 $scope.stageNum=index;    //保存被点击的阶段，如果不是当前阶段，禁止所有的操作按钮
    	 uploadStage = index;
    	 $scope.menuSelected=index;
    	 $http({
  			method:'GET',
  			url:"/tms/qemTask/createStage",
 			params:{
 				taskId: $scope.task.id,
 				stageNum: index
 			}
  		  }).success(function(data) {
  			  $scope.stage=data.stage;
  			  $scope.fileList = data.fileList;
//  			item.file.name
  			  $scope.uploadQueue=[]
  			  angular.forEach($scope.fileList,function(item){
  				  var filename = item.slice(item.lastIndexOf('___') + 3)
  				  var file={'file':{'name':filename}};
  				  $scope.uploadQueue.push(file);
  			  })
//  			  如果未提交，转到填报界面，否则跳到浏览页
  			  
  			  if(!$scope.stage.status || $scope.task.runStatus ==9 ||$scope.task.runStatus ==19 ||$scope.task.runStatus ==29)
  				  $location.url('/stage');
  			  else $location.url('/details');
  			});
     }
     $scope.stageTitle=function(index){
    	 var STAGETITLE = {
     			"1": "年度报告",
     			"2": "中期报告",
     			"3": "结题",
     			"null":"非当前检查阶段！请点绿色按钮！"
     		};
    	 return STAGETITLE[index];
     }
     $scope.open = function($event){   //日期下拉选择
         $event.preventDefault();
         $event.stopPropagation();
         $scope.endid = true;
     }
     
     $scope.saveStage=function(){
//    	 $scope.stage.finishDate
//    	 console.info("stage",$scope.stage)
    	 $http({
				method:'POST',
				url:"/tms/qemTask/saveStage",
				data:JSON.stringify({
					id: $scope.stage.id,
					taskId: $scope.stage.taskId,
					finishDate:  $filter('date')($scope.stage.finishDate,'yyyy-MM-dd'),
					progressText: $scope.stage.progressText,
					unfinishedReson:  $scope.stage.unfinishedReson,
					memo: $scope.stage.memo}),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
//				  $scope.task.status=10;
//				  $scope.task = data.task;
				}).error(function(data){
					alert("信息保存失败！");					
				});
     }
     $scope.applyStage=function(){
//    	 $scope.stage.finishDate
//    	 console.info("stage",$scope.stage)
    	 $http({
				method:'POST',
				url:"/tms/qemTask/applyStage",
				data:JSON.stringify({
					id: $scope.stage.id,
					taskId: $scope.stage.taskId,
					finishDate:  $filter('date')($scope.stage.finishDate,'yyyy-MM-dd'),
					progressText: $scope.stage.progressText,
					unfinishedReson:  $scope.stage.unfinishedReson,
					memo: $scope.stage.memo}),
				headers:{ 'Content-Type': 'application/json' } 
			  }).success(function(data) {
				  $scope.stage=data.stage;
				  $scope.fileList = data.fileList;
				  $scope.task.runStatus = data.runStatus;
				  $location.url('/details');
				}).error(function(data){
					alert("信息保存失败！");					
				});
     }
     $scope.cancelStage=function(){
      	$http({
  			method:'GET',
  			url:"/tms/qemTask/cancelStage",
  			params:{
  				id: $scope.stage.id,
				taskId: $scope.stage.taskId
  			}
  		  }).success(function(data) {
  			  $scope.stage=data.stage;
			  $scope.fileList = data.fileList;
			  $scope.task.runStatus = data.runStatus;
			  if(!$scope.stage.status)
  				  $location.url('/stage');
  			  else $location.url('/details');
  			}).error(function(data){
  				alert("无法撤销！")				
  			});
      }
     $scope.disableAction=function(){
    	 if(!$scope.notice)  return true;//如果没有发任何通知，直接disabled
    	 var isTime = new Date($scope.notice.start) < new Date()&& new Date($scope.notice.end)> new Date() && $scope.notice.workType=='CHE';
    	 switch($scope.stageNum){
    	 case 1: return !$scope.beforeMid() || !isTime;
    	 case 2: return !$scope.isMid() || !isTime;
    	 case 3: return !$scope.isEnd() || !isTime;
    	 default : return true;
    	 
    	 }
     }
//     $scope.mouseOver=function(index){
//     	$scope.attSelected=index;
//     }
//     $scope.mouseLeave=function(){
//      	$scope.attSelected=-1;
//      }
     //删除附件
//     $scope.remove=function(filename){
//     	$http({
// 			 method:'GET',
// 				url:"/tms/qemTask/removeAtt",
// 				params:{
// 					filename:filename,
// 					projectId: $scope.task.id
// 				}
// 		 }).success(function(data) {
// 			 if(data!=null){
// 				 $scope.fileList=data.fileList;
// 			 }			 
// 		 });
//     }
     $scope.currentStage=function(){
    	 var id="0";
    	 if($scope.task.status==10){
    		 if($scope.beforeMid()) id="1";
    		 else if($scope.isMid()) id="2";
    		 else if($scope.isEnd()) id="3";
    		 else if($scope.task.runStatus>=10){
    			 var runstatus=$scope.task.runStatus.toString();
    			 id=runstatus.left(1)
    		 }
    	 }else if($scope.task.status==20){
    		 id="3";
    	 }
    	 return $filter('filter')($scope.flows,{'id':id})[0];
     }
     //过滤提取当前流程对象
     $scope.currentFlow=$scope.currentStage();
     //提取当前步的class样式
     $scope.getClass=function(step,key){
    	 var result=false;
    	 angular.forEach($scope.currentFlow.statuses[step].runStatusCheck[key],function(item){
    		 result=result||( $scope.task.runStatus==item)
    	 })
    	 return result;
     }
     $scope.saveAble=function(){
    	 var statuses=['2','9','19','29','1103','2103','3103'];
    	 var result= false;
    	 angular.forEach(statuses,function(item){
    		 result=result || ( $scope.task.runStatus==item)
    	 })
    	 return !$scope.stage.status && result;
     }
     $scope.cancelAble=function(){
    	 var statuses=['10','20','30'];
    	 var result=false;
    	 angular.forEach(statuses,function(item){
    		 result=result||( $scope.task.runStatus==item)
    	 })
    	 return result;
     }
     $scope.getTag = function(step,key){
//    	 计算出显示符合√或×
    	 var value =step*100+key
    	 var status = $scope.currentFlow.id+ value;
    	 if($scope.task.runStatus==status) return true
    	 else return false;
     }
     $scope.havingStage = function(index){
    	 var result=false;
    	 angular.forEach($scope.task.havingStage,function(data){
    		 result=result||( index==data)
    	 })
    	 return result;
     }
     $scope.remove = function(item){
    	 var dir=item.formData?item.formData[0].isDeclaration:null;
    	 $http({
   			method:'GET',
   			url:"/tms/qemTask/delAttach",
   			params:{
   				filename: item.file.name,
   				taskId:  $scope.task.id,
                isDeclaration: dir?dir:uploadType()
   			}
   		  }).success(function(data) {
   			    if(item.size)
   			    	item.remove();
   			    else {
   			    	var queue=[]
   	  			  angular.forEach($scope.uploadQueue,function(file){
   	  				 if(file !=item){
   	  					 queue.push(file);
   	  				 }
   	  			  })
   	  			$scope.uploadQueue=queue;
   			    }
   			}).error(function(data){
   				alert("无法撤销！")				
   			});
     }
     $scope.dateFormat = function(jsondate){
    	 	if(jsondate)
    	 		return new Date(jsondate);
    	 	else return null;
	    }
     
}]);
