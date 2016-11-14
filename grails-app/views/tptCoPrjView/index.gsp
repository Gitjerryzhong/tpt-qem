<html >
<head>
<meta name="layout" content="main" />
<title>合作项目信息管理</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>

<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:javascript src="angularjs/angular-locale_zh-cn.js"/>
<asset:stylesheet src="bootstrap.min.css"/>
<asset:stylesheet src="bootstrap-theme.min.css"/>
<asset:stylesheet src="font-awesome.min.css"/>
<asset:javascript src="ag-grid/ag-grid.js"/>
<asset:javascript src="tpt/coprojectview.js"/>
<asset:stylesheet src="tpt/coproject.css"/>

</head>
<body >
	<div ng-app="coProjectApp" class="container" ng-controller="parentCtrl">	
		<div  ui-view=""> </div>	
		
	<script type="text/ng-template" id="tpt-co-pro-summary.html">
	<div >		
		<g:render template="project/projectSummary"></g:render>
	</div>
	</script>
<%--  协议详情	--%>
	<script type="text/ng-template" id="tpt-co-pro-detail.html">
	<div ng-controller="CoProDetailCtrl">
            <h3 class="title"><a href="javascript:history.go(-1);"><span class="glyphicon glyphicon-backward" toolTip="返回"></span></a>{{projectDetail.name}}</h3>
		<hr>
        <form name="myform" role="form">
			<div class="form-horizontal" >		
				<g:render template="project/form"></g:render>
				<g:render template="project/itemList"></g:render>
			</div>
		</form>		
	</div>
	</script>
	</div>
</body>
</html>