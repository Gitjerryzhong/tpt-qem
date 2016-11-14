<div class="modal-header">
<div class="pull-right">
		<div class="btn-group">
			<button  class="btn btn-default" ng-click="list()">列表</button>
			<button  class="btn btn-default" ng-click="open()">添加</button>
		</div>
	</div>
	<div class="form-group">
    	<label for="expertName" class="col-sm-2 control-label">搜索导师</label>
		<div class="col-sm-2"><input type="text" name="expertName" placeholder="姓名" ng-model="condition.mentorName" class="form-control"></div>
    </div>
</div>
<div class="modal-body">	
	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>性别</th>	
					<th>电话</th>	
					<th>Email</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in mentorList | filter:{name:condition.mentorName}">	
				<td>{{$index+1}}</td>
				<td>{{item.name}}</td>			
				<td>{{item.sex}}</td>
				<td>{{item.longPhone}}</td>
				<td>{{item.email}}</td>
				<td><span class="glyphicon glyphicon-remove" ng-click="deleteItem(item)" style="cursor: pointer;"></span></td>			
				</tr>
			</tbody>
		</table>
		
</div>
