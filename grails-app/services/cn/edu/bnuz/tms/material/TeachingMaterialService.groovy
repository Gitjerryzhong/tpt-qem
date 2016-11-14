package cn.edu.bnuz.tms.material

import cn.edu.bnuz.tms.graduate.InternshipTeacher
import cn.edu.bnuz.tms.graduate.InternshipTeacherService
import cn.edu.bnuz.tms.graduate.ThesisTeacherService
import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Course
import cn.edu.bnuz.tms.teaching.CourseClass
import cn.edu.bnuz.tms.teaching.Term
import cn.edu.bnuz.tms.material.Material;
import cn.edu.bnuz.tms.material.TeachingMaterial;
import cn.edu.bnuz.tms.material.TermMaterial;

class TeachingMaterialService {
	ThesisTeacherService thesisTeacherService
	InternshipTeacherService internshipTeacherService
	TermMaterialService termMaterialService
	
	def getTeachers(String departmentId, Term term) {
		def department = Department.load(departmentId)
		def results = TermMaterial.executeQuery '''
SELECT material.type, count(*)
FROM TermMaterial termMaterial
JOIN termMaterial.material material
JOIN material.category category
WHERE termMaterial.term = :term
AND termMaterial.department = :department
AND category.type = 1
GROUP by material.type
ORDER BY material.type
''', [department:department, term:term]
		def counts = [0:0, 1:0, 2:0]
		results.each{
			counts[it[0]] = it[1]
		}
		
		// 课程材料
		results = CourseClass.executeQuery '''
SELECT DISTINCT teacher.id, teacher.name, 
  count(distinct courseClass),
  count(distinct course), 
  (SELECT count(*) FROM TeachingMaterial teachingMaterial
   JOIN teachingMaterial.termMaterial termMaterial
   WHERE termMaterial.term = :term
   AND teachingMaterial.teacher = teacher
   AND status = 1) as commitCount,
  (SELECT count(*) FROM TeachingMaterial teachingMaterial
   JOIN teachingMaterial.termMaterial termMaterial
   WHERE termMaterial.term = :term
   AND teachingMaterial.teacher = teacher
   AND status = 2) as freeCount
FROM CourseClass courseClass
JOIN courseClass.course course
JOIN courseClass.teachers courseClassTeacher
JOIN courseClassTeacher.teacher teacher
WHERE courseClass.department = :department
AND courseClass.term = :term
AND substring(courseClass.id, -1) NOT BETWEEN 'A' AND 'Z'
GROUP BY teacher
ORDER BY teacher.id
''', [department:department, term:term]
		def teachers = []
		def map = [:]
		results.each {
			def teacher = [
				id        : it[0],
				name      : it[1],
				total     : counts[0] + counts[1] * it[2] + counts[2] * it[3], 
				commit    : it[4],
				free      : it[5],
				thesis    : false,
				internship: false
			]
			
			teachers << teacher
			map[it[0]] = teacher
		}
		
		// 论文材料
		def thesisCount = termMaterialService.getCountByCategory(department, term, 5);
		def thesisTeachers = thesisTeacherService.getTeachers(departmentId, term)
		thesisTeachers.each {
			def teacher = map[it.id] 
			if(teacher) {
				teacher.total += thesisCount
				teacher.thesis = true
			} else {
				def thesisRecords = getCategoryCounts(it.id, 5, term)
				teacher = [
					id        : it.id,
					name      : it.name,
					total     : thesisCount,
					commit    : thesisRecords.commit ?: 0,
					free      : thesisRecords.free ?: 0,
					thesis    : true,
					internship: false
				]
				teachers << teacher
				map[it.id] = teacher
			}
		}

		// 实习材料
		def internshipCount = termMaterialService.getCountByCategory(department, term, 6)
		def internshipTeachers = internshipTeacherService.getTeachers(departmentId, term)
		internshipTeachers.each {
			def teacher = map[it.id]
			if(teacher) {
				teacher.total += internshipCount
				teacher.internship = true
			} else {
				def internshipRecords = getCategoryCounts(it.id, 6, term)
				teacher = [
					id        : it.id,
					name      : it.name,
					total     : internshipCount,
					commit    : internshipRecords.commit ?: 0,
					free      : internshipRecords.free ?: 0,
					thesis    : false,
					internship: true
				]
				teachers << teacher
				map[it.id] = teacher
			}
		}
		
		return teachers
	}
	
	private getCategoryCounts(String teacherId, Long categoryId, Term term) {
		def results = TeachingMaterial.executeQuery '''
SELECT teachingMaterial.status, count(*) 
FROM TeachingMaterial teachingMaterial
JOIN teachingMaterial.termMaterial termMaterial
JOIN teachingMaterial.teacher teacher
JOIN termMaterial.material material
JOIN material.category category
WHERE termMaterial.term = :term
AND teacher.id = :teacherId
ANd category.id = :categoryId
GROUP BY teachingMaterial.status
''', [teacherId:teacherId, categoryId:categoryId, term:term]
		def counts = [:]
		results.each {
			if(it[0] == 1) {
				counts.commit = it[1]
			} else if(it[0] == 2) {
				counts.free = it[1]
			}
		}
		
		return counts
	}
	
