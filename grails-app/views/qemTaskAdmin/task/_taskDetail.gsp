<div class="col-sm-9">
	<div class="form-group">
	<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>任务书内容</strong></h4>
	</div>
	<div class="form-group">
	    <label class="col-sm-2 control-label">项目编号</label>
	    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>
	    <label class="col-sm-2 control-label">负责人</label>
	    <div class="col-sm-2 form-control-static"><span>{{task.userName}}</span></div>
	    <label for="team" class="col-sm-2 control-label">当前状态</label>
	    <div class="col-sm-2 form-control-static"><span class="badge" ng-class="{'agree':task.runStatus==201,'reject':task.runStatus==202}">{{statusText(task.runStatus)}}</span></div>
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
	    <label for="expectedGain" class="col-sm-2 control-label">学院意见</label>
	    <div class="col-sm-10">
	    	<pre>{{task.collegeAudit}}</pre>
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
	<div class="form-group" ng-if="task.otherLinks">
		<label  class="col-md-2 control-label">相关网址</label>
		<ul class="col-md-10 form-control-static">
			<li ng-repeat="site in task.otherLinks.split('\n')" ><a href="{{site}}" target="_brank">{{site}}</a></li>
		</ul>
	</div>
</div>
<div class="col-sm-3" class="bs-docs-sidebar affix">
	<form name="myForm" role="form" novalidate>
	<div ng-if="task.runStatus==201 || task.runStatus==202" class="panel panel-info">
				<div class="panel-heading">
			    <h4 class="panel-title">合同审核</h4>
			    </div>
			    <div class="panel-body">
				    <label for="result">审核意见</label>
							<textarea name="content"  rows="4" class="form-control" placeholder="请输入意见！不少于6个字！" ng-model="trial.content" required></textarea>
			    </div>
			    <div class="panel-footer">
			 			<button class="btn btn-success btn-sm" ng-click="okT('20',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 同意</button>
						<button class="btn btn-warning btn-sm" ng-click="okT('21',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 不同意</button>
						<button class="btn btn-danger btn-sm" ng-click="okT('26',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 退回</button>
			    	
				</div>
	</div>
	</form>
	<div class="panel panel-info">
			<div class="panel-heading">
		    <h4 class="panel-title">备注</h4>
		    </div>
		    <div class="panel-body">
				<textarea name="content"  rows="4" class="form-control" placeholder="请输入备注！" ng-model="task.memo" required></textarea>
		    </div>
		    <div class="panel-footer">
		 			<button class="btn btn-primary btn-sm"  ng-click="updateMemo(task)" ng-disabled="trial.memo.length<1"> 更新</button>
		    		<span class="btn btn-success btn-sm fade-in" style="margin-left:20px" ng-if="task.updateSuccess">更新成功！</span>
			</div>
	</div>
</div>