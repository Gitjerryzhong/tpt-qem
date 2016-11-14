package cn.edu.bnuz.tms.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher


class TeacherDetails extends User {
	private static final long serialVersionUID = 1
	private final String id
	private final String name
	private final String departmentId
	private final String passwordEs

	TeacherDetails(Teacher teacher, Collection<GrantedAuthority> authorities) {
		super(teacher.id, teacher.password, teacher.enabled, 
			!teacher.accountExpired, 
			!teacher.passwordExpired,
			!teacher.accountLocked, authorities)
		this.id = teacher.id
		this.name = teacher.name
		this.departmentId = teacher.department.id
		this.passwordEs = teacher.passwordEs
	}
	
	String getId() {
		return this.id
	}
	
	String getName() {
		return this.name
	}
	
	String getDepartmentId() {
		return this.departmentId
	}
	
	String getPasswordEs() {
		return this.passwordEs
	}
}
