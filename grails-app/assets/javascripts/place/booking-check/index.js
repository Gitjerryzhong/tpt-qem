//= require lib/doT.min
//= require lib/moment-with-locales.min
//= require core/validate
//= require place/bookings/model
//= require place/bookings/format
//= require_self

(function($, doT, moment, validate, format, model, undefined) {
$.widget("ui.bookingCheck", {
	_create: function() {
		var that = this;
		this.max = 10;
		this.offset = 0;
		this.listTemplate = doT.template($("#list-template").html());
		this.formTemplate = doT.template($("#form-template").html());
		
		this.$content = this.element.find(".content");
		this.element.on("click", ".form-list tr", function(e) {
			that.loadForm($(this).data("id"));
		}).on("click", ".btn-toolbar .prev", function(e) {
			that.loadForm($(this).data("id"));
		}).on("click", ".btn-toolbar .next", function(e) {
			that.loadForm($(this).data("id"));
		}).on("click", ".btn-toolbar .approve", function(e) {
			that.approve($(this).data("id"));
		}).on("click", ".btn-toolbar .disapprove", function(e) {
			that.disapprove($(this).data("id"));
		}).on("click", ".sidebar a", function(e) {
			e.preventDefault();
			var status = $(this).data("status");
			var offset = status == that.status ? that.offset : 0;
			that.loadList(status, offset);
		}).on("click", ".prev-page a", function(e) {
			e.preventDefault();
			if(!$(this).parent(".prev-page").hasClass("disabled")) {
				that.loadList(that.status, that.offset >= that.max ? that.offset - that.max : 0);
			}
		}).on("click", ".next-page a", function(e) {
			e.preventDefault();
			if(!$(this).parent(".next-page").hasClass("disabled")) {
				that.loadList(that.status, that.offset + that.max);
			}
		});
		
		moment.lang("zh-cn");
		this.loadList(0, 0);
	},
	
	/* status:0 -- pending
	 * status:1 -- processed
	 */
	loadList: function(status, offset) {
		var that = this;
		$.ajax({
			type: "GET",
			url: window.location.href,
			data: {
				status: status,
				offset: offset,
				max: this.max,
				format: 'json'
			},
			dataType: 'json',
			cache: false
		}).done(function(data){
			that.activeStatis(status);
			that.updateStatis(data.statis);
			that.offset = offset;
			that.status = status;
			
			that.$content.html(that.listTemplate($.extend(data, {
				moment: moment,
				format: format,
				status: status
			})));
			
			// update pager
			var total = data.statis[status];
			var $next = that.element.find(".next-page");
			var $prev = that.element.find(".prev-page");
			if(total <= that.max) {
				$next.hide();
				$prev.hide();
			} else {
				if(offset + data.forms.length >= total) {
					$next.addClass("disabled");
				} else {
					$next.removeClass("disabled");
				}
				
				if(offset <= 0) {
					$prev.addClass("disabled");
				} else {
					$prev.removeClass("disabled");
				}
			}
		});
	},
	
	loadForm: function(id) {
		var that = this;
		$.ajax({
			type: "GET",
			url: window.location.href + "/" + id,
			data: {status: this.status},
			dataType: 'json',
			cache: false
		}).done(function(data){
			that.$content.html(that.formTemplate(new model.Form(data.form)));
			that.updateStatis(data.statis);
		});
	},
	
	activeStatis: function(status) {
		this.element.find(".sidebar a").removeClass("active");
		this.element.find(".sidebar a[data-status='" + status +"']").addClass("active");
		this.status = status;
	},
	
	updateStatis: function(statis) {
		this.element.find(".sidebar a.statis").each(function() {
			var $item = $(this)
			  , status = $item.data("status");
			$item.find(".badge").text(statis[status]);
		});
	},
	
	approve: function(id) {
		var $textarea = this.element.find("textarea");
		if(validate.size($textarea, true)) {
			this.post(id, true, $.trim($textarea.val()));
		}
	},
	
	disapprove: function(id) {
		var $textarea = this.element.find("textarea");
		if(validate.size($textarea)) {
			this.post(id, false, $.trim($textarea.val()));
		}
	},
	
	post: function(id, approved, comment) {
		var that = this
		  , data = {approved: approved};
		if(comment.length != 0) {
			data.comment = comment;
		}
		$.ajax({
			type: "POST",
			url: window.location.href + "/" + id,
			data: data
		}).done(function() {
			that.loadForm(id);
		});
	}
});
}(window.jQuery, doT, moment, tms.validate, tms.place.booking.format, tms.place.booking.model));
