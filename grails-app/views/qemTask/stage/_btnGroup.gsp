<%--<div class="btn-group pull-right">--%>
	<button class="btn btn-default" ng-click="saveStage()" ng-if="!disableAction() && saveAble()">保存</button>
	<button class="btn btn-default" ng-click="applyStage()" ng-if="!disableAction() && saveAble()" >提交</button>			
	<button class="btn btn-default" ng-click="cancelStage()" ng-if="!disableAction() && cancelAble()">撤销</button>				
<%--	<button class="btn btn-default" ng-click="editStage()" ng-disabled="disableAction() " >编辑</button>--%>
<%--</div>--%>
