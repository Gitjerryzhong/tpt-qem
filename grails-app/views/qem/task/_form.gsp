<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>任务书内容</strong></h4>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">项目编号</label>
    <div class="col-sm-2 form-control-static"><span class="Emphasis">{{task.sn}}</span></div>
    <label class="col-sm-2 control-label">批准金额</label>
    <div class="col-sm-2 form-control-static"><span>{{task.budget}}万元</span></div>
     <label for="beginYear" class="col-sm-2 control-label">立项年份<span class="Emphasis">*</span></label>
    <div class="col-sm-2">
    	<input type="text" name="beginYear"  class="form-control" placeholder="立项年份" ng-model="task.beginYear" ng-change="changeBegin()" required/>
    </div>
</div>
<div class="form-group">   
    <label for="expectedMid" class="col-sm-2 control-label">中期预期<span class="Emphasis">*</span></label>
    <div class="col-sm-4">
    	<input type="number" name="expectedMid"  class="form-control" placeholder="预计中期检查的年份*" ng-model="task.expectedMid" required/>
    </div>
    <label for="expectedEnd" class="col-sm-2 control-label">结题预期<span class="Emphasis">*</span></label>
    <div class="col-sm-4">
    	<input type="number" name="expectedEnd"  class="form-control" placeholder="预计结题的年份*" ng-model="task.expectedEnd" readonly="true"/>
    </div>
</div>
<div class="form-group">
    <label for="projectContent" class="col-sm-2 control-label">主要内容</label>
    <div class="col-sm-10">
    	<textArea name="projectContent" rows="4" class="form-control" placeholder="简略说明项目主要内容" ng-model="task.projectContent" required>
    	</textArea>
    </div>
</div>
<div class="form-group">
    <label for="expectedGain" class="col-sm-2 control-label">预期成果</label>
    <div class="col-sm-10">
    	<textArea name="expectedGain" rows="4" class="form-control" placeholder="简略列举预期成果" ng-model="task.expectedGain" required>
    	</textArea>
    </div>
</div>
<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>项目成员</strong></h4>
</div>
<div class="form-group">
    <label for="team" class="col-sm-2 control-label">成员姓名</label>
    <div class="col-sm-10"> 
    	<span class="input-group  form-control-static"><span class="expert-admin" ng-click="span_click()" ng-show="!clicked" style="margin-right:20px;">【添加第{{members.length+1}}参与人】</span>
    	<input type="text"   class="form-control"  ng-model="member.member"  ng-show="clicked" placeholder="请输入姓名后回车" ng-keyup="myKeyup($event)" size="5" style="width:10em;margin-right:10px;"/> 
    	 <span class="expert-admin" ng-repeat="y in members">{{y}}；</span>
    	 </span>   	
<%--    	<span class="expert-admin  form-control-static" ng-click="span_click()" ng-show="!task.clicked">空</span>--%>
<%--    	<span><input type="text"   class="form-control"  ng-model="member"  ng-show="task.clicked" ng-change="addMember()" size="5" style="width:5em"/></span>--%>
<%--    	<span class="form-control-static">{{task.members}}</span>--%>
<%--    	<span ng-repeat="y in task.members"><span class="expert-admin"  ng-show="!y.clicked">{{y.name}}</span> | --%>
<%--				<select ng-show="y.clicked" ng-model="y.id" class="myselect" ng-options="exp.id+'|'+exp.name as exp.id+'|'+exp.name for exp in allexperts" ng-change="expChange(item,$index)"></select></span>--%>
    </div>
</div>

