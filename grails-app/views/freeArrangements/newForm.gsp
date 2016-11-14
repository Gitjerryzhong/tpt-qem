<html>
<head>
<meta name="layout" content="main" />
<title>免听记录</title>
<asset:stylesheet src="free-arr/form"/>
<asset:javascript src="free-arr/form"/>
<asset:script>
$(function() {
	$("#freeArrangement").freeArrangement({
		arrangements : ${arrangements},
		studentId: "${studentId}"
	});
})
</asset:script>
</head>
<body>
    <div id="freeArrangement">
    	<div id="search" class="row">
			<div class="col-sm-3">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="输入学号">
					<span class="input-group-btn">
						<button class="btn btn-default">查询</button>
					</span>
				</div>
			</div>
			<div class="col-sm-7">
				<div class="studentInfo" style=""></div>
			</div>
		  	<div class="col-sm-2" style="text-align:right">
		  		<g:link uri="/freeArrangements" >所有记录</g:link>
		  	</div>
    	</div>
    	<div id="editPane">
    		<g:render template="schedule"/>
    	</div>
    	<div id="legend" class="form-inline">
   			<span>图例：</span>
   			<span class="teacher">教师课程(学生未选)</span>
   			<span class="teacher"><label class="checkbox-inline selected"><input type="checkbox">教师课程(学生已选)</label></span>
   			<span class="student-conflict">学生选课(冲突课程)</span>
   			<span class="student">学生选课</span>
   		</div>
    </div>
    <script id="teacher-arrangement-template" type="text/x-dot-template">
	<li><label class="checkbox-inline"><input style="display:none" type="checkbox" data-id="{{=it.id}}">{{=it.courseText()}}{{=it.oddEvenText()}}</label></li>
	</script>
</body>
</html>