//= require lib/doT.min
//= require teach/model/arrangement
//= require_self

(function($, doT, Arrangement, undefined) {
$.widget("ui.freeArrangement", {
	_create: function () {
		var that = this
	  	  , $element = this.element
	  	  , options = this.options
	  	  ;
	
		this.$editPane = $("#editPane").editPane(options);
		this.$studentInfo = $("#search .studentInfo");
		this._on($("#search button"), {click:'_findStudent'});
		
		if(options.studentId) {
			$("#search input").val(options.studentId);
			this._findStudent();
		}
	},
	
	_findStudent: function() {
		var that = this
		  , id = $.trim($("#search input").val());
		
		$.ajax({
			type: "GET",
			url: "./student/" + id,
			contentType: 'application/json',
			dataType: 'json',
			cache: false,
			success: function(student) {
				if(id == student.id) {
					that.$studentInfo.text(student.name + " (" + student.major + " " + student.adminClass + ")");
					that.$editPane.editPane({student: student})
				}
			},
			error: function() {
				that.$studentInfo.text("学生不存在");
				that.$editPane.editPane({student: null})
			}
		});
	}
});

$.widget("ui.editPane", {
	arrangements: [], // array of teacher Arrangement
	
	_create: function() {
		var that = this;
		
		this.teacherArrangementTemplate = doT.template($("#teacher-arrangement-template").html())
		// create model
		$.each(this.options.arrangements, function() {
			that.arrangements.push(new Arrangement(this))
		});
		
		this._on({
			"mouseenter .enabled": function(e) {
				$(e.target).addClass("ui-state-hover");
			},
			
			"mouseleave .enabled": function(e) {
				$(e.target).removeClass("ui-state-hover");
			},
			
			"change input[type='checkbox']:visible": function(e) {
				var $checkbox = $(e.target)
				  , arrId = $checkbox.data("id")
				  ;
				if($checkbox.prop('checked')) {
					this._insert(arrId, this.student.id)
				} else {
					this._delete(arrId, this.student.id)
				}
			}
		});
		
		this.renderTeacherArrangements();
	},
	
	_setOption: function(key, value) {
		var that = this;
		if(key == "student") {
			this.student = value;
			if(this.student) {
				var arrs = [];
				$.each(value.arrangements, function() {
					var arr = new Arrangement(this);
					$.each(value.freeArrangements, function() {
						if(this == arr.id) {
							arr.free = true;
							return false;
						}
					});
					arrs.push(arr);
				});
				this.student.arrangements = arrs;
			}
			this.refresh();
		}
	},
	
    renderTeacherArrangements: function() {
    	var that = this
    	$.each(this.arrangements, function(index, arr) {
    		that.renderTeacherArrangement(arr);
    	});
    },
    
    renderTeacherArrangement: function(arr) {
		var row = arr.startSection
          , col = arr.dayOfWeek
          , rowSpan = arr.totalSection
          , rollcalled = false
          ;

		for (var i = 1; i < rowSpan; i++) {
			this._cell(row + i, col).hide();
		}
		
		this._cell(row, col)
			.prop('rowspan', rowSpan)
			.addClass("teacher")
			.find("ul.teacher").eq(0)
			.append($(this.teacherArrangementTemplate(arr)));
	},
	
	refresh: function() {
		this._resetTable();
		if(this.student) {
			this.renderStudentInfo();			
		}
	},
	
	renderStudentInfo: function() {
		var that = this
    	$.each(this.student.arrangements, function(index, arr) {
    		that.renderStudentArrangement(arr);
    	});
	},
	
	renderStudentArrangement: function(arr) {
		var row = arr.startSection
          , col = arr.dayOfWeek
          , rowSpan = arr.totalSection
          , rollcalled = false
          , $cell = this._cell(row, col)
          ;
		// 教师已占的格不处理
		if($cell.prop("rowspan") == 1) {
			for (var i = 1; i < rowSpan; i++) {
				var $temp = this._cell(row + i, col)
				if($temp.prop("rowspan") == 1) {
					$temp.addClass("student")
					$temp.hide();
				} else {
					break;
				}
			}
			
			$cell.prop("rowspan", i).addClass("student");
		}
		
		var $checkbox = $cell.find("input[data-id='" + arr.id + "']").eq(0);
		if($checkbox.length == 1) {
			$checkbox.parents("li").addClass("student");
			if(arr.free) {
				$checkbox.prop("checked", true);
			}
			$checkbox.show();
		} else {
			$("ul.student", $cell).append($("<li/>").text(arr.courseText() + arr.oddEvenText()))					
		}
	},
	
	_cell: function(row, col) {
	   	return $("#a_" + row + "_" + col);
	},
	
	_insert: function(arrangementId, studentId) {
		$.ajax({
			type: "POST",
			url: "./",
			data: JSON.stringify({
				studentId: studentId.toString(),
				arrangementId: arrangementId.toString()
			}),
			contentType: 'application/json'
		}).done(function() {
		}).fail(function() {
		});
	},
	
	_delete: function(arrangementId, studentId) {
		$.ajax({
			type: "DELETE",
			url: "./" + studentId,
			data: JSON.stringify({arrangement: arrangementId}),
			contentType: 'application/json'			
		}).done(function() {
		}).fail(function() {
		});
	},
	
	_resetTable : function() {
		$("input[type='checkbox']", this.element)
			.prop('checked', false)
			.hide();
		
		$("td.student", this.element)
			.prop('rowspan', 1)
			.removeClass("student")
			.show();
		
		$("ul.student", this.element)
			.empty();
		
		$("ul.teacher li.student", this.element)
			.removeClass("student");
	}	
});
}(jQuery, doT, tms.teach.model.Arrangement));