<html>
<head>
<meta name="layout" content="main" />
<title>班级通讯录</title>
</head>
<body>
    <div id="addressList">
    	<table class="table" style="width:auto">
    		<thead>
    		<tr>
	    		<th>姓名</th>
	    		<th>手机</th>
	    		<th>短号</th>
	    		<th>电子邮件</th>
    		</tr>
    		</thead>
    		<tbody>
    		<g:each in="${students}">
    		<tr>
	    		<td><g:if test="${studentId == it.id}"><g:link uri="/profile">${it.name}</g:link>
	    		</g:if><g:else>${it.name}</g:else></td>
	    		<td>${it.longPhone}</td>
	    		<td>${it.shortPhone}</td>
	    		<td>${it.email}</td>
    		</tr>
    		</g:each>
    		</tbody>
    	</table>
    	<div class='alert alert-warning'>通讯信息只对同班同学可见，点击<g:link uri="/profile">这里</g:link>进行修改。</div>
    </div>
</body>
</html>