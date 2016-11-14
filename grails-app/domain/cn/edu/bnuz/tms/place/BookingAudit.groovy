package cn.edu.bnuz.tms.place

class BookingAudit {
	/**
	 * �ύ����
	 */
	public static final int ACTION_COMMIT = 10
	
	/**
	 * ��������
	 */
	public static final int ACTION_CANCEL = 11
	
	/**
	 * �޸�����
	 */
	public static final int ACTION_MODIFY = 12
	
	/**
	 * ���ͨ��
	 */
	public static final int ACTION_CHECK_YES = 20
	
	/**
	 * ��˲�ͨ��
	 */
	public static final int ACTION_CHECK_NO = 21
	
	/**
	 * ����ͨ��
	 */
	public static final int ACTION_APPROVE_YES = 30
	
	/**
	 * ������ͨ��
	 */
	public static final int ACTION_APPROVE_NO = 31
	
	/**
	 * �ر�����
	 */
	public static final int ACTION_CLOSE = 40
	
	/**
	 * ��������
	 */
	public static final int ACTION_REVOKE = 41
	
	String userId
	String userName
	int action
	Date date
	String content

	static belongsTo = [
		form: BookingForm
	]
	
	static mapping = {
		table 		'aff_booking_audit'
		userId		length: 10
		userName	length: 50
		content		length: 255
	}
	
	static constraints = {
		content 	nullable: true
	}
}
