
<div class="form-group">
    <label for="photoUrl" class="col-sm-3 control-label">照片/国外证书/国外成绩单</label>
    <div class="col-sm-7">
	<input type="file" nv-file-select="" uploader="uploader" class="form-control"  multiple />
	<ul>
	<li class="text-danger small">照片要求 ，尺寸：413×626 分辨率：300dpi；<strong>文件名要加上前缀“ photo_”。</strong></li>
	<li class="text-danger small">国外证书：<strong>文件名要加上前缀“ certi_”。</strong></li>
	<li class="text-danger small">国外成绩单学生有两份或者三份（两年成绩），<strong>文件名要加上前缀“ trans_”。</strong></li>
	<li class="text-danger small">选择文件时可以按住ctrl键一次选择多个文件, 也可以多次选择。</li>
	</ul>
	</div>	
	</div>

<div  style="margin-bottom: 40px;color:#333333;background-color:#f5f5f5;border:1px solid #cccccc;border-radius:0;">      
      <Strong>上传队列</Strong>
      <p>队列文件总数: {{ uploader.queue.length }}</p>		
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