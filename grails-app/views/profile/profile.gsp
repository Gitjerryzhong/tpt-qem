<html>
<head>
<meta name="layout" content="main" />
<title>修改个人信息</title>
<asset:javascript src="profile/profile"/>
</head>
<body>
	<div class="tabbable tabs-left">
		<ul class="nav nav-tabs" style="margin-bottom:20px;">
			<li class="active"><a href="#profile" data-toggle="tab">人个信息</a></li>
			<li><a href="#password" data-toggle="tab">修改密码</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="profile">
				<form class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-4" for="longPhone">手机号码</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="longPhone" name="longPhone" value="${user.longPhone}" 
								pattern="^\d{11}$">
						</div>
						<span class="help-block col-sm-4"></span>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="shortPhone">手机短号</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="shortPhone"  name="shortPhone" value="${user.shortPhone}" 
								pattern="^\d{4,6}$">
						</div>
						<span class="help-block col-sm-4"></span>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="email">E-mail</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="email" name="email" value="${user.email}"
								pattern="^[a-zA-Z0-9]{1}[\.a-zA-Z0-9_-]*[a-zA-Z0-9]{1}@[a-zA-Z0-9]+[-]{0,1}[a-zA-Z0-9]+[\.]{1}[a-zA-Z]+[\.]{0,1}[a-zA-Z]+$">
						</div>
						<span class="help-block col-sm-4"></span>
					</div>
					<g:if test="${isTeacher}">
					<div class="form-group">
						<label class="control-label col-sm-4" for="officeAddress">办公室地址</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="officeAddress" name="officeAddress" value="${user.officeAddress}">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="officePhone">办公室电话</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="officePhone" name="officePhone" value="${user.officePhone}">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="homeAddress">家庭地址</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="homeAddress" name="homeAddress" value="${user.homeAddress}">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="homePhone">家庭电话</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="homePhone" id="homePhone" value="${user.homePhone}">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="qqNumber">QQ</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="qqNumber" id="qqNumber" value="${user.qqNumber}" pattern="^\d*$">
						</div>
					</div>
					</g:if>
					<div class="form-group">
						<div class="col-sm-4 col-sm-offset-4">
							<input type="button" id="profileSubmit" class="btn btn-primary" value="保存">
							<span id="profileAlert" class="alert" style="margin-left:20px;display:none;"></span>
						</div>
					</div>
				</form>
			</div>
			<div class="tab-pane" id="password">
				<form class="form-horizontal" action="" method="post">
					<div class="form-group">
						<label class="control-label col-sm-4" for="oldPassword">旧密码</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" id="oldPassword" name="oldPassword">
						</div>
						<span class="help-block col-sm-4"></span>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="newPassword">新密码</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" id="newPassword" name="newPassword" 
								pattern="^[A-Za-z0-9]{6,16}$">
							<span class="help-block">6-16位数字或字母</span>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="confirmPassword">新密码确认</label>
						<div class="col-sm-4">
							<input type="password" class="form-control" name="confirmPassword" id="confirmPassword" 
								pattern="^[A-Za-z0-9]{6,16}$" data-confirm="#newPassword"> 
							<span class="help-block">6-16位数字或字母</span>
						</div>
						<span class="help-block col-sm-4 confirm"></span>
					</div>
					<div class="form-group">
						<div class="col-sm-4 col-sm-offset-4">
							<input type="button" id="passwordSubmit" class="btn btn-primary" value="保存">
							<span id="passwordAlert" class="alert" style="margin-left:20px;display:none;"></span>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>