//= require model
//= require_self

(function($, undefined) {
$.widget("ui.cardReissueOrderReceive", {
	_create: function () {
		var that = this;
		this.element.on("click", ".receive", function(e) {
			that.receive($(e.target));
		}).on("click", ".receive-all", function(e) {
			that.receiveAll($(e.target));
		});
	},
	
	receive: function($btn) {
		var requestId = $btn.data("request-id")
		  , received = $btn.hasClass("glyphicon-ok")
		  ;

		$.ajax({
			type: "POST",
			url: "../" + this.options.orderId,
			data:{
				requestId: requestId,
				received: received
			},
			success: function(data) {
				$btn.removeClass(received ? "glyphicon-ok" : "glyphicon-remove");
				$btn.addClass(received ? "glyphicon-remove" : "glyphicon-ok");
				$btn.closest("tr").find(".status").text(received ? "办理完成" : "正在办理");
			}
		});
	},
	
	receiveAll: function($btn) {
		var that = this
		  , received = $btn.hasClass("glyphicon-ok")
		  ;

		$.ajax({
			type: "POST",
			url: "../" + this.options.orderId,
			data:{
				received: received
			},
			success: function(data) {
				that.element.find(".glyphicon.receive")
					.removeClass(received ? "glyphicon-ok" : "glyphicon-remove")
					.addClass(received ? "glyphicon-remove" : "glyphicon-ok")
					.closest("tr").find(".status").text(received ? "办理完成" : "正在办理");
			},
			error: function(data) {
				alert("无法批量处理");
			}
		});
	}
});
}(window.jQuery));