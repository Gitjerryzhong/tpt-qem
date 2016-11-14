package cn.edu.bnuz.tms.place

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.place.BookingCheckService;
import cn.edu.bnuz.tms.place.BookingsService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Role
import cn.edu.bnuz.tms.place.BookingForm;

class BookingCheckController {
	BookingCheckService bookingCheckService
	BookingsService bookingsService
	SecurityService securityService
	def index() {
		if(!securityService.currentUser.officePhone) {
			return render(view:"fill")
		}
		withFormat {
			html {}
			json {
				def teacherId = securityService.userId
				def status = params.int("status")
				// 当参数中没有offset和max时，表示不分页
				def offset = params.int("offset") ?: 0
				def max = params.int("max") ?: (params.int("offset") ? 20 : Integer.MAX_VALUE)
				def forms, statis
				if(status == 0) {
					forms = bookingCheckService.findPendingForms(teacherId, offset, max)
					statis = bookingCheckService.getStatis(teacherId)
				} else if(status == 1) {
					forms = bookingCheckService.getProcessedForms(teacherId, offset, max)
					statis = bookingCheckService.getStatis(teacherId)
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
		def status = params.int("status")
		if(status == 0) {
			def canCheck = bookingCheckService.canCheckForm(teacherId, id)
			if(canCheck) {
				def form = bookingsService.getInfo(id)
				form.userInfo = bookingsService.getUserExtraInfo(teacherId, departmentId, form.userId)
				form.nextId = bookingCheckService.nextPending(teacherId, id)
				form.prevId = bookingCheckService.prevPending(teacherId, id)
				form.checkable = form.status == BookingForm.STATUS_APPLYING
				def statis = bookingCheckService.getStatis(teacherId)
				return render([
					form: form,
					statis: statis,
				] as JSON)
			} else {
				render(status: HttpStatus.FORBIDDEN)
			}
		} else if(status == 1) {
			def canView = bookingCheckService.canViewForm(teacherId, id)
			if(canView) {
				def form = bookingsService.getInfo(id)
				form.userInfo = bookingsService.getUserExtraInfo(teacherId, departmentId, form.userId)
				form.nextId = bookingCheckService.nextProcessed(teacherId, id)
				form.prevId = bookingCheckService.prevProcessed(teacherId, id)
				form.checkable = false
				def statis = bookingCheckService.getStatis(teacherId)
				return render([
					form: form,
					statis: statis,
				] as JSON)
			} else {
				render(status: HttpStatus.FORBIDDEN)
			}
		} else {
			render(status: HttpStatus.FORBIDDEN)
		}
	}
	
	def modify(Long id) {
		def userId = securityService.userId
		def userName = securityService.userName
		def approved = params.boolean('approved')
		def comment = params.comment
		def result = bookingCheckService.changeStatus(userId, userName, id, approved, comment)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
}
