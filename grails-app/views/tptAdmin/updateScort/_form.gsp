<div class="modal-header">
<h4>{{paper.userName}}({{paper.userId}})</h4>
</div>
<div class="modal-body">
<div class="form-group">
    <label for="paperResult" class="col-sm-3 control-label">等级或分数</label>
    <div class="col-sm-9">
    	<input type="text" name="paperResult"  class="form-control" ng-model="paper.master_result"/>
    </div>
</div>
<div class="form-group">
    <label for="contactphone" class="col-sm-3 control-label">论文英文题目</label>
    <div class="col-sm-9">
    	<input type="text"  name="contactphone"  class="form-control" ng-model="paper.paperName_en" readonly/>
    </div>
</div>
<div class="form-group">
    <label for="contactphone" class="col-sm-3 control-label">论文中文题目</label>
    <div class="col-sm-9">
    	<input type="text"  name="contactphone"  class="form-control" ng-model="paper.paperName" readonly/>
    </div>
</div>
</div>
<div class="modal-footer">
		<button class="btn btn-primary" ng-click="ok()" > 保存</button>
       	<button class="btn btn-warning" ng-click="cancel()">退出</button>
</div>