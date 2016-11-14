//= require lib/dot.min
//= require model/rollcall-item
//= require model/leave-item
//= require_self

(function($, doT, Model, undefined) {
$.widget("ui.rollcallStatisStudent", {
	template: null,
	rollcallItems: [],
	leaveItems:[],
	
	_create: function() {
		var that = this;
		this.template = doT.template($("#statis-template").html());
		
		this.options.rollcallItems.sort(function(a, b) {
			if(a.week != b.week) {
				return a.week - b.week;
			} else if(a.dayOfWeek != b.dayOfWeek) {
				return a.dayOfWeek - b.dayOfWeek;
			} else {
				return a.startSection - b.startSection;
			}
		});
		
		this.options.courses.sort(function(a, b) {
			return a.hours / a.statis - b.hours / b.statis
		});
		
		this.options.leaveItems.sort(function(a, b) {
			if(a.week != b.week) {
				return a.week - b.week;
			} else if(a.dayOfWeek != b.dayOfWeek) {
				return a.dayOfWeek - b.dayOfWeek;
			} else {
				return a.startSection - b.startSection;
			}
		});
		
		$.each(this.options.rollcallItems, function() {
			that.rollcallItems.push(new Model.RollcallItem(this));
		});
		
		$.each(this.options.leaveItems, function() {
			that.leaveItems.push(new Model.LeaveItem(this));
		});
		
		this.updateView();
	},
	
	updateView: function() {
		this.element.html(this.template({
			rollcallItems: this.rollcallItems,
			leaveItems: this.leaveItems,
			courses: this.options.courses
		}));
	}
});
} (jQuery, doT, tms.rollcall.model));