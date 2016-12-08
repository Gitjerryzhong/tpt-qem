package cn.edu.bnuz.qem.project

import java.util.Date;

class QemTaskAudit {

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
	 * 专家评审通过
	 */
	public static final int ACTION_BEGIN_YES = 22
	
	/**
	 * 专家评审不通过
	 */
	public static final int ACTION_BEGIN_NO = 23
	
	/**
	 * 专家弃权
	 */
	public static final int ACTION_BEGIN_NONE = 25
	
	/**
	 * 学院审核撤销
	 */
	public static final int ACTION_CHECK_CANCEL = 24
	
	/**
	 * 学院审核撤销
	 */
	public static final int ACTION_CHECK_BACK = 26
	
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
	public static final int ACTION_NEW_TASK = 50
	/**
	 * 提交任务书
	 */
	public static final int ACTION_SUBMIT_TASK = 51
	/**
	 * 撤销提交任务书
	 */
	public static final int ACTION_CANCEL_TASK = 52
	
	/**
	 * 确认任务书
	 */
	public static final int ACTION_CONFIRM_TASK = 53
	
	/**
	 * 提交阶段报告
	 */
	public static final int ACTION_SUBMIT_STAGE = 60
	/**
	 * 撤销阶段报告
	 */
	public static final int ACTION_CANCEL_STAGE =61
	/**
	 * 阶段报告评审通过
	 */
	public static final int ACTION_STAGE_YES =62
	/**
	 * 阶段报告评审不通过
	 */
	public static final int ACTION_STAGE_NO =63
	
	String userId
	String userName
	int action
	Date date
	String content
	long objectId
	String src

	
	
	static mapping = {
		table 		'qem_task_audit'
		userId		length: 10
		userName	length: 50
		content		length: 255
	}
	
	static constraints = {
		content 	nullable: true
	}
}