	def getMaterials(String departmentId, Term term) {
		def results = TermMaterial.executeQuery '''
SELECT c.id, c.name, c.type, tm.id, m.id, m.name, m.type, m.form
FROM TermMaterial tm
JOIN tm.department d
JOIN tm.material m
JOIN m.category c
WHERE tm.term = :term
AND d.id = :departmentId
ORDER BY c.displayOrder, m.name 
''', [term:term, departmentId:departmentId]
		def categories = []
		def last = null
		def category = null
		results.each{
			if(last != it[0]) {
				last = it[0]
				category = [id:last, name: it[1], type:it[2], materials:[]]
				categories << category
			}
			category.materials << [
				tmid: it[3],
				mid:  it[4],
				name: it[5],
				type: it[6],
				form: it[7]
			]
		}
		return categories
	}
	
	def getTeachingMaterials(String departmentId, String teacherId, Term term) {
		def department = Department.load(departmentId) 
		def teacher = Teacher.load(teacherId)
		// 查询课程及课程班（除了实验）
		def results = CourseClass.executeQuery '''
SELECT DISTINCT c.id, c.name, cc.id, cc.name
FROM CourseClass cc
JOIN cc.teachers cct
JOIN cc.course c
WHERE cct.teacher = :teacher
AND cc.department = :department
AND cc.term = :term
AND substring(cc.id, -1) NOT BETWEEN 'A' AND 'Z'
ORDER BY c.name, cc.id
''', [department:department, teacher:teacher, term:term]
		def courses = []
		def lastId = null
		def course = null
		results.each {
			if(lastId != it[0]) {
				lastId = it[0]
				course = [id: lastId, name: it[1], classes:[]]
				courses << course
			}
			course.classes << [id: it[2], name: it[3]]
		}

		// 提交材料		
		def commits = []
		// 按课程班
		results = TeachingMaterial.executeQuery '''
SELECT teachingMaterial.id, termMaterial.id, case 
when courseClass is not null then courseClass.id
when course is not null then course.id
else termMaterial.term.id end,
teachingMaterial.status
FROM TeachingMaterial teachingMaterial
JOIN teachingMaterial.termMaterial termMaterial
JOIN termMaterial.material material
LEFT join teachingMaterial.courseClass courseClass
LEFT join teachingMaterial.course course
WHERE termMaterial.term = :term
AND termMaterial.department = :department
AND teachingMaterial.teacher = :teacher
''', [department:department, teacher:teacher, term:term]
		results.each {commits << [
			id:     it[0],
			tmid:   it[1],
			target: it[2],
			status: it[3]
		]}
		
		def hasThesis = thesisTeacherService.hasThesis(departmentId, teacherId, term)
		def hasInternship = internshipTeacherService.hasInternship(departmentId, teacherId, term)

		return [courses: courses, commits: commits, thesis: hasThesis, internship: hasInternship]
	}
	
	def getDepartments(String teacherId, Term term) {
		def teacher = Teacher.load(teacherId)
		def results = CourseClass.executeQuery '''
SELECT DISTINCT d.id, d.name
FROM CourseClass cc
JOIN cc.teachers cct
JOIN cc.department d
WHERE cct.teacher = :teacher
AND cc.term = :term
AND substring(cc.id, -1) NOT BETWEEN 'A' AND 'Z'
''',[teacher:teacher, term:term]
		return results.collect{[
			id: it[0],
			name: it[1],	
		]}
	}
	
	def create(Long termMaterialId, String targetId, String teacherId, String checkerId, Integer status) {
		TeachingMaterial teachingMaterial = new TeachingMaterial()
		TermMaterial termMaterial = TermMaterial.get(termMaterialId)
		Material material = termMaterial.material
		if(material.type == 1) {
			teachingMaterial.courseClass = CourseClass.load(targetId)
		} else if(material.type == 2) {
			teachingMaterial.course = Course.load(targetId)
		}
		teachingMaterial.termMaterial = termMaterial
		teachingMaterial.teacher = Teacher.load(teacherId)
		teachingMaterial.checker = Teacher.load(checkerId)
		teachingMaterial.comfirmDate = new Date()
		teachingMaterial.status = status
		
		return teachingMaterial.save(failOnError:true)
	}
		
	def delete(Long id) {
		TeachingMaterial teachingMaterial = TeachingMaterial.load(id)
		teachingMaterial.delete()
		return true
	}
	
	def changeStatus(Long id, status) {
		TeachingMaterial teachingMaterial = TeachingMaterial.get(id)
		teachingMaterial.status = status
		teachingMaterial.save()
	}
}
