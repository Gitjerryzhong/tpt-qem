(function($, undefined) {
$.widget("ui.weekTabs", {
	defaultElement:"<ul>",
	options: {
		step: 1,
    	stretch: false,
    	// callback
    	change: null
	},
	
	_create: function() {
		var start   = this.options.start
          , end     = this.options.end
          , active  = this.options.active
          , step    = this.options.step
          , stretch = this.options.stretch
	  	  ;
  	
	  	for(var i = start; i <= end; i += step) {
	  		var $li = this._addTab(i, i == active);
	  		
	  		if(stretch) {
	  			$li.width(100.0/((end-start+1)/step) + "%")
	  		}
	  	}
	},
	
	_addTab: function(text, active) {
		var that = this;
		
		return $("<li/>")
			.addClass(active ? "active" : null)
			.append($("<a href='#' data-toggle='tab' tabindex='-1'/>").text(text))
			.appendTo(this.element)
	        .on('shown.bs.tab', function(e) {
	        	var $tab = $(e.target);
	        	var week = parseInt($tab.text())
	        	that._trigger('change', null, week)
	        	$tab.blur();
	        });    		
	}
});
}(jQuery));