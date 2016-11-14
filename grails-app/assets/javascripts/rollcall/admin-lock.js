(function($, undefined) {
	$.widget("ui.rollcallAdmin", {
		_create: function() {
			var that = this
			  , options = this.options;
			
			$(".week-tabs").on("shown.bs.tab", function(e) {
				var week = $(e.target).text();
				var url = window.location.href;
				if(url.match(/.*\?week=\d+$/)) {
					url = url.replace(/\?week=\d+$/, "?week=" + week);
				} else {
					url = url + "?week=" + week;
				}
				window.location.href = url;
			});
			
			this._on({
				"click td.lock .outer": function(e) {
					post($(e.target).closest("td"), 0);
				},
				"click td.unlock .outer": function(e) {
					post($(e.target).closest("td"), 1);
				},
				"click #lockAll": function() {
					postAll(options.week, 1);
				},
				"click #unlockAll": function() {
					postAll(options.week, 0);
				}
			});
		}
	});
	
	/* -----------------*/
	/* helper functions */
	/* -----------------*/
	function post($td, status) {
		var url = window.location.href;
		if(url.match(/.*\?week=\d+$/)) {
			url = url.replace(/\?week=\d+$/, "");
		}
		
		$.ajax({
			type: "POST",
			url: url,
			data: {formId:$td.data("id"), status:status},
			success: function() {
				$td.toggleClass("lock unlock");
			}
		});		
	}
	
	function postAll(week, status) {
		var url = window.location.href;
		if(url.match(/.*\?week=\d+$/)) {
			url = url.replace(/\?week=\d+$/, "");
		}
		
		$.ajax({
			type: "POST",
			url: url,
			data: {week:week, status:status},
			success: function() {
				if(status == 0) {
					$("td.lock").toggleClass("lock unlock");
				} else {
					$("td.unlock").toggleClass("lock unlock");
				}
			}
		});		
	}
}(jQuery));