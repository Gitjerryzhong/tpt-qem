package cn.edu.bnuz.tms.util

import java.text.SimpleDateFormat

import org.codehaus.groovy.reflection.ReflectionUtils
import org.joda.time.LocalDate

import cn.edu.bnuz.tms.organization.AdminClass
import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Arrangement
import cn.edu.bnuz.tms.teaching.Course
import cn.edu.bnuz.tms.teaching.CourseClass
import cn.edu.bnuz.tms.teaching.CourseClassStudent
import cn.edu.bnuz.tms.teaching.CourseClassTeacher
import cn.edu.bnuz.tms.teaching.Major
import cn.edu.bnuz.tms.teaching.Term

class DataBuilder {
	private static final String KEY_ADMIN_TEACHER = 'adminTeacher'
	private static final String KEY_GRADE_TEACHER = 'gradeTeacher'
	private static final String KEY_DEFAULT_MAJOR = 'defaultMajor'
	private static final String KEY_MAJOR = 'major'
	private static final String KEY_BIRTHDAY = 'birthday'
	private static final String KEY_WEEK = 'week'
	private static final String KEY_PASSWORD = 'password'
	
	// �������������ʹ��
	private int indent = 0

	// ����ջ��ʹ��parent��ȡջ�����󣬱�ʾ��ǰ����ĸ�����
	private List stack = []

	// ������ȱʡרҵ
	private Major defaultMajor

	/**
	 * ���ñհ�������ǰ���ѹջ����Ϊ�¼����ĸ���㣬��ͨ��getParent���
	 * @param current ��ǰ���
	 * @param c ��ǰ���ıհ����¼���㣩
	 */
	private void call(current, Closure c) {
		stack.push(current)
		indent++
		c.delegate = this
		c.call()
		indent--
		stack.pop()
	}
	
	/**
	 * ѧ�ڡ�
	 * <pre>
	 * term(20121, from:'2012-09-17', to:'2013-01-18', week:1..18) {
	 *     ...
	 * }
	 * </pre>
	 */
	private void term(Map map, Long id) {
		Term term = new Term(map)
		term.id = id
		term.startDate = LocalDate.parse(map.from)
		term.endDate = LocalDate.parse(map.to)
		term.startWeek = map.week.from
		term.endWeek = map.week.to
		term.save()
		
		debug term
	}

	/**
	 * ѧԺ��
	 * <pre>
	 * department('id', name:'name') {
	 *     ...
	 * }
	 * </pre>
	 */
	private void department(Map map, String id, Closure c) {
		Department a = new Department(map)
		a.id = id
		a.save()
		// next
		debug a
		call(a, c)

		// save all
		a.save(flush:true, failOnError:true)
	}
	
	/**
	 * רҵ��
	 * <pre>
	 * major 'id', name:'name'
	 * </pre>
	 */
	private void major(Map map, String id) {
		Major major = new Major(map)
		major.id = id
		Department department = parent as Department
		department.addToMajors major
		major.save()

		debug major
	}

	/**
	 * ��ʦ��
	 * <pre>
	 * teacher 'id', name:'name', password:'password', ...
	 * </pre>
	 */
	private void teacher(Map map, String id) {
		if(map.containsKey(KEY_BIRTHDAY)) {
			map[KEY_BIRTHDAY] = parseDate(map[KEY_BIRTHDAY])
		}
		Teacher teacher = new Teacher(map)
		teacher.id = id
		teacher.enabled = true
		Department department = parent as Department
		department.addToTeachers teacher
		teacher.save()

		debug teacher
	}

	/**
	 * �����ࡣ
	 * <pre>
	 * adminClass('name', grade:2012, gradeTeacher:'tno', adminTeacher:'tno') {
	 *     student(...)
	 *     ...
	 * }
	 * </pre>
	 */
	private void adminClass(Map map, String name, Closure c = {} ) {
		Department department = parent as Department

		replaceNumToTeacher department, map, KEY_GRADE_TEACHER
		replaceNumToTeacher department, map, KEY_ADMIN_TEACHER

		AdminClass adminClass = new AdminClass(map)
		adminClass.name = name
		
		defaultMajor = map.containsKey(KEY_DEFAULT_MAJOR) ? department.majors.find {
			it.id == map[KEY_DEFAULT_MAJOR]
		} : null

		department.addToAdminClasses adminClass
		adminClass.save(failOnError:true)

		debug adminClass
		call(adminClass, c)
	}

