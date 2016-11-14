package cn.edu.bnuz.tms.teaching

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.tms.organization.Teacher


class CourseClassTeacher implements Serializable {
	int flag = 0

	static belongsTo = [
		courseClass: CourseClass,
		teacher: Teacher
	]

	static mapping = {
		id 		composite: ['courseClass', 'teacher', 'flag']
		table 	'edu_course_class_teacher'
		flag	defaultValue:0
	}

	static constraints = {
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof CourseClassTeacher)) {
			return false
		}

		return other.courseClass.id == courseClass.id &&
			   other.teacher.id == teacher.id &&
			   other.flag == flag
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		builder.append(courseClass.id)
		builder.append(teacher.id)
		builder.toHashCode()
	}
}