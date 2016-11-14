package cn.edu.bnuz.tms.teaching

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.tms.organization.Student


class CourseClassStudent implements Serializable, Comparable<CourseClassStudent> {
	/**
	 * ����
	 */
	static final int Normal = 0
	
	/**
	 * ����λ��
	 * <ul>
	 * <li>����status | (1 <<��FREE_LISTEN_MASK)</li>
	 * <li>�ж�status & (1 <<��FREE_LISTEN_MASK)</li>
	 * </ul>
	 * ���ݲ���ֱ�Ӵ洢��status�У�����ͨ����ѯfreeArrangements�õ���
	 * ��{@link ArrangementService#getRollcallStudents}��
	 * ǰ̨����λ��rollcall/model.js��
	 */
	static final int FREE_LISTEN_MASK = 15
	
	/**
	 * ȡ�������ʸ�λ��
	 * <ul>
	 * <li>����status | (1 <<��CANCEL_EXAM_MASK)</li>
	 * <li>�ж�status & (1 <<��CANCEL_EXAM_MASK)</li>
	 * </ul>
	 */
	static final int CANCEL_EXAM_MASK = 1
	
	int status
	
	/**
	 * ѡ��ʱ��
	 */
	Date time
	/**
	 * ��ע
	 */
	String note
	
	@Override
	int compareTo(CourseClassStudent other) {
		this.student.id <=> other.student.id;
	}
	
	static hasMany = [
		freeArrangements: CourseClassStudentFreeArrangement
	]
	
	static belongsTo = [
		courseClass: CourseClass,
		student: Student	
	]
	
	static mapping = {
		table 		'edu_course_class_student'
		id 			composite: ['courseClass', 'student']
		status		defaultValue: 0
	}
	
	static constraints = {
		note		nullable:true
		time		nullable:true
	}
	
	@Override
	boolean equals(other) {
		if (!(other instanceof CourseClassStudent)) {
			return false
		}

		return other.courseClass.id == courseClass.id &&
			   other.student.id == student.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		builder.append(courseClass.id)
		builder.append(student.id)
		builder.toHashCode()
	}
}