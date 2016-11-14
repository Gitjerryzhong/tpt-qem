package cn.edu.bnuz.tms.organization

import cn.edu.bnuz.tms.system.TeacherRole
import cn.edu.bnuz.tms.system.TeacherSetting

class Teacher implements Comparable<Teacher> {
	public static final int ID_LEN = 5
	
	String id
	String password
	String passwordEs
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	String name
	String sex
	Date birthday
	String email
	String longPhone
	String shortPhone
	String officeAddress
	String officePhone
	String homeAddress
	String homePhone
	String qqNumber
	Department department
	/**
	 * 是否是外聘教师
	 */
	boolean external
	
	@Override
	public String toString() {
		"Teacher[$id, name:$name]"
	}
	
	@Override
	public int compareTo(Teacher o) {
		this.id <=> o.id
	}
	
	static belongsTo = [Department]
	static hasMany = [
		settings : TeacherSetting,
		roles: TeacherRole
	]
	
	static mapping = {
		table 				'org_teacher'
		id 					generator: 'assigned', length: 5
		enabled 			defaultValue: true
		accountExpired 		defaultValue: false
		accountLocked 		defaultValue: false
		passwordExpired 	defaultValue: false
		name 				length: 30
		password			length: 50
		passwordEs			length: 50
		sex 				length: 4
		birthday 			type:'date'
		email				length: 50
		longPhone 			length: 11
		shortPhone 			length: 6
		officeAddress 		length: 50
		officePhone 		length: 11
		homeAddress 		length: 50
		homePhone 			length: 12
		qqNumber			length: 11
	}
	
	static constraints = {
		name 			nullable: false, blank: false
		password		nullable: true
		sex 			nullable: true
		birthday 		nullable: true, type: 'date'
		email 			nullable: true, email: true, unique: true
		longPhone 		nullable: true
		shortPhone 		nullable: true
		officeAddress 	nullable: true
		officePhone 	nullable: true
		homeAddress 	nullable: true
		homePhone 		nullable: true
		qqNumber		nullable: true
	}
	
	boolean isGradeTeacher() {
		return AdminClass.countByGradeTeacher(this)	> 0
	}
	
	boolean isAdminClassTeacher() {
		return AdminClass.countByAdminTeacher(this) > 0
	}
	
	boolean isCollegeTeacher() {
		return department.hasStudents
	}
}
