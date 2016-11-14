package cn.edu.bnuz.tms.organization

import cn.edu.bnuz.tms.organization.AdminClass;
import cn.edu.bnuz.tms.organization.Student;
import cn.edu.bnuz.tms.organization.Teacher;


class AddressesService {
	static TEACHER_ADDRESS_COLUMNS = [
		"id",
		"name",
		"longPhone",
		"shortPhone",
		"officePhone",
		"officeAddress",
		"homePhone",
		"homeAddress",
		"email",
		"qqNumber"
	]
	
	def getTeachers(String teacherId) {
		Teacher.executeQuery '''
SELECT new map(
  teacher.id as id, 
  teacher.name as name, 
  teacher.longPhone as longPhone,
  teacher.shortPhone as shortPhone, 
  teacher.officePhone as officePhone,
  teacher.officeAddress as officeAddress,
  teacher.homePhone as homePhone, 
  teacher.homeAddress as homeAddress,
  teacher.email as email, 
  teacher.qqNumber as qqNumber
) FROM Teacher teacher, Teacher current
WHERE teacher.department = current.department
AND teacher.enabled = 1
AND current.id = :teacherId
order by teacher.name
''', [teacherId:teacherId]
	}

	static STUDENT_ADDRESS_COLUMNS = [
		"id",
		"name",
		"longPhone",
		"shortPhone",
		"email",
	]
	
	private def collect(results) {
		return results.collect {
			def student = [:]
			def row = it
			STUDENT_ADDRESS_COLUMNS.eachWithIndex {column, index ->
				student[column] = row[index]
			}
			return student
		}
	}
	
	def getStudents(String studentId) {
		def results = Student.executeQuery '''
SELECT student.id, student.name, student.longPhone, student.shortPhone,
student.email
FROM Student student, Student user
WHERE student.adminClass = user.adminClass
AND user.id = :studentId
''', [studentId: studentId]
		
		return collect(results)
	}
	
	def getAdminClassesByDepartment(String departmentId) {
		def results = AdminClass.executeQuery '''
SELECT adminClass.name
FROM AdminClass adminClass
WHERE adminClass.department.id = :departmentId
''', [departmentId:departmentId]
		return results
	}
	
	def getAdminClassesByTeacher(String teacherId) {
		def results = AdminClass.executeQuery '''
SELECT adminClass.name
FROM AdminClass adminClass
WHERE adminClass.adminTeacher.id = :teacherId
OR adminClass.gradeTeacher.id = :teacherId
''', [teacherId:teacherId]
		return results
	}
	
	def getAdminClassStudents(String adminClass) {
		def results = Student.executeQuery '''
SELECT student.id, student.name, student.longPhone, student.shortPhone,
student.email
FROM Student student
JOIN student.adminClass adminClass
WHERE adminClass.name = :adminClass
''', [adminClass: adminClass]
		return collect(results)
	}
	
}
