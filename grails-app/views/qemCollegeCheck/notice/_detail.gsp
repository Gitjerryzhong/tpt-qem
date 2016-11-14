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
