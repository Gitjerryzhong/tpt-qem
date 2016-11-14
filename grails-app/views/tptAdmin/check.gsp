<html >
<head>
<meta name="layout" content="main" />
<title>审核审批</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>

<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>

<asset:stylesheet src="tpt/tpt.css"/>
<asset:javascript src="tpt/check.js"/>

</head>
<body >
	<div  ng-app="checkApp"  class="container" ng-controller="TrialCtrl">
	<div class="col-sm-3 sidebar">
			<div class="list-group">
				<a class="list-group-item statis" ng-class="{'active':checkStatus<4}" href="#"  ng-click="goTrial(1)"><span class="badge">{{pager.total[1]}}</span>材料初审</a>
				<a class="list-group-item statis"  ng-class="{'active':checkStatus>=4}" href="#"  ng-click="goTrial(4)"><span class="badge">{{pager.total[4]}}</span>论文审核</a>				
<%--				<a class="list-group-item statis" href="#"  ng-click="downloadPhoto()"><span class="badge"></span>批量下载照片</a>--%>
<%--				<a class="list-group-item statis" href="#"  ng-click="downloadPaper()"><span class="badge"></span>批量下载论文</a>--%>
<%--				<a class="list-group-item statis" href="#"  ui-sref="export"><span class="badge"></span>导出Excel</a>--%>
			</div>					
			<div class="form-group">  
			<form name="searchForm" role="form">
		    <div class="input-group">		     
		      <input type="text" ng-pattern="/^\d{10}$/" class="form-control" id="studentId" placeholder="学号" ng-model="studentId" required >
		      <div class="input-group-addon btn btn-default" ng-click="search()" ng-disabled="searchForm.$invalid">查询</div>		      
		    </div>
		    </form> 
		    </div>					
			<div class="form-group">  
		    <form name="searchForm1" role="form">
		    <div class="input-group">		     
		      <input type="text"  class="form-control" id="studentName" placeholder="姓名" ng-model="studentName" required >
		      <div class="input-group-addon btn btn-default" ng-click="search1()" ng-disabled="searchForm1.$invalid">查询</div>		      
		    </div>
		    </form> 
		  </div>
		</div>
		<div class="col-sm-9 content">
			<div  ui-view=""> </div>
		</div>
		
		
	
	<script type="text/ng-template" id="tpt-trial.html">
			<div class="form-horizontal" >				
            	<g:render template="trial/list"></g:render>
        	</div>
	</script>	
	<script type="text/ng-template" id="set-mentor.html">
			<div class="form-horizontal" >				
            	<g:render template="mentor/setMentor"></g:render>
        	</div>
	</script>
	<script type="text/ng-template" id="tpt-details.html">
        %{--//创建详情视图--}%
        <div class="modal-header">            
			<div class="pull-right">
				<div class="btn-group">
				<button class="btn btn-default prev {{disabled_p1()}}" ng-click="details(contact.prevId)">
					<span class="glyphicon glyphicon-chevron-left"></span>
				</button>
				<button class="btn btn-default next {{disabled_n1()}}" ng-click="details(contact.nextId)">
					<span class="glyphicon glyphicon-chevron-right"></span>
				</button>
				</div>
			</div>
			<h3 class="title">{{contact.userName}}({{contact.userId}})</h3>
        </div>
		<form name="myForm" role="form" novalidate>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="details/form"></g:render>
				<div class="row">
					<div class="form-group">
		   				 <label for="content" class="col-sm-2 control-label">审核意见</label>
		    			 <div class="col-sm-10">
		    				<textarea name="content"  rows="4" class="form-control" placeholder="请输入意见！审核不同过的必须说明理由！" ng-model="trial.content" required></textarea>
		   				 </div>
					</div>
				</div>
        	</div>
        </div>
        <div class="modal-footer">
<%--             <button class="btn btn-default" onclick="history.go(-1)"> 返回</button>--%>
 			<button class="btn btn-success" ng-click="ok('20',contact.formId,contact.prevId,contact.nextId)"> 批准</button>
			<button class="btn btn-warning" ng-click="ok('21',contact.formId,contact.prevId,contact.nextId)" ng-disabled="myForm.$invalid"> 不批准</button>
			<button class="btn btn-danger" ng-click="ok('40',contact.formId,contact.prevId,contact.nextId)" ng-disabled="myForm.$invalid"> 关闭</button>
        </div>
		</form>
        </script> 
        <script type="text/ng-template" id="tpt-check.html">
        %{--//创建初审视图--}%
        <div class="modal-header">
            <h3 class="modal-title">初审</h3>
        </div>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="trial/form"></g:render>
        	</div>
        </div>
        <div class="modal-footer">
             <button class="btn btn-primary" ng-click="ok()" > 确定</button>
			<button class="btn btn-warning" ng-click="cancel()" > 关闭</button>
        </div>
        </script>   
        <script type="text/ng-template" id="tpt-search.html">
        %{--//创建详情视图--}%
        <div class="modal-header">
			<h3 class="title">{{contact.userName}}({{contact.userId}})</h3>
        </div>		
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="details/form"></g:render>
        	</div>
        </div>       
        </script> 
         <script type="text/ng-template" id="download-photo.html">
           <div ng-show="downloadUrl!=null"><a ng-href="{{downloadUrl}}">点击下载初审通过学生照片<span class="glyphicon glyphicon-download-alt"></span></a></div>
			<div ng-show="downloadUrlEnd!=null"><a ng-href="{{downloadUrlEnd}}">点击下载终审通过学生照片<span class="glyphicon glyphicon-download-alt"></span></a></div>
        </script> 
        <script type="text/ng-template" id="download-paper.html">
           <div><a ng-href="{{downloadUrl}}">点击下载当前批次论文及互认申请表<span class="glyphicon glyphicon-download-alt"></span></a></div>
        </script> 
        <script type="text/ng-template" id="export.html">
           <div><g:link id="requests" uri="/tptAdmin/export">导出申请清单</g:link></div>
 		   <div><g:link id="requests" uri="/tptAdmin/exportPaperMtlRgn">导出论文成绩互认表</g:link></div>
        </script> 
        <script type="text/ng-template" id="update-scort.html">
			<div class="form-horizontal" style="margin:1em 1em 1em 1em;">				
            	<g:render template="updateScort/form"></g:render>
        	</div>
		</script>
	</div>
</body>
</html>