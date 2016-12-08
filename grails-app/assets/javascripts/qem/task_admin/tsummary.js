(function() {
    agGrid.initialiseAgGridWithAngular1(angular);

    tAdminApp.controller('tsummaryController',['$rootScope','$scope','$http','$state','$filter',function($rootScope,$scope,$http,$state,$filter) {
    	var vm=$scope;
    	vm.gridData=[];
    	vm.college_static={}
    	$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
        //必须
        var columnDefs = [
            {headerName: '', width: 30, checkboxSelection: true, suppressSorting: true,
                suppressMenu: true, pinned: true},
            {headerName: "项目名", field: "projectName",
                        cellRenderer: nameCellRenderer,width: 150, pinned: true},
             {headerName: "编号", field: "sn", width: 80, pinned: true,filter:'text'},
            {headerName: "类别", field: "type", width: 150, pinned: true,filter:TypeFilter},
            {headerName: "姓名", field: "userName",width: 80, pinned: true},
            {headerName: "学院", field: "shortName",width: 80,filter:TypeFilter, pinned: true},
            {headerName: "建设情况", field: "status", filter:TypeFilter,
            	cellRenderer: statusCellRenderer, width: 80},
            {headerName: "立项", field: "beginYear",width: 60, filter:TypeFilter},
            {headerName: "结项", field: "endDate",width: 60, filter:TypeFilter,cellRenderer: endDateCellRenderer},
            {headerName: "拟中期", field: "expectedMid",width: 60, filter:TypeFilter},
            {headerName: "拟结项", field: "expectedEnd",width: 60, filter:TypeFilter},
            {headerName: "已中期", field: "hasMid",width: 80, filter:TypeFilter,cellRenderer: hasMidCellRenderer},
            {headerName: "等级", field: "projectLevel", filter:TypeFilter,
            	cellRenderer: levelCellRenderer, width: 80},
            {headerName: '延期次数', field: "delay", width:60},	
//            {headerName: "学历", field: "currentDegree",width: 60, filter:TypeFilter},
//            {headerName: "岗位", field: "position",width: 100, filter: 'text'},
//            {headerName: "省级拨款", field: "fundingProvince",width: 60, filter: 'number'},
//            {headerName: "校级拨款", field: "fundingUniversity",width: 60, filter: 'number'},
//            {headerName: "院级配套", field: "fundingCollege",width: 60, filter: 'number'},
            {headerName: '备注', field: "memo", width:100}
        ];

        vm.gridOptions = {
        	angularCompileRows: true,
            columnDefs: columnDefs,//必须
            rowData: [],//必须
            rowSelection: 'multiple',
            enableColResize: true,//必须
            enableSorting: true,
            enableFilter: true,
            groupHeaders: true,
            suppressContextMenu: true,
            suppressMenuMainPanel: true,
            suppressMenuColumnPanel: true,
            rowHeight: 22,
            onModelUpdated: onModelUpdated,
            suppressRowClickSelection: true //必须，避免点击行就选中
        };


        function onModelUpdated() {
            var model = $scope.gridOptions.api.getModel();
//            var totalRows = vm.gridData.length;
            var processedRows = model.getRowCount();
            $scope.rowCount = processedRows.toLocaleString() ;//+ ' / ' + totalRows.toLocaleString();
        }

        function nameCellRenderer (params) {
//            console.log(params.node.data.id);
            return "<a href='#' ng-click='taskDetail("+params.node.data.id+")'>"+params.value+"</a>"
        }
        
        function levelCellRenderer (params) {
        	var LEVEL = {
	    			"1": "校级",
	    			"2": "省级",
	    			"3": "国家级"	    		
	    		};
	    	return LEVEL[params.value];
        }
        function statusCellRenderer (params) {
        	return $scope.statusTT(params.value);
        }
        
        function endDateCellRenderer (params) {
        	return $filter('date')(params.value,'yyyy-MM');
        }
        function hasMidCellRenderer (params) {
        	return params.value?'是':'否';
        }
        $scope.details = function(itemId){
//	    	alert("here");
	    	$http({
				 method:'GET',
					url:"/tms/QemProjectAdmin/getRequestDetail",
					params:{
						form_id: itemId 
					}
			 }).success(function(data) {
				 if(data!=null ){	
					 $rootScope.project=data.form;
					 $rootScope.audits=data.audits;
					 $rootScope.fileList = data.fileList;
//					 $location.url('/details');
				 }
				 $state.go('details')
			 });  
	    }
     
        var FILTER_TITLE =
            '<div style="text-align: center; background: lightgray; width: 100%; display: block; border-bottom: 1px solid grey;">' +
            '<b>TITLE_NAME</b>' +
            '</div>';
        var FILTER_TEMPLATE =
            '<label style="padding-left: 4px;">' +
            '<input type="checkbox" checked id="' +
            'RANDOM'+'"/>' +
            'FILTER_NAME' +
            '</label>';
        function TypeFilter() {
        }

        TypeFilter.prototype.init = function (params) {
            this.filterChangedCallback = params.filterChangedCallback;
            this.model={};
            this.p=params.column.colId;
            this.param=params;
        };

        TypeFilter.prototype.getGui = function () {
        	var that = this;
        	var types=$filter('uniKey')(vm.gridData,that.p);
            var eGui = document.createElement('div');
            var eInstructions = document.createElement('div');
            eInstructions.innerHTML = FILTER_TITLE.replace('TITLE_NAME', '筛选条件');
            eGui.appendChild(eInstructions);

//            增加全选功能
            var allSpan =  document.createElement('div');
            var ahtml = "";
            html =FILTER_TEMPLATE.replace("FILTER_NAME", '全选');
            allSpan.innerHTML = html;
            var aCheckbox = allSpan.querySelector('input');
            aCheckbox.addEventListener('click', function () {
            	types.forEach(function (type){
            		that.model[type]=aCheckbox.checked;
            		 var check = document.getElementById(type);
            		 console.log(check);
            		 check.checked =aCheckbox.checked;
            	})
                that.filterChangedCallback();
            });
            eGui.appendChild(allSpan);
//            console.log(that.p);
            types.forEach(function (type) {
                var eSpan = document.createElement('div');
                var html = "";
//              特别处理须转换显示方式的列
                if(that.p=='projectLevel'){
                	html =FILTER_TEMPLATE.replace("FILTER_NAME", $scope.levelText(type));
                }else if(that.p=='status'){
                	html =FILTER_TEMPLATE.replace("FILTER_NAME", $scope.statusTT(type));
                }else if(that.p=='hasMid'){
                	html =FILTER_TEMPLATE.replace("FILTER_NAME", type?'是':'否');
                }else{
                	html =FILTER_TEMPLATE.replace("FILTER_NAME", type);
                }
                html =html.replace("RANDOM", type);
                console.log(html);
                eSpan.innerHTML = html;
                that.model[type]=true;
                var eCheckbox = eSpan.querySelector('input');
                eCheckbox.addEventListener('click', function () {
                	that.model[type] = eCheckbox.checked;
                    that.filterChangedCallback();
                });
//                console.log(that.model);
                eGui.appendChild(eSpan);
            });
            
            return eGui;
        };

        TypeFilter.prototype.doesFilterPass = function (params) {
        	var that = this;
            var rowType = params.data[that.p];
            var model = this.model;
//            var passed = true;

            return model[rowType];
        };

        TypeFilter.prototype.isFilterActive = function () {
           return true;
        };
        vm.statusText = function(status){
        	var STATUS = {
	    			"10": "在研",
	    			"20": "结项",
	    			"32": "终止"	,  
	    			"33": "中止"	, 
	    		};
	    	return STATUS[status];
        	} 
        vm.groupByProjectLevelAndStatus = function(level,status){
        	var result =$filter('filter')(vm.gridData,{'projectLevel':level,'status':status});
//        	console.log(result);
        	return result.length
        	} 
        vm.groupBy = function(field){
        	var colleges =$filter('uniKey')(vm.gridData,field);
        	var colleges_count=[];
        	var colleges_countAll={'c1_10':0,'c1_20':0,'c1_3233':0,'c1':0,'c2_10':0,'c2_20':0,'c2_3233':0,'c2':0};
        	angular.forEach(colleges,function(item){
        		var college_data = $filter('filter2_0')(vm.gridData,field,item);
        		var count10=$filter('filter')(college_data,{'projectLevel':1,'status':10}).length;
        		var count20=$filter('filter')(college_data,{'projectLevel':1,'status':20}).length;
        		var count3233=$filter('filter')(college_data,{'projectLevel':1,'status':32}).length+
        					$filter('filter')(college_data,{'projectLevel':1,'status':33}).length;
        		var count10_=$filter('filter')(college_data,{'projectLevel':2,'status':10}).length;
        		var count20_=$filter('filter')(college_data,{'projectLevel':2,'status':20}).length;
        		var count3233_=$filter('filter')(college_data,{'projectLevel':2,'status':32}).length+
        					$filter('filter')(college_data,{'projectLevel':2,'status':33}).length;
        		var count_item ={'college':item,'c1_10':count10,'c1_20':count20,'c1_3233':count3233,'c1':count10+count20+count3233,'c2_10':count10_,'c2_20':count20_,'c2_3233':count3233_,'c2':count10_+count20_+count3233_};
        		colleges_countAll.c1_10+=count_item.c1_10;
        		colleges_countAll.c1_20+=count_item.c1_20;
        		colleges_countAll.c1_3233+=count_item.c1_3233;
        		colleges_countAll.c1+=count_item.c1;
        		colleges_countAll.c2_10+=count_item.c2_10;
        		colleges_countAll.c2_20+=count_item.c2_20;
        		colleges_countAll.c2_3233+=count_item.c2_3233;
        		colleges_countAll.c2+=count_item.c2;
        		
        		colleges_count.push(count_item);
        	})	;
        	return {count:colleges_count,count_All:colleges_countAll};
        }
        vm.countFor =function(type){
        	vm.report_table=type;
        	switch(type){
			case 2: vm.college_static=vm.groupBy('departmentName');
					break;
			case 3: vm.college_static=vm.groupBy('type');
					break;
			}
        }
        $http({
   		 method:'GET',
   			url:"/tms/qemTaskAdmin/showTasks"
   	 	}).success(function(data) {
   	 		var tasklist=data.taskList;
   	 		tasklist=$filter('mkIndex')(tasklist);
   	 		tasklist=$filter('orderBy')(tasklist,'index',false);
   	 		$scope.gridOptions.api.setRowData(tasklist);
   	 		vm.gridData=tasklist;
   	 		$scope.refreshTaskList(tasklist);
//   	 		$rootScope.actived=[0,0,0,0,0,0,0,0,0];
//   	 		$rootScope.actived[7]=1;
   	 	});
    }]);

})();
