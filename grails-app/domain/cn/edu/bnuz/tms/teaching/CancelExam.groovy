package cn.edu.bnuz.tms.teaching

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher

class CancelExam implements Serializable{
	Term term
	Course course
	Student student
	Date dateCreated
	Date lastUpdated
	Teacher creator
	Teacher updater
	/**
	 * 1：取消资格，0：恢复资格
	 */
	int status  
	
	static mapping = {
		id 			composite: ['term', 'course', 'student']
		table 		'edu_cancel_exam'
	}
	
	static constraints = {
		updater	nullable:true
	}
	
	@Override
	boolean equals(other) {
		if (!(other instanceof CancelExam)) {
			return false
		}

		return other.term.id == term.id &&
			   other.course.id == course.id
			   other.student.id == student.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		builder.append(term.id)
		builder.append(course.id)
		builder.append(student.id)
		builder.toHashCode()
	}
}
