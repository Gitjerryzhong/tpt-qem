<div class="form-group">
	<label for="name" class="col-sm-2 control-label">协议名称</label>
    <div class="col-sm-10">
    <input type="text" name="name" ng-model="coProject.name" class="form-control" placeholder="请合作协议名称" required>
	</div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">协议分类</label>
    <div class="col-sm-4">
    <select class="form-control" 
    ng-model="coProject.coTypeId"
    ng-options="y.id as y.name for y in coTypes" >
	</select>
	</div>
	<label class="col-sm-2 control-label">所属项目</label>
    <div class="col-sm-4">
    <select class="form-control" 
    ng-model="coProject.coCountryId"
    ng-options="y.id as y.name for y in coCountrys" >
	</select>
	</div>
</div>

<%--<div class="form-group">--%>
<%--    <label for="cycle" class="col-sm-2 control-label">起始年份</label>--%>
<%--    <div class="col-sm-4" id="beginYear">--%>
<%--    <div class="input-group">--%>
<%--	<input class="form-control" ng-model="coProject.beginYear" ng-readonly="true"><span class="input-group-btn"><button class="btn btn-default" type="button" ng-click="editYear('beginYear')"><span class="glyphicon glyphicon-calendar"></span></button></span>--%>
<%--	</div>	--%>
<%--	</div>--%>
<%--	<label for="cycle" class="col-sm-2 control-label">有效年份</label>--%>
<%--    <div class="col-sm-4 form-control-static"  id="editYear">--%>
<%--    <span >{{coProject.effeYearStr}}</span><a href="#" ng-click="editYear('editYear')" style="margin-left:20px">编辑</a>--%>
<%--	</div>	--%>
<%--</div>--%>
<div class="form-group">
	<label for="actived" class="col-sm-2 control-label">是否有效</label>
    <div class="col-sm-4 form-control-static">
    	有效<input type="radio" ng-model="coProject.actived" value="1" style="margin-right:40px" required/>
    	无效<input type="radio" ng-model="coProject.actived" value="0"/>
	</div>
	<label for="actived" class="col-sm-2 control-label">双学位</label>
    <div class="col-sm-4 form-control-static">
    	是<input type="radio" ng-model="coProject.ifTowDegree" value="1" style="margin-right:40px" required/>
    	否<input type="radio" ng-model="coProject.ifTowDegree" value="0"/>
	</div>
</div>
<div class="form-group">
	 <label for="content" class="col-sm-2 control-label">备注</label>
	 <div class="col-sm-10">
		<textarea name="content"  rows="4" class="form-control" placeholder="如有特别说明，请备注！" ng-model="coProject.memo"></textarea>
	 </div>
</div>
<%--<div class="panel panel-info" id="effeYear" style="width:250px;height:230px;position:absolute;" ng-show="editYearShow">--%>
<%--	<div class="panel-heading">--%>
<%--		<div class="input-group">--%>
<%--			<span class="input-group-btn"><button class="btn btn-default" type="button" ng-click="prePage()"><span class="glyphicon glyphicon-chevron-left"></span></button></span>--%>
<%--		  	<input type="button" class="form-control" value="选择年份">--%>
<%--		  	<span class="input-group-btn"><button class="btn btn-default" type="button" ng-click="nextPage()"><span class="glyphicon glyphicon-chevron-right"></span></button></span>--%>
<%--		</div>--%>
<%--	</div>--%>
<%--	<div class="panel-body" >--%>
<%--		<div class="form-group"><button class="from-control years" ng-repeat="y in yearList" ng-click="selectYear(y)" ng-class="{'btn-success':yearBtnClass(y)}">{{y}}</button></div>--%>
<%--		<button class="btn btn-primary btn-sm" ng-click="yearConfirm()">确定</button>--%>
<%--	</div>--%>
<%--</div>--%>