<html>
<head>
<meta name="layout" content="main" />
<title>班级通讯录</title>
<asset:javascript src="addresses/admin-classes"/>
<asset:script>
$(function() {
	$("#addressList").adminClassesAddressList({
		adminClasses: ${adminClasses}
	});
});
</asset:script>
</head>
<body>
    <div id="addressList">
    	<div class="toolbar row"></div>
    	<table class="table" style="width:auto"></table>
    </div>
</body>
</html>