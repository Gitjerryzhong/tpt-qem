//= require lib/doT.min
//= require_self

(function($, doT, undefined) {
	$.widget("ui.rollcallStatisCourseClass", {
		
		_create: function() {
			var that = this
			  , termsOptionTemplate = doT.template($("#terms-option-template").html())
			  , $terms = $("#terms")
			  , $export = $("#export")
			  ;
			
			$terms.html(termsOptionTemplate(this.options.terms))
				  .val(this.options.term)
				  .on("change", function() {
					  that._loadCourseClasses($terms.val());
				   });
			
			$export.on("click", function(event) {
				event.preventDefault();
				if(that.courseClassId != null) {
					window.location.href = window.location.href + "?format=excel&course-class-id=" + that.courseClassId;					
				}
				return false;
			});
			
			this._loadCourseClasses($terms.val());
		},
		
		_loadCourseClasses: function(term) {
			var that = this
			  , courseClassesOptionTemplate = doT.template($("#course-classes-option-template").html())
			  , $courseClasses = $("#courseClasses")
			  , courseClasses = []
			  ;
			
			$.each(this.options.courseClasses, function() {
				if(this.term == term) {
					courseClasses.push(this);
				}
			});
			
			$courseClasses.html(courseClassesOptionTemplate(courseClasses))
			  .val(courseClasses[0].id)
			  .on("change", function() {
				  that._loadData($courseClasses.val());
			   });
			
			this._loadData($courseClasses.val());
		},
	
		_loadData: function(courseClassId) {
			var that = this;
			this.courseClassId = null;
			$.ajax({
				type: "GET",
				url: window.location.href + "?format=json&course-class-id=" + courseClassId,
				contentType: 'application/json',
    			dataType: 'json',
    			cache: false
			}).done(function(data) {
				that._fillData(data);
				that.courseClassId = courseClassId;
			});
		},
		
		_fillData: function(data) {
			var that = this
			  , tableTemplate = doT.template($("#table-template").html())
			  , $table = $("#rows")
			  ;
			$table.html(tableTemplate(data));
		}
	});
}(jQuery, doT));

