package cn.edu.bnuz.tms.rollcall

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.tms.teaching.CourseClassStudent

class CourseClassStudentRollcall implements Serializable {
	CourseClassStudent courseClassStudent
	Integer week
	Integer absentCount;
	Integer lateCount;
	Integer earlyCount;
	Integer leaveCount;

	static belongsTo = [
		courseClassStudent: CourseClassStudent
	]

	static mapping = {
		id 			composite: ['courseClassStudent', 'week']
		table 		'rc_course_class_student_rollcall'
		columns {
			courseClassStudent {
				column name: "course_class_id"
				column name: "student_id"
			}
			absentCount	defaultValue: 0
			lateCount	defaultValue: 0
			earlyCount	defaultValue: 0
			leaveCount	defaultValue: 0
		}
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof CourseClassStudentRollcall)) {
			return false
		}

		return other.courseClassStudent.courseClass.id == courseClassStudent.courseClass.id &&
		other.courseClassStudent.student.id == courseClassStudent.student.id &&
		other.week == week
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		builder.append(courseClassStudent.courseClass.id)
		builder.append(courseClassStudent.student.id)
		builder.append(week)
		builder.toHashCode()
	}
}
