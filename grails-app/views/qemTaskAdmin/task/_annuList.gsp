   <div class="form-group" ng-init="checkStatus='未安排评审'" style="margin-bottom:15px;">
	   	<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="未安排评审"><label >未安排评审</label><span class="badge">{{(taskList | filter:{'groups':'未安排评审'}).length}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="需评审" ><label >需评审</label><span class="badge">{{(taskList | filter:{'groups':'需评审'}).length}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="已审" ><label >已审核</label><span class="badge">{{(taskList | filter:{'groups':'已审'}).length}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="未提交或其他" ><label >未提交或其他</label><span class="badge">{{(taskList | filter:{'groups':'未提交或其他'}).length}}</span></span>
	</div>  
    <div class="input-group small">
    	<span class="input-group-addon">项目等级</span>
		<select style="min-width:10em" class="form-control" name="level"  ng-model="trial.level"  ng-options="y.id as y.name for y in projectLevels"  ><option value="">全选</option></select>
		<span class="input-group-addon">项目类别</span>
		<select style="min-width:10em" class="form-control " ng-model="trial.typeName"  ng-options="y for y in taskList |uniKey:'type'"  ><option value="">全选</option></select>
		<span class="input-group-addon">立项年份</span>
		<select style="min-width:10em" class="form-control " ng-model="trial.beginYear"  ng-options="y for y in taskList | orderBy:'beginYear' |uniKey:'beginYear'"  ><option value="">全选</option></select>
		<span class="input-group-addon">结项年份</span>
		<select style="min-width:10em" class="form-control " ng-model="trial.expectedEnd"  ng-options="y for y in taskList | orderBy:'expectedEnd' |uniKey:'expectedEnd' "  ><option value="">全选</option></select>
	</div>
<div class="modal-body">
<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:5em"><input type="checkbox" ng-model="action.selectAll" ng-change="doselectAllAnnual()">序号</th>
			<th class="hand" ng-click="orderBy('projectName')">项目名称</th>
			<th class="hand" ng-click="orderBy('sn')">项目编号</th>
			<th class="hand" style="width:5em" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="hand" style="width:11em" ng-click="orderBy('type')">项目类别</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('userName')">姓名</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">立项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('expectedEnd')">结项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('status')">状态</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList | filter:listConditions | orderBy:order" class='repeat-animation'>
		<td><input type="checkbox" ng-model="item.selected">{{$index+1}}</td>				
		<td><a href="#" ng-click="stageDetail(item.id)">{{item.projectName}}</a></td>
		<td>{{item.sn}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.type}}</td>
		<td>{{item.userName}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.expectedEnd}}</td>
		<td>{{statusTT(item.status)}}</td>
		</tr>
	</tbody>

</table>
</div>