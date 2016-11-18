<div class="panel panel-default form-body"  style="border-bottom:0px">
<div class="panel-heading title">基本信息</div>
<h4 ng-if="commitAction" class="Emphasis">请正确填写下面红框标注的内容再重新提交！</h4>
<div class="form-group" >
<label class="col-sm-2 control-label">变更内容</label>
	<div class="col-sm-10 form-control-static" ng-init="status1.open=false;status2.open=false" ng-class="{'has-error': commitAction && !task.updateType}">
<%--    	  <select class="form-control" name="updateType"--%>
<%--		    ng-model="task.updateType"--%>
<%--		    ng-options="y.id as y.name for y in updateTypes" ng-change="updateContentChanged(status1,status2)">	--%>
<%--		    <option value="">请选择</option>--%>
<%--			</select>--%>
			<span  ng-repeat="item in updateTypes | filter:{'id':'!1'}" style="margin-right:15px;"><input type="checkbox" ng-model="task.updateType" > {{item.name }}</span>
	</div>{{task.index}}
</div>
<div class="form-group">
<label class="col-sm-2 control-label">原成果形式</label>
	<div class="col-sm-10" ng-class="{'has-error': commitAction && task.updateType==6 && !task.origStyle}">
	    	<input type="text" class="form-control" placeholder="如果成果形式有变更，请填写原成果形式" ng-model="task.origStyle"/>
	</div>
</div>
<div class="form-group" >
	 <label class="col-sm-2 control-label">变更理由</label>
	 <div class="col-sm-10" ng-class="{'has-error': commitAction && !task.memo}">
		<textarea  rows="6" class="form-control" placeholder="（变更项目负责人须写明新项目负责人的性别、出生时间、职称、工作单位、联系电话等情况；延期需要写明理由，延长日期及延期后的具体研究计划等。可附页说明。）" ng-model="task.memo" required></textarea>
	 </div>
</div>
<accordion close-others="false" >
<%--<accordion-group is-open="status1.open">--%>
<%--    <accordion-heading >--%>
<%--<div class="pull-right hand" style="margin-right:10px"><span><span ng-if="status1.open" toolTip="点击隐藏">⊟</span><span ng-if="!status1.open" toolTip="点击展开">⊞</span></span></div>--%>
<%--<p class="title">变更项目负责人信息</p>--%>
<%--	</accordion-heading>--%>
<%--<div>--%>
<%--<div class="form-group">--%>
<%--    <label for="teacherName" class="col-sm-2 control-label">姓名</label>--%>
<%--    <div class="col-sm-2">--%>
<%--    	<input type="text"  name="teacherName" class="form-control" placeholder="老师姓名" ng-model="task.userName" ng-change="findTeacher(task.userName)" required/>--%>
<%--    </div>--%>
<%--    <label for="currentTitle" class="col-sm-2 control-label">工号</label>--%>
<%--    <div class="col-sm-2" ng-class="{'has-error': commitAction && (!task.teacherId) }"  toolTip="输入姓名后才可以下拉选择">--%>
<%--    	<select class="form-control" --%>
<%--    ng-model="task.teacherId"--%>
<%--    ng-options="y.id as (y.id +'|'+y.name + '|'+ y.sex) for y in teachers" ></select>--%>
<%--    </div>--%>
<%--    <label for="currentDegree" class="col-sm-2 control-label">学位</label>--%>
<%--    <div class="col-sm-2"  ng-class="{'has-error': commitAction && !task.currentDegree}">--%>
<%--    	  <select class="form-control" name="currentDegree"--%>
<%--    ng-model="task.currentDegree"--%>
<%--    ng-options="y for y in degrees" >	--%>
<%--	</select>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--    <label for="position" class="col-sm-2 control-label">职务</label>--%>
<%--    <div class="col-sm-2" ng-class="{'has-error': commitAction && !task.position}">--%>
<%--    	 <select class="form-control" name="position"--%>
<%--	    	ng-model="task.position"--%>
<%--	    	ng-options="y for y in positions"  >	--%>
<%--		</select>--%>
<%--    </div>--%>
<%--     <label for="currentTitle" class="col-sm-2 control-label">职称</label>--%>
<%--    <div class="col-sm-2" ng-class="{'has-error': commitAction && !task.currentTitle}">--%>
<%--    	  <select class="form-control" name="currentTitle"--%>
<%--    ng-model="task.currentTitle"--%>
<%--    ng-options="y for y in titles"  >	--%>
<%--	</select>--%>
<%--    </div>--%>
<%--    <label for="phoneNum" class="col-sm-2 control-label">电话</label>--%>
<%--    <div class="col-sm-2 " ng-class="{'has-error':myForm.phoneNum.$error.pattern || (commitAction && !task.phoneNum)}">--%>
<%--    	<input type="text" name="phoneNum"  class="form-control" placeholder="常用手机号码 " ng-model="task.phoneNum" ng-pattern="/^((13[0-9])|(15[^4,\D])|(18[0,2,5-9]))\d{8}$/" required/>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--	<label for="specailEmail" class="col-sm-2 control-label">Email</label>--%>
<%--    <div class="col-sm-10 "  ng-class="{'has-error': commitAction && !task.specailEmail}">--%>
<%--    	<input type="email"  name="specailEmail" class="form-control" placeholder="常用电子邮箱" ng-model="task.specailEmail" required/>--%>
<%--    </div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</accordion-group>--%>
<accordion-group is-open="status2.open">
    <accordion-heading >
<div class="pull-right hand" style="margin-right:10px"><span><span ng-if="status2.open" toolTip="点击隐藏">⊟</span><span ng-if="!status2.open" toolTip="点击展开">⊞</span></span></div>
<p class="title" >变更项目信息</p>
	</accordion-heading>
