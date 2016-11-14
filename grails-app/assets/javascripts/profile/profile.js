$(function() {
	var FIELDS = [ "longPhone", "shortPhone", "email", "officeAddress",
			"officePhone", "homeAddress", "homePhone", "qqNumber" ]

	function onSuccess(eId, text) {
		$("#" + eId).text(text)
			.removeClass("alert-danger")
			.addClass("alert-success")
			.fadeIn(400)
			.delay(2000)
			.fadeOut(400);
	}

	function onError(eId, text) {
		$("#" + eId).text(text)
			.removeClass("alert-success")
			.addClass("alert-danger")
			.fadeIn(400)
			.delay(2000)
			.fadeOut(400);
	}
	
	function validateProfile($element) {
		var $help = $element.parent("div").next(".help-block")
		  , text = $.trim($element.val())
		  , pattern = $element.prop("pattern")
		  , valid = true;
		if(text != "" && pattern) {
			valid = text.match(pattern);
			$help.text(valid ? "" : "格式不正确");
		} 

		if(valid) {
			$element.parents(".form-group").removeClass("has-error");			
		} else {
			$element.parents(".form-group").addClass("has-error");
		}
		
		return valid;
	}

	$("#profile input[type='text']").focusout(function(){
		validateProfile($(this));
	});
	
	$("#profileSubmit").click(function(e) {
		var valid = true;
		$("#profile input[type='text']").each(function() {
			valid = valid && validateProfile($(this));
		});
				
		if(!valid) {
			return;
		}
		
		var data = {};

		$("#profile input[type='text']").each(function() {
			var $input = $(this);
			data[$input.attr("id")] = $.trim($(this).val());
		})

		$.ajax({
			type : "PUT",
			url : window.location.href,
			data : JSON.stringify({"profile":data}),
			contentType : 'application/json',
			success : function(data) {
				console.log(data)
				onSuccess("profileAlert", "保存成功");
			},
			error : function() {
				onError("profileAlert", "保存失败");
			}
		});
	});

	/**
	 * password process
	 */
	function validatePassword($element) {
		var $help = $element.parent("div").next(".help-block")
		  , text = $.trim($element.val())
		  , confirmTo = $element.data("confirm")
		  , pattern = $element.prop("pattern")
		  , valid;
		if(pattern) {
			valid = text.match(pattern);
		} else {
			valid = text != "";
			$help.text(valid ? "" : "不能为空");
		}
		
		if(valid && $element.data("confirm")) {
			valid = $.trim($(confirmTo).val()) == text
			$help.text(valid ? "" : "与新密码不一致")
		}
		
		if(valid) {
			$element.parents(".form-group").removeClass("has-error");			
		} else {
			$element.parents(".form-group").addClass("has-error");
		}
		
		return valid;
	}
	
	$("input[type='password']").focusout(function(){
		validatePassword($(this));
	});
	
	$("#passwordSubmit").click(function(e) {
		var valid = true;
		$("input[type='password']").each(function() {
			valid = valid && validatePassword($(this))
		});
				
		if(!valid) {
			return;
		}
		
		$.ajax({
			type : "PUT",
			url : window.location.href,
			data : JSON.stringify({"password":{
				oldPassword: $("#oldPassword").val(),
				newPassword: $("#newPassword").val()
			}}),
			contentType : 'application/json',
			success : function() {
				onSuccess("passwordAlert", "保存成功");
			},
			error : function() {
				var $element = $("#oldPassword");
				$element.next(".help-block").text("密码不正确");
				$element.parents(".form-group").addClass("has-error");
				
				onError("passwordAlert", "保存失败");
			}
		});
	});
});