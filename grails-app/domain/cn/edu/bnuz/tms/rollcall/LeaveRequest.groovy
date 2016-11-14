package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Term


/**
 * ������
 * 
 * @author ����
 * @since 0.1
 * @see LeaveItem
 */
class LeaveRequest {
	/**
	 * �½�(δ�ύ)
	 */
	public static final int STATUS_NEW = 0

	/**
	 * ������(����׼)
	 */
	public static final int STATUS_APPLYING = 1

	/**
	 * ����׼
	 */
	public static final int STATUS_APPROVED = 2

	/**
	 * ����׼
	 */
	public static final int STATUS_REJECTED = 3

	/**
	 * ������
	 */
	public static final int STATUS_REPORT_BACK = 4

	/**
	 * �¼�
	 */
	public static final int TYPE_PRIVATE_AFFAIR = 1

	/**
	 * ����
	 */
	public static final int TYPE_SICK_LEAVE = 2

	/**
	 * ����
	 */
	public static final int TYPE_PUBLIC_AFFAIR = 3

	/**
	 * ����
	 */
	String reason
	
	/**
	 * ״̬���½��������С�����׼������׼������
	 */
	int status

	/**
	 * ������ͣ��¼١����١�����
	 */
	int type

	/**
	 * ����ʱ��
	 */
	Date dateCreated
	
	/**
	 * �޸�ʱ�䡣��ʹ��lastUpdated���ֹ������ֶθ��¡�
	 */
	Date dateModified

	/**
	 * ��׼��
	 */
	Teacher approver

	/**
	 * ��׼ʱ��
	 */
	Date dateApproved

	/**
	 * ѧ��
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
