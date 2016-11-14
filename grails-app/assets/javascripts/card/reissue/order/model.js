//= require core/tms
//= require_self

(function($, tms, undefined) {
	var Model = tms.createModual("tms.card.reissue.model");
	
	var Order = Model.Order = function(options) {
		if(options.order) {
			this.orderId = options.order.orderId;
			this.items = options.order.items;
			this.removedItems = []
		} else {
			this.orderId = 0;
			this.items = [];
		}
	}
	
	Order.prototype = {
		constructor: Order,
		
		addItem: function(request) {
			var index = this._indexOf(this.items, request.id)
			  , removedIndex = this._indexOf(this.removedItems, request.id)
			  ;
			
			if(index == -1) {
				if(removedIndex == -1) { // 构造新Item, request的id转换成requestId
					var item = {requestId: request.id};
					$.each(request, function(key, value) {
						if(key != 'id') {
							item[key] = value;
						}
					});
					this.items.push(item);
				} else { // 重新加入已删除的Item
					var removedItem = this.removedItems[removedIndex];
					this.removedItems.splice(removedIndex, 1);
					this.items.push(removedItem);
				}
			}
		},
		
		removeItem: function(requestId) {
			var index = this._indexOf(this.items, requestId)
			  ;
			
			if(index != -1) {
				var item = this.items[index];
				this.items.splice(index, 1);
				if(item.itemId) {
					this.removedItems.push(item);
				}
			}
		},
		
		contains: function(requestId) {
			return this._indexOf(this.items, requestId) != -1;
		},
		
		_indexOf: function(items, requestId) {
			if(!items) {
				return -1
			}
			
			for(var i = 0; i < items.length; i++) {
				if(items[i].requestId == requestId) {
					return i;
				}
			}
			return -1;
		},
		
		save: function() {
			var that = this;
    		
    		if(!this.orderId) {
    			return $.ajax({
    				type: "POST",
    				url: "./",
    				data: JSON.stringify({
    					addedItems: $.map(this.items, function(item) {
    						return {requestId: item.requestId}
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
    					orderId: this.orderId,
    					addedItems: $.map($.grep(this.items, function(item) {
    						return !item.itemId 
    					}), function(item) {
    						return {
    							requestId: item.requestId
    						}
    					}),
    					removedItems: $.map(this.removedItems, function(item) {
    						return {
    							itemId: item.itemId,
    							requestId: item.requestId
    						}
    					})
        			}),
        			contentType: 'application/json'
    			});
    		}
		}
	}
} (window.jQuery, window.tms));