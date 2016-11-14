<!DOCTYPE html>
<html>
<head>
<title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error</g:else></title>
<meta name="layout" content="main">
</head>
<body>
	<g:if env="development">
		<g:renderException exception="${exception}" />
	</g:if>
	<g:else>
		<div class="alert alert-danger">An error has occurred</div>
	</g:else>
</body>
</html>
