<html>
<head>
<meta name="layout" content="main" />
<title>${["学院学生考勤情况","年级学生考勤情况","班级学生考勤情况"][title]}</title>
<asset:stylesheet src="rollcall-statis/students"/>
<asset:javascript src="rollcall-statis/students"/>
<asset:script>
$(function() {
	$("#rollcallStatis").rollcallStatisStudents();
});
</asset:script>
</head>
<body>
    <div id="rollcallStatis" >
    	<div class="toolbar">
    		<a class="export btn btn-default btn-sm pull-right" href="${request.forwardURI}?format=excel">导出Excel</a>
    	</div>
    	<table class="table table-hover">
    		<thead>
    			<tr>
	    			<th class="id">学号</th>
	    			<th class="name">姓名</th>
	    			<th class="absent">旷课</th>
	    			<th class="late">迟到</th>
	    			<th class="early">早退</th>
	    			<th class="leave">请假</th>
	    			<th class="total">折合旷课</th>
	    			<th class="adminClass">班级</th>
    			</tr>
    		</thead>
    		<tbody id="rows"><g:each in="${statis}" var="v">
    			<tr><td class="id">${v.id}</td><td>${v.name}</td><td>${v.absent}</td><td>${v.late}</td><td>${v.early}</td><td>${v.leave}</td><td>${v.total}</td><td>${v.adminClass}</td></tr></g:each>
    		</tbody>
    	</table>
    </div>
    <script id="detail-template" type="text/x-dot-template">
	<td class='detail' colspan='8'>
		<div class='row'>
			<div class='col-md-6'>
				<ul class='list-unstyled rollcall-items'>
					{{~ it.rollcallItems :ri}}
					<li>
						<label class='label {{= ri.typeClass()}}'>{{= ri.typeText()}}</label>&nbsp;
						<span {{? ri.type < 0}}style="text-decoration:line-through;"{{?}}>
							{{=ri.weekText()}}&nbsp;
							{{=ri.arrangement.dayOfWeekText()}}&nbsp;
							{{=ri.arrangement.sectionsText()}}&nbsp;
							{{=ri.teacher}}&nbsp;
							{{=ri.arrangement.courseText()}}
						</span>
					</li>
					{{~}}
				</ul>
				<ul class='list-unstyled leave-items'>
					{{~ it.leaveItems :li}}
					<li>
						<label class='label {{= li.typeClass()}}'>{{= li.typeText()}}</label>&nbsp;
						<span>
							{{=li.weekText()}}&nbsp;
							{{=li.dayOfWeekText()}}&nbsp;
							{{=li.sectionsText()}}&nbsp;
							{{=li.courseText()}}&nbsp;			
						</span>
					</li>
					{{~}}
				</ul>
			</div>
			<div class='col-md-6'>
				<ul class='list-unstyled course-items'>
					{{~ it.courses :ci}}
					<li>
						<label class='label {{=(ci.statis / ci.hours >= 1 / 9) ? "label-danger": "label-default"}}'>{{= ci.statis}}/{{= ci.hours}}</label>&nbsp;
						<span>{{= ci.course}}</span>
					</li>
					{{~}}
				</ul>
			</div>
		</div>
	</td>
	</script>
</body>
</html>