		<table class="table table-hover">
			<thead>
				<tr>
					<th ng-click="orderBy('projectName')" style="cursor: pointer;">项目名称</th>
					<th  ng-click="orderBy('userName')" style="cursor: pointer;">负责人姓名</th>
					<th  ng-click="orderBy('currentTitle')" style="cursor: pointer;">职称</th>	
					<th  ng-click="orderBy('currentDegree')" style="cursor: pointer;">学历</th>	
					<th  ng-click="orderBy('departmentName')" style="cursor: pointer;">学院</th>		
					<th  ng-click="orderBy('projectLevel')" style="cursor: pointer;">项目等级</th>		
					<th>项目类别</th>
					<th>{{action()}}</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in requests | orderBy:order">	
				<td><a href="/tms/qemExpertCheck/downloadTask/{{item.id}}" class="button btn-info btn-xs" toolTip="下载附件">
				<span class="glyphicon glyphicon-download-alt " ></span></a> {{item.projectName}} </td>			
				<td><a href="/tms/qemExpertCheck/relatedTask/{{item.id}}" target="_brank">{{item.userName}}</a></td>
				<td>{{item.currentTitle}}</td>
				<td>{{item.currentDegree}}</td>
				<td>{{item.departmentName}}</td>
				<td>{{levelText(item.projectLevel)}}</td>
				<td>{{item.type}}</td>
				 <td> 
                      <button type="button" class="btn btn-warning btn-xs"  ng-click="expertViewByStage(item)" ng-show="!status">
                          <span class="glyphicon glyphicon-pencil"></span> 评审
                      </button>
                      <span ng-show="status">{{resultText(item.result)}}</span>
                  </td>
				</tr>
			</tbody>
		</table>