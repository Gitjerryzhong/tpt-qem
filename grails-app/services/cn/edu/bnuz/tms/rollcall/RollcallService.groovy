package cn.edu.bnuz.tms.rollcall

import groovy.sql.Sql

import javax.sql.DataSource

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Arrangement
import cn.edu.bnuz.tms.teaching.Term;
import cn.edu.bnuz.tms.rollcall.CourseClassStudentRollcall;
import cn.edu.bnuz.tms.rollcall.RollcallForm;
import cn.edu.bnuz.tms.rollcall.RollcallItem;

/**
 * 考勤服务
 * @author 杨林
 * @since 0.1
 */
class RollcallService {
	DataSource dataSource
	/**
	 * 根据教学安排ID和周次获取点名表单用于点名显示
	 * @param teacherId 教师ID
	 * @param arrangementId 教学安排ID
	 * @param week 周次
	 * @return 点名表单
	 */
	def getFormForRollcall(String teacherId, String arrangementId, int week) {
		Teacher teacher = Teacher.load(teacherId)
		Arrangement arrangement = Arrangement.load(arrangementId)
		
		def form = RollcallForm.executeQuery '''
SELECT form.id, form.status
FROM RollcallForm form
WHERE form.arrangement = :arrangement
  AND form.week = :week
  AND form.teacher = :teacher 
''', [arrangement: arrangement, week: week, teacher: teacher]
		if(form.size() == 0) {
			return null
		} else {
			def formId = form[0][0]
			def status = form[0][1]
			
			def items = RollcallItem.executeQuery '''
SELECT item.id, item.student.id, item.type
FROM RollcallItem item
WHERE item.form.id = :formId
''', [formId: formId]
			
			return [
				id: formId,
				status: status,
				items: items.collect {
					[id: it[0], studentId: it[1], type: it[2]]
				}
			]
		}
	}

	/**
	 * 创建点名表单
	 * @param arrangementId 教学安排id
	 * @param week 周次
	 * @return 新建点名表单
	 */
	RollcallForm createForm(String teacherId, String arrangementId, int week) {
		// 前台异步提交时，可能结果还没有返回，所以需要验证考勤表是否已经创建。
		Arrangement arrangement = Arrangement.load(arrangementId)
		RollcallForm form = RollcallForm.findByArrangementAndWeek(arrangement, week)
		// 如果不存在，则创建
		if(form == null) {
			def teacher = Teacher.load(teacherId)
			form = new RollcallForm(
					arrangement : arrangement,
					week : week,
					teacher : teacher,
					dateModified : new Date()
			)
			form.save(failOnError: true)
		}
		return form
	}

	/**
	 * 创建点名项
	 * @param formId 表单id
	 * @param studentId 学生学号
	 * @param type 类型
	 * @return 新建的点名项
	 */
	def createItem(long formId, String studentId, int type) {
		def result = null
		Sql sql = new Sql(dataSource)
		sql.call '{call sp_insert_rollcall_item(?,?,?,?,?,?,?,?)}',
				[formId, studentId, type, Sql.BIGINT, Sql.INTEGER, Sql.INTEGER, Sql.INTEGER, Sql.INTEGER], {
			itemId, absentCount, lateCount, earlyCount, leaveCount ->
			if(itemId != 0) {
				result = [itemId: itemId, statis:[absentCount?:0, lateCount?:0, earlyCount?:0, leaveCount?:0]]
			}
		}
		return result
	}

	/**
	 * 更新点名项
	 * @param itemId 点名项id
	 * @param type 类型
	 */
	def updateItem(long itemId, int type) {
		def result = null
		Sql sql = new Sql(dataSource)
		sql.call '{call sp_update_rollcall_item(?,?,?,?,?,?,?)}',
				[itemId, type, Sql.INTEGER, Sql.INTEGER, Sql.INTEGER, Sql.INTEGER, Sql.INTEGER], {
			success, absentCount, lateCount, earlyCount, leaveCount ->
			if(success != 0) {
				result = [itemId: itemId, statis:[absentCount?:0, lateCount?:0, earlyCount?:0, leaveCount?:0]]
			}
		}
		return result
	}

	/**
	 * 删除点名项
	 * @param itemId 点名项id
	 */
	def deleteItem(long itemId) {
		def result = null
		Sql sql = new Sql(dataSource)
		sql.call '{call sp_delete_rollcall_item(?,?,?,?,?,?)}',
				[itemId, Sql.INTEGER, Sql.INTEGER, Sql.INTEGER, Sql.INTEGER, Sql.INTEGER], {
			success, absentCount, lateCount, earlyCount, leaveCount ->
			if(success != 0) {
				result = [itemId: itemId, statis:[absentCount?:0, lateCount?:0, earlyCount?:0, leaveCount?:0]]
			}
		}
		return result
	}

