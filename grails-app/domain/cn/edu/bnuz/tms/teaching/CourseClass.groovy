package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Department


class CourseClass {
	String id
	String name
	
	/**
	 * ѧ�ڣ�2012-2013-2
	 */
	Term term
	
	/**
	 * �����ܿ�ʱ
	 */
	double theoryWeekHours
	
	/**
	 * ʵ���ܿ�ʱ
	 */
	double practiceWeekHours
	
	/**
	 * ��ʼ��
	 */
	int startWeek
	
	/**
	 * ������
	 */
	int endWeek
	
	Course course
	Department department
	SortedSet<CourseClassStudent> students
	
	@Override
	String toString() {
		"CourseClass[$id, name:$name]"
	}
	
	static belongsTo = [
		Course,
		Department
	]
	
	static hasMany = [
		students: CourseClassStudent,
		teachers: CourseClassTeacher,
		arrangements: Arrangement
	]
	
	static mapping = {
		table 			'edu_course_class'
		id 				generator:'assigned', length:32
		term			column:'term'
		arrangements 	joinTable:[name:'edu_course_class_arrangement', key:'course_class_id']
	}
}
