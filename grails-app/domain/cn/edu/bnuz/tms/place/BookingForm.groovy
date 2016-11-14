package cn.edu.bnuz.tms.place

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Term

class BookingForm {
	/**
	 * 未提交
	 */
	public static final int STATUS_NEW = 0

	/**
	 * 申请中
	 */
	public static final int STATUS_APPLYING = 1
	
	/**
	 * 已审核
	 */
	public static final int STATUS_CHECKED = 2
		
	/**
	 * 不批准
	 */
	public static final int STATUS_REJECTED = 3
	
	/**
	 * 已批准
	 */
	public static final int STATUS_APPROVED = 4
	
	/**
	 * 已关闭
	 */
	public static final int STATUS_CLOSED = 5
	
	/**
	 * 已撤销
	 */
	public static final int STATUS_REVOKED = 6

	String userId
	String userName
	String phoneNumber
	Term term
	Department department
	BookingType type
	String reason
	Date dateCreated
	Date dateModified
	Teacher checker
	Date dateChecked
	Teacher approver
	Date dateApproved
	BookingReport report
	int status
	
	static hasMany = [
		items : BookingItem,
		audits: BookingAudit
	]
	
	static mapping = {
		table 		'aff_booking_form'
		userId		length: 10
		userName	length: 50
		phoneNumber length: 50
		reason		length: 255
	}
	
	static constraints = {
		checker			nullable: true
		dateChecked		nullable: true
		approver     	nullable: true
		dateApproved 	nullable: true
		report			nullable: true
	}
	
	boolean allowStatus(int status) {
		switch(this.status) {
			case STATUS_NEW:
				return status == STATUS_APPLYING || // 提交
				       status == STATUS_CLOSED      // 关闭
			case STATUS_APPLYING:
				return status == STATUS_NEW ||      // 撤销
					   status == STATUS_CHECKED ||  // 审核[通过]
					   status == STATUS_REJECTED || // 审核[不通过]
					   status == STATUS_CLOSED      // 关闭
			case STATUS_CHECKED:
				return status == STATUS_APPROVED || // 批准[通过]
					   status == STATUS_REJECTED || // 入库[不通过]
					   status == STATUS_CLOSED      // 关闭
			case STATUS_REJECTED:
				return status == STATUS_APPLYING || // 提交
				       status == STATUS_CLOSED      // 关闭
			case STATUS_APPROVED:
				return status == STATUS_REVOKED		// 回收
			default:
				return false
		}
	}
	
	boolean allowUpdate() {
		return status == STATUS_NEW || status == STATUS_REJECTED
	}
	
	boolean allowDelete() {
		return status == STATUS_NEW || status == STATUS_REJECTED
	}
	
	static allowActions(int status) {
		switch(status) {
			case STATUS_NEW:
			case STATUS_REJECTED:
				return ["edit", "apply", "remove"]
			case STATUS_APPLYING:
				return ["cancel"]
			default:
				return []
		}
	}
}
