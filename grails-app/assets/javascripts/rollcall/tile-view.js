//= require lib/doT.min
//= require model
//= require_self

(function($, Model, undefined) {
/* 平铺视图
 * ======= */
$.widget("ui.tileView", {
	_create: function() {
		var that = this
		  , template = doT.template($("#tile-view-template").html())
      	  ;
		
		this.viewModel = this.options.viewModel;
		
		// 使用模板创建视图
		this.element.append($(template({
			students: this.viewModel.students
		})));

		// 注册按钮监听器
		this.element.on("click", ".tile button", function(e) {
			that.buttonClick($(e.target));
		});
		
		// 当学生状态变化时调用
		$(this.viewModel).on({
			studentSaved: function(e, student) {
				that.stateChanged(student);
			},
			
			studentSaveError: function(e, student) {
				var $tile = that.findTile(student)
				  , $toolbar = $tile.find(".btn-group")
				  ;
				$toolbar.removeClass("wait");
				$toolbar.children('button').removeAttr('disabled', 'disabled');
			},
			
			visible: function(e, student) {
				that.visibleChanged(student);
			}
		});
		
		this._hoverable(this.element.find(".tile"));
	},
	
	buttonClick: function($button) {
		var $toolbar = $button.closest(".btn-group")
		  , state = $button.data("opkey")
		  , studentId = $button.closest(".tile").data("id")
		  ;
		$button.blur();
		// 禁用工具栏
		$toolbar.parents(".wrapper").addClass("wait");
		$toolbar.children('button').attr('disabled', 'disabled');
		this.viewModel.toggleById(studentId, state);
	},
	
	stateChanged: function(student) {
		var $tile = this.findTile(student)
		  , $toolbar = $tile.find(".btn-group")
		  , $status = $tile.find(".status")
		  ;
		
		// 使能工具栏
		$toolbar.parents(".wrapper").removeClass("wait");
		$toolbar.children('button').removeAttr('disabled', 'disabled');
		$toolbar.children('button').each(function() {
      		var $button = $(this) 
      		  , key = $button.data('opkey')
      		  , highlight = student.hasState(key) ? 'btn-' + key : ''
      		  ;
      		if(highlight) {
      			if(!$button.hasClass(highlight)) {
      				$button.addClass(highlight)        				
      			}
      		} else {
      			$button.removeClass(Model.buttonClasses);
      		}
      	});
			
		// 更新统计
		$tile.find(".statis").text(student.statis.join(" / "));
		
		// 更新标签 
      	$.each(Model.states, function(key, state) {
      		var $label = $status.find(".label-" + key);
  			if(student.hasState(key)) {
  				if($label.length == 0) {
  					$status.append($("<label class='label'/>").addClass('label-' + key).text(state.text));
  				}
  			} else {
  				$label.remove();
  			}
  		});
	},
	
	visibleChanged: function(student) {
		var $tile = this.findTile(student)
		if(student.visible) {
			$tile.show();
   		} else {
   			$tile.hide();
   		}
	},
	
	findTile: function(student) {
		return this.element.find(".tile[data-id='" + student.id + "']")
	}
});
}(jQuery, tms.rollcall.model));