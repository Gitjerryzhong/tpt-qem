//= require core/tms
//= require_self

(function($, tms, undefined) {
	
	var Model = tms.createModual('tms.leave.model');
	
    /* VIEW MODEL
     * =============== */
    var LeaveModel = Model.LeaveModel = function(options) {
    	if(options.leaveRequest) {
    		this.requestId    = options.leaveRequest.id;
    		this.leaveType    = options.leaveRequest.type;
    		this.leaveItems   = options.leaveRequest.items;
        	this.removedItems = [];
    	} else {
    		this.requestId  = 0
    		this.leaveType  = 1;
    		this.leaveItems = [];
    	}
    	this.term = options.term;
    	this.existedItems = options.existedItems;
    	this.arrangements = options.arrangements;
    	
    	$.each(this.existedItems, function() {
    		if(this.arr != null) {
    			this.arrangement = get(this.arr);
    		}
    		
    		function get(id) {
    			var arrangement;
    			$.each(options.arrangements, function() {
    				if(this.id == id) {
    					arrangement = this;
    					return false;
    				}
    			})
    			return arrangement;
    		}
    	});
    };
    
    LeaveModel.prototype = {
    	constructor: LeaveModel,
    	
    	itemCount: function() {
    		return this.leaveItems.length;
    	},
    	
    	addItem: function(item) {
    		var leaveItems = this.leaveItems
    		  , index = this._indexOf(item)
    		  , removedIndex = this._indexOfRemoved(item);
    		
    		if(index == -1) {
    			if(removedIndex == -1) {
        			leaveItems.push(item);
         			$(this).trigger("itemAdded", item);
    			} else {
    				// 重新加入已删除的Item
    				var removedItem = this.removedItems[removedIndex];
    				this.removedItems.splice(removedIndex, 1);
    				leaveItems.push(removedItem);
    	 			$(this).trigger("itemAdded", removedItem);
    			}
    		} 
    	},
    	
    	removeItem: function(item) {
    		var leaveItems = this.leaveItems
  		  	  , index = this._indexOf(item)
  		  	  ;
    		
    		if(index != -1) {
    			var item = leaveItems[index];
    			leaveItems.splice(index, 1)
    			if(item.id) { // 有Id则进行保存
    				this.removedItems.push(item);
    			}
    			$(this).trigger("itemRemoved", index);    				
    		}
    	},

    	
    	/**
    	 * 冲突测试。item如果存在arr,则包含arrangement，可用于测试
    	 */
    	conflictTest: function(item) {
    		var type = null;
    		$.each(this.existedItems, function(index, exist) {
    			// 不是相同周，不冲突
    			if(exist.week != item.week)
    				return
    				
    			if(item.day != null) { // 请假一天
    				if(exist.day != null) {
    					if(exist.day == item.day) {
    						type = "当天已请假";
    						return false;
    					}
    				} else if(exist.arr != null) {
    					if(exist.arrangement.dayOfWeek == item.day) {
    						type = "当天已有请假项";
    						return false;
    					}
    				} else {
    					type = "当周已请假";
    					return false;
    				}
    			} else if(item.arr != null) { // 请假一次课
    				if(exist.day != null) {
    					if(exist.day == item.arrangement.dayOfWeek) {
    						type = "当前已请假";
    						return false;
    					}
    				} else if(exist.arr != null) {
    					if(exist.arr == item.arr) {
    						type = "当前节已请假";
    						return false;
    					}
    				} else {
    					type = "当周已请假";
    					return false;
    				}
    			} else { // 请假一周
    				if(exist.day != null || exist.arr != null) {
    					type = "当周已有请假项";
    				} else {
    					type = "当周已请假";
    				}
   					return false;
    			}
    		});
    		
    		return type;
    	},

    	weeks: function() {
    		return $.unique($(this.leaveItems).map(function() {return this.week;}));
    	},
    	
    	hasItem: function(item) {
	  		return this._indexOf(item) != -1;
    	},
    	
    	_indexOf: function(item) {
    		var leaveItems = this.leaveItems;
    		for(var i = 0; i < leaveItems.length; i++) {
    			if(leaveItems[i].week == item.week 
	  			&& leaveItems[i].day == item.day 
	  			&& leaveItems[i].arr == item.arr) {
    				return i;
    			}
    		}
    		return -1;
    	},
    	
    	setLeaveType: function(value) {
   			this.leaveType = value
    	},
    	
    	_indexOfRemoved: function(item) {
    		var items = this.removedItems;
    		if(!items) 
    			return -1;
    		
    		for(var i = 0; i < items.length; i++) {
    			if(items[i].week == item.week 
	  			&& items[i].day == item.day 
	  			&& items[i].arr == item.arr) {
    				return i;
    			}
    		}
    		return -1;
    	},
    	
    	save: function(reason) {
    		var $this = this;
    		
    		if(!this.requestId) {
    			return $.ajax({
    				type: "POST",
    				url: "./",
    				data: JSON.stringify({
    					type: this.leaveType,
    					reason: reason,
    					term: this.term.id,
    					items: this.leaveItems
        			}),
        			contentType: 'application/json',
        			dataType: 'json'
    			});
    		} else {
    			return $.ajax({
    				type: "PUT",
    				url: ".",
    				data:JSON.stringify({
    					requestId: this.requestId,
    					type: this.leaveType,
    					reason: reason,
    					items: this.leaveItems,
    					removedItems: this.removedItems
        			}),
        			contentType: 'application/json'
    			});
    		}
    	}
    }
} (window.jQuery, window.tms));