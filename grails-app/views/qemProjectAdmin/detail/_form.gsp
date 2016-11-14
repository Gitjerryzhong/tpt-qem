<div class="row" style="margin-bottom:20px">
<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>项目负责人信息</strong></h4>
</div>
<div class="form-group striped">
    <label for="currentTitle" class="col-sm-1 control-label">工号</label>
    <div class="col-sm-2 form-control-static">
    	<span>{{project.userId}}</span>
    </div>
    <label for="currentDegree" class="col-sm-1 control-label">姓名</label>
    <div class="col-sm-2 form-control-static">
    	<span>{{project.userName}}</span>
    </div>
    <label for="currentTitle" class="col-sm-1 control-label">职称</label>
    <div class="col-sm-2 form-control-static">
    	 <span>{{project.currentTitle}}</span>
    </div>
    <label for="currentDegree" class="col-sm-1 control-label">学位</label>
    <div class="col-sm-2 form-control-static">
    	 <span>{{project.currentDegree}}</span>
    </div>
</div>
<div class="form-group">
	<label for="position" class="col-sm-1 control-label">学院</label>
    <div class="col-sm-2 form-control-static">
    	<span>{{project.departmentName}}</span>
    </div>
    <label for="position" class="col-sm-1 control-label">职务</label>
    <div class="col-sm-2 form-control-static">
    	<span>{{project.position}}</span>
    </div>
    <label for="phoneNum" class="col-sm-1 control-label">电话</label>
    <div class="col-sm-2 form-control-static">
    	<span>{{project.phoneNum}}</span>
    </div>
    <label for="specailEmail" class="col-sm-1 control-label">Email</label>
    <div class="col-sm-2 form-control-static">
    	<span>{{project.specailEmail}}</span>
    </div>
</div>
<div class="form-group striped">
    <label for="discipline" class="col-sm-2 control-label">学科门类</label>
    <div class="col-sm-2 form-control-static">
    	<span>{{clearNull(project.discipline)}}</span>
    </div>
    <label for="major" class="col-sm-2 control-label">一级专业</label>
    <div class="col-sm-2 form-control-static">
   		<span>{{project.majorName}}</span>
    </div> 
    <label for="direction" class="col-sm-2 control-label">方向</label>
    <div class="col-sm-2 form-control-static">
    	<span>{{clearNull(project.direction)}}</span>
    </div>
</div>

<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>项目信息</strong></h4>
</div>
<div class="form-group">
    <label for="qemType" class="col-sm-2 control-label">项目名称</label>
    <div class="col-sm-2 form-control-static">
   		<span>{{project.projectName}}</span>
    </div>
    <label for="qemType" class="col-sm-2 control-label">项目类别</label>
    <div class="col-sm-2 form-control-static">
   		<span>{{project.qemTypeName}}</span>
    </div>
    <label for="projectLevel" class="col-sm-2 control-label">项目等级</label>
    <div class="col-sm-2 form-control-static">
    	<span>{{levelText(project.projectLevel)}}</span>
    </div>
</div>
<div class="form-group">
	    <label for="" class="col-sm-2 control-label">操作日志</label>
	    <div class="col-sm-10" >
	    <ul class="form-control-static list-unstyled">
			<li ng-repeat="item in audits | orderBy:'id'">				
	    			{{dateFormat(item.date) | date : 'yyyy-MM-dd HH:mm:ss'}}{{item.userName}} {{actionText(item.action)}} {{item.content}}
			</li>			
		</ul>
	    </div>
</div>	
<div class="form-group">
    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10 form-control-static">
    	<pre>{{project.expectedGain}}</pre>
    </div>
</div>
<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>附    件</strong><a href="/tms/qemProjectAdmin/downloadAttch/{{project.id}}">（下载全部<span class="glyphicon glyphicon-download-alt"></span>）</a></h4>
</div>
<div class="form-group" ng-if="fileList">
<label  class="col-md-2 control-label">已上传附件</label>
<ul class="col-md-10 form-control-static">
	<li ng-repeat="filename in fileList" ><a href="#" ng-click="selectItem1($index)">{{filename}}</a><span ng-show="attSelected==$index && !(project.commit)" class="glyphicon glyphicon-remove hand" style="margin-left:100px" ng-click="remove(filename)"></span></li>
</ul>
</div>
<div class="form-group" ng-if="project.otherLinks">
<label  class="col-md-2 control-label">相关网址</label>
<ul class="col-md-10 form-control-static">
	<li ng-repeat="site in project.otherLinks.split('\n')" ><a href="{{site}}" target="_brank">{{site}}</a></li>
</ul>
</div>
</div>