package cn.edu.bnuz.tms.organization

import cn.edu.bnuz.tms.teaching.Major

class AdminClass {
	String id
	String name
	Teacher adminTeacher
	Teacher gradeTeacher
	Major major
	SortedSet<Student> students
	
	@Override
	String toString() {
		"AdminClass[$name]"
	}
	
	static belongsTo = [department : Department]
	
	static hasMany = [
		students : Student
	]
	
	static mapping = {
		table 			'org_admin_class'
		id 				generator:'assigned', length: 8
		name			length:50
		adminTeacher 	column:'head_teacher_id'
		gradeTeacher 	column:'counselor_id'
	}
	
	static constraints = {
		name 			blank:false
		adminTeacher	nullable:true
		gradeTeacher	nullable:true
	}
}
