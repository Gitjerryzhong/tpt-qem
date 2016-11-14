package cn.edu.bnuz.tms.feedback

import java.util.Date;

class Issue {
	static int TITLE_MAX_SIZE = 50
	static int CONTENT_MAX_SIZE = 512
	
	static int TYPE_BUG = 1
	static int TYPE_IMPROVEMENT = 2
	static int TYPE_NEW_FEATURE = 3
	
	int type
	String userId
	String userName
	String title
	String content
	
	/**
	 * 创建时间，问题创建时间
	 */
	Date dateCreated 
	
	/**
	 * 修改时间，问题创建时间/自身内容修改时间
	 */
	Date dateModified
	
	/**
	 * 更新时间，问题创建时间/自身内容修改时间/最后回复时间
	 */
	Date dateUpdated
	
	int supportCount
	int commentCount
	int visitedCount
	boolean visible
	boolean closed
	
	static hasMany = [
		comments: Comment,
		supports: IssueSupport,
		histories: IssueHistory,
	]
	
	static mapping = {
		table 			'fbk_issue'
		userId			length: 10, index: 'fbk_issue_user_id_idx'
		userName 		length: 50
		title			length: Issue.TITLE_MAX_SIZE
		content			length: Issue.CONTENT_MAX_SIZE
	}
}
