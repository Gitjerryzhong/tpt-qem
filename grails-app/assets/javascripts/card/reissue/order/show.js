(function($, undefined) {
$.widget("ui.cardReissueOrderShow", {
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
		});
	}
});
}(window.jQuery));