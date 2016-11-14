package cn.edu.bnuz.tms.system

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.tms.organization.Teacher


/**
 * ½ÌÊ¦½ÇÉ«
 * @author young
 *
 */
class TeacherRole implements Serializable {

	String  role

	static belongsTo = [teacher:Teacher]
	
	static mapping = {
		id    composite: ['role', 'teacher']
		table 'sys_teacher_role'
	}
	
	boolean equals(other) {
		if (!(other instanceof TeacherRole)) {
			return false
		}

		other.teacher?.id == teacher?.id &&
		other.role == role
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (teacher) builder.append(teacher.id)
		if (role) builder.append(role)
		builder.toHashCode()
	}
	
	static List<String> getRoles(String teacherId) {
		TeacherRole.executeQuery 'SELECT role FROM TeacherRole where teacher.id = :teacherId',
			[teacherId: teacherId]
	}

	static TeacherRole get(String teacherId, String role) {
		find 'FROM TeacherRole WHERE teacher.id=:teacherId and role=:role',
			[teacherId: teacherId, role: role]
	}

	static TeacherRole create(Teacher teacher, String role, boolean flush = false) {
		new TeacherRole(teacher: teacher, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(Teacher teacher, String role, boolean flush = false) {
		TeacherRole instance = TeacherRole.findByTeacherAndRole(teacher, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(Teacher teacher) {
		executeUpdate 'DELETE FROM TeacherRole WHERE teacher=:teacher', [teacher: teacher]
	}

	static void removeAll(String role) {
		executeUpdate 'DELETE FROM TeacherRole WHERE role=:role', [role: role]
	}
}
