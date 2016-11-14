<html>
<head>
<meta name="layout" content="main" />
<title>教学材料管理</title>
<asset:stylesheet src="materials/teachers"/>
<asset:javascript src="materials/teachers"/>
<asset:script>
$(function() {
    $("#teachingMaterialList").teachingMaterialList({
        terms: ${termIds},
    	term: ${termId}
    });
});
</asset:script>
</head>
<body>
	<div id="teachingMaterialList">
		<div class="btn-toolbar">
			<div class="col-md-2 pull-right">
				<select id="terms" class="form-control input-sm"></select>
			</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>教师</th>
					<th>应交</th>
					<th>已交</th>
					<th>免交</th>
					<th>比例</th>
					<th>百分比</th>
					<th></th>
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
	<script id="rows-template" type="text/x-dot-template">
	{{~it :v}}
	<tr data-id={{=v.id}} data-thesis="{{=v.thesis}}" data-internship="{{=v.internship}}">
		<td class="c0">{{=v.name}}</td>
		<td class="c1 total">{{=v.total}}</td>
		<td class="c1 s1">{{=v.commit}}</td>
		<td class="c1 s2">{{=v.free}}</td>
		<td class="c2 ratio">
				<span class="total">
					<span class="s1" style="width:{{=v.commit/v.total*100}}%;"></span>
					<span class="s2" style="width:{{=v.free/v.total*100}}%;"></span>
				</span>
		</td>
		<td class="c1 percent">{{=Math.round(v.commit/(v.total-v.free)*100)}}%</td>
		<td></td>
	</tr>
	{{~}}
	</script>
	<script id="detail-template" type="text/x-dot-template">
	{{##def.cell:param: 
	{{ var commit = it.find(it.commits, param.tmid, param.target)
         , status = commit ? commit.status : 0
         , id = commit ? commit.id : 0; }}
	<td class="cell"
		data-id="{{=id}}"
		data-status="{{=status}}"
		data-tmid="{{=param.tmid}}" 
		data-target="{{=param.target}}"
		{{?param.colspan>1}}colspan="{{=param.colspan}}"{{?}}>
		<span class="label {{=it.STATUS[status].color}}">{{=it.STATUS[status].text}}</span>
	</td>
	#}}
	<table class="detail table table-bordered">
	<thead>
		<tr>
			<th colspan="3">教学材料</th>
			{{? it.courses.length}}
				{{~it.courses :course}}
				<th colspan="{{=course.classes.length}}">{{=course.name}}</th>
				{{~}}
			{{??}}
				<th rowspan="2" width="150">提交情况</th>
			{{?}}
		</tr>
		<tr>
			<th>材料类别</th>
			<th>材料名称</th>
			<th>提交形式</th>
			{{? it.courses.length}}
				{{~it.courses :course}}{{~course.classes :courseClass}}
				<th>{{=courseClass.id}}</th>
				{{~}}{{~}}
			{{?}}
		</tr>
	</thead>
	<tbody>
		{{~it.categories :c:ci}}{{~c.materials :m:mi}}
		{{? c.type == 1 && it.courses.length || it.thesis && c.id == 5 || it.internship && c.id == 6}}
		<tr>
			{{? mi == 0}}
			<td rowspan="{{=c.materials.length}}">{{=c.name}}</td>
			{{?}}
			<td>{{=m.name}}</td>
			<td>{{=['形式不限','纸质版','电子版-线下提交','电子版-其它系统','电子版-在线提交'][m.form] }}</td>
			{{? m.type == 1}}
				{{~it.courses :course:courseIndex}}{{~course.classes :courseClass:courseClassIndex}}
					{{#def.cell:{tmid:m.tmid, target:courseClass.id, colspan:1};}}
				{{~}}{{~}}
			{{?? m.type == 2}}
				{{~it.courses :course:courseIndex}}
					{{#def.cell:{tmid:m.tmid, target:course.id, colspan:course.classes.length};}}
				{{~}}
			{{?? m.type == 0}}
				{{#def.cell:{tmid:m.tmid, target:it.termId, colspan:it.classCount};}}
			{{?}}
		</tr>
		{{?}}
		{{~}}{{~}}
	</tbody>
	</table>
	</script>
	<script id="cell-toolbar-template" type="text/x-dot-template">
		<div class="btn-group" data-toggle="buttons-radio">
			<button id="btn0" class="btn btn-xs" data-status="0"><i class="glyphicon glyphicon-remove"></i></button>
			<button id="btn1" class="btn btn-xs" data-status="1"><i class="glyphicon glyphicon-ok"></i></button>
			<button id="btn2" class="btn btn-xs" data-status="2"><i class="glyphicon glyphicon-ban-circle"></i></button>
		</div>
	</script>
</body>
</html>