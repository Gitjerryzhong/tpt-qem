<html>
<head>
<meta name="layout" content="main" />
<title>考勤统计图</title>
<!--[if lte IE 8]>
<script src="${assetPath(src:'lib/excanvas.min.js')}" type="text/javascript"></script>
<![endif]-->
<asset:stylesheet src="rollcall-statis/gradeMajorAdminClass"/>
<asset:javascript src="rollcall-statis/gradeMajorAdminClass"/>
<asset:script>
$(function() {
	$("#rollcallGradeMajorAdminClass").rollcallGradeMajorAdminClass({
		statis:${statis}
	});
});
</asset:script>
</head>
<body>
    <div id="rollcallGradeMajorAdminClass" class="container">
    	<div class="row">
	    	<ul id="statisCategory" class="nav nav-pills col-md-6">
				<li class="active"><a href="#" data-category="0">班级</a></li>
				<li><a href="#" data-category="1">专业</a></li>
				<li><a href="#" data-category="2">年级</a></li>
				<li><a href="#" data-category="3">年级+专业</a></li>
			</ul>
			<div id="select" class="col-md-6" align="right">
				<label class="checkbox-inline"><input type="checkbox" value="0" checked>旷课</label>
				<label class="checkbox-inline"><input type="checkbox" value="1">迟到</label>
				<label class="checkbox-inline"><input type="checkbox" value="2" checked>早退</label>
				<label class="checkbox-inline"><input type="checkbox" value="3">请假</label>
				<label class="radio-inline" style="margin-left:20px"><input type="radio" name="statisType" value="0" checked>按数量</label>
				<label class="radio-inline"><input type="radio" name="statisType" value="1">按比例</label>
			</div>
    	</div>
    	<div id="graph"></div>
    </div>
</body>
</html>