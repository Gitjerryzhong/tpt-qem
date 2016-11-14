package cn.edu.bnuz.tms.material

import grails.converters.JSON
import cn.edu.bnuz.tms.material.TeachingMaterialService;
import cn.edu.bnuz.tms.material.TermMaterialService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.TermService

class PersonalMaterialsController {
	SecurityService securityService
	TermService termService
	TeachingMaterialService teachingMaterialService
	TermMaterialService termMaterialService
	
	
	def index() {
		request.withFormat {
			html {
				def termIds = termService.allTermIds
				def term = termService.currentTerm
				render model:[
					termIds: termIds as JSON,
					termId: term.id
				], view:'/materials/personal'
			}
			json {
				def teacherId = securityService.userId
				def term = termService.getTerm(params.long("term"))
				def departments = teachingMaterialService.getDepartments(teacherId, term)
				departments.each {
					def result = teachingMaterialService.getTeachingMaterials(it.id, teacherId, term)
					it.courses = result.courses
					it.commits = result.commits
					it.thesis = result.thesis
					it.internship = result.internship
					it.categories = teachingMaterialService.getMaterials(it.id, term)
				}
				
				render departments as JSON
			}
		}
	}
}
