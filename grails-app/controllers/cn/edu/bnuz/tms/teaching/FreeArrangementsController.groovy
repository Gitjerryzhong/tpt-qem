package cn.edu.bnuz.tms.teaching

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.ArrangementService;
import cn.edu.bnuz.tms.teaching.FreeArrangementService;
import cn.edu.bnuz.tms.teaching.TermService;

class FreeArrangementsController {
	TermService termService
	SecurityService securityService
	FreeArrangementService freeArrangementService
	ArrangementService arrangementService
	def index() {
		def term = termService.currentTerm
		def teacherId = securityService.userId
		def arrangements = arrangementService.getTeacherArrangements(teacherId, term)
		def students = freeArrangementService.getStudents(teacherId, term)
		return [arrangements: arrangements as JSON, students: students as JSON]
	}
	
	def newForm() {
		def term = termService.currentTerm
		def teacherId = securityService.userId
		def arrangements = arrangementService.getTeacherArrangements(teacherId, term)
		return [
			arrangements: arrangements as JSON,
			studentId: params.studentId ?: ""
		]
	}
	
	def student() {
		def term = termService.currentTerm
		def teacherId = securityService.userId
		def studentId = params.id
		def studentInfo = freeArrangementService.getStudentInfo(studentId)
		
		if(studentInfo) {
			def arrangements = arrangementService.getStudentArrangements(studentId, term)
			def freeArrangements = freeArrangementService.getStudentFreeArrangementsByTeacher(studentId, teacherId, term)
			def student = studentInfo[0]
			student.arrangements = arrangements
			student.freeArrangements = freeArrangements
			render student as JSON
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	
	def create() {
		freeArrangementService.insertFreeArrangement(
			request.JSON.studentId,
			request.JSON.arrangementId
		)
		render status: HttpStatus.OK
	}
	
	def delete(String id) {
		freeArrangementService.deleteFreeArrangement(
			id, request.JSON.arrangement
		)
		render status: HttpStatus.OK
	}
}
