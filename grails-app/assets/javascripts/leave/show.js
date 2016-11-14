(function($, undefined) {
	window.updateToolbar = function() {
		$("#edit").click(function() {
			window.location.href = window.location.href + "/edit"; 
		});
		
		$("#apply").click(function() {
			post(1)
		});
		
		$("#cancel").click(function() {
			post(0)
		});
		
		$("#delete").click(function() {
			$.ajax({
				type : "DELETE",
				url : window.location.href,
				success : function() {
					window.location.href = "../leaves"
				},
				error : function() {
					alert("无法删除");
				}
			});
		});
		
		$("#reportBack").click(function() {
			post(4)
		});
		
		var allowButtons = [
		        [ "edit", "apply", "delete" ], // new ->
			    [ "cancel" ], // applying ->
			    [ "reportBack" ], // approved ->
		 	    [ "edit", "apply", "delete" ], // rejected ->
			    [] // report back ->
		    ]
		  , status = parseInt($("#status").val())
		  ;
		
		$("#toolbar button").each(function() {
			var $button = $(this);
			if ($.inArray($button.attr("id"), allowButtons[status]) != -1) {
				$button.removeAttr("disabled")
			} else {
				$button.attr("disabled", "disabled")
			}
		});
	}
	
	window.updateStatus = function() {
		var status = parseInt($("#status").val());
		
		switch(status) {
		case 1:
			$("#statusName").addClass("label-info");
			break;
		case 2:
			$("#statusName").addClass("label-success");
			break;
		case 3:
			$("#statusName").addClass("label-danger");
			break;
		case 4:
			$("#statusName").addClass("label-default");
			break;		
		}
	}

	function post(status) {
		$("#status").val(status)
		$("form").submit()
	}
}(jQuery));