//= require lib/doT.min
//= require lib/moment.min
//= require ui/pager
//= require format
//= require_self

(function($, doT, format, undefined) {
$.widget("ui.bookingList", {
	_create: function () {
		var that = this
		  , statusTemplate = doT.template("<label class='label label-{{=it.statusClass}}'>{{=it.statusName}}<label>")
		  ;
		
		this.element.find(".status").each(function(){
			var $td = $(this)
			  , status = $td.data("status")
			  ;
			
			$td.html(statusTemplate({
				statusClass: format.statusClass(status),
				statusName:  format.statusText(status)
			}));
		});
		
		$("#export").click(function() {
			$("#dialog").modal("show");
		});
		
		$("#dialog").on("show.bs.modal", function() {
			if($("#start-date").val() == "") {
				var end = moment(that.options.today)
				  , start = moment(that.options.today).subtract(1, 'months')
				  ;
				$("#start-date").val(start.format("YYYY-M-D"));
				$("#end-date").val(end.format("YYYY-M-D"));				
			}
		});
		
		$("#start-date").blur(function() {
			that.validate("#start-date");
		});
		
		$("#end-date").blur(function() {
			that.validate("#end-date");
		});
		
		$("#btnOk").click(function() {
			var v1 = that.validate("#start-date")
			  , v2 = that.validate("#end-date")
			  ;
			
			if(v1 && v2) {
				$("#dialog").modal('hide');
				that.download("/tms/bookings/export?start=" + $("#start-date").val() + "&&end=" + $("#end-date").val()); 
			}
		});
	},

	validate: function(id) {
		var $input = $(id)
		  , date = moment($input.val(), "YYYY-M-D", true)
		  , valid = date.isValid()
		  ; 
		
		if(!valid) {
			$input.parent("div").addClass("has-error");
		} else {
			$input.parent("div").removeClass("has-error");
		}
		
		return valid;
	},
	
	download: function (url) {
	    var hiddenIFrameID = 'hiddenDownloader',
	        iframe = document.getElementById(hiddenIFrameID);
	    if (iframe === null) {
	        iframe = document.createElement('iframe');
	        iframe.id = hiddenIFrameID;
	        iframe.style.display = 'none';
	        document.body.appendChild(iframe);
	    }
	    iframe.src = url;
	}
});
}(window.jQuery, doT, tms.place.booking.format));