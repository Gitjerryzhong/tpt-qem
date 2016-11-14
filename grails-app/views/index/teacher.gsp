<html>
<head>
<meta name="layout" content="main" />
<title>首页</title>
<asset:stylesheet src="home/index"/>
</head>
<body>
	<div class="hello">
		<h1>欢迎使用TMS</h1>
		<p>系统正在建设中，希望为老师们提供更多工作上的便利</p>
	</div>
	<g:each in="${modules}" var="module" status="i">
	<div class="row module">
		<div class="col-md-2 module-name">
			<div>
				<g:message code="module.${module.key}"/>
			</div>
		</div>
		<div class="col-md-10">
			<div class="row tiles">
				<g:each in="${module.value}" var="menu">
				<div class="col-md-3 col-sm-3 tile">
					<a href="${createLink(uri: menu.url)}">
						<span><g:message code="menu.${menu.label}"/></span>
					</a>
				</div>
				</g:each>
				<g:each in="${0 ..< (4 - module.value.size() % 4) % 4}">
				<div class="col-md-3 col-sm-3 hidden-xs empty"><span></span></div>
				</g:each>
			</div>
		</div>
	</div>
	</g:each>
</body>
</html>