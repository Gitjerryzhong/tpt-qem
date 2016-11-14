<div class="form-group">
    <label class="col-sm-2 control-label">协议分类</label>
    <div class="col-sm-2">
<p class="form-control-static">{{projectDetail.coTypeName}}</p>
    </div>
    <label class="col-sm-2 control-label">项目分类</label>
    <div class="col-sm-2">
<p class="form-control-static">{{projectDetail.coCountryName}}</p>
    </div>
    <label class="col-sm-1 control-label">有效</label>
    <div class="col-sm-1">
    	<p class="form-control-static">{{projectDetail.effective?"是":"否"}}</p>
    </div>
    <label class="col-sm-1 control-label">双学位</label>
    <div class="col-sm-1">
    	<p class="form-control-static">{{projectDetail.ifTowDegree?"是":"否"}}</p>
    </div>
</div>

<div class="form-group" ng-if="projectDetail.memo">
    <label class="col-sm-2 control-label">备注</label>
    <div class="col-sm-10">
    	<pre>{{projectDetail.memo}}</pre>
    </div>
</div>
