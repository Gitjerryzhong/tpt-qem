//= require lib/doT.min
//= require_self

(function($, doT, undefined) {
/* 锁定视图
 * ======= */
$.widget("ui.lockView", {
	_create: function () {
		var that = this
		  , template = doT.template($("#lock-view-template").html())
		  ;

		this.viewModel = this.options.viewModel;

		// 使用模板创建视图
		this.element.append($(template({
			students: this.viewModel.students
		})));
	}
});
}(jQuery, doT));