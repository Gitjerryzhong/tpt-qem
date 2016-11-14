
<div class="form-group">
    <label for="photoUrl" class="col-sm-2 control-label">{{picName}}</label>
    <div class="col-sm-9">
	<input type="file" nv-file-select="" uploader="uploader" class="form-control"  ng-disabled="uploader.queue.length"/>	
	</div>
	<div  class="col-sm-12">
	<ul ng-repeat="item in picLimit">
	<li class="Emphasis small">{{item}}</li>	
	</ul>	
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
              </tr>
          </tbody>
      </table>
</div>