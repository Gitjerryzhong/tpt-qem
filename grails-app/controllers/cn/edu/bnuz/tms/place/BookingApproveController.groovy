package cn.edu.bnuz.tms.place

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.place.BookingApproveCommand;
import cn.edu.bnuz.tms.place.BookingApproveService;
import cn.edu.bnuz.tms.place.BookingsService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Role
import cn.edu.bnuz.tms.teaching.TermService
import cn.edu.bnuz.tms.place.BookingForm;

class BookingApproveController {
	BookingApproveService bookingApproveService
	BookingsService bookingsService
	SecurityService securityService
	TermService termService
	def index() {
		withFormat {
			html {}
			json {
				def teacherId = securityService.userId
				def status = params.int("status")
				// 当参数中没有offset和max时，表示不分页
				def offset = params.int("offset") ?: 0
				def max = params.int("max") ?: (params.int("offset") ? 20 : Integer.MAX_VALUE)
				def statis = bookingApproveService.getStatis(teacherId)
				def forms
				if(status == 0) {
					forms = bookingApproveService.findPendingForms(offset, max)
				} else if(status == 1) {
					forms = bookingApproveService.getProcessedForms(teacherId, offset, max)
				} else if(status == 2) {
					statis[2] = bookingApproveService.getSearchCount(teacherId, params.query)
					forms = bookingApproveService.searchProcessedForms(teacherId, offset, max, params.query)
				} else if(status == -1) {
					forms = bookingApproveService.findUncheckedForms(offset, max)
			    } else {
					return render(status: HttpStatus.FORBIDDEN)
				}
				return render([forms:forms, statis: statis] as JSON)
			}
		}
	}

	def show(Long id) {
		def teacherId = securityService.userId
		def departmentId = securityService.departmentId
		def form = bookingsService.getInfo(id)
		if(form == null) {
			return render(status: HttpStatus.NOT_FOUND)
		}
		form.userInfo = bookingsService.getUserExtraInfo(teacherId, departmentId, form.userId)
		def statis = bookingApproveService.getStatis(teacherId)
		def status = params.int("status")
		if(status == 0) { // 待审批
			form.nextId = bookingApproveService.nextPending(id)
			form.prevId = bookingApproveService.prevPending(id)
			if(form.status == BookingForm.STATUS_CHECKED) {
				form.checkable = true
				form.closeable = true
				bookingApproveService.checkConflict(termService.currentTerm, form)
			}
			def term = termService.currentTerm
			return render([
				form: form,
				statis: statis,
				term: [
					termId: term.id, 
					startDate: term.startDate, 
					today: new Date().format("yyyy-MM-dd")
				],
			] as JSON)
		} else if(status == 1) { // 已审批
			form.nextId = bookingApproveService.nextProcessed(teacherId, id)
			form.prevId = bookingApproveService.prevProcessed(teacherId, id)
			if(form.status == BookingForm.STATUS_APPROVED) {
				form.revokable = true
			}
			return render([
				form: form,
				statis: statis,
			] as JSON)
		} else if(status == -1) { // 待审核
			form.nextId = bookingApproveService.nextUnchecked(id)
			form.prevId = bookingApproveService.prevUnchecked(id)
			if(form.status == BookingForm.STATUS_APPLYING) {
				form.closeable = true
				bookingApproveService.checkConflict(termService.currentTerm, form)
			}
			return render([
				form: form,
				statis: statis,
			] as JSON)
		} else if(status == 2) { // 查找已审批
			form.nextId = bookingApproveService.nextSearch(teacherId, id, params.query)
			form.prevId = bookingApproveService.prevSearch(teacherId, id, params.query)
			if(form.status == BookingForm.STATUS_APPROVED) {
				form.revokable = true
			}
			return render([
				form: form,
				statis: statis,
			] as JSON)
		} else if(status == 3) { // Search by ID
			form.nextId = null
			form.prevId = null
			if(form.status == BookingForm.STATUS_APPROVED) {
				form.revokable = true
			}
			return render([
				form: form,
				statis: statis,
			] as JSON)
		} else {
			return render(status: HttpStatus.FORBIDDEN)
		}
	}

	def modify(Long id) {
		def userId = securityService.userId
		def userName = securityService.userName
		def bac = new BookingApproveCommand(request.JSON)
		def result = bookingApproveService.changeStatus(userId, userName, id, bac)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
}
