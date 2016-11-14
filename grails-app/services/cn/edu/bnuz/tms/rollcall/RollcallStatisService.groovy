package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Arrangement
import cn.edu.bnuz.tms.teaching.CourseClass
import cn.edu.bnuz.tms.teaching.Term

class RollcallStatisService {
	/**
	 * 学院考勤明细
	 * @param departmentId 学院
	 * @param term 学期
	 */
	def department(String departmentId, Term term) {
		CourseClassStudentRollcall.executeQuery '''
SELECT new map(student.id as id, student.name as name, adminClass.name as adminClass, 
  sum(ccsr.absentCount) as absent,
  sum(ccsr.lateCount)   as late,
  sum(ccsr.earlyCount)  as early,
  sum(ccsr.leaveCount)  as leave,
  sum(ccsr.absentCount) * 1 + 
  sum(ccsr.lateCount)   * 0.5 + 
  sum(ccsr.earlyCount)  * 1 as total)
FROM CourseClassStudentRollcall ccsr
JOIN ccsr.courseClassStudent ccs
JOIN ccs.student student
JOIN ccs.courseClass courseClass
JOIN student.adminClass adminClass
JOIN adminClass.department department
WHERE department.id = :departmentId 
  AND courseClass.term = :term
GROUP BY student
ORDER by total desc, absent desc, late desc, early desc, leave desc
''', [departmentId:departmentId, term:term]
	}

	/**
	 * 辅导员管理班级考勤明细
	 * @param teacherId 辅导员ID
	 * @param term 学期
	 */
	def grade(String teacherId, Term term) {
		Teacher gradeTeacher = Teacher.load(teacherId)

		CourseClassStudentRollcall.executeQuery '''
SELECT new map(student.id as id, student.name as name, adminClass.name as adminClass, 
  sum(ccsr.absentCount) as absent,
  sum(ccsr.lateCount)   as late,
  sum(ccsr.earlyCount)  as early,
  sum(ccsr.leaveCount)  as leave,
  sum(ccsr.absentCount) * 1 + 
  sum(ccsr.lateCount)   * 0.5 + 
  sum(ccsr.earlyCount)  * 1 as total)
FROM CourseClassStudentRollcall ccsr
JOIN ccsr.courseClassStudent ccs
JOIN ccs.student student
JOIN ccs.courseClass courseClass
JOIN student.adminClass adminClass
WHERE adminClass.gradeTeacher = :gradeTeacher 
  AND courseClass.term = :term
GROUP BY student
ORDER by total desc, absent desc, late desc, early desc, leave desc
''', [gradeTeacher:gradeTeacher, term:term]
	}

	/**
	 * 班主任管理学生考勤明细
	 * @param teacherId 班主任ID
	 * @param term 学期
	 */
	def adminClass(String teacherId, Term term) {
		Teacher adminTeacher = Teacher.load(teacherId)

		CourseClassStudentRollcall.executeQuery '''
SELECT new map(student.id as id, student.name as name, adminClass.name as adminClass, 
  sum(ccsr.absentCount) as absent,
  sum(ccsr.lateCount)   as late,
  sum(ccsr.earlyCount)  as early,
  sum(ccsr.leaveCount)  as leave,
  sum(ccsr.absentCount) * 1 + 
  sum(ccsr.lateCount)   * 0.5 + 
  sum(ccsr.earlyCount)  * 1 as total)
FROM CourseClassStudentRollcall ccsr
JOIN ccsr.courseClassStudent ccs
JOIN ccs.student student
JOIN ccs.courseClass courseClass
JOIN student.adminClass adminClass
WHERE adminClass.adminTeacher = :adminTeacher 
  AND courseClass.term = :term
GROUP BY student
ORDER by total desc, absent desc, late desc, early desc, leave desc
''', [adminTeacher:adminTeacher, term:term]
	}
	
