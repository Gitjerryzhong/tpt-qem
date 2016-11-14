package cn.edu.bnuz.qem.project

import java.util.Date;

class QemAudit {

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
	 * ѧԺ���ͨ��
	 */
	public static final int ACTION_CHECK_YES = 20
	
	/**
	 * ѧԺ��˲�ͨ��
	 */
	public static final int ACTION_CHECK_NO = 21
	
	/**
	 * ר�ҽ�������
	 */
	public static final int ACTION_BEGIN_YES = 22
	
	/**
	 * ר�ҽ��鲻������
	 */
	public static final int ACTION_BEGIN_NO = 23
	
	/**
	 * ר�ҽ��鲻������
	 */
	public static final int ACTION_BEGIN_NONE = 25
	
	/**
	 * ѧԺ��˳���
	 */
	public static final int ACTION_CHECK_CANCEL = 24
	
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
	 * �ύ������
	 */
	public static final int ACTION_SUBMIT_TASK = 50
	/**
	 * �����ύ������
	 */
	public static final int ACTION_CANCEL_TASK = 51
	
	/**
	 * ����ȷ��������
	 */
	public static final int ACTION_CONFIRM_TASK = 52
	
	/**
	 * �ύ�׶α���
	 */
	public static final int ACTION_SUBMIT_STAGE = 60
	/**
	 * �����׶α���
	 */
	public static final int ACTION_CANCEL_STAGE =61
	
	String userId
	String userName
	int action
	Date date
	String content

	static belongsTo = [
		form: QemProject
	]
	
	static mapping = {
		table 		'qem_audit'
		userId		length: 10
		userName	length: 50
		content		length: 255
	}
	
	static constraints = {
		content 	nullable: true
	}
}
