<html>
<head>
<meta name="layout" content="main" />
<title>课程表</title>
<asset:stylesheet src="schedule/student.css"/>
<asset:javascript src="schedule/student.js"/>
<asset:script>
$(function() {
	$("#studentSchedule").studentSchedule({
	   currentWeek:${term.currentWeek},
	   arrangements:${arrangements},
	   freeArrangements:${freeArrangements}
	});
});
</asset:script>
</head>
<body>
    <div id="studentSchedule">
		<g:render template="schedule"/>
	</div>
</body>
</html>