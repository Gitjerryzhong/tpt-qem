package cn.edu.bnuz.tms.organization

import cn.edu.bnuz.tms.teaching.Course
import cn.edu.bnuz.tms.teaching.CourseClass
import cn.edu.bnuz.tms.teaching.Major;

class Department {
	String id
	String name
	boolean hasStudents
	boolean isAdminDept
	boolean enabled
	String shortName
	
	@Override
	String toString() {
		"Department[$name]"
	}
		
	static hasMany = [
		teachers : Teacher,
		majors : Major,
		courses : Course,
		adminClasses : AdminClass,
		courseClasses : CourseClass
	]
	
	static mapping = {
		table 	'org_department'
		id 		generator:'assigned', length:2
		name	length:50
		shortName	length:10,nullable:true
		cache   true
	}
}
