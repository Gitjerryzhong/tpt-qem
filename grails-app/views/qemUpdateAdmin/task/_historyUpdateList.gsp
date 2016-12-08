 	<div class="form-group" ng-init="checkStatus='未审'" style="margin-bottom:15px;">
	   	<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="未审"><label >未审</label><span class="badge">{{(taskList | filter:{'groups':'未审'}).length}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="已审" ><label >已审核</label><span class="badge">{{(taskList | filter:{'groups':'已审'}).length}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="未提交或其他" ><label >未提交或其他</label><span class="badge">{{(taskList | filter:{'groups':'未提交或其他'}).length}}</span></span>
	</div>  
    <div class="input-group">
    	<span class="input-group-addon">学院</span>
		<select style="min-width:10em" class="form-control"  ng-model="trial.departmentName"  ng-options="y for y in taskList |uniKey:'departmentName'"  ><option value="">全选</option></select>
    	<span class="input-group-addon">项目等级</span>
		<select style="min-width:6em" class="form-control"    ng-model="trial.level"  ng-options="levelText(y) for y in taskList |uniKey:'projectLevel'"  ><option value="">全选</option></select>
		<span class="input-group-addon">项目类别</span>
		<select style="min-width:10em" class="form-control "  ng-model="trial.typeName"  ng-options="y for y in taskList |uniKey:'type'"  ><option value="">全选</option></select>
		<span class="input-group-addon">立项年份</span>
		<select style="min-width:5em" class="form-control "  ng-model="trial.beginYear"  ng-options="y for y in taskList | orderBy:'beginYear' |uniKey:'beginYear'"  ><option value="">全选</option></select>
		<span class="input-group-addon">结项年份</span>
		<select style="min-width:6em" class="form-control "  ng-model="trial.expectedEnd"  ng-options="y for y in taskList | orderBy:'expectedEnd' |uniKey:'expectedEnd' "  ><option value="">全选</option></select>
	</div>
<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:3em">序号</th>
			<th class="hand" style="width:13em" ng-click="orderBy('projectName')">项目名称</th>
			<th class="hand" style="width:7em"ng-click="orderBy('sn')">项目编号</th>
			<th class="hand" style="width:5em" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="hand" style="width:8em" ng-click="orderBy('type')">项目类别</th>	
			<th class="hand" style="width:8em" ng-click="orderBy('updateType')">变更内容</th>
			<th class="hand" style="width:5em" ng-click="orderBy('beginYear')">立项年份</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('expectedEnd')">结项年份</th>
			<th class="hand" style="width:8em" ng-click="orderBy('commitDate')">申请时间</th>	
			<th class="hand" style="width:5em" ng-click="orderBy('status')">状态</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList |filter:{'groups':checkStatus} | filter:updateListConditions | orderBy:order" class='repeat-animation'>
		<td>{{$index+1}}</td>				
		<td><a href="#" ng-click="updateDetail(item)">{{item.projectName}}</a></td>
		<td>{{item.sn}}</td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.type}}</td>
		<td>{{updateText(item.updateTypes)}}</td>
		<td>{{item.beginYear}}</td>
		<td>{{item.expectedEnd}}</td>
		<td>{{dateFormat(item.commitDate) | date:'yyyy-MM-dd'}}</td>
		<td>{{updateStatusText(item.flow,item.auditStatus)}}</td>
		</tr>
	</tbody>

</table>
