<%--<div class="pull-right">	--%>
<%--			<a class="btn btn-default" href="/tms/QemProjectAdmin/export">导出Excel</a> --%>
<%--	</div>	--%>
<%--		<table class="table table-hover">--%>
<%--			<thead>--%>
<%--				<tr>--%>
<%--					<th style="width:11em"><span class="expert-admin" ng-click="onClick('type')" ng-show="!clicked.type" toolTip="点击筛选">项目类别</span>--%>
<%--					<select ng-show="clicked.type" ng-model="selected.type" class="myselect" ng-options="y for y in requests | unique: 'type'" ng-change="onChange('type')" style="width:10em"></select></th>				--%>
<%--					<th>项目名称</th>--%>
<%--					<th>负责人姓名</th>--%>
<%--					<th style="width:5em"><span class="expert-admin" ng-click="onClick('currentTitle')" ng-show="!clicked.currentTitle" toolTip="点击筛选">职称</span>--%>
<%--					<select ng-show="clicked.currentTitle" ng-model="selected.currentTitle" class="myselect" ng-options="y for y in requests | unique: 'currentTitle'" ng-change="onChange('currentTitle')" style="width:3em"></select></th>	--%>
<%--					<th>学历</th>	--%>
<%--					<th>岗位</th>--%>
<%--					<th>学院</th>		--%>
<%--					<th><span class="expert-admin" ng-click="onClick('pass')" ng-show="!clicked.pass" toolTip="点击筛选">通过</span>--%>
<%--					<select ng-show="clicked.pass" ng-model="selected.pass" class="myselect" ng-options="y for y in requests | unique: 'pass'" ng-change="onChange('pass')"></select></th>--%>
<%--					<th style="width:3em"><span class="expert-admin" ng-click="onClick('ng')" ng-show="!clicked.ng" toolTip="点击筛选">不通过</span>--%>
<%--					<select ng-show="clicked.ng" ng-model="selected.ng" class="myselect" ng-options="y for y in requests | unique: 'ng'" ng-change="onChange('ng')" style="width:3em"></select></th>--%>
<%--					<th>弃权</th>--%>
<%--					<th>操作</th>--%>
<%--				</tr>--%>
<%--			</thead>--%>
<%--			<tbody id="listBody">--%>
<%--				<tr ng-repeat="item in requests | filter:{'type':selected.type,'currentTitle':selected.currentTitle,'pass':selected.pass,'ng':selected.ng}">	--%>
<%--				<td>{{item.type}}</td>				--%>
<%--				<td>{{item.projectName}}</td>			--%>
<%--				<td>{{item.userName}}</td>--%>
<%--				<td>{{item.currentTitle}}</td>--%>
<%--				<td>{{item.currentDegree}}</td>--%>
<%--				<td>{{item.position}}</td>--%>
<%--				<td>{{item.major}}</td>--%>
<%--				<td>{{item.pass}}</td>--%>
<%--				<td>{{item.ng}}</td>--%>
<%--				<td>{{item.waiver}}</td>--%>
<%--				 <td nowrap>--%>
<%--                                     --%>
<%--                      <button type="button" class="btn btn-warning btn-xs"  ng-click="meetingView(item.id)" >--%>
<%--                          <span class="glyphicon glyphicon-pencil"></span> 结论--%>
<%--                      </button>--%>
<%--                  </td>--%>
<%--				</tr>--%>
<%--			</tbody>--%>
<%--		</table>--%>
<accordion close-others="true" >
      
<div class="panel panel-info">
  <div class="panel-heading"><strong>
        <div class="row ">
            <span class="col-lg-2 col_table" ng-click="onClick('type')" ng-show="!clicked.type" toolTip="点击筛选" ><span class="expert-admin">项目类别</span></span>
				<select ng-show="clicked.type" ng-model="selected.type" class="myselect col-lg-2 col_table" ng-options="y for y in requests | unique: 'type'" ng-change="onChange('type')" ><option value="">全选</option></select>
			<span class="col-lg-2 col_table">项目名称</span>
			<span class="col-lg-1 col_table">负责人</span>	
			<span class="col-lg-1 col_table" ng-click="onClick('currentTitle')" ng-show="!clicked.currentTitle" toolTip="点击筛选"><span class="expert-admin">职称</span></span>
				<select ng-show="clicked.currentTitle" ng-model="selected.currentTitle" class="myselect col-lg-1 col_table" ng-options="y for y in requests | unique: 'currentTitle'" ng-change="onChange('currentTitle')" ><option value="">全选</option></select>
			<span class="col-lg-1 col_table">学历</span>
			<span class="col-lg-1 col_table">职务</span>
			<span class="col-lg-2 col_table" ng-click="orderBy('major')" style="cursor: pointer;" toolTip="点击排序">学院</span>	
			<span class="col-lg-1 col_table" ng-click="orderBy('projectLevel')" style="cursor: pointer;" toolTip="点击排序">等级</span>	
			<span class="col-lg-1 col_table" ng-click="orderBy('status')" style="cursor: pointer;" toolTip="点击排序">状态</span>	
            </div></strong>
  </div>
   <accordion-group is-open="status.open" class="panel" ng-repeat="item in requests | filter:selected.type==null?{}:{'type':selected.type} | filter:selected.currentTitle==null?{}:{'currentTitle':selected.currentTitle} |orderBy:order" ng-click="meetingView(item.id)"  ng-class="{'panel-danger':item.status>=4}">
      <accordion-heading >
<%--      <small> <i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': status.open, 'glyphicon-chevron-right': !status.open}"></i></small>--%>
            
 		<div class="row" ng-click="showTaskInfo(item)">
           		<span class="col-lg-2 col_table">{{item.type}}</span>				
				<span class="col-lg-2 col_table">{{item.projectName}}</span>			
				<span class="col-lg-1 col_table">{{item.userName}}</span>
				<span class="col-lg-1 col_table">{{item.currentTitle}}</span>
				<span class="col-lg-1 col_table">{{item.currentDegree}}</span>
				<span class="col-lg-1 col_table">{{item.position}}</span>
				<span class="col-lg-2 col_table">{{item.major}}</span>
				<span class="col-lg-1 col_table">{{levelText(item.projectLevel)}}</span>
				<span class="col-lg-1 col_table">{{statusText(item.status)}}</span>    
            </div>
      </accordion-heading>
    <div class="row">
      <g:render template="reviewed/form"></g:render>
     </div>
	</accordion-group>

</div>
</accordion>