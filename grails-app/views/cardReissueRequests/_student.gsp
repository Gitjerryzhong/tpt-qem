<div class="form-group" style="text-align:center">
	<image src="${createLink(uri:"/pictures/" + student.id)}" class="img-thumbnail" width="130" height="189"/>
</div>
<div class="row">
	<div class="col-sm-6">
		<div class="form-group">
			<label class="col-sm-6 control-label">姓名</label>
			<div class="col-sm-6">
				<p class="form-control-static">${student.name}</p>
			</div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<label class="col-sm-6 control-label">性别</label>
			<div class="col-sm-6">
				<p class="form-control-static">${student.sex}</p>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-sm-6">
		<div class="form-group">
			<label class="col-sm-6 control-label">学号</label>
			<div class="col-sm-6">
				<p class="form-control-static">${student.id}</p>
			</div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<label class="col-sm-6 control-label">生源地</label>
			<div class="col-sm-6">
				<p class="form-control-static">${student.province}</p>
			</div>
		</div>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-3 control-label">学院（部）</label>
	<div class="col-sm-9">
		<p class="form-control-static">${student.adminClass.department.name}</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-3 control-label">专业</label>
	<div class="col-sm-9">
		<p class="form-control-static">${student.major.name}</p>
	</div>
</div>
<div class="row">
	<div class="col-sm-6">
		<div class="form-group">
			<label class="col-sm-6 control-label">出生年月</label>
			<div class="col-sm-6">
				<p class="form-control-static">${student.birthday}</p>
			</div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<label class="col-sm-6 control-label">层次</label>
			<div class="col-sm-6">
				<p class="form-control-static">${student.educationLevel}</p>
			</div>
		</div>
	</div>
</div>