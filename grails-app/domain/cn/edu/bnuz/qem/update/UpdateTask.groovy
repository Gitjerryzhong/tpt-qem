package cn.edu.bnuz.qem.update

import java.util.Date;

import cn.edu.bnuz.qem.project.QemStage;
import cn.edu.bnuz.qem.project.QemType;
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.organization.Teacher;

class UpdateTask {

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
	//申请信息
	String userId
	String userName
	Date commitDate	
	int	updateType			//变更内容1：变更项目负责人，2：延期，3：变更项目名称，4：研究内容重大调整，5：自行中止项目，6改变成果形式，7：其他
	String	origStyle		//原成果形式
	String memo				//申请理由
	String checker			//审核人
	int status				//审核状态 0：未审，1：通过，2不通过，3：退回
	Date checkDate			//审批日期
	String audit			//审批意见
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
