<div ng-controller="tsummaryController" style="width: 930px;">
	<div style="padding: 4px">
            <div style="float: right;">
<%--            <a style="margin-right:20px" href="/tms/qemTaskAdmin/exportAttach">导出附件</a> --%>
<%--			<a style="margin-right:20px" href="/tms/qemTaskAdmin/exportTask/0">按学院导出</a>--%>
<%--			<a style="margin-right:20px" href="/tms/qemTaskAdmin/exportTask/1">按类别导出</a>--%>
				<span class="dropdown">
				  <button id="showReport" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    查看报表
				   <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" role="menu" aria-labelledby="showReport">
				    <li><a style="margin-right:20px" ng-click="report_table=0">全关闭</a></li>
				    <li><a style="margin-right:20px" ng-click="report_table=1">汇总统计</a></li>
				    <li><a style="margin-right:20px" ng-click="countFor(2)">按学院统计</a></li>
				    <li><a style="margin-right:20px" ng-click="countFor(3)">按类别统计</a></li>
				  </ul>
				</span>
				<span class="dropdown">
				  <button id="dLabel" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    导出
				   <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
				    <li><a style="margin-right:20px" href="/tms/qemTaskAdmin/exportAttach">导出附件</a></li>
				    <li><a style="margin-right:20px" href="/tms/qemTaskAdmin/exportTask/0">按学院导出</a></li>
				    <li><a style="margin-right:20px" href="/tms/qemTaskAdmin/exportTask/1">按类别导出</a></li>
				    <li><a style="margin-right:20px" href="/tms/qemTaskAdmin/report/1">分学院项目统计</a></li>
				    <li><a style="margin-right:20px" href="/tms/qemTaskAdmin/report/2">分类别项目统计</a></li>
				  </ul>
				</span>
            </div>
            <div style="padding: 4px;">
                <b>已立项项目汇总</b> ({{rowCount}}/{{gridData.length}})
                <input type="text" ng-model="gridOptions.quickFilterText" placeholder="输入关键字进行过滤..."/>
            </div>
            <div style="clear: both;"></div>
        </div>
        <div style="width: 100%; height: 360px;"
             ag-grid="gridOptions"
             class="ag-fresh ag-basic" ng-show="!report_table">
        </div>
        <div class="row" style="margin-top:10px">
			<div class="col-sm-4" ng-if="report_table==1">
				<div class="panel panel-info" >				
           			<table class="table table-striped">
           				<thead>
				<tr style="background-color:#d9edf7;">
					<th>项目等级</th>
					<th>执行情况</th>
					<th>数量</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr  ng-repeat="item in gridData | filter:{'projectLevel':1} | uniKey:'status' ">	
				<td>校级</td>				
				<td>{{statusText(item)}}</td>
				<td>{{groupByProjectLevelAndStatus(1,item)}}</td>			
				</tr>
				<tr  ng-repeat="item in gridData | filter:{'projectLevel':2} | uniKey:'status' ">	
				<td>省级</td>				
				<td>{{statusText(item)}}</td>
				<td>{{groupByProjectLevelAndStatus(2,item)}}</td>			
				</tr>
				<tr >	
				<td>合计</td>				
				<td></td>
				<td>{{gridData.length}}</td>			
				</tr>
			</tbody>
           			</table>
       			</div>
			</div>
			<div ng-class="{'col-sm-10':report_table==3,'col-sm-8':report_table==2}"  ng-if="report_table==2 || report_table==3">
				<div class="panel panel-info" >				
           			<table class="table table-striped table-bordered">
           				<thead>
				<tr style="background-color:#d9edf7;">
					<th rowspan="2">{{report_table==2?'学院':'项目类别'}}</th>
					<th colspan="4" class="text-center">校级</th>
					<th colspan="4" class="text-center">省级</th>
				</tr>
				<tr style="background-color:#d9edf7;">
					<th>在研</th>
					<th>结项</th>
					<th>终/中止</th>
					<th>小计</th>
					<th>在研</th>
					<th>结项</th>
					<th>终/中止</th>
					<th>小计</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<tr ng-repeat="item in college_static.count">
					<td>{{item.college}}</td>
					<td>{{item.c1_10}}</td>
					<td>{{item.c1_20}}</td>
					<td>{{item.c1_3233}}</td>
					<td>{{item.c1}}</td>
					<td>{{item.c2_10}}</td>
					<td>{{item.c2_20}}</td>
					<td>{{item.c2_3233}}</td>
					<td>{{item.c2}}</td>
				</tr>
				<tr >
					<td>合计</td>
					<td>{{college_static.count_All.c1_10}}</td>
					<td>{{college_static.count_All.c1_20}}</td>
					<td>{{college_static.count_All.c1_3233}}</td>
					<td>{{college_static.count_All.c1}}</td>
					<td>{{college_static.count_All.c2_10}}</td>
					<td>{{college_static.count_All.c2_20}}</td>
					<td>{{college_static.count_All.c2_3233}}</td>
					<td>{{college_static.count_All.c2}}</td>
				</tr>
			</tbody>
           			</table>
       			</div>
			</div>
		</div>	
		
</div>