<div class="modal-header"> 
	<h4>筛选条件</h4>          
    <div class="row">
    	<span class="col-md-2 text-right"><label class="form-control-static">学科门类</label></span>
		<span class="col-md-2"><select class="form-control input-sm"  ng-model="trial.discipline"  ng-options="y.title as y.title for y in disciplines "><option value="">全选</option></select></span>
    	<span class="col-md-2 text-right"><label  class="form-control-static">项目等级</label></span>
		<span class="col-md-2"><select class="form-control input-sm" name="level"  ng-model="trial.level"  ng-options="y.id as y.name for y in projectLevels"  ><option value="">全选</option></select></span>
		<span class="col-md-2 text-right"><label  class="form-control-static">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态</label></span>
		<span class="col-md-2"><select class="form-control input-sm" name="level"  ng-model="trial.status"  ng-options="y.id as y.name for y in projectStatus"  ><option value="">全选</option></select></span>
    </div>
    <div class="row">
    	<span class="col-md-2 text-right"><label class="form-control-static">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;院</label></span>
		<span class="col-md-2"><select class="form-control input-sm" ng-model="trial.departmentName"  ng-options="y for y in requests |uniKey:'departmentName'"  ><option value="">全选</option></select></span>
		<span class="col-md-2 text-right"><label class="form-control-static">项目类别</label></span>
		<span class="col-md-2"><select class="form-control input-sm" ng-model="trial.typeName"  ng-options="y for y in requests |uniKey:'type'"  ><option value="">全选</option></select></span>
<%--		<span ng-if="!trial.isCurrent">--%>
<%--			<span class="col-md-2 text-right"><label class="form-control-static">批&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次</label></span>--%>
<%--			<span class="col-md-2"><select class="form-control input-sm" ng-model="trial.bn"  ng-options="y for y in requests | uniKey:'bn'"  ><option value="">全选</option></select></span>--%>
<%--		</span>--%>
    </div>

</div>
<div class="modal-body well"  style="overflow:scroll; width:930px; height:650px; position:relative">
		<table class="table table-hover" style="width:1300px">
			<thead>
				<tr>
					<th>序号</th>
					<th>项目名称</th>
					<th  ng-if="!trial.isCurrent">批次</th>
					<th>负责人姓名</th>
					<th>职称</th>	
					<th>学历</th>	
					<th>岗位</th>
					<th>学院</th>
					<th>学科门类</th>
					<th>一级专业</th>		
					<th>项目等级</th>		
					<th>项目类别</th>
					<th>学院意见</th>
				</tr>
			</thead>
			<tbody id="listBody">
<%--			|filter:{majorTypeName:majorType}--%>
				<tr ng-repeat="item in requests | filter:listConditions" ng-class="{'danger':item.status==5,'success':item.status==4 || item.status==6,'info':item.status<4}">	
				<td>{{$index+1}}</td>
				<td><a href="#" ng-click="details(item.id)">{{item.projectName}}</a></td>
				<td  ng-if="!trial.isCurrent">{{item.bn}}</td>
				<td>{{item.userName}}</td>
				<td>{{item.currentTitle}}</td>
				<td>{{item.currentDegree}}</td>
				<td>{{item.position}}</td>
				<td>{{item.departmentName}}</td>
				<td>{{item.discipline}}</td>
				<td>{{item.major}}</td>
				<td>{{levelText(item.projectLevel)}}</td>
				<td>{{item.type}}</td>	
				<td>{{item.collegeAudit}}</td>
				</tr>
			</tbody>
		</table>
		
</div>
<div class="modal-footer" ng-show="trial.bn"> 
<a class="btn btn-default" href="/tms/qemProjectAdmin/exportAttach/{{trial.bn}}">导出附件</a> 
<a class="btn btn-default" href="/tms/qemProjectAdmin/exportReqs">导出汇总表</a> 
</div>