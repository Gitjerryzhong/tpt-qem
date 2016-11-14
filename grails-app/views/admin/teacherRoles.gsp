<%@ page import="cn.edu.bnuz.tms.system.Role" %>
<html>
<head>
<meta name="layout" content="main" />
<title>教师角色</title>
<asset:stylesheet src="admin/teacher-role"/>
<asset:javascript src="admin/teacher-role"/>
</head>
<body>
	<table class="table">
		<thead>
		<tr>
			<th>角色</th>
			<th>教师</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>院长</td>
			<td class="c1">
				<span class="teacher-role">${teacherRoles[Role.DEAN_OF_COLLEGE] ? teacherRoles[Role.DEAN_OF_COLLEGE].name : '&lt;空&gt;'}</span>
				<select class="teacher-role" data-role="${Role.DEAN_OF_COLLEGE}" data-teacher="${teacherRoles[Role.DEAN_OF_COLLEGE]?.id}"></select>
			</td>
		</tr>
		<tr>
			<td>教学副院长</td>
			<td class="c1">
				<span class="teacher-role">${teacherRoles[Role.DEAN_OF_TEACHING] ? teacherRoles[Role.DEAN_OF_TEACHING].name : '&lt;空&gt;'}</span>
				<select class="teacher-role" data-role="${Role.DEAN_OF_TEACHING}" data-teacher="${teacherRoles[Role.DEAN_OF_TEACHING]?.id}"></select>
			</td>
		</tr>
		<tr>
			<td>学生工作负责人</td>
			<td class="c1">
				<span class="teacher-role">${teacherRoles[Role.STUDENT_AFFAIRS] ? teacherRoles[Role.STUDENT_AFFAIRS].name : '&lt;空&gt;'}</span>
				<select class="teacher-role" data-role="${Role.STUDENT_AFFAIRS}" data-teacher="${teacherRoles[Role.STUDENT_AFFAIRS]?.id}"></select>
			</td>
		</tr>
		<tr>
			<td>考勤管理员</td>
			<td class="c1">
				<span class="teacher-role">${teacherRoles[Role.ROLLCALL_ADMIN] ? teacherRoles[Role.ROLLCALL_ADMIN].name : '&lt;空&gt;'}</span>
				<select  class="teacher-role" data-role="${Role.ROLLCALL_ADMIN}" data-teacher="${teacherRoles[Role.ROLLCALL_ADMIN]?.id}"></select>
			</td>
		</tr>
		<tr>
			<td>教务管理员</td>
			<td class="c1">
				<span class="teacher-role">${teacherRoles[Role.ACADEMIC_SECRETARY] ? teacherRoles[Role.ACADEMIC_SECRETARY].name : '&lt;空&gt;'}</span>
				<select  class="teacher-role" data-role="${Role.ACADEMIC_SECRETARY}" data-teacher="${teacherRoles[Role.ACADEMIC_SECRETARY]?.id}"></select>
			</td>
		</tr>
		<tr>
			<td>2+2出国学生管理员</td>
			<td class="c1">
				<span class="teacher-role">${teacherRoles[Role.TPT_ADMIN] ? teacherRoles[Role.TPT_ADMIN].name : '&lt;空&gt;'}</span>
				<select  class="teacher-role" data-role="${Role.TPT_ADMIN}" data-teacher="${teacherRoles[Role.TPT_ADMIN]?.id}"></select>
			</td>
		</tr>
		<tr>
			<td>学院质量工程项目审核员</td>
			<td class="c1">
				<span class="teacher-role">${teacherRoles[Role.QEM_CHECKER] ? teacherRoles[Role.QEM_CHECKER].name : '&lt;空&gt;'}</span>
				<select  class="teacher-role" data-role="${Role.QEM_CHECKER}" data-teacher="${teacherRoles[Role.QEM_CHECKER]?.id}"></select>
			</td>
		</tr>
		<g:if test="${departmentId == '69'}">
     	<tr>
			<td>合作项目管理员</td>
			<td class="c1">
				<span class="teacher-role">${teacherRoles[Role.TPT_COPROJECT] ? teacherRoles[Role.TPT_COPROJECT].name : '&lt;空&gt;'}</span>
				<select  class="teacher-role" data-role="${Role.TPT_COPROJECT}" data-teacher="${teacherRoles[Role.TPT_COPROJECT]?.id}"></select>
			</td>
		</tr>
		</g:if>		
		</tbody>
	</table>
</body>
</html>