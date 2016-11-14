<html>
<head>
<meta name="layout" content="main" />
<title>最近更新</title>
</head>
<body>
	<ul>
		<g:each in="${items}" var="item">
			<li>${item.date} 
				<g:if test="${item.hasRoles()}">
					<g:link uri="${item.uri}"><g:message code="${item.label}"/></g:link>
				</g:if>
			 	<g:else>
			 		<g:message code="${item.label}"/>
			 	</g:else>
			</li>
		</g:each>
	</ul>
</body>
</html>