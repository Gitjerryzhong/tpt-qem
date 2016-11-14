//= require lib/doT.min
//= require lib/Markdown.Converter
//= require lib/moment-with-locales.min
//= require core/validate
//= require_self

(function($, doT, Markdown, moment, Validate, undefined) {
$.widget("ui.issueShow", {
	_create: function () {
		var that = this;
		this.converter = new Markdown.Converter();
		this.$comments = this.element.find(".comments");
		this.template = doT.template($("#editor-template").html());
		
		$("#new-comment").commentNew({
			parent: this,
			converter : this.converter,
			template: this.template
		});
		
		this.element.on("click", ".issue-toolbar .edit", function() {
			window.location.href = window.location.href + "/edit";
		});

		this.element.on("click", ".issue-toolbar .comment", function() {
			$("#new-comment").commentNew("focusEditor");
		});
		
		this.element.on("click", ".issue-toolbar .support", function(e) {
			var $button = $(e.currentTarget);
			that.support().done(function() {
				var $count = that.element.find(".issue-toolbar .support-count");
				$count.text(parseInt($count.text()) + 1);
				$button.removeClass("text-primary support")
					.addClass("text-success cancel-support")
					.attr("title", "取消赞")
					.tooltip("fixTitle")
					.tooltip("show");
			}).fail(function() {
				alert("更新错误");
			});
		});
		
		this.element.on("click", ".issue-toolbar .cancel-support", function(e) {
			var $button = $(e.currentTarget);
			that.cancelSupport().done(function() {
				var $count = that.element.find(".issue-toolbar .support-count");
				$count.text(parseInt($count.text()) - 1);
				$button.removeClass("text-success cancel-support")
					.addClass("text-primary support")
					.attr("title", "赞")
					.tooltip("fixTitle")
					.tooltip("show");
			}).fail(function() {
				alert("更新错误");
			});
		});
		
		this.element.on("click", ".comment-toolbar .edit", function() {
			$(this).closest(".comment").commentEdit({
				parent: that,
				converter : that.converter,
				template: that.template
			});
		});
		
		this.element.on("click", ".comment-toolbar .support", function(e) {
			var $button = $(e.currentTarget)
			  , $comment = $button.parents("li.comment")
			  , commentId = $comment.data("id")
			  ;
			that.supportComment(commentId).done(function() {
				var $count = $comment.find(".comment-toolbar .support-count");
				$count.text(parseInt($count.text()) + 1);
				$button.removeClass("text-primary support")
					.addClass("text-success cancel-support")
					.attr("title", "取消赞")
					.tooltip("fixTitle")
					.tooltip("show");
			}).fail(function() {
				alert("更新错误");
			});
		});
		
		this.element.on("click", ".comment-toolbar .cancel-support", function(e) {
			var $button = $(e.currentTarget)
			  , $comment = $button.parents("li.comment")
			  , commentId = $comment.data("id")
			  ;
			that.cancelSupportComment(commentId).done(function() {
				var $count = $comment.find(".comment-toolbar .support-count");
				$count.text(parseInt($count.text()) - 1);
				$button.removeClass("text-success cancel-support")
					.addClass("text-primary support")
					.attr("title", "赞")
					.tooltip("fixTitle")
					.tooltip("show");
			}).fail(function() {
				alert("更新错误");
			});
		});

		
		moment.lang("zh-cn");
		this.convert(this.element);
	},
	
	convert: function($el) {
		var that = this;
		$el.find(".content .original").each(function() {
			var $original = $(this);
			$original.prev(".markdown").html(that.converter.makeHtml($original.html()));
		});
		$el.find(".toolbar .glyphicon").tooltip();
		
		$el.find(".footer .date-created").each(function() {
			var $date = $(this)
			  , date = $date.text()
			  ;
			$date.text(moment(date).fromNow()).attr('title', date);
		}).tooltip();
	},
	
	appendComment: function(comment) {
		var $comment = $(comment);
		this.convert($comment);
		this.$comments.append($comment);
		var $commentCount = this.element.find(".issue-toolbar .comment-count");
		$commentCount.text(parseInt($commentCount.text()) + 1)
	},
	
	support: function() {
		return $.ajax({
			type: "POST",
			url: window.location.href,
			data: {type: "support"}
		});
	},
	
	cancelSupport: function() {
		return $.ajax({
			type: "POST",
			url: window.location.href,
			data: {type: "cancel-support"}
		});
	},
	
	supportComment: function(commentId) {
		return $.ajax({
			type: "POST",
			url: window.location.href + "/comments/" + commentId,
			data: {type: "support"}
		});
	},
	
	cancelSupportComment: function(commentId) {
		return $.ajax({
			type: "POST",
			url: window.location.href + "/comments/" + commentId,
			data: {type: "cancel-support"}
		});
	}
});

$.widget("ui.commentNew", {
	_create: function() {
		var that = this;
		this.parent = this.options.parent;
		this.converter = this.options.converter;
		this.template = this.options.template;
		
		this.element.find(".media-body").html(this.template({edit:false}));
		
		this.$editorPane = this.element.find(".edit-pane");
		this.$previewPane = this.element.find(".prev-pane");
		
		this.$previewButton = this.element.find(".toolbar .preview");
		this.$submitButton = this.element.find(".toolbar .submit");
		
		this.$markdown = this.$previewPane.find(".markdown");
		this.$editor = this.$editorPane.find("textarea");
		
		this.$previewButton.click(function() {
			if(that.$previewButton.text() == "预览") {
				that.showPreview();
			} else {
				that.showEditor();
			}
		});
		
		this.$submitButton.click(function() {
			that.submit();
		});
	},
	
	focusEditor: function() {
		var that = this;
		this.showEditor();
		$('html, body').animate({
	        scrollTop: that.element.offset().top
	    }, 0, function(){
	    	that.$editor.focus();
	    });
	},
	
	showPreview: function() {
		if(Validate.size(this.$editor)) {
			this.$previewPane.show();
			this.$editorPane.hide();
			this.$previewButton.text("编辑");
			var escaped = $('<div/>').text(this.$editor.val()).html();
			this.$markdown.html(this.converter.makeHtml(escaped));
		}
	},
	
	showEditor: function() {
		this.$previewPane.hide();
		this.$editorPane.show();
		this.$previewButton.text("预览");
		this.$editor.focus();
	},
	
	resetEditor: function() {
		this.$editorPane.find("textarea").val('');
		this.showEditor();
	},
	
	submit: function() {
		var that = this;
		
		if(Validate.size(this.$editor)) {
			var content = $.trim(this.$editor.val());
			this.$submitButton.prop("disabled", true);
			this.save(content).done(function(data){
				that.parent.appendComment(data)
				that.resetEditor();
			}).fail(function() {
				alert("保存错误。")
			}).always(function() {
				that.$submitButton.prop("disabled", false);
			});
		}
	},
	
	save: function(content) {
		return $.ajax({
			type: "POST",
			url: window.location.href + "/comments",
			data: JSON.stringify({
				content: content
			}),
			contentType: 'application/json'
		});
	}
});

$.widget("ui.commentEdit", {
	_create: function() {
		var that = this;
		this.parent = this.options.parent;
		this.converter = this.options.converter;
		this.template = this.options.template;
		
		
		this.commentId = this.element.data("id");
		this.newBody = $(this.template({edit:true}));
		this.oldBody = this.element.find(".media-body");
		
		this.newBody.find(".toolbar .glyphicon").tooltip();
		this.oldBody.find(".toolbar .glyphicon").tooltip("hide");
		
		this.oldBody.detach();
		this.element.append(this.newBody);
		
		this.$editorPane = this.element.find(".edit-pane");
		this.$previewPane = this.element.find(".prev-pane");
		
		this.$previewButton = this.element.find(".toolbar .preview");
		this.$submitButton = this.element.find(".toolbar .submit");
		this.$cancelButton = this.element.find(".toolbar .cancel");
		
		this.$markdown = this.$previewPane.find(".markdown");
		this.$editor = this.$editorPane.find("textarea");
		this.$editor.val(this.oldBody.find(".original").text());
		
		this.$previewButton.click(function() {
			if(that.$previewButton.text() == "预览") {
				that.showPreview();
			} else {
				that.showEditor();
			}
		});
		
		this.$submitButton.click(function() {
			that.submit();
		});
		
		this.$cancelButton.click(function() {
			that.resetEditor();
		});
		
		this.focusEditor();
	},
	
	showPreview: function() {
		if(Validate.size(this.$editor)) {
			this.$previewPane.show();
			this.$editorPane.hide();
			this.$previewButton.text("编辑");
			var escaped = $('<div/>').text(this.$editor.val()).html();
			this.$markdown.html(this.converter.makeHtml(escaped));
		}
	},
	
	showEditor: function() {
		this.$previewPane.hide();
		this.$editorPane.show();
		this.$previewButton.text("预览");
		this.$editor.focus();
	},
	
	updateComment: function(content) {
		var escaped = $('<div/>').text(content).html();
		this.oldBody.find(".original").html(escaped)
		this.oldBody.find(".markdown").html(this.converter.makeHtml(escaped));
		this.resetEditor();
	},
	
	resetEditor: function() {
		this.newBody.detach();
		this.oldBody.appendTo(this.element);
		this.destroy();
	},
	
	focusEditor: function() {
		var that = this;
		$('html, body').animate({
	        scrollTop: that.element.offset().top - 12
	    }, 0, function(){
	    	that.$editor.focus();
	    });
	},
	
	submit: function() {
		var that = this;
		
		if(Validate.size(this.$editor)) {
			var content = $.trim(this.$editor.val());
			this.$submitButton.prop("disabled", true);
			this.save(content).done(function(){
				that.updateComment(content)
			}).fail(function() {
				alert("保存错误。")
			}).always(function() {
				that.$submitButton.prop("disabled", false);
			});
		}
	},
	
	save: function(content) {
		return $.ajax({
			type: "PUT",
			url: window.location.href + "/comments/" + this.commentId, 
			data: JSON.stringify({
				content: content
			}),
			contentType: 'application/json'
		});
	}
});
}(window.jQuery, doT, Markdown, moment, tms.validate));