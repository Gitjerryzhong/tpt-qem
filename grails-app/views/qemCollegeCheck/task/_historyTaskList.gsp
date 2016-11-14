<div class="modal-header"> 
	<h4>筛选条件</h4>          
    <div class="row">
    	<span class="col-md-2 text-right"><label  class="form-control-static">项目等级</label></span>
		<span class="col-md-2"><select class="form-control input-sm" name="level"  ng-model="trial.level"  ng-options="y.id as y.name for y in projectLevels"  ><option value="">全选</option></select></span>
	
    	<span class="col-md-2 text-right"><label class="form-control-static">教师姓名</label></span>
		<span class="col-md-2"><select class="form-control input-sm" ng-model="trial.userName"  ng-options="y for y in taskList |uniKey:'userName'"  ><option value="">全选</option></select></span>
		<span class="col-md-2 text-right"><label class="form-control-static">项目类别</label></span>
		<span class="col-md-2"><select class="form-control input-sm" ng-model="trial.typeName"  ng-options="y for y in taskList |uniKey:'type'"  ><option value="">全选</option></select></span>
	
	</div>
    <div class="row">
    	<span class="col-md-2 text-right"><label  class="form-control-static">建设情况</label></span>
		<span class="col-md-2"><select class="form-control input-sm"  ng-model="trial.status"  ng-options="y.id as y.name for y in projectStatus"  ><option value="">全选</option></select></span>
		<span class="col-md-2 text-right"><label class="form-control-static">立项年份</label></span>
		<span class="col-md-2"><select class="form-control input-sm" ng-model="trial.beginYear"  ng-options="y for y in taskList | orderBy:'beginYear' |uniKey:'beginYear'"  ><option value="">全选</option></select></span>
		<span class="col-md-2 text-right"><label class="form-control-static">结项年份</label></span>
		<span class="col-md-2"><select class="form-control input-sm" ng-model="trial.expectedEnd"  ng-options="y for y in taskList | orderBy:'expectedEnd' |uniKey:'expectedEnd' "  ><option value="">全选</option></select></span>
		
    </div>

</div>
<a href="/tms/QemCollegeCheck/exportTask" toolTip="全部导出" style="margin-right:20px"><span class="glyphicon glyphicon-download-alt"></span>导出Excel</a>
<a href="/tms/QemCollegeCheck/exportAttachAll" toolTip="全部导出附件" style="margin-right:20px"><span class="glyphicon glyphicon-download-alt"></span>导出附件</a>
<div class="modal-body">
<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:3em"><input type="checkbox" ng-model="action.selectAll" ng-change="doselectAllTask()">序号</th>
			<th class="hand" ng-click="orderBy('projectName')" >项目名称</th>
			<th class="hand" ng-click="orderBy('sn')">项目编号</th>
			<th class="hand" style="width:9em" ng-click="orderBy('type')">类别</th>
			<th class="hand" style="width:5em" ng-click="orderBy('userName')">姓名</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('status')">建设情况</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">立项</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('endDate')">结项年</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('expectedMid')">拟中期年</th>
			<th class="hand" style="width:4em" ng-click="orderBy('hasMid')">已中期</th>
			<th class="hand" style="width:5em" ng-click="orderBy('expectedEnd')">拟结项年</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('projectLevel')">等级</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList | filter:listConditions" class='repeat-animation'>
		<td><input type="checkbox" ng-model="item.selected">{{$index+1}}</td>				
		<td><a href="#" ng-click="taskDetail(item.id)">{{item.projectName}}</a></td>
		<td>{{item.sn}}</td>
		<td>{{item.type}}</td>
		<td>{{item.userName}}</td>
		<td>{{statusTT(item.status)}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.endDate}}</td>
		<td>{{item.expectedMid}}</td>
		<td>{{item.hasMid?'是':'否'}}</td>
		<td>{{item.expectedEnd}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		</tr>
	</tbody>

</table>
</div>