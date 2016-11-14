<div class="panel panel-default">
<div class="panel-heading title">{{stageTitle(stage.currentStage)}}
</div>
<div class="form-body">
<div class="form-group">
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span >{{stage.sn}}</span></div>
    <label class="col-sm-2 control-label">参与人</label>
    <div class="col-sm-6 form-control-static"><span>{{stage.members}}</span></div>     
</div>
<div class="form-group">
    <label  class="col-sm-2 control-label">完成日期</label>
    <div class="col-sm-3 form-control-static"> <span>{{dateFormat(stage.finishDate) | date : 'yyyy-MM-dd'}}</span> </div>
</div>
<div class="form-group">
    <label for="projectContent" class="col-sm-2 control-label">{{isEnd()?"主要内容和特色":"进展正文"}}</label>
    <div class="col-sm-10" ng-if="stage.progressText">
    	<p>{{stage.progressText}}</p>
    </div>
</div>
<div class="form-group">
    <label for="unfinishedReson" class="col-sm-2 control-label">{{isEnd()?"主要成果":"未完成原因"}}</label>
    <div class="col-sm-10" ng-if="stage.unfinishedReson">
    	<p>{{stage.unfinishedReson}}</p>
    </div>
</div>
<div class="form-group">
    <label for="memo" class="col-sm-2 control-label">{{isEnd()?"成果应用情况":"其他说明"}}</label>
    <div class="col-sm-10" ng-if="stage.memo">
    	<p>{{stage.memo}}</p>
    </div>
</div>
</div>
</div>
<div class="panel panel-default">
<div class="panel-heading title">附    件
</div>
<div class="form-group" ng-if="uploadQueue">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTask/download?taskId={{task.id}}&fileType={{uploadType()}}"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>{{uploadType()}}附件</label>
<div class="col-md-10 ">
<ul class="col-md-10 form-control-static">
	<li ng-repeat="item in uploadQueue" >{{item.file.name}}</li>
</ul>
</div>
</div>
</div>

























<%--<div class="form-group">--%>
<%--<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>任务书内容</strong></h4>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label class="col-sm-2 control-label">项目编号</label>--%>
<%--    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>--%>
<%--    <label class="col-sm-2 control-label">批准金额</label>--%>
<%--    <div class="col-sm-2 form-control-static"><span>{{task.budget}}万元</span></div>--%>
<%--    <label for="team" class="col-sm-2 control-label">成员姓名</label>--%>
<%--    <div class="col-sm-2 form-control-static"><span>{{task.memberstr}}</span></div>--%>
<%--</div>--%>
<%--<div class="form-group"> --%>
<%--	 <label for="beginYear" class="col-sm-2 control-label">立项年份</label>--%>
<%--	 <div class="col-sm-2 form-control-static"><span>{{task.beginYear}}</span></div>     --%>
<%--    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>--%>
<%--    <div class="col-sm-2 form-control-static"><span>{{task.expectedMid}}</span></div>--%>
<%--    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>--%>
<%--    <div class="col-sm-2 form-control-static"><span>{{task.expectedEnd}}</span></div>    --%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label for="projectContent" class="col-sm-2 control-label">主要内容</label>--%>
<%--    <div class="col-sm-10">--%>
<%--    	<pre>{{task.projectContent}}</pre>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>--%>
<%--    <div class="col-sm-10">--%>
<%--    	<pre>{{task.expectedGain}}</pre>--%>
<%--    </div>    --%>
<%--</div>--%>
<%----%>
