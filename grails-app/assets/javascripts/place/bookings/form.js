//= encoding UTF-8
//= require lib/doT.min
//= require lib/moment-with-locales.min
//= require model
//= require format
//= require_self

(function($, doT, moment, model, format, undefined) {
$.widget("ui.bookingForm", {
	_create: function () {
		var that = this;
		moment.lang("zh-cn");
		
		this.model = new model.Form(this.options.form);
		
		this.itemsTemplate  = doT.template($("#items-template").html());
		this.auditsTemplate = doT.template($("#audits-template").html());
		this.typesTemplate  = doT.template($("#types-template").html()); 
		this.$department    = this.element.find("select[name='department']");
		this.$bookingType   = this.element.find("select[name='bookingType']");
		this.$reason        = this.element.find("textarea[name='reason']");
		
		this.element.on("click", "#addItems", function() {
			that.showDialog();
		}).on("click", "#saveForm", function(e) {
			that.saveModel();
		}).on("click", ".removeItem", function(e) {
			that.removeItem(e);
		}).on("mouseenter", ".form-detail li", function() {
			$(this).find(".removeItem").show();
		}).on("mouseleave", ".form-detail li", function() {
			$(this).find(".removeItem").hide();
		}).on("click", "#continue", function() {
			$("#notice").hide();
			$("#form").show();
		});
		
		this.$department.change(function() {
			that.loadTypes(that.$department.find("option:selected").val()).done(function(data) {
				that.$bookingType.html(that.typesTemplate(data));
			});
		});
		
		this.$dialog = $("#dialog").searchRoom({
			model: this.model,
			term: this.options.term,
			days: this.options.days,
			today: this.options.today,
			conflicts: this.options.conflicts,
			change: function() {
				var i;
				for(i = 1; i < arguments.length; i++) {
					that.model.addItem(arguments[i], that.options.conflicts);
				}
				that.updateView();
			}
		});
		
		this.updateView();
		if(this.model.audits.length > 0) {
			this.updateAudits();
		}
		
		if(this.options.form) {
			$("#form").show();
		} else {
			$("#notice").show();
		}
	},
	
	saveModel: function() {
		var that = this
		  , v1 = this.validateReason()
		  , v2 = this.validateItems()
		  ;
		
		if(!v1 || !v2) {
			return;
		}
		
		this.model.departmentId  = this.$department.val();
		this.model.bookingTypeId = parseInt(this.$bookingType.val());
		this.model.reason        = $.trim(this.$reason.val());
		this.model.save().done(function(data) {
			if(!that.options.form) {
				window.location.href = "./" + data.id
			} else {
				window.location.href = "../" +　that.model.formId
			}
		});
	},
	
	validateReason: function() {
		var reason = $.trim(this.$reason.val())
		  , valid = reason.length >= 10 && reason.length <=100
		  ;
		if(valid) {
			this.$reason.closest('div').removeClass('has-error')
		} else {
			this.$reason.closest('div').addClass('has-error');
		}
		return valid;
	},
	
	validateItems: function() {
		if(this.model.items.length > 0) {
			return true;
		} else {
			this.element.find(".form-detail p").removeClass("text-warning").addClass("text-danger");
			return false;
		}
	},
	
	showDialog: function() {
		this.$dialog.searchRoom("showDialog");
	},
	
	removeItem: function(e) {
		var index = $(e.target).closest("li").data("index");
		this.model.removeItem(index);
		this.updateView();
	},
	
	updateView: function() {
		this.element.find(".form-detail").html(this.itemsTemplate({
			items: this.model.items
		}));
	},
	
	updateAudits: function() {
		this.element.find(".form-audits").html(this.auditsTemplate({
			audits: this.model.audits
		}));
	},
	
	loadTypes: function(deptId) {
		return $.ajax({
			type: "GET",
			url: "./types/" + deptId,
			cache: false
		});	
	}
});

$.widget("ui.searchRoom", {
	_create: function () {
		var that = this;
		this.model = this.options.model;
		this.$searchButton   = this.element.find("button.search");
		this.$selectButton   = this.element.find("button.select");
		this.$okButton       = this.element.find("button.ok");
		this.$deselectButton = this.element.find("button.deselect");
		this.$selectableList = this.element.find("select.rooms-selectable");
		this.$selectedList   = this.element.find("select.rooms-selected");
		this.$sectionType    = this.element.find("select[name='sectionType']");
		this.$roomType       = this.element.find("select[name='roomType']");
		this.$params         = this.element.find(".params")
		if(this.options.days == 0 || this.options.days == -1) {
			this.createWeeks();
		} else {
			this.createDays();
		}
		
		$.each(format.getSectionTypes(), function() {
			that.$sectionType.append($("<option/>").attr("value", this.id).text(this.name));
		});
		
		this.$searchButton.click(function() {
			that.findRoom();
		});
		
		this.$selectableList.change(function() {
			that.$selectButton.prop("disabled", that.$selectableList.find("option:selected").length == 0);
		}).dblclick(function() {
			that.insertSelected();
		});
		
		this.$selectedList.change(function() {
			that.$deselectButton.prop("disabled", that.$selectedList.find("option:selected").length == 0);
		}).dblclick(function() {
			that.removeSelected();
		});
		
		this.$selectButton.click(function() {
			that.insertSelected();
		});
		
		this.$deselectButton.click(function() {
			that.removeSelected();
		});
		
		this.$okButton.click(function() {
			var items = [];
			that.$selectedList.find("option").each(function() {
				items.push($(this).data("item"));
			});
			if(items.length > 0) {
				that._trigger("change", null, items);
			}
			that.element.modal("hide");
		});
	},
	
	createWeeks: function() {
		var that = this;
		this.$startWeek = this.element.find("select[name='startWeek']");
		this.$endWeek = this.element.find("select[name='endWeek']");
		for(var i = this.options.term.currentWeek; i <= this.options.term.maxWeek; i++) {
			if(this.options.days == -1 || (this.options.days == 0 && i <= this.options.term.currentWeek + 2)) {
				this.$startWeek.append($("<option/>").attr("value", i).text(i));
			}
			this.$endWeek.append($("<option/>").attr("value", i).text(i));
		}
		this.$dayOfWeek = this.element.find("select[name='dayOfWeek']");
		for(var i = 1; i <= 7; i++) {
			this.$dayOfWeek.append($("<option/>").attr("value", i).text(format.dayOfWeek(i)));
		}
		
		this.$startWeek.change(function() {
			var val = $(this).val();
			if(parseInt(val) > parseInt(that.$endWeek.val())) {
				that.$endWeek.val(val);
			}
		});
		
		this.$endWeek.change(function() {
			var val = $(this).val();
			if(parseInt(val) < parseInt(that.$startWeek.val())) {
				that.$startWeek.val(val);
			}
		});
	},
	
	createDays: function() {
		var that = this
		  , day = moment(this.options.today)
		  , today = moment(this.options.today)
		  , startDay = moment(this.options.term.startDate)
		  ;
		
		if(today.isBefore(startDay)) {
			day = moment(startDay);
			today = moment(startDay);			
		} else {
			day.add('days', 1); // from next day
		}
		
		this.$days = this.element.find("select[name='days']");
		for(var i = 0; i < this.options.days; i++) {
			
			var week = this.options.term.currentWeek + day.isoWeek() - today.isoWeek();
			if(week < this.options.term.currentWeek) { // cross years
				week += today.isoWeeksInYear();
			}
			if(week > this.options.term.maxWeek) {
				break;
			}
			$("<option/>")
				.text([format.week(week), day.format("dddd")].join(" "))
				.data("week", week)
				.data("dayOfWeek", day.isoWeekday())
				.data("date", day.format("YYYY-MM-DD"))
				.appendTo(this.$days);
			day.add('days', 1);
		}
		
		this.$days.change(function() {
			that.element.find(".date").text($(this).find("option:selected").data("date"));
		});
		this.element.find(".date").text(this.$days.find("option").first().data("date"));
	},
	
	showDialog: function() {
		this.$selectableList.html("");
		this.$selectedList.html("");
		this.element.modal();
	},
	
	insertSelected: function() {
		var that = this;
		this.$selectableList.find("option:selected").each(function() {
			var $selectedItem = $(this)
			  , item = $selectedItem.data("item");
			
			$selectedItem
				.prop("selected", false)
				.wrap("<span/>")
				.hide();
			$("<option/>")
				.text(item.toShortString())
				.data("ref", $selectedItem)
				.data("item", item)
				.appendTo(that.$selectedList);
		});
		this.$selectButton.prop("disabled", true);
	},
	
	removeSelected: function($selectedItem) {
		var that = this;
		this.$selectedList.find("option:selected").each(function() {
			var $selectedItem = $(this);
			$selectedItem.data("ref").unwrap();
			$selectedItem.remove();
		});
		this.$deselectButton.prop("disabled", true);
	},
	
	findRoom: function() {
		var that = this
		  , params = {
				sectionType: parseInt(this.$sectionType.val()),
				roomType: this.$roomType.val()
			}
		  ;
		if(this.options.days == 0 || this.options.days == -1) {
			params.startWeek = parseInt(this.$startWeek.val());
			params.endWeek   = parseInt(this.$endWeek.val());
			params.dayOfWeek = parseInt(this.$dayOfWeek.val());
		} else {
			var option = this.$days.find("option:selected");
			params.startWeek = option.data("week");
			params.endWeek   = option.data("week");
			params.dayOfWeek = option.data("dayOfWeek");
		}
		
		this.$selectableList.html("");
		
		// 处理校历日期调整。
		var term = this.options.term;
		if(params.startWeek == params.endWeek) {
			var day = moment(term.startDate, 'YYYY-MM-DD');
			day.add((params.startWeek - term.startWeek), "WEEKS");
			day.add((params.dayOfWeek - 1), "DAYS");
			var str = day.format("YYYY-MM-DD");
			for(var i = 0; i < term.swapToDates.length; i++) {
				if(term.swapToDates[i] == str) {
					return;
				}
			}
		}
		
		this.$searchButton.button("loading");
		$.ajax({
			type: "GET",
			url: "/tms/bookings/findRoom", 
			contentType: 'application/json',
			data: params,
			cache: false,
			dateType: "json"
		}).done(function(data){
			$.each(data, function() {
				var item = new model.Item($.extend({
					roomId:          this.ID,
					roomName:        this.NAME,
					roomSeat:        this.SEAT,
					reservedCount:	 this.RESERVED_COUNT
				}, params));
				if(!that.isConflict(item)) {
					$("<option/>")
						.text(item.roomName + " ( " + item.roomSeat + "座 )" + (item.reservedCount > 0 ? " [预定" + item.reservedCount + "次]" : ""))
						.data("item", item)
						.appendTo(that.$selectableList);
				}
			});
		}).always(function() {
			that.$searchButton.button("reset");
		});
	},
	
	isConflict: function(item) {
		var that = this;
		// 判断模型中已选
		if(this.model.isConflict(item, this.options.conflicts)){
			return true;
		}
		// 判断对话框中已选
		var conflict = false;
		this.$selectedList.find("option").each(function() {
			if($(this).data("item").isConflict(item, that.options.conflicts)) {
				conflict = true;
				return false; // exit loops
			}
		});
		return conflict;
	}
});
}(window.jQuery, doT, moment, tms.place.booking.model, tms.place.booking.format));