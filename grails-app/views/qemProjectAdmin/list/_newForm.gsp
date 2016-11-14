<div ng-controller="basicController" style="width: 930px;">
	<div style="padding: 4px">
            <div style="float: right;">
            <a style="margin-right:20px" href="/tms/qemProjectAdmin/exportAttach/{{bnselected}}">导出附件</a> 
			<a style="margin-right:20px" href="/tms/qemProjectAdmin/exportReqs">导出汇总表</a>
                <input type="text" ng-model="gridOptions.quickFilterText" placeholder="输入关键字进行过滤..."/>
            </div>
            <div style="padding: 4px;">
                <b>各学院申报情况汇总</b> ({{rowCount}}/{{gridData.length}})
            </div>
            <div style="clear: both;"></div>
        </div>
        <div style="width: 100%; height: 550px;"
             ag-grid="gridOptions"
             class="ag-fresh ag-basic">
        </div>	
		
</div>
