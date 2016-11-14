<div class="pull-right">		
	<button  class="btn btn-default" ng-click="doAssign()">自动指派</button>
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
					<th>序号<input type="checkbox" ng-model="trial.selectAll" ng-change="doselectAllNew({'type':selected.type,'groupId':selected.groupId})"></th>
					<th>项目名称</th>
					<th>负责人</th>
					<th>学院</th>
					<th>项目等级</th>		
					<th style="width:8em;height:50px;"><span class="expert-admin"  ng-click="onClick('type')" ng-show="!clicked.type" toolTip="点击筛选">项目类别</span>
					<select ng-show="clicked.type" ng-model="selected.type" class="myselect col_table" ng-options="y for y in taskList | unique: 'type'" ng-change="onChange('type')" style="width:7em"><option value="">全选</option></select>
					</th>
					<th>专家</th>	
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in taskList | filter:(selected.type==null?{}:{'type':selected.type}) " class='repeat-animation'>	
				<td nowrap>{{$index+1}}<input type="checkbox" ng-model="item.selected"></td>
				<td><small>{{item.projectName}}</small></td>			
				<td><small>{{item.userName}}</small></td>	
				<td><small>{{item.departmentName}}</small></td>					
				<td><small>{{levelText(item.projectLevel)}}</small></td>
				<td><small>{{item.type}}</small></td>	
<%--				<td><small>{{item.groupId}}</small></td>	--%>
				<td width="300px"><small><span ng-repeat="y in item.exp"><span class="expert-admin" ng-click="span_click(y)" ng-show="!y.clicked">{{y.name}}</span> | 
				<select ng-show="y.clicked" ng-model="y.id" class="myselect" ng-options="exp.id+'|'+exp.name as exp.id+'|'+exp.name for exp in allexperts" ng-change="expChange(item,$index)"><option value="">删除</option></select></span></small></td>
<%--				 <td nowrap>                                     --%>
<%--                      <button type="button" class="btn btn-warning btn-xs"  ng-click="details(item.id)" >调整--%>
<%--                      </button>--%>
<%--                  </td>						 --%>
				</tr>
			</tbody>
		</table>
		
</div>
