//= require lib/Markdown.Converter
//= require core/validate
//= require_self

(function($, Markdown, Validate, undefined) {
$.widget("ui.issueForm", {
	_create: function () {
		this.$issueType = $("#issue-type");
		this.$title = $("#title");
		this.$content = $("#content");
		
		this.converter = new Markdown.Converter();
		this._on({
			"click button.preview": "preview",
			"click button.submit": "submit"
		});
	},
	
	preview: function(e) {
		var $button = $(e.currentTarget)
		  , valid;
		if($button.text() == "预览") {
			valid = Validate.size(this.$title);
			valid = Validate.size(this.$content) && valid;
			
			if(valid) {
				$("div.prev-pane").show();
				$("div.edit-pane").hide();
				$button.text("编辑");
				var escapedTitle = $('<div/>').text(this.$title.val()).html();
				var escapedContent = $('<div/>').text(this.$content.val()).html();
				$(".prev-pane .title").html(escapedTitle);
				$(".prev-pane .content").html(this.converter.makeHtml(escapedContent));
			}
		} else {
			$("div.prev-pane").hide();
			$("div.edit-pane").show();
			$button.text("预览");			
		}
	},
	
	submit: function(e) {
		var that = this
		  , $button = $(e.currentTarget)
		  , valid
		  ;
		
		valid = Validate.size(this.$title);
		valid = Validate.size(this.$content) && valid;
		
		if(valid) {
			var type = parseInt(this.$issueType.val())
			  , title = $.trim(this.$title.val())
			  , content = $.trim(this.$content.val())
			  ;
			
			$button.prop("disabled", true);
			this.save(type, title, content).done(function(data){
				if(that.options.issueId) { // 保存
    				window.location.href = "../" +　that.options.issueId				
				} else { // 新建
					window.location.href = "./" + data.id					
				}
			}).fail(function() {
				$button.prop("disabled", false);
				alert("保存错误。")
			});
		}
	},
	
	save: function(type, title, content) {
		if(!this.options.issueId) {
			return $.ajax({
				type: "POST",
				url: "./",
				data: JSON.stringify({
					type: type,
					title: title,
					content: content
				}),
				contentType: 'application/json',
    			dataType: 'json'
			});
		} else {
			return $.ajax({
				type: "PUT",
				url: ".",
				data: JSON.stringify({
					type: type,
					title: title,
					content: content
				}),
				contentType: 'application/json'
			});
		}
	}
});
}(window.jQuery, Markdown, tms.validate));