package cn.edu.bnuz.tms.system

import cn.edu.bnuz.tms.organization.Teacher

class TeacherSetting {
	String name
	String value
	
	static belongsTo = [teacher:Teacher]
	
	static mapping = {
		table 	'sys_teacher_setting'
	}
}
