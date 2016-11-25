<div ng-class="{'col-sm-9':task.runStatus==10 || task.runStatus==20 || task.runStatus==30}">
<div class="panel panel-default"  id="baseInfo" >
<div class="panel-heading title">基本信息</div>
<div class="form-group">
	<label class="col-sm-2 control-label">负责人</label>
    <div class="col-sm-2 form-control-static"><span >{{task.userName}}</span></div>
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span >{{task.sn}}</span></div>
     <label for="expectedEnd" class="col-sm-2 control-label">项目等级</label>
     <div class="col-sm-2 form-control-static"><span >{{levelText(task.projectLevel)}}</span></div>
<%--    <label class="col-sm-2 control-label">批准金额</label>--%>
<%--    <div class="col-sm-2 form-control-static"><span>{{task.buget}}万元</span></div>     --%>
	
</div>
<%--<div class="form-group" >--%>
<%--	<label class="col-sm-2 control-label">参与人</label>--%>
<%--    <div class="col-sm-10 form-control-static"><span>{{task.members}}</span></div>    --%>
<%----%>
<%--</div>--%>
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
    <label class="col-sm-2 control-label">参与人</label>
    <div class="col-sm-2 form-control-static"><span>{{task.members}}</span></div>
</div>
<div class="form-group">
    <label for="projectContent" class="col-sm-2 control-label">主要内容</label>
    <div class="col-sm-10 form-control-static">
    	<p>{{task.projectContent}}</p>
    </div>
</div>
<div class="form-group">
    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10 form-control-static">
    	<p>{{task.expectedGain}}</p>
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
	    	<span>{{statusText(task.runStatus)}}</span>
	    </div>
    </div>
<%--    <div class="form-group">	    --%>
<%--	    <label for="projectContent" class="col-sm-2 control-label">省级划拨</label>--%>
<%--	     <div class="col-sm-2 form-control-static"><span >{{item.fundingProvince}}</span></div>--%>
<%--	     <label for="projectContent" class="col-sm-2 control-label">学校划拨</label>--%>
<%--	    <div class="col-sm-2  form-control-static">--%>
<%--	    	<span>{{item.fundingUniversity}}</span>--%>
<%--	    </div>--%>
<%--	    <label for="projectContent" class="col-sm-2 control-label">院级划拨</label>--%>
<%--	    <div class="col-sm-2  form-control-static">--%>
<%--	    	<span>{{item.fundingCollege}}</span>--%>
<%--	    </div>--%>
<%--    </div>--%>
	<div class="form-group">
    <label for="projectContent" class="col-sm-2 control-label">进展正文</label>
    <div class="col-sm-10 form-control-static">
    	<p>{{item.progressText}}</p>
    </div>
</div>
</div>
</div>
<div class="panel panel-default"  id="attchInfo" >
<div class="panel-heading title">附  件<a href="/tms/qemCollegeCheck/downloadAttch_T/{{task.id}}">（下载全部<span class="glyphicon glyphicon-download-alt"></span>）</a></div>
<div class="form-group" ng-if="(fileList | filter:'申报书').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemUpdateCollegeCheck/downloadT?taskId={{task.id}}&fileType=申报书"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>申报书</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'申报书'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="(fileList | filter:'合同').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemUpdateCollegeCheck/downloadT?taskId={{task.id}}&fileType=合同"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>合同</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'合同'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="(fileList | filter:'年度').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemUpdateCollegeCheck/downloadT?taskId={{task.id}}&fileType=年度"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>年度</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'年度'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="(fileList | filter:'中期').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemUpdateCollegeCheck/downloadT?taskId={{task.id}}&fileType=中期"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>中期</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'中期'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" ng-if="(fileList | filter:'结题').length">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemUpdateCollegeCheck/downloadT?taskId={{task.id}}&fileType=结题"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>结题</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in fileList | filter:'结题'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>

</div>
</div>
<div class="col-sm-3" ng-if="task.runStatus==10 || task.runStatus==20 || task.runStatus==30">
<form name="myForm" role="form" novalidate>
<div class="bs-docs-sidebar affix panel panel-info">
	<div class="panel-heading">
    <h4 class="panel-title">阶段检查</h4>
    </div>
    <div class="panel-body">
	    <label for="result">审查意见</label>
				<textarea  rows="4" class="form-control" placeholder="请输入意见！不少于6个字！" ng-model="trial.content" required></textarea>
    </div>
    <div class="panel-footer">
 			<button class="btn btn-success" ng-click="okT('20',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 同意</button>
			<button class="btn btn-warning" ng-click="okT('21',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 不同意</button>
			<button class="btn btn-danger" ng-click="okT('26',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 退回</button>
    	
	</div>
</div>
</form>
</div>