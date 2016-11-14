<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-md-6 hand" ng-click="orderBy('title')">标题</th>
			<th class="col-md-2 hand" ng-click="orderBy('workType')">通知类型</th>
			<th class="col-md-3 hand" ng-click="orderBy('publishDate')">发布日期</th>	
			<th class="col-md-1 hand">操作</th>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in notices | orderBy:order">				
		<td class="col-md-6"><a href="#" ng-click="goDetail($index)">{{item.title}}</a></td>
		<td class="col-md-2">{{workTypeText(item.workType)}}</td>
		<td class="col-md-3">{{dateFormat(item.publishDate) | date : 'yyyy-MM-dd'}}</td>
		<td><button class="btn btn-primary" ng-click="createProject(item.id)" ng-if="createAble(item)">新项目申报</button>	</td>
		</tr>
	</tbody>
</table>