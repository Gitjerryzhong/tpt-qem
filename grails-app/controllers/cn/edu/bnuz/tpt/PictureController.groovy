package cn.edu.bnuz.tpt

import cn.edu.bnuz.tms.security.SecurityService;
import org.springframework.http.HttpStatus

class PictureController {
    SecurityService securityService
	def show() {
		def bn=params.bn
		def id=params.id
		def filename=params.filename
		if(securityService.isStudent()) {
			def studentId = securityService.userId;
			if(id == studentId) {
				output(bn+"/"+id,filename)
			} else {
				render status:HttpStatus.UNAUTHORIZED
			}
		} else {
			output(bn+"/"+id,filename)
		}		
	}
	
	private output(String basePath,String filename) {
		File file = new File(grailsApplication.config.tms.tpt.uploadPath+"/"+basePath, "${filename}")
		if(file.exists()) {
			response.contentType = URLConnection.guessContentTypeFromName(file.getName())
			response.outputStream << file.bytes
			response.outputStream.flush()
		} else {
			render status:HttpStatus.NOT_FOUND
		}
	}
}
