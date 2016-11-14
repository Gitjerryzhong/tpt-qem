//= require schedule.js

(function($, undefined) {
$.widget("ui.teacherSchedule", {
	_create: function () {
		this.element
			.find(".schedule")
			.schedule(this.options)
			.schedule({"click.arrangement" : function(e, data) {
					window.location.href = '../rollcall/' + data.arr.id + '/' + data.week;
			 }});
	}
});
}(jQuery));