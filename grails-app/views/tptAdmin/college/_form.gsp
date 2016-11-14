<div class="form-horizontal" ng-controler="CollegeAddCtrl" >				
	<div class="form-group">
	    <div class="col-sm-3"><label for="name" class="control-label">国外大学名称</label></div>
	    <div class="col-sm-6">
	    	<input id="collegeId" type="hidden" ng-model="collegeId"/>
	    	<g:textField name="name"  class="form-control" placeholder="国外大学名称" ng-model="college.name" ng-required="true"/>
	    </div>
	    <div class="col-sm-1">
	    <button class="btn btn-primary" ng-click="saveCollege()">保存</button>
	    </div>
	</div>
</div>