<div class="pull-right">
		<div class="btn-group">
			<a  class="btn btn-default" ui-sref="users">查看名单</a>
			<a  class="btn btn-primary" ui-sref="usersAdd">导入名单</a>
		</div>
	</div>
<div ng-controller="CoXsmdAddCtrl">	
	<div class="modal-header">
	<h3>学生名单导入</h3>
	</div>
	<div class="modal-body">
	<div class="col-sm-5 ">
		<label class="control-label">项目名称</label>
		<div><select class="form-control" 
    ng-model="project"
    ng-options="y.name for y in proList" ng-change="showCourse(project)"><option value="">请选择项目名称</option>
	</select></div>
	    <label for="user-list" class="control-label">学生列表</label>
	    <div>
	    	<g:textArea name="user-list"  rows="25" cols="30" class="form-control" placeholder="请将学生学号、姓名粘帖在这里" ng-model="users"/>
	    </div>
	    <div class="btn-group col-sm-offset-3" style="margin-top:20px">
	    <button class="btn btn-primary" ng-click="save()" ng-disabled="!project || !users"> 保存</button>
	    </div>
	</div>
	<div class="col-sm-7 ">
	    <label class="control-label">已添加的课程</label>
	    <table class="table table-bordered">
			<thead>
				<tr>
					<th>专业</th>
					<th>年级</th>
					<th>课程数</th>					
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in courseList | orderBy :'-grade'">				
				<td>{{item.major}}</td>
				<td>{{item.grade}}</td>
				<td>{{item.courseCount}}</td>
				</tr>
			</tbody>
		</table>
		<ul class="error-ul">
			<li class="text-info" >成功添加 {{results.successCount}} 名学生！</li>
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