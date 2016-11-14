<html>
<head>
<meta name="layout" content="main" />
<title>学生证补办申请列表</title>
<asset:stylesheet src="card/reissue/request/index"/>
<asset:javascript src="card/reissue/request/index"/>
<asset:script>
$(function() {
    $("#cardReissueRequestIndex").cardReissueRequestIndex({});
});
</asset:script>
</head>
<body>
	<div id="cardReissueRequestIndex">
		<div class="btn-toolbar">
			<div class="btn-group" data-toggle="buttons">
	  			<label class="btn btn-default status active">
					<input type="radio" name="options" value="1">申请成功(<span class="count">0</span>)
	 			</label>
				<label class="btn btn-default status">
					<input type="radio" name="options" value="2">正在办理(<span class="count">0</span>)
				</label>
				<label class="btn btn-default status">
					<input type="radio" name="options" value="4">办理完成(<span class="count">0</span>)
				</label>
			</div>
		</div>
		<div id="content"></div>
	</div>
	<script id="table-template" type="text/x-dot-template">
	<table class="table">
		<thead>
			<tr>
				<th class="no">序号</th>
				<th class="studentId">学号</th>
				<th class="name">姓名</th>
				<th class="sex">性别</th>
				<th class="department">学院</th>
				<th class="major">专业</th>
				<th class="applyDate">申请时间</th>
				<th class="applyCount">申请次数</th>
			</tr>
		</thead>
		<tbody>
			{{~it.requests :request:index}} 
			<tr data-id="{{=request.id}}">
				<td>{{=it.offset + index + 1}}</td>
				<td><a href="./cardReissueRequests/{{=request.id}}" target="_blank">{{=request.studentId}}</a></td>
				<td>{{=request.name}}</td>
				<td>{{=request.sex}}</td>
				<td>{{=request.department}}</td>
				<td>{{=request.major}}</td>
				<td>{{=moment(request.applyDate).format("YYYY-MM-DD HH:mm")}}
				<td>{{=request.count}}</td>
			</tr>
			{{~}}
		</tbody>
	</table>
	<ul class="pager">
		<li class="prev-page"><a href="#">上一页</a></li>
		<li class="next-page"><a href="#">下一页</a></li>
	</ul>
	</script>
</body>
</html>