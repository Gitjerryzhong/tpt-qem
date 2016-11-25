<div class="btn-group pull-right">
	<button class="btn btn-default" ng-click="save()" ng-if="!(task.runStatus>0 || !isEditAble())" >保存</button>
	<button class="btn btn-default" ng-click="apply()" ng-if="!(task.runStatus>0 && task.runStatus!=203)" >提交</button>			
	<button class="btn btn-default" ng-click="cancel()" ng-if="!(task.runStatus!=1)">撤销</button>				
	<button class="btn btn-default" ng-click="edit()" ng-if="!((task.runStatus>0 && task.runStatus!=203) || isEditAble())" >编辑</button>
</div>
<div id="mystep">
			<ol>
			<li>
				<div class="flowstep">
					<div class="step-name">{{currentStage().statuses[0].title}}</div>
					<div ng-class="{'first-step-no':getClass(0,'no'),'first-step-done':getClass(0,'done')}"><span ng-class="{'glyphicon glyphicon-ok small':getClass(0,'done')}">{{(getClass(0,'done'))?"":"1"}}</span></div>
				</div>
			</li>
			<li>
				<div class="flowstep">
					<div class="step-name">学院审核</div>
					<div ng-class="{'step-no':getClass(1,'no'),'step-done':getClass(1,'done')}"><span ng-class="{'glyphicon glyphicon-ok small':getTag(1,1),'glyphicon glyphicon-remove small':getTag(1,2)}">{{(getClass(1,'done'))?"":"2"}}</span></div>
				</div>
			</li>
			<li>
				<div class="flowstep">
					<div class="step-name">学校审核</div>
					<div ng-class="{'last-step-no':getClass(2,'no'),'last-step-done':getClass(2,'done')}"><span ng-class="{'glyphicon glyphicon-ok small':getClass(2,'done') ,'glyphicon glyphicon-remove small':task.runStatus==3}">{{(getClass(2,'done') || task.runStatus==3)?"":"3"}}</span></div>
				</div>
			</li>
			</ol>
</div>