	/**
	 * 任课教师管理学生考勤明细
	 * @param teacherId 教师ID
	 * @param term 学期
	 */
	def courseClass(String courseClassId) {
		Iterable ccsr = CourseClassStudentRollcall.executeQuery '''
SELECT new map(student.id as id, 
  sum(ccsr.absentCount) as absent,
  sum(ccsr.lateCount)   as late,
  sum(ccsr.earlyCount)  as early,
  sum(ccsr.leaveCount)  as leave,
  sum(ccsr.absentCount) * 1 + 
  sum(ccsr.lateCount)   * 0.5 + 
  sum(ccsr.earlyCount)  * 1 as total)
FROM CourseClassStudentRollcall ccsr
JOIN ccsr.courseClassStudent ccs
JOIN ccs.student student
JOIN ccs.courseClass cc
JOIN cc.teachers courseClassTeacher
JOIN ccs.courseClass courseClass
WHERE courseClass.id = :courseClassId
OR (substr(courseClass.id, length(courseClass.id), 1) between 'A' and 'Z' 
AND substring(courseClass.id, 1, length(courseClass.id) -1) = :courseClassId)
GROUP BY student
''', [courseClassId: courseClassId]
		def map = ccsr.collectEntries() {
			[it.id, it]
		}
		
		def results = CourseClass.executeQuery '''
SELECT new map(student.id as id,
  student.name as name,
  adminClass.name as adminClass,
  0 as absent, 0 as late, 0 as early, 0 as leave, 0 as total)
FROM CourseClassStudent ccs
JOIN ccs.student student
JOIN student.adminClass adminClass
JOIn ccs.courseClass courseClass 
WHERE courseClass.id = :courseClassId 
ORDER BY student.id
''',  [courseClassId:courseClassId]
		results.each {
			def statis = map[it.id] 
			if(statis) {
				it.absent = statis.absent
				it.late = statis.late
				it.earyly = statis.early
				it.leave = statis.leave
				it.total = statis.total
			}
		}
		
		return results
	}
	
	/**
	 * 按年级、专业、行政班汇总考勤数据
	 * @param departemntId 学院ID
	 */
	def gradeMajorAdminClass(String departmentId, Term term) {
		def results = CourseClassStudentRollcall.executeQuery '''
SELECT student.grade, major.name, adminClass.name, 
  sum(ccsr.absentCount) as absentCount,
  sum(ccsr.lateCount) as lateCount,
  sum(ccsr.earlyCount) as earlyCount,
  sum(ccsr.leaveCount) as leaveCount,
  (select count(*) from Student s where s.adminClass = adminClass) as studentCount
FROM CourseClassStudentRollcall ccsr
JOIN ccsr.courseClassStudent ccs
JOIN ccs.student student
JOIN ccs.courseClass courseClass
JOIN student.adminClass adminClass
JOIN student.major major
JOIN adminClass.department department
WHERE department.id = :departmentId 
  AND courseClass.term = :term
GROUP BY student.grade, major, adminClass
''', [departmentId:departmentId, term:term]

		results.collect {[
			grade      : it[0],
			major      : it[1],
			adminClass : it[2],
			statis     : [it[3],it[4], it[5], it[6]],
			studentCount: it[7]
		]}
	}

	/**
	 * 学生考勤统计
	 * @param studentId 学生ID
	 * @param term 学期
	 * @return
	 */
	def studentRollcallItems(String studentId, Term term) {
		Student student = Student.load(studentId)

		RollcallItem.executeQuery '''
SELECT new map(rollcallItem.type as type,
  rollcallForm.week as week, 
  arrangement.dayOfWeek as dayOfWeek, 
  arrangement.startSection as startSection,
  arrangement.totalSection as totalSection,
  courseClass.name as courseClass,
rollcallForm.teacher.name as teacher)
FROM RollcallItem rollcallItem
JOIN rollcallItem.form rollcallForm
JOIN rollcallForm.arrangement arrangement
JOIN arrangement.courseClasses courseClass
JOIN courseClass.students courseClassStudent
WHERE courseClassStudent.student = :student
 AND rollcallItem.student = :student
 AND courseClass.term = :term
 AND rollcallItem.type != 6
''', [student:student, term:term]
	}
	
