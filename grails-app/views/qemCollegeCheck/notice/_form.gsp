<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-md-1">序号</th>
			<th class="col-md-5 hand" ng-click="orderBy('title')">标题</th>
			<th class="col-md-2 hand" ng-click="orderBy('workType')">通知类型</th>
			<th class="col-md-2 hand" ng-click="orderBy('publishDate')">发布日期</th>
			<th class="col-md-2">批次操作</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in notices | orderBy:order">	
		<td class="col-md-1">{{$index+1}}</td>			
		<td class="col-md-6"><a href="#" ng-click="showNotice(item)">{{item.title}}</a></td>
		<td class="col-md-2">{{workTypeText(item.workType)}}</td>
		<td class="col-md-3">{{dateFormat(item.publishDate) | date : 'yyyy-MM-dd'}}</td>
		<td><a class="btn btn-primary" href="/tms/qemCollegeCheck/check/{{item.bn}}">审核</a></td>
		</tr>
	</tbody>
</table>