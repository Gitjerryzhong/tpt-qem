<html>
<head>
<meta name="layout" content="main" />
<title>${issue ? "编辑问题#"+issue.id : "新建问题"}</title>
<asset:stylesheet src="issues/form"/>
<asset:javascript src="issues/form"/>
<asset:script>
$(function() {
    $("#issueForm").issueForm({
    	issueId : ${issue ? issue.id : 'null'}
    });
});
</asset:script>
</head>
<body>
	<div class="row">
		<div id="sidebar" class="col-sm-3">
			<g:render template="sidebar" bean="${statis}"/>
		</div>
		<div id="issueForm" class="col-sm-9 ">
			<div class="btn-toolbar">
				<div class="btn-group">
					<select id="issue-type" class="form-control" style="width:120px;">
						<option value="1" <g:if test="${issue?.type == 1}">selected="selected"</g:if>>错误报告</option>
						<option value="2" <g:if test="${issue?.type == 2}">selected="selected"</g:if>>功能改进</option>
						<option value="3" <g:if test="${issue?.type == 3}">selected="selected"</g:if>>新功能</option>
					</select>
				</div>
				<div class="btn-group pull-right">
					<button class="btn btn-default preview">预览</button>
					<button class="btn btn-primary submit">发布</button>
				</div>
			</div>
			<div class="edit-pane form">
				<div class="form-group">
					<input type="text" id="title"
						class="form-control"
						placeholder="标题(10-50字)"
						data-validate-name="标题" 
						data-validate-min="10"
						data-validate-max="512" value="${issue?.title}">
					</div>
				<div class="form-group">
					<textarea id="content" 
						class="form-control" 
						placeholder="内容(10-500字)"
						rows="20"
						data-validate-name="内容" 
						data-validate-min="10"
						data-validate-max="500">${issue?.content}</textarea>
					<div id="errors" style="display:none">
						<ul class="list-unstyled text-danger" >	</ul>
					</div>
					<div class="well" style="margin-top:15px">
这里<strong>不是</strong>论坛，请提交在使用<a href="http://es.bnuz.edu.cn" target="_blank">教务管理系统</a>、<a 
href="http://eol.bnuz.edu.cn" target="_blank">网络教学综合平台</a>和本系统过程中遇到的问题或改进意见。无关问题将被关闭或删除。
					</div>
				</div>
			</div>
			<div class="prev-pane" style="display:none">
				<div class="title"></div>
				<div class="content markdown"></div>
			</div>
		</div>
	</div>
</body>
</html>