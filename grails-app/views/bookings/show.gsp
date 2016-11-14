<html>
<head>
<meta name="layout" content="main" />
<title>教室借用单  #${form.formId}</title>
<asset:stylesheet src="place/bookings/show"/>
<asset:javascript src="place/bookings/show"/>
<asset:script>
$(function() {
	$("#bookingShow").bookingShow({
		form: ${form as grails.converters.JSON}
	});
});
</asset:script>
</head>
<body>
	<div id="bookingShow">
		<div class="btn-toolbar">
			<div class="pull-right">
				<button class="btn btn-default edit">编辑</button>
				<button class="btn btn-default apply">提交</button>
				<button class="btn btn-default cancel">撤销</button>
				<button class="btn btn-default remove">删除</button>
			</div>
		</div>
		<div>
			<h3><span>教室借用单 #${form.formId}</span></h3>
		</div>
		<div class="row">
			<div class=" col-md-offset-2 col-sm-10 col-sm-offset-1">
				<div class="form-horizontal form"></div>
			</div>
		</div>
	</div>
	<script id="form-template" type="text/x-dot-template">
	<div class="form-group">
		<label class="col-sm-2 control-label">借用人</label>
		<div class="col-sm-4">
			<p class="form-control-static">{{=it.userName}}</p>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">联系电话</label>
		<div class="col-sm-4">
			<p class="form-control-static">{{=it.phoneNumber}}</p>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">借用单位</label>
		<div class="col-sm-10">
			<p class="form-control-static">{{=it.departmentName}}</p>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">借用类别</label>
		<div class="col-sm-10">
			<p class="form-control-static">{{=it.typeName}}</p>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">借用事由</label>
		<div class="col-sm-10">
			<p class="form-control-static">{{=it.reason}}</p>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">借用明细</label>
		<div class="col-sm-10 items">
			<ul class="list-unstyled form-control-static">
				{{~it.items :item}}<li>{{=item.toString()}}</li>{{~}}
			</ul>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">处理状态</label>
		<div class="col-sm-10">
			<p class="form-control-static status"></p>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">操作日志</label>
		<div class="col-sm-10 audits">
			<ul class="form-control-static list-unstyled">
				{{~it.audits :audit}}<li>{{=audit.toString()}}</li>{{~}}
			</ul>
		</div>
	</div>
	</script>
</body>
</html>