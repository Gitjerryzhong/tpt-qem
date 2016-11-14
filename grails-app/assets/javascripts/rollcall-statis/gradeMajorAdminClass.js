//= require lib/jquery.flot.min
//= require lib/jquery.flot.categories.min
//= require lib/jquery.flot.stack.min
//= require_self

(function($, undefined) {
	$.widget("ui.rollcallGradeMajorAdminClass", {
		_create : function() {
			var that = this
			  , $element = this.element
			  , $graph = $("#graph")
			  , oldWidth = this.element.width()
			  ;
			this.category = 0;
			this.statisByCount = true;
			
			// redraw on resize
			$(window).resize(function() {
				var width = $element.width();
				if(width != $graph.width()) {
					$graph.width(width);
					that.plot.resize();
					that.plot.setupGrid();
					that.plot.draw();
				}
			});
			
			// switch category
			$("#statisCategory a").click(function(e) {
				e.preventDefault();
				$(".nav-pills li").removeClass("active");
				$(this).parents("li").addClass("active");
				that.category = parseInt($(this).data("category"));
				that._createPlot();
			});
			
			// select series
			$("#select input[type='checkbox']").change(function(e) {
				// at least one is selected
				if($("#select input[type='checkbox']:checked").length == 0) {
					$(this).prop("checked", true);
				}
				that._createPlot();
			});
			
			$("#select input[type='radio']").change(function(e) {
				// at least one is selected
				that.statisByCount = $("#select input[type='radio']:checked").val() == 0;
				that._createPlot();
			});
			
			this._createPlot();
		},
	    
		_createPlot: function() {
			var that = this
			  , series = []
			  , colors = []
			  , data
			  , count
			  ;
			// filter series and colors
			$("#select input[type='checkbox']").each(function(index) {
				if($(this).prop("checked")) {
					series.push(true);
					colors.push(that.COLORS[index]);
				} else {
					series.push(false);
				}
			});
			
			// get data, setup graph width and height
			data = this._getData(series, this.MAPPERS[this.category]);
			count = data[0].data.length;
			$("#graph").width(this.element.width())
			           .height(count * 30 + 35);
			
			this.plot = $.plot("#graph", data, {
				series: {
					stack : true,
					bars: {
						show:true,
						lineWidth: 1,
						barWidth: 0.5,
						fill: 0.8,
						align: "center",
						horizontal: true
					}
				},
				xaxis: {
					position: 'top',
					autoscaleMargin:0.02
				},
				yaxis: {
					mode: "categories",
					tickLength: 0,
					font: {
						color: "#000",
					    size: 13
					},
					autoscaleMargin:0
				},
				colors: colors
			});
		},

		_getData: function(series, mapper) {
			var that = this
			  , data = this._getEmptyData() 
			  , map = {}
			  , counts = {}
			  , temp = [];
			// count by gourp
			$.each(this.options.statis, function() {
				var group = mapper(this);
				if(!counts[group]) {
					counts[group] = 0;
				}
				counts[group] += this.studentCount;
			});
			
			// map: group -> statis
			$.each(this.options.statis, function() {
				var group = mapper(this);
				if(!map[group]) {
					map[group] = [0,0,0,0];
				}
				for(var i = 0; i < this.statis.length; i++) {
					map[group][i] += this.statis[i];						
				}
			});
			
			// list: {group, statis, total}
			$.each(map, function(group, statis) {
				var total = 0;
				for(var i = 0; i < statis.length; i++) {
					if(series[i]) {
						total += statis[i];						
					}
				}
				if(!that.statisByCount) {
					total = total / counts[group]
				}
				temp.push({group:group, statis: statis, total:total});
			});
						
			// sort
			temp.sort(function(a, b) {
				return a.total - b.total;
			});
			
			// data
			$.each(temp, function() {
				for(var i = 0; i < this.statis.length; i++) {
					data[i].data.push([
					  that.statisByCount ? this.statis[i] : this.statis[i] / counts[this.group], 
					  this.group + ' (' + counts[this.group] + '人)']
				    );
				}
			});
			
			// filtered by series
			return $.grep(data, function(value, index) {
				return series[index];
			});
		},
		
		_getEmptyData: function() {
			return [
				{ label: "旷课", data: [] },
				{ label: "迟到", data: [] },
				{ label: "早退", data: [] },
				{ label: "请假", data: [] }
			]
		},

		MAPPERS: [
	    	function(item){return item.adminClass},
	    	function(item){return item.major},
	    	function(item){return item.grade + '级'},
	    	function(item){return item.grade + '级' + item.major}			    
	    ],
	    
	    COLORS: [
			"#b94a48",
			"#f89406",
			"#bb1684",
			"#3a87ad"
		]
	});

}(jQuery));