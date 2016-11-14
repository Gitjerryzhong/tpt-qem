<html>
<head>
<meta name="layout" content="main" />
<title>教室借用单报表</title>
<asset:stylesheet src="place/booking-report/show"/>
<asset:javascript src="place/booking-report/show"/>
<asset:script>
$(function() {
    $("#booking-report-show").bookingReportShow({
    	reportId: ${report.reportId}
    });
});
</asset:script>
</head>
<body>
	<div id="booking-report-show">
		<div class="btn-toolbar">
			<div class="btn-group pull-right">
				<button id="edit" class="btn btn-default">编辑报表</button>
				<button id="export" class="btn btn-default">导出报表</button>
			</div>
			<div class="btn-group pull-right">
				<button id="all" class="btn btn-default">所有报表</button>
			</div>
		</div>
		<div class="report-header form-inline">
			<div class="form-group">
				<label class="control-label">报表编号</label>
				<p class="static-form-control" id="report-id">${report.reportId}</p>
			</div>
			<div class="form-group">
				<label class="control-label">创建人</label>
				<p class="static-form-control" id="creator-name">${report.creator}</p>
			</div>
			<div class="form-group">
				<label class="control-label">创建时间</label>
				<p class="static-form-control" id="date-created">${report.dateCreated.format("yyyy-MM-dd HH:mm")}</p>
			</div>
			<g:if test="${report.modifier}">
			<div class="form-group">
				<label class="control-label">修改人</label>
				<p class="static-form-control" id="modifier-name">${report.modifier}</p>
			</div>
			<div class="form-group">
				<label class="control-label">修改时间</label>
				<p class="static-form-control" id="date-modified">${report.dateModified?.format("yyyy-MM-dd HH:mm")}</p>
			</div>
			</g:if>
		</div>
		<div class="report-items">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th class="bookingId">借用单号</th>
						<th class="userName">借用人</th>
						<th class="departmenet">借用部门</th>
						<th class="reason">借用事由</th>
						<th class="checker">审核人</th>
						<th class="dateChecked">审核时间</th>
						<th class="approver">审批人</th>
						<th class="dateApproved">审批时间</th>
						<th class="status">状态</th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${report.items}" var="item">
					<tr>
						<td><g:link uri="/bookings/${item.bookingId}" target="_blank">#${item.bookingId}</g:link></td>
						<td>${item.userName}</td>
						<td class="department">${item.department}</td>
						<td class="reason">${item.reason}</td>
						<td>${item.checker}</td>
						<td>${item.dateChecked.format("MM-dd HH:mm")}</td>
						<td>${item.approver}</td>
						<td>${item.dateApproved.format("MM-dd HH:mm")}</td>
						<td class="status" data-status="${item.status}"></td>
					</tr>
					</g:each> 
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>