	/**
	 * �滻map�н�ʦ���ΪTeacher����
	 */
	private void replaceNumToTeacher(Department department, Map map, String key) {
		if(map.containsKey(key)) {
			map[key] = department.teachers.find {it.id == map[key]}
		}
	}

	/**
	 * ѧ�������δָ��רҵ��ʹ��AdminClass��defaultMajor��
	 * <pre>
	 * student 'id', name:'name', password:'password', ...
	 * </pre>
	 */
	private void student(Map map, String id) {
		AdminClass adminClass = parent as AdminClass
		replaceNumToMajor adminClass, map

		// ȱʡ����Ϊѧ����4λ
		map.password = map.password ?: id[-4..-1]
		
		Student student = new Student(map)
		student.id = id
		student.enabled = true
		adminClass.addToStudents student
		student.save()

		debug student
	}

	/**
	 * �滻map��רҵ���ΪMajor����
	 */
	private void replaceNumToMajor(AdminClass adminClass, Map map) {
		if(map.containsKey(KEY_MAJOR)) {
			map[KEY_MAJOR] = adminClass.department.majors.find {it.id == map[KEY_MAJOR]}
		} else {
			map[KEY_MAJOR] = defaultMajor
		}
	}

	/**
	 * �γ̡�
	 * <pre>
	 * course('id', name:'name', ...) {
	 *     courseClass(...)
	 * }
	 * </pre>
	 */
	private void course(Map map, String id, Closure c = {}) {
		Department department = parent as Department

		Course course = new Course(map)
		course.id = id
		department.addToCourses course

		course.save()

		debug course
		call(course, c)
	}

	/**
	 * �γ̰ࡣ
	 * <pre>
	 * courseClass('id', name:'name', ...) {
	 *     arrangement(...)
	 *     arrangement(...)
	 *     ...
	 *     teachers(...) // Ĭ��Ϊ�γ�ID�еĽ�ʦ
	 *     students(...)
	 * }
	 * </pre>
	 */
	private void courseClass(Map map, String id, Closure c = {}) {
		Course course = parent as Course
		CourseClass courseClass = new CourseClass(map)
		courseClass.id = id
		
		def m = (courseClass.id =~ /\((.*)\)-.*-(.*)-.*/)[0];
		def term = m[1] // 2012-2013-1
		courseClass.term = Term.load(new Long(term[0..3]+term[-1]))
		
		def week = map.week ?: 1..18 // week default value is 1..18
		courseClass.startWeek = week.from
		courseClass.endWeek = week.to
		
		def weekHours = map.weekHours ?: '3.0-2.0' // week default value is 1..18
		def hours = (weekHours =~ /(.*)-(.*)/)[0]
		courseClass.theoryWeekHours = Double.parseDouble(hours[1])
		courseClass.practiceWeekHours = Double.parseDouble(hours[2])
		
		courseClass.type = map.type ?: course.type
		courseClass.property = map.property ?: course.property
		
		courseClass.department = course.department
		courseClass.course = course
		courseClass.save(flush:true)
		debug courseClass
		call(courseClass, c)
		
		// ����Ĭ�Ͻ�ʦ
		if(!courseClass.teachers) {
			def teacherId = m[2]
			Teacher teacher = Teacher.get(teacherId)
			debug teacher
			CourseClassTeacher courseClassTeacher = new CourseClassTeacher()
			courseClassTeacher.teacher = teacher
			courseClassTeacher.courseClass = courseClass
			courseClass.addToTeachers(courseClassTeacher)
		}
		courseClass.save()
	}

