<div class="list-body">
<div class="form-group">
		<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="0"><label >未审核</label><span class="badge">{{(requestList|filter:{'auditStatus':0}).length}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="1" ><label >审核通过</label><span class="badge">{{(requestList|filter:{'auditStatus':1}).length}}</span></span>
		<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="2" ><label >审核不通过</label><span class="badge">{{(requestList|filter:{'auditStatus':2}).length}}</span></span>
		<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="3" ><label >退回</label><span class="badge">{{(requestList|filter:{'auditStatus':3}).length}}</span></span>
</div>
<table class="table table-hover" >
	<thead>
		<tr>
			<th style="width:3em">序号</th>
			<th class="hand" style="width:7em">项目编号</th>
			<th class="hand" style="width:7em">申请人</th>
			<th class="hand" style="width:5em" >项目等级</th>
			<th class="hand" style="width:5em" >项目类别</th>	
			<th class="hand" style="width:5em" >立项年份</th>	
			<th class="hand" style="width:20em" >变更内容</th>	
			<th class="hand" style="width:5em" >状态</th>	
			<th class="hand" style="width:3em">操作</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in requestList |filter:{'auditStatus':checkStatus}" >
		<td>{{$index+1}}</td>				
		<td>{{item.sn}}</td>
		<td>{{item.userName}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.type}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{updateTypesText(item.updateTypes)}}</td>
		<td>{{updateStatusText(item.flow,item.auditStatus)}}{{}}</td>
		<td><a href="" ng-click="view(item.id)"><span class='glyphicon glyphicon-search'></span></a></td>
		</tr>
	</tbody>

</table>
</div>