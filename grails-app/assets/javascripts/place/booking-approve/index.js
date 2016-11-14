//= require lib/doT.min
//= require lib/moment-with-locales.min
//= require core/validate
//= require place/bookings/model
//= require place/bookings/format
//= require_self

(function($, doT, moment, validate, format, model, undefined) {
$.widget("ui.bookingApprove", {
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
		}).on("click", ".btn-toolbar .close-form", function(e) {
			that.closeForm($(this).data("id"));
		}).on("click", ".btn-toolbar .revoke-form", function(e) {
			that.revokeForm($(this).data("id"));
		}).on("click", ".sidebar a.statis", function(e) {
			e.preventDefault();
			var status = $(this).data("status");
			var offset = status == that.status ? that.offset : 0;
			that.query = ''; 
			that.loadList(status, offset);
		}).on("click", ".sidebar .search button", function(e) {
			that.search();
		}).on("click", ".prev-page a", function(e) {
			e.preventDefault();
			if(!$(this).parent(".prev-page").hasClass("disabled")) {
				that.loadList(that.status, that.offset >= that.max ? that.offset - that.max : 0, that.query);
			}
		}).on("click", ".next-page a", function(e) {
			e.preventDefault();
			if(!$(this).parent(".next-page").hasClass("disabled")) {
				that.loadList(that.status, that.offset + that.max, that.query);
			}
		});
		
		moment.lang("zh-cn");
		this.loadList(0, 0);
	},
	
	search: function() {
		var offset = 0;
		var query = $.trim($("#query").val());
		if(query.charAt(0) == "#") {
			var id = parseInt(query.substring(1));
			if(!isNaN(id)) {
				this.status = 3;
				this.loadForm(id);
			}
		} else {
			var offset = query == this.query ? this.offset : 0;
			this.loadList(2, offset, query)
		}
	},
	
	/* status:0 -- pending
	 * status:1 -- processed
	 * status:-1 -- unchecked
	 * status:2 -- search
	 * status:3 -- find by id
	 */
	loadList: function(status, offset, query) {
		var that = this;
		
		$.ajax({
			type: "GET",
			url: window.location.href,
			data: {
				status: status,
				offset: offset,
				max: this.max,
				format: 'json',
				query: query
			},
			dataType: 'json',
			cache: false
		}).done(function(data){
			that.activeStatis(status);
			that.updateStatis(data.statis);
			that.offset = offset;
			that.status = status;
			that.query = query;
			
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
			data: {
				status: this.status,
				query: this.query
			},
			dataType: 'json',
			cache: false
		}).done(function(data){
			that.model = new model.Form(data.form, {term: data.term});
			that.$content.html(that.formTemplate(that.model));
			that.updateStatis(data.statis);
		});
	},
	
	activeStatis: function(status) {
		this.element.find(".sidebar a").removeClass("active");
		this.element.find(".sidebar a[data-status='" + status +"']").addClass("active");
		this.status = status;
		
		if(this.status == 1) {
			this.element.find(".sidebar .search").show();
		} else if(this.status == 2) {
			this.element.find(".sidebar a[data-status='1']").addClass("active");
			this.element.find(".sidebar .search").show();
		} else {
			this.element.find(".sidebar .search").hide();
		}
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
			this.post(id, 4, $.trim($textarea.val()));
		}
	},
	
	disapprove: function(id) {
		var $textarea = this.element.find("textarea");
		if(validate.size($textarea)) {
			this.post(id, 3, $.trim($textarea.val()));
		}
	},
	
	closeForm: function(id) {
		var $textarea = this.element.find("textarea");
		if(validate.size($textarea)) {
			if(confirm("确定要关闭申请吗？")) {
				this.post(id, 5, $.trim($textarea.val()));
			}
		}
	},
	
	revokeForm: function(id) {
		var $textarea = this.element.find("textarea");
		if(validate.size($textarea)) {
			if(confirm("确定要回收申请吗？")) {
				this.post(id, 6, $.trim($textarea.val()));
			}
		}
	},
	
	post: function(id, status, comment) {
		var that = this
		  , data = {status: status};
		if(comment.length != 0) {
			data.comment = comment;
		}
		
		if(this.model.hasOccupied()) {
			data.occupied = this.model.getOccupiedItems();
		}
		
		$.ajax({
			type: "POST",
			url: window.location.href + "/" + id,
			data: JSON.stringify(data),
			contentType: 'application/json'
		}).done(function() {
			that.loadForm(id);
		});
	}
});
}(window.jQuery, doT, moment, tms.validate, tms.place.booking.format, tms.place.booking.model));
