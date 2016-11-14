<div class="modal-header"> 
	<div class="pull-right">
		<button class="btn btn-default" ng-click="confirmAll()" ng-show="action.type==1" >确认</button>
	</div>       
    <div class="row">
    	<span class="col-md-2"> <input type="radio" ng-model="action.type" value="0"><label >未提交</label><span class="badge reject">{{taskCounts.t0}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="action.type" value="1"><label >已提交</label><span class="badge noreview">{{taskCounts.t1}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="action.type" value="2"><label >已确认</label><span class="badge agree">{{taskCounts.t2}}</span></span>
    </div>  
</div>
<div class="modal-body">
<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-sm-1"><input type="checkbox" ng-model="action.selectAll" ng-change="doselectAllTask()"></th>
			<th class="col-sm-2 hand" ng-click="orderBy('projectName')">项目名称</th>
			<th class="col-sm-1 hand" ng-click="orderBy('sn')">项目编号</th>
			<th class="col-sm-1 hand" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="col-sm-2 hand" ng-click="orderBy('departmentName')">学院</th>	
			<th class="col-sm-1 hand" ng-click="orderBy('userName')">姓名</th>	
			<th class="col-sm-1 hand" >批准金额</th>	
			<th class="col-sm-2 hand" >参与人</th>	
<%--			<th class="col-sm-2 hand" >确认</th>	--%>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList | filter:{'runStatus':action.type} | orderBy:order">
		<td class="col-sm-1"><input type="checkbox" ng-model="item.selected"></td>				
		<td class="col-sm-2"><a href="#" ng-click="taskDetail(item.id)">{{item.projectName}}</a></td>
		<td class="col-sm-1">{{item.sn}}</td>
		<td class="col-sm-1">{{levelText(item.projectLevel)}}</td>
		<td class="col-sm-2">{{item.departmentName}}</td>
		<td class="col-sm-1">{{item.userName}}</td>
		<td class="col-sm-1">{{item.budget}}万</td>
		<td class="col-sm-2">{{item.memberstr}}</td>
<%--		<td class="col-sm-1"><span class="glyphicon glyphicon-ok btn-success" ng-click="confirmTask(item.projectId)"></span></td>--%>
		</tr>
	</tbody>

</table>
</div>