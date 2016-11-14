package cn.edu.bnuz.tms.system

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.ProfileService;
import cn.edu.bnuz.tms.system.StudentProfileCommand;
import cn.edu.bnuz.tms.system.TeacherProfileCommand;

class ProfileController {
	SecurityService securityService
	ProfileService profileService

	def index() {
		def userId = securityService.userId
		if(securityService.isTeacher()) {
			render model:[user:Teacher.get(userId), isTeacher:true], view:"profile"
		} else if(securityService.isStudent()) {
			render model:[user:Student.get(userId), isTeacher:false], view:"profile"
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}

	def update() {
		def userId = securityService.userId
		def result = false
		
		if(request.JSON.profile) {
			if(securityService.isTeacher()) {
				TeacherProfileCommand tpc = new TeacherProfileCommand(request.JSON.profile)
				result = profileService.update(userId, tpc)
			} else if(securityService.isStudent()) {
				StudentProfileCommand tpc = new StudentProfileCommand(request.JSON.profile)
				result = profileService.update(userId, tpc)
			}
		} else if(request.JSON.password) {
			if(securityService.isTeacher()) {
				result = profileService.updateTeacherPassword(userId, request.JSON.password.oldPassword, request.JSON.password.newPassword)
			} else if(securityService.isStudent()) {
				result = profileService.updateStudentPassword(userId, request.JSON.password.oldPassword, request.JSON.password.newPassword)
			}
		}
		if(result) {
			render status: HttpStatus.OK
		} else {
			render status: HttpStatus.BAD_REQUEST
		}
	}
}
