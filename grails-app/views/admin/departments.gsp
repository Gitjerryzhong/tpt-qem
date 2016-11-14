<html>
<head>
<meta name="layout" content="main" />
<title>部门设置</title>
<asset:stylesheet src="admin/departments"/>
<asset:javascript src="admin/departments"/>
</head>
<body>
	<table id="departments" class="table">
		<thead>
		<tr>
			<th>部门名称</th>
			<th>管理员</th>
		</tr>
		</thead>
		<tbody>
		<g:each in="${departments}" var="department"> 
		<tr>
			<td>${department.name}</td>
			<td class="c1"><span class="dept-admin">${department.adminName?:"&lt;空&gt;"}</span><select class="dept-admin" data-dept="${department.id}"  data-admin="${department.adminId?:''}"></select></td>
		</tr>
		</g:each>
		</tbody>
	</table>
</body>
</html>