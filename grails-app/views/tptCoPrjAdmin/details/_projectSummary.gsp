 <div ng-controller="psummaryController" style="width: 930px;">
 <div class="btn-group">	
				<button class="btn btn-default"  ng-click="effeCurently()">当前有效专业</button>
				<button class="btn btn-default"  ng-click="nameSummary()">按协议名称汇总</button>			
				<button class="btn btn-default"  ng-click="showAll()">显示所有</button>				
			</div>
       <div style="padding: 4px">
            <div style="float: right;">
			<a style="margin-right:20px" href="#">导出汇总表</a>
                <input type="text" ng-model="gridOptions.quickFilterText" placeholder="输入关键字进行过滤..."/>
            </div>
            <div style="padding: 4px;">
                <b>已立项项目汇总</b> ({{rowCount}}/{{gridData.length}})
            </div>
            <div style="clear: both;"></div>
        </div>
        <div style="width: 100%; height: 550px;"
             ag-grid="gridOptions"
             class="ag-fresh ag-basic">
        </div>	
    </div>