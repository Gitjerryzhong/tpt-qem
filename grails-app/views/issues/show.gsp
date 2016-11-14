<html>
<head>
<meta name="layout" content="main" />
<title>问题#${issue.id}</title>
<asset:stylesheet src="issues/show"/>
<asset:javascript src="issues/show"/>
<asset:script>
$(function() {
    $("#issue").issueShow();
});
</asset:script>
<t:hasRole role="ROLE_ISSUE_ADMIN">
<asset:stylesheet src="issues/admin"/>
<asset:javascript src="issues/admin"/>
<asset:script>
$(function() {
    $("#issue").issueAdmin();
});
</asset:script>
</t:hasRole>
</head>
<body>
	<div class="row">
		<div id="sidebar" class="col-sm-3">
			<g:render template="sidebar" bean="${statis}"/>
		</div>
		<div id="issue" class="col-sm-9" data-id="${issue.id}">
			<div class="title">
				${issue.title}
				<t:issueType type="${issue.type}" closed="${issue.closed || !issue.visible}"/>
			</div>
			<div class="media">
				<div class="pull-left">
					<div class="media-object">
						<span class="glyphicon glyphicon-user"></span>
					</div>
				</div>
				<div class="media-body">
					<div class="media-heading">
						<span>${issue.userName}</span>
					</div>
					<div class="content">
						<div class="markdown"></div>
						<div class="original">${issue.content}</div>
					</div>
					<div class="footer" style="margin-bottom:15px;">
						<span class="date-created">${issue.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</span>
						<div class="toolbar pull-right issue-toolbar" data-id="${issue.id}">
							<t:hasRole role="ROLE_SYSTEM_ADMIN">
							<span class="text-danger hidden seperate admin admin-delete" title="删除问题">删除</span>
							</t:hasRole>
							<t:hasRole role="ROLE_ISSUE_ADMIN">
							<span class="text-danger hidden seperate admin admin-hide" title="隐藏问题">隐藏</span>
							<g:if test="${issue.closed}">
							<span class="text-danger hidden seperate admin admin-open" title="打开问题">打开</span>
							</g:if><g:else>
							<span class="text-danger hidden seperate admin admin-close" title="关闭问题">关闭</span>
							</g:else>
							</t:hasRole>
							<g:if test="${!(issue.closed || !issue.visible) && currentUserId == issue.userId}">
							<span class="glyphicon glyphicon-edit text-primary seperate edit" title="编辑"></span>
							</g:if>
							<span class="glyphicon glyphicon-eye-open visited" title="查看"></span>
							<span class="seperate visited-count">${issue.visitedCount}</span>
							<span class="glyphicon glyphicon-comment ${issue.closed || !issue.visible ? '' : 'text-primary comment'}" title="评论"></span>
							<span class="seperate comment-count">${issue.commentCount}</span>
							<g:if test="${issue.closed || !issue.visible || currentUserId == issue.userId}">
							<span class="glyphicon glyphicon-thumbs-up" title="赞"></span>&nbsp;
							</g:if><g:elseif test="${issue.supported != 0}">
							<span class="glyphicon glyphicon-thumbs-up text-success cancel-support" title="取消赞"></span>
							</g:elseif><g:else>
							<span class="glyphicon glyphicon-thumbs-up text-primary support" title="赞"></span>
							</g:else>
							<span class="support-count">${issue.supportCount}</span>
						</div>
					</div>
					<ul class="media-list comments">
						<g:render template="comment" collection="${issue.comments}" var="comment"></g:render>
					</ul>
					<g:if test="${issue.closed}">
					<div class="alert alert-warning">问题已关闭。</div>
					</g:if><g:elseif test="${!issue.visible }">
					<div class="alert alert-danger">问题已隐藏。</div>
					</g:elseif><g:else>
					<div id="new-comment" class="media">
						<div class="pull-left">
							<div class="media-object">
								<span class="glyphicon glyphicon-user"></span>
							</div>
						</div>
						<div class="media-body"></div>
					</div>
					</g:else>
				</div>
			</div>
		</div>
	</div>
	<script id="editor-template" type="text/x-dot-template">
	{{? it.edit}}
	<div class="media-body">
	{{?}}
	<div class="media-heading">
		<span class="userName">${currentUserName}</span>
		<div class="toolbar btn-group pull-right">
			{{? it.edit}}
			<button class="btn btn-xs btn-default cancel">取消</button>
			{{?}}
			<button class="btn btn-xs btn-default preview">预览</button>
			<button class="btn btn-xs btn-default btn-primary submit">发布</button>
		</div>
	</div>
	<div class="content form">
		<div class="edit-pane">
			<textarea class="form-control" 
				placeholder="评论"
				rows="10" 
				data-validate-name="评论" 
				data-validate-min="10"
				data-validate-max="512"></textarea>
			<div id="errors" style="display: none">
				<ul class="list-unstyled text-danger"></ul>
			</div>
		</div>
		<div class="prev-pane" style="display: none">
			<div class="markdown"></div>
		</div>
	</div>
	{{? it.edit}}
	</div>
	{{?}}
	</script>
</body>
</html>