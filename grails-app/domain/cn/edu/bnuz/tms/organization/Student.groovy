package cn.edu.bnuz.tms.organization

import cn.edu.bnuz.tms.teaching.Major
import org.joda.time.LocalDate

class Student implements Comparable<Student>{
	public static final int ID_LEN = 10
	
	String id
	String password
	String passwordEs
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	String name
	String email
	String longPhone
	String shortPhone
	String sex
	int status
	Major major
	AdminClass adminClass
	String majorDirection
	int grade
	boolean atSchool 		// 是否在校
	boolean	isEnrolled 		// 是否有学籍
	String province 		// 来源省
	LocalDate birthday 		// 出生日期
	String educationLevel 	// 层次
	int lengthOfSchooling 	// 学制
	@Override
	String toString() {
		"Student[$id, name:$name]"	
	}
	
	@Override
	public int compareTo(Student o) {
		this.id <=> o.id
	}
	
	static belongsTo = [
		Major,
		AdminClass
	]
	
	static mapping = {
		table 				'org_student'
		id 					generator: 'assigned', length: 10
		enabled 			defaultValue: true
		accountExpired 		defaultValue: false
		accountLocked 		defaultValue: false
		passwordExpired 	defaultValue: false
		password			length: 50
		passwordEs			length: 50
		sex					length: 4
		name 				length: 50
		email				length: 50
		longPhone 			length: 11
		shortPhone 			length: 6
		status 				default: 0
		majorDirection		length: 50
		province			length: 20
		educationLevel		length: 10
	}
	
	static constraints = {
		name 			blank: false
		password		nullable: true
		sex 			nullable: true
		email 			nullable: true, email:true, unique:true
		longPhone 		nullable: true
		shortPhone 		nullable: true
		majorDirection  nullable: true
		grade  			nullable: true
		birthday		nullable: true
		province		nullable: true
		educationLevel	nullable: true
	}
}
