package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.Room;
import cn.edu.bnuz.tms.rollcall.RollcallForm

class Arrangement {
	static final int ALL = 0
	static final int ODD = 1
	static final int EVEN = 2

	/**
	 * ID由属性生成，见{@link Arrangement#setIdByProperty}， 长度17。
	 */
	String id
	
	/**
	 * 与课程班的startWeek可能相同也可能不同，不能为空。
	 */
	int startWeek

	/**
	 * 与课程班的endWeek可能相同也可能不同，不能为空。
	 */
	int endWeek
	
	/**
	 * 1-6: 星期一至星期六
	 * 7：星期日
	 */
	int dayOfWeek
	int startSection
	int totalSection
	
	/**
	 * 标记:大学英语会有三种类型的安排，分别对应三位教师。
	 * 与CourseClassTeacher中的flag对应。
	 *   10-19 = 读写
	 *   20-20 = 听力
	 *   30-39 = 口语
	 *   0 = 其它课程
	 *   1 = 实验课
	 */
	int flag = 0
	
	int getEndSection() {
		return startSection + totalSection - 1
	}
	
	List<String> getCourseClassNames() {
		return courseClasses.collect {it.name}.unique()
	}
	
	/**
	 * 单双周:ALL, ODD, EVEN
	 */
	int oddEven
	Room room
	Teacher teacher
	
	@Override
	String toString() {
		"Arrangement[$id-($startWeek-$endWeek)-$dayOfWeek($oddEven)-($startSection-$endSection), room:$room]"
	}

	/**
	 * 由属性生成id。格式
	 * <pre>
	 * 20121010390110050
	 * ----=-----==-=--=
	 *   | |  |  | ||| |
	 *   | |  |  | ||| `- 标记(0)
	 *   | |  |  | ||`-- 开始节(05)
	 *   | |  |  | |`-- 单双周(0)
	 *   | |  |  | `-- 周几(1)
	 *   | |  |  `-- 开始周(01)
	 *   | |  `-- 教师工号(01039)
	 *   | `-- 学期(1)
	 *   `-- 学年(2012-2013)
	 *   学期 教师
	 * </pre>
	 */
	void setIdByProperty(String courseClassId) {
		def m = (courseClassId =~ /\((.*)\)-.*-(.*)-.*/)[0]
		def term = m[1] // 2012-2013-2 -> 20122
		def teacherId = m[2]
		id =  String.format("%4s%1s%5s%02d%1d%1d%02d%1d", 
			term[0..3], term[-1], 
			teacherId, startWeek, dayOfWeek, oddEven, startSection,
			flag
		)
	}

	static hasMany = [
		courseClasses: CourseClass,
		rollcallForms: RollcallForm
	]

	static belongsTo = [CourseClass]

	static mapping = {
		table 			'edu_arrangement'
		id 				generator:'assigned', length:27
		flag 			defaultValue:0, length:1
		courseClasses 	fetch:'join', joinTable:[name:'edu_course_class_arrangement', key:'arrangement_id']
	}

	static constraints = {
		dayOfWeek 		range:0..6
		startSection 	range:1..12
		totalSection 	range:1..12
		oddEven 		inList:[ALL, ODD, EVEN]
		room            nullable:true
		teacher         nullable:true
	}
}
