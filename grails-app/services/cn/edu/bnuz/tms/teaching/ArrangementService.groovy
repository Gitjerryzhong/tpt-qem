package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.rollcall.CourseClassStudentRollcall
import cn.edu.bnuz.tms.teaching.Arrangement;
import cn.edu.bnuz.tms.teaching.CourseClassStudent;
import cn.edu.bnuz.tms.teaching.Term;


/**
 * 教学安排服务。
 * @author 杨林
 * @since 0.1
 */
class ArrangementService {
	/**
	 * 根据教学ID获取安排
	 * @param id 教学安排ID
	 * @return 教学安排
	 */
	Arrangement get(String id) {
		return Arrangement.get(id)
	}

	/**
	 * 根据教学安排ID获取所有学生
	 * @param id 教学安排ID
	 * @return 学生列表 list of [id, name, major, adminClass, status]
	 */
	def getRollcallStudents(String id) {
		def results = Arrangement.executeQuery '''
SELECT student.id, student.name, major.name as major, 
  adminClass.name as adminClass, courseClassStudent.status, ccsfa.arrangement.id,
  (SELECT count(*) 
   FROM CancelExam cancelExam
   JOIN cancelExam.course ce_course
   JOIN cancelExam.term ce_term
   JOIN cancelExam.student ce_student 
   WHERE ce_course = course
     AND ce_term = term
     AND ce_student = student
     AND cancelExam.status = 1) 
FROM Arrangement arr 
JOIN arr.courseClasses AS courseClass
JOIN courseClass.course AS course
JOIN courseClass.term as term
JOIN courseClass.students AS courseClassStudent 
JOIN courseClassStudent.student AS student 
JOIN student.major AS major
JOIN student.adminClass as adminClass
LEFT JOIN courseClassStudent.freeArrangements ccsfa WITH ccsfa.arrangement.id = :id
WHERE arr.id = :id
''', [id:id]

		def students = [];
		results.each {
			def status = it[4]
			// 免听标记
			if(it[5]) {
				status |= 1 << CourseClassStudent.FREE_LISTEN_MASK
			}
			// 取消考试资格标记
			if(it[6]) {
				status |= 1 << CourseClassStudent.CANCEL_EXAM_MASK
			}
			
			students.add([
				id : it[0],
				name : it[1],
				major : it[2],
				adminClass : it[3],
				status : status
			])
		}
		return students
	}

	/**
	 * 获取教师某学期所有安排
	 * @param teacherId 教师ID
	 * @param term 学期
	 * @return 教师某学期所有安排
	 */
	List<Arrangement> getTeacherArrangements(String teacherId, Term term) {
		Arrangement.executeQuery '''
SELECT new map(arr.id as id, arr.startWeek as startWeek, 
  arr.endWeek as endWeek, arr.dayOfWeek as dayOfWeek, 
  arr.startSection as startSection, arr.totalSection as totalSection, 
  arr.flag as flag, arr.oddEven as oddEven, arr.flag as flag, room.name as room,
  group_concat_distinct(courseClass.name) as courseClass)
FROM Arrangement arr
JOIN arr.courseClasses courseClass
JOIN courseClass.course course
JOIN arr.teacher teacher
LEFT JOIN arr.room room
WHERE teacher.id = :teacherId 
  AND courseClass.term = :term
GROUP BY arr
ORDER BY courseClass.name, arr.id
''', [teacherId:teacherId, term:term]
	}
	
	/**
	 * 获取学生某学期所有安排
	 * @param studentId 学生ID
	 * @param term 学期
	 * @return 学生某学期安排列表
	 */
	def getStudentArrangements(String studentId, Term term) {
		Arrangement.executeQuery '''
SELECT new map(arr.id as id, arr.startWeek as startWeek, arr.endWeek as endWeek, 
  arr.dayOfWeek as dayOfWeek, arr.startSection as startSection, 
  arr.totalSection as totalSection, arr.flag as flag, arr.oddEven as oddEven, 
  room.name as room, courseClass.name as courseClass)
FROM Arrangement arr
JOIN arr.room room
JOIN arr.courseClasses courseClass
JOIN courseClass.students courseClassStudent
JOIN courseClassStudent.student student
WHERE student.id = :studentId
AND courseClass.term = :term
''', [studentId:studentId, term:term]
	}
}
