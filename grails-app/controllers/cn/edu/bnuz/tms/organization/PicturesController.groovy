package cn.edu.bnuz.tms.organization

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.organization.PicturesService;
import cn.edu.bnuz.tms.security.SecurityService

class PicturesController {
	SecurityService securityService
	PicturesService picturesService
	def show(String id) {
		if(securityService.isStudent()) {
			def studentId = securityService.userId;
			if(id == studentId) {
				output(id)
			} else {
				render status:HttpStatus.UNAUTHORIZED
			}
		} else {
			output(id)
		}
	}
	
	private output(String id) {
		File file = picturesService.getFile(id)
		if(file.exists()) {
			response.contentType = URLConnection.guessContentTypeFromName(file.getName())
			response.outputStream << file.bytes
			response.outputStream.flush()
		} else {
			render status:HttpStatus.NOT_FOUND
		}
	}
}
