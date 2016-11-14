package cn.edu.bnuz.tms.rollcall

import java.text.SimpleDateFormat

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.rollcall.LeaveApprocalService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.TermService
import cn.edu.bnuz.tms.rollcall.LeaveItem;
import cn.edu.bnuz.tms.rollcall.LeaveRequest;

class LeaveApprovalsController {
	SecurityService securityService
	TermService termService
	LeaveApprocalService leaveApprocalService

	def index() {
		request.withFormat {
			html { }
			json {
				def teacherId =  securityService.userId
				def term = termService.currentTerm
				def status = params.int('status')
				def leaveRequests = leaveApprocalService.getRequestsByStatus(teacherId, term, status)
				render(contentType:"text/json") {
					array {
						for(LeaveRequest leaveRequest in leaveRequests) {
							if(status == 1) {
								item (id: leaveRequest.id,
								studentId: leaveRequest.student.id,
								studentName: leaveRequest.student.name,
								adminClass: leaveRequest.student.adminClass.name,
								type: message(code: "tms.leave.type.${leaveRequest.type}"),
								dateModified:leaveRequest.dateModified.format("yyyy-MM-dd HH:mm"),
								)
							} else {
								item (id: leaveRequest.id,
								studentId: leaveRequest.student.id,
								studentName: leaveRequest.student.name,
								adminClass: leaveRequest.student.adminClass.name,
								type: message(code: "tms.leave.type.${leaveRequest.type}"),
								dateModified: leaveRequest.dateModified.format("yyyy-MM-dd HH:mm"),
								dateApproved: leaveRequest.dateApproved.format("yyyy-MM-dd HH:mm"),
								status:leaveRequest.status,
								)
							}
						}
					}
				}
			}
		}
	}

	def show() {
		def teacherId =  securityService.userId
		def leaveRequest = leaveApprocalService.get(teacherId, params.long("id"))
		if(leaveRequest != null) {
			def leaveItems = leaveRequest.items.toList().sort();
			render(contentType:"text/json") {
				id = leaveRequest.id
				reason = leaveRequest.reason
				items = array {
					for(LeaveItem leaveItem in leaveItems) {
						item(
								type: leaveItem.dayOfWeek == null ? "day" : leaveItem.arrangement == null ? "arr" : "week",
								text: formatItem(leaveItem)
								)
					}
				}
			}
		} else {
			render status:HttpStatus.NOT_FOUND
		}
	}

	private String formatItem(LeaveItem item) {
		StringBuilder sb = new StringBuilder()
		sb.append(message(code: "tms.date.week", args:[item.week]))
		if(item.dayOfWeek != null) {
			sb.append(" ")
			sb.append(message(code: "tms.date.dayOfWeek.${item.dayOfWeek}"))
		} else if(item.arrangement != null) {
			sb.append(" ")
			sb.append(message(code: "tms.date.dayOfWeek.${item.arrangement.dayOfWeek}"))
			//sb.append(message(code: "tms.arrangement.oddEven.${item.arrangement.oddEven}", default:''))
			sb.append(" ")
			sb.append(message(code: "tms.arrangement.sections", args:[
				item.arrangement.startSection,
				item.arrangement.endSection
			]))
			// TODO: only student's classname
			sb.append(" ${item.arrangement.courseClasses.toList()[0].name}")
		}
		return sb.toString()
	}

	def modify() {
		def userId =  securityService.userId
		def requestId = params.long('id')
		def status = params.int('status')
		def result = leaveApprocalService.changeStatus(userId, requestId, status)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
}
