package cn.edu.bnuz.tms.feedback

import java.util.Date;

class Comment {
	static int STATUS_OPEN = 0
	static int STATUS_HIDDEN = 1
	
	static CONTENT_MAX_SIZE = 512
	
	String userId
	String userName
	String content
	int supportCount
	boolean visible
	
	/**
	 * 创建时间，问题创建时间
	 */
	Date dateCreated
	
	/**
	 * 修改时间，问题创建时间/自身内容修改时间
	 */
	Date dateModified

	static belongsTo = [
		issue : Issue
	]
	
	static hasMany = [
		supports: CommentSupport,
		histories: CommentHistory
	]
	
	static mapping = {
		table 			'fbk_comment'
		userId			length: 10, index: 'fbk_comment_user_id_idx'
		userName 		length: 50
		content			length: Comment.CONTENT_MAX_SIZE
		supportCount	defaultValue: 0
		status			defaultValue: 0
	}
}
