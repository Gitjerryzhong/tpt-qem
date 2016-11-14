<%--		<table class="table table-hover">--%>
<%--			<thead>--%>
<%--				<tr>--%>
<%--					<th style="width:3em">序号</th>--%>
<%--					<th style="cursor: pointer;width:11em"><span class="expert-admin" ng-click="onClick('type')" ng-show="!clicked.type" toolTip="点击筛选">项目类别</span>--%>
<%--					<select ng-show="clicked.type" ng-model="selected.type" class="myselect" ng-options="y for y in requests | unique: 'type'" ng-change="onChange('type')" style="width:10em"><option value="">全选</option></select></th>				--%>
<%--					<th style="cursor: pointer;width:11em" ng-click="orderBy('projectName')">项目名称</th>--%>
<%--					<th style="cursor: pointer;width:5em" ng-click="orderBy('userName')">负责人姓名</th>--%>
<%--					<th style="cursor: pointer;width:5em"><span class="expert-admin" ng-click="onClick('currentTitle')" ng-show="!clicked.currentTitle" toolTip="点击筛选">职称</span>--%>
<%--					<select ng-show="clicked.currentTitle" ng-model="selected.currentTitle" class="myselect" ng-options="y for y in requests | unique: 'currentTitle'" ng-change="onChange('currentTitle')" style="width:4em"><option value="">全选</option></select></th>	--%>
<%--					<th style="cursor: pointer;width:4em" ng-click="orderBy('currentDegree')">学历</th>	--%>
<%--					<th style="cursor: pointer;width:5em" ng-click="orderBy('position')">岗位</th>--%>
<%--					<th style="cursor: pointer;width:4em" ng-click="orderBy('major')">学院</th>		--%>
<%--					<th style="cursor: pointer;width:3em" ng-click="orderBy('projectLevel')">等级</th>	--%>
<%--					<th style="cursor: pointer;width:4em" ng-click="orderBy('status')">状态</th>	--%>
<%--				</tr>--%>
<%--			</thead>--%>
<%--			<tbody id="listBody">--%>
<%--				<tr  ng-repeat="item in requests | filter:selected.type==null?{}:{'type':selected.type} | filter:selected.currentTitle==null?{}:{'currentTitle':selected.currentTitle} |orderBy:order">	--%>
<%--				<td>{{$index+1}}</td>	--%>
<%--				<td>{{item.type}}</td>				--%>
<%--				<td>{{item.projectName}}</td>			--%>
<%--				<td>{{item.userName}}</td>--%>
<%--				<td>{{item.currentTitle}}</td>--%>
<%--				<td>{{item.currentDegree}}</td>--%>
<%--				<td>{{item.position}}</td>--%>
<%--				<td>{{item.major}}</td>--%>
<%--				<td>{{levelText(item.projectLevel)}}</td>--%>
<%--				<td>{{statusText(item.status)}}</td>--%>
<%--				</tr>--%>
<%--			</tbody>--%>
<%--		</table>--%>
<accordion close-others="true" >
      
<div class="panel panel-info">
  <div class="panel-heading"><strong>
  	
        <div class="row ">
        	<span class="col-lg-1 col_table" style="margin-right:0">序号</span>
            <span class="col-lg-2 col_table" ng-click="onClick('type')" ng-show="!clicked.type" toolTip="点击筛选" ><span class="expert-admin">项目类别</span></span>
				<select ng-show="clicked.type" ng-model="selected.type" class="myselect col-lg-2 col_table" ng-options="y for y in requests | unique: 'type'" ng-change="onChange('type')" ><option value="">全选</option></select>
			<span class="col-lg-2 col_table">项目名称</span>
			<span class="col-lg-1 col_table">负责人</span>	
			<span class="col-lg-1 col_table" ng-click="onClick('currentTitle')" ng-show="!clicked.currentTitle" toolTip="点击筛选"><span class="expert-admin">职称</span></span>
				<select ng-show="clicked.currentTitle" ng-model="selected.currentTitle" class="myselect col-lg-1 col_table" ng-options="y for y in requests | unique: 'currentTitle'" ng-change="onChange('currentTitle')" ><option value="">全选</option></select>
			<span class="col-lg-1 col_table">学历</span>
			<span class="col-lg-1 col_table">职务</span>
			<span class="col-lg-1 col_table" ng-click="orderBy('major')" style="cursor: pointer;" toolTip="点击排序">学院</span>	
			<span class="col-lg-1 col_table" ng-click="orderBy('projectLevel')" style="cursor: pointer;" toolTip="点击排序">等级</span>	
			<span class="col-lg-1 col_table" ng-click="orderBy('status')" style="cursor: pointer;" toolTip="点击排序">状态</span>	
            </div></strong>
  </div>
   <accordion-group is-open="status.open" class="panel" ng-repeat="item in requests | filter:selected.type==null?{}:{'type':selected.type} | filter:selected.currentTitle==null?{}:{'currentTitle':selected.currentTitle}:true |orderBy:order" 
    ng-class="{'panel-success':item.status==4,'panel-danger':item.status==5,'panel-warning':item.status==6}">
      <accordion-heading >
 		<div class="row" ng-click="showTaskInfo(item)">
 				<span class="col-lg-1 col_table" style="margin-right:0">{{$index+1}}</span>
           		<span class="col-lg-2 col_table">{{item.type}}</span>				
				<span class="col-lg-2 col_table">{{item.projectName}}</span>			
				<span class="col-lg-1 col_table">{{item.userName}}</span>
				<span class="col-lg-1 col_table">{{item.currentTitle}}</span>
				<span class="col-lg-1 col_table">{{item.currentDegree}}</span>
				<span class="col-lg-1 col_table">{{item.position}}</span>
				<span class="col-lg-1 col_table">{{item.major}}</span>
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