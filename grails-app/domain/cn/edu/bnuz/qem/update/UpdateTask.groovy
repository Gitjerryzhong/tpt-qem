package cn.edu.bnuz.qem.update

import java.util.Date;

import cn.edu.bnuz.qem.project.QemStage;
import cn.edu.bnuz.qem.project.QemType;
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.organization.Teacher;

class UpdateTask {

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
	long qemTypeId
	int projectLevel
	long projectId
	long taskId
	String sn
	String beginYear
	String expectedMid
	String expectedEnd
	String projectContent
	String expectedGain
	String members
	String otherHeader
	//������Ϣ
	String userId
	String userName
	Date commitDate	
	int	updateType			//�������1�������Ŀ�����ˣ�2�����ڣ�3�������Ŀ���ƣ�4���о������ش������5��������ֹ��Ŀ��6�ı�ɹ���ʽ��7������
	String	origStyle		//ԭ�ɹ���ʽ
	String memo				//��������
	String checker			//�����
	int status				//���״̬ 0��δ��1��ͨ����2��ͨ����3���˻�
	Date checkDate			//��������
	String audit			//�������
	static mapping = {
		table	'qem_update_task'
		projectLevel column: "project_level", sqlType: "int", defaultValue:1
		memo column: "memo", sqlType: "text",length:6000
		currentTitle	length:20
		currentDegree	length:20
		specailEmail	length:50
		position		length:20
		phoneNum		length:30
		beginYear		length:10
		expectedMid		length:10
		expectedEnd		length:10
		projectName		length:50
		members			length:100
		sn				length:50
	}
    static constraints = {
		sn				nullable:true
		beginYear		nullable:true
		projectContent	(nullable:true,maxSize:1500)
		otherHeader		nullable:true
		expectedGain	(nullable:true,maxSize:1500)
		expectedMid		nullable:true
		expectedEnd		nullable:true
		members			nullable:true
		checker			nullable:true
		checkDate		nullable:true
		position		nullable:true
		origStyle		nullable:true
		memo(nullable:true)
		audit(nullable:true,maxSize:1000)
    }
}
