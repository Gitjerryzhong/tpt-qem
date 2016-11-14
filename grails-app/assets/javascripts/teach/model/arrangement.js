//= require core/format.js

(function($, tms, undefined) {
	var Model = tms.createModual('tms.teach.model');
	
	var ENGLISH_TYPES = ["", "读写", "听力", "口语"];
	
	/**
	 * arr: arrangement
	 * forms: [arrId, formId]*
	 */
	var Arrangement = Model.Arrangement = function(arr, forms, frees) {
		$.extend(this, arr);
		// collect rollcalled weeks
		if(forms) {
			var rollcalls = []
			$.each(forms, function() {
				if(arr.id == this.id) {
					rollcalls.push(this.week);
				}
			});
			this.rollcalls = rollcalls;
		}
		
		if(frees) {
			var free = false
			for(var i = 0; i < frees.length; i++) {
				if(arr.id == frees[i]) {
					free = true;
					break;
				}
			}
			this.free = free;
		}
	}
	
	Arrangement.prototype = {
		constructor: Arrangement,
		
		isVisible: function(week) {
			return week >= this.startWeek && week <= this.endWeek && 
				  (this.oddEven == 0 || this.oddEven == (week % 2 ? 1 : 2));
		},
		
		isRollcalled: function(week) {
			if(this.rollcalls) {
				for (var i = 0; i < this.rollcalls.length; i++) {
					if(this.rollcalls[i] == week) {
						return true;
					}
				}
				return false;
			} else {
				return false;				
			}
		},
		
		sectionsText: function() {
			return this.startSection + "-" + (this.startSection + this.totalSection - 1) + "节";
		},
		
		courseText: function() {
			var suffix = "";
			if(this.flag == 1) {
				suffix = "（实验）";
			} else if(this.flag >= 10 && this.flag < 40) {
				suffix = "（" + ENGLISH_TYPES[this.flag / 10] +
					(this.flag%10?this.flag%10:'') + "）";
			}
			
			if(this.free) { // 免听
				suffix += "<br><label class='label label-warning'>免听</label>"
			}
			return this.courseClass + suffix;
		},
		
		weeksText: function() {
			return this.startWeek + "-" + this.endWeek + "周";
		},
		
		dayOfWeekText: function() {
			return tms.format.dayOfWeek(this.dayOfWeek);
		},
		
		oddEvenText: function() {
			return this.oddEven == 1 ? "[单]" :  this.oddEven == 2 ? "[双]" : "";
		},
		
		roomText: function() {
			return this.flag != -1 ? this.room : "";
		}
	}	
} (jQuery, tms));