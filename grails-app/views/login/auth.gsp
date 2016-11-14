<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>教学管理系统</title>
<meta name="keywords" content="北京师范大学 珠海分校 考勤 请假 补办学生证">
<meta name="description" content="欢迎登录北京师范大学珠海分校教学管理系统，希望为师生提供更多工作、学习上的便利。">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
<asset:stylesheet src="auth.css"/>
<!--[if lt IE 9]>
<asset:javascript src="lib/html5shiv.js"/>
<asset:javascript src="lib/respond.min.js"/>
<![endif]-->
</head>
<body>
	<div class="container">
		<form class="form-signin" action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
			<h2 class="form-signin-heading">登录TMS</h2>
			<div class="input-group">
				<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
				<input type='text' class='form-control' name='j_username' id='username' placeholder="用户名" />
			</div>
			<div class="input-group">
				<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
				<input type='password' class='form-control' name='j_password' id='password' placeholder="密码" />
			</div>
			<div class="checkbox">
				<label class="checkbox"> <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
					<g:if test='${hasCookie}'>checked='checked'</g:if> /> <g:message code="springSecurity.login.remember.me.label" />
				</label>
			</div>
	        <button class="btn btn-lg btn-primary btn-block" type="submit">${message(code: "springSecurity.login.button")}</button>
	        <g:if test='${flash.message}'>
				<div class='alert alert-danger'>
					${flash.message}
				</div>
			</g:if>
		</form>
		<!--[if lt IE 8 ]>
			<div class="alter alert-warning not-support">
			<p>不支持此版本的浏览器，请升级至<a href="http://www.microsoft.com/zh-cn/download/internet-explorer-8-details.aspx">IE8</a>或者使用<a 
			href="http://firefox.com.cn/">Firefox</a>/<a href="http://www.google.cn/intl/zh-CN/chrome/">Chrome</a>。</p>
			<p>或者下载<a href="/download/FirefoxPortable.exe">Firefox绿色版</a>，可放在U盘中使用。</p>
			<style>
				form { display: none;}
			</style>
			</div>
		<![endif]-->
    </div>	
	<asset:javascript src="auth.js"/>
</body>
</html>
