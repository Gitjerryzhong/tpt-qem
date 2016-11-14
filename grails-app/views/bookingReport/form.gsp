<html>
<head>
<meta name="layout" content="main"/>
<title>教室借用单报表</title>
<asset:stylesheet src="place/booking-report/form"/>
<asset:javascript src="place/booking-report/form"/>
<asset:script>
$(function() {
    $("#booking-report-form").bookingReportForm({
    	report: ${report ? report as grails.converters.JSON : 'null'}
    });
});
</asset:script>
</head>
<body>
	<div id="booking-report-form">
		<div class="btn-toolbar">
			<div class="btn-group pull-right">
				<button id="add-items" class="btn btn-default">添加申请</button>
				<button id="save-report" class="btn btn-primary">保存报表</button>
			</div>
			<div class="btn-group pull-right">
				<g:link id="report-list" class="btn btn-default" uri="/bookingReport">所有报表</g:link>
			</div>
		</div>
		<div class="report-header form-inline">
			<div class="form-group">
				<label class="control-label">报表编号</label>
				<p class="static-form-control" id="report-id"><g:if test="${report}">${report.reportId}</g:if><g:else>（新记录）</g:else></p>
			</div>
			<g:if test="${report}">
			<div class="form-group">
				<label class="control-label">创建时间</label>
				<p class="static-form-control" id="date-created">${report.dateCreated.format("yyyy-MM-dd HH:mm")}</p>
			</div>
			</g:if>
		</div>
		<div class="report-items">
			<div class="alert alert-info">
				<p>点击“添加申请”按钮选择教室借用申请。</p>
			</div>
		</div>
	</div>
	<div id="dialog" class="modal fade" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">添加申请单</h4>
				</div>
				<div class="modal-body">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary ok">确定</button>
				</div>
    		</div>
		</div>
	</div>
	<script id="items-template" type="text/x-dot-template">
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
				<th class="operation">操作</th>
			</tr>
		</thead>
		<tbody>
			{{~it.items :item}} 
			<tr data-booking-id="{{=item.bookingId}}">
				<td><a href="{{? it.saved}}../{{?}}../bookings/{{=item.bookingId}}" target="_blank">#{{=item.bookingId}}</a></td>
				<td>{{=item.userName}}</td>
				<td class="department">{{=item.department}}</td>
				<td class="reason">{{=item.reason}}</td>
				<td>{{=item.checker}}</td>
				<td>{{=moment(item.dateChecked).format("MM-DD HH:mm")}}</td>
				<td>{{=item.approver}}</td>
				<td>{{=moment(item.dateApproved).format("MM-DD HH:mm")}}</td>
				<td><span class="label label-{{=it.format.statusClass(item.status)}}">{{=it.format.statusText(item.status)}}<span></td>
				<td class="operation"><span class="glyphicon glyphicon-trash removeItem"></span></td>
			</tr>
			{{~}}
		</tbody>
	</table>
	</script>
	<script id="select-template" type="text/x-dot-template">
	<table class="table table-hover table-condensed">
		<thead>
			<tr>
				<th class="select"><input type="checkbox" class="check-all"></th>
				<th class="userName">借用单号</th>
				<th class="userName">借用人</th>
				<th class="departmenet">借用部门</th>
				<th class="dateApproved">审批时间</th>
			</tr>
		</thead>
		<tbody>
			{{~it.items :item}} 
			<tr>
				<td><input type="checkbox" class="check" data-booking-id="{{=item.bookingId}}"></td>
				<td>{{=item.bookingId}}</td>
				<td>{{=item.userName}}</td>
				<td>{{=item.department}}</td>
				<td>{{=moment(item.dateApproved).format("YYYY-MM-DD HH:mm")}}</td>
			</tr>
			{{~}}
		</tbody>
	</table>
	</script>
</body>
</html>