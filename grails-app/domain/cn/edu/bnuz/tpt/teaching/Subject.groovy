package cn.edu.bnuz.tpt.teaching
import cn.edu.bnuz.tms.organization.Department

/**
 * �ڲ�רҵ���꼶רҵģ�壩
 * @author Yang Lin
 */
class Subject {
	/**
	 * רҵID
	 * <pre>
	 * 0101
	 * --=-
	 * | ||
	 * | | `-- ��רҵ
	 * | `---- ����רҵ
	 * `------ ѧԺ����
	 * </pre>
	 */
	String id

	/**
	 * ����
	 */
	String name

	/**
	 * Ӣ������
	 */
	String englishName

	/**
	 * ���
	 */
	String shortName

	/**
	 * ���-1:����;2:˶ʿ;3:��ʿ;9:����
	 */
	Integer educationLevel

	/**
	 * ѧ��
	 */
	Integer lengthOfSchooling

	/**
	 * ֹͣ����
	 */
	Boolean stopEnroll

	/**
	 * �Ƿ����������ѧ��Ŀ4+0��Sino-Foreign Joint Program��
	 */
	Boolean isJointProgram

	/**
	 * �Ƿ�������������˫ѧλ2+2
	 */
	Boolean isDualDegree

	/**
	 * �Ƿ�ר����
	 */
	Boolean isTopUp


	/**
	 * ����ѧԺ
	 */
	Department department

	static belongsTo = [department : Department]

	static mapping = {
		table             'tpt_subject'
		id                generator: 'assigned', length: 4, comment: '�ڲ�רҵID'
		name              length: 50, comment: '����'
		englishName       length: 100, comment: 'Ӣ������'
		shortName         length: 20, comment: '���'
		educationLevel    defaultValue: "1", comment: '���'
		lengthOfSchooling defaultValue: "4", comment: 'ѧ��'
		stopEnroll        defaultValue: "false", comment: '�Ƿ�ֹͣ����'
		isJointProgram    defaultValue: "false", comment: '�Ƿ����������ѧ��Ŀ��4+0��'
		isDualDegree      defaultValue: "false", comment: '�Ƿ�������������˫ѧλ��2+2��'
		isTopUp           defaultValue: "false", comment: '�Ƿ�ר����'
		department        comment: '����ѧԺ'
	}

	static constraints = {
		englishName       nullable: true, maxSize: 100
	}
}
