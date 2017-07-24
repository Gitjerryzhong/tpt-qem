package cn.edu.bnuz.qem.project
import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.organization.Teacher;

class QemTask {
	public static final int	S_NEW=0									//�½�������
	public static final int	S_SUBMIT=1								//�ύ������
	public static final int	S_CONFIRM=2								//ȷ��������
	public static final int	S_NG=3									//��Ŀ��ֹ
	public static final int	S_BK=4									//ѧУ�˻�������
	public static final int	S_PAUSE=5								//��Ŀ��ֹ
	public static final int	S_CONFIRM_C=201							//ѧԺȷ��������
	public static final int	S_NG_C=202								//ѧԺ����׼������
	public static final int	S_BK_C=203								//ѧԺ�˻�������
	public static final int	S_ANNUAL_START=9						//��쿪ʼ
	public static final int	S_ANNUAL_SUBMIT=10						//��챨���ύ
	public static final int	S_ANNUAL_CONFIRM=11						//���ȷ������
	public static final int	S_ANNUAL_PASS_C=1101					//ѧԺͨ�����
	public static final int	S_ANNUAL_NG_C=1102						//ѧԺ��ͨ�����
	public static final int	S_ANNUAL_BK_C=1103						//ѧԺ�˻����
	public static final int	S_ANNUAL_EXPERT=12						//������챨������ר��
	public static final int	S_ANNUAL_REVIEW=13						//ר��������챨��
	public static final int	S_ANNUAL_PASS=14						//��챨������ͨ��
	public static final int	S_ANNUAL_NG=15							//��챨������ͨ��
	public static final int	S_ANNUAL_BK=16							//��챨���˻�ѧԺ
	public static final int	S_MID_START=19							//�м쿪ʼ
	public static final int	S_MID_SUBMIT=20							//�м챨���ύ
	public static final int	S_MID_CONFIRM=21						//�м챨��ȷ������
	public static final int	S_MID_PASS_C=2101						//ѧԺͨ���б�
	public static final int	S_MID_NG_C=2102							//ѧԺ��ͨ���б�
	public static final int	S_MID_BK_C=2103							//ѧԺ�˻��б�
	public static final int	S_MID_EXPERT=22							//�����м챨������ר��
	public static final int	S_MID_REVIEW=23							//ר�������м챨��
	public static final int	S_MID_PASS=24							//�м챨������ͨ��
	public static final int	S_MID_NG=25								//�м챨������ͨ��
	public static final int	S_MID_BK=26								//�м챨���˻�ѧԺ
	public static final int	S_MID_DL=27								//�м챨����������
	public static final int	S_END_START=29							//���⿪ʼ
	public static final int	S_END_SUBMIT=30							//�������ύ
	public static final int	S_END_CONFIRM=31						//������ȷ������
	public static final int	S_END_PASS_C=3101						//ѧԺͨ������
	public static final int	S_END_NG_C=3102							//ѧԺ��ͨ������
	public static final int	S_END_BK_C=3103							//ѧԺ�˻ؽ���
	public static final int	S_END_EXPERT=32							//���Ž���������ר��
	public static final int	S_END_REVIEW=33							//ר�����������
	public static final int	S_END_PASS=34							//����������ͨ��
	public static final int	S_END_NG=35								//���ⲻͨ��
	public static final int	S_END_BK=36								//�����˻�ѧԺ
	public static final int	S_END_DL=37								//�����ݻ�ͨ��
	public static final int	STATUS_ACTIVE=10						//����
	public static final int	STATUS_ENDING=20						//����
	public static final int	STATUS_EXCEPTION_OK=31					//�������ֹ��������
	public static final int	STATUS_EXCEPTION_NG=32					//��ֹ
	public static final int	STATUS_EXCEPTION_PAUSE=33				//��ֹ

