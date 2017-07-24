 <div ng-controller="psummaryController" style="width: 930px;"  class="row">
 <div class="btn-group">	
				<button class="btn btn-default"  ng-click="effeCurently()">当前有效专业</button>
				<button class="btn btn-default"  ng-click="nameSummary()">按协议名称汇总</button>			
				<button class="btn btn-default"  ng-click="showAll()">显示所有</button>				
			</div>
       <div style="padding: 4px">
            <div style="float: right;">
			<span class="dropdown">
				  <button id="dLabel" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    导出
				   <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
				    <li><a style="margin-right:20px" href="/tms/tptCoPrjAdmin/export/0">按名称汇总导出</a></li>
				    <li><a style="margin-right:20px" href="/tms/tptCoPrjAdmin/export/1">有效协议导出</a></li>
				    <li><a style="margin-right:20px" href="/tms/tptCoPrjAdmin/export/2">导出全部</a></li>
				  </ul>
				</span>
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