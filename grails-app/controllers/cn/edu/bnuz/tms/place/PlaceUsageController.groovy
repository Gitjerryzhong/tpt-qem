package cn.edu.bnuz.tms.place

import grails.converters.JSON
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.TermService

class PlaceUsageController {
	TermService termService
	SecurityService securityService
	PlaceUsageService placeUsageService
	
	def index() {
		def isTeacher = securityService.isTeacher()
		def term = termService.currentTerm;
		def buildings = placeUsageService.findBuildings(isTeacher, term.id)
		def rooms = placeUsageService.findRooms(isTeacher, term.id, buildings[0])
		return [buildings: buildings as JSON, rooms: rooms as JSON, term: [
			startWeek: term.startWeek,
			maxWeek: term.maxWeek
		] as JSON]
	}
	
	def rooms(String building) {
		def isTeacher = securityService.isTeacher()
		def term = termService.currentTerm;
		def rooms = placeUsageService.findRooms(isTeacher, term.id, building)
		render(rooms as JSON)
	}
	
	def usages(String room) {
		def term = termService.currentTerm;
		def usages = placeUsageService.findUsages(term.id, room)
		render(usages as JSON)
	}
}
