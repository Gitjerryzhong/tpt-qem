<div class="modal-header">           
    <h3 class="modal-title"> 设置导师</h3>
</div>
<div class="modal-body">
	<div class="row" >
	  <div class="col-sm-4">
	  	<h4>导师</h4>
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th>姓名</th>
					<th>性别</th>	
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in mentorList">	
				<td><input type="radio" name="selectedMentor" ng-model="mentor.selected" value="{{item.id}}"></td>
				<td>{{item.name}}</td>			
				<td>{{item.sex}}</td>
				</tr>
			</tbody>
		</table>
		</div>
		<div class="col-sm-6 col-sm-offset-2 ">
		<h4>学生</h4>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>学号</th>
					<th>姓名</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in requests">
				<td>{{$index+1}}</td>				
				<td>{{item.userId}}</td>
				<td>{{item.userName}}</td>
				</tr>
			</tbody>
		</table>
		</div>
	</div>
</div>
<div class="modal-footer">
		<button class="btn btn-primary" ng-click="ok()" ng-disabled="!mentor.selected"> 保存</button>
       	<button class="btn btn-warning" ng-click="cancel()">退出</button>
</div>
