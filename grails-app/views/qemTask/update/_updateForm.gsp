<div class="panel panel-default form-body" >
<div class="panel-heading title">基本信息</div>
<h4 ng-if="commitAction && myForm.$invalid" class="Emphasis">请正确填写下面红色标注的内容再重新提交！</h4>
<div class="form-group" >
<label class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && noChecked() }">变更内容</label>
	<div class="col-sm-10 form-control-static">
			<span  ng-repeat="item in updateTypes | filter:{'id':'!1'}" style="margin-right:15px;"><input type="checkbox" ng-model="item.selected" ng-change="updateTypeChange(item)"> {{item.name }}</span>
	</div>
</div>
<div class="form-group" style="border-bottom:0px;">
	 <label class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && !newData.memo }">变更理由</label>
	 <div class="col-sm-10" >
		<textarea  rows="6" class="form-control" placeholder="（变更项目负责人须写明新项目负责人的性别、出生时间、职称、工作单位、联系电话等情况；延期需要写明理由，延长日期及延期后的具体研究计划等。可附页说明。）" ng-model="newData.memo" required></textarea>
	 </div>
</div>
</div>

<div class="panel panel-default form-body" >
<div class="panel-heading title">变更项目信息</div>
<table class="table table-bordered table-striped" id="updateInfo">
			<thead>
				<tr>
					<th colspan="2" >原项目信息</th>
					<th colspan="2" >变更为</th>
				</tr>
				<tr>
					<th style="width:6em">标题</th>
					<th style="width:30em">内容</th>
					<th style="width:6em">标题</th>
					<th style="width:30em">内容</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-if="updateTypes[2].selected">
					<td>项目名称</td>
					<td>{{task.projectName}}</td>
					<td><span ng-class="{'Emphasis': commitAction && !newData.projectName }">项目名称</span></td>
					<td><input type="text" name="projectName"  class="form-control" placeholder="您的项目名称*" ng-model="newData.projectName" required/></td>
				</tr>
				<tr ng-if="updateTypes[6].selected">
					<td>参与人</td>
					<td>{{task.memberstr}}</td>
					<td>参与人</td>
					<td><span ng-repeat="y in members"><span class="expert-admin" ng-click="member_click(y)" ng-show="!y.clicked" style="margin-right:1em">{{y.name}}</span> 
				<input ng-show="y.clicked" ng-model="y.name" size="5" ng-mouseleave="mouseleave(y)" style="margin-right:1em"></span>
				<a href=""><span class="glyphicon glyphicon-plus" ng-click="oneMore()" ng-if="members.length<6"></span></a></td>
				</tr>
				<tr ng-if="updateTypes[1].selected">
					<td>中期预期</td>
					<td>{{task.expectedMid}}</td>
					<td><span ng-class="{'Emphasis':myForm.expectedMid.$error.pattern || (commitAction && !newData.expectedMid) }">中期预期</span></td>
					<td><input type="text"  class="form-control" name="expectedMid"  ng-pattern="/^20\d{2}$/" placeholder="请输入4位年份" ng-model="newData.expectedMid" required/></td>
				</tr>
				<tr ng-if="updateTypes[1].selected">
					<td>结题预期</td>
					<td>{{task.expectedEnd}}</td>
					<td><span ng-class="{'Emphasis':myForm.expectedEnd.$error.pattern || (commitAction && !newData.expectedEnd)}">结题预期</span></td>
					<td><input type="text"   class="form-control" name="expectedEnd"  ng-pattern="/^20\d{2}$/" placeholder="请输入4位年份"  ng-model="newData.expectedEnd" required/></td>
				</tr>
				<tr ng-if="updateTypes[3].selected">
					<td>研究内容</td>
					<td>{{task.projectContent}}</td>
					<td><span  ng-class="{'Emphasis': commitAction && !newData.projectContent }">研究内容</span></td>
					<td><textArea rows="3" class="form-control" placeholder="简略说明主要研究内容" ng-model="newData.projectContent" required></textArea></td>
				</tr>
				<tr ng-if="updateTypes[5].selected">
					<td>成果形式</td>
					<td>{{task.expectedGain}}</td>
					<td><span  ng-class="{'Emphasis': commitAction && !newData.expectedGain }">成果形式</span></td>
					<td><textArea name="expectedGain" rows="3" class="form-control" placeholder="简略列举预期成果" ng-model="newData.expectedGain" required></textArea></td>
				</tr>
				<tr ng-if="updateTypes[7].selected">
					<td></td>
					<td></td>
					<td><span  ng-class="{'Emphasis': commitAction && !newData.memo }">其他变更</span></td>
					<td><input type="text" name="memo"  class="form-control" placeholder="请列出要变更的其他内容" ng-model="newData.others" required/></td>
				</tr>
			</tbody>
</table>
</div>
<div class="panel panel-default form-body" >
<div class="panel-heading title">附 件</div>
<div class="form-group" ng-if="fileList">
<label for="doc" class="col-md-2 control-label">原申报书</label>
<div class="col-md-10 ">
<ul class="form-control-static">
	<li ng-repeat="filename in declarations" >
			<span>{{filename}}</span>
	</li>
</ul>
</div>
<label for="doc" class="col-md-2 control-label" ng-if="contracts">原合同</label>
<div class="col-md-10 ">
<ul class="form-control-static">
	<li ng-repeat="filename in contracts" >
			<span>{{filename}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" style="border-bottom:0px;">
	<label class="col-sm-2 control-label">申报书</label>
	<div class="col-sm-10">
		<input type="file" nv-file-select="" uploader="uploader3" class="form-control" ng-disabled="uploader3.queue.length"/>
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