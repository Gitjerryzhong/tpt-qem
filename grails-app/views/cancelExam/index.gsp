<html>
<head>
<meta name="layout" content="main" />
<title>取消考试资格</title>
<asset:stylesheet src="cancel-exam/index"/>
<asset:javascript src="cancel-exam/index"/>
<asset:script>
$(function() {
	$("#cancelExam").cancelExamList();
});
</asset:script>
</head>
<body>
    <div id="cancelExam">
		<ul class="nav nav-pills">
			<li class="active"><a href="#" data-status="1">待处理</a></li>
			<li><a href="#" data-status="2">已处理</a></li>
		</ul>
		<table id="list" class="table"></table>
    </div>
</body>
</html>