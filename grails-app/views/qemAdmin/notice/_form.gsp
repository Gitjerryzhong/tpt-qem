<div ng-show="editAble">
<div class="form-group">
    <label for="title" class="col-sm-2 control-label">标题</label>
    <div class="col-sm-8">
    	<input id="projectId" type="hidden" ng-model="notice.id"/>
    	<g:textField name="title"  class="form-control" placeholder="通知标题" ng-model="notice.title" ng-required="true"/>
    </div>
</div>
<div class="form-group">
    <label for="work_type" class="col-sm-2 control-label">通知类型</label>
    <div class="col-sm-8 form-control-static">
    	<span style="margin-right:20px"><input type="radio" ng-model="notice.workType" value="REQ"> 新项目申请</span>
    	<span style="margin-right:20px"><input type="radio" ng-model="notice.workType" value="CHE"> 项目检查</span>
    	<span style="margin-right:20px"><input type="radio" ng-model="notice.workType" value="OTHER"> 其他通知</span>
    </div>
</div>
<div class="form-group">
    <label for="work_type" class="col-sm-2 control-label">限制条件</label>
    <div class="col-sm-8 form-control-static">
    	<span style="margin-right:20px"><input type="radio" ng-model="notice.noTowLine" value="0" ng-disabled="notice.workType=='CHE'"> 不限制</span>
    	<span style="margin-right:20px"><input type="radio" ng-model="notice.noTowLine" value="1"  ng-disabled="notice.workType=='CHE'"> 校级在研</span>
    	<span style="margin-right:20px"><input type="radio" ng-model="notice.noTowLine" value="2" ng-disabled="notice.workType=='CHE'"> 省级在研</span>
    </div>
</div>
<div class="form-group">
    <label for="content" class="col-sm-2 control-label">内容</label>
    <div class="col-sm-8">
    	<g:textArea name="content"  rows="15" cols="40" class="form-control" placeholder="请输入通知内容" ng-model="notice.content"/>
    </div>
</div>
<div class="form-group">
    <label for="start" class="col-sm-2 control-label">开始日期</label>
    <div class="col-sm-8 input-group">
    	<input type="text" class="form-control" current-text="今天" clear-text="清除" close-text="关闭" datepicker-popup ng-model="notice.start" is-open="startid" datepicker-options="dateOptions"  ng-required="true" close-text="Close" ng-readonly="true">
        <span class="input-group-btn">
            <button type="button" class="btn btn-default" ng-click="open($event,0)">
                <i class="glyphicon glyphicon-calendar"></i>
            </button>
        </span>
    </div>
</div>
<div class="form-group">
    <label for="end" class="col-sm-2 control-label">截至日期</label>
    <div class="input-group col-sm-8">
    	<input type="text" class="form-control" current-text="今天" clear-text="清除" close-text="关闭" datepicker-popup ng-model="notice.end" is-open="endid" datepicker-options="dateOptions"  ng-required="true" close-text="Close" ng-readonly="true">
        <span class="input-group-btn">
            <button type="button" class="btn btn-default" ng-click="open($event,1)">
                <i class="glyphicon glyphicon-calendar"></i>
            </button>
        </span>
    </div>
</div>
<%--<div class="form-group" ng-if="fileList">--%>
<%--<label  class="col-md-2 control-label">已上传附件</label>--%>
<%--<ul class="col-md-10 form-control-static">--%>
<%--	<li ng-repeat="filename in fileList" ><a href="#">{{filename}}</a><span class="glyphicon glyphicon-remove hand" style="margin-left:100px" ng-click="remove(filename)"></span></li>--%>
<%--</ul>--%>
<%--</div>--%>
 <div class="form-group"> 
    <label for="doc" class="col-sm-2 control-label">上传附件</label>
    <div class="col-sm-10">
	<input type="file" nv-file-select="" uploader="uploader" class="form-control"/>	
	</div>	
</div>
<div  style="margin-bottom: 40px;color:#333333;background-color:#f5f5f5;border:1px solid #cccccc;border-radius:0;">      
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
              <tr ng-repeat="item in uploader.queue">
                  <td>
                      <strong>{{ item.file.name }}</strong>  
                  </td>
                  <td ng-show="uploader.isHTML5" nowrap>{{ item.file.size/1024/1024|number:2 }} MB</td>
                  <td ng-show="uploader.isHTML5">
                      <div class="progress" style="margin-bottom: 0;">
                          <div class="progress-bar" role="progressbar" ng-style="{ 'width': item.progress + '%' }"></div>
                      </div>
                  </td>
                  <td class="text-center">
                      <span ng-show="item.isSuccess"><i class="glyphicon glyphicon-ok"></i></span>
                      <span ng-show="item.isCancel"><i class="glyphicon glyphicon-ban-circle"></i></span>
                      <span ng-show="item.isError"><i class="glyphicon glyphicon-remove"></i></span>
                  </td>  
                  <td nowrap>
                      <button type="button" class="btn btn-success btn-xs" ng-click="item.upload()" ng-disabled="item.isReady || item.isUploading || item.isSuccess">
                          <span class="glyphicon glyphicon-upload"></span> 上传
                      </button>
                      <button type="button" class="btn btn-danger btn-xs" ng-click="item.remove()">
                          <span class="glyphicon glyphicon-trash"></span> 删除
                      </button>
                  </td>                
              </tr>
          </tbody>
      </table>
</div>
</div>