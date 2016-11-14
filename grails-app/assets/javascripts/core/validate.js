//= require tms

(function(tms, undefined) {
	"use strict";
	tms.createModual("tms.validate", {
		size: function(field, nullable) {
			var $field = $(field)
			  , name = $field.data("validate-name")
			  , min = $field.data("validate-min")
			  , max = $field.data("validate-max")

			return this.validate($(field), function(){
				var length = $.trim($(this).val()).length; 
				if(!nullable) {
					if(min != undefined) {
						if(length < min) {
							return {valid:false, type:name, message:name + "长度不能小于" + min + "。"};
						}
					} else {
						if(length == 0) {
							return {valid:false, type:name, message:name + "不能为空。"};
						}
					}
				} 
				
				if(length > max){
					return {valid:false, type:name, message:name + "长度不能大于" + max + "。"};
				} 
				
				return {valid:true, type:name};
			});
		},
				      
		validate: function($control, func) {
			var result = func.call($control)
			  , $errors = $("#errors")
			  , $ul = $("#errors ul");
			
			$errors.find("." + result.type).remove();
			if(result.valid) {
				$control.closest('div').removeClass('has-error')
			} else {
				$control.closest('div').addClass('has-error');
				$("<li/>").addClass(result.type).text(result.message).appendTo($ul);
			}
			
			if($ul.children().length > 0) {
				$errors.show();
			} else {
				$errors.hide();
			}
			return result.valid;
		}
	});
}(window.tms));