	/**
	 * 学生考勤统计
	 * @param studentId 学生ID
	 * @param courseClassId 教学班ID
	 * @return
	 */
	def studentRollcallItems(String studentId, String courseClassId) {
		Student student = Student.load(studentId)

		RollcallItem.executeQuery '''
SELECT new map(rollcallItem.type as type,
  rollcallForm.week as week, 
  arrangement.dayOfWeek as dayOfWeek, 
  arrangement.startSection as startSection,
  arrangement.totalSection as totalSection,
  courseClass.name as courseClass,
rollcallForm.teacher.name as teacher)
FROM RollcallItem rollcallItem
JOIN rollcallItem.form rollcallForm
JOIN rollcallForm.arrangement arrangement
JOIN arrangement.courseClasses courseClass
JOIN courseClass.students courseClassStudent
WHERE courseClassStudent.student = :student
 AND rollcallItem.student = :student
 AND (courseClass.id = :courseClassId
OR substr(courseClass.id, length(courseClass.id), 1) between 'A' and 'Z' 
AND substring(courseClass.id, 1, length(courseClass.id) -1) = :courseClassId)
 AND rollcallItem.type != 6
''', [student:student, courseClassId:courseClassId]
	}
	
	/**
	 * 按课程统计学生考勤
	 * @param studentId 学生ID
	 * @param term 学期
	 * @return
	 */
	def studentCoursesStatis(String studentId, Term term) {
		Student student = Student.load(studentId)
		
		RollcallItem.executeQuery '''
SELECT new map(course.name as course, (
    SELECT (max(cc.endWeek) - min(cc.startWeek) + 1) * (max(cc.theoryWeekHours) + max(cc.practiceWeekHours))
    FROM CourseClass cc
	JOIN cc.students ccs
    WHERE cc.course = course
    AND ccs.student = rollcallItem.student
  ) as hours, (
  SUM((CASE rollcallItem.type WHEN 1 THEN 1 ELSE 0 END) * arrangement.totalSection) +
  SUM((CASE rollcallItem.type WHEN 2 THEN 1 ELSE 0 END) * arrangement.totalSection) * 0.5 +
  SUM((CASE rollcallItem.type WHEN 3 THEN 1 ELSE 0 END) * arrangement.totalSection) +
  SUM((CASE rollcallItem.type WHEN 5 THEN 1 ELSE 0 END) * arrangement.totalSection)
  ) as statis)
FROM RollcallItem rollcallItem
JOIN rollcallItem.form rollcallForm
JOIN rollcallForm.arrangement arrangement
JOIN arrangement.courseClasses courseClass
JOIN courseClass.students courseClassStudent
JOIN courseClass.course course
WHERE courseClassStudent.student = :student
 AND rollcallItem.student = :student
 AND courseClass.term = :term
 AND rollcallItem.type != 6
 AND rollcallItem.type > 0
GROUP BY course
''', [student:student, term:term]
	}
	
	/**
	 * 学生请假情况
	 * @param studentId 学生ID
	 * @param term 学期
	 * @return
	 */
	def studentLeaveItems(String studentId, Term term) {
		Student student = Student.load(studentId)
		def leaveItems = []
		// 周
		def results = LeaveItem.executeQuery '''
SELECT new map(leaveRequest.type as type, leaveItem.week as week)
FROM LeaveItem leaveItem
JOIN leaveItem.leaveRequest leaveRequest
WHERE leaveRequest.student = :student
AND leaveRequest.term = :term
AND leaveRequest.status in (2, 4)
AND leaveItem.dayOfWeek is NULL AND leaveItem.arrangement is NULL
''', [student: student, term: term]
		leaveItems.addAll results

		// 天
		results = LeaveItem.executeQuery '''
SELECT new map(leaveRequest.type as type, leaveItem.week as week, 
  leaveItem.dayOfWeek as dayOfWeek)
FROM LeaveItem leaveItem
JOIN leaveItem.leaveRequest leaveRequest
WHERE leaveRequest.student = :student
AND leaveRequest.term = :term
AND leaveRequest.status in (2, 4)
AND leaveItem.dayOfWeek is NOT NULL
''', [student: student, term: term]
		leaveItems.addAll results

		// 安排
		results = LeaveItem.executeQuery '''
SELECT new map(leaveRequest.type as type, leaveItem.week as week, 
  arrangement.dayOfWeek as dayOfWeek, 
  arrangement.startSection as startSection, 
  arrangement.totalSection as totalSection, 
  courseClass.name as courseClass)
FROM LeaveItem leaveItem
JOIN leaveItem.leaveRequest leaveRequest
JOIN leaveItem.arrangement arrangement
JOIN arrangement.courseClasses courseClass
JOIN courseClass.students courseClassStudent
WHERE leaveRequest.student = :student
AND leaveRequest.term = :term
AND leaveRequest.status in (2, 4)
AND leaveItem.arrangement is NOT NULL
AND courseClassStudent.student = :student
''', [student: student, term: term]
		leaveItems.addAll results
		
		return leaveItems;
	}

