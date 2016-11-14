<li class="media comment" data-id="${comment.id}">
	<div class="pull-left">
		<div class="media-object">
			<span class="glyphicon glyphicon-user"></span>
		</div>
	</div>
	<div class="media-body">
		<div class="media-heading">
			<span class="userName">${comment.userName}</span>
		</div>
		<div class="content ">
			<div class="markdown"></div>
			<div class="original">${comment.content}</div>
		</div>
		<div class="footer">
			<span class="date-created">${comment.dateCreated.format("yyyy-MM-dd HH:mm:ss")}</span>
			<div class="toolbar comment-toolbar pull-right">
				<t:hasRole role="ROLE_ISSUE_ADMIN">
				<span class="text-danger hidden seperate admin admin-hide" title="隐藏回复">隐藏</span>
				</t:hasRole>
				<g:if test="${!(issue?.closed || !issue?.visible) && currentUserId == comment.userId}">
				<span class="glyphicon glyphicon-edit text-primary seperate edit" title="编辑"></span>
				</g:if>
				<g:if test="${issue?.closed || !issue?.visible || currentUserId == comment.userId}">
				<span class="glyphicon glyphicon-thumbs-up" title="赞"></span>
				</g:if><g:elseif test="${comment.supported != 0}">
				<span class="glyphicon glyphicon-thumbs-up text-success cancel-support" title="取消赞"></span>
				</g:elseif><g:else>
				<span class="glyphicon glyphicon-thumbs-up text-primary support" title="赞"></span>
				</g:else>
				<span class="support-count">${comment.supportCount}</span>
			</div>
		</div>
	</div>
</li>