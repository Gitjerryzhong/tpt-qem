<div class="form-group">
    <label for="name" class="col-sm-2 control-label">协议分类名称</label>
    <div class="col-sm-8">
    	<g:textField name="name"  class="form-control" placeholder="协议分类名称" ng-model="coType.name" ng-required="true"/>
    </div>
    <button class="btn btn-info" ng-click="saveCoType()">{{actionTitle()}}</button>
</div>
