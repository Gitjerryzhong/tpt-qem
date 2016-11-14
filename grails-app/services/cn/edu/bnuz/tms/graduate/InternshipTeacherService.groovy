package cn.edu.bnuz.tms.graduate

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Term;
import cn.edu.bnuz.tms.graduate.Internship;
import cn.edu.bnuz.tms.graduate.InternshipTeacher;

class InternshipTeacherService {
	/**
	 * ��ȡָ��ѧԺ����ʵϰ��ʦ����
	 * @param departmentId ѧԺID
	 * @return ��ʦ����
	 */
	def getTeachers(String departmentId) {
		def department = Department.load(departmentId)
		def results = Internship.executeQuery '''
SELECT id, grade
FROM Internship
WHERE department = :department
ORDER by grade desc
''', [department:department]
		
		def internships = results.collect {[
			id: it[0],
			grade: it[1],
			teachers: []
		]}
		
		results = InternshipTeacher.executeQuery '''
SELECT internship.id, tt.id, teacher.id, teacher.name
FROM InternshipTeacher tt
JOIN tt.internship internship
JOIN tt.teacher teacher
WHERE internship.department = :department
ORDER BY teacher.name
''', [department:department]

		results.each {
			def tid = it[0]
			def internship = internships.find {tid == it.id}
			internship.teachers << [
				itid: it[1], // internshipTeacherId
				tid: it[2],  // TeacherId
				name: it[3]  // TeacherName
			]
		}
		return internships
	}
	
	def create(Long internshipId, String teacherIds) {
		def ids = teacherIds.split(",")
		def itids = []
		ids.each { teacherId ->
			InternshipTeacher internshipTeacher = new InternshipTeacher(
				internship: Internship.load(internshipId),
				teacher: Teacher.load(teacherId)
			)
			internshipTeacher.save(failOnError:true);
			itids << internshipTeacher.id
		}
		return itids
	}
	
	def delete(Long id) {
		InternshipTeacher internshipTeacher = InternshipTeacher.load(id)
		internshipTeacher.delete()
		return true
	}
	
	/**
	 * �жϽ�ʦĳѧ���Ƿ���ʵϰ
	 * @param teacherId ��ʦID
	 * @param term ѧ��
	 * @return �Ƿ���ʵϰ
	 */
	boolean hasInternship(String departmentId, String teacherId, Term term) {
		def results = InternshipTeacher.executeQuery '''
SELECT count(*)
FROM InternshipTeacher internshipTeacher
JOIN internshipTeacher.internship internship
JOIN internshipTeacher.teacher teacher
JOIN internship.department department
WHERE internship.materialTerm = :termId
AND teacher.id = :teacherId
AND department.id = :departmentId
''', [departmentId:departmentId, teacherId:teacherId, termId:term.id]
		return results[0] != 0
	}
	
	/**
	 * ��ȡָ��ѧԺ��ѧ�ڵ�ʵϰ��ʦ
	 * @param departmentId ѧԺID
	 * @param term ѧ��
	 * @return list of [id, name]
	 */
	def getTeachers(String departmentId, Term term) {
		Department department = Department.load(departmentId)
		
		def results = InternshipTeacher.executeQuery '''
SELECT teacher.id, teacher.name
FROM InternshipTeacher internshipTeacher
JOIN internshipTeacher.internship internship
JOIN internshipTeacher.teacher teacher
WHERE internship.materialTerm = :termId
AND internship.department = :department
''', [department:department, termId:term.id]
		
		return results.collect {[
			id : it[0],
			name : it[1]
		]}
	}
}
