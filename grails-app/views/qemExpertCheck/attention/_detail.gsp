<div class="form-group">
    <label for="title" class="col-sm-2 control-label">标题</label>
    <div class="col-sm-6">
    	<p class="form-control-static Emphasis">{{attention.title}}</p>
    </div>
    <label for="work_type" class="col-sm-2 control-label">发布日期</label>
    <div class="col-sm-2">
    	<p class="form-control-static">{{dateFormat(attention.publishDate) | date : 'yyyy-MM-dd'}}</p>
    </div>
</div>
<div class="form-group">
     
</div>
<div class="form-group">
    <label for="content" class="col-sm-2 control-label">内容</label>
    <div class="col-sm-10">
    	<pre class="form-control-static">{{attention.content}}</pre>
    </div>
</div>
<div class="form-group" ng-if="fileList">
<label  class="col-md-2 control-label">附件</label>
<ul class="col-md-10 form-control-static">
	<li ng-repeat="filename in fileList" ><a href="/tms/qemExpertCheck/downloadAttentAtt/{{attention.id}}" toolTip="点击下载">{{filename}}</a></li>
</ul>
</div>
