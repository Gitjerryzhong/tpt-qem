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
<asset:stylesheet src="qem/task_admin.css"/>
<asset:javascript src="qem/task_admin.js"/>
<%--<script>--%>
<%--	tAdminApp.value("config", {--%>
<%--		taskList: ${taskList ? taskList as grails.converters.JSON : []},--%>
<%--		taskCounts: ${taskCounts ? taskCounts  as grails.converters.JSON : []}--%>
<%--	});--%>
<%--</script>--%>
</head>
<body >
	<div  ng-app="tAdminApp"  class="row" ng-controller="defaultCtrl">	
	<div class="col-sm-2 sidebar">
			<div class="list-group">
<%--			<a class="list-group-item list-group-item-info statis" href="#">过程管理</a>	--%>
				<a class="list-group-item statis" href="#"   ng-class="{'active':menuSelected==0}" ui-sref="taskSummary" ng-click="menuSelected=0">已立项项目汇总</a>			
				<a class="list-group-item statis" href="#"   ng-class="{'active':menuSelected==1}" ng-click="tasks();menuSelected=1">合同审核</a>	
				<a class="list-group-item statis" href="#"   ng-class="{'active':menuSelected==2}" ng-click="annual();menuSelected=2">年度检查</a>
				<a class="list-group-item statis" href="#"   ng-class="{'active':menuSelected==3}" ng-click="mid();menuSelected=3">中期检查</a>
				<a class="list-group-item statis" href="#"   ng-class="{'active':menuSelected==4}" ng-click="end();menuSelected=4">结题</a>
				<a class="list-group-item statis" href="#"   ng-class="{'active':menuSelected==5}" ng-click="confirmed();menuSelected=5">分配专家</a>
				<a class="list-group-item statis" href="#"   ng-class="{'active':menuSelected==6}" ng-click="recorded();menuSelected=6">评审结果登记</a>
			</div>	
		</div>
		<div class="col-sm-10 content">
			<div  ui-view=""> </div>
		</div>	
	
	<script type="text/ng-template" id="qem-taskList.html">
	<div >		
		<g:render template="task/taskList"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="qem-taskSummary.html">
	<div >		
		<g:render template="task/newtaskSummary"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="qem-annualList.html">
	<div class="form-horizontal">		
		<g:render template="task/annuList"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="qem-taskDetail.html">
	<div class="btn-toolbar" role="toolbar">
				 <div class="btn-group" role="group" aria-label="title"><h3 class="title">{{task.projectName}}</h3></div>
				 
				<div class="btn-group pull-right" role="group" aria-label="buttons">
					<button class="btn btn-default prev {{disabled_p1T()}}" ng-click="taskDetail(task.preid)">
						<span class="glyphicon glyphicon-chevron-left"></span>
					</button>
					<button class="btn btn-default next {{disabled_n1T()}}" ng-click="taskDetail(task.nextid)">
						<span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</div>
        	</div>	
			<hr>	
        	<div class="">
				<div class="form-horizontal" >				
            		<g:render template="task/taskDetail"></g:render>
        		</div>
        	</div>		
<%--	<div class="modal-header">            --%>
<%--			<div class="pull-right">--%>
<%--				<div class="btn-group">--%>
<%--				<button class="btn btn-default prev {{disabled_p1T()}}" ng-click="taskDetail(pagerT.prevId)">--%>
<%--					<span class="glyphicon glyphicon-chevron-left"></span>--%>
<%--				</button>--%>
<%--				<button class="btn btn-default next {{disabled_n1T()}}" ng-click="taskDetail(pagerT.nextId)">--%>
<%--					<span class="glyphicon glyphicon-chevron-right"></span>--%>
<%--				</button>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--			<h3 class="title">{{task.projectName}}</h3>--%>
<%--        </div>--%>
<%--	<div class="modal-body">			--%>
<%--			<div class="form-horizontal" >				--%>
<%--            	<g:render template="task/taskDetail"></g:render>--%>
<%--        	</div>--%>
<%--     </div>--%>
	</script>
	<script type="text/ng-template" id="qem-stageDetail.html">		
			<div class="btn-toolbar" role="toolbar">
				 <div class="btn-group" role="group" aria-label="title"><h3 class="title">{{task.projectName}}</h3></div>
				 
				<div class="btn-group pull-right" role="group" aria-label="buttons">
					<button class="btn btn-default prev {{disabled_p1T()}}" ng-click="stageDetail(task.preid)">
						<span class="glyphicon glyphicon-chevron-left"></span>
					</button>
					<button class="btn btn-default next {{disabled_n1T()}}" ng-click="stageDetail(task.nextid)">
						<span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</div>
        	</div>	
			<hr>	
        	<div class="">
				<div class="form-horizontal" >				
            		<g:render template="task/stageDetail"></g:render>
        		</div>
        	</div>	

<%--		<div class="modal-header">            --%>
<%--			<div class="pull-right">--%>
<%--				<div class="btn-group">--%>
<%--				<button class="btn btn-default prev {{disabled_p1T()}}" ng-click="stageDetail(pagerT.prevId)">--%>
<%--					<span class="glyphicon glyphicon-chevron-left"></span>--%>
<%--				</button>--%>
<%--				<button class="btn btn-default next {{disabled_n1T()}}" ng-click="stageDetail(pagerT.nextId)">--%>
<%--					<span class="glyphicon glyphicon-chevron-right"></span>--%>
<%--				</button>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--			<h3 class="title"><strong>项目名称：</strong>{{task.projectName}}</h3>--%>
<%--        </div>--%>
<%--	<div class="modal-body">			--%>
<%--			<div class="form-horizontal" >				--%>
<%--            	<g:render template="task/stageDetail"></g:render>--%>
<%--        	</div>--%>
<%--     </div>			--%>
	</script>
	<script type="text/ng-template" id="qem-stageAudit.html">	
	<div class="modal-header">            
			<div class="pull-right">
				<div class="btn-group">
				<button class="btn btn-default prev {{disabled_p1T()}}" ng-click="stageDetail(pagerT.prevId)">
					<span class="glyphicon glyphicon-chevron-left"></span>
				</button>
				<button class="btn btn-default next {{disabled_n1T()}}" ng-click="stageDetail(pagerT.nextId)">
					<span class="glyphicon glyphicon-chevron-right"></span>
				</button>
				</div>
			</div>
			<h3 class="title"><strong>项目名称：</strong>{{task.projectName}}</h3>
        </div>	
	<div class="modal-body">			
			<div class="form-horizontal" >				
            	<g:render template="task/stageAudit"></g:render>
        	</div>
     </div>			
	</script>
	<script type="text/ng-template" id="qem-confirmedList.html">		
	<div class="modal-body">			
			<div class="form-horizontal" >				
            	<g:render template="task/confirmList"></g:render>
        	</div>
     </div>			
	</script>
	<script type="text/ng-template" id="qem-expertView.html">		
	<div class="modal-body">			
			<div class="form-horizontal" >				
            	<g:render template="task/expertView"></g:render>
        	</div>
     </div>			
	</script>
	<script type="text/ng-template" id="qem-newtask.html">		
	<div class="modal-body">			
			<div class="form-horizontal" >				
            	<g:render template="task/newform"></g:render>
        	</div>
     </div>	
	<div class="modal-footer">
		<button class="btn btn-success" ng-click="createTask()">创建</button>
	</div>		
	</script>
		<script type="text/ng-template" id="expert_group.html">
	<div>
		<g:render template="project-expert/expertList"></g:render>
	</div>
	</script>	
	</div>
</body>
</html>