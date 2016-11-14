<html >
<head>
<meta name="layout" content="main" />
<title>系统管理</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>

<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:javascript src="angularjs/angular-locale_zh-cn.js"/>
<asset:stylesheet src="tpt/tpt.css"/>
<asset:javascript src="tpt/index.js"/>

</head>
<body >
	<div  ng-app="adminApp" id="tptAdmin" class="container" ng-controller="parentCtrl">
	<div class="col-sm-3 sidebar">
			<div class="list-group">
				<a class="list-group-item statis" href="#" ui-sref="users" ng-class="{'active':dataStatus==1}" ng-click="dataStatus=1">学生管理</a>
				<a class="list-group-item statis" href="#" ui-sref="notice" ng-class="{'active':dataStatus==2}" ng-click="dataStatus=2">通知管理</a>
				<a class="list-group-item statis" href="#" ui-sref="college" ng-class="{'active':dataStatus==3}" ng-click="dataStatus=3">国外大学管理</a>
				<a class="list-group-item statis" href="#" ui-sref="mentor" ng-class="{'active':dataStatus==4}" ng-click="dataStatus=4">导师管理</a>
			</div>				
		</div>
		<div class="col-sm-9 content">
			<div  ui-view=""> </div>
		</div>
		
		
	
	<script type="text/ng-template" id="tpt-users.html">
	<div id="userAdmin" ng-controller="modal">
	<div class="pull-right">
		<div class="btn-group">
			<button  class="btn btn-default" ng-click="list()">列表</button>
			<button  class="btn btn-default" ng-click="open()">添加</button>
		</div>
	</div>
	<div class="form-horizontal" >				
            	<g:render template="user/formList"></g:render>
    </div>
	</div>
	</script>
	<script type="text/ng-template" id="import-user.html">
		<div class="form-horizontal" >				
         	<g:render template="user/form"></g:render>
      	</div>        
	</script>
	
	<script type="text/ng-template" id="tpt-notice.html">
	<div id="noticeAdmin" ng-controller="NoticeCtrl">
		<div class="pull-right btn-group">
			<button  class="btn btn-default" ng-click="editAble=false" ng-disabled="!editAble">返回</button>
			<button  class="btn btn-default" ng-click="newNotice()">添加</button>
			<button  class="btn btn-default" ng-click="saveNotice()"  ng-disabled="!editAble">保存</button>
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
	
	<script type="text/ng-template" id="tpt-college.html">
	<div id="collegeAdmin" ng-controller="CollegeCtrl">	
	<div class="pull-right">
		<div class="btn-group">
			<button  class="btn btn-default" ng-click="list()">列表</button>
			<button  class="btn btn-default" ng-click="open()">添加</button>
		</div>
	</div>
		<div class="input-group col-sm-3">		     
		      <label for="collegeName" class="input-group-addon">关键字：</label><input type="text" class="form-control" id="collegeName" ng-model="filterName">
		    </div>
        <div class="modal-body">
			<table class="table table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>名称</th>	
					<th>操作</th>	
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in colleges |filter:{'name':filterName} |orderBy: 'name'" >				
				<td>{{$index+1}}</td>
				<td>{{item.name}}</td>
				<td nowrap><span class="btn btn-sm glyphicon glyphicon-edit" ng-click="editCollege($index)"></span>  
<span class="btn btn-sm glyphicon glyphicon-remove" ng-click="rmCollege(item.id)" ng-disabled="item.enableDel"></span></td>
				</tr>
			</tbody>
		</table>			
        </div>
		
	</div>
	</script>
	<script type="text/ng-template" id="add-college.html">
        <div class="modal-header">           
            <h3 class="modal-title">添加国外大学</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="college/form"></g:render>
        	</div>
        </div>
	</script>
	<script type="text/ng-template" id="mentor-list.html">
	<div id="mentorAdmin" ng-controller="mentorCtrl">	
			<div class="form-horizontal" >				
            	<g:render template="mentor/mentorList"></g:render>
        	</div>
    </div>
	</script>
		<script type="text/ng-template" id="mentor-add.html">
			<div class="form-horizontal" >				
            	<g:render template="mentor/teacherList"></g:render>
        	</div>
	</script>
	</div>
</body>
</html>