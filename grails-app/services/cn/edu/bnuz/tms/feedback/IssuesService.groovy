package cn.edu.bnuz.tms.feedback

import cn.edu.bnuz.tms.feedback.IssueCommand;
import cn.edu.bnuz.tms.feedback.Comment;
import cn.edu.bnuz.tms.feedback.CommentHistory;
import cn.edu.bnuz.tms.feedback.CommentSupport;
import cn.edu.bnuz.tms.feedback.Issue;
import cn.edu.bnuz.tms.feedback.IssueHistory;
import cn.edu.bnuz.tms.feedback.IssueSupport;
import groovy.sql.Sql

class IssuesService {
	def dataSource
	/**
	 * ͳ������
	 * @param userId �û�ID
	 * @return ͳ������
	 */
	def statis(String userId) {
		Sql sql = new Sql(dataSource)
		return sql.rows('{call sp_get_issue_statis(?)}', [userId]).collectEntries {
			[it.category, it.count]
		}
	}
	
	/**
	 * �������⣨����ɾ�����⣩
	 * @return �����б�
	 */
	def list(boolean visible) {
		Issue.executeQuery """
select new map(
  id as id, 
  userId as userId,
  userName as userName,
  type as type,
  title as title,
  dateUpdated as dateUpdated,
  visitedCount as visitedCount,
  supportCount as supportCount,
  commentCount as commentCount,
  closed as closed
)
from Issue
where visible = :visible
order by dateUpdated desc
""", [visible: visible]
	}
	
	/**
	 * �ҵ����⣨����ɾ�����⣩
	 * @return �����б�
	 */
	def personal(String userId) {
		Issue.executeQuery """
select new map(
  id as id, 
  userId as userId,
  userName as userName,
  type as type,
  title as title,
  dateUpdated as dateUpdated,
  visitedCount as visitedCount,
  supportCount as supportCount,
  commentCount as commentCount,
  closed as closed
)
from Issue
where userId = :userId
and visible = true
order by dateUpdated desc
""", [userId: userId]
	}
	
	
	
	/**
	 * ָ�����͵��������⣨����ɾ�����⣩
	 * @param type ����
	 * @return �����б�
	 */
	def listByType(int type) {
		Issue.executeQuery """
select new map(
  id as id, 
  userId as userId,
  userName as userName,
  type as type,
  title as title,
  dateUpdated as dateUpdated,
  visitedCount as visitedCount,
  supportCount as supportCount,
  commentCount as commentCount,
  closed as closed
) 
from Issue
where type = :type
and visible = true
order by dateUpdated desc
""", [type: type]
	}
	
	/**
	 * ��ȡIssue����
	 * @param id ����ID
	 * @return Issue����
	 */
	Issue get(Long id) {
		return Issue.get(id)
	}
	
	/**
	 * ��ȡIssue���ݣ�������ʾ��
	 * @param id ����ID
	 * @param userId �û�ID
	 * @return Issue����
	 */
	def getInfo(Long id, String userId) {
		def results = Issue.executeQuery """
select new map(
  id as id, 
  userId as userId,
  userName as userName,
  type as type,
  title as title,
  content as content,
  dateCreated as dateCreated,
  visitedCount as visitedCount,
  supportCount as supportCount,
  commentCount as commentCount,
  closed as closed,
  visible as visible,
  (select count(*)
   from IssueSupport
   where issue.id = :id
   and userId = :userId) as supported
) 
from Issue
where id = :id
""", [id: id, userId: userId]
		if(!results) {
			return null
		}
		
		def issue = results[0]
		
		results = Comment.executeQuery """
select new map(
  comment.id as id,
  comment.userId as userId,
  comment.userName as userName,
  comment.content as content,
  comment.dateCreated as dateCreated,
  comment.supportCount as supportCount,
  (select count(*)
   from CommentSupport support
   where support.comment.id = comment.id
   and userId = :userId) as supported
) from Comment comment
where comment.issue.id = :issueId
and visible = true
order by comment.dateCreated
""", [issueId: id, userId: userId]

		issue.comments = results
		
		Issue.executeUpdate """
update Issue 
set visitedCount = visitedCount + 1
where id = :id
""", [id: id]
		
		return issue
	}
	
