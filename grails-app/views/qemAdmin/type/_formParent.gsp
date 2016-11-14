<div class="form-group">
    <label for="parentType" class="col-sm-2 control-label">项目大类</label>
    <div class="col-sm-8"> 
    <input id="parentTypeId" type="hidden" ng-model="parentTypeId"/>  
     <input type="text" name="name" ng-model="type.parentTypeName" class="form-control" placeholder="请项目类型名称">
	</div>	
	<button class="btn btn-info" ng-click="saveParentType()">{{actionTitle()}}</button>
</div>

