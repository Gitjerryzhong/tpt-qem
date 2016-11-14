package cn.edu.bnuz.qem.project

import cn.edu.bnuz.qem.review.Review
import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher;
import cn.edu.bnuz.tms.teaching.Major

class QemProject {
	/**
	 * �ύ
	 */
	public static final int ACTION_SUBMIT = 1
	/**
	 * ����
	 */
	public static final int ACTION_CANCEL = 2
	/**
	 * ѧԺ���
	 */
	public static final int ACTION_COLLEGE_AUDIT = 3
	/**
	 * ѧԺ��˳���
	 */
	public static final int ACTION_COLLEGE_CANCEL = 4
	/**
	 * У��
	 */
	public static final int UNIVER_LEVEL = 1
	/**
	 * ʡ��
	 */
	public static final int PROVINCE_LEVEL = 2
	/**
	 * ���Ҽ�
	 */
	public static final int NATIONAL_LEVEL = 3
	
	/**
	 * ��������Ϣ
	 */
	Teacher teacher	
	String currentTitle	
	String currentDegree	
	String specailEmail
	/**
	 * ѧ��
	 */
	String discipline	
	/**
	 * ����
	 */
	String direction
//	QemTask qemTask
	Department department
	String	major
	String	position
	String	phoneNum
	QemType qemType
	Review review
	String projectName
	String expectedGain
	boolean isSubmit
	boolean collegePass
	/**
	 * δ���
	 */
	public static final int STATUS_APPLYING = 0
	/**
	 * ���ͨ��
	 */
	public static final int STATUS_CHECKED = 1		
	/**
	 * ��˲�ͨ��
	 */
	public static final int STATUS_REJECTED = 2
	/**
	 * �ѹر�
	 */
	public static final int STATUS_CLOSED = 6
	/**
	 * ѧԺ������
	 */
	int collegeStatus
	String collegeAudit
	boolean specialEdit
	int projectLevel
	Date commitDate
	Notice notice
	String groupId
	String bn
	String otherLinks	//�����ַ�������ַ�ԷֺŸ���
	static hasMany = [
		audits: QemAudit
	]
	static mapping={
		table 'qem_project'
		
		currentTitle	length:20
		currentDegree	length:20
		specailEmail	length:50
		discipline		length:20
		direction		length:20
		position		length:20
		phoneNum		length:30
		projectName		length:50
		groupId			length:10
		bn				length:10
	}
    static constraints = {		
		notice	 	nullable:true
		bn	 		nullable:true
		discipline	nullable:true
		direction	nullable:true
		major		nullable:true
		position	nullable:true
		phoneNum	nullable:true
		groupId		nullable:true
		otherLinks	nullable:true
		collegeAudit	nullable:true
		expectedGain(nullable:true,maxSize:1500)
    }
	boolean allowAction(int action) {
		switch(action) {
			case ACTION_SUBMIT:
				return !isSubmit || collegeStatus==2									//δ�ύ����ѧԺ�˻�
			case ACTION_CANCEL: 			
			case ACTION_COLLEGE_AUDIT: 
				return isSubmit && collegeStatus==0					//���ύ��ѧԺδ����				
			case ACTION_COLLEGE_CANCEL:
				return collegeStatus>0 && review.status==Review.STATUS_NEW //ѧԺ������������δ��ʼר������			
			default:
				return false
		}
	}
}
