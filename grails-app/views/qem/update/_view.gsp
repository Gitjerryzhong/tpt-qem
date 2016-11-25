<div class="panel panel-default" >
<div class="panel-heading title">基本信息</div>
<div class="row">
	<div class="form-group" >
	<label class="col-sm-2 control-label">变更内容</label>
		<div class="col-sm-10 form-control-static">
				<p>{{typesText}}</p>
		</div>
	</div>
	<div class="form-group">
		 <label class="col-sm-2 control-label" >变更理由</label>
		 <div class="col-sm-10 form-control-static" ><p>{{updateView.memo}}</p>
		 </div>
	</div>
</div>
</div>

<div class="panel panel-default" >
<div class="panel-heading title">变更项目信息</div>
<table class="table table-bordered" id="updateInfo">
			<thead>
				<tr>
					<th colspan="2" >原项目信息</th>
					<th colspan="2" >变更为</th>
				</tr>
				<tr>
					<th style="width:6em">标题</th>
					<th style="width:30em">内容</th>
					<th style="width:6em">标题</th>
					<th style="width:30em">内容</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-if="hasUpdateType('3')">
					<td class="oldInfo">项目名称</td>
					<td class="oldInfo">{{taskView.projectName}}</td>
					<td><span >项目名称</span></td>
					<td>{{updateView.projectName}}</td>
				</tr>
				<tr ng-if="hasUpdateType('7')">
					<td class="oldInfo">参与人</td>
					<td class="oldInfo">{{taskView.members}}</td>
					<td>参与人</td>
					<td>{{updateView.members}}</td>
				</tr>
				<tr ng-if="hasUpdateType('2')">
					<td class="oldInfo">中期预期</td>
					<td class="oldInfo">{{taskView.expectedMid}}</td>
					<td>中期预期</td>
					<td>{{updateView.expectedMid}}</td>
				</tr>
				<tr ng-if="hasUpdateType('2')">
					<td class="oldInfo">结题预期</td>
					<td class="oldInfo">{{taskView.expectedEnd}}</td>
					<td>结题预期</td>
					<td>{{updateView.expectedEnd}}</td>
				</tr>
				<tr ng-if="hasUpdateType('4')">
					<td class="oldInfo">研究内容</td>
					<td class="oldInfo">{{taskView.projectContent}}</td>
					<td>研究内容</td>
					<td>{{updateView.projectContent}}</td>
				</tr>
				<tr ng-if="hasUpdateType('6')">
					<td class="oldInfo">成果形式</td>
					<td class="oldInfo">{{taskView.expectedGain}}</td>
					<td>成果形式</td>
					<td>{{updateView.expectedGain}}</td>
				</tr>
				<tr ng-if="hasUpdateType('8')">
					<td class="oldInfo"></td>
					<td class="oldInfo"></td>
					<td>其他变更</td>
					<td>{{updateView.others}}</td>
				</tr>
			</tbody>
</table>
</div>
<div class="panel panel-default form-body" >
<div class="panel-heading title">附 件</div>
<div class="form-group" ng-if="declarations">
<label for="doc" class="col-md-2 control-label">原申报书</label>
<div class="col-md-10 form-control-static">
<ul>
	<li ng-repeat="filename in declarations" >
			<span>{{getFileName(filename)}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group"  ng-if="fileList">
<label class="col-sm-2 control-label">申报书变更为</label>
<div class="col-sm-10 form-control-static">
	<ul>
		<li ng-repeat="item in fileList"><span>{{getFileName(item)}}</span></li>
	</ul>	
</div>			 
</div>
</div>