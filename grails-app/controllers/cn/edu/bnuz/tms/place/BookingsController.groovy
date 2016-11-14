package cn.edu.bnuz.tms.place

import grails.converters.JSON

import java.text.SimpleDateFormat

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.organization.DepartmentService
import cn.edu.bnuz.tms.place.BookingFormCommand;
import cn.edu.bnuz.tms.place.BookingsService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Role;
import cn.edu.bnuz.tms.teaching.SectionService
import cn.edu.bnuz.tms.teaching.TermService
import cn.edu.bnuz.tms.place.BookingForm;

class BookingsController {
	DepartmentService departmentService
	BookingsService bookingsService
	TermService termService
	SectionService sectionService
	SecurityService securityService
	BookingExportService bookingExportService
	
	def index() {
		def userId = securityService.userId
		def offset = params.int("offset") ?: 0
		def max = params.int("max") ?: 10
		def total = bookingsService.total(userId);
		def forms = bookingsService.list(userId, offset, max)
		return [forms: forms, pager: [offset: offset, max: max, total: total] as JSON]
	}
	
	def newForm() {
		def phoneNumber = securityService.currentUser.longPhone
		if(!phoneNumber) {
			return render(view:"fill")
		}
		def userName = securityService.userName
		def term = termService.currentTerm
		def departments = departmentService.listForSection()
		def departmentId = securityService.departmentId
		def bookingTypes = bookingsService.findBookingTypes(departmentId)
		def isTeacher = securityService.isTeacher()
		def roomTypes = bookingsService.findRoomTypes(isTeacher)
		def advUser = securityService.hasRole(Role.BOOKING_ADV_USER)
		render view:'form', model:[
			term: [
				startWeek: term.startWeek,
				maxWeek: term.maxWeek,
				currentWeek: term.actualWeek,
				startDate: term.startDate,
				swapToDates: term.swapToDates
			],
			userName: userName,
			phoneNumber: phoneNumber,
			departments: departments, 
			departmentId: departmentId,
			bookingTypes: bookingTypes,
			roomTypes: roomTypes,
			days: isTeacher ? (advUser ? -1: 0) : 14,
			conflicts: sectionService.getConfilcts(),
		]
	}
	
	def create() {
		def userId = securityService.userId
		def userName = securityService.userName
		def phoneNumber = securityService.currentUser.longPhone
		def pbfc = new BookingFormCommand(request.JSON)
		def term = termService.currentTerm
		def form = bookingsService.create(userId, userName, phoneNumber, term, pbfc)
		render(contentType:"text/json") { id = form.id }
	}

	def findRoom() {
		def rooms = bookingsService.findRooms(
				termService.currentTerm,
				securityService.isTeacher(),
				params.int("startWeek"),
				params.int("endWeek"),
				params.int("dayOfWeek"),
				params.int("sectionType"),
				params.roomType
		)
		render rooms as JSON
	}
	
	def editForm(Long id) {
		def phoneNumber = securityService.currentUser.longPhone
		if(!phoneNumber) {
			return render(view:"fill")
		}
		
		def userId = securityService.userId
		def form = bookingsService.getInfo(id)
		if(form && form.userId == userId) {
			def term = termService.currentTerm
			def departments = departmentService.listForSection()
			def bookingTypes = bookingsService.findBookingTypes(form.departmentId)
			def isTeacher = securityService.isTeacher()
			def roomTypes = bookingsService.findRoomTypes(isTeacher)
			def advUser = securityService.hasRole(Role.BOOKING_ADV_USER)
			render view: 'form', model: [
				term: [
					startWeek: term.startWeek,
					maxWeek: term.maxWeek,
					currentWeek: term.currentWeek,
					startDate: term.startDate,
				],
				form: form,
				departments: departments, 
				bookingTypes: bookingTypes,
				roomTypes: roomTypes,
				days: isTeacher ? (advUser ? -1: 0) : 14,
				conflicts: sectionService.getConfilcts(),
			]
		} else {
			response.sendError(404)
		}
	}
	
	def update() {
		def userId =  securityService.userId
		def pbfc = new BookingFormCommand(request.JSON)
		def result = bookingsService.update(userId, pbfc)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def modify(Long id) {
		def userId =  securityService.userId
		def status = params.int('status')
		def result = bookingsService.changeStatus(userId, id, status)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def show(Long id) {
		def userId = securityService.userId
		def form = bookingsService.getInfo(id)
		if(form && (userId == form.userId || securityService.isTeacher())) {
			form.allowActions = BookingForm.allowActions(form.status)
			form.isOwner = userId == form.userId
			return [form: form]
		} else {
			response.sendError(404)
		}
	}
	
	def delete(Long id) {
		def result = bookingsService.delete(id)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}

	/**
	 * 获取部门可用的借用类型
	 * @param id 部门ID
	 * @return 借用类型
	 */
	def types(String id) {
		def bookingTypes = bookingsService.findBookingTypes(id)
		render bookingTypes as JSON
	}
	
	def export() {
		def userId = securityService.userId
		def start = new SimpleDateFormat("yyyy-MM-dd").parse(params.start)
		def end = new SimpleDateFormat("yyyy-MM-dd").parse(params.end)
		def report = bookingsService.export(userId, start, end)
		def template = grailsApplication.mainContext.getResource("excel/booking-items.xls").getFile()
		def workbook = bookingExportService.exportItems(template, report, params.start + ' -- ' + params.end)
		response.setContentType("application/excel")
		response.setHeader("Content-disposition", "attachment;filename=\"bookings.xls\"")
		workbook.write(response.outputStream)
	}
}
