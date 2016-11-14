<html>
<head>
<meta name="layout" content="main" />
<title>点名</title>
<asset:stylesheet src="rollcall/form"/>
<asset:javascript src="rollcall/form"/>
<asset:script>
$(function() {
	$("#rollcallForm").rollcallForm({
		arrangementId: '${arrangementId}', 
		week         : ${week}, 
		form         : ${form},
		view         : ${view},
		settings     : ${settings},
		arrangements : ${arrangements},
		students     : ${students},
		leaveRequests: ${leaveRequests},
		statis       : ${statis}
	});
});
</asset:script>
<!--[if IE 8]>
<style>
/* for bs 3.2.0, fixed in 3.2.1 */
.switch-view {width:100px;}
</style>
<![endif]-->
</head>
<body>
	<div id="rollcallForm">
		<ul class="nav nav-tabs week-tabs"></ul>
		<div id="toolbar">
			<div class="buttons navbar-right">
				<button class="btn btn-sm btn-success perfect" title="提交全勤">全勤</button>
				<div class="btn-group btn-group-sm switch-view" data-toggle="buttons">
					<label class="btn btn-default detail-view" title="详细视图"><input type="radio" name="views"><span class="glyphicon glyphicon-list-alt"></span></label>
					<label class="btn btn-default list-view"   title="列表视图"><input type="radio" name="views"><span class="glyphicon glyphicon-list"></span></label>
					<label class="btn btn-default tile-view"   title="平铺视图"><input type="radio" name="views"><span class="glyphicon glyphicon-th"></span></label>
				</div>
				<button class="btn btn-sm btn-default setup" title="点名设置" data-toggle="modal" href="#settingDialog"><span class="glyphicon glyphicon-cog"></span></button>
			</div>
			<div class="dropdown arrangementsDropdown" style="display:inline-block">
				<a data-toggle="dropdown" href="#"><span class="arrangement"></span> <b class="caret"></b></a>
				<ul class="dropdown-menu"></ul>
			</div>
			<div id="summary" style="display:inline-block"></div>
		</div>
		
		<div class="student-list">
			<div id="detail-view"></div>
			<div id="list-view" class="row" tabindex="-1">
				<div class="tips-pane col-md-3">
					<span class="label label-info">操作提示</span>
					<ul class="list-unstyled">
						<li>回车键：旷课/取消/查看假条</li>
						<li>上下箭头：选择</li>
						<li>数字1：旷课</li>
						<li>数字2：迟到</li>
						<li>数字3：早退</li>
						<li>数字4：调课</li>
					</ul>
				</div>
				<div class="list-pane col-md-6" tabindex="-1"></div>
				<div class="detail-pane col-md-3"></div>
			</div>
			<div id="tile-view" class="row"></div>
			<div id="lock-view"></div>
		</div>
		<div id="settingDialog" class="modal fade" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4>点名设置</h4>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
						<div class="row">
							<div class="col-md-6">
								<label class="checkbox"><input type="checkbox" name="hideFree">不显示免听学生</label>
								<label class="checkbox"><input type="checkbox" name="hideLeave">不显示请假学生</label>
								<label class="checkbox"><input type="checkbox" name="hideCancel">不显示取消考试资格学生</label>
							</div>
							<div class="col-md-6">
								<label class="checkbox"><input type="checkbox" name="random">随机点名</label>
								<div class="form-inline">
									<label class="checkbox">点名百分比：</label>
									<select class="ratio span1" disabled>
										<g:each in="${9..1}"><option value="${it*10}">${it*10}</option></g:each>
									</select>
								</div>
							</div>
						</div>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn" data-dismiss="modal">取消</button>
						<button class="btn btn-primary" id="settingConfirm">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script id="detail-view-template" type="text/x-dot-template">
	<table>
		<thead>
			<tr>
				<th class="rowbar">操作</th>
				<th class="student-no">序号</th>
				<th class="student-id">学号</th>
				<th class="student-name">姓名</th>
				<th class="student-statis">旷/迟/退/假</th>
				<th class="student-adminClass">班级</th>
				<th class="student-major">专业</th>
			</tr>
		<thead>
		<tbody>
			{{~it.students :student:index}} 
			<tr data-id="{{=student.id}}" {{? !student.visible}}class="hide"{{?}}>
				<td class="rowbar">
				{{?  student.isFreeListen()}}
					免听
				{{?? student.isCanceledExam()}} 
					取消考试资格
				{{?? student.isLeave()}} 
					<a title="点击查看假条" target="_blank" href="../../leaves/{{=student.leaveRequest.id}}">{{=student.leaveLabel()}}</a>
				{{?? true}} 
					<div class="btn-group">
						<button class="btn btn-xs {{? student.hasState("absent")}}btn-absent{{?}}" data-opkey="absent">旷课</button>
						<button class="btn btn-xs {{? student.hasState("late"  )}}btn-late  {{?}}" data-opkey="late"  >迟到</button>
						<button class="btn btn-xs {{? student.hasState("early" )}}btn-early {{?}}" data-opkey="early" >早退</button>
						<button class="btn btn-xs {{? student.hasState("attend")}}btn-attend{{?}}" data-opkey="attend">调课</button>
					</div>
				{{?}} 
				</td>
				<td class="no">{{=index+1}}</td>
				<td>{{=student.id}}</td>
				<td>{{=student.name}}</td>
				<td class="statis">{{=student.statis.join(" / ")}}</td>
				<td>{{=student.adminClass}}</td>
				<td>{{=student.major}}</td>
			</tr>
			{{~}}
		</tbody>
	</table>
	</script>
	<script id="list-view-template" type="text/x-dot-template">
	<ul>
		{{~it.students :student}}
		<li data-id="{{=student.id}}" {{? !student.visible}}class="hide"{{?}}>
			<span class="name">{{=student.name}}</span>
			<span class="statis">{{=student.statis.join(" ")}}</span>
			{{?  student.isFreeListen()}} 
				<label class="label label-default">免听</label>
			{{?? student.isCanceledExam()}}
				<label class="label label-default">取消考试资格</label>
			{{?? student.isLeave()}} 
				<label class="label label-default">{{=student.leaveLabel()}}</label>
			{{?? true}}
				{{? student.hasState("absent")}}<label class="label label-absent">旷课</label>{{?}}
				{{? student.hasState("late"  )}}<label class="label label-late  ">迟到</label>{{?}}
				{{? student.hasState("early" )}}<label class="label label-early ">早退</label>{{?}}
				{{? student.hasState("attend")}}<label class="label label-attend">调课</label>{{?}}
			{{?}}
		</li>
		{{~}}
	</ul>
	</script>
	<script id="list-view-detail-template" type="text/x-dot-template">
	<ul class="list-unstyled">
		<li>学号：{{=it.id}}</li>
		<li>姓名：{{=it.name}}</li>
		<li>专业：{{=it.major}}</li>
		<li>班级：{{=it.adminClass}}</li>
	</ul>
	</script>
	<script id="tile-view-template" type="text/x-dot-template">
	{{~it.students :student}}
	<div class="tile col-md-2 {{? !student.visible}}hide{{?}}" data-id="{{=student.id}}">
	  <div class="wrapper">
		<div class="title">
			{{ var showToolbar = false; }}
			<div class="status">
			{{?  student.isFreeListen()}} 
				<label class="label label-default">免听</label>
			{{?? student.isCanceledExam()}}
				<label class="label label-default">取消考试资格</label>
			{{?? student.isLeave()}} 
				<label class="label"><a title="点击查看假条" target="_blank" href="../../leaves/{{=student.leaveRequest.id}}">{{=student.leaveLabel()}}</a></label>
			{{?? true}}
				{{ showToolbar = true; }} 
				{{? student.hasState("absent")}}<label class="label label-absent">旷课</label>{{?}}
				{{? student.hasState("late"  )}}<label class="label label-late  ">迟到</label>{{?}}
				{{? student.hasState("early" )}}<label class="label label-early ">早退</label>{{?}}
				{{? student.hasState("attend")}}<label class="label label-attend">调课</label>{{?}}
			{{?}}
			</div>
			{{? showToolbar }}
			<div class="btn-group">
				<button class="btn btn-xs {{? student.hasState("absent")}}btn-absent{{?}}" data-opkey="absent">旷课</button>
				<button class="btn btn-xs {{? student.hasState("late"  )}}btn-late  {{?}}" data-opkey="late"  >迟到</button>
				<button class="btn btn-xs {{? student.hasState("early" )}}btn-early {{?}}" data-opkey="early" >早退</button>
				<button class="btn btn-xs {{? student.hasState("attend")}}btn-attend{{?}}" data-opkey="attend">调课</button>
			</div>
			{{?}}
		</div>
		<div class="content">{{=student.name}}</div>
		<div class="statis">{{=student.statis.join(" / ")}}</div>
	  </div>
	</div>
	{{~}}
	</script>
	<script id="lock-view-template" type="text/x-dot-template">
	<table>
		<thead>
			<tr>
				<th class="student-no">序号</th>
				<th class="student-id">学号</th>
				<th class="student-name">姓名</th>
				<th class="student-rollcall">考勤</th>
				<th class="student-statis">旷/迟/退/假</th>
				<th class="student-adminClass">班级</th>
				<th class="student-major">专业</th>
			</tr>
		<thead>
		<tbody>
			{{~it.students :student:index}} 
			<tr>
				<td class="no">{{=index+1}}</td>
				<td>{{=student.id}}</td>
				<td>{{=student.name}}</td>
				<td>
				{{?  student.isFreeListen()}}
					免听
				{{?? student.isCanceledExam()}} 
					取消考试资格
				{{?? student.isLeave()}} 
					<a title="点击查看假条" target="_blank" href="../../leaves/{{=student.leaveRequest.id}}">{{=student.leaveLabel()}}</a>
				{{?? true}} 
					{{? student.hasState("absent")}}<label class="label label-absent">旷课</label>{{?}}
					{{? student.hasState("late"  )}}<label class="label label-late  ">迟到</label>{{?}}
					{{? student.hasState("early" )}}<label class="label label-early ">早退</label>{{?}}
					{{? student.hasState("attend")}}<label class="label label-attend">调课</label>{{?}}
				{{?}} 
				</td>
				<td class="statis">{{=student.statis.join(" / ")}}</td>
				<td>{{=student.adminClass}}</td>
				<td>{{=student.major}}</td>
			</tr>
			{{~}}
		</tbody>
	</table>
	</script>
	<script id="summary-template" type="text/x-dot-template">
	<span data-toggle="tooltip" title="总人数" class="total">{{=it.total}}</span> - 
	<span data-toggle="tooltip" title="免听人数" class="free">{{=it.free}}</span> - 
	<span data-toggle="tooltip" title="请假人数" class="leave">{{=it.leave}}</span> = 
	<span data-toggle="tooltip" title="应到人数" class="should">{{=it.should}}</span> ( 
	<span data-toggle="tooltip" title="显示人数" class="visible">{{=it.visible}}</span> / 
	<span data-toggle="tooltip" title="旷课人数" class="absent">{{=it.absent}}</span> / 
	<span data-toggle="tooltip" title="迟到人数" class="late">{{=it.late + it.lateEarly}}</span> / 
	<span data-toggle="tooltip" title="早退人数" class="early">{{=it.early + it.lateEarly}}</span> / 
	<span data-toggle="tooltip" title="调课人数" class="attend">{{=it.attend}}</span> )
	</script>
</body>
</html>