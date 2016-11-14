//= require place/bookings/format
//= require_self

(function($, format, undefined) {
$.widget("ui.bookingReportShow", {
	_create: function () {
		var that = this;
		this.element.on("click", "#all", function(e){
			window.location.href = "./"
		}).on("click", "#edit", function(e){
			window.location.href = window.location.href + "/edit"
		}).on("click", "#delete", function(e) {
			$.ajax({
				type : "DELETE",
				url : window.location.href,
				success : function() {
					window.location.href = "./"
				},
				error : function() {
					alert("无法删除");
				}
			});
		}).on("click", "#export", function(e) {
			window.location.href = "./export/" + that.options.reportId
		});
		
		this.element.find("td.status").each(function() {
			var $td = $(this)
			  , status = $td.data("status")
			  , statusClass = "label-" + format.statusClass(status)
			  , statusText = format.statusText(status)
			  ;
			$td.append($("<span class='label'/>").addClass(statusClass).text(statusText));
		})
	}
});
}(window.jQuery, tms.place.booking.format));