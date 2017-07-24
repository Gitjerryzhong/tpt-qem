<%--		<form name="searchForm" role="form">--%>
			<h4>查询条件</h4>
			<div class="form-group">
			<div class="col-sm-2">		     
		      <select class="form-control input-sm" ng-model="trial.majorName"  ng-options="y for y in userList |uniKey:'majorName'" ng-change="majorChange()" ><option value="">查询专业</option></select>
		    </div>
		    <div class="col-sm-2">		     
		     <select class="form-control input-sm" ng-model="trial.adminClassName"  ng-options="y for y in marjorStudent |uniKey:'adminClassName'"  ng-change="classChange()"  ><option value="">查询班级</option></select>
		    </div>
		    <div class="col-sm-2">		     
		      <select class="form-control input-sm" ng-model="trial.projectName"  ng-options="y for y in marjorStudent |uniKey:'projectName'"  ng-change="projectChange()"  ><option value="">查询项目</option></select>
		    </div>
		    <div class="col-sm-2">		     
		      <input type="text" ng-pattern="/^\d{10}$/" class="form-control input-sm" id="studentId" placeholder="查询学号" ng-model="trial.studentId" ng-change="stdChange()" >
		    </div>
		    </div>
<%--		  </form> --%>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>学号</th>
					<th>姓名</th>
					<th>性别</th>	
					<th>班级</th>
					<th>专业</th>
					<th>项目</th>
					<th style="width:3em">操作</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in studentList">				
				<td>{{$index+1}}</td>
				<td>{{item.id}}</td>
				<td>{{item.name}}</td>
				<td>{{item.sex}}</td>
				<td>{{item.adminClassName}}</td>
				<td>{{item.majorName}}</td>				
				<td><span class="kind" ng-click="span_click(item)" ng-show="!item.clicked">{{item.projectName}}</span>
				<select ng-show="item.clicked" ng-model="kind" class="myselect" ng-options="y.NAME for y in proList" ng-change="kindChange(item,kind.NAME)"></select></span></td>
				<td><span class="glyphicon glyphicon-remove" ng-click="delItem(item)" ng-if="!item.DELAUTHOR"></span></td>
				</tr>
			</tbody>
		</table>
<%--		<ul class="error-ul">--%>
<%--			<li class="text-danger" ng-repeat="item in errors">--%>
<%--				{{item}}--%>
<%--			</li>--%>
<%--			<p class="text-danger">{{deleteCount}}</p>--%>
<%--		</ul>--%>