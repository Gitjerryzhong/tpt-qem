//= require teach/model/arrangement
//= require_self

(function($, Arrangement, undefined) {
$.widget("ui.schedule", {
	arrangements: [], // array of Arrangement
	
	_create: function () {
		var that = this;
		
		// create model
		$.each(this.options.arrangements, function() {
			that.arrangements.push(new Arrangement(this, that.options.forms))
		});
		
		this._on({
			"mouseenter .enabled": function(e) {
				$(e.target).addClass("ui-state-hover");
			},
			
			"mouseleave .enabled": function(e) {
				$(e.target).removeClass("ui-state-hover");
			},
			
			"click .enabled": function(e) {
				var arr = $(e.target).data("item");
				this._trigger("click.arrangement", e, {arr:arr, week:this.week});
			}
		});
		
		this.render();
    },
    
    render: function() {
    	var that = this
    	  , week = this.week;
    	$.each(this.arrangements, function(index, arr) {
    		if(arr.isVisible(week)) {
    			that.renderCell(arr);
    		}
    	});
    },
    
    renderCell: function(arr) {
		var row = arr.startSection
          , col = arr.dayOfWeek
          , rowSpan = arr.totalSection
          , rollcalled = false
          ;

		for (var i = 1; i < rowSpan; i++) {
			cell(row + i, col).hide();
		}
		
		cell(row, col)
			.prop('rowspan', rowSpan)
			.data("item", arr)
			.addClass("enabled")
			.addClass(arr.isRollcalled(this.week) ? "rc" : null)
			.html(arr.courseText() + "<br>" +
				  arr.weeksText() + arr.oddEvenText() + "<br>" +
				  arr.roomText()
			 );
		
		function cell (row, col) {
		   	return $("#a_" + row + "_" + col);
		};
	},
	
    reset: function() {	    	
    	$("td.arr", this.$el)
    		.prop("rowspan", 1)
            .removeClass("enabled rc")
    	    .removeData("item")
    	    .show()
            .empty();
    }
});
}(jQuery, tms.teach.model.Arrangement));