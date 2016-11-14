
var require = {
	baseUrl: (function() { 
		/*  /appName/static/js */
		var scripts = document.getElementsByTagName("script");
		var src = scripts[scripts.length - 1].getAttribute("src");
		return src.substring(0, src.lastIndexOf('/'));
	}()),
	shim: {
		underscore: {
			exports: '_'
		},
		backbone: {
			deps: ['underscore', 'jquery'],
			exports: 'Backbone'
		}
	},
	paths: {
		jquery: '../plugins/jquery-1.11.0/js/jquery/jquery-1.11.0.min.js',
		underscore: 'lib/underscore-min',
		backbone: 'lib/backbone-min',
		text: 'lib/text',
		page: (function() { 
			/*  app/controller/action */
			var scripts = document.getElementsByTagName("script");
			return scripts[scripts.length - 1].getAttribute("data-page");
		}())
	}
}