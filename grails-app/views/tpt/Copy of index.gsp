<html >
<head>
<meta name="layout" content="tpt_main" />
<title>2+2出国学生学位申请资格审批系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<asset:javascript src="lib/jquery-1.10.2.min.js"/>
<asset:javascript src="bootstrap/bootstrap.min.js"/>
<%--文件上传--%>
<asset:javascript src="qem/angular-file-upload.min.js"/>

<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:javascript src="angularjs/angular-locale_zh-cn.js"/>
<asset:stylesheet src="tpt/tpt.css"/>
<asset:javascript src="tpt/tptstudent.js"/>

</head>
<body >
	<div  ng-app="tptApp" id="tptstd" class="container" >		
		<div class=" col-md-offset-2 col-sm-9 col-sm-offset-1">
			<div  ui-view=""> </div>
		</div>
		<script type="text/ng-template" id="tpt-newrequest.html">
	<div id="tptNew" ng-controller="NewCtrl">
		<div class="modal-header">           
            <h3 class="modal-title">申请起止日期：<strong>{{notice.start}}</strong>——<strong>{{notice.end}}</strong></h3>
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
			<button class="btn btn-primary" ng-show="inTime" ng-click="next()"> 继续>>></button>
			<button class="btn btn-primary" ng-show="checked" ui-sref="flow"> 我要申请</button>
		</div>
	</div>
	</script>
	<script type="text/ng-template" id="tpt-myrequest.html">
	<p>我的申请</p>
	</script>
	<script type="text/ng-template" id="tpt-message.html">
	<p>我的消息</p>
	</script>
	<script type="text/ng-template" id="tpt-flow.html">		
	<div ng-controller="modalFlow">
		<div class="btn-toolbar">
			<button ng-class="bn1" ng-click="open1()">紧急联系信息</button>
			<span class="glyphicon glyphicon-arrow-right"></span>
			<button ng-class="bn2" ng-click="open2()">上传证书成绩单照片</button>
			<span class="glyphicon glyphicon-arrow-right"></span>
			<button  ng-class="bn3" ng-click="open3()">论文互认申请表</button>
			<span class="glyphicon glyphicon-arrow-right"></span>
			<button  ng-class="bn4" ng-click="open4()">上传论文</button>
		</div>
	</div>
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
	</div>
</body>
</html>