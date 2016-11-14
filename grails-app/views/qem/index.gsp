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
<asset:stylesheet src="qem/index.css"/>
<asset:javascript src="qem/index.js"/>

</head>
<body >
	<div  ng-app="qemApp" id="qem" class="row"  ng-controller="parentCtrl">
		<div class="col-sm-2 sidebar">
			<div class="list-group">
				<a class="list-group-item statis active" href="#" >我的项目</a>	
				<a class="list-group-item statis" href="#" ui-sref="default"><span class="badge"></span>项目通知</a>			
				<a class="list-group-item statis" href="#" ui-sref="qemSearch" ng-click="unRemind()"><span class="badge"></span>项目申报</a>
				<a class="list-group-item statis" href="#" ui-sref="qemTask" ng-click="unRemind()"><span class="badge"></span>项目管理</a>
				<a class="list-group-item statis" href="#" ui-sref="qemTemplates"><span class="badge"></span>模版下载</a>
				<a class="list-group-item statis" href="#" ui-sref="qemPublic"><span class="badge"></span>立项公示</a>
			</div>		
			<div class="list-group">
				<a class="list-group-item statis active" href="#" >任务提醒</a>	
				<a class="list-group-item statis" href="#" ui-sref="qemSearch" ng-if="${counts.t3 }" ng-click="remind()"><span class="badge">${counts.t3}</span>须修改项目</a>			
				<a class="list-group-item statis" href="#" ui-sref="qemTask" ng-if="${counts.t0 }" ng-click="taskRemind(1)"><span class="badge">${counts.t0 }</span>须提交合同</a>
				<a class="list-group-item statis" href="#" ui-sref="qemTask" ng-if="${counts.t1 }" ng-click="taskRemind(2)"><span class="badge">${counts.t1 }</span>须检查项目</a>
				<a class="list-group-item statis" href="#" ui-sref="qemTask" ng-if="${counts.t2 }" ng-click="taskRemind(3)"><span class="badge">${counts.t2 }</span>须结题项目</a>
			</div>	
		</div>
		<div class="col-sm-10 content">
			<div  ui-view=""> </div>
		</div>
	

	<script type="text/ng-template" id="qem-default.html">
	<div  ng-controller="defaultCtrl">
	<div id="default" ng-show="!detailShow">	
		<div class="modal-header">           
            <h3 class="modal-title">相关通知</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="notice/form"></g:render>
        	</div>
        </div>
	</div>
	<div id="detail"  ng-show="detailShow">
		
		<div class="modal-header">           
            <h3 class="modal-title">相关通知</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="detail/form"></g:render>
        	</div>
        </div>
		<div class="modal-footer">
			<button class="btn btn-default" ng-click="goBack()">返回</button>
		</div>
	</div>
	</div>
	</script>
	
	<script type="text/ng-template" id="qem-project.html">
	<div  ng-controller="projectCtrl">
	<div>
<%--		<div class="modal-header">           --%>
<%--            <h3 class="Emphasis"></h3>--%>
<%--        </div>--%>
	<form name="myForm" role="form" novalidate>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="project/form"></g:render>
        	</div>
        </div>	
		<div class="modal-footer">
<%--			<button class="btn btn-primary" ng-click="upload()" ng-disabled="!uploader.getNotUploadedItems().length " ng-show="project.id">上传附件</button>--%>
			<button class="btn btn-primary" ng-click="save()" ng-show="!(project.id)">保存信息</button>
			<button class="btn btn-success" ng-click="commit()" ng-disabled="disableAction()" toolTip="{{project.commit?'不要重复提交！':'别忘了上传附件'}}">提交申请</button>
		</div>
	</form>	
	</div>		
	</div>
	</script>
	<script type="text/ng-template" id="qem-template.html">
	<div  ng-controller="templateCtrl">		
		<div class="modal-header">           
            <h3 class="modal-title">附件模版文件下载</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="template/form"></g:render>
        	</div>
        </div>				
	</div>
	</script>	
	<script type="text/ng-template" id="qem-mylist.html">
<%--	<div  ng-controller="myListCtrl">--%>
<%--		<div class="pull-right" ng-show="createAble">				--%>
<%--				<button class="btn btn-default" ng-click="createProject()" >新项目申报</button>				--%>
<%--			</div>	--%>
<%--	<div>--%>
	<div  ng-controller="myListCtrl">	
		<div class="modal-header">           
            <h3 class="modal-title">我的申请项目</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="myList/form"></g:render>
        	</div>
        </div>	
		</div>				
	</div>
	</script>
	<script type="text/ng-template" id="qem-public.html">
	<div  ng-controller="publicCtrl">		
		<div class="modal-header">
            <h3 class="modal-title">立项项目公示</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >
				<div class="form-group">
					<label for="bn" class="col-sm-2 control-label">请选择批次</label>
    				<div class="col-sm-3">
    					<select class="form-control" name="bn" ng-model="bn" ng-options="y for y in bns"  >	</select>
    				</div>     
    				<a class="btn btn-primary" ng-click="getPublics()">查询</a> 
				</div>
				<g:render template="public/form"></g:render>
			</div>
		</div>				
	</div>
	</script>
    <script type="text/ng-template" id="qem-task.html">    
	<div  ng-controller="myTaskCtrl">	
        <div class="modal-header">
			<h3 class="title">我的已立项项目</h3>
        </div>		
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="task/taskList"></g:render>
        	</div>
        </div>		
	</div>       
        </script> 
    <script type="text/ng-template" id="attachment.html">
        %{--//创建文件上传视图--}%
        <div class="modal-header">
            <h3 class="modal-title">上传其他附件</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" nv-file-drop="" uploader="uploader">				
            	<g:render template="attachment/attch1"></g:render>
        	</div>
        </div>
        <div class="modal-footer">
             <button type="button" class="btn btn-success btn-s" ng-click="startUpload()" ng-disabled="!uploader.getNotUploadedItems().length ">确定</button>
            <button type="button" class="btn btn-danger btn-s" ng-click="uploader.clearQueue()" ng-disabled="!uploader.queue.length">清空</button>
			<button class="btn btn-warning" ng-click="cancel()">关闭</button>
			<div ng-show="showHine" style="margin-top:10px;"><span class="glyphicon glyphicon-info-sign"></span><span class="text-warning"><Strong>校验错误</strong>：不符合{{picLimit[1]}}</span></div>
        </div>
        </script> 
        
</div>
</body>
</html>