package cn.edu.bnuz.tms.place
import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.place.BookingExportService;
import cn.edu.bnuz.tms.place.BookingReportCommand;
import cn.edu.bnuz.tms.place.BookingReportService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.TermService
import grails.converters.JSON

class BookingReportController {
	BookingReportService bookingReportService
	BookingExportService bookingExportService
	SecurityService securityService
	TermService termService
	
	def index() {
		def teacherId = securityService.userId
		def offset = params.int("offset") ?: 0
		def max = params.int("max") ?: 10
		def total = bookingReportService.total();
		def reports = bookingReportService.list(offset, max)
		return [reports: reports, pager: [offset: offset, max: max, total: total] as JSON]
	}
	
	def newForm() {
		render view:'form'
	}
	
	def create() {
		def teacherId =  securityService.userId
		def brc = new BookingReportCommand(request.JSON)
		def report = bookingReportService.create(teacherId,brc)
		render(contentType:"text/json") { id = report.id }
	}
	
	def unprocessed() {
		def term = termService.currentTerm
		def requests = bookingReportService.findUnprocessed(term)
		render requests as JSON
	}
	
	def show(Long id) {
		def report = bookingReportService.getInfo(id)
		if(report) {
			[report: report]
		} else {
			response.sendError(404)
		}
	}
	
	def editForm(Long id) {
		def report = bookingReportService.getInfo(id)
		if(report) {
			render view:'form', model:[report: report]
		} else {
			response.sendError(404)
		}
	}
	
	def update() {
		def userId =  securityService.userId
		def brc = new BookingReportCommand(request.JSON)
		def result = bookingReportService.update(userId, brc)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def export(Long id) {
		def report = bookingReportService.export(id)
		if(report) {
			def template = grailsApplication.mainContext.getResource("excel/booking-report.xls").getFile()
			def workbook = bookingExportService.exportReport(template, report)
			response.setContentType("application/excel")
			response.setHeader("Content-disposition", "attachment;filename=\"${report.reportId}.xls\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
}
