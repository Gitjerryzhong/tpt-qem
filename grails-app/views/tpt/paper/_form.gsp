<div class="form-group">
    <label for="paperResult" class="col-sm-2 control-label">论文等级或分数</label>
    <div class="col-sm-10">
    	<input type="text" name="paperResult"  class="form-control" placeholder="合作院校本科论文等级或分数*" ng-model="paper.master_result" required/>
    </div>
</div>
<div class="form-group">
    <label for="contactphone" class="col-sm-2 control-label">论文英文题目</label>
    <div class="col-sm-10">
    	<input type="text"  name="contactphone"  class="form-control" placeholder="本科论文英文题目 *" ng-model="paper.paperName_en" required/>
    </div>
</div>
<div class="form-group">
    <label for="contactphone" class="col-sm-2 control-label">论文中文题目</label>
    <div class="col-sm-10">
    	<input type="text"  name="contactphone"  class="form-control" placeholder="本科论文中文题目 *" ng-model="paper.paperName" required/>
    </div>
</div>
<div class="form-group">
    <label for="user-list" class="col-sm-2 control-label">论文英文摘要</label>
    <div class="col-sm-10">
    	<g:textArea name="user-list"  rows="4" class="form-control" placeholder="本科论文英文摘要" ng-model="paper.content_en"/>
    </div>
</div>
<div class="form-group">
    <label for="user-list" class="col-sm-2 control-label">论文中文摘要</label>
    <div class="col-sm-10">
    	<g:textArea name="user-list"  rows="4" class="form-control" placeholder="本科论文中文摘要（200字以内）" ng-model="paper.content"/>
    </div>
</div>
