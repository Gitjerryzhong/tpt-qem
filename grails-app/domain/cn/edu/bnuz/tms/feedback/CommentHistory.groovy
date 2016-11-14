package cn.edu.bnuz.tms.feedback

class CommentHistory {
	String content
	Date dateCreated
	
	static belongsTo = [
		comment : Comment
	]
	
	static mapping = {
		table 		'fbk_comment_history'
		content		length: 512
	}
}
