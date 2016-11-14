<table class="table table-hover">
	<thead>
		<tr>
			<th style="width:3em">序号</th>
			<th class="hand" ng-click="orderBy('projectName')" style="width:15em">项目名称</th>
			<th class="hand" ng-click="orderBy('projectLevel')" style="width:4em">项目等级</th>
			<th class="hand" ng-click="orderBy('projectLevel')" style="width:15em">项目类型</th>
			<th class="col-sm-2 hand" ng-click="orderBy('commitDate')" style="width:9em">申请日期</th>	
			<th class="hand" style="width:7em">项目状态</th>	
			<th class="hand" style="width:3em">删除</th>	
		</tr>
	</thead>
	<tbody id="listBody">
		<tr ng-repeat="item in myProjects | filter:remindFilter |orderBy:order">		
		<td >{{$index+1}}</td>		
		<td><a ng-href="qem/showDetail/{{item.id}}">{{item.projectName}}</a></td>
		<td>{{levelText(item.projectLevel)}}</td>
		<td>{{item.qemTypeName}}</td>
		<td>{{dateFormat(item.commitDate) | date : 'yyyy-MM-dd'}}</td>
		<td>{{getStatus(item)}}</td>
		<td><span class="glyphicon glyphicon-remove" ng-click="deleteItem(item.id)" style="cursor: pointer;" ng-show="!item.commit"></span></td>
<%--		<td class="col-sm-2">{{collegeStatusText(item.collegeStatus)}}</td>--%>
<%--		<td class="col-sm-2">{{reviewStatusText(item.reviewStatus)}}</td>--%>
		</tr>
	</tbody>

</table>
<%--使用下推表格的技术展示，不过对还要展示1对多的日志信息会比较麻烦，还是改用常用的技术点击查询详情的方法--%>
<%--<accordion close-others="true" >--%>
<%--      --%>
<%--<div class="panel panel-info">--%>
<%--  <div class="panel-heading"><strong>--%>
<%--        <div class="row ">--%>
<%--            <span class="col-md-2">项目名称<span class="sortorder" ></span></span>--%>
<%--            <span class="col-md-2">项目等级<span class="sortorder"></span></span>--%>
<%--            <span class="col-md-2">申请日期<span class="sortorder" ></span></span>--%>
<%--            <span class="col-md-1">提交<span class="sortorder"></span></span>--%>
<%--        	<span class="col-md-2">学院意见<span class="sortorder" ></span></span>--%>
<%--        	<span class="col-md-2">评审结果<span class="sortorder"></span></span>        --%>
<%--            </div></strong>--%>
<%--  </div>--%>
<%--   <accordion-group is-open="status.open" class="panel panel-sm" ng-repeat="item in myProjects | orderBy:order:reverse" ng-click="showAudit(item.id)">--%>
<%--      <accordion-heading ><small>--%>
<%--         <i class="pull-right glyphicon" ng-class="{'glyphicon-chevron-down': status.open, 'glyphicon-chevron-right': !status.open}"></i></small>--%>
<%--            --%>
<%-- 		<div class="row"><small>--%>
<%--            <span class="col-md-2">{{item.projectName}}</span>--%>
<%--            <span class="col-md-2">{{levelText(item.projectLevel)}}</span>--%>
<%--            <span class="col-md-2">{{dateFormat(item.commitDate) | date : 'yyyy-MM-dd'}}</span>--%>
<%--            <span class="col-md-1">{{item.commit?'已':'未'}}</span>--%>
<%--            <span class="col-md-2">{{collegeStatusText(item.collegeStatus)}}</span>--%>
<%--            <span class="col-md-2">{{reviewStatusText(item.reviewStatus)}}</span>--%>
<%--            </small>--%>
<%--            </div>--%>
<%--      </accordion-heading>--%>
<%--    <div class="row">--%>
<%--      <span class="col-md-12">--%>
<%--          <span class="input-group "><span class="input-group-addon" >姓名</span><label class="form-control"  >{{item.name}}</label><span class="input-group-addon">职称</span><label class="form-control"  >{{item.currentTitle}}</label>--%>
<%--                <span class="input-group-addon">学位</span><label class="form-control"  >{{item.currentDegree}}</label><span class="input-group-addon">Email</span><label class="form-control"  >{{item.specailEmail}}</label>         --%>
<%--   		  </span>--%>
<%--   	  </span>--%>
<%--   	  <span class="col-md-12">--%>
<%--          <span class="input-group "><span class="input-group-addon">性别</span><label class="form-control"  >{{item.sex}}</label><span class="input-group-addon">职务</span><label class="form-control"  >{{item.position}}</label>--%>
<%--               <span class="input-group-addon">电话</span><label class="form-control"  >{{item.phoneNum}}</label>       --%>
<%--   		  </span>--%>
<%--   	  </span>--%>
<%--   <span class="col-md-12">--%>
<%--          <span class="input-group "><span class="input-group-addon">部门</span><label class="form-control"  >{{item.departmentName}}</label>  <span class="input-group-addon">专业</span><label class="form-control"  >{{item.majorName}}</label> --%>
<%--          	<span class="input-group-addon">学科</span><label class="form-control"  >{{clearNull(item.discipline)}}</label><span class="input-group-addon">方向</span><label class="form-control"  >{{clearNull(item.direction)}}</label>--%>
<%--   		  </span>--%>
<%--   </span>   --%>
<%--   <span class="col-md-12">--%>
<%--          <span class="input-group "><span class="input-group-addon">项目名称</span><label class="form-control"  >{{item.projectName}}</label><span class="input-group-addon">项目类型</span><label class="form-control"  >{{item.qemTypeName}}</label>--%>
<%--          		<span class="input-group-addon">项目等级</span><label class="form-control"  >{{levelText(item.projectLevel)}}</label>        --%>
<%--   			</span>--%>
<%--    </span>--%>
<%--    <span class="col-md-12">--%>
<%--    	<span class="input-group-addon">预期成果</span> <pre class="col-md-12">{{item.expectedGain}}</pre></span>   --%>
<%--	    <div class="form-group">--%>
<%--		    <label for="" class="col-sm-2 control-label">操作日志</label>--%>
<%--		    <div class="col-sm-10" >--%>
<%--		    <ul class="form-control-static list-unstyled">--%>
<%--				<li ng-repeat="audit in audits">				--%>
<%--		    			{{dateFormat(audit.date) | date : 'yyyy-MM-dd HH:mm:ss'}}{{audit.userName}} {{actionText(audit.action)}} {{audit.content}}--%>
<%--				</li>			--%>
<%--			</ul>--%>
<%--		    </div>--%>
<%--		</div>--%>
<%--     </div>--%>
<%--	</accordion-group>--%>
<%----%>
<%--</div>--%>
<%--</accordion>--%>