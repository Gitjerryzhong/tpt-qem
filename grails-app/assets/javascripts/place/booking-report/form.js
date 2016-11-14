//= require lib/doT.min
//= require lib/moment-with-locales.min
//= require model
//= require place/bookings/format
//= require_self

(function($, doT, moment, format, Report, undefined) {
$.widget("ui.bookingReportForm", {
	_create: function () {
		var that = this;
		this.model = new Report(this.options);
		this.itemsTemplate = doT.template($("#items-template").html());
		this.selectTemplate = doT.template($("#select-template").html());
		this.element.on("click", "#add-items", function(e){
			that.addItems();
		}).on("click", ".removeItem", function(e) {
			that.removeItem(e);
		}).on("click", "#save-report", function(e) {
			var button = $(e.currentTarget);
			button.prop("disabled", true);
			that.model.save().done(function(data){
				if(that.options.report) { // 保存
    				window.location.href = "../" +　that.options.report.reportId				
				} else { // 新建
					window.location.href = "./" + data.id					
				}
			}).fail(function() {
				alert("保存出错！");
				button.prop("disabled", false);
			});
		});

		this.setupDialog();
		
		if(this.model.items.length > 0) {
			this.updateView();
		}
	},
	
	addItems: function() {
		var that = this;
		if(!this.optionItems) {
			$.ajax({
				type: "GET",
				url: "/tms/bookingReport/unprocessed", 
				data: {format: 'json'},
				cache: false
			}).done(function(data) {
				that.optionItems = data;
				that.optionItemsMap = {};
				$.each(data, function() {
					that.optionItemsMap[this.bookingId] = this;
				});
				that.showDialog();
			});
		} else {
			this.showDialog();
		}
	},
	
	showDialog: function() {
		var that = this
		  , $dialog = $("#dialog")
		  , items = [];
		
		items = $.grep(this.optionItems, function(item) {
			return !that.model.contains(item.bookingId);
		});
		
		$dialog.find(".modal-body").html(
			items.length == 0 ? "没有可选择的申请。" : this.selectTemplate({items:items})
		);
		
		$dialog.modal();
	},
	
	removeItem: function(e) {
		var itemId = $(e.target).closest("tr").data("booking-id");
		this.model.removeItem(itemId);
		this.updateView();
	},
	
	updateView: function() {
		this.element.find(".report-items").html(this.itemsTemplate({
			saved: this.model.reportId != 0,
			items: this.model.items,
			moment: moment,
			format: format
		}));
	},
	
	setupDialog: function() {
		var that = this
		  , $dialog = $("#dialog")
		  ;
		
		$dialog.on("click", "tbody tr", function(e) { // 单击行勾选
			var $target = $(e.target);
			if(!$target.is(".check")) {
				var $checkbox = $(e.target).closest("tr").find(".check");
				$checkbox.prop("checked", !$checkbox.prop("checked"));				
			}
		}).on("change", ".check-all", function(e) { // 单击全选
			var $checkbox = $(e.target);
			$dialog.find(".check").prop("checked", $checkbox.prop("checked"));
		}).on("click", ".ok", function() { // 确定
			$dialog.find(".check:checked").each(function(){
				var selectedItem = that.optionItemsMap[$(this).data("booking-id")];
				that.model.addItem(selectedItem);
			});
			that.updateView();
			$dialog.modal('hide');
		});
	}
});
}(window.jQuery, doT, moment, tms.place.booking.format, tms.place.booking.report.model.Report));