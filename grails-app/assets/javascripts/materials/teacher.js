//= require lib/doT.min
//= require_self

(function($, doT, undefined) {
	$.widget("ui.personalMaterials", {
		
		_create: function() {
			var that = this
			  , optionTemplate = doT.template($("#option-template").html())
			  , $terms = $("#terms")
			  ;
			
			$terms.html(optionTemplate(this.options.terms))
				  .val(this.options.term)
				  .on("change", function() {
					  that._loadData($terms.val());
				   });
			this._loadData($terms.val());
			
			this.detailTemplate = doT.template($("#detail-template").html());
		},
	
		_loadData: function(term) {
			var that = this;
			$.ajax({
				type: "GET",
				url: window.location.href + "?term=" + term,
				contentType: 'application/json',
    			dataType: 'json',
    			cache: false
			}).done(function(data) {
				that._fillData(term, data);
			});
		},
		
		_fillData: function(term, data) {
			var that = this
			  , $tables = $("#tables")
			  ;
			$tables.empty();
			
			$.each(data, function(index, dept) {
				var classCount = 0;
				for(var i = 0; i < dept.courses.length; i++) {
					classCount += dept.courses[i].classes.length;
				}
				
				if(data.length > 1) {
					$tables.append($("<h4/>").text(dept.name));
				}
				
				var $table = $(that.detailTemplate({
					categories: dept.categories,
					courses: dept.courses,
					commits: dept.commits,
					classCount: classCount,
					termId: term,
					STATUS: that.STATUS,
					find: that._findCommit,
					thesis: dept.thesis,
					internship: dept.internship
				}));
		
				$tables.append($table);
			});
		},

		_findCommit: function(commits, tmid, target) {
			for(var i = 0; i < commits.length; i++) {
				var commit = commits[i]; 
				if(commit.tmid == tmid && commit.target == target) {
					return commit;
				}
			}
			return null;
		},
		
		STATUS: [
			{text:"未交", color:"label-danger"},
		    {text:"已交", color:"label-success"},
		    {text:"免交", color:"label-default"}
		]
	});
}(jQuery, doT));

