<html>
<head>
<meta name="layout" content="main" />
<title>我的考勤情况</title>
<asset:stylesheet src="rollcall-statis/student"/>
<asset:javascript src="rollcall-statis/student"/>
<asset:script>
$(function(){
	$("#rollcallStatis").rollcallStatisStudent({
		rollcallItems: ${rollcallItems}, 
		leaveItems:    ${leaveItems},
		courses:       ${courses}
	});
});
</asset:script>
</head>
<body>
    <div id="rollcallStatis"></div>
    <script id="statis-template" type="text/x-dot-template">
	<h4>考勤</h4>
	<ul class='list-unstyled rollcall-items'>
	{{? it.rollcallItems.length}}
		{{~ it.rollcallItems :ri}}
		<li>
			<label class='label {{= ri.typeClass()}}'>{{= ri.typeText()}}</label>
			<span {{? ri.type < 0}}style="text-decoration:line-through;"{{?}}>
				{{=ri.weekText()}}&nbsp;
				{{=ri.arrangement.dayOfWeekText()}}&nbsp;
				{{=ri.arrangement.sectionsText()}}&nbsp;
				{{=ri.teacher}}&nbsp;
				{{=ri.arrangement.courseText()}}
			</span>
		</li>
		{{~}}
	{{?? true}}
		<li>暂无考勤数据</li>
	{{?}}
	</ul>
	<h4>请假</h4>
	<ul class='list-unstyled leave-items'>
	{{? it.leaveItems.length }}
		{{~ it.leaveItems :li}}
		<li>
			<label class='label {{= li.typeClass()}}'>{{= li.typeText()}}</label>
			<span>
				{{=li.weekText()}}&nbsp;
				{{=li.dayOfWeekText()}}&nbsp;
				{{=li.sectionsText()}}&nbsp;
				{{=li.courseText()}}&nbsp;			
			</span>
		</li>
		{{~}}
	{{?? true}}
		<li>暂无请假数据</li>
	{{?}}
	</ul>
	<h4>课程</h4>
	<ul class='list-unstyled course-items'>
	{{? it.courses.length}}
		{{~ it.courses :ci}}
		<li>
			<label class='label {{=(ci.statis / ci.hours >= 1 / 9) ? "label-danger": "label-default"}}'>
				{{= ci.statis}}/{{= ci.hours}}
			</label>
			<span>{{= ci.course}}</span>
		</li>
		{{~}}
	{{?? true}}
		<li>暂无课程数据</li>
	{{?}}
	</ul>
	</script>
</body>
</html>