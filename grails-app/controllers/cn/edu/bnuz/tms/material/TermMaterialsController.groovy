package cn.edu.bnuz.tms.material

import org.springframework.http.HttpStatus;

import grails.converters.JSON
import cn.edu.bnuz.tms.material.TermMaterialService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.TermService;

class TermMaterialsController {
	SecurityService securityService
	TermService termService
	TermMaterialService termMaterialService
	def index() {
		def termIds = termService.allTermIds
		def term = termService.currentTerm
		def departmentId = securityService.departmentId
		def materials = termMaterialService.getMaterials(departmentId)
		render model: [termIds: termIds as JSON, termId: term.id, materials: materials as JSON],
		       view: '/materials/term'
	}
	
	def create() {
		def termId = params.long("term")
		def materialId = params.long("mid")
		def departmentId = securityService.departmentId
		def result = termMaterialService.create(departmentId, materialId, termId)
		if(result) {
			render ([id:result.id] as JSON)
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def delete() {
		def result = termMaterialService.delete(params.long('id'))
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def show() {
		def termId = params.long("id")
		def departmentId = securityService.departmentId
		def results = termMaterialService.get(departmentId, termId)
		render results as JSON
	}
}
