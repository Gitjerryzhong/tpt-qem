<html>
<head>
<meta name="layout" content="main" />
<title>学生证补办订单</title>
<asset:stylesheet src="card/reissue/order/show"/>
<asset:javascript src="card/reissue/order/show"/>
<asset:script>
$(function() {
    $("#cardReissueOrderShow").cardReissueOrderShow({
    	orderId: ${order.orderId}
    });
});
</asset:script>
</head>
<body>
	<div id="cardReissueOrderShow">
		<div class="btn-toolbar">
			<div class="btn-group pull-right">
				<button id="all" class="btn btn-default">订单列表</button>
				<button id="edit" class="btn btn-default">编辑订单</button>
				<button id="delete" class="btn btn-default">删除订单</button>
				<g:link id="receive" class="btn btn-default" uri="/cardReissueOrders/receive/${order.orderId}">接收入库</g:link>
				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">导出 <span class="caret"></span></button>
				<ul class="dropdown-menu">
					<li><g:link id="photos" uri="/cardReissueOrders/photos/${order.orderId}">导出照片</g:link></li>
					<li><g:link id="excel" uri="/cardReissueOrders/export/${order.orderId}?type=order">导出订单</g:link></li>
					<li><g:link id="excel" uri="/cardReissueOrders/export/${order.orderId}?type=distribute">导出分发清单</g:link></li>
				</ul>
			</div>
		</div>
		<div class="orderHeader form-inline">
			<div class="form-group">
				<label class="control-label">订单编号</label>
				<p class="static-form-control" id="order-id">${order.orderId}</p>
			</div>
			<div class="form-group">
				<label class="control-label">创建人</label>
				<p class="static-form-control" id="creator-name">${order.creatorName}</p>
			</div>
			<div class="form-group">
				<label class="control-label">创建时间</label>
				<p class="static-form-control" id="date-created">${order.dateCreated.format("yyyy-MM-dd HH:mm")}</p>
			</div>
			<g:if test="${order.modifierName}">
			<div class="form-group">
				<label class="control-label">修改人</label>
				<p class="static-form-control" id="modifier-name">${order.modifierName}</p>
			</div>
			<div class="form-group">
				<label class="control-label">修改时间</label>
				<p class="static-form-control" id="date-modified">${order.dateModified?.format("yyyy-MM-dd HH:mm")}</p>
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
					</tr>
					</g:each> 
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>