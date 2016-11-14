<div class="modal-body">
<table class="table table-hover">
	<thead>
		<tr>
			<th  >项目名称</th>
			<th  style="width:9em" >类别</th>
			<th  style="width:5em" >项目等级</th>
			<th  style="width:5em" >立项年份</th>	
			<th  style="width:5em" >结项年份</th>
			<th  style="width:5em">状态</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList">
		<td>{{item.projectName}}</td>
		<td>{{item.type}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.endDate}}</td>
		<td>{{statusTT(item.status)}}</td>
		</tr>
	</tbody>

</table>
</div>
