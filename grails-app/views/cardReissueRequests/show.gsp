<html>
<head>
<meta name="layout" content="main" />
<title>学生证补办申请</title>
<asset:stylesheet src="card/reissue/request/show"/>
<asset:javascript src="card/reissue/request/show"/>
<asset:script>
$(function() {
    $("#cardReissueRequestShow").cardReissueRequestShow({
    	status:${card.status},
    	editable:${editable}
    });
});
</asset:script>
</head>
<body>
	<div id="cardReissueRequestShow">
		<g:if test="${editable}">
		<div id="toolbar" class="btn-toolbar">
			<div class="pull-right">
				<button id="edit" class='btn btn-default' title='编辑假条'>编辑</button>
				<button id="apply" class='btn btn-default' title='提交申请'>提交</button>
				<button id="cancel" class='btn btn-default' title='撤销申请'>撤销</button>
				<button id="delete" class='btn btn-default' title='删除假条'>删除</button>
			</div>
		</div>
		</g:if>
		<div id="card">
			<form class="form-horizontal container">
				<g:render template="student"/>
				<div class="form-group">
					<label class="col-sm-3 control-label">补办理由</label>
					<div class="col-sm-9">
						<p class="form-control-static">${card.reason.encodeAsHTML().replace("\n", "<br>")}</p>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-sm-6 control-label">补办时间</label>
							<div class="col-sm-6">
								<p class="form-control-static">${card.dateCreated.format("yyyy-MM-dd")}</p>
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<label class="col-sm-6 control-label">办理状态</label>
							<div class="col-sm-6">
								<p class="form-control-static" id="status"></p>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>