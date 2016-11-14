<html>
<head>
<meta name="layout" content="main" />
<title>问题列表</title>
<asset:stylesheet src="issues/index"/>
<asset:javascript src="issues/index"/>
<asset:script>
$(function() {
    $("#issue-list").issueList();
});
</asset:script>
</head>
<body>
	<div class="row">
		<div id="sidebar" class="col-sm-3">
			<g:render template="sidebar" bean="${statis}"/>
		</div>
		<div id="issue-list" class="col-sm-9 ">
			<ul class="list-unstyled issues">
				<g:each in="${issues}" var="issue">
				<li>
					<div class="title">
						<g:link uri="/issues/${issue.id}">${issue.title}</g:link>
						<t:issueType type="${issue.type}" closed="${issue.closed}"/>
					</div>
					<div class="info">
						<span class="seperate date-updated">${issue.dateUpdated}</span>
						<span class="glyphicon glyphicon-eye-open comment" title="查看"></span>
						<span class="seperate">${issue.visitedCount}</span>
						<span class="glyphicon glyphicon-comment comment" title="评论"></span>
						<span class="seperate">${issue.commentCount}</span>
						<span class="glyphicon glyphicon-thumbs-up" title="赞"></span>
						<span>${issue.supportCount}</span>
					</div>
				</li>
				</g:each>
			</ul>
		</div>
	</div>
</body>
</html>