<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:3em">序号</th>
			<th class="hand" style="width:7em"ng-click="orderBy('sn')">项目编号</th>
			<th class="hand" style="width:5em" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="hand" style="width:5em" ng-click="orderBy('type')">项目类别</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">立项年份</th>	
			<th class="hand" style="width:12em" >变更内容</th>	
			<th class="hand" style="width:7em" ng-click="orderBy('commitDate')">申请日期</th>	
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
		<td>{{dateFormat(item.commitDate) | date:"yyyy-MM-dd"}}</td>
		<td>{{updateStatusText(item.flow,item.auditStatus)}}{{}}</td>
		<td><a href="" ng-click="view(item.id)"><span class='glyphicon glyphicon-search' tooltip="查看"></span></a>
		<a href="" ng-click="edit(item.id)"><span class='glyphicon glyphicon-edit' ng-if="item.flow==0 && (item.auditStatus==0 || item.auditStatus==3)" tooltip="编辑"></span></a></td>
		</tr>
	</tbody>

</table>
