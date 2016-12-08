package cn.edu.bnuz.qem.project

import cn.edu.bnuz.qem.review.Review

class QemStage {
	/**
	 * 年报
	 */
	public static final int STAGE_ANNUAL = 1
	/**
	 * 中报
	 */
	public static final int STAGE_MIDDLE = 2
	/**
	 * 结题
	 */
	public static final int STAGE_ENDING = 3
	public static final int	S_NEW=0									//新建
	public static final int	S_SUBMIT=1								//提交
	public static final int	S_CONFIRM=2								//确认
	public static final int	S_REVIEW=30								//评审
	public static final int	S_REVIEW_PASS=31						//评审通过
	public static final int	S_REVIEW_NG=32							//评审不通过
	public static final int	S_REVIEW_BK=33							//学校退回
	public static final int	S_REVIEW_PN=34							//暂缓通过
	/**
	 * 后来插进的学院审批状态
	 */
	public static final int	S_C_PASS=41								//学院通过
	public static final int	S_C_NG=42								//学院不通过
	public static final int	S_C_BK=43								//学院退回
	Integer currentStage
	String submitYear												//检查报告提交年份
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
