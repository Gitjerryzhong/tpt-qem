<div class="form-group">
	<label  class="col-sm-3 control-label">起始年份<span class="Emphasis">*</span></label>
    <div class="col-sm-3" id="beginYear">
    <div class="input-group">
	<input class="form-control" ng-model="projectItem.beginYear" ng-readonly="true" required><span class="input-group-btn"><button class="btn btn-default" type="button" ng-click="editYear('beginYear')"><span class="glyphicon glyphicon-calendar"></span></button></span>
	</div>	
	</div>
	<label  class="col-sm-3 control-label">学院</label>
    <div class="col-sm-3">
    <select class="form-control" 
    ng-model="projectItem.departmentName"
    ng-options="y for y in majors |filter:{'grade':projectItem.beginYear} |uniKey:'departmentName'" >
	</select>
	</div>
</div>

<div class="form-group">
	<label  class="col-sm-3 control-label">专业<span class="Emphasis">*</span></label>
    <div class="col-sm-3">
    <select class="form-control" 
    ng-model="projectItem.majorsId"
    ng-options="y.id as y.majorName for y in majors  |filter:{'grade':projectItem.beginYear,'departmentName': projectItem.departmentName}" >
	</select>
	</div>
	<label  class="col-sm-3 control-label">有效年份<span class="Emphasis">*</span></label>
    <div class="col-sm-3 form-control-static"  id="editYear">
    <span >{{projectItem.effeYearStr}}</span><a href="#" ng-click="editYear('editYear')" style="margin-left:20px" ng-show="projectItem.majorsId">编辑</a>
	</div>	
	
</div>
<div class="form-group">
    <label class="col-sm-3 control-label">外国大学英文名<span class="Emphasis">*</span></label>
    <div class="col-sm-3">
	<input class="form-control" ng-model="projectItem.collegeNameEn" required>
	</div>	
	<label class="col-sm-3 control-label">外国大学中文名<span class="Emphasis">*</span></label>
    <div class="col-sm-3">
    <input class="form-control" ng-model="projectItem.collegeNameCn" required>
	</div>	
	
</div>
<div class="form-group">
	 <label for="content" class="col-sm-3 control-label">可衔接学位/学科/专业</label>
	 <div class="col-sm-9">
		<textarea name="content"  rows="2" class="form-control" placeholder="可衔接学位/学科/专业！" ng-model="projectItem.coDegreeOrMajor"></textarea>
	 </div>
</div>
<div class="form-group">
	 <label for="content" class="col-sm-3 control-label">备注</label>
	 <div class="col-sm-9">
		<textarea name="content"  rows="4" class="form-control" placeholder="如有特别说明，请备注！" ng-model="projectItem.memo"></textarea>
	 </div>
</div>
<div class="panel panel-info" id="effeYear" style="width:250px;height:230px;position:absolute;" ng-show="editYearShow">
	<div class="panel-heading">
		<div class="input-group">
			<span class="input-group-btn"><button class="btn btn-default" type="button" ng-click="prePage()" ng-disabled="preDisabled()"><span class="glyphicon glyphicon-chevron-left"></span></button></span>
		  	<input type="button" class="form-control" value="选择年份">
		  	<span class="input-group-btn"><button class="btn btn-default" type="button" ng-click="nextPage()"><span class="glyphicon glyphicon-chevron-right"></span></button></span>
		</div>
	</div>
	<div class="panel-body" >
		<div class="form-group"><button class="from-control years" ng-repeat="y in yearList track by $index " ng-click="selectYear(y)" ng-class="{'btn-success':yearBtnClass(y)}" ng-disabled="disabledYear(y)">{{y}}</button></div>
		<button class="btn btn-primary btn-sm" ng-click="yearConfirm()">确定</button>
	</div>
</div>