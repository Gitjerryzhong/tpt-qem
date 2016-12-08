<div class="panel panel-default form-body" >
<div class="panel-heading title">基本信息</div>
<h4 ng-if="commitAction && myForm.$invalid" class="Emphasis">请正确填写下面红色标注的内容再重新提交！</h4>
<div class="form-group" >
<label class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && noChecked() }">变更内容</label>
	<div class="col-sm-10 form-control-static">
			<span > {{typesText }}</span>
	</div>
</div>
<div class="form-group" style="border-bottom:0px;">
	 <label class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && !updateView.memo }">变更理由</label>
	 <div class="col-sm-10" >
		<textarea  rows="6" class="form-control" placeholder="（变更项目负责人须写明新项目负责人的性别、出生时间、职称、工作单位、联系电话等情况；延期需要写明理由，延长日期及延期后的具体研究计划等。可附页说明。）" ng-model="updateView.memo" required></textarea>
	 </div>
</div>
</div>

<div class="panel panel-default form-body" >
<div class="panel-heading title">变更项目负责人</div>
<div class="form-group">
    <label for="teacherName" class="col-sm-2 control-label">姓名</label>
    <div class="col-sm-2">
    	<input type="text"  name="teacherName" class="form-control" placeholder="老师姓名" ng-model="updateView.teacherName" ng-change="findTeacher(updateView.teacherName)" required/>
    </div>
    <label for="currentTitle" class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && (!updateView.teacherId) }" >工号</label>
    <div class="col-sm-2"  toolTip="正确输入姓名后才可以下拉选择">
    	<select class="form-control" 
    ng-model="updateView.teacherId"
    ng-options="y.id as (y.id +'|'+y.name + '|'+ y.sex) for y in teachers" ></select>
    </div>
    <label for="currentDegree" class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && !updateView.currentDegree}">学位</label>
    <div class="col-sm-2" >
    	  <select class="form-control" name="currentDegree"
    ng-model="updateView.currentDegree"
    ng-options="y for y in degrees" >	
	</select>
    </div>
</div>
<div class="form-group">
    <label for="position" class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && !updateView.position}">职务</label>
    <div class="col-sm-2">
    	 <select class="form-control" name="position"
	    	ng-model="updateView.position"
	    	ng-options="y for y in positions"  >	
		</select>
    </div>
     <label for="currentTitle" class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && !updateView.currentTitle}">职称</label>
    <div class="col-sm-2">
    	  <select class="form-control" name="currentTitle"
    ng-model="updateView.currentTitle"
    ng-options="y for y in titles"  >	
	</select>
    </div>
    <label for="phoneNum" class="col-sm-2 control-label" ng-class="{'Emphasis':myForm.phoneNum.$error.pattern || (commitAction && !updateView.phoneNum)}">电话</label>
    <div class="col-sm-2 " >
    	<input type="text" name="phoneNum"  class="form-control" placeholder="常用手机号码 " ng-model="updateView.phoneNum" ng-pattern="/^((13[0-9])|(15[^4,\D])|(18[0,2,5-9]))\d{8}$/" required/>
    </div>
</div>
<div class="form-group">
	<label for="specailEmail" class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && !updateView.specailEmail}">Email</label>
    <div class="col-sm-10 "  >
    	<input type="email"  name="specailEmail" class="form-control" placeholder="常用电子邮箱" ng-model="updateView.specailEmail" required/>
    </div>
</div>

</div>
<div class="panel panel-default form-body" >
<div class="panel-heading title">附 件</div>
<div class="form-group" ng-if="fileList">
<label for="doc" class="col-md-2 control-label">原申报书</label>
<div class="col-md-10 ">
<ul class="form-control-static">
	<li ng-repeat="filename in declarations | filter:'申报书'" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
<%--<label for="doc" class="col-md-2 control-label" ng-if="contracts">原合同</label>--%>
<%--<div class="col-md-10 ">--%>
<%--<ul class="form-control-static">--%>
<%--	<li ng-repeat="filename in contracts" >--%>
<%--			<span>{{filename}}</span>--%>
<%--	</li>--%>
<%--</ul>--%>
<%--</div>--%>
</div>
<div class="form-group" style="border-bottom:0px;">
	<label class="col-sm-2 control-label">申报书</label>
	<div class="col-sm-10">
		<input type="file" nv-file-select="" uploader="uploader" class="form-control" ng-disabled="uploader.queue.length"/>
		<div style="margin-top: 15px;color:#333333;background-color:#f5f5f5;border:1px solid #cccccc;border-radius:0;">      
      <table class="table">
          <thead>
              <tr>
                  <th>文件名</th>
                  <th ng-show="uploader.isHTML5">大小</th>
                  <th ng-show="uploader.isHTML5">进度</th>
                  <th>状态</th>
                  <th>操作</th>
              </tr>
          </thead>
          <tbody>
              <tr ng-repeat="item in uploadQueue">
                  <td>
                      <strong>{{ item.file.name }}</strong>  
                  </td>
                  <td ng-show="uploader.isHTML5" nowrap><span ng-if="item.file.size">{{ item.file.size/1024/1024|number:2 }} MB</span></td>
                  <td ng-show="uploader.isHTML5">
                      <div class="progress" style="margin-bottom: 0;" ng-if="item.file.size">
                          <div class="progress-bar" role="progressbar" ng-style="{ 'width': item.progress + '%' }"></div>
                      </div>
                  </td>
                  <td class="text-center">
                      <span ng-show="item.isSuccess">成功</span>
                      <span ng-show="item.isCancel">撤销</span>
                      <span ng-show="item.isError">失败</span>
                  </td>  
                  <td nowrap>
<%--                   <button type="button" class="btn btn-success btn-xs" ng-click="item.upload()" ng-disabled="item.isReady || item.isUploading || item.isSuccess">--%>
<%--                          <span class="glyphicon glyphicon-upload"></span> 上传--%>
<%--                      </button>--%>
                      <button type="button" class="btn btn-danger btn-xs" ng-click="remove(item)">
                          <span class="glyphicon glyphicon-trash"></span> 删除
                      </button>
                  </td>                
              </tr>
          </tbody>
      </table>
</div>	
	</div>			 
</div>
</div>