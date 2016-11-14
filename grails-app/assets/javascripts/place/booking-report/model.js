//= require core/tms
//= require_self

(function($, tms, undefined) {
	var Model = tms.createModual("tms.place.booking.report.model");
	
	var Report = Model.Report = function(options) {
		if(options.report) {
			this.reportId = options.report.reportId;
			this.items = options.report.items;
			this.removedItems = []
		} else {
			this.reportId = 0;
			this.items = [];
		}
	}
	
	Report.prototype = {
		constructor: Report,
		
		addItem: function(item) {
			var index = this._indexOf(this.items, item.bookingId)
			  , removedIndex = this._indexOf(this.removedItems, item.bookingId)
			  ;
			
			if(index == -1) {
				if(removedIndex == -1) {
					this.items.push(item);
				} else { // 重新加入已删除的Item
					var removedItem = this.removedItems[removedIndex];
					this.removedItems.splice(removedIndex, 1);
					this.items.push(removedItem);
				}
			}
		},
		
		removeItem: function(bookingId) {
			var index = this._indexOf(this.items, bookingId)
			  ;
			
			if(index != -1) {
				var item = this.items[index];
				this.items.splice(index, 1);
				if(item.flag) {
					this.removedItems.push(item);
				}
			}
		},
		
		contains: function(bookingId) {
			return this._indexOf(this.items, bookingId) != -1;
		},
		
		_indexOf: function(items, bookingId) {
			if(!items) {
				return -1
			}
			
			for(var i = 0; i < items.length; i++) {
				if(items[i].bookingId == bookingId) {
					return i;
				}
			}
			return -1;
		},
		
		save: function() {
			var that = this;
    		
    		if(!this.reportId) {
    			return $.ajax({
    				type: "POST",
    				url: "./",
    				data: JSON.stringify({
    					addedItems: $.map(this.items, function(item) {
    						return item.bookingId;
    					})
        			}),
        			contentType: 'application/json',
        			dataType: 'json'
    			});
    		} else {
    			return $.ajax({
    				type: "PUT",
    				url: ".",
    				data:JSON.stringify({
    					reportId: this.reportId,
    					addedItems: $.map($.grep(this.items, function(item) {
    						return !item.flag 
    					}), function(item) {
    						return item.bookingId;
    					}),
    					removedItems: $.map(this.removedItems, function(item) {
    						return item.bookingId;
    					})
        			}),
        			contentType: 'application/json'
    			});
    		}
		}
	}
} (window.jQuery, window.tms));