<!DOCTYPE html>
<html>
<head>
<title><g:if env="development">访问的页面不存在</g:if> <g:else>错误</g:else></title>
<meta name="layout" content="main">
<g:if env="development">
    <link rel="stylesheet" href="${assetPath(src: 'errors.css')}" type="text/css">
</g:if>
</head>
<body>
	<div class="alert alert-danger">访问的页面不存在。 </div>
</body>
</html>
