package cn.edu.bnuz.tms.organization

class PicturesService {
	def grailsApplication
	
	File getFile(String id) {
		return new File(grailsApplication.config.tms.student.picturePath, "${id}.jpg")
	}
}
