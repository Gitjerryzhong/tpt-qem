<html >
<head>
<meta name="layout" content="main" />
<title>2+2出国学生学位申请资格审批系统</title>

<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="angularjs/angular-ui-router.js"/>

<asset:javascript src="angularjs/ui-bootstrap-tpls-0.11.0.js"/>
<asset:javascript src="angularjs/angular-locale_zh-cn.js"/>
<asset:stylesheet src="tpt/tpt.css"/>
<asset:javascript src="tpt/mentorCheck.js"/>

</head>
<body >
<div  ng-app="checkApp"  class="container" ng-controller="TrialCtrl">
		<div class="content">
			<div  ui-view=""> </div>
		</div>
	<script type="text/ng-template" id="tpt-trial.html">
			<div class="form-horizontal" >				
            	<g:render template="trial/list"></g:render>
        	</div>
	</script>
    <script type="text/ng-template" id="tpt-audit.html">
       <div class="form-horizontal" >				
           <g:render template="trial/form"></g:render>
   	 </div>
     </script>
</div>
</body>
</html>