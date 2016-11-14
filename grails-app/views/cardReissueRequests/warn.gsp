<html>
<head>
<meta name="layout" content="main" />
<title>学生证补办申请</title>
<style>
#reissueCardWarn {
	width: 496px;
	margin: auto;
}
</style>
</head>
<body>
	<div id="reissueCardWarn">
		<h3>无法补办学生证</h3>
		<div class="alert alert-danger">
			${message}
			<g:if test="${requests}">
				<br>你已经有${requests.size()}次补办学生证申请：
				<g:each in="${requests}" var="req">
					<g:link uri="/cardReissueRequests/${req.id}">查看</g:link>
				</g:each>
				。
			</g:if>
		</div>
	</div>
</body>
</html>