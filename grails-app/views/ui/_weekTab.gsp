<ul class="nav nav-tabs week-tabs"><g:each var="i" in="${term.startWeek..term.endWeek}">
	<li<g:if test="${week == i || week == null && term.currentWeek == i}"> class="active"</g:if>><a href='#' data-toggle='tab' tabindex='-1'>${i}</a></li></g:each>
</ul>