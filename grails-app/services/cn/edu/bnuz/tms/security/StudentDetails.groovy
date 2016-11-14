package cn.edu.bnuz.tms.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

import cn.edu.bnuz.tms.organization.Student

class StudentDetails extends User {
	private static final long serialVersionUID = 1
	private final String id
	private final String name
	private final String passwordEs
	private final String adminClassId
	private final String departmentId
	
	StudentDetails(Student student, Collection<GrantedAuthority> authorities) {
		super(student.id, student.password, student.enabled, 
			!student.accountExpired, 
			!student.passwordExpired,
			!student.accountLocked, authorities)
		this.id = student.id
		this.name = student.name
		this.passwordEs = student.passwordEs
		this.adminClassId = student.adminClass.id
		this.departmentId = student.adminClass.department.id
	}
	
	String getId() {
		return this.id
	}
	
	String getName() {
		return this.name
	}
	
	String getPasswordEs() {
		return this.passwordEs
	}
	
	String getAdminClassId() {
		return this.adminClassId
	}
	
	String getDepartmentId() {
		return this.departmentId
	}
}
