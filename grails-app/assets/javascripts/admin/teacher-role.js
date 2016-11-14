$(function() {
	var teachers = null;
	
	$("span.teacher-role").on("click", function() {
		var $span = $(this)
		  , $select = $span.siblings("select.teacher-role")
		  ;
		$("select.teacher-role").hide();
		$("span.teacher-role").show();
		
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
		$.each(teachers, function(index, department) {
			$select.append("<option value='" + department.id + "'>" + department.name + "</option>");
		});	
		
		$select.val($select.data("teacher"));
	};
	
	$("select.teacher-role").on("blur", function() {
		var $select = $(this)
		  , $span = $select.siblings("span.teacher-role")
		  , prevAdminId = $select.data("teacher")
		  , currAdminId = $select.val()
		  ;
		if(prevAdminId != currAdminId) {
			$.ajax({
				type: "POST",
				url: "./teacherRole",
				data: {role: $select.data("role"), teacher:currAdminId},
				success: function(data) {
					var name = $select.find("option:selected").text()
					$span.text(name != "" ? name:"<Пе>").show();
					$select.data("teacher", currAdminId).hide();
				}
			});			
		} else {
			$span.show();
			$select.hide();
		}
	});
});