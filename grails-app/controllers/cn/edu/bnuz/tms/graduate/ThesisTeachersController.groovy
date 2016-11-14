package cn.edu.bnuz.tms.graduate

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.graduate.ThesisTeacherService;
import cn.edu.bnuz.tms.organization.DepartmentService
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.TermService

class ThesisTeachersController {
	SecurityService securityService
	TermService termService
	DepartmentService departmentService
	ThesisTeacherService thesisTeacherService
	def index() {
		def term = termService.currentTerm
		def departmentId = securityService.departmentId
		def theses = thesisTeacherService.getTeachers(departmentId)
		def teachers = departmentService.getTeachers(departmentId)
		render model: [theses: theses as JSON, teachers: teachers as JSON],
		       view: '/theses/teachers'
	}
	
	def create() {
		def thesisId = params.long("thesisId")
		def teacherIds = params.teacherIds
		def results = thesisTeacherService.create(thesisId, teacherIds)
		if(results) {
			render results as JSON
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def delete() {
		def result = thesisTeacherService.delete(params.long('id'))
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
}
