<div class="modal-header"> 
	<h4><strong>项目名称：</strong>{{projectName}}</h4>
</div>
<form name="myForm" role="form" novalidate>
<div class="modal-body">
		<table class="table table-bordered" id="mytable">
				<tr>
					<td colspan="2"><strong>学院意见</strong></td>
					<td colspan="2" >{{collegeAudit}}</td>
				</tr>
				<tr>
					<td colspan="2"><strong>专家意见</strong></td>
					<td colspan="2" id="finalResult"><span><input type="radio" name="result" ng-model="review.result" value="0" required>同意</span><span> <input type="radio" name="result"  ng-model="review.result" value="1" required>不同意</span><span><input type="radio" name="result"  ng-model="review.result" value="2" required>弃权</span></td>
				</tr>
				<tr>
					<td colspan="2"><strong>评审意见</strong></td>
					<td colspan="2"><textarea rows="4" cols="70" ng-model="review.content" placeholder="请输入意见！不少于6个字！" required></textarea></td>
				</tr>
			</tbody>
		</table>
</div>
<div class="modal-footer">
<input type="button" class="btn btn-default" ng-click="saveTask()" value="保存" ng-disabled="myForm.$invalid || review.content.length<6">
<input type="button" class="btn btn-default" ng-click="commitTask()" value="提交" ng-disabled="myForm.$invalid || review.content.length<6">
</div>
</form>