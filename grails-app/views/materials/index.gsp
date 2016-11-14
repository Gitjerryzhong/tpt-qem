<html>
<head>
<meta name="layout" content="main" />
<title>教学材料设置</title>
<asset:stylesheet src="materials/admin"/>
<asset:javascript src="materials/admin"/>
<asset:script>
$(function() {
    $("#materialList").materialList({
    	system: ${system},
        materials: ${materials}
    });
});
</asset:script>
</head>
<body>
	<div id="materialList">
		<div class="btn-toolbar">
			<button id="btnCreate" class="btn btn-sm btn-default">新建</button>
		</div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>分类</th>
					<th>材料名称</th>
					<th>学院</th>
					<th>类型</th>
					<th>提交形式</th>
					<th>说明</th>
					<th style="text-align:right">操作</th>
				</tr>
			</thead>
			<tbody id="listBody">
			</tbody>
		</table>
	</div>
	<script id="row-template" type="text/x-dot-template">
		<td>{{=it.categoryName}}</td>
		<td>{{=it.name}}</td>
		<td>{{? it.deptId}}{{=it.deptName}}{{??}}校级材料{{?}}</td>
		<td>{{=['按学期','按课程班','按课程'][it.type]}}</td>
		<td>{{=['形式不限','纸质版','电子版-线下提交','电子版-其它系统','电子版-在线提交'][it.form] }}</td>
		<td>{{? it.description}}
              {{? it.description.length > 30}}
                {{=it.description.substring(0,30)}}...
              {{??}}
                {{=it.description}}
              {{?}}
            {{?}}
        </td>
		<td style="text-align:right"><i class="glyphicon glyphicon-pencil edit"/> <i class="glyphicon glyphicon-trash delete"/></td>
	</script>
	<div id="dialog" class="modal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3 id="myModalLabel">教学材料编辑</h3>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-2" for="material-name">名称</label>
							<div class="col-sm-10">
								<input class="form-control" type="text" id="material-name">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="material-category">类别</label>
							<div class="col-sm-10">
								<g:select class="form-control" name="material-category" from="${categories}" optionKey="id" optionValue="name"/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="material-type">提交频率</label>
							<div class="col-sm-10">
								<select id="material-type" class="form-control">
									<option value="0">按学期</option>
									<option value="1">按教学班</option>
									<option value="2">按课程</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="material-form">提交形式</label>
							<div class="col-sm-10">
								<select id="material-form"  class="form-control">
									<option value="0">形式不限</option>
									<option value="1">纸质版</option>
									<option value="2">电子版-线下提交</option>
									<option value="3">电子版-其它系统</option>
									<!-- 
									<option value="4">电子版（在线提交）</option>
									 -->
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2" for="material-description">说明</label>
							<div class="col-sm-10">
								<textarea id="material-description" rows="5" class="input-xlarge form-control"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="btnSave" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	<div id="confirm" class="modal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<p>确定要删除吗？</p>
				</div>
				<div class="modal-footer">
					<button class="btn" data-dismiss="modal">取消</button>
					<button id="btnDelete" class="btn btn-primary" data-dismiss="modal">确定</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>