	/**
	 * 查询指定开课学院教师的点名情况
	 * @param departmentId
	 * @param term
	 * @return
	 */
	def teachers(String departmentId, Term term) {
		// 计算教师每周安排数量，用于前台计算每周几次课
		def results = Arrangement.executeQuery '''
SELECT teacher.id, teacher.name, arrangement.startWeek, arrangement.endWeek, arrangement.oddEven, count(distinct arrangement.id)
FROM Arrangement arrangement
JOIN arrangement.courseClasses courseClass
JOIN arrangement.teacher teacher
WHERE courseClass.department.id = :departmentId
AND courseClass.term = :term
GROUP BY teacher.id, teacher.name, arrangement.startWeek, arrangement.endWeek, arrangement.oddEven
ORDER BY teacher.name
''', [departmentId:departmentId, term:term]

		def teachers = [:]
		//id : {name:'name', arrangements:[[start, end, oddEven, count]*]}
		results.each {
			def id = it[0]
			if(teachers[id]) {
				teachers[id].arrs << [it[2], it[3], it[4], it[5]]
			} else {
				teachers[id] = [
					name: it[1],
					arrs: [[it[2], it[3], it[4], it[5]]],
				]
			}
		}

		// 查询课程点名情况
		results = RollcallForm.executeQuery '''
SELECT form.teacher.id, form.week, count(distinct arrangement.id)
FROM RollcallForm form
JOIN form.arrangement arrangement
JOIN arrangement.courseClasses courseClass
WHERE courseClass.department.id = :departmentId
AND courseClass.term = :term
AND form.week <= :week
GROUP BY form.teacher, form.week
''', [departmentId:departmentId, term:term, week: term.currentWeek]

		results.each {
			if(!teachers[it[0]].rcws) {
				teachers[it[0]].rcws = [:];
			}
			teachers[it[0]].rcws[it[1]] = it[2]
		}

		return teachers
	}

	/**
	 * 查询某教师某周的点名情况。列出所有安排，如果数据rollcall为null，表示未点名
	 * @param departmentId 开课学院ID
	 * @param teacherId 教师ID
	 * @param week 周次
	 * @param term 学期
	 * @return [dayOfWeek, startSection, totalSecion, courseClasses, rollcall?]*
	 */
	def teacher(String departmentId, String teacherId, int week, Term term) {
		Teacher teacher = Teacher.load(teacherId)

		Arrangement.executeQuery '''
SELECT new map(
  arrangement.dayOfWeek as dayOfWeek, 
  arrangement.startSection as startSection, 
  arrangement.totalSection as totalSection,
  arrangement.flag as flag,
  group_concat_distinct(courseClass.name) as courseClass, 
  rollcallForm.id as rollcall)
FROM Arrangement arrangement
JOIN arrangement.courseClasses courseClass
LEFT JOIN arrangement.rollcallForms rollcallForm WITH rollcallForm.week = :week
WHERE arrangement.teacher = :teacher
AND (arrangement.oddEven = 0 OR arrangement.oddEven = :oddEven)
AND :week between arrangement.startWeek AND arrangement.endWeek
AND courseClass.department.id = :departmentId
AND courseClass.term = :term
GROUP BY arrangement.id
ORDER BY arrangement.dayOfWeek, arrangement.startSection
''', [departmentId: departmentId, teacher:teacher, week:week, oddEven: week % 2 ? 1 : 2, term:term]
	}

