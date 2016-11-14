<html >
<head>
<meta name="layout" content="main" />
<title>教学质量工程系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:stylesheet src="bootstrap.min.css"/>
<asset:stylesheet src="bootstrap-theme.min.css"/>
<asset:stylesheet src="font-awesome.min.css"/>
<asset:javascript src="ag-grid/ag-grid.js"/>
<asset:stylesheet src="qem/project_admin.css"/>
<asset:javascript src="qem/project_admin.js"/>
<script>
pAdminApp.value("config", {
		bns: ${bns as grails.converters.JSON}
	});
</script>
</head>
<body >
	<div  ng-app="pAdminApp"  class="container" ng-controller="defaultCtrl">	
	<div class="col-sm-2 sidebar">
			<div class="list-group">
			<a class="list-group-item statis active" href="#"> 
				<div class="row">
					<div class="form-group">
						<label class="col-sm-4" style="margin-right:0;padding-right:0">批次</label>
						<div class="col-sm-8"><select class="form-control input-sm"  ng-model="bnselected"  ng-options="y for y in bns " ng-change="assigned(bnselected)" style="margin:0;padding:0"></select></div>
					</div>
				</div>				
			</a>
				<a class="list-group-item statis" href="#"  <%--ng-click="requestList(true,null)" --%> ui-sref="requestList">学院申报汇总</a>
<%--				<a class="list-group-item statis" href="#"  ng-click="requestList(false,'2')">历史申报汇总</a>--%>
				<a class="list-group-item statis" href="#"  ng-click="assigned(bnselected)"><span class="badge">{{pager.total.total}}</span>专家分配</a>				
				<a class="list-group-item statis" href="#"  ng-click="goTrial(1)">项目分组</a>
				<a class="list-group-item statis" href="#"  ui-sref="expertView"><span class="badge">{{pager.total.formeeting}}</span>专家评审汇总</a>				
				<a class="list-group-item statis" href="#"  ng-click="goTrial(0)"><span class="badge">{{pager.total.noreview}}</span>会审批量登记</a>
<%--				<a class="list-group-item statis" href="#"  ng-click="tasks()"><span class="badge">{{pager.total.task}}</span>任务书确认</a>	--%>
<%--				<a class="list-group-item statis" href="#"  ng-click=""><span class="badge"></span>待中检</a>--%>
<%--				<a class="list-group-item statis" href="#"  ng-click="">变更审批</a>					--%>
			</div>	
		</div>
		<div class="col-sm-10 content">
			<div ui-view="" > </div>
		</div>
		
		
	
	<script type="text/ng-template" id="qem-default.html">
	<div >
		<g:render template="audit/form"></g:render>
<%--		<ul class="pager">--%>
<%--		<li class="prev-page {{disabled_prev()}}"><a href="#" ng-click="previous()" >上一页</a></li>--%>
<%--		<li class="next-page {{disabled_next()}}"><a href="#" ng-click="next()">下一页</a></li>--%>
<%--		</ul>	--%>
	</div>
	</script>
	<script type="text/ng-template" id="qem-groups.html">
	<div >		
		<g:render template="group/form"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="qem-requestList.html">
	<div >		
		<g:render template="list/newForm"></g:render>
	</div>
	</script>	
	<script type="text/ng-template" id="qem-project-expert.html">
	<div >		
		<g:render template="project-expert/form"></g:render>
	</div>
	</script>	
	<script type="text/ng-template" id="expert_group.html">
	<div>
		<g:render template="project-expert/expertList"></g:render>
	</div>
	</script>	
	<script type="text/ng-template" id="qem-reviewed.html">
	<div >		
		<g:render template="reviewed/form"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="qem-details.html">
	<div class="form-horizontal" >			
		<g:render template="detail/form"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="qem-expertview.html">
	<div >		
		<g:render template="expertView/newform"></g:render>
<%--		<ul class="pager">--%>
<%--		<li class="prev-page {{disabled_prev()}}"><a href="#" ng-click="previous()" >上一页</a></li>--%>
<%--		<li class="next-page {{disabled_next()}}"><a href="#" ng-click="next()">下一页</a></li>--%>
<%--		</ul>	--%>
	</div>
	</script>
	<script type="text/ng-template" id="qem-taskList.html">
	<div >		
		<g:render template="task/taskList"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="qem-taskDetail.html">		
	<div class="modal-body">			
			<div class="form-horizontal" >				
            	<g:render template="task/taskDetail"></g:render>
        	</div>
     </div>
	<div class="modal-footer">
			<button class="btn btn-success" ng-click="confirmTask(task.id)">确认</button>
	</div>		
	</script>
	</div>
</body>
</html>