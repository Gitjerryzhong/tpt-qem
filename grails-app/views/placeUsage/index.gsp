<html>
<head>
<meta name="layout" content="main" />
<title>教室使用查询</title>
<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="place/place-usage/index.js"/>
<asset:stylesheet src="place/place-usage/index.css"/>
<script>
	angular.module("app").constant("config", {
		term: ${term},
		buildings: ${buildings},
		rooms: ${rooms}
	});
</script>
</head>
<body>
	<div class="container" id="ng-app" ng-app="app" ng-controller="MainCtrl">
		<div class="form-inline">
			<div class="form-group">
				<select class="form-control"
					ng-model="selectedBuilding" 
					ng-options="building for building in buildings"
					ng-change="loadRooms(selectedBuilding)"></select>
			</div>
			<div class="form-group">
				<select class="form-control" 
					ng-model="selectedRoom" 
					ng-options="room.id as room.name + ' (' + room.type + '/' + room.seat + '座)' for room in rooms"></select>
			</div>
			<button class="btn btn-default" ng-click="loadUsages(selectedRoom)">查询</button>
		</div>
		<div id="schedule">
			<table>
				<thead>
					<tr>
						<th class="week" colspan="2"></th>
						<th class="day">星期一</th>
						<th class="day">星期二</th>
						<th class="day">星期三</th>
						<th class="day">星期四</th>
						<th class="day">星期五</th>
						<th class="day">星期六</th>
						<th class="day">星期日</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="i in rows">
						<td class="c1" ng-if="spans[i]" rowspan="{{spans[i].span}}">{{spans[i].text}}</td>
						<td class="c2">{{i ? i : ''}}</td>
						<td ng-repeat="j in cols" id="a-{{i}}-{{j}}" class="arr"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>