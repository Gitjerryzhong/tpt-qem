package cn.edu.bnuz.tms.place

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Role
import cn.edu.bnuz.tms.organization.DepartmentService
import cn.edu.bnuz.tms.place.BookingAdminService;
import grails.converters.JSON

class BookingAdminController {
	SecurityService securityService
	BookingAdminService bookingAdminService
	DepartmentService departmentService
	
	/*
	 * 教学单位审核人（Teaching departments )
	 */
	def tdCheckers() {
		def departments = departmentService.getTeachingDepartments()
		def bookingTypes = bookingAdminService.getTdBookingTypes()
		def auths = bookingAdminService.getTdBookingCheckers()
		return [
			departments: departments,
			bookingTypes: bookingTypes,
			auths: auths as JSON
		]
	}
	
	/*
	 * 行政部门审核人（Administrative departments )
	 */
	def adCheckers() {
		def departments = departmentService.getAdministrativeDepartments()
		def bookingTypes = bookingAdminService.getAdBookingTypes()
		def auths = bookingAdminService.getAdBookingCheckers()
		return [
			departments: departments,
			bookingTypes: bookingTypes,
			auths: auths as JSON
		]
	}
	
	def checker() {
		def typeId = params.long("typeId")
		def checkerId = params.checkerId
		def departmentId = params.departmentId
		bookingAdminService.setChecker(departmentId, typeId, checkerId)
		def auths = bookingAdminService.getAdBookingCheckers()
		render auths as JSON
	}
}
