<html >
<head>
<meta name="layout" content="main" />
<title>教学质量工程系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:stylesheet src="qem/c_check.css"/>
<asset:javascript src="qem/c_index.js"/>
<script>
cIndexApp.value("config", {
		notices: ${notices ? notices as grails.converters.JSON : {}}
	});
</script>
</head>
<body >
	<div  ng-app="cIndexApp"  class="container" ng-controller="defaultCtrl">
		<tabset>
                <tab heading="{{tabs[0].title}}" active="tabs[0].active" ng-click="go(tabs[0].link)">
				</tab>
                <tab heading="{{tabs[2].title}}" ng-click="go(tabs[2].link)" active="tabs[2].active">
				</tab>
				<tab heading="{{tabs[1].title}}" ng-click="historyTaskList()" active="tabs[1].active">
				</tab>
         </tabset>
        <div  ui-view=""> </div>
       <script type="text/ng-template" id="notice_detail.html">
        %{--//创建详情视图--}%
        <div class="modal-header">
			<h3 class="title">{{notice.title}}</h3>
        </div>		
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="notice/detail"></g:render>
        	</div>
        </div>       
        </script>  
        <script type="text/ng-template" id="tab1.html">
		<div class="modal-header" ng-if="detailShow">            
			<h3 class="title">{{task.projectName}}</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" ng-if="!detailShow" >				
            	<g:render template="notice/form"></g:render>
        	</div>
			<div class="form-horizontal" ng-if="detailShow">				
            	<g:render template="task/contractDetail"></g:render>
        	</div>
        </div>       
        </script> 
        <script type="text/ng-template" id="tab2.html">
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="task/historyTaskList"></g:render>
        	</div>
        </div>       
        </script> 
        <script type="text/ng-template" id="tab3.html">
         <div ng-controller="contractCtrl" style="margin-top:15px;">		
		     <g:render template="task/taskList"></g:render>
	       </div>     
        </script> 
        <script type="text/ng-template" id="qem-taskDetail.html">	
			<div class="btn-toolbar" role="toolbar" style="margin-top:15px;">
				 <div class="btn-group" role="group" aria-label="title"><h3 class="title">{{task.projectName}}</h3></div>
				 
				<div class="btn-group pull-right" role="group" aria-label="buttons">
					<button class="btn btn-default prev {{disabled_p1T()}}" ng-click="taskDetail(pagerT.prevId)">
						<span class="glyphicon glyphicon-chevron-left"></span>
					</button>
					<button class="btn btn-default next {{disabled_n1T()}}" ng-click="taskDetail(pagerT.nextId)">
						<span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</div>
        	</div>	
			<hr>	
			<div class="">			
				<div class="form-horizontal" >				
            		<g:render template="task/contractDetail"></g:render>
        		</div>
     		</div>
		</script>
	</div>
	   
</body>
</html>