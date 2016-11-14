<div class="form-group">
    <label for="title" class="col-sm-2 control-label">标题</label>
    <div class="col-sm-10">
    	<p class="form-control-static Emphasis">{{notice.title}}</p>
    </div>
</div>
<div class="form-group">
    <label for="work_type" class="col-sm-2 control-label">通知类型</label>
    <div class="col-sm-4">
    	<p class="form-control-static">{{workTypeText(notice.workType)}}</p>
    </div>
     <label for="work_type" class="col-sm-2 control-label">发布日期</label>
    <div class="col-sm-4">
    	<p class="form-control-static">{{dateFormat(notice.publishDate) | date : 'yyyy-MM-dd'}}</p>
    </div>
</div>
<div class="form-group">
    <label for="content" class="col-sm-2 control-label">内容</label>
    <div class="col-sm-10">
    	<pre class="form-control-static">{{notice.content}}</pre>
    </div>
</div>
<div class="form-group">
    <label for="start" class="col-sm-2 control-label">开始日期</label>
    <div class="col-sm-4">
    	<p class="form-control-static">{{dateFormat(notice.start) | date : 'yyyy-MM-dd'}}</p>
    </div>
    <label for="end" class="col-sm-2 control-label">截至日期</label>
    <div class="col-sm-4">
    	<p class="form-control-static">{{dateFormat(notice.end) | date : 'yyyy-MM-dd'}}</p>
    </div>
</div>
<div class="form-group">
<label  class="col-md-2 control-label">附件</label>
<ul class="col-md-10 form-control-static">
	<li ng-repeat="filename in fileList" ><a href="/tms/qem/downloadNoticeAtt/{{notice.id}}" toolTip="点击下载">{{filename}}</a></li>
</ul>
</div>