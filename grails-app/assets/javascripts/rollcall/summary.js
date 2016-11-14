//= require lib/doT.min
//= require_self

(function($, doT, undefined) {
$.widget("ui.summary", {
	_create: function() {
		this.template = doT.template($("#summary-template").html())
		this._on(this.options.viewModel, {studentSaved:"refresh", visible:"refresh"});
		this.refresh();
		
	},
	
	refresh: function() {
    	var summary = this.options.viewModel.getSummary()
    	this.element.html(this.template(summary));
    	this.element.find("span[data-toggle='tooltip']").tooltip();
    }
});
}(jQuery, doT));