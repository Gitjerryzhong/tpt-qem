<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>项目负责人信息</strong></h4>
</div>
<div class="form-group">
    <label for="currentTitle" class="col-sm-2 control-label">职称<span class="Emphasis">*</span></label>
    <div class="col-sm-4" ng-class="{'has-error':commitAction && !project.currentTitle}">
    	 <select class="form-control" name="currentTitle"
    ng-model="project.currentTitle"
    ng-options="y for y in titles"  >	
	</select>
    </div>
    <label for="currentDegree" class="col-sm-2 control-label">学位<span class="Emphasis">*</span></label>
    <div class="col-sm-4"  ng-class="{'has-error':commitAction && !project.currentDegree}">
    	 <select class="form-control" name="currentDegree"
    ng-model="project.currentDegree"
    ng-options="y for y in degrees" >	
	</select>
    </div>
</div>
<div class="form-group">
    <label for="position" class="col-sm-2 control-label">职务<span class="Emphasis">*</span></label>
    <div class="col-sm-4" ng-class="{'has-error':commitAction && !project.position}">
	    <select class="form-control" name="position"
	    	ng-model="project.position"
	    	ng-options="y for y in positions"  >	
		</select>
<%--    	<input type="text" name="position"  class="form-control" placeholder="您的职务 *" ng-model="project.position"/>--%>
    </div>
    <label for="phoneNum" class="col-sm-2 control-label">电话<span class="Emphasis">*</span></label>
    <div class="col-sm-4" ng-class="{'has-error':myForm.phoneNum.$error.pattern ||(commitAction && !project.phoneNum)}">
    	<input type="text" name="phoneNum"  class="form-control" placeholder="您的手机长号 *" ng-model="project.phoneNum" ng-pattern="/^((13[0-9])|(15[^4,\D])|(17[0-9])|(18[0,2,5-9]))\d{8}$/" required/>
    </div>
</div>
<div class="form-group">
    <label for="specailEmail" class="col-sm-2 control-label">Email<span class="Emphasis">*</span></label>
    <div class="col-sm-10" ng-class="{'has-error':commitAction && !project.specailEmail}">
    	<input type="email" name="specailEmail"  class="form-control" placeholder="您的常用电子邮箱*" ng-model="project.specailEmail" required/>
    </div>
</div>
<div class="form-group">
    <label for="discipline" class="col-sm-2 control-label">学科门类<span class="Emphasis">*</span></label>
    <div class="col-sm-4" ng-class="{'has-error':commitAction && !discipline.title}">
	    <select class="form-control" name="discipline"
	    ng-model="discipline"
	    ng-options="y.title  for y in disciplines" >	
		</select>
	</div>
	<label for="majorId" class="col-sm-2 control-label">一级专业<span class="Emphasis">*</span></label>
    <div class="col-sm-4" ng-class="{'has-error':commitAction && !project.majorId}">
	     <select class="form-control" name="majorId"
	    ng-model="project.majorId"
	    ng-options=" y for y in discipline.majors "  >	
		</select>
	</div>
</div>
<div class="form-group">
    <label for="departmentId" class="col-sm-2 control-label">单位</label>
    <div class="col-sm-4">
    <select class="form-control" name="departmentId"
    ng-model="project.departmentId"
    ng-options="y.id as y.name for y in departments"  >	
	</select>
    </div>   
	<label for="direction" class="col-sm-2 control-label">方向</label>
    <div class="col-sm-4">
    	<input type="text" name="direction"  class="form-control" placeholder="您的研究方向" ng-model="project.direction"/>
    </div>
</div>
<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>项目信息</strong></h4>
</div>
<div class="form-group">
    <label for="projectName" class="col-sm-2 control-label">项目名称<span class="Emphasis">*</span></label>
    <div class="col-sm-10" ng-class="{'has-error':commitAction && !project.projectName}">
    	<input id="projectId" type="hidden" ng-model="project.id"/>
    	<input type="text" name="projectName"  class="form-control" placeholder="您的项目名称*" ng-model="project.projectName" required/>
    </div>
</div>
<div class="form-group">
    <label for="qemType" class="col-sm-2 control-label">项目类别<span class="Emphasis">*</span></label>
    <div class="col-sm-4" ng-class="{'has-error':commitAction && !project.qemTypeId}">
    <select class="form-control" name="qemType"
    ng-model="project.qemTypeId"
    ng-options="y.id as y.name for y in qemTypes"  >	
	</select>
    </div>
    <label for="projectLevel" class="col-sm-2 control-label">项目等级<span class="Emphasis">*</span></label>
    <div class="col-sm-4" ng-class="{'has-error':commitAction && !project.projectLevel}">
    <select class="form-control" name="projectLevel"
    ng-model="project.projectLevel"
    ng-options="y.id as y.name for y in projectLevels"  >	
	</select>
    </div>
</div>
<div class="form-group">
    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10">
    	<textArea name="expectedGain" rows="4" class="form-control" placeholder="简略列举预期成果" ng-model="project.expectedGain" required>
    	</textArea>
    </div>
</div>

<%--<div>--%>
	<div class="form-group">
	<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>附    件</strong></h4>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">相关网址</label>
		<div class="col-md-10 form-control-static">
			<textArea rows="4" class="form-control" placeholder="列举支撑本项目的相关网址，每网站占一行" ng-model="project.otherLinks">
    	</textArea>
		</div>
	</div>
<div ng-show="project.id">
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
			<button type="button" class="btn btn-success btn-xs" ng-click="uploadAttch(project.id)">上传其他附件</button>
		</div>
	</div>
	
</div>