(function($, undefined) {
$.widget("ui.cardReissueRequestShow", {
	_create: function () {		
		this._on({
			"click #edit": function() {
				window.location.href = window.location.href + "/edit"; 
			},
			"click #apply": function() {
				this.post(1);
			},
			"click #cancel": function() {
				this.post(0);
			},
			"click #delete": function() {
				this.remove();
			}
		});
		
		this.setStatus(this.options.status);
	},
	
	setStatus: function(status) {
		this.status = status;
		if(this.options.editable) {
			this.updateButtons();
		}
		this.updateStatus();
	},
	
	updateButtons: function() {
		var that = this
		  , allowButtons = [
		        [ "edit", "apply", "delete" ], // new ->
			    [ "cancel" ],                  // applying ->
			    [],                            // approved ->
		 	    [ "edit", "apply", "delete" ], // rejected ->
			    []                             // finished ->
		    ]
		  ;
		$("#toolbar button").each(function() {
			var $button = $(this);
			if ($.inArray($button.attr("id"), allowButtons[that.status]) != -1) {
				$button.removeAttr("disabled")
			} else {
				$button.attr("disabled", "disabled")
			}
		});
	},
	
	updateStatus: function() {
		var labels = ['未提交', '申请成功', '正在办理', '不批准', '办理完成'];
		var $status = $("#status");
		$status.text(labels[this.status])
	},
	
	post: function(status) {
		var that = this;
		$.ajax({
			type: "POST",
			url: window.location.href,
			data:{status:status},
			success: function(data) {
				that.setStatus(status);
			}
		});
	},
		
	remove: function() {
		$.ajax({
			type : "DELETE",
			url : window.location.href,
			success : function() {
				window.location.href = "../"
			},
			error : function() {
				alert("无法删除");
			}
		});
	}
});
}(window.jQuery));