package cn.edu.bnuz.tms.organization

import cn.edu.bnuz.tms.system.Role
import cn.edu.bnuz.tms.system.TeacherRole
import cn.edu.bnuz.tms.organization.AdminClass;
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.organization.Teacher;

class DepartmentService {
	/**
	 * 部门列表（用于下拉选择）
	 * @return [id:id, name:name]*
	 */
	def listForSection() {
		Department.executeQuery 'SELECT new map(id as id, name as name) FROM Department where enabled = true'
	}
	
	/**
	 * 获取行政部门
	 * @return [id:id, name:name]*
	 */
	def getAdministrativeDepartments() {
		Department.executeQuery 'SELECT new map(id as id, name as name) FROM Department where isAdminDept = true and enabled = true'
	}
	
	/**
	 * 获取教学单位
	 * @return [id:id, name:name]*
	 */
	def getTeachingDepartments() {
		Department.executeQuery 'SELECT new map(id as id, name as name) FROM Department where isAdminDept = false and enabled = true'
	}
	
	/**
	 * 获取所有部门管理员
	 * @return
	 */
	def getDepartmentAdmins() {
		def results = Department.executeQuery '''
SELECT department.id, department.name, 
 (SELECT teacher.id FROM Teacher teacher JOIN teacher.roles teacherRole WHERE teacher.department = department AND teacherRole.role = :role),  
 (SELECT teacher.name FROM Teacher teacher JOIN teacher.roles teacherRole WHERE teacher.department = department AND teacherRole.role = :role)
FROM Department department
WHERE enabled = true
''', [role:Role.DEPARTMENT_ADMIN]

		return results.collect() {
			[id:it[0], name:it[1], adminId:it[2], adminName:it[3]]
		}
	}

	def getTeachers(String departmentId) {
		def results = Teacher.executeQuery '''
SELECT teacher.id, teacher.name
FROM Teacher teacher
WHERE teacher.department.id = :departmentId
AND teacher.enabled = true
ORDER BY teacher.name
''', [departmentId:departmentId]
		return results.collect {
			[id: it[0], name: it[1]]
		}
	}

	def setDepartmentAdmin(String departmentId, String teacherId) {
		// 删除已有
		TeacherRole.executeUpdate '''
DELETE TeacherRole teacherRole
WHERE teacherRole.teacher in (FROM Teacher WHERE department.id = :departmentId)
AND teacherRole.role = :role
''', [departmentId:departmentId, role:Role.DEPARTMENT_ADMIN]
		// 创建
		if(teacherId) {
			TeacherRole.create(Teacher.load(teacherId), Role.DEPARTMENT_ADMIN, true);
		}
	}

	/**
	 * 获取指定学院的教师角色
	 * @param departmentId 学院ID
	 * @return Map of [role: [id:teacherId, name:teacherName]]
	 */
	def getTeacherRoles(String departmentId) {
		def roles = [
			Role.DEAN_OF_COLLEGE,
			Role.DEAN_OF_TEACHING,
			Role.STUDENT_AFFAIRS,
			Role.ROLLCALL_ADMIN,
			Role.ACADEMIC_SECRETARY,
			Role.TPT_ADMIN,
			Role.QEM_CHECKER,
			Role.TPT_COPROJECT
		]
		
		def results = TeacherRole.executeQuery '''
SELECT teacher.id, teacher.name, teacherRole.role
FROM TeacherRole teacherRole
JOIN teacherRole.teacher teacher
WHERE teacher.department.id = :departmentId
AND teacherRole.role in :roles
''', [departmentId:departmentId, roles:roles]

		
		def teacherRoles = roles.collectEntries {
			[it, null]
		} 
		
		results.each {
			teacherRoles[it[2]] = [id:it[0], name:it[1]]
		}
		
		return teacherRoles
	}
	
	/**
	 * 设置教师角色
	 * @param departmentId 学院ID
	 * @param teacherId 教师ID
	 * @param role 角色
	 */
	def setTeacherRole(String departmentId, String teacherId, String role) {
		// 删除已有
		TeacherRole.executeUpdate '''
DELETE TeacherRole teacherRole
WHERE teacherRole.teacher in (FROM Teacher WHERE department.id = :departmentId)
AND teacherRole.role = :role
''', [departmentId:departmentId, role:role]
		// 创建
		if(teacherId) {
			TeacherRole.create(Teacher.load(teacherId), role, true);
		}
	}
	
	/**
	 * 获取行政班
	 * @param departmentId 学院ID
	 * @return List of [adminClassName, adminTeacherId, adminTeacherName, gradeTeacherId, gradeTeacherName]
	 */
	def getAdminClasses(String departmentId) {
		def results = AdminClass.executeQuery '''
SELECT new map(
  adminClass.name as name,
  adminTeacher.id as adminTeacherId,
  adminTeacher.name as adminTeacherName,
  gradeTeacher.id as gradeTeacherId,
  gradeTeacher.name as gradeTeacherName
)
FROM AdminClass adminClass
LEFT JOIN adminClass.adminTeacher adminTeacher
LEFT JOIN adminClass.gradeTeacher gradeTeacher
WHERE adminClass.department.id = :departmentId
ORDER BY adminClass.name
''', [departmentId:departmentId]
	}
	
	/**
	 * 设置班主任
	 * @param adminClassName 行政班名称
	 * @param teacherId 教师ID
	 * @return
	 */
	def setAdminTeacher(String adminClassName, String teacherId) {
		AdminClass.executeUpdate '''
UPDATE AdminClass SET adminTeacher = :teacher WHERE name=:adminClassName
''',[adminClassName:adminClassName, teacher:Teacher.load(teacherId)]
	}
	
	/**
	 * 设置辅导员
	 * @param adminClassName 行政班名称
	 * @param teacherId 教师ID
	 * @return
	 */
	def setGradeTeacher(String adminClassName, String teacherId) {
		AdminClass.executeUpdate '''
UPDATE AdminClass SET gradeTeacher = :teacher WHERE name=:adminClassName
''',[adminClassName:adminClassName, teacher:Teacher.load(teacherId)]
	}
}
