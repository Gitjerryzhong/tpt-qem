<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-sm-3 hand" ng-click="orderBy('projectName')">项目名称</th>
			<th class="col-sm-2 hand" ng-click="orderBy('sn')">项目编号</th>
			<th class="col-sm-2 hand" ng-click="orderBy('qemTypeName')">项目类型</th>
			<th class="col-sm-2 hand" ng-click="orderBy('departmentName')">学院</th>	
			<th class="col-sm-1 hand" ng-click="orderBy('name')">姓名</th>	
			<th class="col-sm-1 hand" ng-click="orderBy('currentDegree')">职称</th>	
			<th class="col-sm-1 hand" ng-click="orderBy('projectLevel')">项目等级</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in qemPublic">				
		<td class="col-sm-3">{{item.projectName}}</td>
		<td class="col-sm-2">{{item.sn}}</td>
		<td class="col-sm-2">{{item.qemTypeName}}</td>
		<td class="col-sm-2">{{item.departmentName}}</td>
		<td class="col-sm-1">{{item.name}}</td>
		<td class="col-sm-1">{{item.currentDegree}}</td>
		<td class="col-sm-1">{{levelText(item.projectLevel)}}</td>

		</tr>
	</tbody>

</table>