//= require lib/doT.min
//= require_self

(function($, doT, undefined) {
	$.widget("ui.internshipTeacherList", {
		_create : function() {
			var that = this
			  , internshipTemplate = doT.template($("#internships-template").html())
			  ;
			this.element.html(internshipTemplate(this.options.internships));
			
			this.element.on("mouseenter", ".teacher", function() {
				$(this).find("i").css("visibility", "visible");
			}).on("mouseleave", ".teacher", function() {
				$(this).find("i").css("visibility", "hidden");
			});
			
			$(".btnAdd").click(function() {
				that.internshipId = $(this).parents("div.internship").data("id");
				that.$teachers = $(this).parents("div.internship").find(".teachers");
				that._showDialog();
			});
			
			$("#btnSave").click(function() {
				that._onSave();
			});
			
			$("#internshipTeacherList .teachers").on("click", ".teacher i", function() {
				var $teacher = $(this).parents(".teacher");
				that._delete($teacher);
			});
		},
		
		_showDialog: function() {
			var template = doT.template($("#select-template").html())
			var teachers = [];
			var exists = this._getExistsTeacherIds(this.internshipId);
			for(var i = 0; i < this.options.teachers.length; i++) {
				var found = false;
				for(var j = 0; j < exists.length; j++) {
					if(this.options.teachers[i].id == exists[j]) {
						found = true;
						break;
					}
				}
				if(!found) {
					teachers.push(this.options.teachers[i]);
				}
			}
			$("#teacherList").html(template(teachers));
			$("#dialog").modal("show");
		},
		
		_onSave: function() {
			var template = doT.template($("#teacher-template").html())
			  , $teachers = this.$teachers
			  , selectedId = []
			  , selectedName = []
			  ;
			
			$("#teacherList input:checked").each(function() {
				selectedId.push($(this).data("id"));
				selectedName.push($(this).parents("label").text());
			});
			
			$.ajax({
				type: "POST",
				url: window.location.href,
				data: {
					internshipId: this.internshipId,
					teacherIds: selectedId.join(",")
				},
    			dataType: 'json'
			}).done(function(data) {
				for(var i = 0; i < selectedId.length; i++) {
					$teachers.append($(template({
						itid: data[i],
						tid: selectedId[i],
						name: selectedName[i]
					})));
				}
			}).fail(function() {
				alert("无法插入");
			});
		},
		
		_delete: function($teacher) {
			var itid = $teacher.data("itid")
			$.ajax({
				type: "DELETE",
				url: window.location.href + "/" + itid
			}).done(function(data) {
				$teacher.remove();
			}).fail(function() {
				alert("无法删除");
			});
		},
		
		_getExistsTeacherIds: function(internshipId) {
			var teacherIds = [];
			$("#internshipTeacherList .internship").filter(function(index) {
				return $(this).data("id") == internshipId;
			}).find(".teachers .teacher").each(function() {
				teacherIds.push($(this).data("tid"));
			});
			
			return teacherIds;
		}
	});
}(jQuery, doT));

