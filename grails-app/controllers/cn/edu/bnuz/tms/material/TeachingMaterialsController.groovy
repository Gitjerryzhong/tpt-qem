package cn.edu.bnuz.tms.material

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.material.TeachingMaterialService;
import cn.edu.bnuz.tms.material.TermMaterialService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.TermService

class TeachingMaterialsController {
	SecurityService securityService
	TermService termService
	TeachingMaterialService teachingMaterialService
	TermMaterialService termMaterialService
	def index() {
		request.withFormat {
			html { 
				def termIds = termService.allTermIds
				def term = termService.currentTerm
				render model: [
					termIds: termIds as JSON,
					termId: term.id
				], view: '/materials/teaching'
			}
			json {
				def departmentId = securityService.departmentId
				def term = termService.getTerm(params.long("term"))
				def teachers = teachingMaterialService.getTeachers(departmentId, term)
				def categories = teachingMaterialService.getMaterials(departmentId, term)
				def result = [teachers:teachers, categories:categories]
				render result as JSON
			}
		}
	}
	
	/**
	 * @param id ΩÃ ¶ID
	 */
	def show(String id) {
		def departmentId = securityService.departmentId
		def termId = params.long("term")
		def term = termService.getTerm(termId)
		def results = teachingMaterialService.getTeachingMaterials(departmentId, id, term)
		render results as JSON
	}
	
	def create() {
		def checkerId = securityService.userId
		def termMaterialId = params.long("tmid")
		def targetId = params.target
		def teacherId = params.teacherId
		def status = params.int("status")
		def result = teachingMaterialService.create(termMaterialId, targetId, teacherId, checkerId, status)
		if(result) {
			render(contentType:"text/json") { id = result.id } 
		} else {
			render status: HttpStatus.BAD_REQUEST 
		}
	}
	
	def modify() {
		def userId =  securityService.userId
		def id = params.long('id')
		def status = params.int('status')
		def result = teachingMaterialService.changeStatus(id, status)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def delete() {
		def result = teachingMaterialService.delete(params.long("id"))
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
}
