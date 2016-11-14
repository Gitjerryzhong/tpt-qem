//= require core/format
//= require_self

(function(tms, undefined) {
	"use strict";
	var RC_TYPES = ["", "旷课","迟到","早退","", "迟到+早退"];
	var RC_CLASSES = ["", "label-danger", "label-warning", "label-early", "label-info", "label-early"];
	var LV_TYPES = ["", "事假", "病假", "公假"];
	var DAYS = " 一二三四五六日";
	
	tms.createModual("tms.rollcall.format", {
		rollcallType: function(type) {
			return RC_TYPES[Math.abs(type)];
		},
		
		rollcallClass: function(type) {
			return RC_CLASSES[Math.abs(type)];
		},
		
		leaveType: function(type) {
			return LV_TYPES[type];
		},
		
		leaveClass: function(type) {
			return RC_CLASSES[4];
		},
		
		week: function(week) {
			return tms.format.week(week); 
		},

		dayOfWeek: function(day) {
			return tms.format.dayOfWeek(day);
		}
	});
}(window.tms));