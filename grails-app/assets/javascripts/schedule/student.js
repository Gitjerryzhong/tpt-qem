//= require schedule.js

(function($, undefined) {
$.widget("ui.studentSchedule", {
	_create: function () {
		this.element
			.find(".schedule")
			.schedule(this.options);
	}
});
}(jQuery));