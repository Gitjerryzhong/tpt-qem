<%--<div class="modal-header"> --%>
<%--	<h4>过滤条件</h4>          --%>
<%--    <div class="row">--%>
<%--    	<span class="col-md-2"><input type="checkbox" ng-model="trial.majorTypeChecked" name="check1"> <label >学科门类：</label></span>--%>
<%--		<span class="col-md-2"><select class="myselect" name="majorType"  ng-model="trial.majorType"  ng-options="y for y in majorTypes | filter: '!null'"  ></select></span>--%>
<%--    </div>--%>
<%--    <div class="row">--%>
<%--    	<span class="col-md-2"><input type="checkbox" ng-model="trial.levelChecked" name="check2"> <label >项目等级：</label></span>--%>
<%--		<span class="col-md-2"><select class="myselect" name="level"  ng-model="trial.level"  ng-options="y.id as y.name for y in projectLevels"  ></select></span>--%>
<%--    </div>--%>
<%--    <div class="row">--%>
<%--    	<span class="col-md-2"><input type="checkbox" ng-model="trial.groupChecked" name="check2"> <label >分组：</label></span>--%>
<%--		<span class="col-md-2"><select class="myselect" name="groupId"  ng-model="trial.groupId"  ng-options="y as groupText(y) for y in groups"  ></select></span>--%>
<%--    </div>--%>
<%--</div>--%>
<div class="modal-body">
		<table class="table table-hover">
			<thead>
				<tr>
					<th><input type="checkbox" ng-model="trial.selectAll" ng-change="doselectAllNew()"></th>
					<th>项目名称</th>
					<th>负责人姓名</th>
					<th>职称</th>	
					<th>学历</th>	
					<th>岗位</th>
					<th style="width:5em;height:50px;"><span class="expert-admin"  ng-click="onClick('departmentName')" ng-show="!clicked.departmentName" toolTip="点击筛选">学院</span>
					<select ng-show="clicked.departmentName" ng-model="selected.departmentName" class="myselect col_table" ng-options="y for y in requests | unique: 'departmentName'" ng-change="onChange('departmentName')" style="width:4em"><option value="">全选</option></select>
					</th>		
					<th style="width:5em;height:50px;"><span class="expert-admin"  ng-click="onClick('discipline')" ng-show="!clicked.discipline" toolTip="点击筛选">学科门类</span>
					<select ng-show="clicked.discipline" ng-model="selected.discipline" class="myselect col_table" ng-options="y for y in requests | unique: 'discipline'" ng-change="onChange('discipline')" style="width:4em"><option value="">全选</option></select>
					</th>
					
					<th>项目等级</th>		
					<th style="width:8em;height:50px;"><span class="expert-admin"  ng-click="onClick('type')" ng-show="!clicked.type" toolTip="点击筛选">项目类别</span>
					<select ng-show="clicked.type" ng-model="selected.type" class="myselect col_table" ng-options="y for y in requests | unique: 'type'" ng-change="onChange('type')" style="width:7em"><option value="">全选</option></select>
					</th>
					<th style="width:3em"><span class="expert-admin"  ng-click="onClick('groupId')" ng-show="!clicked.groupId" toolTip="点击筛选">分组</span>
					<select ng-show="clicked.groupId" ng-model="selected.groupId" class="myselect col_table" ng-options="groupText(y) as groupText(y) for y in requests | unique: 'groupId'" ng-change="onChange('groupId')" style="width:2em"><option value="">全选</option></select>
					</th>
				</tr>
			</thead>
			<tbody id="listBody">
<%--			|filter:{majorTypeName:majorType}--%>
				<tr ng-repeat="item in requests | filter:filterCondition">	
				<td><input type="checkbox" ng-model="item.selected"></td>
				<td><small>{{item.projectName}}</small></td>			
				<td><small>{{item.userName}}</small></td>
				<td><small>{{item.currentTitle}}</small></td>
				<td><small>{{item.currentDegree}}</small></td>
				<td><small>{{item.position}}</small></td>
				<td><small>{{item.departmentName}}</small></td>
				<td><small>{{item.majorTypeName}}</small></td>
				<td><small>{{levelText(item.projectLevel)}}</small></td>
				<td><small>{{item.type}}</small></td>	
				<td><small>{{item.groupId}}</small></td>			 
				</tr>
			</tbody>
		</table>
		
</div>
<div class="modal-footer">
<input type="button" class="btn btn-default" ng-click="createGroup()" value="创建分组">
<input type="button" class="btn btn-default" ng-click="moveOut()" value="移出分组">
</div>