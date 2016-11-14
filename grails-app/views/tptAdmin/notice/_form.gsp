<div ng-show="editAble">
<div class="form-group">
    <label for="title" class="col-sm-2 control-label">标题</label>
    <div class="col-sm-8">
    	<input id="projectId" type="hidden" ng-model="notice.id"/>
    	<g:textField name="title"  class="form-control" placeholder="通知标题" ng-model="notice.title" ng-required="true"/>
    </div>
</div>
<div class="form-group">
    <label for="content" class="col-sm-2 control-label">内容</label>
    <div class="col-sm-8">
    	<g:textArea name="content"  rows="15" cols="40" class="form-control" placeholder="请输入通知内容" ng-model="notice.content"/>
    </div>
</div>
<div class="form-group">
    <label for="start" class="col-sm-2 control-label">开始日期</label>
    <div class="col-sm-8 input-group">
    	<input type="text" class="form-control" current-text="今天" clear-text="清除" close-text="关闭" datepicker-popup ng-model="notice.start" is-open="startid" datepicker-options="dateOptions" date-disabled = "disabled(date,mode)" ng-required="true" close-text="Close" ng-readonly="true">
        <span class="input-group-btn">
            <button type="button" class="btn btn-default" ng-click="open($event,0)">
                <i class="glyphicon glyphicon-calendar"></i>
            </button>
        </span>
    </div>
</div>
<div class="form-group">
    <label for="end" class="col-sm-2 control-label">截至日期</label>
    <div class="input-group col-sm-8">
    	<input type="text" class="form-control" current-text="今天" clear-text="清除" close-text="关闭" datepicker-popup ng-model="notice.end" is-open="endid" datepicker-options="dateOptions" date-disabled = "disabled(date,mode)" ng-required="true" close-text="Close" ng-readonly="true">
        <span class="input-group-btn">
            <button type="button" class="btn btn-default" ng-click="open($event,1)">
                <i class="glyphicon glyphicon-calendar"></i>
            </button>
        </span>
    </div>
</div>
</div>
<%--<div class="form-group">--%>
<%--    <label for="bn" class="col-sm-2 control-label">批号</label>--%>
<%--    <div class="col-sm-8">--%>
<%--    	<g:textField name="bn"  class="form-control" placeholder="申请批号" ng-model="notice.bn" ng-required="true"/>--%>
<%--    </div>--%>
<%--</div>--%>