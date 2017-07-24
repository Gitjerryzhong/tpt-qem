        <div class="modal-header">            
			<h3 class="title">{{contact.userName}}({{contact.userId}})</h3>
        </div>		
        <div class="modal-body">
				<div class="row">
					<div id="history-view" class="form-group">
					    <label for="" class="col-sm-2 control-label">审核意见</label>
					    <div class="col-sm-10" >
					    <ul class="form-control-static list-unstyled">
							<li ng-repeat="item in audits | orderBy:'id'">				
					    			{{dateFormat(item.date) | date : 'yyyy-MM-dd HH:mm:ss'}}{{item.userName}} {{actionText(item.action)}} {{item.content}}
							</li>			
						</ul>
					    </div>
					</div>	
				</div>
        </div>
        <div class="modal-footer">
			<button class="btn btn-default" ng-click="cancel()" > 关闭</button>
        </div>
