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
	 * ����ʱ�䣬���ⴴ��ʱ��
	 */
	Date dateCreated 
	
	/**
	 * �޸�ʱ�䣬���ⴴ��ʱ��/���������޸�ʱ��
	 */
	Date dateModified
	
	/**
	 * ����ʱ�䣬���ⴴ��ʱ��/���������޸�ʱ��/���ظ�ʱ��
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
