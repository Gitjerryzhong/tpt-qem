<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:3em">序号</th>
			<th class="hand" style="width:13em" ng-click="orderBy('projectName')">项目名称</th>
			<th class="hand" style="width:7em"ng-click="orderBy('sn')">项目编号</th>
			<th class="hand" style="width:5em" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="hand" style="width:8em" ng-click="orderBy('type')">项目类别</th>	
			<th class="hand" style="width:8em" ng-click="orderBy('updateType')">变更内容</th>
			<th class="hand" style="width:5em" ng-click="orderBy('teacherName')">新负责人</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">立项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('expectedEnd')">结项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('status')">状态</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList | filter:updateListConditions | orderBy:order" class='repeat-animation'>
		<td>{{$index+1}}</td>				
		<td><a href="#" ng-click="updateDetail(item.id)">{{item.projectName}}</a></td>
		<td>{{item.sn}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.type}}</td>
		<td>{{updateText(item.updateType)}}</td>
		<td>{{item.teacherName}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.expectedEnd}}</td>
		<td>{{statusUp(item.status)}}</td>
		</tr>
	</tbody>

</table>
