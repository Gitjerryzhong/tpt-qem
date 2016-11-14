package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Term


/**
 * 假条。
 * 
 * @author 杨林
 * @since 0.1
 * @see LeaveItem
 */
class LeaveRequest {
	/**
	 * 新建(未提交)
	 */
	public static final int STATUS_NEW = 0

	/**
	 * 申请中(待批准)
	 */
	public static final int STATUS_APPLYING = 1

	/**
	 * 已批准
	 */
	public static final int STATUS_APPROVED = 2

	/**
	 * 不批准
	 */
	public static final int STATUS_REJECTED = 3

	/**
	 * 已销假
	 */
	public static final int STATUS_REPORT_BACK = 4

	/**
	 * 事假
	 */
	public static final int TYPE_PRIVATE_AFFAIR = 1

	/**
	 * 病假
	 */
	public static final int TYPE_SICK_LEAVE = 2

	/**
	 * 公假
	 */
	public static final int TYPE_PUBLIC_AFFAIR = 3

	/**
	 * 事由
	 */
	String reason
	
	/**
	 * 状态：新建、申请中、已批准、不批准、销假
	 */
	int status

	/**
	 * 请假类型：事假、病假、公假
	 */
	int type

	/**
	 * 创建时间
	 */
	Date dateCreated
	
	/**
	 * 修改时间。不使用lastUpdated，手工进行字段更新。
	 */
	Date dateModified

	/**
	 * 批准人
	 */
	Teacher approver

	/**
	 * 批准时间
	 */
	Date dateApproved

	/**
	 * 学期
	 */
	Term term
	
	static hasMany = [items : LeaveItem]

	static belongsTo = [
		student  : Student
	]

	static mapping = {
		table 	'rc_leave_request'
		term 	column:'term'
		status 	defaultValue: STATUS_NEW
	}

	static constraints = {
		dateApproved nullable: true
		approver     nullable: true
		reason       blank: false
	}

	boolean allowStatus(int status) {
		switch(this.status) {
			case STATUS_NEW:
				return status == STATUS_APPLYING
			case STATUS_APPLYING:
				return status == STATUS_NEW || status == STATUS_APPROVED || status == STATUS_REJECTED
			case STATUS_APPROVED:
				return status == STATUS_REPORT_BACK;
			case STATUS_REJECTED:
				return status == STATUS_APPLYING
			case STATUS_REPORT_BACK:
				return false;
			default:
				return false;
		}
	}
	
	boolean allowUpdate() {
		return status == STATUS_NEW || status == STATUS_REJECTED
	}
	
	boolean allowDelete() {
		return status == STATUS_NEW || status == STATUS_REJECTED
	}

	@Override
	String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append("\nstudent: $student\n")
				.append("type: $type\n")
				.append("reason: $reason\n")
				.append("status:$status\n")
				.append("items:\n")
		items.each {
			sb.append("\t").append(it).append("\n")
		}

		return sb.toString()
	}
}
