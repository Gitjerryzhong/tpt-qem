<html >
<head>
<meta name="layout" content="main" />
<title>教学质量工程系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>
<%--<asset:javascript src="bootstrap/bootstrap.min.js"/>--%>
<%--文件上传--%>
<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:stylesheet src="qem/c_check.css"/>
<asset:javascript src="qem/c_check.js"/>
<script>
cCheckApp.value("config", {
	requests: ${ requests as grails.converters.JSON},
	pager: ${pager as grails.converters.JSON},
	bn: ${bn}
	});
</script>
</head>
<body >
	<div  ng-app="cCheckApp"  class="container" ng-controller="defaultCtrl">
	<div class="col-sm-2 sidebar">
			<div class="list-group">
			<a class="list-group-item statis active" href="#" data-status="0">学院审核</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrial(0)"><span class="badge">{{pager.total.t0}}</span>未审核</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrial(1)"><span class="badge">{{pager.total.t1}}</span>已通过</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrial(2)"><span class="badge">{{pager.total.t2}}</span>退回</a>						
				<a class="list-group-item statis" href="#"  ng-click="goTrial(6)"><span class="badge">{{pager.total.t3}}</span>已关闭</a>
				<a class="list-group-item statis" href="#"  ng-click="contractList()">合同审核</a>
<%--				<a class="list-group-item statis" href="#"  ng-click="historyTaskList()">已立项项目汇总</a>--%>
			<a class="list-group-item statis active" href="#" data-status="0">学校审核结果</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrial(7)"><span class="badge">{{pager.total.t4}}</span>拟立项</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrial(8)"><span class="badge">{{pager.total.t5}}</span>不立项</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrial(9)"><span class="badge">{{pager.total.t6}}</span>须修改</a>
				<a class="list-group-item statis" href="#"  ng-click="goTrial(3)"><span class="badge">{{pager.total.t7}}</span>退回</a>
			</div>	
		</div>
		<div class="col-sm-10 content">
			<div  ui-view=""> </div>
		</div>
		
		
	
	<script type="text/ng-template" id="qem-default.html">
	<div >	
	<div class="pull-right" style="margin-bottom:10px">
			<a style="margin-right:20px" href="/tms/QemCollegeCheck/exportAttach_All/{{bn}}" toolTip="批量导出全部附件"><span class="glyphicon glyphicon-download-alt"></span>导出全部附件</a>
			<a style="margin-right:20px" href="/tms/QemCollegeCheck/exportAttach/{{bn}}" toolTip="批量导出通过学院评审项目附件"><span class="glyphicon glyphicon-download-alt"></span>导出通过学院评审项目附件</a> 	
			<a href="/tms/QemCollegeCheck/export/{{bn}}" toolTip="分类导出项目清单"><span class="glyphicon glyphicon-download-alt"></span>导出Excel</a> 
	</div>	
		<table class="table table-hover">
			<thead>
				<tr>
					<th>项目名称</th>
					<th>负责人姓名</th>
					<th>职称</th>	
					<th>学历</th>	
					<th>岗位</th>
					<th>电话</th>	
					<th>专业</th>		
					<th>项目等级</th>		
					<th>项目类别</th>
					<th>申请日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in requests">	
				<td>{{item.projectName}}</td>			
				<td>{{item.userName}}</td>
				<td>{{item.currentTitle}}</td>
				<td>{{item.currentDegree}}</td>
				<td>{{item.position}}</td>
				<td>{{item.phoneNum}}</td>
				<td>{{item.major}}</td>
				<td>{{levelText(item.projectLevel)}}</td>
				<td>{{item.type}}</td>
				<td>{{dateFormat(item.commitDate) | date : 'yyyy-MM-dd'}}</td>
				 <td>
                                     
                      <button type="button" class="btn btn-warning btn-xs"  ng-click="details(item.id)" ng-show="auditAble()">
                          <span class="glyphicon glyphicon-pencil"></span> 审核
                      </button>
<%--						<button type="button" class="btn btn-success btn-xs"  ng-click="details(item.id)" ng-show="!auditAble()">--%>
                          <span class="glyphicon glyphicon-align-justify btn-success" ng-click="details(item.id)" toolTip="详情" ng-show="!auditAble()" style="background-color:green"></span> 
							<span class="glyphicon glyphicon-ban-circle btn-warning" toolTip="撤销" ng-if="item.status==0 && checkStatus!=0 && checkStatus!=3" ng-click="cancel(item.id)"></span>
