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
	 * ����ʱ�䣬���ⴴ��ʱ��
	 */
	Date dateCreated
	
	/**
	 * �޸�ʱ�䣬���ⴴ��ʱ��/���������޸�ʱ��
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
