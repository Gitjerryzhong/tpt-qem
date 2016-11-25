<html >
<head>
<meta name="layout" content="main" />
<title>教学质量工程系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:stylesheet src="qem/c_mid_check.css"/>
<asset:javascript src="qem/c_end_check.js"/>

</head>
<body >
	<div  ng-app="cCheckApp"  class="container" ng-controller="defaultCtrl">
	<div class="col-sm-2 sidebar">
			<div class="list-group">
<%--			<a class="list-group-item statis active" href="#" data-status="0">学院结项审核</a>--%>
				<a class="list-group-item statis active" href="#"  ng-click="end()"><span class="badge">{{taskCounts.t2?taskCounts.t2:0}}</span>结项审核</a>
			</div>	
		</div>
		<div class="col-sm-10 content">
			<div  ui-view=""> </div>
		</div>
		
        <script type="text/ng-template" id="qem-taskList.html">
	       <div >		
		     <g:render template="task/annuList"></g:render>
	       </div>
	    </script>
	<script type="text/ng-template" id="qem-taskDetail.html">	
		<div class="btn-toolbar" role="toolbar">
				 <div class="btn-group" role="group" aria-label="title"><h3 class="title">{{task.projectName}}</h3></div>
				 
				<div class="btn-group pull-right" role="group" aria-label="buttons">
					<button class="btn btn-default prev {{disabled_p1T()}}" ng-click="stageDetail(pagerT.prevId)">
						<span class="glyphicon glyphicon-chevron-left"></span>
					</button>
					<button class="btn btn-default next {{disabled_n1T()}}" ng-click="stageDetail(pagerT.nextId)">
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
	</script>
	</div>
</body>
</html>