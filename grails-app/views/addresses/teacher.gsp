<html>
<head>
<meta name="layout" content="main" />
<title>学院通讯录</title>
</head>
<body>
    <div id="addressList">
    	<table class="table">
    		<thead>
    		<tr>
	    		<th>姓名</th>
	    		<th>移动电话</th>
	    		<th>手机短号</th>
	    		<th>办公电话</th>
	    		<th>办公地址</th>
	    		<th>家庭电话</th>
	    		<th>家庭地址</th>
	    		<th>电子邮件</th>
	    		<th>QQ号</th>
    		</tr>
    		</thead>
    		<tbody>
    		<g:each in="${teachers}">
    		<tr>
	    		<td><g:if test="${teacherId == it.id}"><g:link uri="/profile">${it.name}</g:link>
	    		</g:if><g:else>${it.name}</g:else></td>
	    		<td>${it.longPhone}</td>
	    		<td>${it.shortPhone}</td>
	    		<td>${it.officePhone}</td>
	    		<td>${it.officeAddress}</td>
	    		<td>${it.homePhone}</td>
	    		<td>${it.homeAddress}</td>
	    		<td>${it.email}</td>
	    		<td>${it.qqNumber}</td>
    		</tr>
    		</g:each>
    		</tbody>
    	</table>
    	<div class='alert alert-warning'>部门通讯信息只对本部门教师可见，点击<g:link uri="/profile">这里</g:link>进行修改。</div>
    </div>
</body>
</html>