<html>
<head>
<meta name="layout" content="main" />
<title>借教室权限管理</title>
<asset:stylesheet src="place/booking-admin/td-checkers"/>
<asset:javascript src="place/booking-admin/td-checkers"/>
<asset:script>
$(function() {
    $("#td-checkers").tdCheckers({
    	auths : ${auths}
    });
});
</asset:script>
</head>
<body>
	<table id="td-checkers" class="table">
		<thead>
			<tr>
				<th>教学单位</th>
				<g:each in="${bookingTypes}" var="bookingType">
				<th>${bookingType.name}</th>
				</g:each>
			</tr>
		</thead>
		<tbody>
			<g:each in="${departments}" var="department">
			<tr data-department-id="${department.id}">
				<td>${department.name}</td>
				<g:each in="${bookingTypes}" var="bookingType" status="i">
				<td class="booking-type" data-type-id="${bookingType.id}"><span></span><select></select></td>
				</g:each>
			</tr>
			</g:each>
		</tbody>
	</table>
</body>
</html>