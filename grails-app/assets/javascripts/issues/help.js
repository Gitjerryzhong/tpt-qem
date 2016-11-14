//= require lib/Markdown.Converter
//= require_self

(function($, Markdown, undefined) {
$.widget("ui.issueHelp", {
	_create: function () {
		var converter = new Markdown.Converter();
		this.element.find("pre").each(function() {
			var $pre = $(this)
			  , $well = $pre.parents("tr").find(".markdown");
			$well.html(converter.makeHtml($pre.html()));
		});
	}
});
}(window.jQuery, Markdown));