	/**
	 * �����������нضϺͱ���
	 * @param title ����
	 * @return ����
	 */
	private String encodeTitle(String title) {
		(title.length() > Issue.TITLE_MAX_SIZE
			? title.substring(0, Issue.TITLE_MAX_SIZE)
			: title).encodeAsHTML()
	}
	
	/**
	 * ���������ݽ��нضϺͱ���
	 * @param content ����
	 * @return ����
	 */
	private String encodeContent(String content) {
		(content.length() > Issue.CONTENT_MAX_SIZE 
			? content.substring(0, Issue.CONTENT_MAX_SIZE) 
			: content).encodeAsHTML()
	}
	
	/**
	 * �½����⡣
	 * @param userId �û�ID
	 * @param userName �û�����
	 * @param type ���
	 * @param content ����
	 * @return Issue����
	 */
	Issue create(String userId, String userName, IssueCommand issueCommand) {
		def now = new Date()
		Issue issue = new Issue(
			userId: userId,
			userName: userName,
			type: (issueCommand.type),
			title: encodeTitle(issueCommand.title),
			content: encodeContent(issueCommand.content),
			dateCreated: now,
			dateModified: now,
			dateUpdated: now,
			supportCount: 0,
			commentCount: 0,
			visitedCount: 0,
			visible: true,
			closed: false
		)
		issue.save(failOnError:true)
	}
	
	/**
	 * ��������
	 * @param id ����ID
	 * @param userId �û�ID
	 * @param issueCommand ��������
	 * @return �Ƿ�ɹ�
	 */
	boolean update(Long id, String userId, IssueCommand issueCommand) {
		Issue issue = Issue.get(id)
		if(issue.userId != userId) {
			return false
		}
		
		def now = new Date()
		
		// ������ʷ
		IssueHistory history = new IssueHistory(
			type: (issue.type),
			title: (issue.title),
			content: (issue.content),
			dateCreated: (issue.dateModified)
		)
		
		issue.type = (issueCommand.type)
		issue.title = encodeTitle(issueCommand.title)
		issue.content = encodeContent(issueCommand.content)
		issue.dateModified = now
		issue.dateUpdated = now
		
		issue.addToHistories(history)
		issue.save(faileOnError:true)
		return true
	}
	
	/**
	 * ֧�����⡣
	 * @param id ����ID
	 * @param userId �û�ID
	 * @return �Ƿ�ɹ�
	 */
	boolean support(Long id, String userId) {
		Issue issue = Issue.get(id)
		if(issue.userId == userId) {
			return false
		}
		
		IssueSupport support = new IssueSupport(
			userId: userId
		)
		
		issue.supportCount = IssueSupport.countByIssue(issue) + 1
		issue.addToSupports(support)
		issue.save(faileOnError:true)
		return true
	}
	
	/**
	 * ȡ��֧�֡�
	 * @param id ����ID
	 * @param userId �û�ID
	 * @return �Ƿ�ɹ�
	 */
	boolean cancelSupport(Long id, String userId) {
		Issue issue = Issue.get(id)
		IssueSupport support = IssueSupport.findByIssueAndUserId(issue, userId)
		if(!support) {
			return false
		}
		
		issue.supportCount = IssueSupport.countByIssue(issue) - 1
		issue.save(faileOnError:true)
		support.delete()
		return true
	}
	
	/**
	 * ɾ������
	 * @param id ����
	 * @return �Ƿ�ɹ�
	 */
	boolean delete(Long id) {
		Issue issue = Issue.load(id)
		issue.delete()
		return true
	}
	
	/**
	 * ��������
	 * @param id ����
	 * @return �Ƿ�ɹ�
	 */
	boolean hide(Long id) {
		Issue.executeUpdate "update Issue set visible = false where id = :id", [id: id]
		return true
	}
	
