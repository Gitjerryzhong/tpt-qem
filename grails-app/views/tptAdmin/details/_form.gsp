<div class="row">
<div class="col-sm-9">
	<div class="form-group">
	    <label for="foreignCollege" class="col-sm-2 control-label">大学名称</label>
	    <div class="col-sm-10">
	<p class="form-control-static">{{contact.collegeName}}</p>
	    </div>
	</div>
	<div class="form-group">
	    <label for="foreignMajor" class="col-sm-2 control-label">主修专业</label>
	    <div class="col-sm-10">
	    	<p class="form-control-static">{{contact.foreignMajor}}</p>
	    </div>
	</div>
	<div class="form-group">
	    <label for="contact" class="col-sm-2 control-label">联系人{{contact.FormId}}</label>
	    <div class="col-sm-10">
	    	<p class="form-control-static">{{contact.contact}}</p>
	    </div>
	</div>
	<div class="form-group">
	    <label for="contactphone" class="col-sm-2 control-label">联系电话</label>
	    <div class="col-sm-10">
	    	<p class="form-control-static">{{contact.phoneNumber}}</p>
	    </div>
	</div>
	<div class="form-group">
	    <label for="email" class="col-sm-2 control-label">Email地址</label>
	    <div class="col-sm-10">
	    	<p class="form-control-static">{{contact.email}}</p>
	    </div>    
	</div>
	<div class="form-group">
	    <label for="email" class="col-sm-2 control-label">当前状态</label>
	    <div class="col-sm-10">
	    	<p class="form-control-static">{{statusText(contact.status)}}</p>
	    </div>    
	</div>
	<div class="form-group">
	    <label for="" class="col-sm-2 control-label">操作日志</label>
	    <div class="col-sm-10" >
	    <ul class="form-control-static list-unstyled">
			<li ng-repeat="item in audits | orderBy:'id'">				
	    			{{dateFormat(item.date) | date : 'yyyy-MM-dd HH:mm:ss'}}{{item.userName}} {{actionText(item.action)}} {{item.content}}
			</li>			
		</ul>
	    </div>
	</div>	
</div>
<div class="col-sm-3 bg-info text-center">
	<img alt="照片" src="{{imgs.photo}}" width="auto" height="192" style="text-align: center;max-width:150px">
</div>	
</div>
<div class="row">
	<div class="col-sm-3">
		<div class="text-center panel panel-info" toolTip="点击查看原文件"><p>证书</p><a href="showImage?filename=certi_&form_id={{contact.formId}}" target="_blank"><img alt="证书" src="{{imgs.cert}}" width="auto" height="192" style="text-align: center;max-width:150px"></a></div>
	</div>	
	<div class="col-sm-3">
		<div class="text-center panel panel-info" toolTip="点击查看原文件"><p>国外本科成绩</p><a href="showImage?filename=trans_1&form_id={{contact.formId}}" target="_blank"><img alt="国外本科成绩" src="{{imgs.trans1}}" width="auto" height="192" style="text-align: center;max-width:150px"></a></div>
	</div>	
	<div class="col-sm-3">
		<div class="text-center panel panel-info" toolTip="点击查看原文件"><p>国外硕士成绩</p><a href="showImage?filename=trans_2&form_id={{contact.formId}}" target="_blank"><img alt="国外硕士成绩" src="{{imgs.trans2}}" width="auto" height="192" style="text-align: center;max-width:150px"></a></div>
	</div>	
	<div class="col-sm-3">
		<div class="text-center panel panel-info" toolTip="点击查看原文件"><p>成绩3</p><a href="showImage?filename=trans_3&form_id={{contact.formId}}" target="_blank"><img alt="成绩3" src="{{imgs.trans3}}" width="auto" height="192" style="text-align: center;max-width:150px"></a></div>
	</div>	
<%--	<p style="color:red">说明：PDF文件不可预览，请直接点击查看源文件！</p>--%>
</div>
<div class="form-group well" ng-if="contact.status==4">
		<div class="col-sm-9">
	    <label class="col-sm-2 control-label">论文材料</label>
	    <div class="col-sm-10 ">
	    	<ul class="form-control-static" >
	    		<li><span class="glyphicon glyphicon-warning-sign Emphasis" ng-if="!paperFile"></span>论文：<strong>{{paperFile?"√":"上传不成功！"}}</strong></li>
	    		<li><span class="glyphicon glyphicon-warning-sign Emphasis" ng-if="!paperExchFile"></span>论文成绩互认表：<strong>{{paperExchFile?"√":"提交失败！"}}</strong><span style="margin-left:6em;"><a class="btn btn-primary" href="" ng-click="updateScort(contact.formId)">修正成绩</a></span></li>
	    	</ul>
	    </div>
	    </div>
</div >
