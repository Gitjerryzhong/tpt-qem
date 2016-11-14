(function($, undefined) {
	STATUSES = {
		2: {className:'label-success', label:"批准"},
		3: {className:'label-danger', label:"不批准"},
		4: {className:'label-info', label:"销假"}
	}

	$.widget("ui.leaveApprovalList", {
		_create : function() {
			var $element = this.element
			this.$detail = $.ui.leaveApprovalDetail();
			
			this._on($("ul.nav > li > a", this.element), {click:"_changeStatus"});
			
			this._getList(1);
		},
		
		_changeStatus: function(e) {
			var $target = $(e.target)
			  , status = $target.data("status")
			e.preventDefault(); // Must, else append # after url.
			$("ul.nav li", this.element).removeClass("active");
			$target.parent().addClass("active");
			
			this._getList(parseInt(status));
		},
		
		_fillTable: function(status, data) {
			var that = this
			  , $table = $("#list")
			  , columns = this["COLUMNS" + status]
			  , $thead
 			  , $tr;

			that.$detail.option({"activeRow": null});
			$table.empty();
			if(data.length == 0) {
				$table.append($("<thead><tr><th>尚无数据</th></tr></thead>"));
				return;
			}
			// ths
			$thead = $("<thead/>").appendTo($table)
			$tr = $("<tr/>")
			$.each(columns, function(key, value) {
				$("<col/>").css("width", value.width).appendTo($table);
				$("<th/>").text(value.label).appendTo($tr);
			});
			$tr.appendTo($thead);
			
			// trs
			$.each(data, function(index, row) {
				$tr = $("<tr/>");
				$tr.data("requestId", row.id)
				$.each(columns, function(key) {
					if(status == 2 && key == "status") {
						var statusInfo = STATUSES[row[key]];
						$("<td/>").append($("<span>")
							.addClass("label")
							.addClass(statusInfo.className)
							.text(statusInfo.label)
						).appendTo($tr);
					} else {
						$("<td/>").text(row[key]).appendTo($tr);
					}
				});
				$tr.appendTo($table);
				that._hoverable($tr);
				$tr.click(function(e) {
					e.preventDefault();
					$("tr").removeClass("selected");
					$(this).addClass("selected");
					that.$activeRow = $(this)
					that.$detail.option({"activeRow": that.$activeRow, status: status});
				});
			});
		},
		
		
		_getList: function(status) {
			var that = this;
			$.ajax({
				type: "GET",
				url: window.location.href,
				data: {status:status},
    			contentType: 'application/json',
    			dataType: 'json',
    			cache: false,
				success: function(data) {
					that._fillTable(status, data);
				}
			});
		},
		
		COLUMNS1: {
			studentId    : {label:"学号", width:80},
			studentName  : {label:"姓名", width:60},
			type         : {label:"类型", width:50},
			dateModified : {label:"请假时间", width:140},
			adminClass   : {label:"班级"},
			status       : {label:"", width:70}
		},
		
		COLUMNS2: {
			studentId    : {label:"学号", width:80},
			studentName  : {label:"姓名", width:60},
			type         : {label:"类型", width:50},
			dateModified : {label:"请假时间", width:140},
			dateApproved : {label:"审批时间", width:140},
			status       : {label:"审核结果", width:80},
			adminClass   : {label:"班级"}
		}
	});

	$.widget("ui.leaveApprovalDetail", {
		defaultElement: "<tr>",
		
		_create : function() {
			var that = this;
			this.$td = $("<td class='detail'/>").appendTo(this.element);
			this.$items = $("<ul class='items list-unstyled'/>")
			this.$reason = $("<div class='reason'/>")
			this.$approve = $("<button class='btn btn-sm btn-success' title='编辑假条'>批准</button>");
			this.$refuse = $("<button class='btn btn-sm btn-danger' title='提交申请'>不批准</button>");
			this.$toolbar = $("<div class='btn-toolbar'>").append(this.$approve).append(this.$refuse);
			$("<div class='row'/>")
				.append($("<div class='col-md-4'/>").append("<label>条目</label>").append(this.$items))
				.append($("<div class='col-md-8'/>")
						.append("<label>事由</label>")
						.append(this.$reason)
						.append(this.$toolbar))
				.appendTo(this.$td);
			this.$approve.click(function(){
				that.changeStatus(2);
			});
			this.$refuse.click(function(){
				that.changeStatus(3);
			});
		},
		
		changeStatus: function(status) {
			var that = this
			  , $row = this.$activeRow
			  , id = this.$activeRow.data("requestId")
			  , statusInfo = STATUSES[status]
			  ;
			$.ajax({
				type: "POST",
				url: window.location.href + "/" + id,
				data: {status:status},
				success: function() {
					that.$activeRow.find("td:last").empty().append(
						$("<span class='label status'/>").addClass(statusInfo.className).text(statusInfo.label)
					);
					that.detach(true);
				}
			});
		},
		
		_setOption:function(key, value) {
			var that = this;
			if(key == "activeRow") {
				this.$activeRow = value;
								
				if(value != null) {
					this.detach(true);					
					this._getDetail();			
				} else {
					this.detach(false);
				}
			}
			
			this._superApply(arguments);
		},
		
		attach: function() {
			this.element.insertAfter(this.$activeRow);
			this.element.slideDown("fast");
		},
		
		detach: function(animate) {
			var that = this;
			if(animate) {
				this.element.slideUp('fast',function() {
					that.element.detach();
				});
			} else {
				this.element.detach();
			}
		},

		_getDetail: function() {
			var that = this
			  , requestId = this.$activeRow.data("requestId");
			$.ajax({
				type: "GET",
				url: window.location.href + "/" + requestId,
    			contentType: 'application/json',
    			dataType: 'json',
    			cache: false,
				success: function(data) {
					that._fillDetail(requestId, data);
				}
			});
		},
		
		_fillDetail: function(id, data) {
			var that = this;
			if(id != this.$activeRow.data("requestId")) {
				return;
			}
			
			this.element.stop(true, true);				
			
			var colSpan = this.$activeRow.children().length
			this.$td.prop("colspan", colSpan);
			
			var reviewed = this.$activeRow.find("td:last-child .status").length != 0;			
			if(this.options.status == 1 && !reviewed) {
				this.$approve.show();
				this.$refuse.show();
			} else {
				this.$approve.hide();
				this.$refuse.hide();
			}
			
			this.$items.empty();
			this.$reason.text(data.reason);
			
			$(data.items).each(function(){
				var $li = $("<li/>").text(this.text).appendTo(that.$items);
				switch(this.type) {
				case "week":
					$li.addClass("text-danger");
					break;
				case "day":
					$li.addClass("text-warning");
					break;
				case "arr":
					$li.addClass("text-info");
					break;
				}
			});

			this.attach();
		}
	});
}(jQuery));