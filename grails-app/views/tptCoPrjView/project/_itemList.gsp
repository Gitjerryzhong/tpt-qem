<table class="table table-hover well">
	<thead>
		<tr>
			<th style="width:3em">序号</th>
			<th class="hand" ng-click="orderBy('majorName')" >校内专业</th>
			<th class="hand" style="width:5em" ng-click="orderBy('departmentName')">学院</th>
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">起始年份</th>	
			<th class="hand" style="width:8em" ng-click="orderBy('effeYearStr')">有效年份</th>	
			<th class="hand" ng-click="orderBy('collegeNameCn')">国外合作院校中文名</th>	
			<th class="hand" ng-click="orderBy('collegeNameEn')">国外合作院校英文名</th>	
			<th class="hand" style="width:10em">可衔接学位/学科/专业</th>	
			<th class="hand" style="width:10em" >备注</th>
<%--			<th style="width:3em">操作</th>--%>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in projectItems | ifUneffective" ng-class="{'uneffective':item.isUneffective}">
		<td>{{$index+1}}</td>				
		<td>{{item.majorName}}</td>
		<td>{{item.departmentName}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.effeYearStr}}</td>
		<td>{{item.collegeNameCn}}</td>
		<td>{{item.collegeNameEn}}</td>
		<td>{{item.coDegreeOrMajor}}</td>
		<td><small>{{item.memo}}</small></td>
<%--		<td><span  class="hand"><span class="glyphicon glyphicon-edit" ng-click="edit(item)" toolTip="编辑"></span><span  toolTip="今年续约" class="glyphicon glyphicon-check text-primary" ng-if="canGoOn(item)" ng-click="goOn(item)"></span></span></td>--%>
		</tr>
	</tbody>

</table>
