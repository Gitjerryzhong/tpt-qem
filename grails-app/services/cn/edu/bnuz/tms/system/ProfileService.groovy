package cn.edu.bnuz.tms.system

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.system.StudentProfileCommand;
import cn.edu.bnuz.tms.system.TeacherProfileCommand;

class ProfileService {
	def update(String teacherId, TeacherProfileCommand tpc) {
		Teacher teacher = Teacher.get(teacherId)
		tpc.copyTo(teacher)
		teacher.save(failOnError: true)
		return true
	}

	def update(String studentId, StudentProfileCommand spc) {
		Student student = Student.get(studentId)
		spc.copyTo(student)
		student.save(failOnError: true)
		return true
	}

	def updateTeacherPassword(String teacherId, String oldPassword, String newPassword) {
		Teacher teacher = Teacher.findByIdAndPassword(teacherId, oldPassword)
		if(!teacher) {
			teacher = Teacher.findByIdAndPasswordEs(teacherId, oldPassword)
			if(!teacher)
				return false
		}

		teacher.password = newPassword
		teacher.save(failOnError: true)
		return true
	}

	def updateStudentPassword(String studentId, String oldPassword, String newPassword) {
		Student student = Student.findByIdAndPassword(studentId, oldPassword)
		if(!student) {
			student = Student.findByIdAndPassword(studentId, oldPassword)
			if(!student)
				return false
		}
		
		student.password = newPassword
		student.save(failOnError: true)
		return true
	}
}