<div>
<div class="form-group">
	 <label for="projectName" class="col-sm-2 control-label">项目名称</label>
	<div class="col-sm-4" ng-class="{'has-error':commitAction && !task.projectName}">
    	<input type="text" name="projectName"  class="form-control" placeholder="您的项目名称*" ng-model="task.projectName" required/>
    </div>
    <label  class="col-sm-2 control-label">参与人</label>
    <div class="col-sm-4 ">
<%--    	<input type="text"   class="form-control" placeholder="项目所有参与人，请按';'隔开" ng-model="task.members" required/>--%>
		<span ng-repeat="y in members"><span class="expert-admin" ng-click="member_click(y)" ng-show="!y.clicked" style="margin-right:1em">{{y.name}}</span> 
				<input ng-show="y.clicked" ng-model="y.name" size="5" ng-mouseleave="mouseleave(y)" style="margin-right:1em"></span>
				<a href=""><span class="glyphicon glyphicon-plus" ng-click="oneMore()" ng-if="members.length<6"></span></a>
    </div>  
</div>

<%--<div class="form-group">--%>
<%--    <label for="qemType" class="col-sm-2 control-label">项目类别</label>--%>
<%--    <div class="col-sm-4" ng-class="{'has-error':!task.qemTypeId}">--%>
<%--    <select class="form-control" name="qemType"--%>
<%--    ng-model="task.qemTypeId"--%>
<%--    ng-options="y.id as y.name for y in qemTypes"  >	--%>
<%--	</select>--%>
<%--    </div>--%>
<%--    <label for="projectLevel" class="col-sm-2 control-label">项目等级</label>--%>
<%--    <div class="col-sm-4" ng-class="{'has-error':!task.projectLevel}">--%>
<%--    <select class="form-control" name="projectLevel"--%>
<%--    ng-model="task.projectLevel"--%>
<%--    ng-options="y.id as y.name for y in projectLevels"  >	--%>
<%--	</select>--%>
<%--    </div>--%>
<%--</div>--%>
<div class="form-group">  
	 <label for="beginYear" class="col-sm-2 control-label">立项日期</label>
     <div class="col-sm-2" ng-class="{'has-error':commitAction && !task.beginYear}">
    	<input type="text" name="beginYear"  class="form-control" placeholder="立项日期 *" ng-model="task.beginYear" readonly/>
    </div>
    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>
     <div class="col-sm-2">
    	<input type="text" name="expectedMid"  class="form-control" placeholder="四位年份*" ng-model="task.expectedMid" />
    </div>
    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>
 	<div class="col-sm-2" ng-class="{'has-error':commitAction && !task.expectedEnd}">
    	<input type="text" name="expectedEnd"  class="form-control" placeholder="四位年份*" ng-model="task.expectedEnd" required/>
    </div>
</div>
<div class="form-group">
    <label  class="col-sm-2 control-label">研究内容</label>
    <div class="col-sm-10"  ng-class="{'has-error':commitAction && !task.projectContent}">
    	<textArea rows="4" class="form-control" placeholder="简略说明主要研究内容" ng-model="task.projectContent" required>
    	</textArea>
    </div>
</div>
<div class="form-group">
    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10" ng-class="{'has-error':commitAction && !task.expectedGain}">
    	<textArea name="expectedGain" rows="4" class="form-control" placeholder="简略列举预期成果" ng-model="task.expectedGain" required>
    	</textArea>
    </div>
</div>
</div>
</accordion-group>
<accordion-group is-open="status3.open">
    <accordion-heading >
<div class="pull-right hand" style="margin-right:10px"><span><span ng-if="status3.open" toolTip="点击隐藏">⊟</span><span ng-if="!status3.open" toolTip="点击展开">⊞</span></span></div>
<p class="title">附    件<a href="/tms/qemCollegeCheck/downloadAttch_T/{{task.id}}">（下载全部<span class="glyphicon glyphicon-download-alt"></span>）</a></p>
</accordion-heading>
<div>
<div class="form-group" ng-show="fileList">
<label for="doc" class="col-md-2 control-label">已上传附件</label>
<ul class="col-md-10 form-control-static">
	<li ng-repeat="filename in fileList">{{filename}}</li>
</ul>
</div>
<div class="form-group">
		<label class="col-sm-2 control-label">申报书</label>
		<div class="col-sm-4">
			<input type="file" nv-file-select="" uploader="uploader" class="form-control" ng-disabled="uploader.queue.length"/>	
		</div>
		<div class="form-control-static" ng-repeat="item in uploader.queue">
			<div class="col-md-2" ng-show="uploader.isHTML5">
	          <div class="progress" style="margin-bottom: 0;">
	              <div class="progress-bar" role="progressbar" ng-style="{ 'width': item.progress + '%' }"></div>
	          </div>
		    </div>
	      	<div class="col-md-1">
	          <span ng-show="item.isSuccess"><i class="glyphicon glyphicon-ok"></i></span>
	          <span ng-show="item.isCancel"><i class="glyphicon glyphicon-ban-circle"></i></span>
	          <span ng-show="item.isError"><i class="glyphicon glyphicon-remove"></i></span>
	      	</div> 
	      	<div class="col-md-2">
	      	<button type="button" class="btn btn-success btn-xs" ng-click="item.upload()" ng-disabled="item.isReady || item.isUploading || item.isSuccess">
                <span class="glyphicon glyphicon-upload"></span> 上传文件
            </button>
	      	</div>
		</div>		 
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">其他附件</label>
		<div class="col-md-10 form-control-static">
			<button type="button" class="btn btn-success btn-xs" ng-click="uploadAttch(task.id)">上传其他附件</button>
		</div>
</div>
</div>
</accordion-group>
</accordion>
</div>