	/**
	 * �γ̰��š�
	 * ���weekʡ�ԣ�Ĭ��Ϊ1-18�ܡ�
	 * <pre>
	 * arrangement(week:1..18, day:1, section:5..6, room:'room')
	 * </pre>
	 */
	private void arrangement(Map map, String teacherId = null) {
		CourseClass courseClass = parent as CourseClass
		
		Arrangement arrangement = new Arrangement()
		
		// ��ʼ������
		def week = map.week ?: 1..18 // week default value is 1..18
		arrangement.startWeek = week.from
		arrangement.endWeek = week.to		
		// ��ʼ������
		arrangement.startSection = ((Range)map.section).from
		arrangement.totalSection = ((Range)map.section).to - ((Range)map.section).from + 1
		// ����
		arrangement.room = map.room
		// �ܼ��͵�˫��
		switch(map.day) {
			case Integer:
				arrangement.dayOfWeek = map.day
				arrangement.oddEven = 0
				break;
			case Map:
				if(map.day.odd) {
					arrangement.dayOfWeek = map.day.odd
					arrangement.oddEven = 1
				} else if(map.day.even){
					arrangement.dayOfWeek = map.day.even
					arrangement.oddEven = 2
				} else {
					throw new Exception("Unsupported day format. It should be [odd:5] or [even:5]")
				}
				break;
			default :
				throw new Exception("Unsupported day format. It should be 5 or [odd:5] or [even:5]")
		}
		
		// ������������id (term)
		arrangement.setIdByProperty(courseClass.id) 
		Arrangement saved = Arrangement.get(arrangement.id)
		if(!saved) {
			arrangement.save()
		} else {
			arrangement = saved
		}
		courseClass.addToArrangements(arrangement)
		debug arrangement
	}

	/**
	 * �γ̰��ʦ��
	 * <pre>
	 * teachers('id1', 'id2', ...)
	 * </pre>
	 */
	private void teachers(Object ... arg) {
		CourseClass courseClass = parent as CourseClass
		arg.each {
			Teacher teacher = Teacher.get(it)
			debug teacher
			CourseClassTeacher courseClassTeacher = new CourseClassTeacher()
			courseClassTeacher.teacher = teacher
			courseClassTeacher.courseClass = courseClass
			courseClass.addToTeachers(courseClassTeacher)
		}
	}
	
	/**
	 * �γ̰�ѧ����
	 * <pre>
	 * students('id1', 'id2', ...)
	 * </pre>
	 */
	private void students(Object ... arg) {
		CourseClass courseClass = parent as CourseClass
		arg.each {
			Student student = Student.get(it)
			debug student
			CourseClassStudent courseClassStudent = new CourseClassStudent()
			courseClassStudent.student = student
			courseClass.addToStudents(courseClassStudent)
		}
	}
	
	private Date parseDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd')
		return sdf.parse(date)
	}

	/**
	 * ��ȡ�����
	 * @return �����
	 */
	private getParent() {
		return stack.empty ? null : stack.last()
	}

	/**
	 * ����
	 * @param message ������Ϣ
	 */
	private void debug(message) {
		def tree = indent >= 1 ? '|   '*(indent-1) + '|-- ' : ''
	}
	
	/**
	 * �����ⲿ�ļ�
	 * @param fileName �ļ���
	 */
	void load(String fileName, Binding binding = null) {
		getShell(binding).evaluate(new File(fileName))
	}

	/**
	 * ��Reader�м���
	 * @param reader Reader����
	 */
	void loadFromClassPath(String fileName, Binding binding = null) {
		def clazz = ReflectionUtils.getCallingClass(2)
		def input = clazz.getResourceAsStream(fileName)
		Reader reader = new InputStreamReader(input)
		getShell(binding).evaluate(reader)
	}

	/**
	 * ��ȡGroovyShell
	 * @return GroovyShell����
	 */
	private GroovyShell getShell(Binding inBinding) {
		// �󶨶��㺯��
		Binding bindings = new Binding([
			term: this.&term,
			department: this.&department
		])
		
		// �ϲ���
		if(inBinding)
			bindings.variables.putAll(inBinding.variables)

		return new GroovyShell(this.class.classLoader, bindings)
	}
}
