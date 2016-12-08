<html >
<head>
<meta name="layout" content="main" />
<title>教学质量工程系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="qem/angular-file-upload.min.js"/>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:javascript src="angularjs/angular-locale_zh-cn.js"/>
<asset:stylesheet src="qem/taskMenagement.css"/>
<asset:javascript src="qem/taskMenagement.js"/>
<script>
	taskApp.value("config", {
		task: ${task as grails.converters.JSON},
		notice: ${notice  as grails.converters.JSON},
		isUpdate: ${isUpdate  as grails.converters.JSON},
		fileList: ${fileList as grails.converters.JSON },
	});
</script>
</head>
<body >
	<div  ng-app="taskApp" id="qem" class="row" ng-controller="defaultCtrl" >
	<div class="col-sm-2 sidebar">
			<div class="list-group">	
				<a class="list-group-item statis" href="#" ng-click="goDefault()" ng-class="{'active':menuSelected==0}">项目总览</a>				
				<a class="list-group-item statis" ng-class="{'active':menuSelected==4}" href="#" ng-click="goContract()" ui-sref="contract" ng-if="task.status<10"><span class="badge agree" >★</span>任务书</a>			
				<a class="list-group-item statis"  ng-class="{'active':menuSelected==1}" href="#" ng-click="goStage('1')" ng-if="havingStage(1) || beforeMid()")><span class="badge agree" ng-show="beforeMid()">★</span>年度</a>
				<a class="list-group-item statis"  ng-class="{'active':menuSelected==2}" href="#" ng-click="goStage('2')" ng-if="havingStage(2) || isMid()"><span class="badge agree" ng-show="isMid()">★</span>中检</a>
				<a class="list-group-item statis"  ng-class="{'active':menuSelected==3}" href="#" ng-click="goStage('3')" ng-if="havingStage(3) || isEnd()"><span class="badge agree" ng-show="isEnd()">★</span>结题</a>
			
			</div>			
		</div>
		<div class="col-sm-10 content">
			<div  ui-view=""> </div>
		</div>
<%--		<div class="btn-group pull-right">	--%>
<%--				<button class="btn btn-default" ng-click="save()" ng-disabled="task.status>0" ng-show="editAble">保存</button>--%>
<%--				<button class="btn btn-default" ng-click="apply()" ng-disabled="task.status>0" ng-show="task.status<10">提交</button>			--%>
<%--				<button class="btn btn-default" ng-click="cancel()" ng-disabled="task.status!=1" ng-show="task.status<10">撤销</button>				--%>
<%--				<button class="btn btn-default" ng-click="edit()" ng-disabled="task.status>0" ng-show="task.status<10">编辑</button>--%>
<%--				<button class="btn btn-default" ng-click="saveStage()" ng-show="task.status==10" ng-disabled="disableAction()">保存</button>--%>
<%--				<button class="btn btn-default" ng-click="applyStage()" ng-show="task.status==10" ng-disabled="disableAction() || stage.status">提交</button>--%>
<%--				<button class="btn btn-default" ng-click="cancelStage()" ng-show="task.status==10" ng-disabled="disableAction() || !stage.status">撤销</button>				--%>
<%--				<button class="btn btn-default" ng-click="editStage()" ng-show="task.status==10" ng-disabled="disableAction()">编辑</button>--%>
<%--		</div>--%>
		
	<script type="text/ng-template" id="qem-contract.html">
	<div  >
		<div id="default" >	
			<div class="modal-header">
				<g:render template="task/btnGroup"></g:render>
<%--			<h3 class="title Emphasis">{{task.projectName}}</h3>--%>
        	</div>		
        	<div class="modal-body">
				<div class="form-horizontal" >				
            		<g:render template="task/details"></g:render>
        		</div>
        	</div>	
		</div>
	</div>
	</script>
	<script type="text/ng-template" id="qem-default.html">
	<div ng-controller="baseInfoCtrl" >
		<div id="default" >	
			<div class="btn-toolbar" role="toolbar">
				 <div class="btn-group" role="group" aria-label="title"><h3 class="title">{{task.projectName}}</h3></div>
				 
				<div class="btn-group pull-right" role="group" aria-label="buttons">
<%--						<button class="btn btn-default" ng-click="edit()">编辑</button>--%>
						<button class="btn btn-default" ng-click="goUpdate()" ng-if="task.status!=0 && task.status!=20 && task.status!=32">变更申请</button>
					</div>
        	</div>	
			<hr>	
        	<div class="">
				<div class="form-horizontal" >				
            		<g:render template="task/details"></g:render>
        		</div>
        	</div>	
		</div>
	</div>
	</script>
	<script type="text/ng-template" id="warning.html">
		<div class="form-horizontal" >				
            <g:render template="task/warning"></g:render>
        </div>
	</script>
     <script type="text/ng-template" id="qem-editTask.html">  
		<div class="btn-toolbar" role="toolbar">
				 <div class="btn-group" role="group" aria-label="title"><h3 class="title">{{task.projectName}}</h3></div>
				 
				<div class="btn-group pull-right" role="group" aria-label="buttons">
						<g:render template="task/bigFlow"></g:render>
					</div>
        	</div>	
			<hr>	  
            <div class="">
			<div class="form-horizontal" >				
            	<g:render template="task/form"></g:render>
<%--				<g:render template="task/formUpload"></g:render>--%>
        	</div>
        </div>		
	</div>       
        </script>  
         <script type="text/ng-template" id="qem-stageReport.html">  
			<div class="btn-toolbar" role="toolbar">
				 <div class="btn-group" role="group" aria-label="title"><h3 class="title">{{task.projectName}}</h3></div>
				 
				<div class="btn-group pull-right" role="group" aria-label="buttons">
						<g:render template="stage/btnGroup"></g:render>
					</div>
        	</div>	
			<hr>	  
		<form name="myForm" role="form" novalidate>	
			<div class="form-horizontal" >				
            	<g:render template="stage/form"></g:render>			
        	</div>
		</form>	
	</div>       
        </script>  
         <script type="text/ng-template" id="qem-stageDetail.html">    
			<div class="btn-toolbar" role="toolbar">
				 <div class="btn-group" role="group" aria-label="title"><h3 class="title">{{task.projectName}}</h3></div>
				 
				<div class="btn-group pull-right" role="group" aria-label="buttons">
						<g:render template="stage/btnGroup"></g:render>
					</div>
        	</div>	
			<hr>	
			<div class="form-horizontal" >				
            	<g:render template="stage/details"></g:render>			
        	</div>
	      
        </script>   
        <script type="text/ng-template" id="qem-updateForm.html">
		<div  ng-controller="updateCtrl" >
		<form name="myForm" role="form" novalidate>
			<div class="form-horizontal" >				
            	<g:render template="update/updateForm"></g:render>
        	</div>
 			<button class="btn btn-primary pull-right" ng-click="commit(myForm.$valid)" > 提交</button>
		</form>
		</div>
        </script>
        <script type="text/ng-template" id="qem-updateView.html">
		<form name="myForm" role="form">
			<div class="form-horizontal" >				
            	<g:render template="update/view"></g:render>
        	</div>
		</form>
        </script>
	</div>
	  
</body>
</html>