<html >
<head>
<title>显示PDF</title>
<asset:javascript src="grails-angularjs.js"/>
<asset:javascript src="pdf/pdf.combined.js"/>
<asset:javascript src="pdf/angular-pdf-viewer.min.js"/>
<asset:javascript src="tpt/showpdf.js"/>
<asset:stylesheet src="tpt/showpdf.css"/>
</head>
<body ng-app="pdfApp">
<div class="container"  ng-controller="AppCtrl">
<pdf-viewer
    delegate-handle="my-pdf-container"
    url="'${filename }'"
    scale="1"
    show-toolbar="true"
    headers="{ 'x-you-know-whats-awesome': 'EVERYTHING' }"></pdf-viewer>
</div>
	
</body>
</html>