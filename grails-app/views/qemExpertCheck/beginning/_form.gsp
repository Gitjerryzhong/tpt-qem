		<table class="table table-hover">
			<thead>
				<tr>
					<th ng-click="orderBy('projectName')" style="cursor: pointer;width:15em">项目名称</th>
					<th  ng-click="orderBy('userName')" style="cursor: pointer;">负责人姓名</th>
					<th  ng-click="orderBy('currentTitle')" style="cursor: pointer;">职称</th>	
					<th  ng-click="orderBy('currentDegree')" style="cursor: pointer;">学历</th>	
					<th  ng-click="orderBy('departmentName')" style="cursor: pointer;">学院</th>		
					<th  ng-click="orderBy('projectLevel')" style="cursor: pointer;">项目等级</th>		
					<th ng-click="orderBy('type')" style="cursor: pointer;">项目类别</th>
					<th ng-click="orderBy('commitDate')" style="cursor: pointer;">申请日期</th>
					<th>{{action()}}</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in requests">	
				<td><a href="/tms/qemExpertCheck/download/{{item.id}}" class="button btn-info btn-xs" toolTip="下载附件">
				<span class="glyphicon glyphicon-download-alt " ></span></a> {{item.projectName}} </td>			
				<td><a class="hand" ng-click="related(item)" toolTip="查看历史申报情况" ng-if="item.otherElse">{{item.userName}}</a><span ng-if="!item.otherElse">{{item.userName}}</span></td>
				<td>{{item.currentTitle}}</td>
				<td>{{item.currentDegree}}</td>
				<td>{{item.departmentName}}</td>
				<td>{{levelText(item.projectLevel)}}</td>
				<td>{{item.type}}</td>
				<td>{{dateFormat(item.commitDate) | date : 'yyyy-MM-dd'}}</td>
				 <td> 
                      <button type="button" class="btn btn-warning btn-xs"  ng-click="details(item)" ng-show="!status">
                          <span class="glyphicon glyphicon-pencil"></span> 评审
                      </button>
                      <span ng-show="status">{{resultText(item.result)}}</span>
                  </td>
				</tr>
			</tbody>
		</table>