package cn.edu.bnuz.tms.card

import cn.edu.bnuz.tms.organization.Student

class ReissueRequest {
	/**
	 * δ�ύ
	 */
	public static final int STATUS_NEW = 0

	/**
	 * ������
	 */
	public static final int STATUS_APPLYING = 1

	/**
	 * ������
	 */
	public static final int STATUS_MAKING = 2
	
	/**
	 * ����׼
	 */
	public static final int STATUS_REJECTED = 3

	/**
	 * �������
	 */
	public static final int STATUS_FINISHED = 4
	
	Student student
	
	/**
	 * ����
	 */
	String reason
	
	/**
	 * ����ʱ��
	 */
	Date dateCreated
	
	/**
	 * �޸�ʱ��
	 */
	Date dateModified
	
	/**
	 * 0��δ�ύ
	 * 1��������
	 * 2��������
	 * 3������׼
	 * 4���������
	 */
	int status
	
	static mapping = {
		table 	'aff_card_reissue_request'
	}
	
	boolean allowStatus(int status) {
		switch(this.status) {
			case STATUS_NEW:
				return status == STATUS_APPLYING    // �ύ
			case STATUS_APPLYING:
				return status == STATUS_NEW ||      // ���� 
					   status == STATUS_MAKING ||   // ����
					   status == STATUS_REJECTED    // ����׼
			case STATUS_MAKING:
				return status == STATUS_APPLYING || // ɾ��������
				       status == STATUS_FINISHED    // ���
			case STATUS_REJECTED:
				return status == STATUS_APPLYING    // �ύ 
			case STATUS_FINISHED:
				return status == STATUS_MAKING      // ȡ�����
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
}
