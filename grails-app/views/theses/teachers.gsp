<html>
<head>
<meta name="layout" content="main" />
<title>论文教师设置</title>
<asset:stylesheet src="thesis/teachers"/>
<asset:javascript src="thesis/teachers"/>
<asset:script>
$(function() {
    $("#thesisTeacherList").thesisTeacherList({
        theses: ${theses},
        teachers: ${teachers}
    });
});
</asset:script>
</head>
<body>
	<div id="thesisTeacherList">
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
	<script id="theses-template" type="text/x-dot-template">
	{{~it :thesis}}
	<div class="thesis" data-id="{{=thesis.id}}">
		<div class="grade">
			<h4>{{=thesis.grade}}级<button class="btn btn-xs btn-default btnAdd"><i class="glyphicon glyphicon-plus"></i></button></h4>
		</div>
		<div class="teachers">
			{{~thesis.teachers :tt}} 
			<span class="teacher" data-ttid="{{=tt.ttid}}" data-tid="{{=tt.tid}}">{{=tt.name}} <i class="glyphicon glyphicon-remove" style="visibility:hidden"></i></span>
			{{~}}
		</div>
	</div>
	{{~}}
	</script>
	<script id="teacher-template" type="text/x-dot-template">
	<span class="teacher" data-ttid="{{=it.ttid}}" data-tid="{{=it.tid}}">{{=it.name}} <i class="glyphicon glyphicon-remove" style="visibility:hidden"></i></span>
	</script>
	<script id="select-template" type="text/x-dot-template">
	{{~it :teacher}}
	<div class="col-md-2"><label class="checkbox-inline"><input type="checkbox" data-id="{{=teacher.id}}">{{=teacher.name}}</label></div>
	{{~}}
	</script>
</body>
</html>