<div class="well" ng-class="{'col-sm-9':task.runStatus==10 || task.runStatus==20 || task.runStatus==30}">
<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>基本信息</strong></h4>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label">负责人</label>
    <div class="col-sm-2 form-control-static"><span >{{task.userName}}</span></div>
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span >{{task.sn}}</span></div>
    <label class="col-sm-2 control-label">批准金额</label>
    <div class="col-sm-2 form-control-static"><span>{{task.buget}}万元</span></div>     
</div>
<div class="form-group" >
	<label class="col-sm-2 control-label">参与人</label>
    <div class="col-sm-10 form-control-static"><span>{{task.members}}</span></div>    

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
<div class="form-group">
    <label for="projectContent" class="col-sm-2 control-label">主要内容</label>
    <div class="col-sm-10">
    	<pre>{{task.projectContent}}</pre>
    </div>
</div>
<div class="form-group">
    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10">
    	<pre>{{task.expectedGain}}</pre>
    </div>    
</div>
<div ng-repeat="item in stages | orderBy:'currentStage'">
	<div class="form-group">
	<h4 class="col-md-offset-4 col-md-4 control-label" style="text-align:center;padding-bottom:15px"><strong>{{stageTitle(item.currentStage)}}</strong></h4>
	</div>
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
    <div class="col-sm-10">
    	<pre>{{item.progressText}}</pre>
    </div>
</div>
</div>
<div class="form-group">
<h4 class="col-md-offset-4 col-md-4 control-label" style="text-align:center;padding-bottom:15px"><strong>附    件</strong><a href="/tms/qemCollegeCheck/downloadAttch_T/{{task.id}}">（下载全部<span class="glyphicon glyphicon-download-alt"></span>）</a></h4>
</div>
<div class="form-group" ng-if="fileList">
<label for="doc" class="col-md-2 control-label">已上传附件</label>
<div class="col-md-10 ">
<ul class="col-md-10 form-control-static">
	<li ng-repeat="filename in fileList" >{{filename}}</li>
</ul>
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