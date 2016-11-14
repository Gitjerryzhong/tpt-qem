<html>
<head>
<meta name="layout" content="main" />
<title>学生证补办申请</title>
<asset:stylesheet src="card/reissue/request/form"/>
<asset:javascript src="card/reissue/request/form"/>
<asset:script>
$(function() {
	$("#cardReissueRequestForm").cardReissueRequestForm({
		requestId: ${reissueRequest ? reissueRequest.id : 'null'}
	});
});
</asset:script>
</head>
<body>
	<div id="cardReissueRequestForm">
		<div id="toolbar" class="btn-toolbar">
			<div class="pull-right">
				<button id="continue" class="btn btn-primary" title='开始申请'>继续</button>
				<button id="submit" class='btn btn-primary' title='提交申请' style="display:none">提交</button>
			</div>
		</div>
		<g:if test="${requests}">
		<div class="alert alert-danger">
			你已经有${requests.size()}次补办学生证申请：
			<g:each in="${requests}" var="req">
			<g:link uri="/cardReissueRequests/${req.id}">查看</g:link>
			</g:each>
		</div>
		</g:if>
		<div id="card">
			<div id="notice">
				<h3>补办学生证注意事项</h3>
				<ol >
					<li>学生在校期间只能补办一次学生证。</li>
					<li>每学期受理两次补办学生证业务：
						<ul>
							<li>第5教学周前（含）申请补办学生证的学生，在第9教学周到学生所在学院（部）领取补办的学生证；</li>
							<li>第13教学周前（含）申请补办学生证的学生，在第17教学周到学生所在学院（部）领取补办的学生证。</li>
						</ul>
					<li>补办学生证满半年后可申请补办火车票乘车优惠卡。</li>
				</ol>
				<table>
					<caption>各学院（部）教务办公地点</caption>
					<thead>
						<tr><th>学院</th><th>联系人</th><th>办公电话</th><th>办公地点</th></tr>
					</thead>
					<tbody>
						<tr><td>文学院</td><td>姜老师</td><td>6126055</td><td>乐育楼B307</td></tr>
						<tr><td>教育学院</td><td>杨老师</td><td>6126621</td><td>综合楼C203</td></tr>
						<tr><td>管理学院</td><td>丁老师</td><td>6126900</td><td>综合楼C301</td></tr>
						<tr><td>特许经营学院</td><td>姚老师</td><td>6126610</td><td>京师综合楼二楼</td></tr>
						<tr><td>不动产学院</td><td>甄老师</td><td>6126913-615</td><td>综合C302-1</td></tr>
						<tr><td>信息技术学院</td><td>曾老师</td><td>6126760</td><td>励耘楼A314</td></tr>
						<tr><td>外国语学院</td><td>赖老师</td><td>6126619</td><td>励耘楼B205</td></tr>
						<tr><td>艺术与传播学院</td><td>江老师</td><td>6126798</td><td>艺传楼C105</td></tr>
						<tr><td>法律与行政学院</td><td>曾老师</td><td>6126070</td><td>综合楼C202</td></tr>
						<tr><td>物流学院</td><td>赖老师</td><td>6126566</td><td>乐育楼B106</td></tr>
						<tr><td>设计学院</td><td>张老师</td><td>6126121-303</td><td>传媒楼D202</td></tr>
						<tr><td>应用数学学院</td><td>马老师</td><td>6126672</td><td>综合C302-2</td></tr>
						<tr><td>工程技术学院</td><td>孔老师</td><td>6128216-118</td><td>工程楼2504</td></tr>
						<tr><td>运动休闲学院</td><td>陈老师</td><td>6126083</td><td>励耘楼A308</td></tr>
						<tr><td>国际商学部</td><td>林老师</td><td>6126643</td><td>综合楼C201</td></tr>
						<tr><td>中加合作项目</td><td>郑老师</td><td>6126711</td><td>图书馆B105</td></tr>
					</tbody>
				</table>
			</div>
			<form id="content" class="form-horizontal container" style="display:none">
				<g:render template="student"/>
				<div class="form-group">
					<label class="col-sm-3 control-label">补办理由</label>
					<div class="col-sm-9">
						<textarea id="reason" class="form-control" rows="8">${reissueRequest?.reason}</textarea>
						<div id="errors" style="display:none">
							<ul class="list-unstyled text-danger" >	</ul>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>