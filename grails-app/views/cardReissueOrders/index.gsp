<html>
<head>
<meta name="layout" content="main" />
<title>学生证补办订单列表</title>
<asset:stylesheet src="card/reissue/order/index"/>
</head>
<body>
	<div id="cardReissueOrderIndex">
		<div class="btn-toolbar" style="text-align:right">
			<g:link uri="/cardReissueOrders/new" class="btn btn-default">新建订单</g:link>
		</div>
		<div id="content">
			<table class="table">
				<thead>
					<tr>
						<th class="orderId">订单编号</th>
						<th class="request-count">申请数量</th>
						<th class="finished-count">完成数量</th>
						<th class="creator-name">创建人</th>
						<th class="date-created">创建时间</th>
						<th class="modifier-name">修改人</th>
						<th class="date-modified">修改时间</th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${orders}" var="order">
					<tr>
						<td><g:link uri="/cardReissueOrders/${order.orderId}">${order.orderId}</g:link></td>
						<td>${order.requestCount}</td>
						<td>${order.finishedCount}</td>
						<td>${order.creatorName}</td>
						<td>${order.dateCreated.format("yyyy-MM-dd HH:mm")}</td>
						<td>${order.modifierName}</td>
						<td>${order.dateModified.format("yyyy-MM-dd HH:mm")}</td>
					</tr>
					</g:each>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>