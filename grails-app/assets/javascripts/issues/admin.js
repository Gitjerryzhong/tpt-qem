(function($, undefined) {
$.widget("ui.issueAdmin", {
	_create: function() {
		var that = this;
		this.element.on("dblclick", ".footer", function(e){
			var $footer = $(e.currentTarget)
			$footer.find(".admin").toggleClass("hidden");
		}).on("click", ".issue-toolbar .admin-delete", function() {
			if (confirm("确定要删除问题吗？")) {
				that.deleteIssue().done(function() {
					window.location.href = "./"
				}).fail(function() {
					alert("保存错误")
				});				
			}
		}).on("click", ".issue-toolbar .admin-hide", function() {
			if (confirm("确定要隐藏问题吗？")) {
				that.hideIssue().done(function() {
					window.location.href = "./"
				}).fail(function() {
					alert("保存错误")
				});				
			}
		}).on("click", ".issue-toolbar .admin-close", function(e) {
			var $button = $(e.currentTarget);
			if (confirm("确定要关闭问题吗？")) {
				that.closeIssue().done(function() {
					window.location.reload(); 
				}).fail(function() {
					alert("保存错误");
				});
			}
		}).on("click", ".issue-toolbar .admin-open", function(e) {
			var $button =$(e.currentTarget);
			if (confirm("确定要打开问题吗？")) {
				that.openIssue().done(function() {
					window.location.reload();
				}).fail(function() {
					alert("保存错误");
				});
			}
		}).on("click", ".comment-toolbar .admin-hide", function(e) {
			var $button = $(e.currentTarget)
			  , $comment = $button.parents("li.comment")
			  , commentId = $comment.data("id")
			  ;
			
			that.hideComment(commentId).done(function() {
				$comment.detach();
				var $commentCount = that.element.find(".issue-toolbar .comment-count");
				$commentCount.text(parseInt($commentCount.text()) - 1)
			}).fail(function() {
				alert("保存错误");
			});;
		});
		this.element.find(".toolbar .admin").tooltip();
	},
	
	deleteIssue: function() {
		return $.ajax({
			type: "POST",
			url: window.location.href,
			data: {type: "delete"}
		});
	},
	
	hideIssue: function() {
		return $.ajax({
			type: "POST",
			url: window.location.href,
			data: {type: "hide"}
		});
	},
	
	closeIssue: function() {
		return $.ajax({
			type: "POST",
			url: window.location.href,
			data: {type: "close"}
		});
	},
	
	openIssue: function() {
		return $.ajax({
			type: "POST",
			url: window.location.href,
			data: {type: "open"}
		});
	},
	
	hideComment: function(commentId) {
		return $.ajax({
			type: "POST",
			url: window.location.href + "/comments/" + commentId,
			data: {type: "hide"}
		});
	}
});
}(window.jQuery));