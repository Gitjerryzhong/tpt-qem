<div class="list-body">
<table class="table table-hover" >
	<thead>
		<tr>
			<th style="width:3em">序号</th>
			<th class="hand" style="width:10em">项目名称</th>
			<th class="hand" style="width:7em">项目编号</th>
			<th class="hand" style="width:5em">原负责人</th>
			<th class="hand" style="width:5em" >项目等级</th>
			<th class="hand" style="width:8em" >项目类别</th>	
			<th class="hand" style="width:5em" >立项年份</th>
			<th class="hand" style="width:5em" >预期结项</th>	
<%--			<th class="hand" style="width:7em" >变更内容</th>	--%>
			<th class="hand" style="width:5em" >状态</th>	
			<th class="hand" style="width:5em">申请人</th>
			<th class="hand" style="width:3em">操作</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in requestList |filter:{'auditStatus':checkStatus}" >
		<td>{{$index+1}}</td>		
		<td>{{item.projectName}}</td>		
		<td>{{item.sn}}</td>
		<td>{{item.teacherName}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.type}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.expectedEnd}}</td>
<%--		<td>{{updateTypesText(item.updateTypes)}}</td>--%>
		<td>{{updateStatusText(item.flow,item.auditStatus)}}{{}}</td>
		<td>{{item.userName}}</td>
		<td><a href="" ng-click="view(item.id)"><span class='glyphicon glyphicon-search'></span></a></td>
		</tr>
	</tbody>

</table>
</div>