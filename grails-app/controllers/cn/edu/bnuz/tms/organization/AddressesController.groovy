package cn.edu.bnuz.tms.organization

import grails.converters.JSON
import cn.edu.bnuz.tms.organization.AddressesService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Role

class AddressesController {
	AddressesService addressesService
	SecurityService securityService
	def index() {
		if(securityService.isStudent()) {
			def studentId = securityService.userId;
			def students = addressesService.getStudents(studentId)
			render model:[students:students, studentId:studentId], view:'student'
		} else {
			def teacherId = securityService.userId
			def teachers = addressesService.getTeachers(teacherId)
			render model:[teachers:teachers, teacherId:teacherId], view:'teacher'
		}
	}

	def adminClasses() {
		def adminClasses
		if(securityService.hasRole(Role.STUDENT_AFFAIRS) ||
		   securityService.hasRole(Role.ROLLCALL_ADMIN)) {
			adminClasses = addressesService.getAdminClassesByDepartment(securityService.departmentId)
		} else {
			adminClasses = addressesService.getAdminClassesByTeacher(securityService.userId)
		}
		return [adminClasses: adminClasses as JSON]
	}

	def adminClass(String id) {
		def students = addressesService.getAdminClassStudents(id);
		render students as JSON
	}
}
