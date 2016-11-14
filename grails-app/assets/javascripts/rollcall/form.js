//= require ui/week-tabs
//= require format
//= require model
//= require summary
//= require toolbar
//= require detail-view
//= require list-view
//= require tile-view
//= require lock-view
//= require_self

(function($, Model, format, undefined) {
$.widget("ui.rollcallForm", {
	_create: function() {
		var that = this
		  ;
		// 创建视图模型
		this.viewModel   = new Model.ViewModel(this.options);
		
		
		// 创建周次TAB
    	this.$tabs = this.element.find(".week-tabs").weekTabs({
    		start   : this.viewModel.currentArrangement.startWeek,
    		end     : this.viewModel.currentArrangement.endWeek,
    		active  : this.options.week,
        	step    : this.viewModel.currentArrangement.oddEven == 0 ? 1 : 2,
        	change  : function(e, week){
        		window.location.href = "./" + week;
            }
        });
    	
		// 创建工具栏
		this.$toolbar = $("#toolbar");
		this.$toolbar.rollcallToolbar({viewModel: this.viewModel}, {
			"switch": function(e, view) {that.switchView(view);}
		});
		this.$summary = $('#summary').summary({viewModel: this.viewModel});
		
		if(this.viewModel.isLocked()) { // locked
			$("#lock-view").lockView({viewModel : this.viewModel}).show();
		} else {
			// 初始视图
			this.switchView(this.options.view?this.options.view:"detail");
			
			// 设置对话框
			this._initSettingDialog();
		}
	},
	
	switchView: function(view) {
		var $view = $("#" + view + "-view")
		if(this.$currentView != $view) {
			if(this.$currentView) {
				this.$currentView.hide();
			}
			this.$currentView = $view;
			// if not create plugin, create it
			if(!this.$currentView.is(":ui-" + view + "View")) {
				this.$currentView[view+ "View"]({viewModel : this.viewModel});
			}
			this.$currentView.show();
			if(view == 'list') {
				this.$currentView.focus();
			}
			if(view != this.options.view) {
				this.options.view = view;
				this.viewModel.saveSettings("rollcall.view", view);
			}
		}
	},
	
	// 根据用户配置设置界面
	_initSettingDialog: function() {
		var that = this
		  , settings = this.options.settings
		  , $hideFree = $("#settingDialog input[name=hideFree]")
		  , $hideLeave = $("#settingDialog input[name=hideLeave]")
		  , $hideCancel = $("#settingDialog input[name=hideCancel]")
		  , $random = $("#settingDialog input[name=random]")
		  ;
		if(this.options.settings) {
			// 根据settings设置界面
			$hideFree.prop("checked", settings.hideFree);
			$hideLeave.prop("checked", settings.hideLeave);
			$hideCancel.prop("checked", settings.hideCancel);
			$random.prop("checked", settings.random >= 10 && settings.random <= 90);
			if(settings.random >= 10 && settings.random <= 90) {
				$("#settingDialog select.ratio").val(settings.random).prop('disabled', false);
			} else {
				$("#settingDialog select.ratio").prop('true', false);
			}
			
		}
		// 随机点名使能比例选择
		$random.on("change", function() {
			$("#settingDialog select.ratio").prop('disabled', !$random.prop("checked"));
		});
		$("#settingConfirm").on("click", function() { that._onSettingConfirm(); })
	},
	
	// 用户点击确定
	_onSettingConfirm: function() {
		var settings = this.options.settings
		  , free = $("#settingDialog input[name=hideFree]").prop("checked")
		  , leave = $("#settingDialog input[name=hideLeave]").prop("checked")
		  , cancel = $("#settingDialog input[name=hideCancel]").prop("checked")
		  , random = $("#settingDialog input[name=random]").prop("checked")
		  , ratio
		  ;
		
		if(random) {
			ratio = $("#settingDialog select.ratio").val();
		} else {
			ratio = 100;
		}
		$("#settingDialog").modal("hide");
		
		this.viewModel.hide(free, leave, cancel, ratio);
	}
});
}(jQuery, tms.rollcall.model, tms.format));