<html>
<head>
<meta name="layout" content="main" />
<title>教室借用单列表</title>
<asset:stylesheet src="place/bookings/index"/>
<asset:javascript src="place/bookings/index"/>
<asset:script>
$(function() {
    $("#booking-list").bookingList({
    	today: "${new Date().format("yyyy-MM-dd")}"
    });
    $("#pager").pager(${pager});
});
</asset:script>
</head>
<body>
   	<g:if test="${!forms}">
   	<p>暂无申请数据。</p>
   	</g:if><g:else>
	<div class="btn-toolbar">
		<div class="btn-group pull-right">
			<button id="export" class="btn btn-default">导出</button>
		</div>
	</div>
	<table id="booking-list" class="table">
	    <thead>
	        <tr>
	            <th>查看</th>
	            <th>申请单位</th>
	            <th>类型</th>
	            <th>事由</th>
	            <th>提交时间</th>
	            <th>状态</th>
	        </tr>
	    </thead>
	    <tbody>
			<g:each var="form" in="${forms}">
		    <tr>
		        <td><g:link mapping="item" id="${form.formId}">查看</g:link></td>
		        <td>${form.department}</td>
		        <td>${form.type}</td>
		        <td><div class="reason">${form.reason}</div></td>
		        <td>${form.dateModified.format("yyyy-MM-dd HH:mm")}</td>
		        <td class="status" data-status="${form.status}"></td>
		    </tr>
			</g:each>
	</table>
	<ul id="pager" class="pager"></ul>
    </g:else>
    <div id="dialog" class="modal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3 id="myModalLabel">选择提交起止时间</h3>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-4" for="state-date">开始时间</label>
							<div class="col-sm-4">
								<input class="form-control" type="text" id="start-date">
							</div>
							<div class="col-sm-4">
								<p class="form-control-static">格式：2014-1-1</p>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-4" for="end-date">结束时间</label>
							<div class="col-sm-4">
								<input class="form-control" type="text" id="end-date">
							</div>
							<div class="col-sm-4">
								<p class="form-control-static">格式：2014-1-1</p>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="btnOk" class="btn btn-primary">确定</button>
				</div>
			</div>
		</div>
	</div>
	<a class="hide" href="#" id="download"></a>
</body>
</html>