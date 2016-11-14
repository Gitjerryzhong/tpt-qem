<div class="modal-header"> 
	<div class="pull-right">
		<button class="btn btn-default"  ng-click="newtask()" >新建任务</button>
	</div>       
    <div class="row">
    	<span class="col-md-2"> <input type="radio" ng-model="action.type" value="0"><label >未提交</label><span class="badge">{{taskCounts.t0}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="action.type" value="1"><label >已提交学院</label><span class="badge">{{taskCounts.t1}}</span></span>
		<span class="col-md-2"> <input type="radio" ng-model="action.type" value="201"><label >学院同意</label><span class="badge agree">{{taskCounts.t2}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="action.type" value="202"><label >学院不同意</label><span class="badge reject">{{taskCounts.t3}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="action.type" value="203"><label >学院退回</label><span class="badge noreview">{{taskCounts.t4}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="action.type" value="2"><label >学校同意</label><span class="badge">{{taskCounts.t5}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="action.type" value="3"><label >终止项目</label><span class="badge">{{taskCounts.t6}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="action.type" value="4"><label >学校退回</label><span class="badge">{{taskCounts.t7}}</span></span>
    </div>  
</div>
<div class="modal-body">
<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-sm-1"><input type="checkbox" ng-model="action.selectAll" ng-change="doselectAllTask()">序号</th>
			<th class="col-sm-2 hand" ng-click="orderBy('projectName')">项目名称</th>
			<th class="col-sm-1 hand" ng-click="orderBy('sn')">项目编号</th>
			<th class="col-sm-1 hand" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="col-sm-2 hand" ng-click="orderBy('departmentName')">学院</th>	
			<th class="col-sm-1 hand" ng-click="orderBy('userName')">姓名</th>	
			<th class="col-sm-1 hand" ng-click="orderBy('budget')">批准金额</th>	
			<th class="col-sm-2 hand" >参与人</th>	
<%--			<th class="col-sm-2 hand" >确认</th>	--%>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList | filter:filterTask | orderBy:order">
		<td class="col-sm-1"><input type="checkbox" ng-model="item.selected">{{$index+1}}</td>				
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