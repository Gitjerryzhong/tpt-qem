package cn.edu.bnuz.qem.project
import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.organization.Teacher;

class QemTask {
	public static final int	S_NEW=0									//新建任务书
	public static final int	S_SUBMIT=1								//提交任务书
	public static final int	S_CONFIRM=2								//确认任务书
	public static final int	S_NG=3									//项目终止
	public static final int	S_BK=4									//学校退回任务书
	public static final int	S_PAUSE=5								//项目中止
	public static final int	S_CONFIRM_C=201							//学院确认任务书
	public static final int	S_NG_C=202								//学院不批准任务书
	public static final int	S_BK_C=203								//学院退回任务书
	public static final int	S_ANNUAL_START=9						//年检开始
	public static final int	S_ANNUAL_SUBMIT=10						//年检报告提交
	public static final int	S_ANNUAL_CONFIRM=11						//年检确定评审
	public static final int	S_ANNUAL_PASS_C=1101					//学院通过年检
	public static final int	S_ANNUAL_NG_C=1102						//学院不通过年检
	public static final int	S_ANNUAL_BK_C=1103						//学院退回年检
	public static final int	S_ANNUAL_EXPERT=12						//安排年检报告评审专家
	public static final int	S_ANNUAL_REVIEW=13						//专家评审年检报告
	public static final int	S_ANNUAL_PASS=14						//年检报告评审通过
	public static final int	S_ANNUAL_NG=15							//年检报告评审不通过
	public static final int	S_ANNUAL_BK=16							//年检报告退回学院
	public static final int	S_MID_START=19							//中检开始
	public static final int	S_MID_SUBMIT=20							//中检报告提交
	public static final int	S_MID_CONFIRM=21						//中检报告确定评审
	public static final int	S_MID_PASS_C=2101						//学院通过中报
	public static final int	S_MID_NG_C=2102							//学院不通过中报
	public static final int	S_MID_BK_C=2103							//学院退回中报
	public static final int	S_MID_EXPERT=22							//安排中检报告评审专家
	public static final int	S_MID_REVIEW=23							//专家评审中检报告
	public static final int	S_MID_PASS=24							//中检报告评审通过
	public static final int	S_MID_NG=25								//中检报告评审不通过
	public static final int	S_MID_BK=26								//中检报告退回学院
	public static final int	S_MID_DL=27								//中检报告限期整改
	public static final int	S_END_START=29							//结题开始
	public static final int	S_END_SUBMIT=30							//结题书提交
	public static final int	S_END_CONFIRM=31						//结题书确定评审
	public static final int	S_END_PASS_C=3101						//学院通过结题
	public static final int	S_END_NG_C=3102							//学院不通过结题
	public static final int	S_END_BK_C=3103							//学院退回结题
	public static final int	S_END_EXPERT=32							//安排结题书评审专家
	public static final int	S_END_REVIEW=33							//专家评审结题书
	public static final int	S_END_PASS=34							//结题书评审通过
	public static final int	S_END_NG=35								//结题不通过
	public static final int	S_END_BK=36								//结题退回学院
	public static final int	S_END_DL=37								//结题暂缓通过
	public static final int	STATUS_ACTIVE=10						//在研
	public static final int	STATUS_ENDING=20						//结题
	public static final int	STATUS_EXCEPTION_OK=31					//免结题终止：已弃用
	public static final int	STATUS_EXCEPTION_NG=32					//终止
	public static final int	STATUS_EXCEPTION_PAUSE=33				//中止

	/**
	 * 负责人信息
	 */
	Teacher teacher
	String currentTitle
	String currentDegree
	String specailEmail
	Department department
	String	position
	String	phoneNum	
	/***
	 * 项目信息
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
	String summary			//摘要/关键字
	String mainContent		//主要内容
	String mainGain			//主要成果
	String applicationArea	//应用情况
	String collegeAudit
	String contractAudit	//合同审核意见
	String otherLinks
	boolean hasMid			//已中期
	int delay

	
	/***
	 * 执行状态：在研、结题、结题异常、免结题
	 */
	int	status
	/***
	 * 全流程状态
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
				return 	S_CONFIRM_C											//学院审核通过
			case S_CONFIRM_C:
				return S_CONFIRM
			case S_ANNUAL_SUBMIT:
			case S_ANNUAL_BK:
				return S_ANNUAL_PASS_C										//学院通过年检
			case S_ANNUAL_PASS_C:
			case S_ANNUAL_NG_C:
				return S_ANNUAL_PASS										//学校通过年检
			case S_MID_SUBMIT:
			case S_MID_BK:
				return S_MID_PASS_C											//学院通过中期
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
				return 	S_NG_C													//学院审核不通过
			case S_NG_C:
			case S_CONFIRM_C:
				return S_NG
			case S_ANNUAL_SUBMIT:
			case S_ANNUAL_BK:
				return S_ANNUAL_NG_C										//学院不通过年检
			case S_ANNUAL_PASS_C:
			case S_ANNUAL_NG_C:
				return S_ANNUAL_NG										//学校不通过年检
			case S_MID_SUBMIT:
			case S_MID_BK:
				return S_MID_NG_C											//学院不通过中期
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
				return 	S_BK_C													//学院审核退回
			case S_CONFIRM_C:
			case S_NG_C:
				return S_BK
			case S_ANNUAL_SUBMIT:
			case S_ANNUAL_BK:
				return S_ANNUAL_BK_C										//学院退回年检
			case S_ANNUAL_PASS_C:
			case S_ANNUAL_NG_C:
				return S_ANNUAL_BK											//学校退回年检
			case S_MID_SUBMIT:
			case S_MID_BK:
				return S_MID_BK_C											//学院退回中期
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
