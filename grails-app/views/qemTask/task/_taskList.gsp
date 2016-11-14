<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-sm-3 hand" ng-click="orderBy('projectName')">项目名称</th>
			<th class="col-sm-2 hand" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="col-sm-2 hand" ng-click="orderBy('commitDate')">申请日期</th>	
			<th class="col-sm-2 hand" >项目状态</th>	
			<th class="col-sm-1 hand" >任务</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in myProjects | orderBy:order">				
		<td class="col-sm-3"><a ng-href="qem/showTask/{{item.projectId}}">{{item.projectName}}</a></td>
		<td class="col-sm-2">{{levelText(item.projectLevel)}}</td>
		<td class="col-sm-2">{{dateFormat(item.commitDate) | date : 'yyyy-MM-dd'}}</td>
		<td class="col-sm-2">{{statusText(item.status)}}</td>
		<td class="col-sm-1"></td>

		</tr>
	</tbody>

</table>
