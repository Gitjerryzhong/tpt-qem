<div class="modal-header"> 
	<h4>设置专家专业类别</h4>          
   	
</div>
<div class="modal-body">
		<div class="row">
    	<span class="col-md-2"> <label >专家：</label></span>
		<span class="col-md-2" ng-repeat="y in expertsSelected">{{y.name}}</span>
    </div>
    <div class="row">
    	<span class="col-md-2"><label >可审专业</label></span>
		<span class="col-md-2" ng-repeat="item in majorTypes"><input type="checkbox" ng-model="majortypeSelected[$index]" > {{item | nullFilter}}</span>
    </div>  		
</div>
<div class="modal-footer">
<input type="button" class="btn btn-default" ng-click="ok()" value="确定">
<input type="button" class="btn btn-default" ng-click="cancel()" value="取消">
</div>