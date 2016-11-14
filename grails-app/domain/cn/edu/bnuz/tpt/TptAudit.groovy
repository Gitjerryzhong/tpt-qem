package cn.edu.bnuz.tpt

import java.util.Date;

class TptAudit {

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
	 * 审核通过
	 */
	public static final int ACTION_CHECK_YES = 20
	
	/**
	 * 审核不通过
	 */
	public static final int ACTION_CHECK_NO = 21
	
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
	 * 论文上传
	 */
	public static final int ACTION_UPLOAD = 51
	
	/**
	 * 修改论文成绩
	 */
	public static final int ACTION_UPDATESCORT = 52
	String userId
	String userName
	int action
	Date date
	String content

	static belongsTo = [
		form: TptRequest
	]
	
	static mapping = {
		table 		'tpt_audit'
		userId		length: 10
		userName	length: 50
		content		length: 255
	}
	
	static constraints = {
		content 	nullable: true
	}
}
