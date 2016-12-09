<div class="pull-right" ng-init="checkStatus='未审'">
	<ul class="nav nav-pills" role="tablist">
	  <li role="presentation" ng-class="{'active':checkStatus=='未审'}"><a href="" ng-click="checkStatus='未审'">未审核 <span class="badge">{{(taskList | filter:{'groups':'未审'}).length}}</span></a></li>
	  <li role="presentation" ng-class="{'active':checkStatus=='已审'}"> <a href="" ng-click="checkStatus='已审'">已审核<span class="badge">{{(taskList | filter:{'groups':'已审'}).length}}</span></a></li>
	  <li role="presentation" ng-class="{'active':checkStatus=='未提交或其他'}"><a href="" ng-click="checkStatus='未提交或其他'">未提交或其他 <span class="badge">{{(taskList | filter:{'groups':'未提交或其他'}).length}}</span></a></li>
	</ul>
<%--   	<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="未审" ><label >未审核</label><span class="badge">{{(taskList | filter:{'groups':'未审'}).length}}</span></span>--%>
<%--	<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="已审" ><label >已审核</label><span class="badge">{{(taskList | filter:{'groups':'已审'}).length}}</span></span>--%>
<%--	<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="未提交或其他"><label >未提交或其他</label><span class="badge">{{(taskList | filter:{'groups':'未提交或其他'}).length}}</span></span>--%>
</div>  
<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:3em"><input type="checkbox" ng-model="action.selectAll" ng-change="doselectAllTask()"></th>
			<th style="width:13em" class="hand" ng-click="orderBy('projectName')">项目名称</th>
			<th style="width:8em" class="hand" ng-click="orderBy('type')">类别</th>
			<th style="width:6em" class="hand" ng-click="orderBy('sn')">编号</th>
			<th style="width:3em" class="hand" ng-click="orderBy('projectLevel')">等级</th>
			<th style="width:5em" class="hand" ng-click="orderBy('userName')">负责人</th>	
			<th style="width:5em" class="hand" ng-click="orderBy('budget')">批准金额</th>
			<th style="width:6em" >状态</th>	
			<th style="width:3em">操作</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList | filter:{'groups':checkStatus} | orderBy:order">
		<td><input type="checkbox" ng-model="item.selected"></td>				
		<td ><a href="#" ng-click="taskDetail(item.id)">{{item.projectName}}</a></td>
		<td>{{item.type}}</td>
		<td>{{item.sn}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.userName}}</td>
		<td>{{item.budget}}万</td>
		<td>{{statusText(item.runStatus)}}</td>
		<td><button class="btn btn-sm" ng-class="{'btn-success':item.groups=='未审','btn-warning':item.groups!='未审'}" ng-click="taskDetail(item.id)">{{item.groups=='未审'?"审核":"详情"}}</button></td>
		</tr>
	</tbody>

</table>
