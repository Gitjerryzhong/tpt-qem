<div ng-show="!editAble">
<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-md-9 hand" ng-click="orderBy('title')">标题</th>
			<th class="col-md-3 hand" ng-click="orderBy('publishDate')">发布日期</th>	
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in notices | orderBy:order">				
		<td class="col-md-9"><a href="#" ng-click="goDetail($index)">{{item.title}}</a></td>
		<td class="col-md-3">{{dateFormat(item.publishDate) | date : 'yyyy-MM-dd'}}</td>
		<td><a class="btn btn-default btn-xs" ng-disabled="item.enableDel" ng-click="delNotice(item.id)"><span class="glyphicon glyphicon-remove" ></span></a></td>	
		</tr>
	</tbody>
</table>
</div>