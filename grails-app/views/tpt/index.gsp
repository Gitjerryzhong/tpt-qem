<html >
<head>
<meta name="layout" content="main" />
<title>2+2出国学生学位申请资格审批系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="qem/angular-file-upload.min.js"/>

<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:javascript src="angularjs/angular-locale_zh-cn.js"/>
<asset:stylesheet src="tpt/tpt.css"/>
<asset:javascript src="tpt/tptstudent.js"/>

</head>
<body >
	<div  ng-app="tptApp" id="tptstd" class="container" ng-controller="ReqCtrl">		
		<div class=" col-md-offset-2 col-sm-9 col-sm-offset-1">
			<div class="btn-toolbar pull-right">
				<button ng-class="bn1" ">阅读声明</button>
				<span class="glyphicon glyphicon-arrow-right"></span>
				<button ng-class="bn2" ">初审材料</button>
				<span class="glyphicon glyphicon-arrow-right"></span>
				<button  ng-class="bn3"">初审结果</button>
				<span class="glyphicon glyphicon-arrow-right"></span>			
				<button  ng-class="bn5" ">终审结果</button>
			</div>
			<a class="btn btn-success" href="tpt/help" target="_blank">帮助</a>
			<div  ui-view=""> </div>
		</div>
		<script type="text/ng-template" id="tpt-declareInfo.html">
	<div ng-show="!noticeDetail">
		<div class="modal-header">           
            <h3 class="modal-title">相关通知</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="notice/formList"></g:render>
        	</div>
        </div>
	</div>
	<div id="tptNew" ng-show="noticeDetail">
		<div class="modal-header">           
            <h3 class="modal-title">申请起止日期：<strong>{{dateFormat(notice.start) | date:"yyyy-MM-dd"}}</strong>——<strong>{{dateFormat(notice.end) | date:"yyyy-MM-dd"}}</strong></h3>
			<h3>请在此间提交相关材料</h3>
        </div>		
        <div class="modal-body">
			<pre>
{{notice.content}}
			</pre>
			<div ng-show="agreeShow">
				<strong>我同意：  </strong><input type="checkBox" ng-model="checked" ng-click="check()">
			</div>
        </div>
		<div class="modal-footer">
			<p class="Emphasis" ng-if="!succeed">为了您能顺利完成申请，请先阅读帮助！</p>
			<p class="Emphasis" ng-if="succeed">您的申请已终审通过！</p>
			<button  class="btn btn-default" ng-click="noticeDetail=false;agreeShow=false;">后 退</button> 
			<button class="btn btn-primary" ng-if="inTime && !myRequest " ng-click="next()"> 继续>>></button>
			<button class="btn btn-primary" ng-if="myRequest" ng-click="showRequest()"> 我的申请</button>
			<button class="btn btn-primary" ng-if="checked && !myRequest && !succeed" ng-click="gofirstStep()"> 我要申请</button>
		</div>
	</div>
	</script>

	<script type="text/ng-template" id="tpt-firstStep.html">		
	 %{--//创建联系人视图--}%
		
        <div class="modal-header">
            <h3 class="modal-title"><strong>{{username}}</strong> 申请单</h3>
        </div>
		<form name="myForm" role="form" novalidate>	
        <div class="modal-body">		
			<div class="row">
				<div class="col-sm-9">
					<div class="form-horizontal" >				
            			<g:render template="contact/form"></g:render>
        			</div>
				</div>
				<div class="col-sm-3 bg-info" style="margin-top:50px;padding-top:5px">
					<div class="text-center"><img alt="照片" src="{{imgs.photo}}" width="auto" height="192" style="text-align: center;max-width:150px"></div>
					<div class="img-btn"><button class="btn-xs btn-info" ng-click="uploadPhoto(1)">上传照片</button></div>
				</div>	
			</div>
			<div class="row">
				<div class="col-sm-3">
					<div class="text-center panel panel-info"><a href="tpt/showImage?filename=certi_" target="_blank"><img alt="证书" src="{{imgs.cert}}" width="auto" height="192" style="text-align: center;max-width:150px"></a></div>
					<div class="img-btn"><button class="btn-xs btn-info" ng-click="uploadPhoto(2)">上传证书</button></div>
				</div>	
				<div class="col-sm-3">
					<div class="text-center panel panel-info"><a href="tpt/showImage?filename=trans_1" target="_blank"><img alt="国外本科成绩" src="{{imgs.trans1}}" width="auto" height="192" style="text-align: center;max-width:150px"></a></div>
					<div class="img-btn"><button class="btn-xs btn-info" ng-click="uploadPhoto(3)">国外本科成绩</button></div>
				</div>	
				<div class="col-sm-3">
					<div class="text-center panel panel-info"><a href="tpt/showImage?filename=trans_2" target="_blank"><img alt="国外硕士成绩" src="{{imgs.trans2}}" width="auto" height="192" style="text-align: center;max-width:150px"></a></div>
					<div class="img-btn"><button class="btn-xs btn-info" ng-click="uploadPhoto(4)">国外硕士成绩</button></div>
				</div>	
				<div class="col-sm-3">
					<div class="text-center panel panel-info"><a href="tpt/showImage?filename=trans_3" target="_blank"><img alt="成绩3" src="{{imgs.trans3}}" width="auto" height="192" style="text-align: center;max-width:150px"></a></div>
					<div class="img-btn"><button class="btn-xs btn-info" ng-click="uploadPhoto(5)">成绩3</button></div>
				</div>	
				<p class="Emphasis"><strong>注意：</Strong></p>
				<ol class="Emphasis" >
					<li style="padding:1em">请务必上传数码相片，以备制作学位证用！</li>
					<li style="padding:1em">请不要上传北师大珠海分校的成绩单！</li>
				</ol>				
				<p style="color:blue">说明：PDF文件不可预览，请直接点击查看源文件！</p>
			</div>
		
        </div>
        <div class="modal-footer">
            <button class="btn btn-default" ng-click="save()"  ng-disabled="myForm.$invalid"> 保存</button>
<%--            <button class="btn btn-info" ng-click="upload()">上传文件</button>--%>
			<button class="btn btn-primary" ng-show="saved" ng-click="apply()">提交申请</button>
        </div>
		</form>
	</script>
	<script type="text/ng-template" id="contact.html">
        %{--//创建联系人视图--}%
        <div class="modal-header">
            <h3 class="modal-title">填写紧急联系信息</h3>
        </div>
        <div class="modal-body">			
			<div class="form-horizontal" >				
            	<g:render template="contact/form"></g:render>
        	</div>			
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="ok()"> 保存</button>
            <button class="btn btn-warning" ng-click="cancel()">取消</button>
        </div>
    </script>  
      <script type="text/ng-template" id="attachment.html">
        %{--//创建文件上传视图--}%
        <div class="modal-header">
            <h3 class="modal-title">上传{{picName}}</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" nv-file-drop="" uploader="uploader">				
            	<g:render template="attachment/form"></g:render>
        	</div>
        </div>
        <div class="modal-footer">
             <button type="button" class="btn btn-success btn-s" ng-click="startUpload()" ng-disabled="!uploader.getNotUploadedItems().length ">确定</button>
            <button type="button" class="btn btn-danger btn-s" ng-click="uploader.clearQueue()" ng-disabled="!uploader.queue.length">清空</button>
			<button class="btn btn-warning" ng-click="cancel()">关闭</button>
			<div ng-show="showHine" style="margin-top:10px;"><span class="glyphicon glyphicon-info-sign"></span><span class="text-warning"><Strong>校验错误</strong>：不符合{{picLimit[1]}}</span></div>
        </div>
        </script>  
      <script type="text/ng-template" id="tpt_subStep.html">
        %{--//创建提交后视图--}%
        <div class="modal-header">
            <h3 class="modal-title" ng-class="{Emphasis:showWarning(contact.status)}">{{statusTitle(contact.status)}}</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="apply/form"></g:render>
        	</div>
        </div>
        <div class="modal-footer">
			 <button class="btn btn-warning" ng-click="goUpdate()" ng-show="allowUpdate()"> 修改材料</button>
			<button class="btn btn-primary" ng-show="allowPaperUpdate()" ui-sref="paperAudit3"> 仅重新上传论文</button>
			<button class="btn btn-primary" ng-show="allowPaper() || allowPaperUpdate()" ui-sref="paperAudit2"> 课程论文互认申请</button>
             <button class="btn btn-primary" ng-show="allowPaper() || allowPaperUpdate()" ui-sref="paperAudit"> 本科论文互认申请</button>
			 <button class="btn btn-primary" ng-show="allowPaper() || allowPaperUpdate()" ui-sref="paperAudit1"> 硕士论文互认申请</button>
        </div>
        </script> 
        <script type="text/ng-template" id="tpt_finally.html">
        %{--//创建提交后视图--}%
        <div class="modal-header">
            <h3 class="modal-title" ng-class="{Emphasis:showWarning(contact.status)}">{{statusTitle(contact.status)}}</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="apply/form"></g:render>
        	</div>
        </div>
        <div class="modal-footer">
			
        </div>
        </script>   
        <script type="text/ng-template" id="tpt_paperAudit.html">
        %{--//创建本科论文互认表视图--}%
        <div class="modal-header">
            <h3 class="modal-title">国外学生本科毕业论文成绩互认申请表</h3>
        </div>
		<form name="masterForm" role="form" novalidate>	
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="paper/form"></g:render>
				<g:render template="paper/formUpload"></g:render>
        	</div>
        </div>
        <div class="modal-footer">							 
             <button class="btn btn-primary" ng-disabled="masterForm.$invalid || !uploader.getNotUploadedItems().length" ng-click="paperSubmit(1)"> 提交论文</button>
        </div>
		</form>
        </script>
        <script type="text/ng-template" id="tpt_paperAudit1.html">
        %{--//创建硕士论文互认表视图--}%
        <div class="modal-header">
            <h3 class="modal-title">国外学生硕士毕业论文成绩互认申请表</h3>
        </div>
		<form name="educateForm" role="form" novalidate>	
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="paper/forme"></g:render>
				<g:render template="paper/formUpload"></g:render>
        	</div>
        </div>
        <div class="modal-footer">					 
             <button class="btn btn-primary" ng-disabled="educateForm.$invalid || !uploader.getNotUploadedItems().length" ng-click="paperSubmit(2)"> 提交论文</button>
        </div>
		</form>
        </script>  
        <script type="text/ng-template" id="tpt_paperAudit2.html">
        %{--//创建课程论文互认表视图--}%
        <div class="modal-header">
            <h3 class="modal-title">国外学生本科课程论文成绩互认申请表</h3>
        </div>
		<form name="educateForm" role="form" novalidate>	
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="paper/formc"></g:render>
				<g:render template="paper/formUpload"></g:render>
        	</div>
        </div>
        <div class="modal-footer">					 
             <button class="btn btn-primary" ng-disabled="educateForm.$invalid || !uploader.getNotUploadedItems().length" ng-click="paperSubmit(3)"> 提交论文</button>
        </div>
		</form>
        </script>  
        <script type="text/ng-template" id="tpt_paperAudit3.html">
        %{--//创建课程论文互认表视图--}%
        <div class="modal-header">
            <h3 class="modal-title">国外学生本科课程论文成绩互认申请表</h3>
        </div>
		<form name="educateForm" role="form" novalidate>	
        <div class="modal-body">
			<div class="form-horizontal" >				
				<g:render template="paper/formUpload"></g:render>
        	</div>
        </div>
        <div class="modal-footer">					 
             <button class="btn btn-primary" ng-disabled=" !uploader.getNotUploadedItems().length" ng-click="uploader.uploadAll()"> 提交论文</button>
        </div>
		</form>
        </script>  
	</div>
</body>
</html>