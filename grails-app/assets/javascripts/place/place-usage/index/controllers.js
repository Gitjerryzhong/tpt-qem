angular.module("app").controller("MainCtrl", ["$scope", "$http", "config", function($scope, $http, config) {
	$scope.rows = [1, 2, 3, 4, 0, 5, 6, 7, 8, 9, 10, 11, 12];
	$scope.cols = [1, 2, 3, 4, 5, 6, 7];
	
	$scope.spans = [];
	$scope.spans[1] = {span: 4, text: '上午'};
	$scope.spans[0] = {span: 1, text: '中午'};
	$scope.spans[5] = {span: 5, text: '下午'};
	$scope.spans[10] = {span: 4, text: '晚上'};
	
	$scope.buildings = config.buildings;
	$scope.selectedBuilding = $scope.buildings[0];
	$scope.rooms = config.rooms;
	if($scope.rooms.length > 0) {
		$scope.selectedRoom = $scope.rooms[0].id;		
	}
	
	$scope.loadRooms = function(building) {
		$http.get("/tms/placeUsage/rooms?building=" + encodeURIComponent(building)).success(function(data) {
			$scope.rooms = data;
			if($scope.rooms.length > 0) {
				$scope.selectedRoom = $scope.rooms[0].id;		
			}
		});
	};
	
	$scope.loadUsages = function(room) {
		$http.get("/tms/placeUsage/usages?room=" + room).success(function(data) {
			$scope.usages = data;
		});
	};
	
	var TYPE_NAMES = {
		jy: '借用',
		pk: '上课',
		bk: '上课',
		ty: '体育',
		qt: '其它',
		ks: '考试',
		fsks: '考试'
	};
	
	$scope.$watchCollection('usages', function(newUsages, oldUsages) {
		$("td.arr").empty();
		angular.forEach(newUsages, function(usage) {
			for(var i = 0; i < usage.totalSection; i++) {
				var $td = $("#a-" + (usage.startSection + i) + "-" + usage.dayOfWeek);
				var $item = $("<span></span");
				if(usage.type == 'jy') {
					$item.addClass("label label-success");
				} else if(usage.type == 'ks' || usage.type == 'fsks') {
					$item.addClass("label label-danger");
				} else {
					$item.addClass("label label-primary");
				}
				$item.text(usage.startWeek == usage.endWeek ? usage.startWeek : (usage.startWeek + '-' + usage.endWeek + ['', '(单)', '(双)'][usage.oddEven]));
				$item.attr("data-toggle", "tooltip");
				$item.attr("data-placement", "top");
				var typeName = TYPE_NAMES[usage.type]
				$item.attr("title", "<div>" + (typeName || '其它') + " - " + usage.department + "</div><div>" + usage.description + "</div>");
				$td.append($item);
			}
		});
		
		$("#schedule span").tooltip({html: true});
	});
}]);