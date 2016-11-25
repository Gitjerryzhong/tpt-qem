<div class="row" ng-init="checkStatus='未审'">
   	<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="未审" ng-click=""><label >未审核</label><span class="badge">{{(taskList | filter:{'groups':'未审'}).length}}</span></span>
	<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="已审" ng-click=""><label >已审核</label><span class="badge">{{(taskList | filter:{'groups':'已审'}).length}}</span></span>
	<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="未提交或其他" ng-click=""><label >未提交或其他</label><span class="badge">{{(taskList | filter:{'groups':'未提交或其他'}).length}}</span></span>
</div>  
<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:5em"><input type="checkbox" ng-model="action.selectAll" ng-change="doselectAllTask()">序号</th>
			<th class="hand" ng-click="orderBy('projectName')">项目名称</th>
			<th class="hand" ng-click="orderBy('sn')">项目编号</th>
			<th class="hand" style="width:5em" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="hand" style="width:11em" ng-click="orderBy('type')">项目类别</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('userName')">姓名</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">立项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('expectedEnd')">结项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('status')">状态</th>	
			<th style="width:5em">操作</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList |filter:{'groups':checkStatus} | orderBy:order" class='repeat-animation'>
		<td><input type="checkbox" ng-model="item.selected">{{$index+1}}</td>				
		<td>{{item.projectName}}</td>
		<td>{{item.sn}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.type}}</td>
		<td>{{item.userName}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.expectedEnd}}</td>
		<td>{{statusTT(item.status)}}</td>
		<td><button class="btn btn-sm" ng-class="{'btn-success':auditAble(item),'btn-warning':!auditAble(item)}" ng-click="stageDetail(item.id)">{{auditAble(item)?"审核":"详情"}}</button></td>
		</tr>
	</tbody>

</table>