	/**
	 * �ر�����
	 * @param id ����
	 * @return �Ƿ�ɹ�
	 */
	boolean close(Long id) {
		Issue.executeUpdate "update Issue set closed = true where id = :id", [id: id]
		return true
	}
	
	/**
	 * ������
	 * @param id ����
	 * @return �Ƿ�ɹ�
	 */
	boolean open(Long id) {
		Issue.executeUpdate "update Issue set closed = false where id = :id", [id: id]
		return true
	}
	
	/**
	 * �Իظ����ݽ��нضϺͱ���
	 * @param content ����
	 * @return ����
	 */
	private String encodeComment(String content) {
		(content.length() > Comment.CONTENT_MAX_SIZE
			? content.substring(0, Comment.CONTENT_MAX_SIZE)
			: content).encodeAsHTML()
	}
	
	/**
	 * �����ظ���
	 * @param issueId ����ID
	 * @param userId �û�ID
	 * @param userName �û���
	 * @param content ����
	 * @return Comment����
	 */
	Comment createComment(Long issueId, String userId, String userName, String content) {
		def now = new Date()
		Comment comment = new Comment(
			userId: userId,
			userName: userName,
			content: encodeComment(content),
			dateCreated: now,
			dateModified: now,
			visible: true
		)
		Issue issue = Issue.get(issueId)
		issue.commentCount = Comment.countByIssueAndVisible(issue, true) + 1
		issue.addToComments(comment)
		issue.dateUpdated = now
		issue.save(failOnError:true)
		return comment
	}
	
	/**
	 * ���»ظ�
	 * @param issueId ����ID
	 * @param commentId �ظ�ID
	 * @param content ����
	 * @return �����Ƿ�ɹ�
	 */
	boolean updateComment(Long issueId, Long commentId, String content) {
		Comment comment = Comment.get(commentId)
		if(!comment || comment.issue.id != issueId) {
			return false
		}
		
		CommentHistory history = new CommentHistory(
			content: (comment.content),
			dateCreated: (comment.dateModified)
		)
		comment.content = encodeComment(content)
		comment.addToHistories(history)
		comment.save(failOnError: true)
		return true
	}
	
	
	/**
	 * ���ػظ�
	 * @param id �ظ�ID
	 * @return �Ƿ�ɹ�
	 */
	boolean hideComment(Long issueId, Long commentId) {
		Comment.executeUpdate """
update Comment 
set visible = false 
where id = :id""", [id: commentId]

		Issue.executeUpdate """
update Issue issue
set issue.commentCount = (
  select count(*)
  from Comment comment
  where comment.issue.id = issue.id
  and visible = true
)
where id = :id  
""", [id: issueId]
		return true
	}
	
	/**
	 * ֧�ֻظ�
	 * @param issueId ����ID
	 * @param commentId �ظ�ID
	 * @param userId �û�ID
	 * @return �Ƿ�ɹ�
	 */
	boolean supportComment(Long issueId, Long commentId, String userId) {
		Comment comment = Comment.get(commentId)
		if(comment.userId == userId) {
			return false
		}
		CommentSupport support = new CommentSupport(
			userId: userId
		)
		
		comment.supportCount = CommentSupport.countByComment(comment) + 1
		comment.addToSupports(support)
		comment.save(faileOnError:true)
		return true
	}
	
	/**
	 * ȡ��֧��
	 * @param issueId ����ID
	 * @param commentId �ظ�ID
	 * @param userId �û�ID
	 * @return �Ƿ�ɹ�
	 */
	boolean cancelSupportComment(Long issueId, Long commentId, String userId) {
		Comment comment = Comment.get(commentId)
		CommentSupport support = CommentSupport.findByCommentAndUserId(comment, userId)
		if(!support) {
			return false
		}
		comment.supportCount = CommentSupport.countByComment(comment) - 1
		comment.save(faileOnError:true)
		support.delete()
		return true
	}

}
