//= require lib/moment-with-locales.min
//= require_self

(function($, moment, undefined) {
$.widget("ui.issueList", {
	_create: function() {
		moment.lang("zh-cn");
		this.element.find(".info .date-updated").each(function() {
			var $date = $(this)
			  , date = $date.text()
			  ;
			$date.text(moment(date).fromNow()).attr('title', date);
		});
	}
});
}(window.jQuery, moment));