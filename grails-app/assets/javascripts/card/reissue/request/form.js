(function($, undefined) {
$.widget("ui.cardReissueRequestForm", {
	_create: function () {
		var that = this;
		
		this.requestId = this.options.requestId;
		if(this.requestId) {
			$("#submit").text("保存");  // '提交'改为'保存'
			$("#content").show();
			$("#notice").hide();
			$("#continue").hide();
			$("#submit").show();
		}
		
		this._on({
			"click #continue": function() {
				$("#content").show();
				$("#notice").hide();
				$("#continue").hide();
				$("#submit").show();
			},
			"change #reason": function() {
				this.validateReason();
			},
			"click #submit": function() {
				if(this.validateReason()) {
					$("#submit").prop("disabled", true);
					this.save($.trim($("#reason").val()));
				}
			}
		});
	},
	
	validateReason: function() {
		return this.validate($("#reason"), function(){
			var length = $.trim($(this).val()).length; 
			if(length < 10) {
				return {valid:false, type:"reason", message:"事由长度小于10。"};
			} else if(length > 250){
				return {valid:false, type:"reason", message:"事由长度大于250。"};
			} else {
				return {valid:true, type:"reason"};
			}
		});
	},
	      
	validate: function($control, func) {
		var result = func.call($control)
		  , $errors = $("#errors")
		  , $ul = $("#errors ul");
		
		$errors.find("." + result.type).remove();
		if(result.valid) {
			$control.closest('div').removeClass('has-error').addClass('has-success')
		} else {
			$control.closest('div').removeClass('has-success').addClass('has-error');
			$("<li/>").addClass(result.type).text(result.message).appendTo($ul);
		}
		
		if($ul.children().length > 0) {
			$errors.show();
		} else {
			$errors.hide();
		}
		return result.valid;
	},
		
	save: function(reason) {
		var that = this;
		
		if(!this.requestId) {
			$.ajax({
				type: "POST",
				url: "./",
				data: {reason: reason},
    			dataType: 'json',
				success: function(data) {
					window.location.href = "./" + data.id
				},
				error: function() {
					$("#submit").prop("disabled", false);
				}
			});
		} else {
			$.ajax({
				type: "PUT",
				url: "../" +　that.requestId,
				data: {reason: reason},
				contentType:'application/x-www-form-urlencoded',
				success: function(data) {
					window.location.href = "../" +　that.requestId				
				}
			});
		}
	}
});
}(window.jQuery));