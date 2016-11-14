(function() {
	"use strict";
	var Tms = function() {};
	
	Tms.prototype = {
		createModual: function(name, source) {
			var modual
			  , current
			  , paths = name.split('.')
			  , parent = window
			  ;
			
			for ( var i = 0; i < paths.length; i++) {
				current = parent[paths[i]]
				if (!current) {
					current = parent[paths[i]] = {}
				}
				parent = current;
			}
			modual =  current;
			
			if(source) {
				for (var prop in source) {
					modual[prop] = source[prop];
				}
			}
			
			return modual;
		}
	}
	
	window.tms = new Tms();
}());