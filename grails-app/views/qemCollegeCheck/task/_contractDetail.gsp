<div ng-class="{'col-sm-9':task.runStatus==1 || task.runStatus==4}">
<div class="panel panel-default"  id="baseInfo" >
<div class="panel-heading title">基本信息</div>
<div class="form-group">
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>
    <label class="col-sm-2 control-label">负责人</label>
    <div class="col-sm-2 form-control-static"><span>{{task.userName}}</span></div>
    <label for="team" class="col-sm-2 control-label">当前状态</label>
    <div class="col-sm-2 form-control-static"><span  class="Emphasis">{{statusText(task.runStatus)}}</span></div>
</div>
<div class="form-group">
    <label for="position" class="col-md-2 control-label">当时职务</label>
    <div class="col-md-2 form-control-static">
    	<span>{{task.position}}</span>
    </div>
    <label for="currentTitle" class="col-md-2 control-label">当时职称</label>
    <div class="col-md-2 form-control-static">
    	 <span>{{task.currentTitle}}</span>
    </div>
    <label for="currentDegree" class="col-md-2 control-label">当时学位</label>
    <div class="col-md-2 form-control-static">
    	 <span>{{task.currentDegree}}</span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">项目类别</label>
    <div class="col-sm-2 form-control-static"><span>{{task.typeName}}</span></div>
    <label for="team" class="col-sm-2 control-label">项目等级</label>
    <div class="col-sm-2 form-control-static"><span>{{(projectLevels | filter:{'id':task.projectLevel})[0].name}}</span></div>
    <label class="col-sm-2 control-label">参与人</label>
    <div class="col-sm-2 form-control-static"><span>{{task.members}}</span></div>
</div>
<div class="form-group"> 
	 <label for="beginYear" class="col-sm-2 control-label">立项日期</label>
	 <div class="col-sm-2 form-control-static"><span>{{task.beginYear}}</span></div>     
    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>
    <div class="col-sm-2 form-control-static"><span>{{task.expectedMid}}年</span></div>
    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>
    <div class="col-sm-2 form-control-static"><span>{{task.expectedEnd}}年</span></div>    
</div>
<div class="form-group" ng-if="task.projectContent">
    <label for="projectContent" class="col-sm-2 control-label">主要内容</label>
    <div class="col-sm-10">
    	<pre>{{task.projectContent}}</pre>
    </div>
</div>
<div class="form-group" ng-if="task.expectedGain">
    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10">
    	<pre>{{task.expectedGain}}</pre>
    </div>    
</div>
<div class="form-group" ng-if="task.collegeAudit">
    <label for="expectedGain" class="col-sm-2 control-label">学院意见</label>
    <div class="col-sm-10">
    	<pre>{{task.collegeAudit}}</pre>
    </div>    
</div>
<div class="form-group" ng-if="task.contractAudit">
    <label for="expectedGain" class="col-sm-2 control-label">学校意见</label>
    <div class="col-sm-10">
    	<pre>{{task.contractAudit}}</pre>
    </div>    
</div>
</div>
<div class="panel panel-default"  id="attchInfo" >
<div class="panel-heading title">附  件<a href="/tms/qemCollegeCheck/downloadAttch_T/{{task.id}}">（下载全部<span class="glyphicon glyphicon-download-alt"></span>）</a></div>
<div class="form-group" ng-if="(fileList | filter:'申报书').length">
<label for="doc" class="col-md-2 control-label">申报书</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'申报书'" >
			<span>{{getFileName1(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="(fileList | filter:'合同').length">
<label for="doc" class="col-md-2 control-label">合同</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'合同'" >
			<span>{{getFileName1(filename)}}</span>
	</li>
</ul>
</div>
</div>
</div>
<%--<div class="form-group" ng-if="fileList">--%>
<%--<label for="doc" class="col-md-2 control-label">已上传附件</label>--%>
<%--<div class="col-md-10 ">--%>
<%--<ul class="col-md-10 form-control-static">--%>
<%--	<li ng-repeat="filename in fileList" >{{filename}}</li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--</div>--%>
</div>
<div class="col-sm-3" ng-if="task.runStatus==1 || task.runStatus==4">
<form name="myForm" role="form" novalidate>
<div class="bs-docs-sidebar affix panel panel-info">
	<div class="panel-heading">
    <h4 class="panel-title">合同审核</h4>
    </div>
    <div class="panel-body">
	    <label for="result">审核意见</label>
				<textarea name="content"  rows="4" class="form-control" placeholder="请输入意见！不少于6个字！" ng-model="trial.content" required></textarea>
    </div>
    <div class="panel-footer">
 			<button class="btn btn-success" ng-click="okT('20',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 同意</button>
			<button class="btn btn-warning" ng-click="okT('21',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 不同意</button>
			<button class="btn btn-danger" ng-click="okT('26',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 退回</button>
    	
	</div>
</div>
</form>
</div>