angular.module('mine.directive', [])
.directive('ngConfirmSelect',
	 [function () {
	    return {
	        restrict: 'AEC',
	        scope: {
	            data: '=data'
	        },
	        link: function (scope, element, attr) {
	            var value = attr.ngValue;
	            var clickAction = attr.ngClick;
	            element.bind('click', function (event) {
	                var items = scope.data;
	                angular.forEach(items, function (item) {
	                    if (value == 'all') item.selected = true;
	                    if (value == "none") item.selected = false;
	                    if (value == "invert") item.selected = !item.selected;
	                })
	                scope.$eval(clickAction);
	            });
	        }

	    };
	}]);
