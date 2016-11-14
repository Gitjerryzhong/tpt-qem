package cn.edu.bnuz.tms.card

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.organization.PicturesService
import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Role
/**
 * 本文件为UTF-8
 * @author yanglin
 *
 */
class CardReissueRequestsController {
	SecurityService securityService
	CardReissueRequestsService cardReissueRequestsService
	PicturesService picturesService

	def index() {
		if(securityService.hasRole(Role.ENROLL_ADMIN)) {
			withFormat {
				html {}
				json {
					def status = params.int("status")
					// 当参数中没有offset和max时，表示不分页
					def offset = params.int("offset") ?: 0
					def max = params.int("max") ?: (params.int("offset") ? 20 : Integer.MAX_VALUE)
					def reissueRequests = cardReissueRequestsService.findAllByStatus(status, offset, max)
					def counts = cardReissueRequestsService.getCountsByStatus()
					return render([counts:counts, requests:reissueRequests] as JSON)
				}
			}
		} else {
			withFormat {
				html {response.sendError(404)}
				json {render(status: HttpStatus.FORBIDDEN)}
			}
		}
	}

	def newForm() {
		def studentId =  securityService.userId

		// 本科生
		Student student = Student.findByIdAndEducationLevel(studentId, "本科")
		if(student == null) {
			return warning("tms.card.reissue.warning.notUndergraduate")
		} else if(!student.atSchool) {
			return warning("tms.card.reissue.warning.notAtSchool")
		}

		List<ReissueRequest> requests = cardReissueRequestsService.getAll(studentId)
		// 申请次数限制
		if(requests.size() >= 2) {
			return warning("tms.card.reissue.warning.exceedCount", requests)
		}

		// 如果有未完成的申请，重定向
		if(requests.size() > 0) {
			def reissueRequest = requests.find {it.status != ReissueRequest.STATUS_FINISHED}
			if(reissueRequest) {
				return redirect(uri:"/cardReissueRequests/${reissueRequest.id}")
			}
		}

		// 照片不存在
		def file = picturesService.getFile(studentId)
		if(!file.exists()) {
			return warning("tms.card.reissue.warning.noPicture")
		}

		// 正常申请
		render view:'form', model: [student: Student.get(studentId), requests: requests]
	}

	private warning(String code, requests = null) {
		render view:'warn', model:[message:message(code:code), requests: requests]
	}

	def create() {
		def studentId =  securityService.userId
		def reissueRequest = cardReissueRequestsService.create(studentId, params.reason)
		render(contentType:"text/json") { id = reissueRequest.id }
	}

	def editForm(Long id) {
		def userId =  securityService.userId
		def reissueRequest = cardReissueRequestsService.get(id)
		if(reissueRequest) {
			if(reissueRequest.student.id == userId) {
				render view:'form', model:[
					reissueRequest: reissueRequest,
					student:reissueRequest.student,
				]
			} else {
				response.sendError(404)
			}
		} else {
			response.sendError(404)
		}
	}

	def update(Long id) {
		def userId =  securityService.userId
		def result = cardReissueRequestsService.update(userId, id, params.reason)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}

	def show(Long id) {
		def userId = securityService.userId
		def reissueRequest = cardReissueRequestsService.get(id)
		if(reissueRequest) {
			if(userId == reissueRequest.student.id ||
			securityService.isTeacher()) {
				return [
					card: reissueRequest,
					student: reissueRequest.student,
					editable: userId == reissueRequest.student.id
				]
			} else {
				response.sendError(404)
			}
		} else {
			response.sendError(404)
		}
	}

	def modify(Long id) {
		def userId =  securityService.userId
		def status = params.int('status')
		def result = cardReissueRequestsService.changeStatus(userId, id, status)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}

	def delete(Long id) {
		def result = cardReissueRequestsService.delete(id)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
}
