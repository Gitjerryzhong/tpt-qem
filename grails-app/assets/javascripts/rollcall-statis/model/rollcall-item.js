//=require core/tms
//=require rollcall/format
//=require teach/model/arrangement
//=require_self

(function($, tms, Arrangement, format, undefined) {
	var Model = tms.createModual('tms.rollcall.model');

	var RollcallItem = Model.RollcallItem = function(item) {
		this.type = item.type;
		this.week = item.week;
		this.teacher = item.teacher;
		this.arrangement = new Arrangement({
			dayOfWeek: item.dayOfWeek,
			startSection: item.startSection,
			totalSection: item.totalSection,
			courseClass: item.courseClass,
			flag: item.flag
		});
	}
	
	RollcallItem.prototype = {
		constructor: RollcallItem,
			
		typeText: function() {
			return format.rollcallType(this.type);
		},
		
		typeClass: function() {
			return format.rollcallClass(this.type);
		},
		
		weekText: function() {
			return format.week(this.week);
		}
	}	
} (jQuery, tms, tms.teach.model.Arrangement, tms.rollcall.format));