<div ng-show="!showList">
<div class="pull-right">
		<div class="btn-group">
			<button  class="btn btn-default" ng-click="add()">添加专家</button>
			<button  class="btn btn-default" ng-click="list()">显示全部专家</button>
		</div>
</div>
<div class="modal-header">
	<h4>过滤条件</h4>          
    <div class="row">
    	<span class="col-md-2"> <label >学院：</label></span>
		<span class="col-md-2"><select class="myselect" name="department"  ng-model="condition.department"  ng-options="y for y in teachers |uniDept" ></select></span>
    </div>
    <div class="row">
    	<span class="col-md-2"><label >姓名：</label></span>
		<span class="col-md-2"><input type="text" name="expertName" ng-model="condition.expertName" class="form-control"></span>
    </div>   
</div>
<div class="modal-body">
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th>姓名</th>
					<th>学院</th>
					<th>性别</th>	
					<th>电话</th>	
					<th>Email</th>
					<th>办公地点</th>
					<th>外聘</th>
				</tr>
			</thead>
			<tbody id="listBody">
<%--			|filter:{majorTypeName:majorType}--%>
				<tr ng-repeat="item in teachers | filter:conditions">	
				<td><input type="checkbox" ng-model="item.selected"></td>
				<td>{{item.name}}</td>			
				<td>{{item.departmentName}}</td>
				<td>{{item.sex}}</td>
				<td>{{item.longPhone}}</td>
				<td>{{item.email}}</td>
				<td>{{item.officeAddress}}</td>	
				<td>{{item.external?"是":"否"}}</td>				
				</tr>
			</tbody>
		</table>
		
</div>
</div>
<%--<div class="modal-footer">--%>
<%--<input type="button" class="btn btn-default" ng-click="add()" value="添加专家">--%>
<%--</div>--%>