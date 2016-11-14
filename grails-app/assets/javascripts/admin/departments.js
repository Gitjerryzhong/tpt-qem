$(function() {
	$("span.dept-admin").on("click", function() {
		var $span = $(this)
		  , $select = $span.siblings("select.dept-admin")
		  ;
		$("select.dept-admin").hide();
		$("span.dept-admin").show();
		
		if($("option", $select).length == 0) {
			$.ajax({
				type: "GET",
				url: "./departmentTeachers/" + $select.data("dept"),
				cache: false,
				success: function(data) {
					$select.append("<option></option>");
					$.each(data, function(index, department) {
						$select.append("<option value='" + department.id + "'>" + department.name + "</option>");
					});	
					
					$select.val($select.data("admin"));
					$span.hide();
					$select.show();
					$select.focus();						
				}
			});			
		} else {
			$span.hide();
			$select.show();
			$select.focus();
		}		
	});
	
	$("select.dept-admin").on("blur", function() {
		var $select = $(this)
		  , $span = $select.siblings("span.dept-admin")
		  , prevAdminId = $select.data("admin")
		  , currAdminId = $select.val()
		  ;
		if(prevAdminId != currAdminId) {
			$.ajax({
				type: "POST",
				url: "./departmentAdmin",
				data: {dept: $select.data("dept"), admin:currAdminId},
				success: function(data) {
					var name = $select.find("option:selected").text()
					$span.text(name != "" ? name:"<Пе>").show();
					$select.data("admin", currAdminId).hide();
				}
			});			
		} else {
			$span.show();
			$select.hide();
		}
	});
});