<div class="form-group">
	<label for="parentType" class="col-sm-1 control-label">大类</label>
    <div class="col-sm-3">
    <select class="form-control" name="parentType"
    ng-model="parentTypeId"
    ng-options="y.id as y.parentTypeName for y in qemParentTypes"  >	
	</select>
    </div>
    <label for="qemType" class="col-sm-2 control-label">项目类别</label>
    <div class="col-sm-5">
    <select class="form-control" name="qemType"
    ng-model="qemTypeId"
    ng-options="y.id as y.name for y in qemTypes | filter:{'parentType':{'id':parentTypeId}}"  >	
	</select>
    </div>   
    <a class="btn btn-primary" href="/tms/qem/download/{{qemTypeId}}">下 载</a> 
</div>