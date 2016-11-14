<html>
<head>
<meta name="layout" content="main" />
<title>学院教师考勤情况</title>
<asset:stylesheet src="rollcall-statis/teachers"/>
<asset:javascript src="rollcall-statis/teachers"/>
<asset:script>
$(function() {
    $("#rollcallStatis").rollcallStatis({
    	term:   ${term},
    	statis: ${statis}
    });
})
</asset:script>
</head>
<body>
    <div id="rollcallStatis"></div>
    <script id="thead-template" type="text/x-dot-template">
	<thead>
    	<tr>
	    	<th class='name'>姓名</th>
	    	{{ for(var i = it.startWeek; i <= it.endWeek; i++) { }}
			<th>{{=i}}</th>
			{{ } }}
    	</tr>
    </thead>
	</script>
	<script id="tbody-template" type="text/x-dot-template">
	<tbody>
		{{~it.ids :id}}
    	<tr>
			{{ var v = it.statis[id]; }}
			<td class="name">{{=v.name}}</td>
			{{ for(var i = it.term.startWeek; i <= it.term.endWeek; i++) { }}
				{{ var rcc = v.rcws && v.rcws[i] ? v.rcws[i] : 0; }}
				{{ var wc = v.weekArrs[i]; }}
				{{? i <= it.term.currentWeek && wc}}
					<td data-id="{{=id}}" data-week="{{=i}}">
						<div class="container">
						<span class="outer">
							<span class="inner" style="width:{{=rcc / wc * 100}}%"></span>
						</span>
						<span class="text">{{=rcc}}/{{=wc}}</span>
						</div>
					</td>
				{{??}}
					<td>{{=wc?wc:' '}}</td>
            	{{?}}
			{{ } }}
    	</tr>
		{{~}}
    </tbody>
	</script>
	<script id="detail-template" type="text/x-dot-template">
	<ul class="rollcall-items list-unstyled">
		{{~it :arr}}
		<li>
			<span class="{{? arr.rollcall}} text-success {{??}} text-danger {{?}}">
			{{=arr.dayOfWeekText()}} 
			{{=arr.sectionsText()}}  
			{{=arr.courseText()}}
			</span>
		</li>
		{{~}}
	</ul>
	</script>
</body>
</html>