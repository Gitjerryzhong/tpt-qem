<div class="modal-header"> 
	<div class="row" ng-if="checkStatus>=4">
    	<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="4" ng-click="goTrial(4)"><label >未审核</label><span class="badge">{{pager.total[4]}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="5" ng-click="goTrial(5)"><label >审核通过</label><span class="badge">{{pager.total[5]}}</span></span>
		<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="6" ng-click="goTrial(7)"><label >审核不通过</label><span class="badge agree">{{pager.total[7]}}</span></span>
		<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="6" ng-click="goTrial(6)"><label >已关闭</label><span class="badge agree">{{pager.total[6]}}</span></span>
    </div>  
</div>
	<div class="modal-body">		
		<table class="table table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>学号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>班级</th>
					<th>电话</th>
					<th style="width:9em">email</th>
					<th>日期</th>
					<th>下载</th>
					<th>操作</th>				
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in requests">
				<td nowrap><input type="checkbox" ng-model="item.selected">{{$index+1}}</td>				
				<td>{{item.userId}}</td>
				<td>{{item.userName}}</td>
				<td>{{item.sex}}</td>
				<td>{{item.adminClassName}}</td>
				<td>{{item.phoneNumber}}</td>
				<td>{{item.email}}</td>
				<td>{{dateFormat(item.dateCreate)|date:'yyyy-MM-dd'}}</td>
				<td><a href="/tms/tptMentorCheck/downloadPapers/{{item.id}}" class="button btn-info btn-xs" toolTip="下载附件">
				<span class="glyphicon glyphicon-download-alt " ></span></a> {{item.projectName}}</td>
				 <td nowrap>
                                     
                      <button type="button" class="btn btn-warning btn-xs"  ng-click="open(item)" ng-show="auditAble()">
                          <span class="glyphicon glyphicon-pencil"></span> 审核
                      </button>
                  </td>
				</tr>
			</tbody>
		</table>
		
		<ul class="pager">
		<li class="prev-page {{disabled_prev()}}"><a href="#" ng-click="previous()" >上一页</a></li>
		<li class="next-page {{disabled_next()}}"><a href="#" ng-click="next()">下一页</a></li>
		</ul>	
	</div>