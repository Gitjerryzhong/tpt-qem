package cn.edu.bnuz.tms.system

import grails.converters.JSON
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.TeacherService;

class TeacherController {
	SecurityService securityService
	TeacherService teacherService
	
    def index() {
		render 'should empty'
	}
	
	def changeSetting() {
		assert params.name && params.value
		
		def userId = securityService.userId
		teacherService.changeSetting(userId, params.name, params.value)
		
		render {status:200}
	}
}
