<div class="pull-right">	
			<a class="btn btn-default" href="/tms/QemTaskAdmin/export">导出Excel</a> 
	</div>
	<div><h4>专家意见（包含：通过、不通过、弃权、未评审）</h4>
	<ul class="list-inline">
		<li><span class="badge agree">通过</span></li>
		<li><span class="badge reject">不通过</span></li>
		<li><span class="badge giveup">弃权</span></li>
		<li><span class="badge noreview">未评审</span></li>
	</ul>
	</div>	
		<table class="table table-bordered">
			<thead>
				<tr style="background-color:#d9edf7;">
					<th ng-click="orderBy('type')" style="cursor: pointer;">项目类别</th>				
					<th ng-click="orderBy('projectName')" style="cursor: pointer;">项目名称</th>
					<th ng-click="orderBy('userName')" style="cursor: pointer;">负责人</th>
					<th ng-click="orderBy('currentTitle')" style="cursor: pointer;">职称</th>	
					<th ng-click="orderBy('currentDegree')" style="cursor: pointer;">学历</th>	
					<th ng-click="orderBy('position')" style="cursor: pointer;">职务</th>
					<th ng-click="orderBy('major')" style="cursor: pointer;">学院</th>		
					<th ng-click="orderBy('pass')" style="cursor: pointer;width:8em;">专家意见	</th>					
<%--					<th ng-click="orderBy('avgScore')" style="cursor: pointer;">平均分</th>--%>
					<th style="width:15em">综合意见</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in requests | orderBy:order">	
				<td>{{item.type}}</td>				
				<td>{{item.projectName}}<span class="badge">{{item.currentStage | stageText}}</span></td>			
				<td>{{item.userName}}</td>
				<td>{{item.currentTitle}}</td>
				<td>{{item.currentDegree}}</td>
				<td>{{item.position}}</td>
				<td>{{item.major}}</td>
				<td style="width:8em"><span class="badge agree">{{item.pass}}</span>
				<span class="badge reject">{{item.ng}}</span>
				<span class="badge giveup">{{item.waiver}}</span><span class="badge noreview">{{reviewLeft(item)}}</span></td>
<%--				<td>{{item.avgScore | number:2}}</td>--%>
				<td style="width:15em">{{item.view}}</td>	
				<td><button type="button" class="btn btn-default btn-xs" ng-click="stageAudit(item.id)" ><span class="glyphicon glyphicon-pencil"></span></button></td>			 
				</tr>
			</tbody>
		</table>