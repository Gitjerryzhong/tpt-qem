<html>
<head>
<meta name="layout" content="main" />
<title>免听记录</title>
<asset:stylesheet src="free-arr/list"/>
<asset:javascript src="free-arr/list"/>
<asset:script>
$(function() {
	$("#freeArrangementList").freeArrangementList({
		arrangements: ${arrangements},
		students: ${students}
	});
})
</asset:script>
</head>
<body>
	<div id="toolbar" class="form-inline">
		<g:link uri="/freeArrangements/new" class="pull-right">新建</g:link>
	</div>
    <div id="freeArrangementList"></div>
    <script id="table-template" type="text/x-dot-template">
	<table class="table">
		<thead>
			<tr>
				<th class="arrangement">课程</th>
				<th class="students">免听学生</th>
			</tr>
		<thead>
		<tbody>
			{{~it.arrangements :arr}} 
			{{? arr.students}}
			<tr>
				<td>
					{{=arr.courseText()}}<br>
					{{=arr.dayOfWeekText()}} {{=arr.sectionsText()}} {{=arr.oddEvenText()}}
				</td>
				<td>
					{{~arr.students :student}}
					<span class="student">
						<a href="freeArrangements/new?studentId={{=student.id}}">{{=student.id}} {{=student.name}}</a>
					</span>
					{{~}}
				</td>
			</tr>
			{{?}}
			{{~}}
		</tbody>
	</table>
	</script>
</body>
</html>