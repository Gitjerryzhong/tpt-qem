package cn.edu.bnuz.tms.material

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.material.MaterialService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Role
import cn.edu.bnuz.tms.teaching.MaterialCommand;

class MaterialsController {
	SecurityService securityService
	MaterialService materialService

	def index() {
		if(securityService.hasRole(Role.SYSTEM_ADMIN)) {
			def categories = materialService.getCategories();
			def materials = materialService.getAll();
			return [categories: categories, materials: materials as JSON, system: true]
		} else if(securityService.hasRole(Role.ACADEMIC_SECRETARY)) {
			def categories = materialService.getCategories();
			def departmentId = securityService.departmentId
			def materials = materialService.getAll(departmentId)
			return [categories: categories, materials: materials as JSON, system: false]
		} else {
			render status:HttpStatus.UNAUTHORIZED
		}
	}

	def create() {
		MaterialCommand mc = new MaterialCommand(request.JSON)
		if(securityService.hasRole(Role.SYSTEM_ADMIN)) {
			def material = materialService.create(null, mc)
			render(contentType:"text/json") { 
				id = material.id
				deptId = null
				deptName = null 
			}
		} else if(securityService.hasRole(Role.ACADEMIC_SECRETARY)) {
			def material = materialService.create(securityService.departmentId, mc)
			render(contentType:"text/json") { 
				id = material.id
				deptId = material.department.id
				deptName = material.department.name 			
			}
		} else {
			render status:HttpStatus.UNAUTHORIZED
		}
	}
	
	def update() {
		MaterialCommand mc = new MaterialCommand(request.JSON)
		def result = materialService.update(params.long('id'), mc)
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
	
	def delete() {
		def result = materialService.delete(params.long("id"))
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
}
