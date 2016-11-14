package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.place.Room;
import cn.edu.bnuz.tms.rollcall.RollcallForm

class Arrangement {
	static final int ALL = 0
	static final int ODD = 1
	static final int EVEN = 2

	/**
	 * ID���������ɣ���{@link Arrangement#setIdByProperty}�� ����17��
	 */
	String id
	
	/**
	 * ��γ̰��startWeek������ͬҲ���ܲ�ͬ������Ϊ�ա�
	 */
	int startWeek

	/**
	 * ��γ̰��endWeek������ͬҲ���ܲ�ͬ������Ϊ�ա�
	 */
	int endWeek
	
	/**
	 * 1-6: ����һ��������
	 * 7��������
	 */
	int dayOfWeek
	int startSection
	int totalSection
	
	/**
	 * ���:��ѧӢ������������͵İ��ţ��ֱ��Ӧ��λ��ʦ��
	 * ��CourseClassTeacher�е�flag��Ӧ��
	 *   10-19 = ��д
	 *   20-20 = ����
	 *   30-39 = ����
	 *   0 = �����γ�
	 *   1 = ʵ���
	 */
	int flag = 0
	
	int getEndSection() {
		return startSection + totalSection - 1
	}
	
	List<String> getCourseClassNames() {
		return courseClasses.collect {it.name}.unique()
	}
	
	/**
	 * ��˫��:ALL, ODD, EVEN
	 */
	int oddEven
	Room room
	Teacher teacher
	
	@Override
	String toString() {
		"Arrangement[$id-($startWeek-$endWeek)-$dayOfWeek($oddEven)-($startSection-$endSection), room:$room]"
	}

	/**
	 * ����������id����ʽ
	 * <pre>
	 * 20121010390110050
	 * ----=-----==-=--=
	 *   | |  |  | ||| |
	 *   | |  |  | ||| `- ���(0)
	 *   | |  |  | ||`-- ��ʼ��(05)
	 *   | |  |  | |`-- ��˫��(0)
	 *   | |  |  | `-- �ܼ�(1)
	 *   | |  |  `-- ��ʼ��(01)
	 *   | |  `-- ��ʦ����(01039)
	 *   | `-- ѧ��(1)
	 *   `-- ѧ��(2012-2013)
	 *   ѧ�� ��ʦ
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
