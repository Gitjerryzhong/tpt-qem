<div class="modal-header"> 
<%--	<h4>请按评分标准评分</h4>  --%>
	<h4><strong>项目名称：</strong>{{projectName}}</h4>
</div>
<form name="myForm" role="form" novalidate>
<div class="modal-body">
		<table class="table table-bordered" id="mytable">
<%--			<thead>--%>
<%--				<tr>--%>
<%--					<th>评分项目</th>--%>
<%--					<th>满分</th>--%>
<%--					<th>细则</th>	--%>
<%--					<th>评分输入</th>--%>
<%--				</tr>--%>
<%--			</thead>--%>
			<tbody id="listBody">
<%--				<tr>				--%>
<%--				<td rowspan="2" width="250px" ><strong>课程的内容（该课程是否提出了新的观点、方法或采用新的研究手段，研究内容是否完整、充实）</strong></td>--%>
<%--				<td width="5px" >20</td>--%>
<%--				<td><ol>--%>
<%--					<li>有明显的学术创新和特色20-17</li>--%>
<%--					<li>有一定学术创新16-13</li>--%>
<%--					<li>创新性较少12-7</li>--%>
<%--					<li>没有创新6-0</li>--%>
<%--				</ol></td>	--%>
<%--				<td width="30px" ><input type="number" ng-model="score[0]" size="3" max="20" min="0" required></td> --%>
<%--				</tr>--%>
<%--				<tr>		--%>
<%--				<td  width="5px" >20</td>--%>
<%--				<td><ol>--%>
<%--					<li>阐述全名，知识结构完整，概念明确，逻辑严密20-17</li>--%>
<%--					<li>主要方面全名阐述，次要方面略有欠缺16-13</li>--%>
<%--					<li>主要方面基本阐述，某些方面有欠缺12-7</li>--%>
<%--					<li>主要方面有遗漏或只是结构混乱6-0</li>--%>
<%--				</ol></td>	--%>
<%--				<td width="30px" ><input type="number" ng-model="score[1]" size="3" max="20" min="0" required></td>  --%>
<%--				</tr>--%>
<%--				<tr>				--%>
<%--				<td width="250px" ><strong>课程的研究思路和方法（该项课题的研究思路和设计的研究路线是否有清晰可行性）</strong></td>--%>
<%--				<td width="5px" >30</td>--%>
<%--				<td><ol>--%>
<%--					<li>清晰可行性30-24</li>--%>
<%--					<li>较合理可行23-19</li>--%>
<%--					<li>不够合理，欠完整18-8</li>--%>
<%--					<li>模糊，不可行7-0</li>--%>
<%--				</ol></td>	--%>
<%--				<td width="30px" ><input type="number" ng-model="score[2]" size="3" max="30" min="0" required></td> --%>
<%--				</tr>--%>
<%--				<tr>				--%>
<%--				<td width="250px" ><strong>课程组的研究基础与研究条件（该项课题负责人及成员的研究基础、研究水平及所具有的研究条件能否满足课题的研究需要）</strong></td>--%>
<%--				<td width="5px" >15</td>--%>
<%--				<td><ol>--%>
<%--					<li>基础雄厚、条件优越15-11</li>--%>
<%--					<li>有一定基础，条件尚可10-7</li>--%>
<%--					<li>基础、条件一般6-3</li>--%>
<%--					<li>基础、条件很差2-0</li>--%>
<%--				</ol></td>	--%>
<%--				<td width="30px" ><input type="number" ng-model="score[3]" size="3" max="15" min="0" required></td> --%>
<%--				</tr>--%>
<%--				<tr>				--%>
<%--				<td width="250px" ><strong>课程申请经费的分配和使用（经费的分配是否合理、使用是否充分）</strong></td>--%>
<%--				<td width="5px" >15</td>--%>
<%--				<td><ol>--%>
<%--					<li>使用重复、分配合理15-11</li>--%>
<%--					<li>使用较充分、分配较合理10-7</li>--%>
<%--					<li>使用不充分、分配不合理6-0</li>--%>
<%--				</ol></td>	--%>
<%--				<td width="10px" ><input type="number" ng-model="score[4]" size="3" max="15" min="0" required></td> --%>
<%--				</tr>--%>
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
<input type="button" class="btn btn-default" ng-click="save()" value="保存" ng-disabled="myForm.$invalid || review.content.length<6">
<input type="button" class="btn btn-default" ng-click="commit()" value="提交" ng-disabled="myForm.$invalid || review.content.length<6">
</div>
</form>