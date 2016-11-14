//= require tms.js

(function(tms, undefined) {
	"use strict";
	var DAYS = " 一二三四五六日";
	
	tms.createModual("tms.format", {
		week: function(week) {
			return "第" + week + "周"; 
		},

		dayOfWeek: function(day) {
			return "周" + DAYS.charAt(day);
		}
	});
}(window.tms));