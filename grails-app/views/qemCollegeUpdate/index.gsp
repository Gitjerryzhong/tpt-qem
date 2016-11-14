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
<asset:stylesheet src="qem/c_update.css"/>
<asset:javascript src="qem/c_update.js"/>

</head>
<body >
	<div  ng-app="cCheckApp"  class="container" ng-controller="defaultCtrl">
	<div class="col-sm-2 sidebar">
			<div class="list-group">
			<a class="list-group-item statis active" href="#" data-status="0">学院项目</a>
				<a class="list-group-item statis" href="#"  ng-click="contractList()"><span class="badge">{{taskCounts.t1}}</span>变更申请</a>
				<a class="list-group-item statis" href="#"  ng-click="historyRequestList()"><span class="badge">{{taskCounts.t2}}</span>历史查询</a>
			</div>	
		</div>
		<div class="col-sm-10 content">
			<div  ui-view=""> </div>
		</div>
	
	<script type="text/ng-template" id="qem-taskDetail.html">
		<div class="modal-header">            
			<h3 class="title"><strong>项目名称：</strong>{{task.projectName}}</h3>
        </div>	
        <div class="modal-body well">
			<div class="form-horizontal" >				
            	<g:render template="detail/form"></g:render>
        	</div>
        </div>
        </script> 
        <script type="text/ng-template" id="qem-updateForm.html">
		<form name="myForm" role="form" novalidate>
        <div class="modal-body well">
			<div class="form-horizontal" >				
            	<g:render template="detail/updateForm"></g:render>
        	</div>
        </div>
        <div class="modal-footer">
 			<button class="btn btn-success" ng-click="commit(myForm.$invalid,status1,status2)" > 提交</button>
        </div>
		</form>
        </script> 
         <script type="text/ng-template" id="qem-updateDetail.html">
        <div class="modal-body well">
			<div class="form-horizontal" >				
            	<g:render template="detail/updateDetail"></g:render>
        	</div>
        </div>
        </script> 
        <script type="text/ng-template" id="qem-taskList.html">
	       <div >		
		     <g:render template="task/taskList"></g:render>
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