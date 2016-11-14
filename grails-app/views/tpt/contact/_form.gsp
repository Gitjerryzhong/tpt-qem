<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>个人信息</strong></h4>
</div>
<div class="form-group">
    <label for="foreignCollege" class="col-sm-2 control-label">大学名称</label>
    <div class="col-sm-10">
<%--    	<g:textField name="foreignCollege"  class="form-control" placeholder="国外大学名称（全称）" ng-model="contact.foreignCollege" ng-required="true"/>--%>
    <select class="form-control" name="collegeName"
    ng-model="contact.collegeName"
    ng-options="y for y in colleges"    
	>
	<option value="" selected>请选择国外大学名称（全称）*</option>
	</select>
    </div>
</div>
<div class="form-group">
    <label for="foreignMajor" class="col-sm-2 control-label">主修专业</label>
    <div class="col-sm-10">
    	<input type="text" name="foreignMajor"  class="form-control" placeholder="在国外大学主修专业名称（英文） *" ng-model="contact.foreignMajor" required/>
<%--    	<span class="glyphicon glyphicon-ok form-control-feedback"--%>
<%--                              ng-show="myForm.foreignMajor.$dirty && myForm.foreignMajor.$valid"></span>--%>
    </div>
</div>
<div class="form-group">
    <label for="email" class="col-sm-2 control-label">Email地址</label>
    <div class="col-sm-10">
    	<input type="email"  name="email" class="form-control" placeholder="常用有效Email地址 *" ng-model="contact.email" required/>
    	<div ng-show="myForm.email.$dirty && myForm.email.$invalid"><span class="text-danger small">请正确格式输入Email地址</span></div>
    </div>    
</div>
<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>国内紧急联系人信息</strong></h4>
</div>
<div class="form-group">
    <label for="contact" class="col-sm-2 control-label">联系人{{contact.FormId}}</label>
    <div class="col-sm-10">
    	<input id="contactId" type="hidden" ng-model="contact.id"/>
    	<input type="text" name="contact"  class="form-control" placeholder="紧急联系人姓名，如本人在国内可填自己姓名 *" ng-model="contact.contact" required/>
    </div>
</div>
<div class="form-group">
    <label for="contactphone" class="col-sm-2 control-label">联系电话</label>
    <div class="col-sm-10">
    	<input type="text"  name="contactphone"  class="form-control" placeholder="紧急联系电话（国内） *" ng-model="contact.phoneNumber" required/>
    </div>
</div>
