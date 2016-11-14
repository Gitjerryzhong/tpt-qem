<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>任务书内容</strong></h4>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>
    <label class="col-sm-2 control-label">批准金额</label>
    <div class="col-sm-2 form-control-static"><span>{{task.budget}}万元</span></div>
    <label for="team" class="col-sm-2 control-label">成员姓名</label>
    <div class="col-sm-2 form-control-static"><span>{{task.memberstr}}</span></div>
</div>
<div class="form-group"> 
	 <label for="beginYear" class="col-sm-2 control-label">立项年份</label>
	 <div class="col-sm-2 form-control-static"><span>{{task.beginYear}}</span></div>     
    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>
    <div class="col-sm-2 form-control-static"><span>{{task.expectedMid}}</span></div>
    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>
    <div class="col-sm-2 form-control-static"><span>{{task.expectedEnd}}</span></div>    
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
<div class="form-group" ng-show="fileList">
<h4 class="col-md-offset-4 col-md-4 control-label" style="text-align:center;padding-bottom:15px"><strong>附    件</strong></h4>
</div>
<div class="form-group" ng-show="fileList">
<label for="doc" class="col-md-2 control-label">已上传附件</label>
<a class="col-md-3 form-control-static" ng-href="{{filename}}" ng-repeat="filename in fileList">{{getFileName(filename)}}</a>
</div>

