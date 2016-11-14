<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-md-9 hand">标题</th>
			<th class="col-md-3 hand">发布日期</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in notices">				
		<td class="col-md-9"><a href="#" ng-click="goDetail(item.id)">{{item.title}}</a></td>
		<td class="col-md-3">{{dateFormat(item.publishDate) | date : 'yyyy-MM-dd'}}</td>
		</tr>
	</tbody>
</table>
