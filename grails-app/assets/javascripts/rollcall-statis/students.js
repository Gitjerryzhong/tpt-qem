//=require lib/doT.min
//=require model/rollcall-item
//=require model/leave-item

(function($, doT, Model, undefined) {
	$.widget("ui.rollcallStatisStudents", {
		_create : function() {
			var that = this;
			this.$detail = $.ui.rollcallStatisDetail();
			
			this.element.on("click", "tbody tr:not(.detail)", function(e) {
				var $row = $(e.currentTarget)
				if($row.hasClass("selected")) {
					$row.removeClass("selected");
					that.$detail.detach();
				} else {
					$("tbody tr", this.element).removeClass("selected");
					$row.addClass("selected");
					that.$detail.option({"activeRow": $row});
				}
			});
		}
	});

	$.widget("ui.rollcallStatisDetail", {
		defaultElement: "<tr class='detail'/>",
		
		_create : function() {
			var that = this;
			this.template = doT.template($("#detail-template").html())
			this.$td = $("<td class='detail'/>").appendTo(this.element);
			this.detached = true;
		},
		
		_setOption:function(key, value) {
			if(key == "activeRow") {
				this.detach();
				this.$activeRow = value;
				if(value) {
					this._getDetail(value.children(".id").text())									
				}
			}
			
			this._superApply(arguments);
		},
		
		detach: function() {
			var that = this;
			if(!this.detached) {
				this.element.detach();
				this.detached = true;
			}
		},

		_getDetail: function(id) {
			var that = this;
			$.ajax({
				type: "GET",
				url: "./student/" + id,
    			contentType: 'application/json',
    			dataType: 'json',
    			cache: false
			}).done(function(data) {
				that._fillDetail(id, data);
			}).done(function() {
				that.element.insertAfter(that.$activeRow);
				that.detached = false;
			});
		},
		
		_fillDetail: function(id, data) {
			var that = this
			  , rollcallItems = []
			  , leaveItems = [];
			
			var colSpan = this.$activeRow.children().length
			
			data.rollcallItems.sort(function(a, b) {
				if(a.week != b.week) {
					return a.week - b.week;
				} else if(a.dayOfWeek != b.dayOfWeek) {
					return a.dayOfWeek - b.dayOfWeek;
				} else {
					return a.startSection - b.startSection;
				}
			});
			
			data.courses.sort(function(a, b) {
				return a.hours / a.statis - b.hours / b.statis
			});
			
			data.leaveItems.sort(function(a, b) {
				if(a.week != b.week) {
					return a.week - b.week;
				} else if(a.dayOfWeek != b.dayOfWeek) {
					return a.dayOfWeek - b.dayOfWeek;
				} else {
					return a.startSection - b.startSection;
				}
			});
			
			$.each(data.rollcallItems, function() {
				rollcallItems.push(new Model.RollcallItem(this));
			});
			
			$.each(data.leaveItems, function() {
				leaveItems.push(new Model.LeaveItem(this));
			});
			
			this.element.html(this.template({
				rollcallItems: rollcallItems,
				leaveItems: leaveItems,
				courses: data.courses
			}));
		}
	});
}(jQuery, doT, tms.rollcall.model));