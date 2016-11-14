//= require core/format
//= require model
//= require schedule
//= require item-list
//= require_self

(function($, LeaveModel, undefined) {
$.widget("ui.leaveRequest", {
	_create: function () {		
		var options = this.options
	  	  , leaveModel = options.leaveModel = new LeaveModel(options);
	  	  ;
	  	
		$("#schedule").leaveSchedule(options);
		$("#leaveItems").leaveItemList(options);
		
		$(leaveModel).on("itemAdded itemRemoved", function() {
			$("#itemCount").text(leaveModel.itemCount())
			validateItemCount()
		});
	    
		$("#leaveReason").on("change", function() {
			validateLeaveReason();
		});
	      
		$("input[name=leaveType]").on("change", function() {
			leaveModel.leaveType = getLeaveType();
		});
	      
		if(this.options.leaveRequest) {
			$("#submit").text("保存");  // '提交'改为'保存'
		}
		
		$("#submit").click(function(e) {
			var v1 = validateItemCount()
			  , v2 = validateLeaveReason()
			  ;
			if(v1 && v2) {
				var $button = $(e.currentTarget);
				$button.prop("disabled", true); // 防止重复
				leaveModel.save(getLeaveReason()).done(function(data) {
					if(options.leaveRequest) { // 编辑保存
						window.location.href = "../" +　options.leaveRequest.id			
					} else { // 新建保存
						window.location.href = "./" + data.id
					}
				}).fail(function(){
					alert("保存出错。");
					$button.prop("disabled", false);
				});
			}
		});
	      
		// init count
		$("#itemCount").text(leaveModel.itemCount());
	      
		// init type
		$("input[name=leaveType]").each(function() {
			$(this).prop("checked", parseInt($(this).val()) == leaveModel.leaveType);
		});
	      
		// helper functions
		function getLeaveType() {
			return parseInt($("input[name=leaveType]:checked").val())
		}
	      
		function getLeaveReason() {
			return $("#leaveReason").val();
		}
	      
		function validateItemCount() {
			return validate($("#itemCount"), function(){
				var count = parseInt($(this).text());
				if(count == 0) {
					return {valid:false, type:"itemCount", message:"请假项不能为空。"};
				} else {
					return {valid:true, type:"itemCount"};
				}
			});
		}
	      
		function validateLeaveReason() {
			return validate($("#leaveReason"), function(){
				var length = $(this).val().length; 
				if(length < 10) {
					return {valid:false, type:"leaveReason", message:"事由长度小于10。"};
				} else if(length > 250){
					return {valid:false, type:"leaveReason", message:"事由长度大于250。"};
				} else {
					return {valid:true, type:"leaveReason"};
				}
			});
		}
	      
		function validate($control, func) {
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
		}
	}
});
}(window.jQuery, window.tms.leave.model.LeaveModel));