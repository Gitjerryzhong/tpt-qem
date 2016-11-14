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
<asset:javascript src="tpt/coproject.js"/>
<asset:stylesheet src="tpt/coproject.css"/>

</head>
<body >
	<div  ng-app="coProjectApp" class="container"  ng-controller="parentCtrl">
	<div class="col-sm-2 sidebar">
			<div class="list-group">
				<a class="list-group-item statis active" href="#" >合作项目管理</a>
				<a class="list-group-item statis" href="#" ui-sref="cocountry"><span class="badge"></span>项目分类</a>
				<a class="list-group-item statis" href="#" ui-sref="cotype"><span class="badge"></span>协议分类</a>
				<a class="list-group-item statis" href="#" ui-sref="coproject"><span class="badge"></span>协议信息</a>
				<a class="list-group-item statis" href="#" ui-sref="prosummary"><span class="badge"></span>协议汇总表</a>
<%--				<a class="list-group-item statis" href="#" ui-sref="users"><span class="badge"></span>学生管理</a>--%>
			</div>				
		</div>
		<div class="col-sm-10 content">
			<div  ui-view=""> </div>
		</div>
		
<%--	协议分类 --%>	
	<script type="text/ng-template" id="tpt-co-type.html">
	<div id="typeAdmin" ng-controller="CoTypeCtrl">		
		<div class="btn-toolbar">
			<button  class="btn btn-primary" ng-click="showCoType()">显示全部</button>
		</div>
		<div class="modal-header">           
            <h3 class="modal-title">协议分类设置</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="cotype/form"></g:render>
        	</div>
			<p class="Emphasis">{{rmErrors}}</p>
			<table class="table table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>名称</th>	
					<th>操作</th>	
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in coTypes">				
				<td>{{item.id}}</td>
				<td>{{item.name}}</td>
				<td nowrap><span class="btn btn-sm glyphicon glyphicon-edit" ng-click="editCoType(item)"></span>  
<span class="btn btn-sm glyphicon glyphicon-remove" ng-click="rmCoType(item.id)" ></span></td>
				</tr>
			</tbody>
		</table>			
        </div>
		
	</div>
	</script>
<%--	项目分类 --%>
	<script type="text/ng-template" id="tpt-co-country.html">
	<div id="typeAdmin" ng-controller="CoCountryCtrl">		
		<div class="btn-toolbar">
			<button  class="btn btn-primary" ng-click="showCoType()">显示全部</button>
		</div>
		<div class="modal-header">           
            <h3 class="modal-title">项目分类设置</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="cocountry/form"></g:render>
        	</div>
			<p class="Emphasis">{{rmErrors}}</p>
			<table class="table table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>名称</th>	
					<th>操作</th>	
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in coTypes">				
				<td>{{item.id}}</td>
				<td>{{item.name}}</td>
				<td nowrap><span class="btn btn-sm glyphicon glyphicon-edit" ng-click="editCoType(item)"></span>  
<span class="btn btn-sm glyphicon glyphicon-remove" ng-click="rmCoType(item.id)" ></span></td>
				</tr>
			</tbody>
		</table>			
        </div>
		
	</div>
	</script>
<%--  协议录入	--%>
	<script type="text/ng-template" id="tpt-co-project.html">
	<div id="CoProjectAdmin" ng-controller="CoProjectCtrl">
		<div class="modal-header">           
            <h3 class="modal-title">协议信息录入</h3>
        </div>
		<form name="myForm" role="form" novalidate>
        <div class="modal-body">
			<div class="form-horizontal" >		
				<g:render template="project/form"></g:render>
			</div>
		</div>
		<div class="modal-footer">
				<button type="button" class="btn btn-primary" ng-click="save()" ng-disabled="myForm.$invalid || !coProject.coTypeId || !coProject.coCountryId">提 交</button>
		</div>
		</form>
	</div>
	</script>
<%--  协议详情	--%>
	<script type="text/ng-template" id="tpt-co-pro-detail.html">
	<div ng-controller="CoProDetailCtrl">
		<div class="modal-header">           
            <h3 class="modal-title">{{projectDetail.name}}</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >		
				<g:render template="details/form"></g:render>
				<g:render template="details/itemList"></g:render>
			</div>
		</div>
		<form name="myForm" role="form" novalidate>
		<div class="modal-body">
			<div class="form-horizontal" >						
				<g:render template="details/itemForm"></g:render>
			</div>	
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" ng-click="save()" ng-disabled="myForm.$invalid || !projectItem.majorsId || !projectItem.effeYearStr">{{projectItem.id?'更新':'添加'}}</button>
		</div>
		</form>
	</div>
	</script>
	<script type="text/ng-template" id="tpt-co-pro-summary.html">
	<div >		
		<g:render template="details/projectSummary"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="tpt-students.html">
	<div >		
		<g:render template="user/list"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="tpt-students-add.html">
	<div >		
		<g:render template="user/form"></g:render>
	</div>
	</script>
	</div>
</body>
</html>