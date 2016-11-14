(function() {
    agGrid.initialiseAgGridWithAngular1(angular);

    coProjectApp.controller('psummaryController',['$rootScope','$scope','$http','$state','$filter',function($rootScope,$scope,$http,$state,$filter) {
    	
    	var vm=$scope;
    	var org_data = {};
    	vm.gridData=[];
        //必须
        var columnDefs = [
            {headerName: '', width: 30, checkboxSelection: true, suppressSorting: true,
                suppressMenu: true, pinned: true},
            {headerName: "协议名", field: "projectName",
                        cellRenderer: nameCellRenderer,width: 150, pinned: true},
             {headerName: "协议分类", field: "coTypeName", width: 80, pinned: true,filter:TypeFilter},
            {headerName: "项目分类", field: "coProTypeName", width: 80, pinned: true,filter:TypeFilter},
//            {headerName: "有效", field: "effective",width: 60, pinned: true,filter:TypeFilter},
//            {headerName: "双学位", field: "ifTowDegree",width: 60,filter:TypeFilter, pinned: true},
            {headerName: "学院", field: "departmentName",width: 60, pinned: true, filter:TypeFilter},
            {headerName: "校内专业", field: "majorName",width: 150, pinned: true, filter:TypeFilter},
            {headerName: "起始年份", field: "beginYear",width: 80, filter:TypeFilter},
            {headerName: "有效年份", field: "effeYearStr",width: 80, filter:TypeFilter},
            {headerName: "国外合作院校中文名", field: "collegeNameCn",width: 150, filter:TypeFilter},
            {headerName: "国外合作院校英文名", field: "collegeNameEn",width: 150, filter:TypeFilter},
            {headerName: "可衔接学位/学科/专业", field: "coDegreeOrMajor",width: 150, filter:"text"},
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
//            console.log(params.node.data.coProjectId);
            return "<a href='#' ng-click='projectDetail("+params.node.data.coProjectId+")'>"+params.value+"</a>"
        }
        
        $scope.projectDetail = function(itemId){
//	    	alert("here");
	    	$http({
				 method:'GET',
					url:"/tms/tptCoPrjView/getProjectDetail",
					params:{
						coProjectId: itemId 
					}
			 }).success(function(data) {
				 if(data!=null ){	
					 $rootScope.projectDetail=data.projectDetail;
//					 console.log($rootScope.projectDetail);
//					 $location.url('/details');
				 }
				 $state.go('copro-detail')
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
//                if(that.p=='projectLevel'){
//                	html =FILTER_TEMPLATE.replace("FILTER_NAME", $scope.levelText(type));
//                }else{
//                	html =FILTER_TEMPLATE.replace("FILTER_NAME", type);
//                }
                html =FILTER_TEMPLATE.replace("FILTER_NAME", type);
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
        
//    	当前有效专业
    	$scope.effeCurently = function(){
    		var data=$filter('effeCurrently')(org_data);
    		$scope.gridOptions.api.setRowData(data);
    		vm.gridData=data;
    	}
//        按协议名汇总
    	$scope.nameSummary = function(){
    		var data=$filter('uniName')(org_data);
    		$scope.gridOptions.api.setRowData(data);
    		vm.gridData=data;
    	}
//    	专业是否当前无效
    	$scope.ifUneffective = function(item){
    		var currentYear = new Date().getFullYear();
			var dif = currentYear-item.beginYear;
			var effe = (item.effeYears >> dif) & 1 ;
			return !effe;
    	}
//    	显示全部
    	$scope.showAll = function(){
    		$scope.gridOptions.api.setRowData(org_data);
    		vm.gridData=org_data;
    	}
        $http({
   		 method:'GET',
   			url:"/tms/tptCoPrjView/projectList"
   	 	}).success(function(data) {
//   	 		console.log(data.projectList);
   	 		$scope.gridOptions.api.setRowData(data.projectList);
   	 		vm.gridData=data.projectList;
   	 		org_data = data.projectList;
   	 	});
    	
    }]);

})();
