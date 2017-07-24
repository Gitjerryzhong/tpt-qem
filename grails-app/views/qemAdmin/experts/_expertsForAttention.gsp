<div style="high:500px" class="pre-scrollable">
	<table class="table table-hover">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>学院</th>
				<th>性别</th>	
				<th>Email</th>
			</tr>
		</thead>
		<tbody id="listBody">
			<tr ng-repeat="item in expertList | filter:{name:condition.expertName1}">	
			<td><input type="checkbox" ng-model="item.selected"></td>
			<td>{{item.name}}</td>			
			<td>{{item.departmentName}}</td>
			<td>{{item.sex}}</td>
			<td>{{item.email}}</td>
			</tr>
		</tbody>
	</table>		
</div>