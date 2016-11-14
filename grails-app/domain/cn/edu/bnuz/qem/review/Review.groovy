package cn.edu.bnuz.qem.review

class Review {
	/**
	 * ״̬���½���������ר�ҡ������󡢴��ϻᡢ�������
	 */	
	/**
	 * �½�
	 */
	public static final int STATUS_NEW = 0

	/**
	 * �ѷ���ר��
	 */
	public static final int STATUS_EXPERT = 1
	
	/**
	 * ר��������
	 */
	public static final int STATUS_ASSESSED = 2
		
	/**
	 * ���ϻ�
	 */
	public static final int STATUS_MEETING = 3
	
	/**
	 * ����ͨ��
	 */
	public static final int STATUS_PASS = 4
	
	/**
	 * ����ͨ��
	 */
	public static final int STATUS_REJECTED = 5
	
	/**
	 * ����������޸�
	 */
	public static final int STATUS_UPDATETOPASS = 6
	
	String detail

	String result

	String additionalDetail	
	
	String experts
	int status
	
	static hasMany = [
		expertView: ExpertsView
	]
	
	static mapping={
		table	'qem_review'
		experts length:60
	}
	
    static constraints = {
		detail(maxSize:1500,nullable:true)
		result  nullable:true
		experts nullable:true
		additionalDetail nullable:true
//		attachments nullable:true
		additionalDetail(maxSize:1500)
    }
}