	/**
	 * 获取指定教师ID的点名情况，用于教师查看自己的考勤情况
	 * @param teacherId 教师ID
	 * @param term 学期
	 * @return [arrangementId, week]*
	 */
	def teacher( String teacherId, Term term) {
		RollcallForm.executeQuery '''
SELECT distinct new map(arrangement.id as id, rollcallForm.week as week)
FROM RollcallForm rollcallForm
JOIN rollcallForm.arrangement arrangement
JOIN arrangement.courseClasses courseClass
WHERE courseClass.term = :term
AND rollcallForm.teacher.id = :teacherId
''', [teacherId:teacherId, term:term]
	}

	/**
	 * 根据教学安排ID获取所有考勤情况，用于Excel导出
	 * @param id 教学安排ID
	 * @return
	 *  [ 
	 *  	arrangemant:arrangement, 
	 *    	students: list of [studentId, name, major],
	 *    	rollcallItems: list of [studentId, week, type],
	 *    	leaveItems: list of [studentId, week, type]
	 *  ]
	 *    
	 */
	def getArrangementRollcall(String id) {
		def arrangement = Arrangement.executeQuery('''
SELECT new map(
  arr.id as arrangementId,
  group_concat_distinct(courseClass.id) as courseClassId, 
  group_concat_distinct(courseClass.name) as courseClassName, 
  group_concat_distinct(teacher.name) as teacher,
  group_concat_distinct(department.name) as department, 
  arr.dayOfWeek as dayOfWeek, 
  arr.startSection as startSection, 
  arr.totalSection as totalSection, 
  arr.oddEven as oddEven, 
  room.name as room)
FROM Arrangement arr
JOIN arr.courseClasses courseClass
JOIN arr.teacher teacher
JOIN teacher.department department
LEFT JOIN arr.room room
WHERE arr.id = :id
group by arr
''', [id:id])[0]

		def students = Arrangement.executeQuery '''
SELECT new map(student.id as id, student.name as name, major.name as major)
FROM Arrangement arr 
JOIN arr.courseClasses AS courseClass 
JOIN courseClass.students AS courseClassStudent 
JOIN courseClassStudent.student AS student 
JOIN student.major AS major
WHERE arr.id = :id
''', [id:id]

		def rollcallItems = RollcallItem.executeQuery '''
SELECT new map(rollcallItem.student.id as id, rollcallForm.week as week, rollcallItem.type as type)
FROM RollcallForm rollcallForm
JOIN rollcallForm.items rollcallItem
WHERE rollcallForm.arrangement.id = :id 
AND rollcallItem.type BETWEEN 1 AND 5
''', [id:id]

		def leaveItems = LeaveRequest.executeQuery '''
SELECT new map(leaveRequest.student.id as id, leaveItem.week as week, leaveRequest.type as type) 
FROM LeaveRequest leaveRequest 
JOIN leaveRequest.items leaveItem 
WHERE leaveRequest.status in (2, 4) 
  AND leaveRequest.term = (SELECT DISTINCT term 
    FROM Arrangement arrangement 
    JOIN arrangement.courseClasses courseClass 
    JOIN courseClass.term term
    WHERE arrangement.id = :id) 
  AND ( 
    leaveItem.arrangement.id = :id OR ( 
      leaveRequest.student in (SELECT student 
	    FROM Arrangement arrangement 
	    JOIN arrangement.courseClasses courseClass 
	    JOIN courseClass.students courseClassStudent 
	    JOIN courseClassStudent.student student 
	    WHERE arrangement.id = :id ) 
      AND (( 
        leaveItem.dayOfWeek is not null AND 
	    leaveItem.dayOfWeek = (SELECT arr.dayOfWeek FROM Arrangement arr WHERE arr.id=:id) 
      ) OR ( 
	    leaveItem.dayOfWeek is null AND 
		leaveItem.arrangement is null 
	  )) 
    ) 
  )''', [id: id]
		
		return [
			arrangement:   arrangement,
			students:      students,
			rollcallItems: rollcallItems,
			leaveItems:    leaveItems
		]
	}
}
