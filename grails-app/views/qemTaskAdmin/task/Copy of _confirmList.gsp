<div class="modal-header"> 
	<div class="pull-right">
		<button class="btn btn-default" ng-click="doAssign()" ng-show="action.hasexpert==0" >分配专家</button>
	</div>       
    <div class="row">
    	<span class="col-md-2"> <input type="radio" ng-model="action.hasexpert" value="0"><label >未分配专家</label><span class="badge reject">{{taskCounts.t0}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="action.hasexpert" value="1"><label >已分配专家</label><span class="badge noreview">{{taskCounts.t1}}</span></span>
    </div>  
</div>
<div class="modal-body">
<div class="panel panel-info">
    <div class="row panel-heading">
    	<span class="col-md-4"><input type="checkbox" ng-model="rules.check1" name="check1"> 同单位排除</span>
    	<span class="col-md-4"><input type="checkbox" ng-model="rules.check2" name="check2"> 每项目评审专家数：
    	<select class="myselect" ng-model="rules.value2" ng-options="y for y in maxExperts"></select>
    	</span>	
    	<span class="col-md-4"><input type="checkbox" ng-model="rules.check3" name="check3"> 每专家评审项目数：
    	<select class="myselect" ng-model="rules.value3" ng-options="y for y in maxExperts"></select>
    	</span>		
    </div> 
  </div>  
<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-sm-1"><input type="checkbox" ng-model="action.selectAll" ng-change="doselectAllConfirmed()">序号</th>
			<th class="col-sm-2 hand" ng-click="orderBy('projectName')">项目名称</th>
			<th class="col-sm-1 hand" ng-click="orderBy('sn')">项目编号</th>
			<th class="col-sm-1 hand" ng-click="orderBy('projectLevel')">项目等级</th>
			<th class="col-sm-2 hand" ng-click="orderBy('departmentName')">学院</th>	
			<th class="col-sm-1 hand" ng-click="orderBy('userName')">姓名</th>	
			<th class="col-sm-4 hand" >专家</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in taskList | filter: hasexperts | orderBy:order">
		<td class="col-sm-1"><input type="checkbox" ng-model="item.selected">{{$index+1}}</td>				
		<td class="col-sm-2"><a href="#" ng-click="stageDetail(item.id)">{{item.projectName}}</a></td>
		<td class="col-sm-1">{{item.sn}}</td>
		<td class="col-sm-1">{{levelText(item.projectLevel)}}</td>
		<td class="col-sm-2">{{item.departmentName}}</td>
		<td class="col-sm-1">{{item.userName}}</td>
		<td class="col-sm-4"><small><span ng-repeat="y in item.exp"><span class="expert-admin" ng-click="span_click(y)" ng-show="!y.clicked">{{ y.name }}</span> | 
				<select ng-show="y.clicked" ng-model="y.id" class="myselect" ng-options="exp.id+'|'+exp.name as exp.id+'|'+exp.name for exp in allexperts" ng-change="expChange(item,$index)"></select></span></small></td>
		</tr>
	</tbody>

</table>
</div>