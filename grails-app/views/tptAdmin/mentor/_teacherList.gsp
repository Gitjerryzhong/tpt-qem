<div class="modal-body">
	<div class="form-group">
		<div class="col-sm-offset-1 col-sm-3 input-group">
		  <input type="text"  placeholder="姓名" ng-model="condition.teacherName" class="form-control">
		  <span class="btn btn-default input-group-addon" ng-click="search(condition.teacherName)">查找老师</span>
		</div>
<%--		<div class="col-sm-2">--%>
<%--			<input type="text"  placeholder="姓名" ng-model="condition.teacherName" class="form-control">--%>
<%--		</div>--%>
    </div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th>姓名</th>
					<th>性别</th>	
					<th>电话</th>	
					<th>Email</th>
					<th>办公地点</th>
					<th>外聘</th>
				</tr>
			</thead>
			<tbody id="listBody">
<%--			|filter:{majorTypeName:majorType}--%>
				<tr ng-repeat="item in teachers ">	
				<td><input type="checkbox" ng-model="item.selected"></td>
				<td>{{item.name}}</td>			
				<td>{{item.sex}}</td>
				<td>{{item.longPhone}}</td>
				<td>{{item.email}}</td>
				<td>{{item.officeAddress}}</td>	
				<td>{{item.external?"是":"否"}}</td>				
				</tr>
			</tbody>
		</table>
		<ul class="pager">
		<li class="prev-page {{disabled_prev()}}"><a href="#" ng-click="previous()" >上一页</a></li>
		<li class="next-page {{disabled_next()}}"><a href="#" ng-click="next()">下一页</a></li>
		</ul>
</div>

<div class="modal-footer">
<input type="button" class="btn btn-default" ng-click="add()" value="添加">
<input type="button" class="btn btn-default" ng-click="cancel()" value="关闭">
</div>