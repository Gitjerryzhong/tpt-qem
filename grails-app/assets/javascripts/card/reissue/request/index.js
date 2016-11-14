//= require lib/doT.min
//= require lib/moment-with-locales.min
//= require_self

(function($, doT, undefined) {
$.widget("ui.cardReissueRequestIndex", {
	_create: function () {
		var that = this;
		this.template = doT.template($("#table-template").html());
		this.max = 10;
		this.loadData(1, 0);
		this._on({
			"click .status": function(e) {
				that.loadData($(e.currentTarget).find("input[type='radio']").val(), 0);
			},
			"click .prev-page a": function(e) {
				e.preventDefault();
				if(!$(e.target).parent(".prev-page").hasClass("disabled")) {
					that.loadData(that.status, that.offset - that.max);
				}
			},
			"click .next-page a": function(e) {
				e.preventDefault();
				if(!$(e.target).parent(".next-page").hasClass("disabled")) {
					that.loadData(that.status, that.offset + that.max);
				}
			}
		});
	},
	
	loadData: function(status, offset) {
		var that = this;
		$.ajax({
			type: "GET",
			url: "/tms/cardReissueRequests", 
			data: {
				status: status, 
				offset: offset,
				max: this.max,
				format: 'json'
			},
			cache: false
		}).done(function(data) {
			that.status = status;
			that.offset = offset;
			that.updateView(data);
		});
	},
	
	updateView: function(data) {
		// update count
		this.element.find(".status .count").each(function() {
			var status = $(this).prev("input[type='radio']").val();
			var count = data.counts[status]; 
			$(this).text(count ? count : 0);
		});
		
		// fill table
		$("#content").html(this.template({
			requests: data.requests,
			offset: this.offset
		}));
		
		// update pager
		var total = data.counts[this.status] ? data.counts[this.status]: 0;
		var $next = $("#content .next-page");
		var $prev = $("#content .prev-page");
		
		if(this.offset + data.requests.length >= total) {
			$next.addClass("disabled");
		} else {
			$next.removeClass("disabled");
		}
		
		if(this.offset <= 0) {
			$prev.addClass("disabled");
		} else {
			$prev.removeClass("disabled");
		}
	}
});
}(window.jQuery, doT));