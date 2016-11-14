<html>
<head>
<meta name="layout" content="main" />
<title>查看请假条</title>
<asset:stylesheet src="leave/show"/>
<asset:javascript src="leave/show"/>
</head>
<body>
    <div id="leaveRequest">
        <g:if test="${editable}">
        <div class="btn-toolbar">
            <div id="toolbar" class="btn-group pull-right">
                <button id="edit" class='btn btn-sm btn-default' title='编辑假条'>编辑</button>
                <button id="apply" class='btn btn-sm btn-default' title='提交申请'>提交</button>
                <button id="cancel" class='btn btn-sm btn-default' title='撤销申请'>撤销</button>
                <button id="delete" class='btn btn-sm btn-default' title='删除假条'>删除</button>
                <button id="reportBack" class='btn btn-sm btn-default' title='销假'>销假</button>
            </div>
        </div>
        </g:if>
        <div class="row">
            <label class="col-md-2">类型</label><div class="col-md-2 value">${type}</div>
        </div>
        <div class="row">
            <label class="col-md-2">请假人</label><div class="col-md-2 value">${applicant}</div>
        </div>
        <div class="row">
            <label class="col-md-2">请假时间</label><div class="col-md-4 value">${dateApplied}</div>
        </div>
        <g:if test="${approver}">
        <div class="row">
            <label class="col-md-2">审核人</label><div class="col-md-2 value">${approver}</div>
        </div>
        <div class="row">
            <label class="col-md-2">审核时间</label><div class="col-md-4 value">${dateApproved}</div>
        </div>
        </g:if>
        <div class="row">
            <label class="col-md-2">事由</label> <div class="col-md-8 value">${reason}</div>
        </div>
        <div class="row">
            <label class="col-md-2">条目</label>
            <ul class="col-md-8 value">
            <g:each var="item" in="${items}">
                <li>
                	<g:message code="tms.date.week" args="${item.week}"/>
                	<g:if test="${item.dayOfWeek != null}">
                		<g:message code="tms.date.dayOfWeek.${item.dayOfWeek}"/>
                	</g:if>
                	<g:if test="${item.arrangement != null}">
                		<g:message code="tms.date.dayOfWeek.${item.arrangement.dayOfWeek}"/>
                		<g:message code="tms.arrangement.sections" args="${[item.arrangement.startSection, item.arrangement.endSection]}"/>
                		${item.arrangement.courseClasses.toList()[0].name}
                	</g:if>
                </li>
            </g:each>
            </ul>
        </div>
        <div class="row">
            <label class="col-md-2">状态</label><div class="col-md-1 value"><span id="statusName" class="label"> ${statusName}</span></div>
        </div>
        <form id="form" method="post" style="display: none">
            <input id="status" type="hidden" name="status" value="${status}" />
        </form>
    </div>
    <jq:jquery>
	<g:if test="${editable}">
	updateToolbar();
	</g:if>
	updateStatus();
    </jq:jquery>
</body>
</html>