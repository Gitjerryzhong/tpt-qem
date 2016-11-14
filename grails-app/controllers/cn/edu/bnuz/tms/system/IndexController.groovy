package cn.edu.bnuz.tms.system

import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Menu;


class IndexController {
	SecurityService securityService

	def index() {
		if(securityService.isTeacher()) {
			render view:"teacher", model:[modules: getModules()]
		} else if(securityService.isStudent()) {
			render view:"student", model:[modules: getModules()]
		} else {
			render status: 404
		}
	}

	private getModules() {
		def menus = session["menus"]
		def modules = [:]
		menus.each { 
			collectMenu it, modules
		}

		return modules
	}

	private collectMenu(Menu m, Map modules) {
		if(m.module) {
			if(modules[m.module]) {
				modules[m.module] << m
			} else {
				modules[m.module] = [m]
			}
		}

		if(m.submenus) {
			m.submenus.each { 
				collectMenu it, modules 
			}
		}
	}
}
