//= require lib/doT.min
//= require teach/model/arrangement

(function($, Arrangement, doT, undefined) {
	$.widget("ui.rollcallTeacher", {
		_create : function() {
			var that = this
			  , optionTemplate = doT.template($("#option-template").html())
			  , $terms = $("#terms")
			  ;
			
			$terms.html(optionTemplate(this.options.termIds))
				  .val(this.options.termId)
				  .on("change", function() {
					  that._loadData($terms.val());
				   });
			
			this.element.on("click", ".bar :not(.disabled)", function() {
				var $span = $(this)
				  , $div = $span.parent("div")
				  , arrangement = $div.data("arrangement")
				  , week = $span.data("week")
				  ;
				window.location.href = "./" + arrangement + "/" + week
			});
			
			this.template = doT.template($("#tbody-template").html());
			this._loadData(this.options.termId)
		},
		
		_loadData: function(term) {
			var that = this;
			$.ajax({
				type: "GET",
				url: window.location.href + "?format=json&term=" + term,
				contentType: 'application/json',
    			dataType: 'json',
    			cache: false
			}).done(function(data) {
				that._fillData(data);
			});
		},
		
		_fillData: function(data) {
			var arrangements = []
			  , $tbody = this.element.find("tbody")
			  ;
			$tbody.empty();
			// create model
			$.each(data.arrangements, function() {
				arrangements.push(new Arrangement(this, data.forms));
			});
			
			$tbody.append($(this.template({
				term: data.term,
				arrangements: arrangements
			})));
		}
	});
}(jQuery, tms.teach.model.Arrangement, doT));