//= require lib/doT.min
//= require model
//= require_self

(function($, doT, Model, undefined) {
/* 详细视图
 * ======= */
$.widget("ui.detailView", {
	_create: function () {
		var that = this
		  , template = doT.template($("#detail-view-template").html())
		  ;

		this.viewModel = this.options.viewModel;

		// 使用模板创建视图
		this.element.append($(template({
			students: this.viewModel.students
		})));
		
		// 注册按钮监听器
		this.element.on("click", ".rowbar button", function(e) {
			that.buttonClick($(e.target));
		});
		
		// 当学生状态变化时调用
		$(this.viewModel).on({
			studentSaved: function(e, student) {
				that.stateChanged(student);
			},
			
			studentSaveError: function(e, student) {
				var $tr = that.findRow(student)
				  , $toolbar = $tr.find("td.rowbar .btn-group")
				  ;
				$toolbar.removeClass("wait");
				$toolbar.children('button').removeAttr('disabled', 'disabled');
			},
			
			visible: function(e, student) {
				that.visibleChanged(student);
			}
		});
	},
	
	buttonClick: function($button) {
		var $toolbar = $button.closest(".btn-group")
		  , state = $button.data("opkey")
		  , studentId = $button.closest("tr").data("id")
		  ;
		$button.blur();
		// 禁用工具栏
		$toolbar.parent("td").addClass("wait");
		$toolbar.children('button').attr('disabled', 'disabled');
		this.viewModel.toggleById(studentId, state);
	},
	
	stateChanged: function(student) {
		var $tr = this.findRow(student)
		  , $toolbar = $tr.find("td.rowbar .btn-group")
		  ;
		
		// 使能工具栏
		$toolbar.parent("td").removeClass("wait");
		$toolbar.children('button').removeAttr('disabled', 'disabled');
		$toolbar.children('button').each(function() {
      		var $button = $(this) 
      		  , key = $button.data('opkey')
      		  , highlight = student.hasState(key) ? 'btn-' + key : ''
      		  ;
      		if(highlight) {
      			if(!$button.hasClass(highlight)) {
      				$button.addClass(highlight);    				
      			}
      		} else {
      			$button.removeClass(Model.buttonClasses);
      		}
      	});
		
		// 更新统计
		$tr.find(".statis").text(student.statis.join(" / "));
	},
	
	visibleChanged: function(student) {
		var $tr = this.findRow(student)
		if(student.visible) {
			$tr.show();
   		} else {
   			$tr.hide();
   		}
	},
	
	findRow: function(student) {
		return this.element.find("tr[data-id='" + student.id + "']")
	}
});
}(jQuery, doT, tms.rollcall.model));