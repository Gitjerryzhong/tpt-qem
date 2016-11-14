<div ng-controller="vsummaryController" style="width: 930px;">	
	<div><h4>专家意见（包含：同意、不同意、弃权、未评审）</h4>
	<ul class="list-inline">
		<li><span class="badge agree">同意</span></li>
		<li><span class="badge reject">不同意</span></li>
		<li><span class="badge giveup">弃权</span></li>
		<li><span class="badge noreview">未评审</span></li>
	</ul>
	</div>	
		<div style="padding: 4px">
            <div style="float: right;">
           		<a style="margin-right:20px" href="/tms/QemProjectAdmin/export">导出Excel</a>
                <input type="text" ng-model="gridOptions.quickFilterText" placeholder="输入关键字进行过滤..."/>
            </div>
            <div style="padding: 4px;">
                <b>专家意见汇总</b> ({{rowCount}}/{{gridData.length}})
            </div>
            <div style="clear: both;"></div>
        </div>
        <div style="width: 100%; height: 550px;"
             ag-grid="gridOptions"
             class="ag-fresh ag-basic">
        </div>	
</div>