package cn.edu.bnuz.tms.feedback

class CommentSupport {
	String userId
	
	static belongsTo = [
		comment : Comment
	]
	
	static mapping = {
		table 		'fbk_comment_support'
		userId		length: 10
	}
	
	static constraints = {
		userId 	unique:'comment'
	}
}
