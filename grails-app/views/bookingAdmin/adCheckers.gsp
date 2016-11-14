<html>
<head>
<meta name="layout" content="main" />
<title>行政借教室权限管理</title>
<asset:stylesheet src="place/booking-admin/ad-checkers"/>
<asset:javascript src="place/booking-admin/ad-checkers"/>
<asset:script>
$(function() {
    $("#ad-checkers").adCheckers({
    	auths : ${auths}
    });
});
</asset:script>
</head>
<body>
	<div id="ad-checkers">
		<table class="table">
			<thead>
				<tr>
					<th>部门</th>
					<th>借用类型</th>
					<th>审核人</th>
					<th><button class="btn btn-xs btn-default add-item"><span class="glyphicon glyphicon-plus"></span></button></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div id="dialog" class="modal fade" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">添加权限</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-md-4">部门</label>
							<div class="col-md-8">
								<g:select class="form-control" from="${departments}" name="department" 
									optionKey="id" optionValue="name"/>
							</div>
							
						</div>
						<div class="form-group">
							<label class="control-label col-md-4">类型</label>
							<div class="col-md-8">
								<g:select class="form-control" from="${bookingTypes}" name="bookingType" 
									optionKey="id" optionValue="name"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4">审核人</label>
							<div class="col-md-8">
								<select class="form-control" name="checker"></select>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary ok">确定</button>
				</div>
			</div>
		</div>
	</div>
	<script id="rows-template" type="text/x-dot-template">
	{{~it.auths :auth}}
	<tr data-department="{{=auth.departmentId}}" data-type="{{=auth.typeId}}" data-checker="{{=auth.checkerId}}">
		<td>{{=auth.departmentName}}</td>
		<td>{{=auth.typeName}}</td>
		<td>{{=auth.checkerName}}</td>
		<td><button class="btn btn-xs btn-default remove-item"><span class="glyphicon glyphicon-remove"></span></button></td>
	</tr>
	{{~}}
	</script>
	<script id="row-template" type="text/x-dot-template">
	<tr data-department="{{=it.departmentId}}" data-type="{{=it.typeId}}" data-checker="{{=it.checkerId}}">
		<td>{{=it.departmentName}}</td>
		<td>{{=it.typeName}}</td>
		<td>{{=it.checkerName}}</td>
		<td><button class="btn btn-xs btn-default remove-item"><span class="glyphicon glyphicon-remove"></span></button></td>
	</tr>
	</script>
	<script id="checkers-template" type="text/x-dot-template">
	{{~it :t}}
	<option value="{{=t.id}}">{{=t.name}}</option>
	{{~}}
	</script>
</body>
</html>