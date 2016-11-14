<%--<div class="form-group">--%>
<%--<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>{{stageTitle(stage.currentStage)}}</strong></h4>--%>
<%--</div>--%>
<div class="panel panel-default">
<div class="panel-heading title">{{stageTitle(stage.currentStage)}}
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span >{{stage.sn}}</span></div>
    <label class="col-sm-2 control-label">参与人</label>
    <div class="col-sm-6 form-control-static"><span>{{stage.members}}</span></div>     
</div>
<div class="form-group">
    <label  class="col-sm-2 control-label">完成日期</label>
    <div class="col-sm-3">
    	<div class="input-group">
    	<input type="text" class="form-control"  current-text="今天" clear-text="清除" close-text="关闭" datepicker-popup ng-model="stage.finishDate" is-open="endid" datepicker-options="dateOptions"  ng-required="true" close-text="Close" ng-readonly="true" >
        <span class="input-group-btn">
            <button type="button" class="btn btn-default" ng-click="open($event)">
                <i class="glyphicon glyphicon-calendar"></i>
            </button>
        </span>
        </div>
    </div>
</div>
<div class="form-group">
    <label for="projectContent" class="col-sm-2 control-label">{{isEnd()?"主要内容和特色":"进展正文"}}</label>
    <div class="col-sm-10">
    	<textArea name="projectContent" rows="4" class="form-control" ng-model="stage.progressText" ng-maxlength="1500" required>
    	</textArea>
    	<div ng-show="myForm.projectContent.$error.maxlength"><span class="text-danger small">内容不要超过1500字</span></div>
    </div>
</div>
<div class="form-group">
    <label for="unfinishedReson" class="col-sm-2 control-label">{{isEnd()?"主要成果":"未完成原因"}}</label>
    <div class="col-sm-10">
    	<textArea name="unfinishedReson" rows="4" class="form-control"  ng-model="stage.unfinishedReson" ng-maxlength="1500" required>
    	</textArea>
    	<div ng-show="myForm.unfinishedReson.$error.maxlength"><span class="text-danger small">内容不要超过1500字</span></div>
    </div>
</div>
<div class="form-group">
    <label for="memo" class="col-sm-2 control-label">{{isEnd()?"成果应用情况":"其他说明"}}</label>
    <div class="col-sm-10">
    	<textArea name="memo" rows="4" class="form-control" ng-model="stage.memo"  ng-maxlength="1500" required>
    	</textArea>
    	<div ng-show="myForm.memo.$error.maxlength"><span class="text-danger small">内容不要超过1500字</span></div>
    </div>
</div>
</div>
<div class="panel panel-default">
<div class="panel-heading title">附    件
</div>
<g:render template="stage/formUpload"></g:render>
</div>
<%--<div class="form-group">--%>
<%--<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>本期划拨经费</strong></h4>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--	<label class="col-sm-2 control-label">省级资助</label>--%>
<%--    <div class="col-sm-2 form-control-static"><span>{{stage.fundingProvince}}万元</span></div>--%>
<%--    <label class="col-sm-2 control-label">校级配套</label>--%>
<%--    <div class="col-sm-2 form-control-static"><span >{{stage.fundingUniversity}}万元</span></div>--%>
<%--    <label class="col-sm-2 control-label">院级配套</label>--%>
<%--    <div class="col-sm-2 form-control-static"><span>{{stage.fundingCollege}}万元</span></div>     --%>
<%--</div>--%>

