<html>
<head>
<meta name="layout" content="main" />
<title>${form ? "编辑教室申请单#$form.formId": "新建教室申请单"}</title>
<asset:stylesheet src="place/bookings/form"/>
<asset:javascript src="place/bookings/form"/>
<asset:script>
$(function() {
    $("#bookingForm").bookingForm({
    	form : ${form ? form as grails.converters.JSON : 'null'},
    	term : ${term as  grails.converters.JSON},
    	conflicts: ${conflicts as grails.converters.JSON},
    	days : ${days},
    	today: "${new Date().format("yyyy-MM-dd")}"
    });
});
</asset:script>
</head>
<body>
	<div id="bookingForm">
		<div id="notice" style="display:none">
			<div class="btn-toolbar">
				<div class="btn-group pull-right">
					<button id="continue" class="btn btn-primary">继续</button>
				</div>
			</div>
			<div class="well col-md-8 col-md-offset-2 notice">
				<h3>教室借用须知</h3>
				<ol>
					<li>申请活动必须提前一天以上申请。</li>
					<li>申请部门审核同意后由教务处审批。教务处审批时间：周一至周五上午10:30-11:00，下午16:00-16:30。如申请错过此时间段，将顺延至下一次审批时间段。教务处审批后将会把审批结果报各个教学楼的物业管理员。
					</li>
					<li>如有变更或取消，申请人必须提前到教学楼物业办公室签名确认。因没及时通知物业造成教室设备被盗等问题,将由申请单位负责。教学楼物业办公室如下:
						<ol>
							<li>励耘楼A113</li>
							<li>丽泽楼C208</li>
							<li>综合楼A111</li>
							<li>乐育楼C座1楼</li>
							<li>工程楼2号楼1楼</li>
						</ol>
					<li>以下情况将退回申请:  
						<ol>
							<li>借用300座以上教室在借用理由里未注明使用人数，或使用人数未达到教室座位数的一半或以上。</li>
							<li>自主发展课堂未注明项目名称。</li>
							<li>讲座未注明讲座主题和主讲人。</li>
						</ol>
					<li>出现教室借用冲突情况请听从教学楼物业管理员安排。</li>
				</ol>
			</div>
		</div>
		<div id="form" style="display:none">
			<div class="btn-toolbar">
				<div class="btn-group pull-right">
					<button id="addItems" class="btn btn-default">选择教室</button>
					<button id="saveForm" class="btn btn-default btn-primary">${form ? "保存" : "提交"}</button>
				</div>
			</div>
			<h3><span>教室借用单<g:if test="${form}">#${form.formId}</g:if></span></h3>
			<div class="row">
				<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">借用人</label>
							<div class="col-sm-4">
								<p class="form-control-static">${form ? form.userName : userName}</p>
							</div>
							<label class="col-sm-2 control-label">联系电话</label>
							<div class="col-sm-4">
								<p class="form-control-static">${form ? form.phoneNumber: phoneNumber}</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">借用单位</label>
							<div class="col-sm-4">
								<g:select class="form-control" name="department"
									from="${departments}" optionKey="id" optionValue="name" 
									value="${form ? form.departmentId : departmentId}"/>
							</div>
							<label class="col-sm-2 control-label">借用类别</label>
							<div class="col-sm-4">
								<g:select class="form-control" name="bookingType" from="${bookingTypes}"
									optionKey="id" optionValue="name"
									value="${form?.typeId}"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">借用事由</label>
							<div class="col-sm-10">
								<textarea class="form-control" name="reason" rows="4">${form?.reason}</textarea>
								<p class="help-block">请注明借用单位下属部门（班级或社团名称）、使用人数和用途。(10-100个字符)</p>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">借用明细</label>
							<div class="col-sm-10 form-detail"></div>
						</div>
						<g:if test="${form}">
						<div class="form-group">
							<label class="col-sm-2 control-label">操作日志</label>
							<div class="col-sm-10 form-audits"></div>
						</div>
						</g:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="dialog" class="modal fade" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">选择教室</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-4 form">
							<g:if test="${days == 0 || days == -1}">
							<div class="form-group">
								<label class="control-label">开始周</label>
								<select class="form-control" name="startWeek"></select>
							</div>
							<div class="form-group">
								<label class="control-label">结束周</label>
								<select class="form-control" name="endWeek"></select>
							</div>
							<div class="form-group">
								<label class="control-label">星期几</label>
								<select class="form-control" name="dayOfWeek"></select>
							</div>
							</g:if><g:else>
							<div class="form-group">
								<label class="control-label">日期</label><span class="date"></span>
								<select class="form-control" name="days"></select>
							</div>
							</g:else>
							<div class="form-group">
								<label class="control-label">时间段</label>
								<select class="form-control" name="sectionType"></select>
							</div>
							<div class="form-group">
								<label class="control-label">教室类型</label>
								<g:select class="form-control" from="${roomTypes}" name="roomType" 
									optionKey="TYPE" optionValue="TYPE" value="多媒体教室"/>
							</div>
							<div class="form-group">
								<button class="btn btn-default pull-right search" style="margin-top:25px;width:100%" data-loading-text="正在查询...">查询</button>
							</div>
						</div>
						<div class="col-xs-8">
							<div class="form-group">
								<div>
									<label class="control-label">可选教室</label>
									<button class="btn btn-default btn-xs pull-right select" disabled>选择教室</button>
								</div>
								<select multiple class="form-control rooms-selectable"></select>
							</div>
							<div class="form-group">
								<div>
									<label class="control-label">已选教室</label>
									<button class="btn btn-default btn-xs pull-right deselect" disabled>取消选择</button>
								</div>
								<select multiple class="form-control rooms-selected"></select>
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
	<script id="types-template" type="text/x-dot-template">
	{{~it :type}}
	<option value="{{=type.id}}">{{=type.name}}</option>
	{{~}}
	</script>
	<script id="items-template" type="text/x-dot-template">
	{{?it.items.length > 0}}
	<ul class="list-unstyled form-control-static">
		{{~it.items :item:index}} 
		<li data-index="{{=index}}">
			{{=item.toString()}} 
			<span class="glyphicon glyphicon-remove text-danger removeItem" style="display:none"></span>
		</li>
		{{~}}
	</ul>
	{{??}}
	<p class="form-control-static text-warning">一张借用单可借用不同时段的多个教室，请点击“选择教室”按钮添加教室。</p>
	{{?}}
	</script>
	<script id="audits-template" type="text/x-dot-template">
	<ul class="list-unstyled form-control-static">
		{{~it.audits :audit:index}}
		<li>{{=audit.toString()}}</li>
		{{~}}
	</ul>
	</script>
</body>
</html>