//= require core/tms
//= require_self

(function($, tms, undefined) {
$.widget("ui.leaveItemList", {
	_create: function() {
		var that = this
		  , leaveModel = this.options.leaveModel;
		
		this._on(leaveModel, {
			itemAdded: function(e, item) {
				this._createItem(item);
			},
			itemRemoved: function(e, index) {
				this._removeItem(index);
			}
		});
	  	
		$(leaveModel.leaveItems).each(function() {
			that._createItem(this);
		});
	},
    
	_createItem: function(item) {
		var $item = $("<li/>")
		  , arrangements = this.options.arrangements
		  ;
		
		if(item.day != undefined) {
			$item.text(tms.format.week(item.week) + ", " + tms.format.dayOfWeek(item.day));
		} else if(item.arr != undefined) {
			for(var i = 0; i < arrangements.length; i++) {
    			var arr = arrangements[i];
				if(arr.id == item.arr) {
	    			$item.text(tms.format.week(item.week) 
	    					+ ", " + tms.format.dayOfWeek(arr.dayOfWeek)
	    					+ ", " + arr.startSection + "-" + (arr.startSection + arr.totalSection - 1) + "节"
	    			        + ", " + arr.courseClass
	    			);
	    			break;
				}
			}
		} else {
			$item.text(tms.format.week(item.week));
		}
		
		$item.appendTo(this.element);
	},
	
	_removeItem: function(index) {
		this.element.find(":nth-child("+(index+1)+")").remove();
	}
});
}(jQuery, tms));