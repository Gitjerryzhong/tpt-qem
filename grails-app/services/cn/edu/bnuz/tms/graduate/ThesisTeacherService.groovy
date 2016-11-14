package cn.edu.bnuz.tms.graduate

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.graduate.InternshipTeacher;
import cn.edu.bnuz.tms.graduate.Thesis;
import cn.edu.bnuz.tms.graduate.ThesisTeacher;

class ThesisTeacherService {
	/**
	 * 获取指定学院所有论文教师安排
	 * @param departmentId 学院ID
	 * @return 老师安排
	 */
	def getTeachers(String departmentId) {
		def department = Department.load(departmentId)
		def results = Thesis.executeQuery '''
SELECT id, grade
FROM Thesis
WHERE department = :department
ORDER by grade desc
''', [department:department]
		
		def theses = results.collect {[
			id: it[0], 
			grade: it[1], 
			teachers: []
		]}
		
		results = ThesisTeacher.executeQuery '''
SELECT thesis.id, tt.id, teacher.id, teacher.name
FROM ThesisTeacher tt
JOIN tt.thesis thesis
JOIN tt.teacher teacher
WHERE thesis.department = :department
ORDER BY teacher.name
''', [department:department]

		results.each {
			def tid = it[0]
			def thesis = theses.find {tid == it.id}
			thesis.teachers << [
				ttid: it[1], // ThesisTeacherId
				tid: it[2],  // TeacherId
				name: it[3]  // TeacherName
			]
		}
		return theses
	}
	
	def create(Long thesisId, String teacherIds) {
		def ids = teacherIds.split(",")
		def ttids = []
		ids.each { teacherId ->
			ThesisTeacher thesisTeacher = new ThesisTeacher(
				thesis: Thesis.load(thesisId),
				teacher: Teacher.load(teacherId)  
			)
			thesisTeacher.save(failOnError:true);
			ttids << thesisTeacher.id
		}
		return ttids
	}
	
	def delete(Long id) {
		ThesisTeacher thesisTeacher = ThesisTeacher.load(id)
		thesisTeacher.delete()
		return true
	}
	
	/**
	 * 判断教师某学期是否安排论文
	 * @param teacherId 教师ID
	 * @param term 学期
	 * @return 是否安排论文
	 */
	boolean hasThesis(String departmentId, String teacherId, Term term) {
		def results = InternshipTeacher.executeQuery '''
SELECT count(*)
FROM ThesisTeacher thesisTeacher
JOIN thesisTeacher.thesis thesis
JOIN thesisTeacher.teacher teacher
JOIN thesis.department department
WHERE thesis.materialTerm = :termId
AND teacher.id = :teacherId
AND department.id = :departmentId
''', [departmentId:departmentId, teacherId:teacherId, termId:term.id]
		return results[0] != 0
	}
	
	/**
	 * 获取指定学院和学期的论文教师
	 * @param departmentId 学院ID
	 * @param term 学期
	 * @return list of [id, name]
	 */
	def getTeachers(String departmentId, Term term) {
		Department department = Department.load(departmentId)
		
		def results = ThesisTeacher.executeQuery '''
SELECT teacher.id, teacher.name
FROM ThesisTeacher thesisTeacher
JOIN thesisTeacher.thesis thesis
JOIN thesisTeacher.teacher teacher
WHERE thesis.materialTerm = :termId
AND thesis.department = :department
''', [department:department, termId:term.id]
		
		return results.collect {[
			id : it[0],
			name : it[1]
		]}
	}
}
