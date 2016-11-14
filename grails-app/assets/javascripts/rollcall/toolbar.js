//= require lib/doT.min
//= require format
//= require_self

(function($, doT, format, undefined) {
$.widget("ui.rollcallToolbar", {
	_create: function() {
		var that = this;
		this.viewModel = this.options.viewModel;
		
		this.term = this.viewModel.options.term;
		this.currentWeek = this.viewModel.options.week;
		this.arrangements = this.viewModel.arrangements;
		this.currentArrangement = this.viewModel.currentArrangement;
		
		this.createArrangementDropDown();
		
		// 切换视图按钮
		if(this.viewModel.isLocked()) {
			this.element.find(".buttons").empty().append("<span>点名已锁定</span>")
		} else {
			$.each(["detail", "list", "tile"], function(index, view) {
				that.element.find(".switch-view ." + view + "-view").on("click", function(event) {
					that._trigger("switch", null, view);
				});
			});
			
			if(this.viewModel.options.view) {
				that.element.find(".switch-view ." + this.viewModel.options.view + "-view").button('toggle');
			}
		}
		
		// 全勤
		this.$perfect = this.element.find(".perfect");
		this.$perfect.on("click", function() {
			that.viewModel.perfect();
		});
		
		this._on(this.viewModel, {
			perfectSaved: "updatePerfect", 
			studentSaved: "updatePerfect"}
		);
		this.updatePerfect();
	},
	
	updatePerfect: function() {
		var disable = this.viewModel.submitted();
		if(!this.$perfect.prop('disabled') && disable) {
			this.$perfect
			    .addClass("disabled")
			    .prop("disabled", true)
			    .prop("title", "已提交考勤，可继续编辑")
			    .text("已考勤");
		}
	},
	
	createArrangementDropDown: function() {
		var that = this
		  , template = doT.template("<li><a tabindex='-1' href='{{=it.url}}'>{{=it.label}}</a></li>")
		  , $arrs = this.element.find('.arrangementsDropdown .dropdown-menu')
		  ;
		this.element.find('.arrangementsDropdown .arrangement').text(this.arrangementLabel(this.currentArrangement));
		
		$.each(this.arrangements, function() {
			if(this.isVisible(that.currentWeek)) {
				$arrs.append($(template({
					 url:'../' + this.id + "/" + that.currentWeek,
					 label: that.arrangementLabel(this)
				})));
			}
		});
	},
	
	arrangementLabel: function(arr) {
		return [arr.dayOfWeekText(), arr.sectionsText(), arr.courseText()].join(' ')
	}
});
}(jQuery, doT, tms.format));