<html>
<head>
<meta name="layout" content="main" />
<title>学生证补办订单入库</title>
<asset:stylesheet src="card/reissue/order/receive"/>
<asset:javascript src="card/reissue/order/receive"/>
<asset:script>
$(function() {
    $("#cardReissueOrderReceive").cardReissueOrderReceive({
    	orderId: ${order.orderId}
    });
});
</asset:script>
</head>
<body>
	<div id="cardReissueOrderReceive">
		<div class="btn-toolbar" style="text-align:right">
			<g:link id="all" class="btn btn-default" uri="/cardReissueOrders">订单列表</g:link>
		</div>
		<div class="orderHeader form-inline">
			<div class="form-group">
				<label class="control-label">订单编号</label>
				<p class="static_form-control" id="order-id">${order.orderId}</p>
			</div>
			<div class="form-group">
				<label class="control-label">创建人</label>
				<p class="static_form-control" id="creator-name">${order.creatorName}</p>
			</div>
			<div class="form-group">
				<label class="control-label">创建时间</label>
				<p class="static_form-control" id="date-created">${order.dateCreated.format("yyyy-MM-dd HH:mm")}</p>
			</div>
			<g:if test="${order.modifierName}">
			<div class="form-group">
				<label class="control-label">修改人</label>
				<p class="static_form-control" id="modifier-name">${order.modifierName}</p>
			</div>
			<div class="form-group">
				<label class="control-label">修改时间</label>
				<p class="static_form-control" id="date-modified">${order.dateModified?.format("yyyy-MM-dd HH:mm")}</p>
			</div>
			</g:if>
		</div>
		<div id="items">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th class="studentId">学号</th>
						<th class="name">姓名</th>
						<th class="sex">性别</th>
						<th class="department">学院</th>
						<th class="major">专业</th>
						<th class="applyDate">申请时间</th>
						<th class="status">状态</th>
						<th class="operation">
							<span class="glyphicon glyphicon-ok receive-all"></span>
							<span class="glyphicon glyphicon-remove receive-all"></span>
						</th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${order.items}" var="item">
					<tr>
						<td><g:link uri="/cardReissueRequests/${item.requestId}" target="_blank">${item.studentId}</g:link></td>
						<td>${item.name}</td>
						<td>${item.sex}</td>
						<td>${item.department}</td>
						<td>${item.major}</td>
						<td>${item.applyDate.format("yyyy-MM-dd HH:mm")}</td>
						<td class="status">${['未提交', '申请成功', '正在办理', '不批准', '办理完成'][item.status]}</td>
						<td class="operation"><span class="receive glyphicon ${item.status == 2 ? 'glyphicon-ok' : 'glyphicon-remove'}" 
							data-request-id="${item.requestId}"></span></td>
					</tr>
					</g:each> 
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>