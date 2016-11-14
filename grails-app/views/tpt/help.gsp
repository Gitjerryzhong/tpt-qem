<html>
<head>
<title>帮助</title>
<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="tpt/help.js"/>
<asset:stylesheet src="bootstrap-3.0.0/css/bootstrap.css"/>
</head>
<body ng-app="helpApp">
<div id="tptHelp" class="container"  ng-controller="HelpCtrl" >
		<div class="modal-header">           
            <h3 class="modal-title"><strong>{{title[step]}}</strong></h3>
        </div>		
        <div class="modal-body">			
			<div class="row">
				<div class="col-md-1 btn" style="margin-top:25%;font-size:40;"><span class="glyphicon glyphicon-chevron-left" ng-click="goStep(-1)"></span></div>
				<div class="col-md-10"><img src="/tms/help/step{{step}}.jpg"/></div>
				<div class="col-md-1 btn" style="margin-top:25%;font-size:40;"><span class="glyphicon glyphicon-chevron-right" ng-click="goStep(1)"></span></div>
			</div>
        </div>
		
	</div>
</body>
</html>