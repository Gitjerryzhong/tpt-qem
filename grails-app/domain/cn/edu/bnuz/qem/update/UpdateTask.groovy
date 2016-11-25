package cn.edu.bnuz.qem.update

import java.util.Date;

import cn.edu.bnuz.qem.project.QemStage;
import cn.edu.bnuz.qem.project.QemType;
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.organization.Teacher;

class UpdateTask {
	public static final int	F_PERSON=0									//负责人
	public static final int	F_COLLEGE=1									//学院
	public static final int	F_UNIVERSITY=2								//学校
	public static final int	AU_NONE=0									//未审
	public static final int	AU_PASS=1									//通过
	public static final int	AU_NG=2										//不通过
	public static final int	AU_BG=3										//退回

    /**
	 * 负责人信息
	 */
	String teacherId
	String currentTitle
	String currentDegree
	String specailEmail
	String departmentId
	String	position
	String	phoneNum	
	/***
	 * 项目信息
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
	//申请信息
	String userId
	String userName
	Date commitDate	
	/**
	 * 变更内容
	 * 1：变更项目负责人
	 * 2：延期
	 * 3：变更项目名称
	 * 4：研究内容重大调整
	 * 5：自行中止项目
	 * 6：改变成果形式
	 * 7：变更参与人
	 * 8：其他
	 */
	String	updateTypes		//已变更内容id记录
	String memo				//申请理由
	
	int flow				//流程状态 0：负责人，1：学院，2：学校
	int auditStatus				//审核状态 0：未审，1：通过：，2：不通过，3：退回
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
