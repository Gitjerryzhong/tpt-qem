<html>
<head>
<meta name="layout" content="main" />
<title>课程表</title>
<asset:stylesheet src="schedule/teacher.css"/>
<asset:javascript src="schedule/teacher.js"/>
<asset:script>
$(function() {
	$("#teacherSchedule").teacherSchedule({
	   currentWeek:${term.currentWeek},
	   arrangements:${arrangements},
	   forms:${forms}
	});
});
</asset:script>
</head>
<body>
    <div id="teacherSchedule">
		<g:render template="schedule"/>
	</div>
</body>
</html>