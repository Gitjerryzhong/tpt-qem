<div ng-show="!editAble && !showDetail">
<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-md-7 hand">标题</th>
			<th class="col-md-3 hand" >发布日期</th>	
			<th class="col-md-2 hand">编辑</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in attentions">				
		<td class="col-md-7"><a href="" ng-click="showAttention(item.id)">{{item.title}}</a></td>
		<td class="col-md-3">{{dateFormat(item.publishDate) | date : 'yyyy-MM-dd'}}</td>
		<td><span class="glyphicon glyphicon-edit" ng-click="edit(item)" style="cursor: pointer;"></span></td>	
		</tr>
	</tbody>
</table>
</div>