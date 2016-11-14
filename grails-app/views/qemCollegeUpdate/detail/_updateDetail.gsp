<div class="form-group">
<h3 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>重要事项变更审批表</strong></h4>
</div>
<div class="form-group" style="margin-right:2px">
<label class="col-sm-2 control-label">变更内容<span class="glyphicon glyphicon-star Emphasis"></span></label>
	<div class="col-sm-2 form-control-static"><span>{{updateText(form.updateType)}}</span></div>
	<label class="col-sm-2 control-label">原成果形式</label>
	<div class="col-sm-2 form-control-static"><span>{{form.origStyle}}</span></div>
	<label class="col-sm-2 control-label">提交日期</label>
	<div class="col-sm-2 form-control-static"><span>{{form.commitDate}}</span></div>
</div>
<div class="form-group" style="margin-right:2px">
	 <label class="col-sm-2 control-label">变更理由</label>
	 <div class="col-sm-10">
	    	<pre>{{form.memo}}</pre>
	    </div>
</div>
<accordion close-others="false" >
<accordion-group is-open="status1.open">
    <accordion-heading >
<div class="form-group">
<div class="pull-right hand" style="margin-right:10px"><span><span ng-if="status1.open" toolTip="点击隐藏">⊟</span><span ng-if="!status1.open" toolTip="点击展开">⊞</span></span></div>
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center" ><strong>变更项目负责人信息</strong></h4>
</div>
	</accordion-heading>
<div>
<div class="form-group">
    <label for="teacherName" class="col-sm-2 control-label">姓名</label>
    <div class="col-sm-2 form-control-static"><span>{{form.teacherName}}</span><span ng-if="form.teacherName!=task.origTeacherName" class="Emphasis">【{{task.origTeacherName}}】</span></div>
    <label for="currentTitle" class="col-sm-2 control-label">工号</label>
    <div class="col-sm-2 form-control-static"><span>{{form.teacherId}}</span><span ng-if="form.teacherId!=task.teacher.id" class="Emphasis">【{{task.teacher.id}}】</span></div>
    <label for="currentDegree" class="col-sm-2 control-label">学位</label>
    <div class="col-sm-2 form-control-static"><span>{{form.currentDegree}}</span><span ng-if="form.currentDegree!=task.currentDegree" class="Emphasis">【{{task.currentDegree}}】</span></div>
</div>
<div class="form-group">
    <label for="position" class="col-sm-2 control-label">职务</label>
    <div class="col-sm-2 form-control-static"><span>{{form.position}}</span><span ng-if="form.position!=task.position" class="Emphasis">【{{task.position}}】</span></div>
     <label for="currentTitle" class="col-sm-2 control-label">职称</label>
    <div class="col-sm-2 form-control-static"><span>{{form.currentTitle}}</span><span ng-if="form.currentTitle!=task.currentTitle" class="Emphasis">【{{task.currentTitle}}】</span></div>
    <label for="phoneNum" class="col-sm-2 control-label">电话</label>
   <div class="col-sm-2 form-control-static"><span>{{form.phoneNum}}</span><span ng-if="form.phoneNum!=task.phoneNum" class="Emphasis">【{{task.phoneNum}}】</span></div>
</div>
<div class="form-group">
	<label for="specailEmail" class="col-sm-2 control-label">Email</label>
	<div class="col-sm-2 form-control-static"><span>{{form.specailEmail}}</span></div>
</div>
</div>
</accordion-group>
<accordion-group is-open="status2.open">
    <accordion-heading >
<div class="form-group">
<div class="pull-right hand" style="margin-right:10px"><span><span ng-if="status2.open" toolTip="点击隐藏">⊟</span><span ng-if="!status2.open" toolTip="点击展开">⊞</span></span></div>
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;"><strong>变更项目信息</strong></h4>
</div>
	</accordion-heading>
<div>
<div class="form-group">
	 <label for="projectName" class="col-sm-2 control-label">项目名称</label>
	 <div class="col-sm-4 form-control-static"><span>{{form.projectName}}</span><span ng-if="form.projectName!=task.projectName" class="Emphasis">【{{task.projectName}}】</span></div>
    <label  class="col-sm-2 control-label">参与人</label>
     <div class="col-sm-4 form-control-static"><span>{{form.members}}</span><span ng-if="form.members!=task.members" class="Emphasis">【{{task.members}}】</span></div>
</div>

<div class="form-group">
    <label for="qemType" class="col-sm-2 control-label">项目类别</label>
    <div class="col-sm-4 form-control-static"><span>{{form.type}}</span><span ng-if="form.type!=task.origType" class="Emphasis">【{{task.origType}}】</span></div>
    <label for="projectLevel" class="col-sm-2 control-label">项目等级</label>
    <div class="col-sm-4 form-control-static"><span>{{levelText(form.projectLevel)}}</span><span ng-if="form.projectLevel!=task.projectLevel" class="Emphasis">【{{levelText(task.projectLevel)}}】</span></div>
</div>
<div class="form-group">  
	 <label for="beginYear" class="col-sm-2 control-label">立项日期</label>
	  <div class="col-sm-2 form-control-static"><span>{{form.beginYear}}</span><span ng-if="form.beginYear!=task.beginYear" class="Emphasis">【{{task.beginYear}}】</span></div>
    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>
     <div class="col-sm-2 form-control-static"><span>{{form.expectedMid}}</span><span ng-if="form.expectedMid!=task.expectedMid" class="Emphasis">【{{task.expectedMid}}】</span></div>
    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>
     <div class="col-sm-2 form-control-static"><span>{{form.expectedEnd}}</span><span ng-if="form.expectedEnd!=task.expectedEnd" class="Emphasis">【{{task.expectedEnd}}】</span></div>
</div>
<div class="form-group">
    <label  class="col-sm-2 control-label">研究内容</label>
    <div class="col-sm-10">
	    	<pre>{{form.projectContent}}</pre>
	</div>
	<div ng-if="form.projectContent!=task.projectContent">
		 <label  class="col-sm-2 control-label Emphasis">原研究内容</label>
		<div class="col-sm-10 form-control-static" >
		    	<pre>{{task.projectContent}}</pre>
		</div>
	</div>	
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10">
	    	<pre>{{form.expectedGain}}</pre>
	</div>
	<div ng-if="form.expectedGain!=task.expectedGain">
		<label class="col-sm-2 control-label Emphasis">原预期成果</label>
		<div class="col-sm-10 form-control-static">
		    	<pre>{{task.expectedGain}}</pre>
		</div>
	</div>
</div>
</div>
</accordion-group>
<accordion-group is-open="status3.open">
    <accordion-heading >
<div class="form-group">
<div class="pull-right hand" style="margin-right:10px"><span><span ng-if="status3.open" toolTip="点击隐藏">⊟</span><span ng-if="!status3.open" toolTip="点击展开">⊞</span></span></div>
<h4 class="col-md-offset-4 col-md-4 control-label" style="text-align:center"><strong>附    件</strong></h4>
</div>
</accordion-heading>
<div>
<div class="form-group" ng-show="fileList">
<label for="doc" class="col-md-2 control-label"><a href="/tms/qemCollegeUpdate/downloadAttch/{{form.id}}"><span class="glyphicon glyphicon-download-alt" toolTip="下载全部"></span></a>已上传附件</label>
<ul class="col-md-10 form-control-static">
	<li ng-repeat="filename in fileList">{{filename}}</li>
</ul>
</div>
</div>
</accordion-group>
</accordion>
