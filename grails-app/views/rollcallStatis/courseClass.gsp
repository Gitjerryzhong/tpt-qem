<html>
<head>
<meta name="layout" content="main" />
<title>班级考勤情况</title>
<asset:javascript src="rollcall-statis/course-class"/>
<asset:stylesheet src="rollcall-statis/course-class"/>
<asset:script>
$(function() {
	$("#rollcallStatis").rollcallStatisCourseClass({
		term: ${term},
		terms: ${terms},
		courseClasses: ${courseClasses}
	});
});
</asset:script>
</head>
<body>
    <div id="rollcallStatis" >
    	<div class="toolbar row">
    		<a id="export" class="export btn btn-default btn-sm pull-right" href="#">导出Excel</a>
    		<div class="col-md-10">
				<select id="terms" class="inline-form-control input-sm"></select>
				<select id="courseClasses" class="inline-form-control input-sm"></select>
			</div>
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
    		<tbody id="rows"></tbody>
    	</table>
    </div>
    <script id="terms-option-template" type="text/x-dot-template">
	{{~it :t}}
		<option value="{{=t}}">{{=parseInt(t/10)}}-{{=parseInt(t/10)+1}}-{{=t%10}}</option>
	{{~}}
	</script>
	<script id="course-classes-option-template" type="text/x-dot-template">
	{{~it :t}}
		<option value="{{=t.id}}">{{=t.name}}</option>
	{{~}}
	</script>
	<script id="table-template" type="text/x-dot-template">
	{{~it :v}}
		<tr><td class="id">{{=v.id}}</td><td>{{=v.name}}</td><td>{{=v.absent}}</td><td>{{=v.late}}</td><td>{{=v.early}}</td><td>{{=v.leave}}</td><td>{{=v.total}}</td><td>{{=v.adminClass}}</td></tr>
	{{~}}
	</script>
</body>
</html>