<div class="row">
<div ng-class="{'col-sm-9':updateView.flow==1 &&(updateView.auditStatus==0 || updateView.auditStatus==3),'col-sm-12':updateView.flow!=1 || updateView.auditStatus==1 || updateView.auditStatus==2}">
<div class="panel panel-default"  id="mainInfo" >
<div class="panel-heading title">基本信息</div>
<div class="form-body">
	<div class="form-group" >
	<label class="col-sm-2 control-label">变更内容</label>
		<div class="col-sm-10 form-control-static">
				<p>{{typesText}}</p>
		</div>
	</div>
	<div class="form-group">
		 <label class="col-sm-2 control-label" >变更理由</label>
		 <div class="col-sm-10 form-control-static" ><p>{{updateView.memo}}</p>
		 </div>
	</div>
</div>
</div>

<div class="panel panel-default" >
<div class="panel-heading title">变更项目信息</div>
<table class="table table-bordered" id="updateInfo">
			<thead>
				<tr>
					<th colspan="2" >原项目信息</th>
					<th colspan="2" >变更为</th>
				</tr>
				<tr>
					<th style="width:6em">标题</th>
					<th style="width:30em">内容</th>
					<th style="width:6em">标题</th>
					<th style="width:30em">内容</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-if="hasUpdateType('3')">
					<td class="oldInfo">项目名称</td>
					<td class="oldInfo">{{taskView.projectName}}</td>
					<td><span >项目名称</span></td>
					<td>{{updateView.projectName}}</td>
				</tr>
				<tr ng-if="hasUpdateType('7')">
					<td class="oldInfo">参与人</td>
					<td class="oldInfo">{{taskView.members}}</td>
					<td>参与人</td>
					<td>{{updateView.members}}</td>
				</tr>
				<tr ng-if="hasUpdateType('2')">
					<td class="oldInfo">中期预期</td>
					<td class="oldInfo">{{taskView.expectedMid}}</td>
					<td>中期预期</td>
					<td>{{updateView.expectedMid}}</td>
				</tr>
				<tr ng-if="hasUpdateType('2')">
					<td class="oldInfo">结题预期</td>
					<td class="oldInfo">{{taskView.expectedEnd}}</td>
					<td>结题预期</td>
					<td>{{updateView.expectedEnd}}</td>
				</tr>
				<tr ng-if="hasUpdateType('4')">
					<td class="oldInfo">研究内容</td>
					<td class="oldInfo">{{taskView.projectContent}}</td>
					<td>研究内容</td>
					<td>{{updateView.projectContent}}</td>
				</tr>
				<tr ng-if="hasUpdateType('6')">
					<td class="oldInfo">成果形式</td>
					<td class="oldInfo">{{taskView.expectedGain}}</td>
					<td>成果形式</td>
					<td>{{updateView.expectedGain}}</td>
				</tr>
				<tr ng-if="hasUpdateType('8')">
					<td class="oldInfo"></td>
					<td class="oldInfo"></td>
					<td>其他变更</td>
					<td>{{updateView.others}}</td>
				</tr>
				<tr ng-if="hasUpdateType('1')">
					<td class="oldInfo">负责人</td>
					<td class="oldInfo">{{taskView.teacherName}}</td>
					<td><span >负责人</span></td>
					<td>{{updateView.teacherName}}</td>
				</tr>
				<tr ng-if="hasUpdateType('1')">
					<td class="oldInfo">原职称</td>
					<td class="oldInfo">{{taskView.currentTitle}}</td>
					<td><span >职称</span></td>
					<td>{{updateView.currentTitle}}</td>
				</tr>
				<tr ng-if="hasUpdateType('1')">
					<td class="oldInfo">原职务</td>
					<td class="oldInfo">{{taskView.position}}</td>
					<td><span >职务</span></td>
					<td>{{updateView.position}}</td>
				</tr>
				<tr ng-if="hasUpdateType('1')">
					<td class="oldInfo">原学历</td>
					<td class="oldInfo">{{taskView.currentDegree}}</td>
					<td><span >学历</span></td>
					<td>{{updateView.currentDegree}}</td>
				</tr>
				<tr ng-if="hasUpdateType('1')">
					<td class="oldInfo">原EMAIL</td>
					<td class="oldInfo">{{taskView.specailEmail}}</td>
					<td><span >EMAIL</span></td>
					<td>{{updateView.specailEmail}}</td>
				</tr>
				<tr ng-if="hasUpdateType('1')">
					<td class="oldInfo">原电话</td>
					<td class="oldInfo">{{taskView.phoneNum}}</td>
					<td><span >电话</span></td>
					<td>{{updateView.phoneNum}}</td>
				</tr>
			</tbody>
</table>
</div>
<div class="panel panel-default form-body" >
<div class="panel-heading title">附 件</div>
<div class="form-group" ng-if="declarations">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemUpdateCollegeCheck/downloadT?taskId={{updateView.taskId}}&fileType=申报书"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>原申报书</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in declarations | filter:'申报书'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group"  ng-if="fileList">
<label class="col-sm-2 control-label"><a href="/tms/qemUpdateCollegeCheck/downloadU?taskId={{updateView.id}}&fileType=申报书&isMine=1"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>申报书变更为</label>
<div class="col-sm-10 form-control-static">
	<ul>
		<li ng-repeat="item in fileList"><span>{{getFileName(item)}}</span></li>
	</ul>	
</div>			 
</div>
</div>
</div>
<div class="col-sm-3" ng-if="updateView.flow==1 &&(updateView.auditStatus==0 || updateView.auditStatus==3)">
<form name="myForm" role="form" >
<div class="panel panel-info">
	<div class="panel-heading">
    <h4 class="panel-title">审查意见</h4>
    </div>
    <div class="panel-body">
				<textarea  rows="4" class="form-control" placeholder="请输入意见！不少于6个字！" ng-model="trial.content" required></textarea>
    </div>
    <div class="panel-footer">
 			<button class="btn btn-success" ng-click="okT('20',updateView.id)" ng-disabled="myForm.$invalid || trial.content.length<6"> 同意</button>
			<button class="btn btn-warning" ng-click="okT('21',updateView.id)" ng-disabled="myForm.$invalid || trial.content.length<6"> 不同意</button>
			<button class="btn btn-danger" ng-click="okT('26',updateView.id)" ng-disabled="myForm.$invalid || trial.content.length<6"> 退回</button>
    	
	</div>
</div>
</form>
</div>
</div>