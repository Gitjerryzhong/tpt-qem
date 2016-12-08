<table class="table table-hover">
	<thead>
		<tr>
			<th class="hand" style="width:4em">序号</th>
			<th class="hand" ng-click="orderBy('projectName')" style="width:15em">项目名称</th>
			<th class="hand" ng-click="orderBy('qemTypeName')" style="width:10em">类型</th>	
			<th class="hand" ng-click="orderBy('projectLevel')" style="width:4em">等级</th>
			<th class="hand" ng-click="orderBy('commitDate')" style="width:4em">立项</th>
			<th class="hand" ng-click="orderBy('sn')" style="width:7em">编号</th>	
			<th class="hand" style="width:4em">结项</th>	
			<th class="hand" style="width:4em">状态</th>	
			<th class="hand" style="width:8em">进度</th>	
			<th class="hand" style="width:4em">变更申请</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in myProjects | filter:remindFilter | orderBy:order">			
		<td>{{$index+1}}<span class="badge reject">{{myWork(item)}}</span></td>	
		<td><a ng-href="qemTask/showTask/{{item.id}}">{{item.projectName}}</a></td>
		<td >{{item.qemTypeName}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{dateFormat(item.beginYear) | date : 'yyyy'}}</td>
		<td>{{item.sn}}</td>
		<td>{{item.expectedEnd}}</td>
		<td>{{currentTask(item.status)}}</td>		
		<td>{{statusText(item.runStatus)}}</td>
		<td style="text-align:center;"><a href="/tms/qemTask/showTask/486?update=true" ng-if="item.status!=0 && item.status!=20 && item.status!=32"><span class="glyphicon glyphicon-send" toolTip="变更申请"></span></a></td>
		</tr>
	</tbody>

</table>
