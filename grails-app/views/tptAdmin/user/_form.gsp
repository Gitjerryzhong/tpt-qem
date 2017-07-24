<div class="modal-header">           
    <h3 class="modal-title"> 批量导入出国名单</h3>
</div>
<div class="modal-body">
	<div class="row" style="margin-left:1em">
	  <div class="col-sm-5">
		<div class="form-group">
			<select class="form-control" 
		    ng-model="project"
		    ng-options="y.name for y in proList" ><option value="">请选择项目名称</option>
			</select>
<%--			<span class="help-block">注：15级以前的可以不选</span>--%>
		</div>
		<div class="form-group">
		    <label for="user-list" class="control-label">学生列表</label>
		    <div>
		    	<g:textArea name="user-list"  rows="15" cols="30" class="form-control" placeholder="请将学生学号、姓名粘帖在这里" ng-model="users"/>
		    </div>
		</div>
		</div>
		<div class="col-sm-6 ">
		<ul class="pre-scrollable">
			<li class="text-info" ng-if="!error && results.successCount">成功添加 {{results.successCount}} 名学生！</li>
			<li class="text-danger" ng-if="error">导入失败！请纠正后重新导入：</li>
<%--			<li class="text-danger" ng-repeat="item in errors">{{item}} </li>--%>
<%--			<li class="text-info" ng-if="results">以下是成绩自助打印系统反馈的消息：</li>--%>
<%--			<li class="text-info" ng-if="results">成功添加 {{results.successCount}} 名学生！</li>--%>
			<li class="text-danger" ng-if="results.duplicateList">重复录入名单：</li>
			<li class="text-danger" ng-repeat="item in results.duplicateList">
				{{item.ID}}：{{item.NAME}}
			</li>
			<li class="text-danger" ng-if="results.unmatchList">姓名学号不匹配名单：</li>
			<li class="text-danger" ng-repeat="item in results.unmatchList">
				{{item.ID}}：{{item.NAME}}
			</li>
			<li class="text-danger" ng-if="results.prjUnMatchList">项目不匹配名单：</li>
			<li class="text-danger" ng-repeat="item in results.prjUnMatchList">
				{{item.ID}}：{{item.NAME}}
			</li>
			<li class="text-danger" ng-if="results.notDualList">不是出国专业名单：</li>
			<li class="text-danger" ng-repeat="item in results.notDualList">
				{{item.ID}}：{{item.NAME}}
			</li>
		</ul>
		</div>
	</div>
</div>
<div class="modal-footer">
		<button class="btn btn-primary" ng-click="ok()"> 保存</button>
       	<button class="btn btn-warning" ng-click="cancel()">退出</button>
</div>
