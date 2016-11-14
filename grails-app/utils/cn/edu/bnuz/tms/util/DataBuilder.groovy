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
	
	// 缩进，调试输出使用
	private int indent = 0

	// 对象栈，使用parent获取栈顶对象，表示当前对象的父对象
	private List stack = []

	// 行政班缺省专业
	private Major defaultMajor

	/**
	 * 调用闭包。将当前结点压栈，作为下级结点的父结点，可通过getParent获得
	 * @param current 当前结点
	 * @param c 当前结点的闭包（下级结点）
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
	 * 学期。
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
	 * 学院。
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
	 * 专业。
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
	 * 教师。
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
	 * 行政班。
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
	 * 替换map中教师编号为Teacher对象
	 */
	private void replaceNumToTeacher(Department department, Map map, String key) {
		if(map.containsKey(key)) {
			map[key] = department.teachers.find {it.id == map[key]}
		}
	}

	/**
	 * 学生。如果未指定专业，使用AdminClass的defaultMajor。
	 * <pre>
	 * student 'id', name:'name', password:'password', ...
	 * </pre>
	 */
	private void student(Map map, String id) {
		AdminClass adminClass = parent as AdminClass
		replaceNumToMajor adminClass, map

		// 缺省密码为学生后4位
		map.password = map.password ?: id[-4..-1]
		
		Student student = new Student(map)
		student.id = id
		student.enabled = true
		adminClass.addToStudents student
		student.save()

		debug student
	}

	/**
	 * 替换map中专业编号为Major对象
	 */
	private void replaceNumToMajor(AdminClass adminClass, Map map) {
		if(map.containsKey(KEY_MAJOR)) {
			map[KEY_MAJOR] = adminClass.department.majors.find {it.id == map[KEY_MAJOR]}
		} else {
			map[KEY_MAJOR] = defaultMajor
		}
	}

	/**
	 * 课程。
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
	 * 课程班。
	 * <pre>
	 * courseClass('id', name:'name', ...) {
	 *     arrangement(...)
	 *     arrangement(...)
	 *     ...
	 *     teachers(...) // 默认为课程ID中的教师
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
		
		// 处理默认教师
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
	 * 课程安排。
	 * 如果week省略，默认为1-18周。
	 * <pre>
	 * arrangement(week:1..18, day:1, section:5..6, room:'room')
	 * </pre>
	 */
	private void arrangement(Map map, String teacherId = null) {
		CourseClass courseClass = parent as CourseClass
		
		Arrangement arrangement = new Arrangement()
		
		// 开始结束周
		def week = map.week ?: 1..18 // week default value is 1..18
		arrangement.startWeek = week.from
		arrangement.endWeek = week.to		
		// 开始结束节
		arrangement.startSection = ((Range)map.section).from
		arrangement.totalSection = ((Range)map.section).to - ((Range)map.section).from + 1
		// 教室
		arrangement.room = map.room
		// 周几和单双周
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
		
		// 根据属性设置id (term)
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
	 * 课程班教师。
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
	 * 课程班学生。
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
	 * 获取父结点
	 * @return 父结点
	 */
	private getParent() {
		return stack.empty ? null : stack.last()
	}

	/**
	 * 调试
	 * @param message 调试消息
	 */
	private void debug(message) {
		def tree = indent >= 1 ? '|   '*(indent-1) + '|-- ' : ''
	}
	
	/**
	 * 加载外部文件
	 * @param fileName 文件名
	 */
	void load(String fileName, Binding binding = null) {
		getShell(binding).evaluate(new File(fileName))
	}

	/**
	 * 从Reader中加载
	 * @param reader Reader对象
	 */
	void loadFromClassPath(String fileName, Binding binding = null) {
		def clazz = ReflectionUtils.getCallingClass(2)
		def input = clazz.getResourceAsStream(fileName)
		Reader reader = new InputStreamReader(input)
		getShell(binding).evaluate(reader)
	}

	/**
	 * 获取GroovyShell
	 * @return GroovyShell对象
	 */
	private GroovyShell getShell(Binding inBinding) {
		// 绑定顶层函数
		Binding bindings = new Binding([
			term: this.&term,
			department: this.&department
		])
		
		// 合并绑定
		if(inBinding)
			bindings.variables.putAll(inBinding.variables)

		return new GroovyShell(this.class.classLoader, bindings)
	}
}
