<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><g:layoutTitle default="Grails"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="${assetPath(src: '/favicon.ico')}" type="image/x-icon">
<link rel="apple-touch-icon" href="${assetPath(src: '/apple-touch-icon.png')}">
<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: '/apple-touch-icon-retina.png')}">
<asset:stylesheet src="application.css"/>
<asset:stylesheet src="tpt/tpt.css"/>
<g:layoutHead/>
<!--[if lt IE 9]>
<asset:javascript src="bs-ie8.js"/>
<![endif]-->
</head>
<body>
<div id="header" class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<div class="navbar-header">
			<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
      		</button>
      		<a class="navbar-brand"><span id="logo" style="display:inline-block;width:30px"></span></a>
    	</div>
		<div class="collapse navbar-collapse bs-navbar-collapse">
			<t:ifTeacher>
			<ul class="nav navbar-nav">
				<li><a href="/tms">首页</a></li>
				<li><a href="${createLink(uri: '/tptAdmin/check')}">审核任务</a></li>
				<li><a href="${createLink(uri: '/tptAdmin')}">系统管理</a></li>			
			</ul></t:ifTeacher>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><sec:loggedInUserInfo field="name"/> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${createLink(uri: '/profile')}">修改个人信息</a></li>
						<li class="divider"></li>
						<li><a href="${createLink(uri: '/logout')}">退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
<div class="container">
	<g:layoutBody />
</div>
<div id="footer">
	<div class="container">
	   <span class="pull-right"><a href="http://www.bnuz.edu.cn" target="_blank">北京师范大学珠海分校</a></span>
	   <span><g:link uri="/about">关于我们</g:link> <t:ifTeacher> | <g:link uri="/about/news">最近更新</g:link></t:ifTeacher></span>
	</div>
</div>
<%--<asset:javascript src="logo.js"/>--%>
<asset:deferredScripts/>
</body>
</html>
