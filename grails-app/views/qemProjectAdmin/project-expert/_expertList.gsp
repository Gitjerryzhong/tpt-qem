<div class="modal-header">
	<div class="pull-right">
	<button  class="btn btn-default" ng-click="ok()">确定</button>
	<button  class="btn btn-default" ng-click="cancel()">取消</button>
	</div>
	<h4>选择专家</h4>
</div>
<div class="modal-body">	
	<div class="form-group">
    	<label for="expertName" class="col-sm-2 control-label">搜索 姓名</label>
		<div class="col-sm-4"><input type="text" name="expertName" ng-model="condition.expertName1" class="form-control"></div>
    </div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>序号<input type="checkbox" ng-model="trial.selectAll" ng-change="doselectAll()"></th>
					<th>姓名</th>
					<th>学院</th>
					<th>性别</th>
					<th>评审专业</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in expertList | filter:{name:condition.expertName1}">	
				<td nowrap>{{$index+1}}<input type="checkbox" ng-model="item.selected"></td>
				<td>{{item.name}}</td>			
				<td>{{item.departmentName}}</td>
				<td>{{item.sex}}</td>
				<td>{{item.majorTypes | nullFilter}}</td>	
				</tr>
			</tbody>
		</table>
		
</div>
</div>