<%--                      </button>--%>
                  </td>
				</tr>
			</tbody>
		</table>
		
		<ul class="pager">
		<li class="prev-page {{disabled_prev()}}"><a href="#" ng-click="previous()" >上一页</a></li>
		<li class="next-page {{disabled_next()}}"><a href="#" ng-click="next()">下一页</a></li>
		</ul>	
	</div>
	</script>	
	<script type="text/ng-template" id="qem-details.html">
        %{--//创建详情视图--}%
        <div class="modal-header">            
			<div class="pull-right">
				<div class="btn-group">
				<button class="btn btn-default prev {{disabled_p1()}}" ng-click="details(pager.prevId)">
					<span class="glyphicon glyphicon-chevron-left"></span>
				</button>
				<button class="btn btn-default next {{disabled_n1()}}" ng-click="details(pager.nextId)">
					<span class="glyphicon glyphicon-chevron-right"></span>
				</button>
				</div>
			</div>
			<h3 class="title">{{project.projectName}}</h3>
        </div>
		<form name="myForm" role="form" novalidate>
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="detail/form"></g:render>
				<div class="row">
					<div class="form-group">
		   				 <label for="content" class="col-sm-2 control-label">审核意见</label>
		    			 <div class="col-sm-10">
		    				<textarea name="content"  rows="4" class="form-control" placeholder="请输入意见！不少于6个字！" ng-model="trial.content" required></textarea>
		   				 </div>
					</div>
				</div>
        	</div>
        </div>
        <div class="modal-footer">
 			<button class="btn btn-success" ng-click="ok('20',project.id,pager.prevId,pager.nextId)" ng-disabled="!trial.content || trial.content.length<6"> 同意</button>
			<button class="btn btn-warning" ng-click="ok('21',project.id,pager.prevId,pager.nextId)" ng-disabled="myForm.$invalid"> 退回</button>
			<button class="btn btn-danger" ng-click="ok('40',project.id,pager.prevId,pager.nextId)" ng-disabled="myForm.$invalid"> 关闭</button>
        </div>
		</form>
        </script> 
        <script type="text/ng-template" id="qem-search.html">
        %{--//创建详情视图--}%
        <div class="modal-header">
			<h3 class="title">{{project.projectName}}</h3>
        </div>		
        <div class="modal-body">
			<div class="form-horizontal" >				
            	<g:render template="detail/form"></g:render>
        	</div>
        </div>       
        </script> 
        <script type="text/ng-template" id="qem-taskList.html">
	       <div >		
		     <g:render template="task/taskList"></g:render>
	       </div>
	    </script>
        <script type="text/ng-template" id="qem-historyTaskList.html">
	       <div >		
		     <g:render template="task/historyTaskList"></g:render>
	       </div>
	    </script>	    
<script type="text/ng-template" id="qem-taskDetail.html">	
		<div class="modal-header">            
			<div class="pull-right">
				<div class="btn-group">
				<button class="btn btn-default prev {{disabled_p1T()}}" ng-click="taskDetail(pagerT.prevId)">
					<span class="glyphicon glyphicon-chevron-left"></span>
				</button>
				<button class="btn btn-default next {{disabled_n1T()}}" ng-click="taskDetail(pagerT.nextId)">
					<span class="glyphicon glyphicon-chevron-right"></span>
				</button>
				</div>
			</div>
			<h3 class="title">{{task.projectName}}</h3>
        </div>
<%--<form name="myForm" role="form" novalidate>	--%>
	<div class="modal-body">			
			<div class="form-horizontal" >				
            	<g:render template="task/contractDetail"></g:render>
<%--				<div class="form-group" ng-if="task.runStatus==1">--%>
<%--					 <label for="content" class="col-sm-2 control-label">审核意见</label>--%>
<%--					 <div class="col-sm-10">--%>
<%--						<textarea name="content"  rows="4" class="form-control" placeholder="请输入意见！不少于6个字！" ng-model="trial.content" required></textarea>--%>
<%--					 </div>--%>
<%--				</div>--%>
        	</div>
     </div>
<%--	<div class="modal-footer" ng-if="task.runStatus==1">--%>
<%--	<button class="btn btn-success" ng-click="okT('20',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 同意</button>--%>
<%--	<button class="btn btn-warning" ng-click="okT('21',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 不同意</button>--%>
<%--	<button class="btn btn-danger" ng-click="okT('26',task.id,pagerT.prevId,pagerT.nextId)" ng-disabled="myForm.$invalid || trial.content.length<6"> 退回</button>--%>
<%--	</div>--%>
<%--</form>		--%>
</script>
	</div>
</body>
</html>