<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:3em">序号</th>
			<th class="hand" style="width:13em" ng-click="orderBy('projectName')">项目名称</th>
			<th class="hand" style="width:7em"ng-click="orderBy('sn')">项目编号</th>
			<th class="hand" style="width:5em" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="hand" style="width:5em" ng-click="orderBy('departmentName')">学院</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('userName')">姓名</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">立项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('expectedEnd')">结项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('status')">状态</th>	
			<th class="hand" style="width:3em" ng-click="orderBy('status')">变更</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList | filter:listConditions | orderBy:order" class='repeat-animation'>
		<td>{{$index+1}}</td>				
		<td><a href="#" ng-click="taskDetail(item.id)">{{item.projectName}}</a></td>
		<td>{{item.sn}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.departmentName}}</td>
		<td>{{item.userName}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.expectedEnd}}</td>
		<td>{{statusTT(item.status)}}</td>
		<td><span class='glyphicon glyphicon-edit hand' ng-if="item.status==10" ng-click="updateRequest(item.id)"></span></td>
		</tr>
	</tbody>

</table>
