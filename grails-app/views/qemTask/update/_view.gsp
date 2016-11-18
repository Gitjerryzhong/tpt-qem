<div class="panel panel-default form-body" >
<div class="panel-heading title">基本信息</div>
<h4 ng-if="commitAction && myForm.$invalid" class="Emphasis">请正确填写下面红色标注的内容再重新提交！</h4>
<div class="form-group" >
<label class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && noChecked() }">变更内容</label>
	<div class="col-sm-10 form-control-static">
			<span  ng-repeat="item in updateTypes | filter:{'selected':true}" style="margin-right:15px;"> {{item.name }}；</span>
	</div>
</div>
<div class="form-group" style="border-bottom:0px;">
	 <label class="col-sm-2 control-label" ng-class="{'Emphasis': commitAction && !newData.memo }">变更理由</label>
	 <div class="col-sm-10 form-control-static" >{{newData.memo}}
	 </div>
</div>
</div>

<div class="panel panel-default form-body" >
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
				<tr ng-if="updateTypes[2].selected">
					<td class="oldInfo">项目名称</td>
					<td class="oldInfo">{{task.projectName}}</td>
					<td><span >项目名称</span></td>
					<td>{{newData.projectName}}</td>
				</tr>
				<tr ng-if="updateTypes[6].selected">
					<td class="oldInfo">参与人</td>
					<td class="oldInfo">{{task.memberstr}}</td>
					<td>参与人</td>
					<td><span ng-repeat="y in members">{{y.name}};</td>
				</tr>
				<tr ng-if="updateTypes[1].selected">
					<td class="oldInfo">中期预期</td>
					<td class="oldInfo">{{task.expectedMid}}</td>
					<td>中期预期</td>
					<td>{{newData.expectedMid}}</td>
				</tr>
				<tr ng-if="updateTypes[1].selected">
					<td class="oldInfo">结题预期</td>
					<td class="oldInfo">{{task.expectedEnd}}</td>
					<td>结题预期</td>
					<td>{{newData.expectedEnd}}</td>
				</tr>
				<tr ng-if="updateTypes[3].selected">
					<td class="oldInfo">研究内容</td>
					<td class="oldInfo">{{task.projectContent}}</td>
					<td>研究内容</td>
					<td>{{newData.projectContent}}</td>
				</tr>
				<tr ng-if="updateTypes[5].selected">
					<td class="oldInfo">成果形式</td>
					<td class="oldInfo">{{task.expectedGain}}</td>
					<td>成果形式</td>
					<td>{{newData.expectedGain}}</td>
				</tr>
				<tr ng-if="updateTypes[7].selected">
					<td class="oldInfo"></td>
					<td class="oldInfo"></td>
					<td>其他变更</td>
					<td>{{newData.others}}</td>
				</tr>
			</tbody>
</table>
</div>
<div class="panel panel-default form-body" >
<div class="panel-heading title">附 件</div>
<div class="form-group" ng-if="fileList">
<label for="doc" class="col-md-2 control-label">原申报书</label>
<div class="col-md-10 ">
<ul class="form-control-static">
	<li ng-repeat="filename in declarations" >
			<span>{{filename}}</span>
	</li>
</ul>
</div>
<label for="doc" class="col-md-2 control-label" ng-if="contracts">原合同</label>
<div class="col-md-10 ">
<ul class="form-control-static">
	<li ng-repeat="filename in contracts" >
			<span>{{filename}}</span>
	</li>
</ul>
</div>
</div>
<div class="form-group" style="border-bottom:0px;" ng-if="updateQueue">
	<label class="col-sm-2 control-label">申报书</label>
	<div class="col-sm-10">
		<ur>
			<li ng-repeat="item in updateQueue">{{item.file.name}}</li>
		</ur>	
	</div>			 
</div>
</div>