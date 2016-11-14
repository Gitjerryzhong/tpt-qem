<html>
<head>
<meta name="layout" content="main" />
<title>审批假条</title>
<asset:stylesheet src="leave/approval"/>
<asset:javascript src="leave/approval"/>
<asset:script>
$(function() {
	$("#leaveApproval").leaveApprovalList();
});
</asset:script>
</head>
<body>
    <div id="leaveApproval">
		<ul class="nav nav-pills">
			<li class="active"><a href="#" data-status="1">待审核</a></li>
			<li><a href="#" data-status="2">已审核</a></li>
		</ul>
		<table id="list" class="table"></table>
        <form id="form" method="post" style="display: none">
            <input id="requestId" type="hidden" name="requestId" /> 
            <input id="status" type="hidden" name="status" />
        </form>
    </div>
</body>
</html>