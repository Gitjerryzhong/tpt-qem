package cn.edu.bnuz.qem.project

import java.util.Date;

class QemTaskAudit {

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
	 * ר������ͨ��
	 */
	public static final int ACTION_BEGIN_YES = 22
	
	/**
	 * ר������ͨ��
	 */
	public static final int ACTION_BEGIN_NO = 23
	
	/**
	 * ר����Ȩ
	 */
	public static final int ACTION_BEGIN_NONE = 25
	
	/**
	 * ѧԺ��˳���
	 */
	public static final int ACTION_CHECK_CANCEL = 24
	
	/**
	 * ѧԺ��˳���
	 */
	public static final int ACTION_CHECK_BACK = 26
	
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
	public static final int ACTION_NEW_TASK = 50
	/**
	 * �ύ������
	 */
	public static final int ACTION_SUBMIT_TASK = 51
	/**
	 * �����ύ������
	 */
	public static final int ACTION_CANCEL_TASK = 52
	
	/**
	 * ȷ��������
	 */
	public static final int ACTION_CONFIRM_TASK = 53
	
	/**
	 * �ύ�׶α���
	 */
	public static final int ACTION_SUBMIT_STAGE = 60
	/**
	 * �����׶α���
	 */
	public static final int ACTION_CANCEL_STAGE =61
	/**
	 * �׶α�������ͨ��
	 */
	public static final int ACTION_STAGE_YES =62
	/**
	 * �׶α�������ͨ��
	 */
	public static final int ACTION_STAGE_NO =63
	
	String userId
	String userName
	int action
	Date date
	String content
	long objectId
	String src

	
	
	static mapping = {
		table 		'qem_task_audit'
		userId		length: 10
		userName	length: 50
		content		length: 255
	}
	
	static constraints = {
		content 	nullable: true
	}
}
