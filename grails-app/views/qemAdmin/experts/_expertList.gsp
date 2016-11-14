<div ng-show="showList">
<div class="pull-right">
	<button  class="btn btn-default" ng-click="back()">返回</button>
</div>
<div class="modal-header">
	<h4>专家库</h4>
</div>
<div class="modal-body">	
	<div class="form-group">
    	<label for="expertName" class="col-sm-2 control-label">搜索 姓名</label>
		<div class="col-sm-4"><input type="text" name="expertName" ng-model="condition.expertName1" class="form-control"></div>
    </div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>学院</th>
					<th>性别</th>	
					<th>电话</th>	
					<th>Email</th>
<%--					<th>办公地点</th>--%>
					<th>评审专业</th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in expertList | filter:{name:condition.expertName1}">	
				<td>{{$index+1}}</td>
				<td>{{item.name}}</td>			
				<td>{{item.departmentName}}</td>
				<td>{{item.sex}}</td>
				<td>{{item.longPhone}}</td>
				<td>{{item.email}}</td>
<%--				<td>{{item.officeAddress}}</td>	--%>
				<td>{{item.majorTypes | nullFilter}}</td>	
				<td><span class="glyphicon glyphicon-remove" ng-click="deleteItem(item)" style="cursor: pointer;"></span></td>			
				</tr>
			</tbody>
		</table>
		
</div>
</div>