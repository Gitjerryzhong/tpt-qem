<div class="panel panel-default">
<div class="panel-heading title">基本信息
<%--<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>基本信息</strong></h4>--%>
</div>
<div class="form-body">
<div class="form-group" >
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>
    <label class="col-sm-2 control-label">项目等级</label>
    <div class="col-sm-1 form-control-static"><span>{{levelText(task.projectLevel)}}</span></div>
    <label for="team" class="col-sm-2 control-label">项目类别</label>
    <div class="col-sm-3 form-control-static"><span>{{task.qemTypeName}}</span></div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">流程进度</label>
    <div class="col-sm-2 form-control-static">{{statusText(task.runStatus)}}</div>
    <label class="col-sm-2 control-label">负责人</label>
    <div class="col-sm-1 form-control-static"><span>{{task.userName}}</span></div>
    <label class="col-sm-2 control-label">所属单位</label>
    <div class="col-sm-3 form-control-static"><span>{{task.departmentName}}</span></div>
</div>
<div class="form-group" ng-if="task.memberstr">
	<label for="team" class="col-sm-2 control-label">成员姓名</label>
    <div class="col-sm-10 form-control-static"><span>{{task.memberstr}}</span></div>
</div>
<div class="form-group"> 
	 <label for="beginYear" class="col-sm-2 control-label">立项日期</label>
	 <div class="col-sm-2 form-control-static"><span>{{task.beginYear}}</span></div>     
    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>
    <div class="col-sm-1 form-control-static"><span>{{task.expectedMid}}</span></div>
    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>
    <div class="col-sm-3 form-control-static"><span>{{task.expectedEnd}}</span></div>    
</div>
<div class="form-group" ng-if="task.fundingUniversity">
    <label class="col-sm-2 control-label">省级划拨</label>
    <div class="col-sm-2 form-control-static"><span>{{task.fundingProvince}}</span></div>
    <label class="col-sm-2 control-label">校级划拨</label>
    <div class="col-sm-1 form-control-static"><span>{{task.fundingUniversity}}万元</span></div>
    <label for="team" class="col-sm-2 control-label">学院配套</label>
    <div class="col-sm-3 form-control-static"><span>{{task.fundingCollege}}</span></div>
</div>
<div class="form-group" ng-if="task.projectContent">
    <label for="projectContent" class="col-sm-2 control-label">主要内容</label>
    <div class="col-sm-10 form-control-static">
    	<p>{{task.projectContent}}</p>
    </div>
</div>
<div class="form-group" ng-if="task.expectedGain">
    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10  form-control-static">
    	<p>{{task.expectedGain}}</p>
    </div>    
</div>
<div class="form-group" ng-if="task.auditContent" style="border-bottom:0px">
    <label for="expectedGain" class="col-sm-2 control-label">立项评审意见</label>
    <div class="col-sm-10  form-control-static">
    	<p>{{task.auditContent}}</p>
    </div>    
</div>
<div class="form-group" ng-if="task.contractAudit" style="border-bottom:0px">
    <label for="expectedGain" class="col-sm-2 control-label">合同审核意见</label>
    <div class="col-sm-10  form-control-static">
    	<p>{{task.contractAudit}}</p>
    </div>    
</div>
</div>
</div>
<div class="panel panel-default">
<div class="panel-heading title">附    件
</div>
<div class="form-group" ng-if="fileList">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTask/download?taskId={{task.id}}&fileType=申报书"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>申报书</label>
<div class="col-md-10 ">
<%--<a class="col-md-2 form-control-static" ng-href="{{filename}}" ng-repeat="filename in fileList">{{getFileName(filename)}}</a>--%>
<ul class="form-control-static">
	<li ng-repeat="filename in declarations" >
			<span>{{filename}}</span>
	</li>
</ul>
</div>
<label for="doc" class="col-md-2 control-label" ng-if="contracts"><a href="/tms/qemTask/download?taskId={{task.id}}&fileType=合同"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>合同</label>
<div class="col-md-10 ">
<ul class="form-control-static">
	<li ng-repeat="filename in contracts" >
			<span>{{filename}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="task.otherLinks">
<label  class="col-md-2 control-label">相关网址</label>
<ul class="col-md-10 form-control-static">
	<li ng-repeat="site in task.otherLinks.split('\n')" ><a href="{{site}}" target="_brank">{{site}}</a></li>
</ul>
</div>
</div>