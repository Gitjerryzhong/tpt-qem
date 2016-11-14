<html>
<head>
<meta name="layout" content="main" />
<title>我的考勤情况</title>
<asset:stylesheet src="rollcall-statis/teacher"/>
<asset:javascript src="rollcall-statis/teacher"/>
<asset:script>
$(function() {
    $("#rollcallStatis").rollcallTeacher({
    	termIds: ${termIds},
    	termId: ${termId}
    });
});
</asset:script>
</head>
<body>
    <div id="rollcallStatis" class="container">
    	<div class="btn-toolbar">
			<div class="col-md-2 pull-right">
				<select id="terms" class="form-control input-sm"></select>
			</div>
		</div>
    	<table class="table">
    		<thead>
    			<tr>
    				<th class="courseClass">课程名称</th>
    				<th class="time">时间</th>
    				<th class="room">地点</th>
    				<th class="rollcallss">考勤</th>
    				<th class="exportExcel">导出Excel</th>
    			</tr>
    		</thead>
    		<tbody id="rows">
    		</tbody>
    	</table>
    </div>
    <script id="option-template" type="text/x-dot-template">
	{{~it :t}}
		<option value="{{=t}}">{{=parseInt(t/10)}}-{{=parseInt(t/10)+1}}-{{=t%10}}</option>
	{{~}}
	</script>
    <script id="tbody-template" type="text/x-dot-template">
	{{~it.arrangements :arr}}
   	<tr>
		<td>{{=arr.courseText()}}</td>
		<td>{{=[arr.dayOfWeekText(), arr.sectionsText(), arr.oddEvenText()].join(" ")}}</td>
		<td>{{=arr.roomText()}}</td>
		<td>
			<div class='bar' data-arrangement="{{=arr.id}}">
			{{ for(var i = it.term.startWeek; i <= it.term.endWeek; i++) { }}
				<span data-week="{{=i}}" class="{{? !arr.isVisible(i)}}disabled{{?? i > it.term.currentWeek}}not-yet{{?? arr.isRollcalled(i)}}yes{{?? true}}no{{?}}"/>
			{{ } }}
			</div>
		</td>
		<td><a class='btn btn-default btn-xs' href="./personal/export/{{=arr.id}}">导出</a></td>
    </tr>
	{{~}}
	</script>
</body>
</html>