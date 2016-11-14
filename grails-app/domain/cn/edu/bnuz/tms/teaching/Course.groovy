package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Department


class Course {
	String id
	String name
	int credit
	String weekHours
	
	/**
	 * 课程类别:选修课/必修课/实践环节
	 */
	
	String type
	/**
	 * 课程性质:
	 *    公共必修课/公共选修课/公共选修课1/强化英语
	 *         学科基础课/专业主干课/专业方向课/个性课程/实践教学
	 *         专业必修课/专业选修课/专业实践课
	 *         双学位课/辅修课
	 */
	String property
	
	@Override
	String toString() {
		"Course[$id, name:$name]"
	}
	
	static belongsTo = [department : Department]
	
	static mapping = {
		table 		'edu_course'
		id 			generator:'assigned', length:8
		type 		length:20
		property	length:20
		weekHours	length:9
	}
	
	static constraints = {
		name 			nullable:false, blank:false
		credit 			nullable:true
		type 			nullable:true
		property		nullable:true
		weekHours 		nullable:true
	}
}
