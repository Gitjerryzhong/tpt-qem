//= require lib/doT.min
//= require teach/model/arrangement

(function($, Arrangement, doT, undefined) {
	$.widget("ui.rollcallStatis", {
		_create : function() {
			var that = this
			  , $table = $("<table class='table'/>")
			  , theadTemplate = doT.template($("#thead-template").html())
			  , tbodyTemplate = doT.template($("#tbody-template").html())
			  ;
			
			this.$detail = $.ui.rollcallStatisDetail();

			// ths
			$table.append($(theadTemplate(this.options.term)));
			
			// trs
			var teacherIds = [];
			$.each(this.options.statis, function(key, value) {
				teacherIds.push(key);
				value.weekArrs = that._calcArrsByWeek(value.arrs);
			});
			teacherIds.sort();
			
			$table.append($(tbodyTemplate({
				term: this.options.term,
				ids: teacherIds,
				statis: this.options.statis
			})));
			
			$table.appendTo(this.element);
			
			$("td[data-id]", $table).click(function() {
				$("td", $table).removeClass("selected");
				$(this).addClass("selected");
				that.$detail.option({"activeCell": $(this)});
			});
		},
		
		_calcArrsByWeek: function(arrs) {
			var weekArrs = [];
			
			$.each(arrs, function() {
				var start   = this[0]
				  , end     = this[1]
				  , oddEven = this[2]
				  , count   = this[3]
				  , step
				  ;
				if(oddEven == 1 && start % 2 != 1 ||
				   oddEven == 2 && start % 2 != 0) {
					start++;
				}
						
				step = oddEven == 0 ? 1 : 2;
									
				for(var i = start; i <= end; i += step) {
					if(weekArrs[i]) {
						weekArrs[i] += count;
					} else {
						weekArrs[i] = count;
					}
				}
			});
			
			return weekArrs;
		}
	});
	
	$.widget("ui.rollcallStatisDetail", {
		defaultElement: "<tr>",
		
		_create : function() {
			var that = this;
			this.$week = $("<td class='week'/>").appendTo(this.element);
			this.$td = $("<td class='detail'/>").appendTo(this.element);
			this.template = doT.template($("#detail-template").html());
		},
		
		_setOption:function(key, value) {
			var that = this;
			if(key == "activeCell") {
				this.$activeCell = value;
				var teacherId = value.data("id")
				  , week = value.data("week")
				  ;
				
				that._getDetail(teacherId, week);				
			}
			
			this._superApply(arguments);
		},

		_getDetail: function(id, week) {
			var that = this
			$.ajax({
				type: "GET",
				url: window.location.href + "/" + id + "/" + week,
    			contentType: 'application/json',
    			dataType: 'json',
    			cache: false,
				success: function(data) {
					that._fillDetail(id, week, data);
				}
			});
		},
		
		_fillDetail: function(id, week, data) {
			var arrangements = [];
			if(id != this.$activeCell.data("id")) {
				return;
			}
			
			$.each(data, function() {
				arrangements.push(new Arrangement(this));
			});
			
			this.$week.text("第" + week + "周");
			
			this.$td.prop("colspan", this.$activeCell.parent().children().length - 1)
					.empty()
					.html(this.template(arrangements));
			
			this.element.insertAfter(this.$activeCell.parent());
		}
	});
}(jQuery, tms.teach.model.Arrangement, doT));