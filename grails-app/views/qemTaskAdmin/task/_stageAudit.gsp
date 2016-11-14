<div class="col-sm-9 well">
<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>任务书内容</strong></h4>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label">负责人</label>
    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.userName}}</span></div>
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>
    <label class="col-sm-2 control-label">批准金额</label>
    <div class="col-sm-2 form-control-static"><span>{{task.buget}}万元</span></div>     
</div>
<div class="form-group">
	<label class="col-sm-2 control-label">参与人</label>
    <div class="col-sm-6 form-control-static"><span>{{task.members}}</span></div>    
	 <label for="team" class="col-sm-2 control-label">当前状态</label>
	    <div class="col-sm-2 form-control-static"><span class="badge" ng-class="{'agree':task.runStatus==1101 || task.runStatus==2101 || task.runStatus==3101,'reject':task.runStatus==1102 || task.runStatus==2102 || task.runStatus==3102}">{{statusText(task.runStatus)}}</span></div>
</div>
<div class="form-group">	    
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
    <div class="form-group">	    
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
<div class="form-group">
<h4 class="col-md-offset-4 col-md-4 control-label" style="text-align:center;padding-bottom:15px"><strong>附    件</strong><a href="/tms/qemTaskAdmin/downloadAttch_T/{{task.id}}">（下载全部<span class="glyphicon glyphicon-download-alt"></span>）</a></h4>
</div>
<div class="form-group" ng-if="fileList">
<label for="doc" class="col-md-2 control-label">已上传附件</label>
<div class="col-md-10 ">
<ul class="col-md-10 form-control-static">
	<li ng-repeat="filename in fileList" >{{filename}}</li>
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
<div class="col-sm-3">
<div class="bs-docs-sidebar affix panel panel-info">
	<div class="panel-heading">
    <h4 class="panel-title">结果登记及经费划拨</h4>
    </div>
    <div class="panel-body">
	    <label for="result">汇总结果</label>
	    <select id="result" class="form-control" ng-model="summary.result">
	    	<option value="0">通过</option>
	    	<option value="1">不通过</option>
	    </select>
	    <label for="review">汇总意见</label>
	    <textarea  id="review" class="form-control" rows="5" cols="10" ng-model="summary.review"></textarea>
	    <label for="province">省级划拨</label>
	    <input id="provice" type="text" class="form-control" ng-model="summary.fundingProvince">
	    <label for="university">学校划拨</label>
	    <input id="university" type="text" class="form-control" ng-model="summary.fundingUniversity">
	    <label for="college">学院划拨</label>
	    <input id="college" type="text" class="form-control" ng-model="summary.fundingCollege">
    </div>
    <div class="panel-footer">
    	<button class="btn btn-default" ng-click="finalAudit(item)">提交</button>
	</div>
</div>
</div>
