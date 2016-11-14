<html>
<head>
<meta name="layout" content="main" />
<title>假条列表</title>
</head>
<body>
    <div id="leaveList">
    	<g:if test="${!leaveRequests}">
    		<p>暂无请假数据。</p>
    	</g:if>
    	<g:else>
        <table id="list" class="table">
            <thead>
                <tr>
                    <th>查看</th>
                    <th>类型</th>
                    <th>事由</th>
                    <th>提交时间</th>
                    <th>状态</th>
                    <th>批准人</th>
                    <th>批准时间</th>
                </tr>
            </thead>
        <g:each var="leaveRequest" in="${leaveRequests}">
            <tr>
                <td><g:link mapping="item" id="${leaveRequest.id}">查看</g:link></td>
                <td><g:message code="tms.leave.type.${leaveRequest.type}"/></td>
                <td>${leaveRequest.reason.length() > 25 ? leaveRequest.reason[0..22] + "..." : leaveRequest.reason}
                <td>${leaveRequest.dateModified.format("yyyy-MM-dd HH:mm")}</td>
                <td><g:message code="tms.leave.status.${leaveRequest.status}"/></td>
                <td>${leaveRequest.approver?.name}</td>
                <td>${leaveRequest.dateApproved?.format("yyyy-MM-dd HH:mm")}</td>
            </tr>
        </g:each>
        </table>
        </g:else>
    </div>
</body>
</html>