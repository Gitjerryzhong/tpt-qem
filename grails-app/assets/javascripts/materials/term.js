//= require lib/doT.min
//= require_self

(function($, doT, undefined) {
	$.widget("ui.termMaterialList", {
		_create : function() {
			var that = this
			  , template = this.template = doT.template($("#row-template").html())
			  , optionTemplate = doT.template($("#option-template").html())
			  , $listBody = this.$listBody = $("#listBody")
			  , $terms = this.$terms = $("#terms")
			  , $checkboxes
			  ;	
			
			$terms.html(optionTemplate(this.options.terms))
				.val(this.options.term)
				.on("change", function() {
					that._loadData($terms.val());
				});
			
			
			$.each(this.options.materials, function() {
				that._insertRow(this);
			});
			
			this.map = {};
			$checkboxes = $("input[type='checkbox']");
			
			$checkboxes.each(function() {
				var $checkbox = $(this);
				that.map[$checkbox.data("mid")] = $checkbox;
			});
			
			$checkboxes.on("change", function() {
				var $checkbox = $(this) 
				  , checked = $checkbox.prop("checked")
				  , mid = $checkbox.data("mid")
				  , term = parseInt($terms.val())
				  ;
				if(checked) {
					that._insertItem(term, mid, $checkbox);
				} else {
					that._deleteItem($checkbox);
				}
			});
			
			this._loadData($terms.val());
		},
		
		_insertRow: function(item) {
			$tr = $("<tr/>")
				.html(this.template(item))
				.appendTo(this.$listBody);
		},
		
		_loadData: function(term) {
			var that = this;
			$.ajax({
				type: "GET",
				url: window.location.href + "/" + term,
    			dataType: 'json',
    			cache: false
			}).done(function(data) {
				that._fillData(data);
			});
		},
		
		_fillData: function(data) {
			var map = this.map;
			
			$("input[type='checkbox']", this.$listBody).prop("checked", false);
			$.each(data, function() {
				var $checkbox = map[this.mid];
				if($checkbox) {
					$checkbox.prop("checked", true).data("id", this.id);
				}
			});
		},
		
		_insertItem: function(term, mid, $checkbox) {
			$.ajax({
				type: "POST",
				url: window.location.href,
				data: {term:term, mid:mid},
				dataType: "json"
			}).done(function(data) {
				$checkbox.data("id", data.id);
			}).fail(function() {
				$checkbox.prop("checked", false);
				alert("无法插入");
			});
		},
		
		_deleteItem: function($checkbox) {
			var id = $checkbox.data("id");
			$.ajax({
				type : "DELETE",
				url : window.location.href + "/" + id
			}).fail(function() {
				$checkbox.prop("checked", true);
				alert("无法删除");
			});
		}
	});
}(jQuery, doT));