	/**
	 * 根据教学安排ID查询学生的考勤统计。
	 * @param arrangementId 教学安装ID
	 * @return 学生考勤统计 list of [studentId, absentCount, lateCount, earlyCount, leaveCount] 
	 */
	def getStudentRollcallStatis(String arrangementId) {
		def results = CourseClassStudentRollcall.executeQuery '''
SELECT student.id, 
  sum(ccsr.absentCount) as absentCount,
  sum(ccsr.lateCount) as lateCount,
  sum(ccsr.earlyCount) as earlyCount,
  sum(ccsr.leaveCount) as leaveCount
FROM CourseClassStudentRollcall ccsr
JOIN ccsr.courseClassStudent ccs
JOIN ccs.courseClass cc
JOIN cc.arrangements arr 
JOIN ccs.student student
WHERE arr.id = :arrangementId 
GROUP BY ccsr.courseClassStudent
ORDER BY student.id
''', [arrangementId:arrangementId]

		def students = [:];
		results.each {
			students[it[0]] = [it[1], it[2], it[3], it[4]]
		}
		return students
	}

	/**
	 * 根据教学安排ID查询指定学生的考勤统计。
	 * @param arrangementId 教学安装ID
	 * @param studentId 学生ID
	 * @return 指定学生的考勤统计 [absentCount, lateCount, earlyCount, leaveCount]
	 */
	def getStudentRollcallStatis(String arrangementId, String studentId) {
		def results = CourseClassStudentRollcall.executeQuery '''
SELECT 
  sum(ccsr.absentCount),
  sum(ccsr.lateCount),
  sum(ccsr.earlyCount),
  sum(ccsr.leaveCount)
FROM CourseClassStudentRollcall ccsr
WHERE ccsr.courseClassStudent.courseClass in (
  SELECT courseClass
  FROM Arrangement arr 
  JOIN arr.courseClasses AS courseClass 
  WHERE arr.id = :arrangementId) 
  AND ccsr.courseClassStudent.student.id = :studentId
''', [arrangementId:arrangementId, studentId:studentId]

		def item = results[0]
		return [
			item[0]?:0,
			item[1]?:0,
			item[2]?:0,
			item[3]?:0
		]
	}
	
	/**
	 * 获取指定周次全部考勤表，用于锁定设置
	 * @param departmentId 学院ID
	 * @param week 周次
	 * @param term 学期
	 * @return 考勤列表
	 */
	def getLockList(String departmentId, int week, Term term) {
		def results = RollcallForm.executeQuery '''
SELECT rollcallForm.id, teacher.name, arrangement.dayOfWeek, 
arrangement.startSection, arrangement.totalSection,
group_concat_distinct(courseClass.name),
sum(CASE rollcallItem.type WHEN 1 THEN 1 ELSE 0 END),
sum(CASE rollcallItem.type WHEN 2 THEN 1 ELSE 0 END),
sum(CASE rollcallItem.type WHEN 3 THEN 1 ELSE 0 END),
sum(CASE rollcallItem.type WHEN 5 THEN 1 ELSE 0 END),
rollcallForm.dateCreated, rollcallForm.dateModified, rollcallForm.status
FROM RollcallForm rollcallForm
JOIN rollcallForm.arrangement arrangement
JOIN arrangement.courseClasses courseClass
JOIN rollcallForm.teacher teacher
LEFT JOIN rollcallForm.items rollcallItem
WHERE courseClass.department.id = :departmentId
  AND rollcallForm.week = :week
  AND courseClass.term = :term
GROUP BY arrangement.id
ORDER BY teacher.id, courseClass.id, arrangement.id
''', [departmentId:departmentId, week:week, term:term]
		def forms = results.collect {
			[
				id: 			it[0],
				teacher: 		it[1],
				dayOfWeek: 		it[2],
				startSection: 	it[3],
				totalSection: 	it[4],
				courseClasses:	it[5],
				absentCount:	it[6],
				lateCount:		it[7] + it[9],
				earlyCount:		it[8] + it[9],
				dateCreated:	it[10],
				dateModified:	it[11],
				status:			it[12]
			]
		}
		
		return forms
	}
	
	
	/**
	 * 更新指定ID的考勤表状态
	 * @param id 考勤表ID
	 * @param status 状态
	 */
	void updateStatus(long id, int status) {
		RollcallForm.executeUpdate '''
UPDATE RollcallForm rollcallForm 
SET rollcallForm.status = :status
WHERE rollcallForm.id = :id
''', [id:id, status:status]
	}
	
	/**
	 * 更新指定周次全部考勤表
	 * @param week 周次
	 * @param status 状态
	 */
	void updateWeekStatus(String departmentId, int week, Term term, int status) {
		RollcallForm.executeUpdate '''
UPDATE RollcallForm rf
SET rf.status = :status
WHERE rf.week = :week and rf.arrangement.id in (
  select arr.id
  from CourseClass cc
  join cc.department dept
  join cc.arrangements arr
  where cc.term = :term
  and dept.id = :departmentId
)
''', [departmentId: departmentId, week:week, term: term, status:status]
	}
}
