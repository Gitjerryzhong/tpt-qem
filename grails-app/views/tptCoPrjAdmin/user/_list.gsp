	<div class="pull-right">
		<div class="btn-group">
			<a  class="btn btn-default" ui-sref="users">查看名单</a>
			<a  class="btn btn-primary" ui-sref="usersAdd">导入名单</a>
		</div>
	</div>
<div ng-controller="CoXsmdCtrl">	
	<div class="modal-header">
		<h3>学生名单列表</h3>
	</div>
		<form name="searchForm" role="form">
		  <div class="form-group" >	
		    <div class="col-sm-2">
		    	<input type="text" ng-pattern="/^\d/" class="form-control" id="studentId" placeholder="学号" ng-model="myfilter.studentId" >
		    </div>     
		    <div class="col-sm-2">  
		      <input type="text" class="form-control" id="project" placeholder="出国项目名称" ng-model="myfilter.co_Country" >
		    </div>
		   <div class="col-sm-2">
		    <button class="btn btn-default" ng-click="search()" ng-disabled="searchForm.$invalid">查询</button>
		   </div>
		  </div>
		</form> 
		<table class="table table-hover">
			<thead>
				<tr>
					<th style="width:3em">序号</th>
					<th style="width:10em">学号</th>
					<th style="width:5em">姓名</th>
					<th style="width:9em">录入日期</th>
					<th style="width:9em">删除日期</th>
					<th style="width:5em">录入人</th>
					<th style="width:5em">删除人</th>	
					<th style="width:11em">项目名称</th>
					<th style="width:3em">操作</th>				
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in userList">
				<td>{{$index+1}}</td>				
				<td>{{item.XH}}</td>
				<td>{{item.XM}}</td>
				<td>{{dateFormat(item.ADDDATE) | date : 'yyyy-MM-dd'}}</td>
				<td>{{dateFormat(item.DELDATE) | date : 'yyyy-MM-dd'}}</td>
				<td>{{item.ADDAUTHOR}}</td>
				<td>{{item.DELAUTHOR}}</td>
				<td><span class="kind" ng-click="span_click(item)" ng-show="!item.clicked">{{item.KIND}}</span>
				<select ng-show="item.clicked" ng-model="kind" class="myselect" ng-options="y.NAME for y in proList" ng-change="kindChange(item,kind.NAME)"></select></span></td>
				<td><span class="glyphicon glyphicon-remove" ng-click="delItem(item)" ng-if="!item.DELAUTHOR"></span></td>
				</tr>
			</tbody>
		</table>
</div>