<div class="form-group">
    <label for="parentType" class="col-sm-2 control-label">项目大类</label>
    <div class="col-sm-4">
    <select class="form-control" name="parentTypeId"
    ng-model="type.parentTypeId"
    ng-options="y.id as y.parentTypeName for y in parentTypes"    
	>
	</select>
	</div>
	<label for="name" class="col-sm-2 control-label">项目名称</label>
    <div class="col-sm-4">
    <input id="typeId" type="hidden" ng-model="typeId"/>  
    <input type="text" name="name" ng-model="type.name" class="form-control" placeholder="请项目类型名称" required>
	</div>
</div>

<div class="form-group">
    <label for="cycle" class="col-sm-2 control-label">周期</label>
    <div class="col-sm-4">
    <input type="number" name="cycle" ng-model="type.cycle" class="form-control" placeholder="请项目默认周期" required/>
	</div>
	<label for="actived" class="col-sm-2 control-label">是否屏蔽</label>
    <div class="col-sm-4 form-control-static">
    <input type="checkBox" name="actived" ng-model="type.actived"/>
	</div>
</div>

