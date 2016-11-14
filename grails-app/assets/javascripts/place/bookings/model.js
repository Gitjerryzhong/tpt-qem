//= require lib/moment-with-locales.min
//= require format
//= require_self

(function($, moment, tms, format, undefined) {
var Model = tms.createModual("tms.place.booking.model");

var Form = Model.Form = function(form, options) {
	this.options = options;
	if(form) {
		$.extend(this, form);
		this.items = $.map(form.items, function(item) {
			return new Item(item, options);
		});
		this.audits = $.map(form.audits, function(audit) {
			return new Audit(audit);
		});
		this.removedItems = []
	} else {
		this.formId = 0;
		this.items = [];
		this.audits = [];
	}
};

Form.prototype = {
	constructor: Form,
	
	statusText: function() {
		return format.statusText(this.status)
	},
	
	readonly: function() {
		return !this.isOwner || this.allowActions.length == 0
	},
	
	addItem: function(item, confilcts) {
		if(!this.isConflict(item, confilcts)) {
			this.items.push(item);				
		}
	},
	
	removeItem: function(index) {
		var item = this.items[index];
		this.items.splice(index, 1);
		if(item.itemId) {
			this.removedItems.push(item);
		}
	},
	
	isConflict: function(item, confilcts) {
		if(!this.items) {
			return false;
		}
		
		for(var i = 0; i < this.items.length; i++) {
			if(this.items[i].isConflict(item, confilcts)) {
				return true;
			}
		}
		return false;
	},
	
	hasOccupied: function() {
		for(var i = 0; i < this.items.length; i++) {
			if(this.items[i].occupied) {
				return true;
			}
		}
		return false;
	},
	
	getOccupiedItems: function() {
		return $.map($.grep(this.items, function(item) {
			return item.occupied 
		}), function(item) {
			return item.itemId;
		});
	},
	
	getAddedItems: function() {
		return $.map($.grep(this.items, function(item) {
			return !item.itemId 
		}), function(item) {
			return {
				roomId:      item.roomId,
				startWeek:   item.startWeek,
				endWeek:     item.endWeek,
				dayOfWeek:   item.dayOfWeek,
				sectionType: item.sectionType
			}
		});
	},
	
	getRemovedItems: function() {
		return $.map(this.removedItems, function(item) {
			return {itemId: item.itemId}
		});
	},
	
	save: function() {
		var that = this;
		
		if(!this.formId) {
			return $.ajax({
				type: "POST",
				url: "./",
				data: JSON.stringify({
					departmentId:  this.departmentId,
					bookingTypeId: this.bookingTypeId,
					reason:        this.reason,
					addedItems:    this.getAddedItems()
    			}),
    			contentType: 'application/json',
    			dataType: 'json'
			});
		} else {
			return $.ajax({
				type: "PUT",
				url: ".",
				data:JSON.stringify({
					formId:        this.formId,
					departmentId:  this.departmentId,
					bookingTypeId: this.bookingTypeId,
					reason:        this.reason,
					addedItems:    this.getAddedItems(),
					removedItems:  this.getRemovedItems()
    			}),
    			contentType: 'application/json'
			});
		}
	},
	
	changeStatus: function(status) {
		return $.ajax({
			type: "POST",
			url: window.location.href,
			data:{status:status}
		});
	},
	
	remove: function() {
		return $.ajax({
			type : "DELETE",
			url : window.location.href
		});
	}
};

var Item = Model.Item = function(item, options) {
	this.options = options;
	$.extend(this, item);
};

Item.prototype = {
	constructor: Item,
	_baseString: function() {
		return [
			format.weeks(this.startWeek, this.endWeek), 
			format.dayOfWeek(this.dayOfWeek), 
			format.sectionType(this.sectionType), 
			this.roomName
		].join(" ")
	},

	toShortString: function() {
		return this._baseString() + " ( " + this.roomSeat + "座 )";
	},
	
	toLongString: function() {
		return this._baseString() + " ( " + this.roomType + " / " + this.roomSeat + "座 )";
	},

	toString: function() {
		var text = this.toLongString();
		if(this.occupied) {
			return "<span class='text-danger'>" + text + "</span> <span class='label label-danger'>已占用</span>";
		} else {
			return text;
		}
	},
	
	toApproveString: function() {
		var text = this.toLongString();
		if(this.occupied) {
			return "<span class='text-danger'>" + text + "</span> <span class='label label-danger'>已占用</span>";
		} else {
			var diff = this.diffDays();
			if(diff < 0) {
				return "<span class='text-warning'>" + text + "</span> <span class='label label-warning'>已过期</span>";
			} else if(diff == 0) {
				return "<span class='text-warning'>" + text + "</span>";
			} else {
				return text;				
			}
		}
	},
	
	isConflict: function(that, confilcts) {
		return this.roomId == that.roomId &&
		   	   this.dayOfWeek == that.dayOfWeek &&
		   	   this.isOverlaped(this.startWeek, this.endWeek, that.startWeek, that.endWeek) &&
		   	   $.inArray(that.sectionType, confilcts[this.sectionType]) != -1
	},
	
	// http://nedbatchelder.com/blog/201310/range_overlap_in_two_compares.html
	isOverlaped: function(s1, e1, s2, e2) {
		return e1 >= s2 && e2 >= s1
	},
	
	diffDays: function() {
		if(!this.options || !this.options.term) {
			return Number.MAX_VALUE;
		}
		return moment(this.options.term.startDate).add({
			days: this.dayOfWeek -1,
			weeks: this.startWeek - 1
		}).diff(moment(this.options.term.today));
	}
};

var Audit = Model.Audit = function(audit) {
	$.extend(this, audit);
};

Audit.prototype = {
	constructor: Audit,
	
	toString: function() {
		return [
			moment(this.date).format("YYYY-MM-DD HH:mm"), 
			this.userName , 
			format.actionText(this.action),
			this.content ? this.content : ""
		].join(" ")
	}
};
} (window.jQuery, moment, window.tms, tms.place.booking.format));