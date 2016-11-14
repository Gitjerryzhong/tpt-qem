package cn.edu.bnuz.tms.rollcall

import grails.converters.JSON
import grails.web.JSONBuilder

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.Arrangement
import cn.edu.bnuz.tms.teaching.ArrangementService
import cn.edu.bnuz.tms.teaching.FreeArrangementService
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.teaching.TermService

/**
 * 学生请假条
 * @author 杨林
 * @since 0.1
 */
class LeavesController {
	ArrangementService arrangementService
	FreeArrangementService freeArrangementService
	SecurityService securityService
	TermService termService
	LeaveService leaveService
	
	def index() {
		def studentId =  securityService.userId
		def term = termService.currentTerm
		return [leaveRequests:leaveService.findAllLeaveRequests(studentId, term)]
	}

	def newForm() {
		def studentId =  securityService.userId
		def term = termService.currentTerm
		List<Arrangement> arrangements = arrangementService.getStudentArrangements(studentId, term)
		List<String> freeArrangements = freeArrangementService.getStudentFreeArrangements(studentId, term)
		List<LeaveItem> existedItems = leaveService.findAllLeaveItems(studentId, term)
		render(view:'form', model:[
			term: toJSON(term),
			arrangements: arrangements as JSON,
			freeArrangements : freeArrangements as JSON,
			leaveRequest: 'null',
			existedItems: leaveItemsToJSON(existedItems)
		])
	}

	def create() {
		def studentId =  securityService.userId
		LeaveRequestCommand lrc = new LeaveRequestCommand(request.JSON)
		LeaveRequest leaveRequest = leaveService.create(studentId, lrc)
		render(contentType:"text/json") { id = leaveRequest.id }
	}

	def editForm() {
		def studentId =  securityService.userId
		def term = termService.currentTerm
		List<Arrangement> arrangements = arrangementService.getStudentArrangements(studentId, term)
		def requestId = new Long(params.id)
		def leaveRequest = leaveService.get(studentId, requestId)
		List<LeaveItem> existedItems = leaveService.findAllLeaveItems(studentId, term, requestId)
		if(leaveRequest) {
			render(view:'form', model:[
				term:         toJSON(term),
				arrangements: arrangements as JSON,
				reason:       leaveRequest.reason,
				type:         leaveRequest.type,
				leaveRequest: toJSON(leaveRequest),
				existedItems: leaveItemsToJSON(existedItems)
			])
		} else {
			response.sendError(404)
		}
	}

	def update() {
		def studentId =  securityService.userId
		LeaveRequestCommand lrc = new LeaveRequestCommand(request.JSON)
		def result = leaveService.update(studentId, lrc)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def delete() {
		def studentId =  securityService.userId
		def result = leaveService.delete(params.long("id"))
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}

	def show() {
		def user = securityService.principal
		def userId =  user.id

		def requestId = new Long(params.id)
		def leaveRequest = leaveService.get(requestId)
		if(leaveRequest && (userId == leaveRequest.student.id || securityService.isTeacher())) {
			return [
				requestId: requestId,
				type: message(code: "tms.leave.type.${leaveRequest.type}"),
				applicant: leaveRequest.student.name,
				dateApplied: leaveRequest.dateCreated,
				lastChanged: leaveRequest.dateModified,
				approver: leaveRequest.approver?.name,
				dateApproved: leaveRequest.dateApproved,
				reason: leaveRequest.reason,
				items: leaveRequest.items.toList().sort(),
				status: leaveRequest.status,
				statusName: message(code: "tms.leave.status.${leaveRequest.status}"),
				editable: userId == leaveRequest.student.id
			]
		} else {
			response.sendError(404)
		}
	}
	
	def modify() {
		def userId =  securityService.userId
		def requestId = params.long('id')
		def status = params.int('status')
		def result = leaveService.changeStatus(userId, requestId, status)
		if(result) {
			chain action:params.id
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	private JSON toJSON(Term term) {
		return new JSONBuilder().build {
			id = term.id
			startWeek = term.startWeek
			endWeek = term.endWeek
			currentWeek = term.currentWeek
		}
	}

	private JSON toJSON(LeaveRequest leaveRequest) {
		return new JSONBuilder().build {
			id = leaveRequest.id
			type = leaveRequest.type
			items = array {
				for(LeaveItem leaveItem in leaveRequest.items) {
					if(leaveItem.arrangement != null) {
						item(id: leaveItem.id, week: leaveItem.week, arr: leaveItem.arrangement.id)
					} else if(leaveItem.dayOfWeek != null) {
						item(id: leaveItem.id, week: leaveItem.week, day: leaveItem.dayOfWeek)
					} else {
						item(id: leaveItem.id, week: leaveItem.week)
					}
				}
			}
		}
	}
	
	private JSON leaveItemsToJSON(List<LeaveItem> leaveItems) {
		return new JSONBuilder().build {
			array {
				for(LeaveItem leaveItem in leaveItems) {
					if(leaveItem.arrangement != null) {
						item(id: leaveItem.id, week: leaveItem.week, arr: leaveItem.arrangement.id)
					} else if(leaveItem.dayOfWeek != null) {
						item(id: leaveItem.id, week: leaveItem.week, day: leaveItem.dayOfWeek)
					} else {
						item(id: leaveItem.id, week: leaveItem.week)
					}
				}
			}
		}
	}
}
