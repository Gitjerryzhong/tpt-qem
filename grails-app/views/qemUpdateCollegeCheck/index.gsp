<html >
<head>
<meta name="layout" content="main" />
<title>学院审批变更申请</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="qem/angular-file-upload.min.js"/>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:stylesheet src="qem/c_update_check.css"/>
<asset:javascript src="qem/c_update_check.js"/>
</head>
<body >
	<div  ng-app="cUpdateCheckApp"  class="row" ng-controller="defaultCtrl" id="cUpdateCheckApp">
		<tabset>
                <tab ng-repeat="index in [0,1,2]" heading="{{tabs[index].title}}" active="tabs[index].active" ng-click="go(tabs[index].link)">
				</tab>
         </tabset>
        <div  ui-view=""> </div>
        <script type="text/ng-template" id="tab1.html">
		     <div class="form-horizontal" ng-controller="checkCtrl">				
            	<g:render template="update/list"></g:render>
        	</div>
        </script> 
        <script type="text/ng-template" id="tab2.html">
        	<div class="form-horizontal" ng-controller="taskListCtrl">				
            	<g:render template="task/taskList"></g:render>
        	</div>      
        </script>
        <script type="text/ng-template" id="tab3.html">
        	<div class="form-horizontal" ng-controller="myListCtrl">				
            	<g:render template="update/mylist"></g:render>
        	</div>      
        </script>  
        <script type="text/ng-template" id="qem-updateRequest.html">
        	<div class="form-horizontal" ng-controller="requestCtrl">
				<form name="myForm" role="form" novalidate>				
            		<g:render template="update/form"></g:render>
					<button class="btn btn-primary pull-right" ng-click="commit(myForm.$invalid)" > 提交</button>
				</form>
        	</div>      
        </script> 
        <script type="text/ng-template" id="qem-editRequest.html">
        	<div class="form-horizontal" ng-controller="editCtrl">
				<form name="myForm" role="form" novalidate>				
            		<g:render template="update/edit"></g:render>
					<button class="btn btn-primary pull-right" ng-click="commit(myForm.$invalid)" > 提交</button>
				</form>
        	</div>      
        </script> 
        <script type="text/ng-template" id="qem-updateView.html">  
			<div  ng-controller="updateViewCtrl" class="form-horizontal">	
            		<g:render template="update/view"></g:render>
			</div>	
       </script> 
	</div>
</body>
</html>