<div class="pull-right">		
	<button  class="btn btn-default" ng-click="doAssigned()">自动指派</button>
	<button  class="btn btn-primary" ng-click="applyGroup()">应用专家组</button>
</div>
<div class="modal-header"> 
	<h4>分配原则</h4>          
    <div class="row">
    	<span class="col-md-12" style="margin-bottom:5px"><input type="checkbox" ng-model="rules.check1" name="check1"> 专家不评审本单位项目</span>
    	<span class="col-md-12" style="margin-bottom:5px"><input type="checkbox" ng-model="rules.check2" name="check2"> 每项目评审专家数：
<%--    	<select class="myselect" ng-model="rules.value2" ng-options="y for y in maxExperts"></select>--%>
		<input type="number" ng-model="rules.value2" size="3" style="width:5em;margin-right:10px;">
    	</span>	
    	<span class="col-md-12" style="margin-bottom:5px"><input type="checkbox" ng-model="rules.check3" name="check3"> 每专家评审项目数：
<%--    	<select class="myselect" ng-model="rules.value3" ng-options="y for y in maxExperts"></select>--%>
    		<input type="number" ng-model="rules.value3" size="3" style="width:5em;margin-right:10px;">
    	</span>
    	<span class="col-md-12"><button  class="btn btn-primary" ng-click="expertGroup()" style="margin-right:6px">创建专家组</button><span class="agree">{{selectedExperts.names}}</span></span>		
    </div>
        
</div>
<div class="modal-body">
		<table class="table table-hover well">
			<thead>
				<tr>
					<th>序号<input type="checkbox" ng-model="trial.selectAll" ng-change="doselectAllNew()"></th>
					<th>项目名称</th>
					<th>负责人</th>
					<th style="width:5em;height:50px;"><span class="expert-admin"  ng-click="onClick('departmentName')" ng-show="!clicked.departmentName" toolTip="点击筛选">学院</span>
					<select ng-show="clicked.departmentName" ng-model="selected.departmentName" class="myselect col_table" ng-options="y for y in requests | uniKey: 'departmentName'" ng-change="onChange('departmentName')" style="width:4em"><option value="">全选</option></select>
					</th>
					<th style="width:5em;height:50px;"><span class="expert-admin"  ng-click="onClick('discipline')" ng-show="!clicked.discipline" toolTip="点击筛选">学科门类</span>
					<select ng-show="clicked.discipline" ng-model="selected.discipline" class="myselect col_table" ng-options="y for y in requests | uniKey: 'discipline'" ng-change="onChange('discipline')" style="width:4em"><option value="">全选</option></select>
					</th>
					<th>项目等级</th>		
					<th style="width:8em;height:50px;"><span class="expert-admin"  ng-click="onClick('type')" ng-show="!clicked.type" toolTip="点击筛选">项目类别</span>
					<select ng-show="clicked.type" ng-model="selected.type" class="myselect col_table" ng-options="y for y in requests | uniKey: 'type'" ng-change="onChange('type')" style="width:7em"><option value="">全选</option></select>
					</th>
					<th style="width:3em"><span class="expert-admin"  ng-click="onClick('groupId')" ng-show="!clicked.groupId" toolTip="点击筛选">分组</span>
					<select ng-show="clicked.groupId" ng-model="selected.groupId" class="myselect col_table" ng-options="groupText(y) as groupText(y) for y in requests | unique: 'groupId'" ng-change="onChange('groupId')" style="width:2em"><option value="">全选</option></select>
					</th>
					<th>专家</th>	
<%--					<th>操作</th>				--%>
				</tr>
			</thead>
			<tbody id="listBody">
<%--				<tr ng-repeat="item in requests | filter: selected.type==null?{}:{'type':selected.type} | filter: selected.groupId==null?{}:{'groupId':selected.groupId}" class='repeat-animation'>	--%>
				<tr ng-repeat="item in requests | filter: filterCondition" class='repeat-animation'>
				<td nowrap>{{$index+1}}<input type="checkbox" ng-model="item.selected"></td>
				<td style="width:10em"><small>{{item.projectName}}</small></td>			
				<td><small>{{item.userName}}</small></td>	
				<td><small>{{item.departmentName}}</small></td>					
				<td><small>{{item.discipline}}</small></td>
				<td><small>{{levelText(item.projectLevel)}}</small></td>
				<td><small>{{item.type}}</small></td>	
				<td><small>{{item.groupId}}</small></td>	
				<td style="width:15em"><small><span ng-repeat="y in item.exp"><span class="expert-admin" ng-click="span_click(y)" ng-show="!y.clicked">{{y.name}}</span> | 
				<select ng-show="y.clicked" ng-model="y.id" class="myselect" ng-options="exp.id+'|'+exp.name as exp.id+'|'+exp.name for exp in allexperts" ng-change="expChange(item,$index)"><option value="">删除</option></select></span></small></td>
<%--				 <td nowrap>                                     --%>
<%--                      <button type="button" class="btn btn-warning btn-xs"  ng-click="details(item.id)" >调整--%>
<%--                      </button>--%>
<%--                  </td>						 --%>
				</tr>
			</tbody>
		</table>
		
</div>
