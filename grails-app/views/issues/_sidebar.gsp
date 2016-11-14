<div class="list-group">
	<g:link class="list-group-item ${it.active==0?'active':''}" uri="/issues" >
		<span class="badge">${it.total}</span>所有问题
	</g:link>
	<t:hasRole role="ROLE_ISSUE_ADMIN">
	<g:link class="list-group-item ${it.active==-2?'active':''}" uri="/issues?invisible">
		<span class="badge">${it.invisible}</span>隐藏的问题
	</g:link>
	</t:hasRole>
	<g:link class="list-group-item ${it.active==-1?'active':''}" uri="/issues?personal">
		<span class="badge">${it.user}</span>我的问题
	</g:link>
</div>
<div class="list-group">
	<g:link class="list-group-item ${it.active==1?'active':''}" uri="/issues?type=1">
		<span class="badge">${it.t1?:0}</span>错误报告
	</g:link>
	<g:link class="list-group-item ${it.active==2?'active':''}" uri="/issues?type=2">
		<span class="badge">${it.t2?:0}</span>功能改进
	</g:link>
	<g:link class="list-group-item ${it.active==3?'active':''}" uri="/issues?type=3">
		<span class="badge">${it.t3?:0}</span>新功能
	</g:link>
</div>
<div class="list-group">
	<g:link class="list-group-item" uri="/issues/new">新建问题</g:link>
	<g:link class="list-group-item" uri="/issues/help">使用帮助</g:link>
</div>