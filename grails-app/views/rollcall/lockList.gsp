<html>
<head>
<meta name="layout" content="main" />
<title>考勤管理</title>
<asset:stylesheet src="rollcall/admin-lock"/>
<asset:javascript src="rollcall/admin-lock"/>
<asset:script>
$(function() {
    $("#rollcallAdmin").rollcallAdmin({
    	week: ${week}
    });
})
</asset:script>
</head>
<body>
	<div id="rollcallAdmin">
		<g:render template="/ui/weekTab"/>
		<table class="table">
			<thead>
			<tr>
				<th class="c0">
					<span class="outer">
						<span id="lockAll" class="inner"></span><span id="unlockAll" class="inner"></span>
					</span>
				</th>
				<th>教师</th>
				<th>课程名称</th>
				<th>上课时间</th>
				<th>旷课</th>
				<th>迟到</th>
				<th>早退</th>
				<th>提交时间</th>
				<th>修改时间</th>
			</tr>
			<thead>
			<tbody>
			<g:each in="${forms}" var="form">
				<tr>
					<td class="${form.status?'lock':'unlock'}" data-id="${form.id}"><span class="outer"><span class="inner"></span></span></td>
					<td>${form.teacher}</td>
					<td>${form.courseClasses}</td>
					<td>周${" 一二三四五六日"[form.dayOfWeek]} ${form.startSection}-${form.startSection + form.totalSection - 1} 节</td>
					<td>${form.absentCount}</td>
					<td>${form.lateCount}</td>
					<td>${form.earlyCount}</td>
					<td>${form.dateCreated}</td>
					<td>${form.dateModified}</td>
					<td></td>
				</tr>
			</g:each>
			</tbody>
		</table>
	</div>
</body>
</html>