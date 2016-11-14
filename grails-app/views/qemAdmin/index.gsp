<html >
<head>
<meta name="layout" content="main" />
<title>系统管理</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="qem/angular-file-upload.min.js"/>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:javascript src="angularjs/angular-locale_zh-cn.js"/>
<asset:stylesheet src="qem/admin.css"/>
<asset:javascript src="qem/admin.js"/>

</head>
<body >
	<div  ng-app="adminApp" id="tptAdmin" class="container" >
	<div class="col-sm-3 sidebar">
			<div class="list-group">
				<a class="list-group-item statis active" href="#" >系统管理</a>
				<a class="list-group-item statis" href="#" data-status="-1" ui-sref="parentTypes"><span class="badge"></span>大类管理</a>
				<a class="list-group-item statis" href="#" data-status="0" ui-sref="types"><span class="badge"></span>类别管理</a>
				<a class="list-group-item statis" href="#" data-status="1" ui-sref="notice"><span class="badge"></span>通知管理</a>
				<a class="list-group-item statis" href="#" data-status="2" ui-sref="attention"><span class="badge"></span>注意事项</a>
				<a class="list-group-item statis" href="#" data-status="1" ui-sref="experts"><span class="badge"></span>专家管理</a>				
			</div>			
		</div>
		<div class="col-sm-9 content">
			<div  ui-view=""> </div>
		</div>
	

	<script type="text/ng-template" id="qem-notice.html">
	<div id="noticeAdmin" ng-controller="NoticeCtrl">
		<div class="btn-toolbar">
			<button  class="btn btn-default" ng-click="editAble=false" ng-show="editAble">返回</button>
			<button  class="btn btn-default" ng-click="newNotice()">新通知</button>
			<button  class="btn btn-default" ng-click="saveNotice()"  ng-show="editAble">保存通知</button>
		</div>
		<div class="modal-header">           
            <h3 class="modal-title">申请通知设置</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="notice/form"></g:render>
				<g:render template="notice/formList"></g:render>
        	</div>
        </div>
	</div>
	</script>
	<script type="text/ng-template" id="qem-attention.html">
	<div ng-controller="AttentionCtrl">
		<div class="btn-toolbar">
			<button  class="btn btn-default" ng-click="goback()" ng-show="editAble || showDetail">返回</button>
			<button  class="btn btn-default" ng-click="newAttention()">新注意事项</button>
			<button  class="btn btn-default" ng-click="saveAttention()"  ng-show="editAble">保存</button>
		</div>
		<div class="modal-header">           
            <h3 class="modal-title">专家注意事项</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="attention/form"></g:render>
				<g:render template="attention/formList"></g:render>
				<g:render template="attention/detail"></g:render>
        	</div>
        </div>
	</div>
	</script>
	<script type="text/ng-template" id="qem-parenttype.html">
	<div id="parentAdmin" ng-controller="ParentCtrl">		
		<div class="btn-toolbar">
			<button  class="btn btn-primary" ng-click="showType()">显示全部</button>
		</div>
		<div class="modal-header">           
            <h3 class="modal-title">大类设置</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="type/formParent"></g:render>
        	</div>
			<p class="Emphasis">{{rmErrors}}</p>
			<table class="table table-hover">
			<thead>
				<tr>
					<th class="hand col-md-1" ng-click="orderBy('id')">ID</th>
					<th class="hand col-md-6" ng-click="orderBy('parentTypeName')">名称</th>	
					<th >操作</th>	
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in parentTypes | orderBy:order">				
				<td>{{item.id}}</td>
				<td>{{item.parentTypeName}}</td>
				<td nowrap><span class="btn btn-sm glyphicon glyphicon-edit" ng-click="editParentType(item)"></span>  
<span class="btn btn-sm glyphicon glyphicon-remove" ng-click="rmParentType(item.id)" ></span></td>
				</tr>
			</tbody>
		</table>			
        </div>
		
	</div>
	</script>
	<script type="text/ng-template" id="qem-types.html">
	<div id="typeAdmin" ng-controller="TypeCtrl">
		<form name="myForm" role="form" novalidate>			
		<div class="btn-toolbar">
			<button  class="btn btn-primary" ng-click="addType()" ng-disabled="myForm.$invalid || !type.downLoadUrl">保存</button>
			<button  class="btn btn-default" ng-click="showType()">显示全部</button>
		</div>
		<div class="modal-header">           
            <h3 class="modal-title">类别管理</h3>
        </div>
        <div class="modal-body">			
			<div class="form-horizontal" >				
            	<g:render template="type/form"></g:render>
				<g:render template="type/formUpload"></g:render>
        	</div>			
			<div class="Emphasis">				
					<p ng-repeat="error in rmErrors">
						{{error.message}}
					</p>				
			</div>
			<table class="table table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th class="hand" ng-click="orderBy('parentTypeName')">项目大类</th>	
					<th class="hand" ng-click="orderBy('name')">类别名称</th>	
					<th class="hand" ng-click="orderBy('cycle')">项目周期</th>
					<th>模版文件</th>
					<th class="hand" ng-click="orderBy('actived')">屏蔽</th>
					<th>操作</th>	
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in types | orderBy:order">				
				<td>{{item.id}}</td>
				<td>{{item.parentTypeName}}</td>
				<td>{{item.name}}</td>
				<td>{{item.cycle}}</td>
				<td>{{item.downLoadUrl}}</td>	
				<td><input type="checkBox" name="actived" ng-model="item.actived" ng-change="shielding(item.id)"/></td>			
				<td nowrap><span class="btn btn-sm glyphicon glyphicon-edit" ng-click="editType(item)"></span>  
<span class="btn btn-sm glyphicon glyphicon-remove" ng-click="rmType(item.id)" ></span></td>
				</tr>
			</tbody>
		</table>			
        </div>
	</form>	
	</div>
	</script>	
	<script type="text/ng-template" id="qem-experts.html">
	<div id="noticeAdmin" ng-controller="ExpertsCtrl">
		<g:render template="experts/form"></g:render>
		<g:render template="experts/expertList"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="expert_majorType.html">
	<div id="expertAdmin">
		<g:render template="experts/forme"></g:render>
	</div>
	</script>
	<script type="text/ng-template" id="expert_list.html">
	<div id="expertList">
		<g:render template="experts/expertList"></g:render>
	</div>
	</script>
	</div>
</body>
</html>