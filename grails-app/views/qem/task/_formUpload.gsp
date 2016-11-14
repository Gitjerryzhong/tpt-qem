<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>上传合同</strong></h4>
</div>
<div class="form-group" ng-show="fileList">
<label for="doc" class="col-sm-2 control-label">已上传附件</label>
<a class="col-sm-3 form-control-static" ng-href="{{filename}}" ng-repeat="filename in fileList">{{getFileName(filename)}}</a>
</div>
 <div class="form-group"> 
    <label for="doc" class="col-sm-2 control-label">上传附件</label>
    <div class="col-sm-10">
	<input type="file" nv-file-select="" uploader="uploader" class="form-control"  ng-disabled="uploader.queue.length"/>	
	</div>	
</div>
<div class="col-sm-offset-1 col-sm-11" style="margin-bottom: 40px;color:#333333;background-color:#f5f5f5;border:1px solid #cccccc;border-radius:0;">      
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