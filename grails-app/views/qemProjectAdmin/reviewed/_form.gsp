<%--<div class="modal-header"> --%>
<%--	<h4>请按评分标准评分</h4>  --%>
<%--</div>--%>
<form name="myForm" role="form" novalidate>
<div class="modal-body">
		<table class="table table-bordered" id="mytable">			
			<tbody id="listBody">
				<tr>
					<td><strong>项目编号</strong></td>
					<td><input type="text" ng-model="item.sn" size="10" ng-maxlength="10" required></td>
					<td><strong>批准金额（万元）</strong></td>
					<td><span>省级经费<input type="number" ng-model="item.budget0" size="3" max="999" min="0"></span>
					<span>校级经费<input type="number" ng-model="item.budget1" size="3" max="999" min="0"></span>
					<span>学院配套<input type="number" ng-model="item.budget2" size="3" max="999" min="0"></span></td>
				</tr>			
				<tr>
					<td colspan="2"><strong>最终结果</strong></td>
					<td colspan="2" id="finalResult"><span><input type="radio" name="result" ng-model="item.result" value="0" required>同意立项 </span><span><input type="radio" name="result"  ng-model="item.result" value="1" required>不予立项</span>
					<span><input type="radio" name="result"  ng-model="item.result" value="2" required>同意立项，需修改内容</span></td>
				</tr>
				<tr>
					<td colspan="2"><strong>评审委员会综合意见</strong></td>
					<td colspan="2"><textarea rows="4" cols="70" ng-model="item.content" required></textarea></td>
				</tr>
			</tbody>
		</table>
		<input type="button" class="btn btn-default" ng-click="commit(item)" value="提交" ng-disabled="myForm.$invalid">
</div>
<%--<div class="modal-footer">--%>
<%--<input type="button" class="btn btn-default" ng-click="save()" value="保存" ng-disabled="myForm.$invalid">--%>
<%--<input type="button" class="btn btn-default" ng-click="commit()" value="提交" ng-disabled="myForm.$invalid">--%>
<%--</div>--%>
</form>