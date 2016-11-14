package cn.edu.bnuz.tms.rollcall

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.rollcall.CancelExamService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.TermService

class CancelExamController {
	SecurityService securityService
	TermService termService
	CancelExamService cancelExamService
	def index() {
		request.withFormat {
			html { }
			json {
				def departmentId = securityService.departmentId
				def term = termService.currentTerm
				def status = params.int('status')
				def students
				if(status == 1) { // 1 or 2
					students = cancelExamService.getCancelExamCandidates(departmentId, term)
				} else {
					students = cancelExamService.getCancelExams(departmentId, term)
				}
				render students as JSON
			}
		}
	}

	def show() {
		def studentId =  params.id
		def courseId = params.course
		def term = termService.currentTerm
		def rollcallItems = cancelExamService.getRollcallItems(studentId, courseId, term)
		render rollcallItems as JSON
	}

	def modify() {
		def teacherId = securityService.userId
		def studentId = params.id
		def courseId = params.course
		def status = params.int('status')
		def term = termService.currentTerm
		if(status == 1) {
			cancelExamService.cancelExam(teacherId, studentId, courseId, term)
		} else {
			cancelExamService.revokeExam(teacherId, studentId, courseId, term)
		}
		render status: HttpStatus.OK
	}
}
