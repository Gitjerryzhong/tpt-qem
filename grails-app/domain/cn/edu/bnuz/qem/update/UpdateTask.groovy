package cn.edu.bnuz.qem.update

import java.util.Date;

import cn.edu.bnuz.qem.project.QemStage;
import cn.edu.bnuz.qem.project.QemType;
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.organization.Teacher;

class UpdateTask {
	public static final int	F_PERSON=0									//������
	public static final int	F_COLLEGE=1									//ѧԺ
	public static final int	F_UNIVERSITY=2								//ѧУ
	public static final int	AU_NONE=0									//δ��
	public static final int	AU_PASS=1									//ͨ��
	public static final int	AU_NG=2										//��ͨ��
	public static final int	AU_BG=3										//�˻�

    /**
	 * ��������Ϣ
	 */
	String teacherId
	String currentTitle
	String currentDegree
	String specailEmail
	String departmentId
	String	position
	String	phoneNum	
	/***
	 * ��Ŀ��Ϣ
	 */
	String projectName
//	long qemTypeId
//	int projectLevel
	long projectId
	long taskId
	String sn
//	String beginYear
	String expectedMid
	String expectedEnd
	String projectContent
	String expectedGain
	String members
	String others
	//������Ϣ
	String userId
	String userName
	Date commitDate	
	/**
	 * �������
	 * 1�������Ŀ������
	 * 2������
	 * 3�������Ŀ����
	 * 4���о������ش����
	 * 5��������ֹ��Ŀ
	 * 6���ı�ɹ���ʽ
	 * 7�����������
	 * 8������
	 */
	String	updateTypes		//�ѱ������id��¼
	String memo				//��������
	
	int flow				//����״̬ 0�������ˣ�1��ѧԺ��2��ѧУ
	int auditStatus				//���״̬ 0��δ��1��ͨ������2����ͨ����3���˻�
	static mapping = {
		table	'qem_update_task'
//		projectLevel column: "project_level", sqlType: "int", defaultValue:1
		memo column: "memo", sqlType: "text",length:6000
		currentTitle	length:20
		currentDegree	length:20
		specailEmail	length:50
		position		length:20
		phoneNum		length:30
//		beginYear		length:10
		expectedMid		length:10
		expectedEnd		length:10
		projectName		length:50
		members			length:100
		sn				length:50
	}
    static constraints = {
		teacherId		nullable:true
		currentTitle	nullable:true
		currentDegree	nullable:true
		specailEmail		nullable:true
		departmentId		nullable:true
		position		nullable:true
		phoneNum		nullable:true
		projectName		nullable:true
		sn				nullable:true
//		beginYear		nullable:true
		projectContent	(nullable:true,maxSize:1500)
		others		nullable:true
		expectedGain	(nullable:true,maxSize:1500)
		expectedMid		nullable:true
		expectedEnd		nullable:true
		members			nullable:true
		position		nullable:true
		memo(nullable:true)
    }
}
