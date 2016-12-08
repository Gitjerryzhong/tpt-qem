<div ng-class="{'col-sm-9':auditAble(task)}" id="mainLayout">
<div class="panel panel-default"  id="baseInfo" >
<div class="panel-heading title">基本信息</div>
<div class="form-group">
	<label class="col-sm-2 control-label">负责人</label>
    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.userName}}</span></div>
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>
    <label class="col-sm-2 control-label" ng-if="task.buget">批准金额</label>
    <div class="col-sm-2 form-control-static" ng-if="task.buget"><span>{{task.buget}}万元</span></div>     
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" ng-if="task.members">参与人</label>
    <div class="col-sm-6 form-control-static" ng-if="task.members"><span>{{task.members}}</span></div>    
	 <label for="team" class="col-sm-2 control-label">当前状态</label>
	    <div class="col-sm-2 form-control-static"><span class="badge" ng-class="{'agree':task.runStatus==1101 || task.runStatus==2101 || task.runStatus==3101,'reject':task.runStatus==1102 || task.runStatus==2102 || task.runStatus==3102}">{{statusText(task.runStatus)}}</span></div>
</div>
<div class="form-group" ng-if="task.fundingUniversity">	    
	    <label for="projectContent" class="col-sm-2 control-label">省级划拨</label>
	     <div class="col-sm-2 form-control-static"><span >{{task.fundingProvince}}万</span></div>
	     <label for="projectContent" class="col-sm-2 control-label">学校划拨</label>
	    <div class="col-sm-2  form-control-static">
	    	<span>{{task.fundingUniversity}}万</span>
	    </div>
	    <label for="projectContent" class="col-sm-2 control-label">院级配套</label>
	    <div class="col-sm-2  form-control-static">
	    	<span>{{task.fundingCollege}}万</span>
	    </div>
    </div>
<div class="form-group">  
	 <label for="beginYear" class="col-sm-2 control-label">立项日期</label>
     <div class="col-sm-2 form-control-static"><span>{{task.beginYear}}</span></div>
    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>
    <div class="col-sm-2 form-control-static"><span>{{task.expectedMid}}</span></div>
    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>
     <div class="col-sm-2 form-control-static"><span >{{task.expectedEnd}}</span></div>
</div>
<div class="form-group">  
    <label for="expectedMid" class="col-sm-2 control-label">项目类型</label>
    <div class="col-sm-6 form-control-static"><span>{{task.qemTypeName}}</span></div>
    <label for="expectedEnd" class="col-sm-2 control-label">项目等级</label>
     <div class="col-sm-2 form-control-static"><span >{{levelText(task.projectLevel)}}</span></div>
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
</div>
<div class="panel panel-default"  id="mainInfo" >
<div class="panel-heading title">阶段检查信息</div>
<div ng-repeat="item in stages | orderBy:'currentStage'">
	<div class="form-group">	    
	    <label for="projectContent" class="col-sm-2 control-label">检查年份</label>
	     <div class="col-sm-2 form-control-static"><span >{{item.submitYear}}</span></div>
	     <label for="projectContent" class="col-sm-2 control-label">完成时间</label>
	    <div class="col-sm-2  form-control-static">
	    	<span>{{dateFormat(item.finishDate) | date:'yyyy-MM-dd'}}</span>
	    </div>
	    <label for="projectContent" class="col-sm-2 control-label">评审状态</label>
	    <div class="col-sm-2  form-control-static">
	    	<span>{{item.status | stageStatus}}</span>
	    </div>
    </div>
    <div class="form-group" ng-if="task.fundingUniversity">	    
	    <label for="projectContent" class="col-sm-2 control-label">省级划拨</label>
	     <div class="col-sm-2 form-control-static"><span >{{item.fundingProvince}}</span></div>
	     <label for="projectContent" class="col-sm-2 control-label">学校划拨</label>
	    <div class="col-sm-2  form-control-static">
	    	<span>{{item.fundingUniversity}}</span>
	    </div>
	    <label for="projectContent" class="col-sm-2 control-label">院级划拨</label>
	    <div class="col-sm-2  form-control-static">
	    	<span>{{item.fundingCollege}}</span>
	    </div>
    </div>
	<div class="form-group">
    <label for="projectContent" class="col-sm-2 control-label">进展正文</label>
    <div class="col-sm-10">
    	<pre>{{item.progressText}}</pre>
    </div>
</div>
</div>
</div>
<div class="panel panel-default"  id="attchInfo" >
<div class="panel-heading title">附  件<a href="/tms/qemTaskAdmin/downloadAttch_T/{{task.id}}">（下载全部<span class="glyphicon glyphicon-download-alt"></span>）</a></div>
<div class="form-group" ng-if="(fileList | filter:'申报书').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=申报书"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>申报书</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'申报书'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="(fileList | filter:'合同').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=合同"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>合同</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'合同'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="(fileList | filter:'年度').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=年度"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>年度</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'年度'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="(fileList | filter:'中期').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=中期"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>中期</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'中期'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="(fileList | filter:'结题').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=结题"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>结题</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'结题'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>

</div>
<div class="form-group" ng-if="task.otherLinks">
<label  class="col-md-2 control-label">相关网址</label>
<ul class="col-md-10 form-control-static">
	<li ng-repeat="site in task.otherLinks.split('\n')" ><a href="{{site}}" target="_brank">{{site}}</a></li>
</ul>
</div>
</div>
<div id="rightSlideLayout" class="col-sm-3" ng-if="auditAble(task)">
<form name="myForm" role="form" novalidate>
<div class="panel panel-info">
	<div class="panel-heading">
    <h4 class="panel-title">阶段检查</h4>
    </div>
    <div class="panel-body">
    	 <label>学院意见</label>
    	 <pre>{{task.collegeAudit}}</pre>
	    <label for="result">审核意见</label>
		<textarea name="result"  rows="4" class="form-control" placeholder="请输入意见！不少于6个字！" ng-model="trial.content" required></textarea>
<%--		<label for="province">省级划拨</label>--%>
<%--	    <input id="provice" type="text" class="form-control" ng-model="trial.fundingProvince">--%>
<%--	    <label for="university">学校划拨</label>--%>
<%--	    <input id="university" type="text" class="form-control" ng-model="trial.fundingUniversity">--%>
<%--	    <label for="college">学院划拨</label>--%>
<%--	    <input id="college" type="text" class="form-control" ng-model="trial.fundingCollege">--%>
		
    </div>
    <div class="panel-footer">
    	<div class="btn-group">
				<button class="btn btn-success btn-sm" ng-click="okS('20',task.id,task.preid,task.nextid)" ng-disabled="myForm.$invalid || trial.content.length<6"> 通过</button>
			<button class="btn btn-warning btn-sm" ng-click="okS('21',task.id,task.preid,task.nextid)" ng-disabled="myForm.$invalid || trial.content.length<6"> 不通过</button>
		</div>
 		<div class="btn-group" style="margin-top:10px">
			<button class="btn btn-default btn-sm" ng-click="okS('26',task.id,task.preid,task.nextid)" ng-disabled="myForm.$invalid || trial.content.length<6"> 退回</button>
			<button class="btn btn-default btn-sm" ng-click="okS('27',task.id,task.preid,task.nextid)" ng-disabled="myForm.$invalid || trial.content.length<6" ng-if="delayBtnText(task)"> {{delayBtnText(task)}}</button>
		</div>	
	</div>
</div>
</form>
</div>