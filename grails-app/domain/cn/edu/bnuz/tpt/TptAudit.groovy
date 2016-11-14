package cn.edu.bnuz.tpt

import java.util.Date;

class TptAudit {

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
	
	/**
	 * �����ϴ�
	 */
	public static final int ACTION_UPLOAD = 51
	
	/**
	 * �޸����ĳɼ�
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
