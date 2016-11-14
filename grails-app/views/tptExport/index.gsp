<html >
<head>
<meta name="layout" content="main" />
<title>数据导出</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>

<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>

<asset:stylesheet src="tpt/tpt.css"/>
<asset:javascript src="tpt/export.js"/>

</head>
<body >
	<div  ng-app="checkApp"  class="container" ng-controller="TrialCtrl">
	<div class="col-sm-3 sidebar">
			<div class="list-group">
				<a class="list-group-item statis active" href="#" data-status="0">批量导出</a>
				<a class="list-group-item statis" href="#"  ui-sref="downloadPhoto"><span class="badge"></span>导出照片</a>				
				<a class="list-group-item statis" href="#"  ui-sref="downloadPaper"><span class="badge"></span>导出论文</a>
				<a class="list-group-item statis" href="#"  ui-sref="downloadALl"><span class="badge"></span>导出所有</a>
				<a class="list-group-item statis" href="#"  ui-sref="exportRequest"><span class="badge"></span>导出申请表</a>
				<a class="list-group-item statis" href="#"  ui-sref="exportMtlRgn"><span class="badge"></span>导出成绩互认表</a>
			</div>				
		</div>
		<div class="col-sm-9 content">
			<div  ui-view=""> </div>
		</div>
		
	
         <script type="text/ng-template" id="download-photo.html">
 		<div class="modal-header">
            <h3 class="modal-title">导出历年批次照片</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >
				<div class="form-group">
					<label for="bn" class="col-sm-2 control-label">请选导出年份</label>
    				<div class="col-sm-3">
    					<select class="form-control" name="bn" ng-model="bn" ng-options="y for y in bns"  >	</select>
    				</div>     
    				<a class="btn btn-primary" href="/tms/tptExport/downloadPhotos/{{bn}}">下 载</a> 
				</div>
			</div>
		</div>
        </script> 
        <script type="text/ng-template" id="download-paper.html">
 		<div class="modal-header">
            <h3 class="modal-title">导出历年批次论文</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >
				<div class="form-group">
					<label for="bn" class="col-sm-2 control-label">请选导出年份</label>
    				<div class="col-sm-3">
    					<select class="form-control" name="bn" ng-model="bn" ng-options="y for y in bns"  >	</select>
    				</div>     
    				<a class="btn btn-primary" href="/tms/tptExport/downloadPapers/{{bn}}">下 载</a> 
				</div>
			</div>
		</div>
        </script> 
        <script type="text/ng-template" id="download-all.html">
 		<div class="modal-header">
            <h3 class="modal-title">导出历年获得学位学生所有文件</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >
				<div class="form-group">
					<label for="bn" class="col-sm-2 control-label">请选导出年份</label>
    				<div class="col-sm-3">
    					<select class="form-control" name="bn" ng-model="bn" ng-options="y for y in bns"  >	</select>
    				</div>     
    				<a class="btn btn-primary" href="/tms/tptExport/downloadAll/{{bn}}">下 载</a> 
				</div>
			</div>
		</div>
        </script> 
        <script type="text/ng-template" id="export.html">
 		<div class="modal-header">
            <h3 class="modal-title">导出历年申请表（Excel）</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >
				<div class="form-group">
					<label for="bn" class="col-sm-2 control-label">请选导出年份</label>
    				<div class="col-sm-3">
    					<select class="form-control" name="bn" ng-model="bn" ng-options="y for y in bns"  >	</select>
    				</div>     
    				<a class="btn btn-primary" href="/tms/tptExport/export/{{bn}}">导出</a> 
				</div>
			</div>
		</div>
        </script> 
        <script type="text/ng-template" id="export-mtlRgn.html">
 		<div class="modal-header">
            <h3 class="modal-title">导出历年成绩互认表（Excel）</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >
				<div class="form-group">
					<label for="bn" class="col-sm-2 control-label">请选导出年份</label>
    				<div class="col-sm-3">
    					<select class="form-control" name="bn" ng-model="bn" ng-options="y for y in bns"  >	</select>
    				</div>     
    				<a class="btn btn-primary" href="/tms/tptExport/exportPaperMtlRgn/{{bn}}">导出</a> 
				</div>
			</div>
		</div>
        </script> 
	</div>
</body>
</html>