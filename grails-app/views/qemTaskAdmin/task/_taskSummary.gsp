<div class="modal-header"> 
	<h4>筛选条件</h4>          
    <div class="row">
    	<span class="col-md-2 text-right"><label  class="form-control-static">项目等级</label></span>
		<span class="col-md-2"><select class="form-control input-sm" name="level"  ng-model="trial.level"  ng-options="y.id as y.name for y in projectLevels"  ><option value="">全选</option></select></span>
	
    	<span class="col-md-2 text-right"><label class="form-control-static">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;院</label></span>
		<span class="col-md-2"><select class="form-control input-sm" ng-model="trial.departmentName"  ng-options="y for y in taskList |uniKey:'departmentName'"  ><option value="">全选</option></select></span>
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
<div class="modal-body">
<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:5em"><input type="checkbox" ng-model="action.selectAll" ng-change="doselectAllTask()">序号</th>
			<th class="hand" ng-click="orderBy('projectName')">项目名称</th>
			<th class="hand" ng-click="orderBy('sn')">项目编号</th>
			<th class="hand" style="width:5em" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="hand" style="width:5em" ng-click="orderBy('departmentName')">学院</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('userName')">姓名</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">立项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('expectedEnd')">结项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('status')">状态</th>	
			<th style="width:5em">备注</th>	
<%--			<th class="col-sm-2 hand" >确认</th>	--%>
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList | filter:listConditions | orderBy:order" class='repeat-animation'>
		<td><input type="checkbox" ng-model="item.selected">{{$index+1}}</td>				
		<td><a href="#" ng-click="taskDetail(item.id)">{{item.projectName}}</a></td>
		<td>{{item.sn}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.departmentName}}</td>
		<td>{{item.userName}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.expectedEnd}}</td>
		<td>{{statusTT(item.status)}}</td>
		<td toolTip="{{item.memo}}">{{item.memo | memoFilter}}</td>
<%--		<td class="col-sm-1"><span class="glyphicon glyphicon-ok btn-success" ng-click="confirmTask(item.projectId)"></span></td>--%>
		</tr>
	</tbody>

</table>
</div>