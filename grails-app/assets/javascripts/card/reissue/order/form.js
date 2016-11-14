//= require lib/doT.min
//= require lib/moment-with-locales.min
//= require model
//= require_self

(function($, doT, Order, undefined) {
$.widget("ui.cardReissueOrderForm", {
	_create: function () {
		var that = this;
		this.model = new Order(this.options);
		this.itemsTemplate = doT.template($("#items-template").html());
		this.selectTemplate = doT.template($("#select-template").html());
		this.element.on("click", "#addItems", function(e){
			that.addItems();
		}).on("click", ".removeItem", function(e) {
			that.removeItem(e);
		}).on("click", "#saveOrder", function(e) {
			var button = $(e.currentTarget);
			button.prop("disabled", true);
			that.model.save().done(function(data){
				if(that.options.order) { // 保存
    				window.location.href = "../" +　that.options.order.orderId				
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
				url: "/tms/cardReissueRequests", 
				data: {
					status: 1,
					format: 'json'
				},
				cache: false,
				success: function(data) {
					that.optionItems = [];
					that.optionItemsMap = {};
					$.each(data.requests, function() {
						that.optionItemsMap[this.id] = this;
						that.optionItems.push(this)
					});
					that.showDialog();
				}
			});
		} else {
			this.showDialog();
		}
	},
	
	showDialog: function() {
		var that = this
		  , $dialog = $("#dialog")
		  , requests = [];
		
		$.each(this.optionItems, function() {
			if(!that.model.contains(this.id)) {
				requests.push(this)
			}
		});
		
		$dialog.find(".modal-body").html(
			requests.length == 0 ? "没有可选择的申请。" : this.selectTemplate({requests:requests})
		);
		
		$dialog.modal();
	},
	
	removeItem: function(e) {
		var requestId = $(e.target).closest("tr").data("request-id");
		this.model.removeItem(requestId);
		this.updateView();
	},
	
	updateView: function() {
		$("#items").html(this.itemsTemplate({
			items: this.model.items
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
				var selectedItem = that.optionItemsMap[$(this).data("id")];
				that.model.addItem(selectedItem);
			});
			that.updateView();
			$dialog.modal('hide');
		});
	}
});
}(window.jQuery, doT, tms.card.reissue.model.Order));