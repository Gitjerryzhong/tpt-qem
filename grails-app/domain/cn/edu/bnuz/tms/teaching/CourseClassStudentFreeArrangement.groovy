package cn.edu.bnuz.tms.teaching

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.tms.organization.Student

/**
 * 课程班学生免听安排
 * @author 杨林
 * @version 0.1
 * @since 0.1
 */
class CourseClassStudentFreeArrangement implements Serializable {
	CourseClassStudent courseClassStudent
	Arrangement arrangement
	
	static belongsTo = [
		CourseClassStudent,
		Arrangement
	]
	
	static mapping = {
		id 			composite: ['courseClassStudent', 'arrangement']
		table 		'edu_course_class_student_free_arrangement'
		columns {
			courseClassStudent {
				column name: "course_class_id"
				column name: "student_id"
			}
		}
	}
	
	@Override
	boolean equals(other) {
		if (!(other instanceof CourseClassStudentFreeArrangement)) {
			return false
		}

		return other.courseClassStudent.courseClass.id == courseClassStudent.courseClass.id &&
			   other.courseClassStudent.student.id == courseClassStudent.student.id &&
			   other.arrangement.id == arrangement.id
			   
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		builder.append(courseClassStudent.courseClass.id)
		builder.append(courseClassStudent.student.id)
		builder.append(arrangement.id)
		builder.toHashCode()
	}
}
