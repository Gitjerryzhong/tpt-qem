package cn.edu.bnuz.tms.material

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Course
import cn.edu.bnuz.tms.teaching.CourseClass

/**
 * 教学材料提交情况
 * @author 杨林
 * @since 0.4
 */
class TeachingMaterial {
	/**
	 * 学期材料
	 */
	TermMaterial termMaterial
	
	/**
	 * 提交人
	 */
	Teacher	teacher
	
	/**
	 * 课程班
	 */
	CourseClass courseClass
	
	/**
	 * 课程
	 */
	Course course
	
	/**
	 * 检查人
	 */
	Teacher checker
	
	/**
	 * 确认时间
	 */
	Date comfirmDate
	
	/**
	 * 状态。1：提交，2：免交
	 */
	int status
	
	static mapping = {
		table 		'edu_teaching_material'
	}
	
	static constraints = {
		courseClass		nullable:true
		course			nullable:true
		checker 		nullable:true
		comfirmDate		nullable:true
	}
}
