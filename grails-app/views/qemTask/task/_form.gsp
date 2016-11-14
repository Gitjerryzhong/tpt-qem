<div class="panel panel-default">
<div class="panel-heading title">基本信息
<%--<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>基本信息</strong></h4>--%>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>
    <label class="col-sm-2 control-label">批准金额</label>
    <div class="col-sm-2 form-control-static"><span>{{task.buget}}万元</span></div>
    <label class="col-sm-2 control-label">负责人</label>
    <div class="col-sm-2 form-control-static"><span >{{task.userName}}</span></div>
</div>
<div class="form-group">  
	 <label for="beginYear" class="col-sm-2 control-label">立项日期</label>
     <div class="col-sm-2 form-control-static"><span>{{task.beginYear}}</span></div>
    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>
    <div class="col-sm-2 form-control-static"><span>{{task.expectedMid}}</span></div>
    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>
     <div class="col-sm-2 form-control-static"><span >{{task.expectedEnd}}</span></div>
</div>
<div class="form-group">  
	 <label for="beginYear" class="col-sm-2 control-label">单位</label>
     <div class="col-sm-2 form-control-static"><span>{{task.departmentName}}</span></div>
    <label for="expectedMid" class="col-sm-2 control-label">项目类型</label>
    <div class="col-sm-2 form-control-static"><span>{{task.qemTypeName}}</span></div>
    <label for="expectedEnd" class="col-sm-2 control-label">项目等级</label>
     <div class="col-sm-2 form-control-static"><span >{{levelText(task.projectLevel)}}</span></div>
</div>
<div class="form-group">
    <label for="projectContent" class="col-sm-2 control-label">主要内容</label>
    <div class="col-sm-10">
    	<textArea name="projectContent" rows="4" class="form-control" placeholder="简略说明项目主要内容" ng-model="task.projectContent" required>
    	</textArea>
    </div>
</div>
<div class="form-group">
    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10">
    	<textArea name="expectedGain" rows="4" class="form-control" placeholder="简略列举预期成果" ng-model="task.expectedGain" required>
    	</textArea>
    </div>
</div>

<div class="form-group">
    <label for="team" class="col-sm-2 control-label">成员姓名</label>
    <div class="col-sm-10 form-control-static"> 
    	<span ng-repeat="y in members"><span class="expert-admin" ng-click="member_click(y)" ng-show="!y.clicked" style="margin-right:1em">{{y.name}}</span> 
				<input ng-show="y.clicked" ng-model="y.name" size="5" ng-mouseleave="mouseleave(y)" style="margin-right:1em"></span>
				<a href=""><span class="glyphicon glyphicon-plus" ng-click="oneMore()" ng-if="members.length<6"></span></a>
    </div>
</div>
</div>
<div class="panel panel-default">
<div class="panel-heading title">附 件
<%--<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>基本信息</strong></h4>--%>
</div>
<div class="form-group" style="margin-top:15px;">
	<label class="col-sm-2 control-label">合 同</label>
	<div class="col-sm-10">
		<input type="file" nv-file-select="" uploader="uploader" class="form-control" ng-disabled="uploader.queue.length"/>	
	</div>	 
</div>
<div class="form-group">
	<label class="col-sm-2 control-label">申报书</label>
	<div class="col-sm-10">
		<input type="file" nv-file-select="" uploader="uploader1" class="form-control" ng-disabled="uploader1.queue.length"/>
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
