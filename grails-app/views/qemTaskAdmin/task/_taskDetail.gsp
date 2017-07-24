<div class="col-sm-9" id="mainLayout">
<div class="panel panel-default"  id="baseInfo" >
<div class="panel-heading title">基本信息</div>
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
	<div class="form-group" ng-if="task.fundingUniversity">
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
	<div class="form-group" ng-if="task.auditContent">
	    <label for="expectedGain" class="col-sm-2 control-label">立项审核意见</label>
	    <div class="col-sm-10">
	    	<pre>{{task.auditContent}}</pre>
	    </div>    
	</div>
	<div class="form-group" ng-if="task.collegeAudit">
	    <label for="expectedGain" class="col-sm-2 control-label">学院意见</label>
	    <div class="col-sm-10">
	    	<pre>{{task.collegeAudit}}</pre>
	    </div>    
	</div>
	<div class="form-group" ng-if="task.contractAudit">
	    <label for="expectedGain" class="col-sm-2 control-label">学校意见</label>
	    <div class="col-sm-10">
	    	<pre>{{task.contractAudit}}</pre>
	    </div>    
	</div>
</div>
<div class="panel panel-default"  id="mainInfo" ng-if="stages">
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
	<div class="form-group" ng-if="item.collegeAudit">
    <label for="projectContent" class="col-sm-2 control-label">学院意见</label>
    <div class="col-sm-10">
    	<pre>{{item.collegeAudit}}</pre>
    </div>
	</div>
	<div class="form-group" ng-if="item.endAudit">
    <label for="projectContent" class="col-sm-2 control-label">学校意见</label>
    <div class="col-sm-10">
    	<pre>{{item.endAudit}}</pre>
    </div>
	</div>
</div>
</div>
<div class="panel panel-warning" ng-if="auditList">
			<div class="panel-heading">
		    <h4 class="panel-title">变更溯源</h4>
		    </div>
		    <div class="panel-body">
			    <div ng-repeat="item in auditList">
			    <span  class="form-group">
			    	<label >{{$index+1}}.变更日期</label>	
			    	<span >{{dateFormat(item.date) | date:"yyyy-MM-dd"}}</span>	
			    </span>		
			<table class="table table-bordered" id="updateInfo">
			<thead>
				<tr>
					<th>变更项目</th>
					<th >原信息</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-if="item.taskView.projectName">
					<td >项目名称</td>
					<td >{{item.taskView.projectName}}</td>
				</tr>
				<tr ng-if="item.taskView.members">
					<td >参与人</td>
					<td >{{item.taskView.members}}</td>
				</tr>
				<tr ng-if="item.taskView.expectedMid">
					<td >中期预期</td>
					<td >{{item.taskView.expectedMid}}</td>
				</tr>
				<tr ng-if="item.taskView.expectedEnd">
					<td >结题预期</td>
					<td >{{item.taskView.expectedEnd}}</td>
				</tr>
				<tr ng-if="item.taskView.projectContent">
					<td >研究内容</td>
					<td >{{item.taskView.projectContent}}</td>
				</tr>
				<tr ng-if="item.taskView.expectedGain">
					<td >成果形式</td>
					<td >{{item.taskView.expectedGain}}</td>
				</tr>
				<tr ng-if="item.taskView.teacherId">
					<td >负责人</td>
					<td >{{item.taskView.teacherId}}</td>
				</tr>
				<tr ng-if="item.taskView.currentTitle">
					<td >职称</td>
					<td >{{item.taskView.currentTitle}}</td>
				</tr>
				<tr ng-if="item.taskView.position">
					<td >职务</td>
					<td >{{item.taskView.position}}</td>
				</tr>
				<tr ng-if="item.taskView.currentDegree">
					<td >学历</td>
					<td >{{item.taskView.currentDegree}}</td>
				</tr>
				<tr ng-if="item.taskView.specailEmail">
					<td >原EMAIL</td>
					<td >{{item.taskView.specailEmail}}</td>
				</tr>
				<tr ng-if="item.taskView.phoneNum">
					<td >原电话</td>
					<td >{{item.taskView.phoneNum}}</td>
				</tr>
			</tbody>
			</table>
			    </div>		    
		    </div>		    
	</div>
<div class="panel panel-default"  id="attchInfo" >
	<div class="panel-heading title">附  件<a href="/tms/qemTaskAdmin/downloadAttch_T/{{task.id}}">（下载全部<span class="glyphicon glyphicon-download-alt"></span>）</a></div>
	<div class="form-group" ng-if="(fileList | filter:'申报书___').length">
	<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=申报书"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>申报书</label>
	<div class="col-md-10 form-control-static">
	<ul>
		<li ng-repeat="filename in fileList | filter:'申报书___'" >
				<span>{{getFileName(filename)}}</span>
		</li>
	</ul>
	</div>
	</div>
	<div class="form-group" ng-if="(fileList | filter:'合同___').length">
	<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=合同"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>合同</label>
	<div class="col-md-10 form-control-static">
	<ul>
		<li ng-repeat="filename in fileList | filter:'合同___'" >
				<span>{{getFileName(filename)}}</span>
		</li>
	</ul>
	</div>
	</div>
	<div class="form-group" ng-if="(fileList | filter:'年度___').length">
	<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=年度"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>年度</label>
	<div class="col-md-10 form-control-static">
	<ul>
		<li ng-repeat="filename in fileList | filter:'年度___'" >
				<span>{{getFileName(filename)}}</span>
		</li>
	</ul>
	</div>
	</div>
	<div class="form-group" ng-if="(fileList | filter:'中期___').length">
	<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=中期"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>中期</label>
	<div class="col-md-10 form-control-static">
	<ul>
		<li ng-repeat="filename in fileList | filter:'中期___'" >
				<span>{{getFileName(filename)}}</span>
		</li>
	</ul>
	</div>
	</div>
	<div class="form-group" ng-if="(fileList | filter:'结题___').length">
	<label for="doc" class="col-md-2 control-label"><a href="/tms/qemTaskAdmin/downloadT?taskId={{task.id}}&fileType=结题"><span class="glyphicon glyphicon-download-alt" Tooltip="点击下载"></span></a>结题</label>
	<div class="col-md-10 form-control-static">
	<ul>
		<li ng-repeat="filename in fileList | filter:'结题___'" >
				<span>{{getFileName(filename)}}</span>
		</li>
	</ul>
	</div>
	</div>
	<div class="form-group" ng-if="(fileList | filter:'!___').length">
	<label for="doc" class="col-md-2 control-label">其他</label>
	<div class="col-md-10 form-control-static">
	<ul>
		<li ng-repeat="filename in fileList | filter:'!___'" >
				<span>{{filename}}</span>
		</li>
	</ul>
	</div>
	</div>
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