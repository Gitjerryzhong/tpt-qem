(function($, undefined) {
	$.widget("ui.cancelExamList", {
		_create : function() {
			var $element = this.element
			this.$detail = $.ui.cancelExamDetail();
			
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

			that.$detail.option({"activeRow": null}); // detach first
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
				$tr.data("studentId", row.id)
				   .data("courseId", row.courseId)
				   .data("status", row.status)
				$.each(columns, function(key) {
					var text;
					if(key == "dateCreated" || key == "lastUpdated") {
						text = new Date(row[key]).toLocaleString();						
					} else if(key == "status") {
						if(row[key] == null) {
							text = "未处理";
						} else if(row[key] == 0) {
							text = "已恢复";
						} else if(row[key] == 1) {
							text = "已取消";
						} else {
							text = "未知状态";
						}
					} else {
						text = row[key];
					}
					$("<td/>").text(text).appendTo($tr);
				});
				$tr.appendTo($table);
			});

			var $trs = $("tbody tr", $table);
			this._hoverable($trs);
			$trs.click(function(e) {
				e.preventDefault();
				$trs.removeClass("selected");
				$(this).addClass("selected");
				that.$activeRow = $(this)
				that.$detail.option({"activeRow": that.$activeRow, status: status});
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
			id    		: {label:"学号"},
			name 		: {label:"姓名"},
			adminClass  : {label:"班级"},
			courseName  : {label:"课程名称"},
			teacher 	: {label:"任课教师"},
			hours 		: {label:"总课时"},
			absent 		: {label:"旷课课时"},
			status      : {label:"状态"}
		},
		
		COLUMNS2: {
			id    		: {label:"学号"},
			name 		: {label:"姓名"},
			adminClass  : {label:"班级"},
			courseName  : {label:"课程名称"},
			creator 	: {label:"创建人"},
			dateCreated : {label:"创建时间"},
			updater		: {label:"修改人"},
			lastUpdated	: {label:"修改时间"},
			status		: {label:"状态"}
		}
	});

	$.widget("ui.cancelExamDetail", {
		defaultElement: "<tr>",
		
		_create : function() {
			var that = this;
			this.$td = $("<td class='detail'/>").appendTo(this.element);
			this.$items = $("<ul class='items list-unstyled'/>")
			this.$cancel = $("<button class='btn btn-sm btn-default'>取消考试资格</button>");
			this.$revoke = $("<button class='btn btn-sm btn-default'>恢复考试资格</button>");
			this.$toolbar = $("<div class='btn-toolbar'>").append(this.$cancel).append(this.$revoke);
			$("<div class='row'/>")
				.append($("<div class='col-md-6'/>").append(this.$items))
				.append($("<div class='col-md-6'/>").append(this.$toolbar))
				.appendTo(this.$td);
			this.$cancel.click(function(){
				that.changeStatus(1);
			});
			this.$revoke.click(function(){
				that.changeStatus(2);
			});
		},
		
		changeStatus: function(status) {
			var that = this
			  , $row = this.$activeRow
			  , studentId = this.$activeRow.data("studentId")
			  , courseId = this.$activeRow.data("courseId")
			  ;
			$.ajax({
				type: "POST",
				url: window.location.href + "/" + studentId,
				data: {
					course:courseId, 
					status:status
				},
				success: function() {
					that.$activeRow.data("status", status == 1 ? 1 : 0);
					that.$activeRow.find("td:last").text(status == 1 ? "已取消" : "已恢复");
					that.detach(true);
				}
			});
		},
		
		_setOption:function(key, value) {
			var that = this;
			if(key == "activeRow") {
				this.$activeRow = value;

				if(value != null) {
					this.detach(true)
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
			  , studentId = this.$activeRow.data("studentId")
			  , courseId = this.$activeRow.data("courseId");
			$.ajax({
				type: "GET",
				url: window.location.href + "/" + studentId,
				data: {course: courseId},
    			contentType: 'application/json',
    			dataType: 'json',
    			cache: false,
				success: function(data) {
					that._fillDetail(studentId, courseId, data);
				}
			});
		},
		
		_fillDetail: function(studentId, courseId, data) {
			var that = this;
			if(studentId != this.$activeRow.data("studentId") || 
			   courseId != this.$activeRow.data("courseId")) {
				return;
			}
			
			this.element.stop(true, true);				
			
			var colSpan = this.$activeRow.children().length
			this.$td.prop("colspan", colSpan);
			
			if(this.$activeRow.data("status") == null 
				|| this.$activeRow.data("status") == 0) {
				this.$cancel.show();
				this.$revoke.hide();
			} else {
				this.$cancel.hide();
				this.$revoke.show();
			}
			
			this.$items.empty();
						
			data.sort(function(a, b) {
				if(a.week != b.week) {
					return a.week - b.week;
				} else if(a.dayOfWeek != b.dayOfWeek) {
					return a.dayOfWeek - b.dayOfWeek;
				} else {
					return a.startSection - b.startSection;
				}
			});
			
			$.each(data, function(){
				var $type = $("<label class='label'/>").text(that.RC_TYPES[this.type]).addClass(that.RC_CLASSES[this.type])
				  , $text = $("<span/>").text("第" + this.week + "周 星期" + dayOfWeek(this.dayOfWeek) + " " +
						this.startSection + "-" + (this.startSection +　this.totalSection - 1) +
						"节 " + this.teacher + " " + this.courseClass);
				$("<li/>").append($type).append($text).appendTo(that.$items);
			});

			this.attach();
			
			function dayOfWeek(day) {
				var days = " 一二三四五六日";
				return days.charAt(day);
			}
		},
		
		RC_TYPES : ["", "旷课","迟到","早退","", "迟到+早退"],
		RC_CLASSES: ["", "label-absent", "label-late", "label-early", "", "label-early"],
	});
}(jQuery));