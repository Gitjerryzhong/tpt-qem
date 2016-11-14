(function($, undefined) {
	$.widget("ui.adminClassesAddressList", {
		_create: function() {
			var adminClass = this._createSelection();
			this._getList(adminClass)
		},
		
		_createSelection: function() {
			var that = this
			  , adminClasses = this.options.adminClasses
			  , $toolbar = this.element.find(".toolbar");
			switch(adminClasses.length) {
			case 0:
				$("<p>尚无数据</p>").appendTo($toolbar);
				return null;
			case 1:
				return adminClasses[0];
			default:
				var $select = $("<select class='form-control input-sm'/>");
				$.each(adminClasses, function() {
					$select.append("<option>" + this + "</option>");
				});
				$("<div class='col-md-3'/>").append($select).appendTo($toolbar);
				$select.change(function() {
					that._getList($select.val());
				});
				return adminClasses[0];
			}
		},
		
		_getList: function(adminClass) {
			var that = this;
			$.ajax({
				type: "GET",
				url: "./adminClass/" + encodeURI(adminClass),
    			contentType: 'application/json',
    			dataType: 'json',
    			cache: false,
				success: function(data) {
					that._fillTable(status, data);
				}
			});
		},
		
		_fillTable: function(status, data) {
			var that = this
			  , $table = $(".table", this.element)
			  , columns = this.COLUMNS
			  , $thead
 			  , $tr;

			$table.empty();
			if(data.length == 0) {
				return;
			}
			// ths
			$thead = $("<thead/>").appendTo($table)
			$tr = $("<tr/>")
			$.each(columns, function(key, value) {
				$("<th/>").text(value.label).appendTo($tr);
			});
			$tr.appendTo($thead);
			
			// trs
			$.each(data, function(index, row) {
				$tr = $("<tr/>");
				$.each(columns, function(key) {
					$("<td/>").text(row[key] == null ? "" : row[key]).appendTo($tr);
				});
				$tr.appendTo($table);
			});
		},
		
		COLUMNS: {
			id         : {label:"学号"},
			name       : {label:"姓名"},
			longPhone  : {label:"长号"},
			shortPhone : {label:"短号"},
			email      : {label:"电子邮件"}
		}
	});
}(jQuery));