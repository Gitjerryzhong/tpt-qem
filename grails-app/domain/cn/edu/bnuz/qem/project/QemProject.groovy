package cn.edu.bnuz.qem.project

import cn.edu.bnuz.qem.review.Review
import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher;
import cn.edu.bnuz.tms.teaching.Major

class QemProject {
	/**
	 * 提交
	 */
	public static final int ACTION_SUBMIT = 1
	/**
	 * 撤销
	 */
	public static final int ACTION_CANCEL = 2
	/**
	 * 学院审核
	 */
	public static final int ACTION_COLLEGE_AUDIT = 3
	/**
	 * 学院审核撤销
	 */
	public static final int ACTION_COLLEGE_CANCEL = 4
	/**
	 * 校级
	 */
	public static final int UNIVER_LEVEL = 1
	/**
	 * 省级
	 */
	public static final int PROVINCE_LEVEL = 2
	/**
	 * 国家级
	 */
	public static final int NATIONAL_LEVEL = 3
	
	/**
	 * 负责人信息
	 */
	Teacher teacher	
	String currentTitle	
	String currentDegree	
	String specailEmail
	/**
	 * 学科
	 */
	String discipline	
	/**
	 * 方向
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
	 * 未审核
	 */
	public static final int STATUS_APPLYING = 0
	/**
	 * 审核通过
	 */
	public static final int STATUS_CHECKED = 1		
	/**
	 * 审核不通过
	 */
	public static final int STATUS_REJECTED = 2
	/**
	 * 已关闭
	 */
	public static final int STATUS_CLOSED = 6
	/**
	 * 学院审核意见
	 */
	int collegeStatus
	String collegeAudit
	boolean specialEdit
	int projectLevel
	Date commitDate
	Notice notice
	String groupId
	String bn
	String otherLinks	//相关网址，多个网址以分号隔开
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
				return !isSubmit || collegeStatus==2									//未提交或者学院退回
			case ACTION_CANCEL: 			
			case ACTION_COLLEGE_AUDIT: 
				return isSubmit && collegeStatus==0					//已提交但学院未审批				
			case ACTION_COLLEGE_CANCEL:
				return collegeStatus>0 && review.status==Review.STATUS_NEW //学院已审批，但还未开始专家评审			
			default:
				return false
		}
	}
}
