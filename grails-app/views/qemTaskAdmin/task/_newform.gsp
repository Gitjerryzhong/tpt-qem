<form name="newProject" role="form" novalidate>
<div class="form-group">
<h4 class="col-sm-offset-4 col-sm-4 control-label" style="text-align:center;padding-bottom:15px"><strong>新建任务书</strong></h4>
</div>
<div class="form-group">
<label class="col-sm-2 control-label">项目名称</label>
<div class="col-sm-4">
    	<input type="text" name="projectName"  class="form-control" placeholder="新建项目名称 *" ng-model="task.projectName" required/>
    </div>
 <label class="col-sm-2 control-label">负责人</label>
   	<div class="col-sm-2">
   		<span class="input-group "><span class="input-group-addon">姓名</span><input type="text"  ng-model="teacherName" class="form-control" ng-change="findTeacher(teacherName)" required/></span> 
    </div>   
    <div class="col-sm-2">
   		<span class="input-group "><span class="input-group-addon">工号</span>
   			<select class="form-control" 
		    ng-model="task.teacherId"
		    ng-options="y.id as (y.id +'|'+y.name + '|'+ y.sex) for y in teachers" ></select>
   		</span> 
    </div> 
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">项目编号</label>
   <div class="col-sm-2">
    	<input type="text" name="sn"  class="form-control" placeholder="项目编号 *" ng-model="task.sn" required/>
    </div>
    <label class="col-sm-2 control-label">批准金额（万）</label>
    <div class="col-sm-2">
    <span class="input-group "><span class="input-group-addon" >省级：</span><input type="number" ng-model="task.fundingProvince" class="form-control" max="999" min="0"></span>
    </div>
    <div class="col-sm-2">
	<span class="input-group "><span class="input-group-addon" >校级：</span><input type="number" ng-model="task.fundingUniversity" class="form-control" max="999" min="0"></span>
	</div>
    <div class="col-sm-2">
	<span class="input-group "><span class="input-group-addon" >学院：</span><input type="number" ng-model="task.fundingCollege" class="form-control"  max="999" min="0"></span>
    </div>   
</div>
<div class="form-group">  
	 <label for="beginYear" class="col-sm-2 control-label">立项日期</label>
     <div class="col-sm-2">
    	<input type="text" name="beginYear"  class="form-control" placeholder="立项日期 *" ng-model="task.beginYear" required/>
    </div>
    <label for="expectedMid" class="col-sm-2 control-label">中期预期</label>
     <div class="col-sm-2">
    	<input type="text" name="expectedMid"  class="form-control" placeholder="四位年份*" ng-model="task.expectedMid" required/>
    </div>
    <label for="expectedEnd" class="col-sm-2 control-label">结题预期</label>
 	<div class="col-sm-2">
    	<input type="text" name="expectedEnd"  class="form-control" placeholder="四位年份*" ng-model="task.expectedEnd" required/>
    </div>
</div>
<div class="form-group">  
	 <label for="department" class="col-sm-2 control-label">单位</label>
	 <div class="col-sm-2">
	 <select class="form-control" name="departmentId"
    ng-model="task.departmentId"
    ng-options="y.id as y.name for y in departments"  >	
	</select>
    </div>
     <label for="qemType" class="col-sm-2 control-label">项目类别</label>
    <div class="col-sm-2">
    <select class="form-control" name="qemType"
    ng-model="task.qemTypeId"
    ng-options="y.id as y.name for y in qemTypes"  >	
	</select>
    </div>
    <label for="projectLevel" class="col-sm-2 control-label">项目等级</label>
    <div class="col-sm-2">
    <select class="form-control" name="qemType"
    ng-model="task.projectLevel"
    ng-options="y.id as y.name for y in projectLevels"  >	
	</select>
    </div>
</div>
</form>
