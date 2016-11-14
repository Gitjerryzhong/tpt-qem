package cn.edu.bnuz.tms.schedule

import grails.converters.JSON
import grails.web.JSONBuilder
import cn.edu.bnuz.tms.rollcall.RollcallStatisService
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.ArrangementService
import cn.edu.bnuz.tms.teaching.FreeArrangementService
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.teaching.TermService

class ScheduleController {
	ArrangementService arrangementService
	FreeArrangementService freeArrangementService
	TermService termService
	SecurityService securityService
	RollcallStatisService rollcallStatisService
	def teacher() {
		def teacherId = securityService.userId
		def term = termService.currentTerm
		def arrangements = arrangementService.getTeacherArrangements(teacherId, term)
		def forms = rollcallStatisService.teacher(teacherId, term)
		return [
			term: term,
			arrangements: arrangements as JSON,
			forms: forms as JSON
		]
	}
	
	def student() {
		def studentId =  securityService.userId
		def term = termService.currentTerm
		def arrangements = arrangementService.getStudentArrangements(studentId, term)
		def freeArrangements = freeArrangementService.getStudentFreeArrangements(studentId, term)
		return [
			term: term,
			arrangements: arrangements as JSON,
			freeArrangements: freeArrangements as JSON
		]
	}
	
	private JSON toJSON(Term term) {
		return new JSONBuilder().build {
			startWeek = term.startWeek
			endWeek = term.endWeek
			currentWeek = term.currentWeek
		}
	}
}