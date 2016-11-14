package cn.edu.bnuz.qem

import cn.edu.bnuz.qem.project.QemTask;
import cn.edu.bnuz.qem.project.QemType;
import cn.edu.bnuz.qem.review.Review;
import cn.edu.bnuz.tms.organization.Department;

class ProjectCommand {
	int id
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
	String departmentId
	String qemTypeId

	String projectName
	String expectedGain	
	String projectLevel
	String commit
	String position
	String majorId
	String phoneNum
	String collegeStatus
	String status
	String otherLinks
	Long noticeId
}
