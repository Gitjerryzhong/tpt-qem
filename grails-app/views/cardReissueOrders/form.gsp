<html>
<head>
<meta name="layout" content="main" />
<title>学生证补办订单</title>
<asset:stylesheet src="card/reissue/order/form"/>
<asset:javascript src="card/reissue/order/form"/>
<asset:script>
$(function() {
    $("#cardReissueOrderForm").cardReissueOrderForm({
    	order: ${order ? order as grails.converters.JSON : 'null'}
    });
});
</asset:script>
</head>
<body>
	<div id="cardReissueOrderForm">
		<div class="btn-toolbar">
			<div class="btn-group pull-right">
				<g:link id="orderList" class="btn btn-default" uri="/cardReissueOrders">订单列表</g:link>
				<button id="addItems" class="btn btn-default">添加申请</button>
				<button id="saveOrder" class="btn btn-default">保存订单</button>
			</div>
		</div>
		<div class="orderHeader form-inline">
			<div class="form-group">
				<label class="control-label">订单编号</label>
				<p class="static-form-control" id="order-id"><g:if test="${order}">${order.orderId}</g:if><g:else>（新订单）</g:else></p>
			</div>
			<g:if test="${order}">
			<div class="form-group">
				<label class="control-label">创建人</label>
				<p class="static-form-control" id="creator-name">${order.creatorName}</p>
			</div>
			<div class="form-group">
				<label class="control-label">创建时间</label>
				<p class="static-form-control" id="date-created">${order.dateCreated.format("yyyy-MM-dd HH:mm")}</p>
			</div>
			<g:if test="${order.modifier}">
			<div class="form-group">
				<label class="control-label">修改人</label>
				<p class="static-form-control" id="modifier-name">${order.modifierName}</p>
			</div>
			<div class="form-group">
				<label class="control-label">修改时间</label>
				<p class="static-form-control" id="date-modified">${order.dateModified.format("yyyy-MM-dd HH:mm")}</p>
			</div>
			</g:if>
			</g:if>
		</div>
		<div id="items">
			<div class="alert alert-info">
				<p>点击“添加申请”按钮添加学生证补办申请。</p>
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
				<th class="studentId">学号</th>
				<th class="name">姓名</th>
				<th class="sex">性别</th>
				<th class="department">学院</th>
				<th class="major">专业</th>
				<th class="applyDate">申请时间</th>
				<th class="status">状态</th>
				<th class="operation">操作</th>
			</tr>
		</thead>
		<tbody>
			{{~it.items :item}} 
			<tr data-request-id="{{=item.requestId}}">
				<td><a href="../../cardReissueRequests/{{=item.requestId}}" target="_blank">{{=item.studentId}}</a></td>
				<td>{{=item.name}}</td>
				<td>{{=item.sex}}</td>
				<td>{{=item.department}}</td>
				<td>{{=item.major}}</td>
				<td>{{=moment(item.applyDate).format("YYYY-MM-DD HH:mm")}}
				</td>
				<td class="status">{{=['未提交', '申请成功', '正在办理', '不批准', '办理完成'][item.status]}}</td>
				<td class="operation">
					{{? item.status == 1 || item.status == 2}}
					<span class="glyphicon glyphicon-trash removeItem"></span>
                    {{?? true}}
					<span class="glyphicon glyphicon-ban-circle"></span>
					{{?}}
				</td>
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
				<th class="studentId">学号</th>
				<th class="name">姓名</th>
				<th class="department">学院</th>
				<th class="applyDate">申请时间</th>
			</tr>
		</thead>
		<tbody>
			{{~it.requests :request}} 
			<tr>
				<td><input type="checkbox" class="check" data-id="{{=request.id}}"></td>
				<td>{{=request.studentId}}</td>
				<td>{{=request.name}}</td>
				<td>{{=request.department}}</td>
				<td>{{=moment(request.applyDate).format("YYYY-MM-DD HH:mm")}}
				</td>
			</tr>
			{{~}}
		</tbody>
	</table>
	</script>
</body>
</html>