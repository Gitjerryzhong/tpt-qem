package cn.edu.bnuz.tms.taglib

import cn.edu.bnuz.tms.security.SecurityService

class SecurityTagLib {
	static namespace = 't'
	SecurityService securityService
	def ifTeacher = { attrs, body ->
		if(securityService.isTeacher()) {
			out << body()
		}
	}

	def ifStudent = { attrs, body ->
		if(securityService.isStudent()) {
			out << body()
		}
	}
	
	def hasRole = {attrs, body ->
		if(securityService.hasRole(attrs.role)) {
			out << body()
		}
	}
}