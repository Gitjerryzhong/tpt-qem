<div class="form-group">
    <label for="name" class="col-sm-2 control-label">项目分类名称</label>
    <div class="col-sm-3">
    	<g:textField name="name"  class="form-control" placeholder="项目名称，通常以国别命名" ng-model="coType.name" ng-required="true"/>
    </div>
    <label for="name" class="col-sm-1 control-label">备注</label>
    <div class="col-sm-5">
    	<g:textField name="memo"  class="form-control" placeholder="如有特别说明，请备注" ng-model="coType.memo"/>
    </div>
    <div class="col-sm-1"><button class="btn btn-info" ng-click="saveCoType()">{{actionTitle()}}</button></div>
</div>
