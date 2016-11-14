 <div class="form-group" style="margin-top:15px"> 
    <label for="doc" class="col-sm-2 control-label">上传附件</label>
    <div class="col-sm-10">
	<input type="file" nv-file-select="" uploader="uploader" class="form-control"/>
	<span class="help-block">支持多次选择文件上传多个文件</span>
	<div style="margin-bottom: 40px;color:#333333;background-color:#f5f5f5;border:1px solid #cccccc;border-radius:0;">      
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
