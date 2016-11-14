<html>
<head>
<meta name="layout" content="main" />
<title>编辑请假条</title>
<asset:stylesheet src="leave/form"/>
<asset:javascript src="leave/form"/>
<asset:script>
$(function() {
    $("#leaveRequest").leaveRequest({
        term : ${term},
        arrangements: ${arrangements},
        freeArrangements: ${freeArrangements},
        leaveRequest: ${leaveRequest},
        existedItems: ${existedItems}
    });
});
</asset:script>
</head>
<body>
	<div id="leaveRequest" class="row">
		<div class="col-md-8">
			<div>
				<label class="radio-inline"><input type="radio" name="leaveType" value="1">事假</label>
				<label class="radio-inline"><input type="radio" name="leaveType" value="2">病假</label>
				<label class="radio-inline"><input type="radio" name="leaveType" value="3">公假</label>
			</div>
			<div>
				<g:textArea id="leaveReason" class="form-control" name="leaveReason" value="${reason}"
					rows="6" placeholder="请填写请假事由(10-250个字)" />
			</div>
			<div id="schedule">
				<ul class="nav nav-tabs"></ul>
				<table></table>
			</div>
		</div>
		<div class="col-md-4">
			<div class="btn-toolbar">
				<button id="submit" class="btn btn-sm btn-success" title="提交假条">提交</button>
			</div>
			<div class="control-group">
				<label class="control-label">请假<span id="itemCount"></span>项:
				</label>
			</div>
			<ul id="leaveItems"></ul>
			<div id="errors" class='alert alert-danger' style="display:none">
				<ul></ul>
			</div>
		</div>
	</div>
</body>
</html>