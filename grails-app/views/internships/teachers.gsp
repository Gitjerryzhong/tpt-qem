<html>
<head>
<meta name="layout" content="main" />
<title>实习教师设置</title>
<asset:stylesheet src="internship/teachers"/>
<asset:javascript src="internship/teachers"/>
<asset:script>
$(function() {
    $("#internshipTeacherList").internshipTeacherList({
        internships: ${internships},
        teachers: ${teachers}
    });
});
</asset:script>
</head>
<body>
	<div id="internshipTeacherList">
	</div>
	<div id="dialog" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
    		<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3>添加老师</h3>
				</div>
				<div class="modal-body">
					<div id="teacherList" class="row"></div>
				</div>
				<div class="modal-footer">
					<button class="btn" data-dismiss="modal">取消</button>
					<button id="btnSave" data-dismiss="modal" class="btn btn-primary">确定</button>
				</div>
			</div>
		</div>
	</div>
	<script id="internships-template" type="text/x-dot-template">
	{{~it :internship}}
	<div class="internship" data-id="{{=internship.id}}">
		<div class="grade">
			<h4 class="grade">{{=internship.grade}}级 <button type="button" class="btn btn-xs btn-default btnAdd"><i class="glyphicon glyphicon-plus"></i></button></h4>
		</div>
		<div class="teachers">
			{{~internship.teachers :tt}} 
			<span class="teacher" data-itid="{{=tt.itid}}" data-tid="{{=tt.tid}}">{{=tt.name}} <i class="glyphicon glyphicon-remove" style="visibility:hidden"></i></span>
			{{~}}
		</div>
	</div>
	{{~}}
	</script>
	<script id="teacher-template" type="text/x-dot-template">
	<span class="teacher" data-itid="{{=it.itid}}" data-tid="{{=it.tid}}">{{=it.name}} <i class="glyphicon glyphicon-remove" style="visibility:hidden"></i></span>
	</script>
	<script id="select-template" type="text/x-dot-template">
	{{~it :teacher}}
	<div class="col-md-2"><label class="checkbox-inline"><input type="checkbox" data-id="{{=teacher.id}}">{{=teacher.name}}</label></div>
	{{~}}
	</script>
</body>
</html>