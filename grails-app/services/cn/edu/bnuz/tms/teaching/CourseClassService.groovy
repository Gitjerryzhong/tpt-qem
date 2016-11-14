package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Teacher


class CourseClassService {
	TermService termService

	boolean isCourseTeacher(Teacher teacher) {
		def termIds = termService.getLastTermIds(4)
		def count = CourseClass.executeQuery """
SELECT COUNT(*)
FROM CourseClass AS courseClass
JOIN courseClass.teachers AS courseClassTeacher
WHERE courseClassTeacher.teacher = :teacher 
  AND courseClass.term.id in (:termIds)""", [teacher:teacher, termIds:termIds]
		
		return count[0] > 0
	}
	
	/**
	 * 获取合并后的课程（组合理论和实验课）
	 * @param teacherId
	 * @return 课程列表
	 */
	def getAllCombinedCourseClasses(String teacherId) {
		def results = CourseClass.executeQuery '''
select new map(
  courseClass.id as id,
  courseClass.name as name,
  courseClass.term.id as term
) from CourseClass courseClass
join courseClass.teachers courseClassTeacher
join courseClassTeacher.teacher teacher
where teacher.id = :teacherId
order by id desc
''', [teacherId: teacherId]
		def combined = []
		results.each {
			def last = it.id[-1..-1] 
			if(!(last >= 'A' && last <= 'Z')) {
				combined << it
			}
		}
		return combined
	}
}
