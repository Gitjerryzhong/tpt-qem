package cn.edu.bnuz.tpt

class TptRequest {
	/**
	 * δ�ύ
	 */
	public static final int STATUS_NEW = 0

	/**
	 * ������
	 */
	public static final int STATUS_APPLYING = 1
	
	/**
	 * �������Ƭ��֤�顢�ɼ�
	 */
	public static final int STATUS_CHECKED = 2
		
	/**
	 * ��Ƭ��֤�顢�ɼ���˲�ͨ��
	 */
	public static final int STATUS_REJECTED = 3
	
	/**
	 * ���������
	 */
	public static final int STATUS_PAPERUPLOAD = 4
	
	/**
	 * ���������ͨ��
	 */
	public static final int STATUS_PAPERCHECKED = 5
	
	/**
	 * ������˲�ͨ��
	 */
	public static final int STATUS_PAPERREJECTED = 7
	
	/**
	 * �ѹر�
	 */
	public static final int STATUS_CLOSED = 6
	String userId
	String userName
	String contact
	String phoneNumber
	String email
	TptCollege foreignCollege
	String collegeName
	String foreignMajor
	String photoUrl
	String resultsUrl
	String certificateUrl
	String paperUrl
	Date dateCreate
	String bn
	boolean allIn
	TptMentor mentor
	static hasMany = [
		audits: TptAudit
	]
	int status
	

	
   static mapping = {
		table 			'tpt_request'
		userId			length: 10
		userName		length: 50
		phoneNumber 	length: 50
		foreignCollege	length: 50
		foreignMajor	length: 50
		bn				length: 10
		photoUrl		length: 50
		resultsUrl		length: 50
		certificateUrl	length: 50
		paperUrl		length: 50
	}
	
	static constraints = {
		email(email:true, blank:false)
		photoUrl			nullable: true
		resultsUrl		nullable: true
		certificateUrl     	nullable: true
		paperUrl 	nullable: true
		allIn 	nullable: true
		collegeName	nullable:true
		foreignCollege	nullable:true
		mentor	nullable:true
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
				return status == STATUS_PAPERUPLOAD || // �ϴ�����
					   status == STATUS_CLOSED      // �ر�
			case STATUS_REJECTED:
				return status == STATUS_APPLYING || // �ύ
					   status == STATUS_CLOSED      // �ر�		
			case STATUS_PAPERUPLOAD:
			   	return status == STATUS_PAPERCHECKED || // �������ͨ��
			   		  status == STATUS_PAPERREJECTED  ||     // ������˲�ͨ��
					  status == STATUS_CLOSED      // �ر�
			case STATUS_PAPERREJECTED:
						return status == STATUS_PAPERUPLOAD  // �ϴ�����
			default:
				return false
		}
	}
}
