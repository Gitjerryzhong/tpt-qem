<html >
<head>
<meta name="layout" content="main" />
<title>教学质量工程系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="qem/angular-file-upload.min.js"/>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:javascript src="angularjs/angular-locale_zh-cn.js"/>
<asset:stylesheet src="qem/projectDetail.css"/>
<asset:javascript src="qem/projectDetail.js"/>
<script>
qemApp.value("config", {
	project: ${ project as grails.converters.JSON},
	notice: ${notice as grails.converters.JSON},
	fileList: ${fileList as grails.converters.JSON}
	});
</script>
</head>
<body >
	<div  ng-app="qemApp" id="qem" class="row">
		<div  ng-controller="myDetailCtrl">	
		<div class="btn-group pull-right">	
				<button class="btn btn-default" ng-click="save()" ng-disabled="checkEditAble()" ng-show="editAble">保存</button>
				<button class="btn btn-default" ng-click="apply()" ng-disabled="checkEditAble()">提交</button>			
				<button class="btn btn-default" ng-click="cancel()" ng-disabled="!(project.commit && project.collegeStatus==0)">撤销</button>				
				<button class="btn btn-default" ng-click="edit()" ng-disabled="checkEditAble()">编辑</button>
			</div>
        <div class="modal-header" id="mystep">
			<ol>
			<li>
				<div class="flowstep">
					<div class="step-name">提交申请</div>
					<div ng-class="{'first-step-no':!project.commit,'first-step-done':project.commit}"><span ng-class="{'glyphicon glyphicon-ok small':project.commit}">{{project.commit?"":"1"}}</span></div>
				</div>
			</li>
			<li>
				<div class="flowstep">
					<div class="step-name">学院审核</div>
					<div ng-class="{'step-no':project.collegeStatus==0,'step-done':project.collegeStatus==1 || project.collegeStatus==3,'step-stop':project.collegeStatus==2}"><span ng-class="{'glyphicon glyphicon-ok small':project.collegeStatus==1 ||project.collegeStatus==3,'glyphicon glyphicon-remove small':project.collegeStatus==2}">{{project.collegeStatus?"":"2"}}</span></div>
				</div>
			</li>
			<li ng-if="project.collegeStatus==3">
				<div class="flowstep">
					<div class="step-name">学校审核</div>
					<div ng-class="{'last-step-done':project.collegeStatus==3}"><span ng-class="{'glyphicon glyphicon-remove small':project.collegeStatus==3}">{{project.collegeStatus?"":"3"}}</span></div>
				</div>
			</li>
			<li ng-if="project.collegeStatus!=3">
				<div class="flowstep">
					<div class="step-name">专家评审</div>
					<div ng-class="{'step-no':project.status==0,'step-done':project.status>=1}"><span ng-class="{'glyphicon glyphicon-ok small':project.status>=1}">{{project.status?"":"3"}}</span></div>
				</div>
			</li>
			<li  ng-if="project.collegeStatus!=3">
				<div class="flowstep">
					<div class="step-name">结果</div>
					<div ng-class="{'last-step-no':project.status<4,'last-step-done':project.status>=4}"><span ng-class="{'glyphicon glyphicon-ok small':project.status==4 || project.status==6,'glyphicon glyphicon-remove small':project.status==5}">{{project.status?"":"4"}}</span></div>
				</div>
			</li>
			</ol>
			<div class="form-group"  ng-show="project.status>=4">
		    <label  class="col-md-2 control-label Emphasis">专家评审意见</label>
		    <div>
		    	{{project.view}}
		    </div>
</div>
        </div>		
        <div class="modal-body well">
        	<button type="button" class="btn btn-link pull-right" ng-click="goBack()" toolTip="退出返回" style="font-size:16pt;"><span class="glyphicon glyphicon-log-out"></span></button>
			<div class="form-horizontal" ng-show="!editAble">				
            	<g:render template="mydetail/form"></g:render>
        	</div>
        	<div class="form-horizontal" ng-show="editAble">				
            	<g:render template="project/form"></g:render>
<%--            	<g:render template="project/formUpload"></g:render>--%>
        	</div>
        </div>
	</div>   	
</div>
</body>
</html>