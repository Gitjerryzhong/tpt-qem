//= require lib/doT.min
//= require lib/moment-with-locales.min
//= require model
//= require format
//= require_self

(function($, doT, model, undefined) {
$.widget("ui.bookingShow", {
	_create: function () {
		var that = this
		  , formTemplate = doT.template($("#form-template").html())
		  ;
		
		this.model = new model.Form(this.options.form);
		
		this.element.find(".form").html(formTemplate(this.model));
		
		this.updateButtons();
		this.updateStatus();
	},

	updateButtons: function() {
		var that = this
		  , $toolbar = this.element.find(".btn-toolbar")
		  , actions = {
				edit: function() {
					window.location.href = window.location.href + "/edit"; 
				},
				apply: function() {
					that.model.changeStatus(1).done(function(data) {
						window.location.reload(true);
					});
				},
				cancel: function() {
					that.model.changeStatus(0).done(function(data) {
						window.location.reload(true); ;
					});
				},
				remove: function() {
					that.model.remove().done(function() {
						window.location.href = "../"
					}).fail(function() {
						alert("无法删除");
					});
				}
		    }
		  ;
		if(this.model.readonly()) {
			$toolbar.hide();
		} else {
			$toolbar.find("button").attr("disabled", "disabled");
			$(this.model.allowActions).each(function() {
				$toolbar.find("." + this).removeAttr("disabled").click(actions[this]);
			});
		}
	},
	
	updateStatus: function() {
		this.element.find(".status").text(this.model.statusText())
	}
});
}(window.jQuery, doT, tms.place.booking.model));