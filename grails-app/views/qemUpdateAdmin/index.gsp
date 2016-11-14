<html >
<head>
<meta name="layout" content="main" />
<title>教学质量工程系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:stylesheet src="qem/update_admin.css"/>
<asset:javascript src="qem/update_admin.js"/>
<script>
updateAdminApp.value("config", {
	taskList: ${taskList ? taskList as grails.converters.JSON : {}},
<%--	taskCounts: ${taskCounts ? taskCounts  as grails.converters.JSON : {}}--%>
	});
</script>
</head>
<body >
	<div  ng-app="updateAdminApp"  class="container" ng-controller="defaultCtrl">
	<div class="col-sm-12 content">
			<div  ui-view=""> </div>
	</div>
		<script type="text/ng-template" id="qem-updateDetail.html">
		<div class="modal-header">            
			<div class="pull-right">
				<div class="btn-group">
				<button class="btn btn-default prev {{disabled_p1()}}" ng-click="updateDetail(pager.prevId)">
					<span class="glyphicon glyphicon-chevron-left"></span>
				</button>
				<button class="btn btn-default next {{disabled_n1()}}" ng-click="updateDetail(pager.nextId)">
					<span class="glyphicon glyphicon-chevron-right"></span>
				</button>
				</div>
			</div>
			<h3 class="title">{{form.projectName}}</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="detail/updateDetail"></g:render>
        	</div>
        </div>
        </script> 
        <script type="text/ng-template" id="qem-historyUpdateList.html">
	       <div >		
		     <g:render template="task/historyUpdateList"></g:render>
	       </div>
	    </script>	    
	</div>
</body>
</html>