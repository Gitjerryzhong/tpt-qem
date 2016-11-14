package cn.edu.bnuz.qem.project

import java.util.Date;

class QemAudit {

     /**
	 * 提交申请
	 */
	public static final int ACTION_COMMIT = 10
	
	/**
	 * 撤销申请
	 */
	public static final int ACTION_CANCEL = 11
	
	/**
	 * 修改申请
	 */
	public static final int ACTION_MODIFY = 12
	
	/**
	 * 学院审核通过
	 */
	public static final int ACTION_CHECK_YES = 20
	
	/**
	 * 学院审核不通过
	 */
	public static final int ACTION_CHECK_NO = 21
	
	/**
	 * 专家建议立项
	 */
	public static final int ACTION_BEGIN_YES = 22
	
	/**
	 * 专家建议不予立项
	 */
	public static final int ACTION_BEGIN_NO = 23
	
	/**
	 * 专家建议不予立项
	 */
	public static final int ACTION_BEGIN_NONE = 25
	
	/**
	 * 学院审核撤销
	 */
	public static final int ACTION_CHECK_CANCEL = 24
	
	/**
	 * 审批通过
	 */
	public static final int ACTION_APPROVE_YES = 30
	
	/**
	 * 审批不通过
	 */
	public static final int ACTION_APPROVE_NO = 31
	
	/**
	 * 关闭申请
	 */
	public static final int ACTION_CLOSE = 40
	
	/**
	 * 回收申请
	 */
	public static final int ACTION_REVOKE = 41
	/**
	 * 提交任务书
	 */
	public static final int ACTION_SUBMIT_TASK = 50
	/**
	 * 撤销提交任务书
	 */
	public static final int ACTION_CANCEL_TASK = 51
	
	/**
	 * 撤销确认任务书
	 */
	public static final int ACTION_CONFIRM_TASK = 52
	
	/**
	 * 提交阶段报告
	 */
	public static final int ACTION_SUBMIT_STAGE = 60
	/**
	 * 撤销阶段报告
	 */
	public static final int ACTION_CANCEL_STAGE =61
	
	String userId
	String userName
	int action
	Date date
	String content

	static belongsTo = [
		form: QemProject
	]
	
	static mapping = {
		table 		'qem_audit'
		userId		length: 10
		userName	length: 50
		content		length: 255
	}
	
	static constraints = {
		content 	nullable: true
	}
}
