       <div class="btn-toolbar" role="toolbar">
				 <div class="btn-group" role="group" aria-label="title"><h3 class="title">{{project.projectName}}</h3></div>
				 
				<div class="btn-group pull-right" role="group" aria-label="buttons">
					<button class="btn btn-default prev {{disabled_p1()}}" ng-click="details(pager.prevId)">
					<span class="glyphicon glyphicon-chevron-left"></span>
				</button>
				<button class="btn btn-default next {{disabled_n1()}}" ng-click="details(pager.nextId)">
					<span class="glyphicon glyphicon-chevron-right"></span>
				</button>
				</div>
        	</div>	
			<hr>
       
		<form name="myForm" role="form" novalidate>
			<div class="form-horizontal" >				
            	<g:render template="detail/form"></g:render>
				<div class="row">
					<div class="form-group">
		   				 <label for="content" class="col-sm-2 control-label">审核意见</label>
		    			 <div class="col-sm-10">
		    				<textarea name="content"  rows="4" class="form-control" placeholder="请输入意见！不少于6个字！" ng-model="trial.content" required></textarea>
		   				 </div>
					</div>
				</div>
        	</div>
        </div>
        <div class="modal-footer">
 			<button class="btn btn-success" ng-click="ok('20',project.id,pager.prevId,pager.nextId)" ng-disabled="!trial.content || trial.content.length<6"> 同意</button>
			<button class="btn btn-warning" ng-click="ok('21',project.id,pager.prevId,pager.nextId)" ng-disabled="myForm.$invalid"> 退回</button>
			<button class="btn btn-danger" ng-click="ok('40',project.id,pager.prevId,pager.nextId)" ng-disabled="myForm.$invalid"> 关闭</button>
        </div>
		</form>