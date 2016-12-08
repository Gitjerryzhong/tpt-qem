package cn.edu.bnuz.qem.project

import cn.edu.bnuz.qem.review.Review

class QemStage {
	/**
	 * �걨
	 */
	public static final int STAGE_ANNUAL = 1
	/**
	 * �б�
	 */
	public static final int STAGE_MIDDLE = 2
	/**
	 * ����
	 */
	public static final int STAGE_ENDING = 3
	public static final int	S_NEW=0									//�½�
	public static final int	S_SUBMIT=1								//�ύ
	public static final int	S_CONFIRM=2								//ȷ��
	public static final int	S_REVIEW=30								//����
	public static final int	S_REVIEW_PASS=31						//����ͨ��
	public static final int	S_REVIEW_NG=32							//����ͨ��
	public static final int	S_REVIEW_BK=33							//ѧУ�˻�
	public static final int	S_REVIEW_PN=34							//�ݻ�ͨ��
	/**
	 * ���������ѧԺ����״̬
	 */
	public static final int	S_C_PASS=41								//ѧԺͨ��
	public static final int	S_C_NG=42								//ѧԺ��ͨ��
	public static final int	S_C_BK=43								//ѧԺ�˻�
	Integer currentStage
	String submitYear												//��鱨���ύ���
	float fundingProvince
	float fundingUniversity
	float fundingCollege
	Date finishDate
	String progressText
	String unfinishedReson
	String memo
	String collegeAudit
	String endAudit
	Review	review
	int status
	static belongsTo =[task:QemTask]

	
	
	static mapping = {
		table	'qem_stage'
		submitYear	length: 6
		columns {
			fundingProvince defaultValue:0
			fundingUniversity defaultValue:0
			fundingCollege defaultValue:0
			currentStage defaultValue:1
			status  defaultValue:0
		}
	}
    static constraints = {
		finishDate		nullable:true
		progressText	nullable:true,maxsize:1500
		unfinishedReson	nullable:true,maxsize:1500
		memo			nullable:true,maxsize:1500
		review			nullable:true
		submitYear		nullable:true
		collegeAudit	nullable:true
		endAudit	nullable:true
    }
}
