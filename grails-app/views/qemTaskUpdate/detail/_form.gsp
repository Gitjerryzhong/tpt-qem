<div class="form-group">
	<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>任务书内容</strong></h4>
	</div>
	<div class="form-group">
	    <label class="col-sm-2 control-label">项目编号</label>
	    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>
	    <label class="col-sm-2 control-label">负责人</label>
	    <div class="col-sm-2 form-control-static"><span>{{task.userName}}</span></div>
	    <label for="team" class="col-sm-2 control-label">当前状态</label>
	    <div class="col-sm-2 form-control-static">{{statusTT(task.status)}}</div>
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
	    <label class="col-sm-2 control-label">省级划拨</label>
	    <div class="col-sm-2 form-control-static"><span>{{task.fundingProvince}}万元</span></div>
	    <label class="col-sm-2 control-label">校级划拨</label>
	    <div class="col-sm-2 form-control-static"><span>{{task.fundingUniversity}}万元</span></div>
	    <label for="team" class="col-sm-2 control-label">学院配套</label>
	    <div class="col-sm-2 form-control-static"><span>{{task.fundingCollege}}万元</span></div>
	</div>
	<div class="form-group"> 
		 <label for="beginYear" class="col-sm-2 control-label">立项日期</label>
		 <div class="col-sm-2 form-control-static"><span>{{task.beginYear}}</span></div>     
	    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>
	    <div class="col-sm-2 form-control-static"><span>{{task.expectedMid}}年</span></div>
	    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>
	    <div class="col-sm-2 form-control-static"><span>{{task.expectedEnd}}年</span></div>    
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
	
	<div class="form-group">
	<h4 class="col-md-offset-4 col-md-4 control-label" style="text-align:center;padding-bottom:15px"><strong>附    件</strong><a href="/tms/qemTaskAdmin/downloadAttch_T/{{task.id}}">（下载全部<span class="glyphicon glyphicon-download-alt"></span>）</a></h4>
	</div>
	<div class="form-group" ng-show="fileList">
		<label for="doc" class="col-md-2 control-label">已上传附件</label>
		<div class="col-md-10 ">
			<ul class="col-md-10 form-control-static">
				<li ng-repeat="filename in fileList" >{{filename}}</li>
			</ul>
		</div>
	</div>