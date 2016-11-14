$(function() {
	var teachers = null;
	
	$("span.admin-teacher, span.grade-teacher").on("click", function() {
		var $span = $(this)
		  , $select = $span.siblings("select")
		  ;
		$(".table select").hide();
		$(".table span").show();
		
		if($("option", $select).length == 0) {
			if(teachers == null) {
				$.ajax({
					type: "GET",
					url: "./departmentTeachers",
					cache: false,
					success: function(data) {
						teachers = data;
						fillSelect($select);
						$span.hide();
						$select.show();
						$select.focus();
					}
				});							
			} else {
				fillSelect($select);
				$span.hide();
				$select.show();
				$select.focus();
			}
		} else {
			$span.hide();
			$select.show();
			$select.focus();
		}		
	});
	
	function fillSelect($select) {
		$select.append("<option></option>");
		$.each(teachers, function(index, teacher) {
			$select.append("<option value='" + teacher.id + "'>" + teacher.name + "</option>");
		});	
		
		$select.val($select.data("teacher"));
	};
	
	$("select.admin-teacher, select.grade-teacher").on("blur", function() {
		var $select = $(this)
		  , $span = $select.siblings("span")
		  , prevAdminId = $select.data("teacher")
		  , currAdminId = $select.val()
		  , adminClass = $select.parents("tr").children().eq(0).text()
		  ;
		if(prevAdminId != currAdminId) {
			$.ajax({
				type: "POST",
				url: "./adminClass",
				data: {
					adminClass: adminClass, 
					teacher:currAdminId,
					type: $select.hasClass("admin-teacher") ? "admin" : "grade"
				},
				success: function(data) {
					var name = $select.find("option:selected").text()
					$span.text(name != "" ? name:"<空>").show();
					$select.data("teacher", currAdminId).hide();
				}
			});			
		} else {
			$span.show();
			$select.hide();
		}
	});
});