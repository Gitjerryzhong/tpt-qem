package cn.edu.bnuz.tms.graduate

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.graduate.InternshipTeacherService;
import cn.edu.bnuz.tms.organization.DepartmentService
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.TermService

class InternshipTeachersController {
	SecurityService securityService
	TermService termService
	DepartmentService departmentService
	InternshipTeacherService internshipTeacherService
	def index() {
		def term = termService.currentTerm
		def departmentId = securityService.departmentId
		def internships = internshipTeacherService.getTeachers(departmentId)
		def teachers = departmentService.getTeachers(departmentId)
		render model: [internships: internships as JSON, teachers: teachers as JSON],
		       view: '/internships/teachers'
	}
	
	def create() {
		def internshipId = params.long("internshipId")
		def teacherIds = params.teacherIds
		def results = internshipTeacherService.create(internshipId, teacherIds)
		if(results) {
			render results as JSON
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def delete() {
		def result = internshipTeacherService.delete(params.long('id'))
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
}
