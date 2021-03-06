(function() {
    agGrid.initialiseAgGridWithAngular1(angular);

    pAdminApp.controller('basicController',['$rootScope','$scope','$http','$state','$filter',function($rootScope,$scope,$http,$state,$filter) {
    	var vm=$scope;
    	vm.gridData=[];
    	$scope.projectLevels=[{"id":"1","name":"校级"},{"id":"2","name":"省级"},{"id":"3","name":"国家级"}];
        //必须
        var columnDefs = [
            {headerName: '', width: 30, checkboxSelection: true, suppressSorting: true,
                suppressMenu: true, pinned: true},
            {headerName: "项目名", field: "projectName",
                        cellRenderer: nameCellRenderer,width: 150, pinned: true},
            {headerName: "类别", field: "type", width: 150, pinned: true,filter:TypeFilter},
            {headerName: "姓名", field: "userName",width: 80, filter:'text', pinned: true},
            {headerName: "职称", field: "currentTitle",width: 80, filter:TypeFilter, pinned: true},
            {headerName: "学院", field: "departmentName",width: 80,filter:TypeFilter, pinned: true},
            {headerName: "等级", field: "projectLevel", filter:TypeFilter,
            	cellRenderer: levelCellRenderer, width: 80},
            {headerName: "学历", field: "currentDegree",width: 60, filter:TypeFilter},
            {headerName: "岗位", field: "position",width: 100, filter:TypeFilter},
            {headerName: "学科门类", field: "discipline",width: 80, filter:TypeFilter},
            {headerName: "一级专业", field: "major",width: 150, filter:'text'},
            {headerName: '学院意见', field: "collegeAudit", width:200}
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
            return "<a href='#' ng-click='details("+params.node.data.id+")'>"+params.value+"</a>"
        }
        
        function levelCellRenderer (params) {
        	var LEVEL = {
	    			"1": "校级",
	    			"2": "省级",
	    			"3": "国家级"	    		
	    		};
	    	return LEVEL[params.value];
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
        
        $http({
   		 method:'GET',
   			url:"/tms/qemProjectAdmin/requestListAll",
   			params:{
   				bn:$scope.bnselected //在上一层controller的$scope中
   			}
   	 	}).success(function(data) {
//   	 		var types=$filter('uniKey')(data.requestList,'type');
//   	 		console.log(types);
   	 		$scope.gridOptions.api.setRowData(data.requestList);
   	 		vm.gridData=data.requestList;
   	 	});
    }]);

})();
