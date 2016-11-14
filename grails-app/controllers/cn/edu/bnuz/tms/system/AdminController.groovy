package cn.edu.bnuz.tms.system

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.organization.DepartmentService
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Role;

class AdminController {
	DepartmentService departmentService
	SecurityService securityService

	/**
	 * 部门列表
	 */
	def departments() {
		if(securityService.hasRole(Role.SYSTEM_ADMIN)) {
			def departments = departmentService.getDepartmentAdmins();
			return [departments:departments]
		} else {
			render status:HttpStatus.UNAUTHORIZED
		}
	}

	/**
	 * 设置部门管理员	
	 */
	def departmentAdmin() {
		if(securityService.hasRole(Role.SYSTEM_ADMIN)) {
			String departmentId = params.dept
			String adminId = params.admin
			departmentService.setDepartmentAdmin(departmentId, adminId)
			render status:HttpStatus.OK
		} else {
			render status:HttpStatus.UNAUTHORIZED
		}
	}

	/**
	 * 获取学院教师。	
	 * @param id 学院ID，如果ID为空，则取当前用户所在学院ID
	 */
	def departmentTeachers(String id) {
		def departmentId = id ?: securityService.departmentId
		def teachers = departmentService.getTeachers(departmentId)
		render teachers as JSON
	}

	/**
	 * 教师角色列表	
	 */
	def teacherRoles() {
		def departmentId = securityService.departmentId
		def teacherRoles = departmentService.getTeacherRoles(departmentId)
		return [teacherRoles:teacherRoles,departmentId:departmentId]
	}

	/**
	 * 设置教师角色
	 */
	def teacherRole() {
		if(securityService.hasRole(Role.DEPARTMENT_ADMIN)) {
			def departmentId = securityService.departmentId
			def teacherId = params.teacher
			def role = params.role
			departmentService.setTeacherRole(departmentId, teacherId, role)
			render status:HttpStatus.OK
		} else {
			render status:HttpStatus.UNAUTHORIZED
		}
	}

	/**
	 * 行政班列表
	 */
	def adminClasses() {
		def departmentId = securityService.departmentId
		def adminClasses = departmentService.getAdminClasses(departmentId)
		return [adminClasses:adminClasses]
	}

	/**
	 * 设置班主任或辅导员
	 */
	def adminClass() {
		if(securityService.hasRole(Role.DEPARTMENT_ADMIN)) {
			def adminClass = params.adminClass
			def teacherId = params.teacher
			def type = params.type
			if(type == 'admin') {
				departmentService.setAdminTeacher(adminClass, teacherId)
				render status:HttpStatus.OK
			} else if(type == 'grade') {
				departmentService.setGradeTeacher(adminClass, teacherId)
				render status:HttpStatus.OK
			} else {
				render status:HttpStatus.BAD_REQUEST
			}
		} else {
			render status:HttpStatus.UNAUTHORIZED
		}
	}
}
