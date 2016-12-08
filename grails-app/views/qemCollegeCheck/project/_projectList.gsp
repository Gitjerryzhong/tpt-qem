<div >	
	<div class="pull-right" style="margin-bottom:10px">
			<a style="margin-right:20px" href="/tms/QemCollegeCheck/exportAttach_All/{{bn}}" toolTip="批量导出全部附件"><span class="glyphicon glyphicon-download-alt"></span>导出全部附件</a>
			<a style="margin-right:20px" href="/tms/QemCollegeCheck/exportAttach/{{bn}}" toolTip="批量导出通过学院评审项目附件"><span class="glyphicon glyphicon-download-alt"></span>导出通过学院评审项目附件</a> 	
			<a href="/tms/QemCollegeCheck/export/{{bn}}" toolTip="分类导出项目清单"><span class="glyphicon glyphicon-download-alt"></span>导出Excel</a> 
	</div>	
		<table class="table table-hover">
			<thead>
				<tr>
					<th style="width:17em">项目名称</th>
					<th style="width:5em">负责人</th>
					<th style="width:6em">职称</th>	
					<th style="width:4em">学历</th>	
					<th style="width:6em">岗位</th>
					<th style="width:6em">电话</th>	
					<th style="width:5em">专业</th>		
					<th style="width:4em">等级</th>		
					<th style="width:10em">类别</th>
					<th style="width:7em">申请日期</th>
					<th style="width:4em">操作</th>
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