//=require core/tms
//=require rollcall/format
//=require teach/model/arrangement
//=require_self

(function($, tms, Arrangement, format, undefined) {
	var Model = tms.createModual('tms.rollcall.model');

	var LeaveItem = Model.LeaveItem = function(item) {
		this.type = item.type;
		this.week = item.week;
		if(item.startSection != undefined) {
			this.arrangement = new Arrangement({
				dayOfWeek: item.dayOfWeek,
				startSection: item.startSection,
				totalSection: item.totalSection,
				courseClass: item.courseClass,
				flag: item.flag
			});
		} else if(item.dayOfWeek != undefined){
			this.dayOfWeek = item.dayOfWeek;
		}
		
	}
	
	LeaveItem.prototype = {
		constructor: LeaveItem,
		
		typeText: function() {
			return format.leaveType(this.type);
		},
		
		typeClass: function() {
			return format.leaveClass(this.type);
		},
		
		weekText: function() {
			return format.week(this.week);
		},
		
		dayOfWeekText: function() {
			if(this.dayOfWeek) {
				return format.dayOfWeek(this.dayOfWeek);
			} else if(this.arrangement) {
				return this.arrangement.dayOfWeekText();
			} else {
				return "";
			}
		},
		
		sectionsText: function() {
			if(this.arrangement) {
				return this.arrangement.sectionsText();
			} else {
				return "";
			}
		},
		
		courseText: function() {
			if(this.arrangement) {
				return this.arrangement.courseText();
			} else {
				return "";
			}
		}
	}	
} (jQuery, tms, tms.teach.model.Arrangement, tms.rollcall.format));