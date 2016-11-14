<html>
<head>
<meta name="layout" content="main" />
<title>教室借用审批</title>
<asset:stylesheet src="place/booking-approve/index"/>
<asset:javascript src="place/booking-approve/index"/>
<asset:script>
$(function() {
    $("#booking-approve").bookingApprove();
});
</asset:script>
</head>
<body>
	<div id="booking-approve" class="row">
		<div class="col-sm-3 sidebar">
			<div class="list-group">
				<a class="list-group-item statis" href="#" data-status="-1"><span class="badge"></span>待审核</a>
				<a class="list-group-item statis" href="#" data-status="0"><span class="badge"></span>待审批</a>
				<a class="list-group-item statis" href="#" data-status="1"><span class="badge"></span>已审批</a>
			</div>
			<div class="input-group search">
				<input id="query" type="text" class="form-control" placeholder="#单号 / 借用人">
				<span class="input-group-btn">
					<button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search"></span></button>
				</span>
			</div>
		</div>
		<div class="col-sm-9 content"></div>
	</div>
	<script id="list-template" type="text/x-dot-template">
	{{?it.forms.length}}
	<table class="table table-hover form-list">
		<thead>
			<tr>
				<th>申请单号</th>
				<th>申请单位</th>
				<th>借用类别</th>
				<th>借用人</th>
				<th>事由</th>
				<th>{{?it.status==-1}}申请{{??it.status==0}}审核{{??}}审批{{?}}时间</th>
				{{?it.status == 1 || it.status == 2}}
				<th>状态</th>
				<th>报表</th>
				{{?}}
			</tr>
		<thead>
		<tbody>
			{{~it.forms :form}}
			<tr data-id="{{=form.formId}}">
				<td>{{=form.formId}}</td>
				<td>{{=form.department}}</td>
				<td>{{=form.type}}</td>
				<td>{{=form.userName}} </td>
				<td class="reason">{{=form.reason}}</td>
				<td>{{=it.moment(form.date).fromNow()}} </td>
				{{?it.status == 1 || it.status == 2}}
				<td><span class="{{=it.format.checkStatusClass(form.status)}}">{{=it.format.checkStatusText(form.status)}}</span></td>
				<td>{{=form.reportId?form.reportId:""}}</td>
				{{?}}
			</tr>
			{{~}}
		</tbody>
	</table>
	<ul class="pager">
		<li class="prev-page"><a href="#">上一页</a></li>
		<li class="next-page"><a href="#">下一页</a></li>
	</ul>
	{{?}}
	</script>
	<script id="form-template" type="text/x-dot-template">
	<div class="btn-toolbar">
		<div class="btn-toolbar pull-right">
			<div class="btn-group">
				<button class="btn btn-default prev" {{?it.prevId == null}}disabled {{??}}data-id="{{=it.prevId}}"{{?}}>
					<span class="glyphicon glyphicon-chevron-left"></span>
				</button>
				<button class="btn btn-default next" {{?it.nextId == null}}disabled {{??}}data-id="{{=it.nextId}}"{{?}}>
					<span class="glyphicon glyphicon-chevron-right"></span>
				</button>
			</div>
		</div>
		<div class="title">教室申请单 #{{=it.formId}}</div>
	</div>
	<div class="form-horizontal">
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
			<div class="col-sm-10">
				<ul class="form-control-static list-unstyled">
					{{~it.items :item}}<li>{{=item.toApproveString()}}</li>{{~}}
				</ul>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">借用人</label>
			<div class="col-sm-10">
				<p class="form-control-static">{{=it.userInfo.join(" ")}} {{=it.userName}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">联系电话</label>
			<div class="col-sm-10">
				<p class="form-control-static">{{=it.phoneNumber}}</p>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">操作日志</label>
			<div class="col-sm-10">
				<ul class="form-control-static list-unstyled">
					{{~it.audits :audit}}<li>{{=audit.toString()}}</li>{{~}}
				</ul>
			</div>
		</div>
		{{? it.checkable || it.closeable || it.revokable}}
		<div class="form-group">
			<label class="col-sm-2 control-label">审核意见</label>
			<div class="col-sm-10">
				<textarea class="form-control" name="comment" rows="4" 
					data-validate-name="审核意见"
					data-validate-max="250" placeholder="当不批准或关闭时，必须填写审核意见，最长250个字符。">
				{{?it.hasOccupied()}}教室已被占用。{{?}}
				</textarea>
				<div id="errors" style="display:hidden"><ul class="list-unstyled"></ul></div>
			</div>
		</div>
		<div class="btn-toolbar">
			{{? it.checkable}}
			<div class="btn-group pull-right">
				<button class="btn btn-default approve" data-id="{{=it.formId}}" {{?it.hasOccupied()}}disabled{{?}}>批准</button>
				<button class="btn btn-default disapprove" data-id="{{=it.formId}}">不批准</button>
			</div>
			{{?}}
			{{? it.closeable}}
			<div class="btn-group pull-right">
				<button class="btn btn-default close-form" data-id="{{=it.formId}}">关闭</button>
			</div>
			{{?}}
			{{? it.revokable}}
			<div class="btn-group pull-right">
				<button class="btn btn-default revoke-form" data-id="{{=it.formId}}">回收申请</button>
			</div>
			{{?}}
		</div>
		{{?}}
	</div>
	</script>
</body>
</html>