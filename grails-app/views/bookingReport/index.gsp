<html>
<head>
<meta name="layout" content="main" />
<title>教室借用报表</title>
<asset:stylesheet src="place/booking-report/index"/>
<asset:javascript src="place/booking-report/index"/>
<asset:script>
$(function() {
    $("#booking-report-index .pager").pager(${pager});
});
</asset:script>
</head>
<body>
	<div id="booking-report-index">
		<div class="btn-toolbar">
	  		<g:link class="btn btn-default pull-right" uri="/bookingReport/new">新建报表</g:link>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th class="c1">报表编号</th>
					<th class="c1">创建人</th>
					<th class="c1">创建时间</th>
					<th class="c1">修改人</th>
					<th class="c1">修改时间</th>
					<th class="c2"></th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${reports}" var="report">
				<tr>
					<td class="c1"><g:link uri="/bookingReport/${report.reportId}">#${report.reportId}</g:link></td>
					<td class="c1">${report.creator}</td>
					<td class="c1">${report.dateCreated?.format("yyyy-MM-dd HH:mm")}</td>
					<td class="c1">${report.modifier}</td>
					<td class="c1">${report.dateModified?.format("yyyy-MM-dd HH:mm")}</td>
					<td class="c2"></td>
				</tr>
				</g:each>
				
			</tbody>
		</table>
		<ul class="pager"></ul>
	</div>
</body>
</html>