	/**
	 * ��������Ϣ
	 */
	Teacher teacher
	String currentTitle
	String currentDegree
	String specailEmail
	Department department
	String	position
	String	phoneNum	
	/***
	 * ��Ŀ��Ϣ
	 */
	String projectName
	QemType qemType
	int projectLevel
	long projectId
	static hasMany =[stage:QemStage]
	String sn
	String beginYear
	String expectedMid
	String expectedEnd
	String projectContent
	String expectedGain
	String members
	float fundingProvince
	float fundingUniversity
	float fundingCollege
	boolean isGood
	QemStage currentStage
	Date endDate
	String summary			//ժҪ/�ؼ���
	String mainContent		//��Ҫ����
	String mainGain			//��Ҫ�ɹ�
	String applicationArea	//Ӧ�����
	String collegeAudit
	String contractAudit	//��ͬ������
	String otherLinks
	boolean hasMid			//������
	int delay

	
	/***
	 * ִ��״̬�����С����⡢�����쳣�������
	 */
	int	status
	/***
	 * ȫ����״̬
	 */
	int runStatus
	String otherHeader
	String memo
	static mapping = {
		table	'qem_task'
		columns {
			fundingProvince defaultValue:0
			fundingUniversity defaultValue:0
			fundingCollege defaultValue:0
			projectLevel defaultValue:1
			isGood defaultValue:false
		}
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
		memo			(nullable:true,maxSize:1500)
		expectedGain	nullable:true
		expectedMid		nullable:true
		expectedEnd		nullable:true
		endDate			nullable:true
		members			nullable:true
		currentStage	nullable:true
		position		nullable:true
		collegeAudit	nullable:true
		contractAudit	nullable:true
		otherLinks		nullable:true
		delay			nullable:true
		summary(nullable:true,maxSize:1500)
		mainContent(nullable:true,maxSize:1500)
		mainGain(nullable:true,maxSize: 1500)
		applicationArea(nullable:true,maxSize: 1500)
    }
	int passAction() {
		switch(runStatus) {
			case S_SUBMIT:
			case S_BK:
				return 	S_CONFIRM_C											//ѧԺ���ͨ��
			case S_CONFIRM_C:
				return S_CONFIRM
			case S_ANNUAL_SUBMIT:
			case S_ANNUAL_BK:
				return S_ANNUAL_PASS_C										//ѧԺͨ�����
			case S_ANNUAL_PASS_C:
			case S_ANNUAL_NG_C:
				return S_ANNUAL_PASS										//ѧУͨ�����
			case S_MID_SUBMIT:
			case S_MID_BK:
				return S_MID_PASS_C											//ѧԺͨ������
			case S_MID_PASS_C:
			case S_MID_NG_C:
				return S_MID_PASS
			case S_END_SUBMIT:
			case S_END_BK:
				return S_END_PASS_C
			case S_END_PASS_C:
			case S_END_NG_C:
				return S_END_PASS
			default:
				return runStatus
		}
	}
	int ngAction(){
		switch(runStatus) {
			case S_SUBMIT:
			case S_BK:
				return 	S_NG_C													//ѧԺ��˲�ͨ��
			case S_NG_C:
			case S_CONFIRM_C:
				return S_NG
			case S_ANNUAL_SUBMIT:
			case S_ANNUAL_BK:
				return S_ANNUAL_NG_C										//ѧԺ��ͨ�����
			case S_ANNUAL_PASS_C:
			case S_ANNUAL_NG_C:
				return S_ANNUAL_NG										//ѧУ��ͨ�����
			case S_MID_SUBMIT:
			case S_MID_BK:
				return S_MID_NG_C											//ѧԺ��ͨ������
			case S_MID_PASS_C:
			case S_MID_NG_C:
				return S_MID_NG
			case S_END_SUBMIT:
			case S_END_BK:
				return S_END_NG_C
			case S_END_PASS_C:
			case S_END_NG_C:
				return S_END_NG
			default:
				return runStatus
		}
	}
	int bkAction(){
		switch(runStatus) {
			case S_SUBMIT:
			case S_BK:
				return 	S_BK_C													//ѧԺ����˻�
			case S_CONFIRM_C:
			case S_NG_C:
				return S_BK
			case S_ANNUAL_SUBMIT:
			case S_ANNUAL_BK:
				return S_ANNUAL_BK_C										//ѧԺ�˻����
			case S_ANNUAL_PASS_C:
			case S_ANNUAL_NG_C:
				return S_ANNUAL_BK											//ѧУ�˻����
			case S_MID_SUBMIT:
			case S_MID_BK:
				return S_MID_BK_C											//ѧԺ�˻�����
			case S_MID_PASS_C:
			case S_MID_NG_C:
				return S_MID_BK
			case S_END_SUBMIT:
			case S_END_BK:
				return S_END_BK_C
			case S_END_PASS_C:
			case S_END_NG_C:
				return S_END_BK
			default:
				return runStatus
		}
	}
	int dlAction(){
		switch(runStatus) {
			case S_MID_PASS_C:
			case S_MID_NG_C:
				return S_MID_DL
			case S_END_PASS_C:
			case S_END_NG_C:
				return S_END_DL
			default:
				return runStatus
		}
	}
	int confirm(){
		switch(runStatus) {
			case S_ANNUAL_PASS_C:
			case S_ANNUAL_NG_C:
				return S_ANNUAL_CONFIRM
			case S_MID_PASS_C:
			case S_MID_NG_C:
				return S_MID_CONFIRM										
			case S_END_PASS_C:
			case S_END_NG_C:
					return S_END_CONFIRM
			default:
				return runStatus
		}
	}
}
