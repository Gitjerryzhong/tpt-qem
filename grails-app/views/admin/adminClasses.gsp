<html>
<head>
<meta name="layout" content="main" />
<title>行政班设置</title>
<asset:stylesheet src="admin/admin-classes"/>
<asset:javascript src="admin/admin-classes"/>
</head>
<body>
	<table id="departments" class="table">
		<thead>
		<tr>
			<th>班级名称</th>
			<th>班主任</th>
			<th>辅导员</th>
		</tr>
		</thead>
		<tbody>
		<g:each in="${adminClasses}" var="adminClass"> 
		<tr>
			<td>${adminClass.name}</td>
			<td class="c1"><span class="admin-teacher">${adminClass.adminTeacherName?:"&lt;空&gt;"}</span><select class="admin-teacher" data-adminClass="${adminClass.name}" data-teacher="${adminClass.adminTeacherId?:''}"></select></td>
			<td class="c2"><span class="grade-teacher">${adminClass.gradeTeacherName?:"&lt;空&gt;"}</span><select class="grade-teacher" data-adminClass="${adminClass.name}" data-teacher="${adminClass.gradeTeacherId?:''}"></select></td>
		</tr>
		</g:each>
		</tbody>
	</table>
</body>
</html>