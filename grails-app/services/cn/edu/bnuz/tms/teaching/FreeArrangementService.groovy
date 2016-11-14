package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.teaching.Arrangement;
import cn.edu.bnuz.tms.teaching.CourseClass;
import cn.edu.bnuz.tms.teaching.CourseClassStudent;
import cn.edu.bnuz.tms.teaching.CourseClassStudentFreeArrangement;
import cn.edu.bnuz.tms.teaching.Term;

/**
 * 免听服务。
 * @author 杨林
 * @version 0.1
 * @since 0.1
 */
class FreeArrangementService {
	/**
	 * 查找指定教师和学期的所有免听学生
	 * @param teacherId 教师ID
	 * @param term 学期
	 * @return [arrangementId, id, name]*
	 */
	def getStudents(String teacherId, Term term) {
		CourseClass.executeQuery '''
SELECT new map(arr.id as arrangementId, student.id as id, student.name as name)
FROM CourseClassStudentFreeArrangement ccsf
JOIN ccsf.courseClassStudent ccs
JOIN ccs.student student
JOIN ccs.courseClass cc
JOIN ccsf.arrangement arr
WHERE arr.teacher.id = :teacherId
AND cc.term = :term
''',[teacherId: teacherId, term: term]
	}
	
	def getStudentInfo(String studentId) {
		Student.executeQuery '''
SELECT new map(
  student.id as id, 
  student.name as name, 
  student.adminClass.name as adminClass, 
  student.major.name as major)
FROM Student student
WHERE student.id = :studentId
''', [studentId: studentId]
	}
	
	/**
	 * 查找学生的免听记录
	 * @param studentId 学生ID
	 * @param term 学期
	 * @return 免听数据  arrangement.id*
	 */
	def getStudentFreeArrangements(String studentId, Term term) {
		CourseClass.executeQuery '''
SELECT arrangement.id
FROM CourseClass courseClass
JOIN courseClass.students courseClassStudent
JOIN courseClassStudent.freeArrangements freeArrangement
JOIN freeArrangement.arrangement arrangement
WHERE courseClassStudent.student.id = :studentId
AND courseClass.term = :term
''',[studentId: studentId, term: term]
	}

	/**
	 * 查找学生与该教师相关课程的免听记录
	 * @param teacherId 教师ID
	 * @param studentId 学生ID
	 * @param term 学期
	 * @return 免听数据  arrangement.id*
	 */
	def getStudentFreeArrangementsByTeacher(String studentId, String teacherId, Term term) {
		CourseClass.executeQuery '''
SELECT new list(arrangement.id)
FROM CourseClass courseClass
JOIN courseClass.students courseClassStudent
JOIN courseClassStudent.freeArrangements freeArrangement
JOIN freeArrangement.arrangement arrangement
WHERE arrangement.teacher.id = :teacherId
AND courseClassStudent.student.id = :studentId
AND courseClass.term = :term
''',[teacherId: teacherId, studentId: studentId, term: term]
	}

	def insertFreeArrangement(String studentId, String arrangementId) {
		def result = CourseClassStudent.executeQuery """
SELECT ccs from CourseClassStudent ccs
JOIN ccs.courseClass cc
JOIN cc.arrangements arr
WHERE ccs.student.id = :studentId
AND arr.id = :arrangementId
""", [studentId: studentId, arrangementId: arrangementId]
		if(result.size() == 0) {
			return null
		}
		
		CourseClassStudent courseClassStudent = result[0]
		
		def freeArrangement = new CourseClassStudentFreeArrangement(
				courseClassStudent: courseClassStudent,
				arrangement: Arrangement.load(arrangementId)
		)
		courseClassStudent.addToFreeArrangements(freeArrangement)
		courseClassStudent.save(failOnError:true);
	}

	def deleteFreeArrangement(String studentId, String arrangementId) {
		def result = CourseClassStudent.executeQuery """
SELECT ccs from CourseClassStudent ccs
JOIN ccs.courseClass cc
JOIN cc.arrangements arr
WHERE ccs.student.id = :studentId
AND arr.id = :arrangementId
""", [studentId: studentId, arrangementId: arrangementId]
		if(result.size() == 0) {
			return null
		}
		
		CourseClassStudent courseClassStudent = result[0]

		// 删除免听记录
		CourseClassStudentFreeArrangement.executeUpdate '''
DELETE CourseClassStudentFreeArrangement ccsfa
WHERE ccsfa.courseClassStudent = :courseClassStudent
AND arrangement = :arrangement
''', [courseClassStudent: courseClassStudent, arrangement: Arrangement.load(arrangementId)]
	}
}
