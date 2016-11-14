<table class="table table-hover">
	<thead>
		<tr>
			<th class="hand" style="width:4em">序号</th>
			<th class="hand" ng-click="orderBy('projectName')" style="width:15em">项目名称</th>
			<th class="hand" ng-click="orderBy('qemTypeName')" style="width:10em">项目类型</th>	
			<th class="hand" ng-click="orderBy('projectLevel')" style="width:5em">项目等级</th>
			<th class="hand" ng-click="orderBy('commitDate')" style="width:7em">立项日期</th>
			<th class="hand" ng-click="orderBy('sn')" style="width:7em">项目编号</th>	
			<th class="hand" style="width:5em">结项年份</th>	
			<th class="hand" style="width:5em">项目状态</th>	
			<th class="hand" style="width:8em">流程进度</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in myProjects | filter:remindFilter | orderBy:order">			
		<td>{{$index+1}}<span class="badge reject">{{myWork(item)}}</span></td>	
		<td><a ng-href="qemTask/showTask/{{item.id}}">{{item.projectName}}</a></td>
		<td >{{item.qemTypeName}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{dateFormat(item.beginYear) | date : 'yyyy-MM-dd'}}</td>
		<td>{{item.sn}}</td>
		<td>{{item.expectedEnd}}</td>
		<td>{{currentTask(item.status)}}</td>		
		<td>{{statusText(item.runStatus)}}</td>
		</tr>
	</tbody>

</table>
