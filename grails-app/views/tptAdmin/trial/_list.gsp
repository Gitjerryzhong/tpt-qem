<div class="modal-header"> 
	<div class="pull-right">
		<button  class="btn btn-default" ng-click="open()" ng-if="checkStatus>=4" ng-disabled="!checkSelected()">设置导师</button>
	</div>
    <div class="row" ng-if="checkStatus<4">
    	<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="1" ng-click="goTrial(1)"><label >未审核</label><span class="badge">{{pager.total[1]}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="2" ng-click="goTrial(2)"><label >审核通过</label><span class="badge">{{pager.total[2]}}</span></span>
		<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="3" ng-click="goTrial(3)"><label >审核不通过</label><span class="badge agree">{{pager.total[3]}}</span></span>
    </div> 
	<div class="row" ng-if="checkStatus>=4">
    	<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="4" ng-click="goTrial(4)"><label >未审核</label><span class="badge">{{pager.total[4]}}</span></span>
		<span class="col-md-2"><input type="radio" ng-model="checkStatus" value="5" ng-click="goTrial(5)"><label >审核通过</label><span class="badge">{{pager.total[5]}}</span></span>
		<span class="col-md-2"> <input type="radio" ng-model="checkStatus" value="7" ng-click="goTrial(7)"><label >审核不通过</label><span class="badge agree">{{pager.total[7]}}</span></span>
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
					<th>日期</th>
					<th>下载</th>
					<th ng-if="checkStatus>=4">导师</th>	
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
				<td>{{dateFormat(item.dateCreate)|date:'yyyy-MM-dd'}}</td>
				<td><a href="/tms/tptAdmin/downloadMaterial/{{item.id}}" class="button btn-info btn-xs" toolTip="下载附件">
				<span class="glyphicon glyphicon-download-alt " ></span></a> {{item.projectName}}</td>
				<td ng-if="checkStatus>=4">{{item.mentorName}}</td>
				 <td nowrap>
                                     
                      <button type="button" class="btn btn-warning btn-xs"  ng-click="details(item.id)" ng-show="auditAble()">
                          <span class="glyphicon glyphicon-pencil"></span> 审核
                      </button>
						<button type="button" class="btn btn-success btn-xs"  ng-click="details(item.id)" ng-show="!auditAble()">
                          <span class="glyphicon glyphicon-align-justify"></span> 详情
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