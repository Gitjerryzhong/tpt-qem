package cn.edu.bnuz.tms.rollcall

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.CancelExam
import cn.edu.bnuz.tms.teaching.Course
import cn.edu.bnuz.tms.teaching.CourseClassStudent
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.rollcall.RollcallItem;

class CancelExamService {
	def getCancelExamCandidates(String departmentId, Term term) {
		def department = Department.load(departmentId)
		
		def results = RollcallItem.executeQuery '''
SELECT student.id, student.name, adminClass.name, course.id, course.name, group_concat_distinct(teacher.name), 
 (SELECT (max(cc.endWeek) - min(cc.startWeek) + 1) * (max(cc.theoryWeekHours) + max(cc.practiceWeekHours))
  FROM CourseClass cc
  JOIN cc.students ccs
  WHERE cc.course = course
    AND ccs.student = student),
  SUM((CASE rollcallItem.type WHEN 1 THEN 1 ELSE 0 END) * arrangement.totalSection) + 
  SUM((CASE rollcallItem.type WHEN 2 THEN 1 WHEN 5 THEN 1 ELSE 0 END) * arrangement.totalSection) * 0.5 +
  SUM((CASE rollcallItem.type WHEN 3 THEN 1 WHEN 5 THEN 1 ELSE 0 END) * arrangement.totalSection),
 (SELECT cancelExam.status 
  FROM CancelExam cancelExam
  WHERE cancelExam.term = courseClass.term
    AND cancelExam.course = course
    AND cancelExam.student = student)
FROM RollcallItem rollcallItem
JOIN rollcallItem.student student
JOIN student.adminClass adminClass
JOIN rollcallItem.form rollcallForm
JOIN rollcallForm.arrangement arrangement
JOIN arrangement.courseClasses courseClass
JOIN arrangement.teacher teacher
JOIN courseClass.students courseClassStudent
JOIN courseClass.course course
WHERE courseClassStudent.student = rollcallItem.student
 AND courseClass.term = :term
 AND rollcallItem.type != 6
 AND rollcallItem.type > 0
 AND adminClass.department = :department
 AND NOT EXISTS (FROM CancelExam cancelExam
   WHERE cancelExam.term = courseClass.term
     AND cancelExam.course = course
     AND cancelExam.student = student
     AND cancelExam.status = 1)
GROUP BY student, course
HAVING col_7_0_ / col_6_0_ >= 1 / 9
ORDER BY col_7_0_ desc
''', [department:department, term:term]

		results.collect{ [
			id         : it[0],
			name       : it[1],
			adminClass : it[2],
			courseId   : it[3],
			courseName : it[4],
			teacher    : it[5],
			hours      : it[6],
			absent     : it[7],
			status     : it[8]
		]}
	}
	
	def getCancelExams(String departmentId, Term term) {
		def department = Department.load(departmentId)
		
		def results = CancelExam.executeQuery '''
SELECT student.id, student.name, adminClass.name, course.id, course.name,
creator.name, cancelExam.dateCreated, 
updater.name, cancelExam.lastUpdated,
cancelExam.status
FROM CancelExam cancelExam
JOIN cancelExam.course course
JOIN cancelExam.student student
JOIN student.adminClass adminClass
JOIN cancelExam.creator creator
LEFT JOIN cancelExam.updater updater
WHERE cancelExam.term = :term
  AND adminClass.department = :department
ORDER BY cancelExam.lastUpdated DESC
''', [department:department, term:term]
 
		results.collect{ [
			id          : it[0],
			name        : it[1],
			adminClass  : it[2],
			courseId    : it[3],
			courseName  : it[4],
			creator     : it[5],
			dateCreated : it[6],
			updater     : it[7]?:"",
			lastUpdated : it[8],
			status      : it[9]
		]}
	}
	
	def getRollcallItems(String studentId, String courseId, Term term) {
		Student student = Student.load(studentId)
		Course course = Course.load(courseId)
		// ¿¼ÇÚÃ÷Ï¸
		def results = RollcallItem.executeQuery '''
SELECT rollcallItem.type,
rollcallForm.week, 
arrangement.dayOfWeek, 
arrangement.startSection,
arrangement.totalSection,
courseClass.name,
rollcallForm.teacher.name
FROM RollcallItem rollcallItem
JOIN rollcallItem.form rollcallForm
JOIN rollcallForm.arrangement arrangement
JOIN arrangement.courseClasses courseClass
JOIN courseClass.students courseClassStudent
WHERE courseClassStudent.student = :student
 AND rollcallItem.student = :student
 AND courseClass.term = :term
 AND rollcallItem.type != 6
 AND rollcallItem.type > 0
 AND courseClass.course = :course
''', [student:student, course:course, term:term]

		results.collect {[
			type         : it[0],
			week         : it[1],
			dayOfWeek    : it[2],
			startSection : it[3],
			totalSection : it[4],
			courseClass  : it[5],
			teacher      : it[6],
		]}
	}
	
	def cancelExam(String teacherId, String studentId, String courseId, Term term) {
		def cancelExam = CancelExam.get( new CancelExam(
			term: term,
			course: Course.load(courseId),
			student: Student.load(studentId)
		))
		if(cancelExam) {
			cancelExam.updater = Teacher.load(teacherId)
			cancelExam.status = 1
		} else {
			cancelExam = new CancelExam(
				term: term,
				course: Course.load(courseId),
				student: Student.load(studentId),
				creator: Teacher.load(teacherId),
				status: 1
			)
		}
		cancelExam.save(flush:true, failOnError:true)
	}
	
	def revokeExam(String teacherId, String studentId, String courseId, Term term) {
		def cancelExam = CancelExam.get( new CancelExam(
			term: term,
			course: Course.load(courseId),
			student: Student.load(studentId)
		))
		cancelExam.updater = Teacher.load(teacherId)
		cancelExam.status = 0

		cancelExam.save(flush:true, failOnError:true)
	}
}
