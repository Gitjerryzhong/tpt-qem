<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:3em">序号</th>
			<th class="hand" style="width:7em"ng-click="orderBy('sn')">项目编号</th>
			<th class="hand" style="width:5em" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="hand" style="width:5em" ng-click="orderBy('type')">项目类别</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">立项年份</th>	
			<th class="hand" style="width:20em" >变更内容</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('status')">状态</th>	
			<th class="hand" style="width:3em" ng-click="orderBy('status')">操作</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in updateList" >
		<td>{{$index+1}}</td>				
		<td>{{item.sn}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.type}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{updateTypesText(item.updateTypes)}}</td>
		<td>{{updateStatusText(item.flow,item.auditStatus)}}{{}}</td>
		<td><a href="" ng-click="view(item.id)"><span class='glyphicon glyphicon-search'></span></a></td>
		</tr>
	</tbody>

</table>
