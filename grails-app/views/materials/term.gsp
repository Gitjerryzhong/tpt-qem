<html>
<head>
<meta name="layout" content="main" />
<title>学期教学材料设置</title>
<asset:stylesheet src="materials/term"/>
<asset:javascript src="materials/term"/>
<asset:script>
$(function() {
    $("#termMaterialList").termMaterialList({
    	terms: ${termIds},
    	term: ${termId},
        materials: ${materials}
    });
});
</asset:script>
</head>
<body>
	<div id="termMaterialList">
		<div class="btn-toolbar">
			<div class="btn-group">
				<g:link uri="/thesisTeachers" class="btn btn-sm btn-default">设置毕业论文教师</g:link>
				<g:link uri="/internshipTeachers" class="btn btn-sm btn-default">设置毕业实习教师</g:link>
			</div>
			<div class="col-md-2 pull-right">
				<select id="terms" class="pull-right form-control input-sm"></select>
			</div>
		</div>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>选择</th>
					<th>分类</th>
					<th>材料名称</th>
					<th>类型</th>
					<th>提交形式</th>
					<th>说明</th>
				</tr>
			</thead>
			<tbody id="listBody">
			</tbody>
		</table>
	</div>
	<script id="option-template" type="text/x-dot-template">
	{{~it :t}}
		<option value="{{=t}}">{{=parseInt(t/10)}}-{{=parseInt(t/10)+1}}-{{=t%10}}</option>
	{{~}}
	</script>
	<script id="row-template" type="text/x-dot-template">
		<td><input type="checkbox" data-mid={{=it.id}}></td>
		<td>{{=it.category}}</td>
		<td>{{=it.name}}</td>
		<td>{{=['按学期','按教学班','按课程'][it.type]}}</td>
		<td>{{=['形式不限','纸质版','电子版-线下提交','电子版-其它系统','电子版-在线提交'][it.form] }}</td>
		<td>{{? it.description}}
              {{? it.description.length > 30}}
                {{=it.description.substring(0,30)}}...
              {{??}}
                {{=it.description}}
              {{?}}
            {{?}}
        </td>
	</script>
</body>
</html>