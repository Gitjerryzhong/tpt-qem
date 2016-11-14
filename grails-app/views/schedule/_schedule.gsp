<g:set var="spans" value="${[
    1  : [span: "4", text: '上午'],
	5  : [span: "5", text: '下午'],
	10 : [span: "4", text: '晚上']
]}" />
<div class="schedule">
	<g:render template="/ui/weekTab"/>
	<table>
		<thead>
			<tr>
				<th class="week" colspan="2" data-week="${term.currentWeek}">第${term.currentWeek}周</th>
				<th class="day">星期一</th>
				<th class="day">星期二</th>
				<th class="day">星期三</th>
				<th class="day">星期四</th>
				<th class="day">星期五</th>
				<th class="day">星期六</th>
				<th class="day">星期日</th>
			</tr>
		</thead>
		<tbody><g:each var="i" in="${1..13}">
			<tr><g:if test="${spans[i]}">
				<td class="c1" rowspan="${spans[i].span}">${spans[i].text}</td></g:if>
				<td class="c2">${i}</td><g:each var="j" in="${1..7}">
				<td id="a_${i}_${j}" class="arr"></td></g:each>
			</tr></g:each>
		</tbody>
	</table>
</div>