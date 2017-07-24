
<%--<div class="form-group">--%>
<%--    <label for="content" class="col-sm-2 control-label">审核意见</label>--%>
<%--    <div class="col-sm-10">--%>
<%--    	<g:textArea name="content"  rows="5" class="form-control" placeholder="请输入意见！审核不同过的必须说明理由！" ng-model="trial.content"/>    	--%>
<%--    </div>--%>
<%--</div>--%>
<%----%>
 %{--//创建详情视图--}%
        <div class="modal-header">            
			<h3 class="title">{{contact.userName}}({{contact.userId}})</h3>
        </div>
		<form name="myForm" role="form" novalidate>
        <div class="modal-body">
			<div class="form-horizontal" >				
				<div class="row">
					<div  class="form-group" ng-if="audits">
					    <label for="" class="col-sm-2 control-label">审核意见</label>
					    <div class="col-sm-9" >
					    <ul class="form-control-static list-unstyled">
							<li ng-repeat="item in audits | orderBy:'id'">				
					    			{{dateFormat(item.date) | date : 'yyyy-MM-dd'}} {{item.userName}} {{actionText(item.action)}} {{item.content}}
							</li>			
						</ul>
					    </div>
					</div>	
					<div class="form-group">
		   				 <label for="content" class="col-sm-2 control-label">审核意见</label>
		    			 <div class="col-sm-9">
		    				<textarea name="content"  rows="4" class="form-control" placeholder="请输入意见！审核不同过的必须说明理由！" ng-model="trial.content" required></textarea>
		   				 </div>
					</div>
				</div>
        	</div>
        </div>
        <div class="modal-footer">
<%--             <button class="btn btn-default" onclick="history.go(-1)"> 返回</button>--%>
 			<button class="btn btn-success" ng-click="ok('30')"> 通过</button>
			<button class="btn btn-warning" ng-click="ok('31')" ng-disabled="myForm.$invalid"> 不通过</button>
			<button class="btn btn-default" ng-click="cancel()" > 关闭</button>
        </div>
		</form>