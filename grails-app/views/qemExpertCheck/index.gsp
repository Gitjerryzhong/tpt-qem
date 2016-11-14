<html >
<head>
<meta name="layout" content="main" />
<title>教学质量工程系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:stylesheet src="qem/expert.css"/>
<asset:javascript src="qem/expert.js"/>

</head>
<body >
	<div  ng-app="expertApp"  class="container" ng-controller="defaultCtrl">	
	<div class="col-sm-2 sidebar">
			<div class="list-group">
			<a class="list-group-item statis active" href="#" data-status="0">立项评审</a>
				<a class="list-group-item statis" href="#"  ui-sref="attention">注意事项</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrial(0)"><span class="badge">{{pager.total[0]}}</span>待立项</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrial(1)"><span class="badge">{{pager.total[1]}}</span>已立项评审</a>	
			<a class="list-group-item statis active" href="#">检查评审</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrialTask(0)"><span class="badge">{{pager.total1[0]}}</span>待检查</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrialTask(1)"><span class="badge">{{pager.total1[1]}}</span>已检查评审</a>	
			<a class="list-group-item statis active" href="#">结题评审</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrialTask(0)"><span class="badge">{{pager.total1[2]}}</span>待结项</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrialTask(1)"><span class="badge">{{pager.total1[3]}}</span>已结项评审</a>							
			</div>	
		</div>
		<div class="col-sm-10 content">
			<div  ui-view=""> </div>
		</div>
		
	
	<script type="text/ng-template" id="qem-beginning.html">
	<div >		
		<g:render template="beginning/form"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="qem-related.html">
	<div >		
		<g:render template="beginning/relatedTask"></g:render>
	</div>
	</script>	
	<script type="text/ng-template" id="qem-reviewed.html">
	<div >		
		<g:render template="reviewed/form"></g:render>
	</div>
	</script>	
	<script type="text/ng-template" id="qem-stageReviewed.html">
	<div >		
		<g:render template="reviewed/formtask"></g:render>
	</div>
	</script>	
	<script type="text/ng-template" id="qem-taskview.html">
	<div >		
		<g:render template="taskview/form"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="qem-attention.html">
	<div class="modal-header">           
            <h3 class="modal-title">专家注意事项</h3>
        </div>
	<div class="modal-body" ng-if="attention">	
		<div class="form-horizontal" >	
			<g:render template="attention/detail"></g:render>
		</div>
	</div>
	</script>		
	</div>
</body>
</html>