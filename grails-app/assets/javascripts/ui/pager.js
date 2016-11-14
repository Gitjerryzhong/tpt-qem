//= require lib/URI-1.13.1.min

(function($, undefined) {
$.widget("ui.pager", {
	defaultElement:"<ul>",
	options: {
		total: 0,
		offset: 0,
		max: 10
	},
	
	_create: function() {
	  	var total = this.options.total;
	  	var offset = this.options.offset;
	  	var max = this.options.max;
		
		var $prev = $("<li><a>上一页</a></li>").appendTo(this.element);
		var $next = $("<li><a>下一页</a></li>").appendTo(this.element);
		
		if(offset <= 0) {
			$prev.addClass("disabled");
		} else {
			$prev.find("a").attr("uri:query", {
				offset: offset - max >= 0 ? offset - max : 0,
				max: max
			});
		}
		
		if(offset + max >= total) {
			$next.addClass("disabled");
		} else {
			$next.find("a").attr("uri:query", {
				offset: offset + max,
				max: max
			});
		}
	},
});
}(jQuery));