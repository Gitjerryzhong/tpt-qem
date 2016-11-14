package cn.edu.bnuz.tms.place

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Term

class BookingForm {
	/**
	 * δ�ύ
	 */
	public static final int STATUS_NEW = 0

	/**
	 * ������
	 */
	public static final int STATUS_APPLYING = 1
	
	/**
	 * �����
	 */
	public static final int STATUS_CHECKED = 2
		
	/**
	 * ����׼
	 */
	public static final int STATUS_REJECTED = 3
	
	/**
	 * ����׼
	 */
	public static final int STATUS_APPROVED = 4
	
	/**
	 * �ѹر�
	 */
	public static final int STATUS_CLOSED = 5
	
	/**
	 * �ѳ���
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
				return status == STATUS_APPLYING || // �ύ
				       status == STATUS_CLOSED      // �ر�
			case STATUS_APPLYING:
				return status == STATUS_NEW ||      // ����
					   status == STATUS_CHECKED ||  // ���[ͨ��]
					   status == STATUS_REJECTED || // ���[��ͨ��]
					   status == STATUS_CLOSED      // �ر�
			case STATUS_CHECKED:
				return status == STATUS_APPROVED || // ��׼[ͨ��]
					   status == STATUS_REJECTED || // ���[��ͨ��]
					   status == STATUS_CLOSED      // �ر�
			case STATUS_REJECTED:
				return status == STATUS_APPLYING || // �ύ
				       status == STATUS_CLOSED      // �ر�
			case STATUS_APPROVED:
				return status == STATUS_REVOKED		// ����
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
