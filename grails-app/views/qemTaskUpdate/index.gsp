<html >
<head>
<meta name="layout" content="main" />
<title>项目变更申请</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="qem/angular-file-upload.min.js"/>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:stylesheet src="qem/qem_update.css"/>
<asset:javascript src="qem/qem_update.js"/>

</head>
<body >
	<div  ng-app="cCheckApp"  class="container" ng-controller="defaultCtrl">
	<div class="col-sm-2 sidebar">
			<div class="list-group">
				<a class="list-group-item statis" href="#" ng-class="{'active':menu_status==1}" ng-click="contractList()"><span class="badge">{{taskCounts.t1}}</span>变更申请</a>
				<a class="list-group-item statis" href="#" ng-class="{'active':menu_status==2}" ng-click="historyRequestList()"><span class="badge">{{taskCounts.t2}}</span>历史查询</a>
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
			<div class="form-horizontal" >				
            	<g:render template="detail/updateForm"></g:render>
        	</div>
 			<button class="btn btn-primary pull-right" ng-click="commit(myForm.$invalid,status1,status2)" > 提交</button>
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