package cn.edu.bnuz.tms.rollcall

import grails.converters.JSON
import grails.web.JSONBuilder

import java.text.SimpleDateFormat

import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.teaching.ArrangementService
import cn.edu.bnuz.tms.teaching.CourseClassService
import cn.edu.bnuz.tms.teaching.TermService

/**
 * 考勤统计
 * @author 杨林
 */
class RollcallStatisController {
	SecurityService securityService
	RollcallStatisService rollcallStatisService
	TermService termService
	LeaveService leaveService
	CourseClassService courseClassService
	ArrangementService arrangementService
	RollcallExporterService rollcallExporterService
	
	/**
	 * 学院学生考勤情况
	 */
	def department() {
		def term = termService.currentTerm
		def departmentId = securityService.departmentId
		def statis = rollcallStatisService.department(departmentId, term)
		withFormat {
			html { render model:[title:0, statis:statis], view:"students" }
			excel {	exportStatis(term, statis); return }
		}
	}


	/**
	 * 年级学生考勤情况
	 */
	def grade() {
		def term = termService.currentTerm
		def teacherId = securityService.userId
		def statis = rollcallStatisService.grade(teacherId, term)
		withFormat {
			html { render model:[title:1, statis:statis], view:"students" }
			excel { exportStatis(term, statis); return }
		}
	}

	/**
	 * 班级学生考勤情况
	 */
	def adminClass() {
		def term = termService.currentTerm
		def teacherId = securityService.userId
		def statis = rollcallStatisService.adminClass(teacherId, term)
		withFormat {
			html { render model:[title:2, statis:statis], view:"students" }
			excel { exportStatis(term, statis); return }
		}
	}

	/**
	 * 导出统计数据
	 * @param statis 统计数据
	 */
	private exportStatis(term, statis) {
		def template = grailsApplication.mainContext.getResource("excel/student-rollcall-statis.xls").getFile()
		def workbook = rollcallExporterService.exportStudentsStatis(template, term, statis, rollcallStatisService)
		def time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
		response.setContentType("application/excel")
		response.setHeader("Content-disposition", "attachment;filename=${time}.xls")
		workbook.write(response.outputStream)
	}
	
	/**
	 * 按年级、专业、行政班汇总考勤数据
	 */
	def gradeMajorAdminClass() {
		def term = termService.currentTerm
		def departmentId = securityService.departmentId
		def statis = rollcallStatisService.gradeMajorAdminClass(departmentId, term)
		return [statis:statis as JSON]
	}

	/**
	 * 学生考勤情况
	 * @param id 学生ID
	 */
	def student(String id) {
		def term = termService.currentTerm
		def rollcallItems = rollcallStatisService.studentRollcallItems(id, term)
		def courses = rollcallStatisService.studentCoursesStatis(id, term)
		def leaveItems = rollcallStatisService.studentLeaveItems(id, term)
		def statis = [rollcallItems:rollcallItems, courses: courses, leaveItems:leaveItems]
		render statis as JSON
	}

	/**
	 * 学院教师考勤情况
	 */
	def teachers() {
		def term = termService.currentTerm
		def departmentId = securityService.departmentId
		def statis = rollcallStatisService.teachers(departmentId, term)

		return [
			term: new JSONBuilder() .build {
				startWeek = term.startWeek
				endWeek = term.endWeek
				currentWeek = term.currentWeek
			},
			statis: statis as JSON
		]
	}

	/**
	 * 教师指定周次的点名情况	
	 * @param id 教师ID
	 * @param week 周次
	 */
	def teacher(String id, int week) {
		def term = termService.currentTerm
		def departmentId = securityService.departmentId
		def statis = rollcallStatisService.teacher(departmentId, id, week, term)
		render statis as JSON
	}
	
	/**
	 * 按教学班查看点名情况
	 */
	def courseClass() {
		withFormat {
			html {
				def teacherId = securityService.userId
				def courseClasses = courseClassService.getAllCombinedCourseClasses(teacherId)
				def term = termService.currentTerm
				def termIds = courseClasses.collect {it.term}.unique().sort().reverse()
				def termId = termIds.contains(term.id) ? term.id : termIds[0]
				return [term:termId, terms:termIds as JSON, courseClasses: courseClasses as JSON]
			}
			json {
				def courseClassId = params['course-class-id']
				def statis = rollcallStatisService.courseClass(courseClassId)
				render(statis as JSON)
			}
			excel { 
				def courseClassId = params['course-class-id']
				def statis = rollcallStatisService.courseClass(courseClassId)
				def template = grailsApplication.mainContext.getResource("excel/student-rollcall-statis.xls").getFile()
				def workbook = rollcallExporterService.exportCourseClassStatis(template, courseClassId, statis, rollcallStatisService)
				def time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				response.setContentType("application/excel")
				response.setHeader("Content-disposition", "attachment;filename=${time}.xls")
				workbook.write(response.outputStream)
				return 
			}
		}
	}

	/**
	 * 个人考勤情况（教师或学生）
	 * @return
	 */
	def personal() {
		if(securityService.isTeacher()) {
			withFormat {
				html {
					def termIds = termService.allTermIds
					def termId = termService.currentTerm.id
					render view:'teacher', model:[
						termIds: termIds,
						termId: termId,
					]
				}
				json {
					def termId = params.long("term")
					def term = termService.getTerm(termId)
					def teacherId = securityService.userId
					def arrangements = arrangementService.getTeacherArrangements(teacherId, term)
					def forms = rollcallStatisService.teacher(teacherId, term)
					render ([
						term: [
							id: term.id,
							startWeek: term.startWeek,
							endWeek: term.endWeek,
							currentWeek: term.currentWeek,
						],
						arrangements: arrangements,
						forms: forms
					] as JSON)
				}
			}
		} else {
			def term = termService.currentTerm
			def studentId = securityService.userId
			def rollcallItems = rollcallStatisService.studentRollcallItems(studentId, term)
			def courses = rollcallStatisService.studentCoursesStatis(studentId, term)
			def leaveItems = rollcallStatisService.studentLeaveItems(studentId, term)
			withFormat {
				html {
					render view:'student', model:[
						rollcallItems:rollcallItems as JSON, 
						leaveItems:leaveItems as JSON,
						courses: courses as JSON, 
					]
				}
				json {
					render ([
						rollcallItems:rollcallItems, 
						leaveItems:leaveItems,
						courses: courses, 
					] as JSON)
				}
			}
		}
	}

	def personalExport(String id) {
		def template = grailsApplication.mainContext.getResource("excel/arrangement-rollcall.xls").getFile()
		def term = termService.currentTerm
		def rollcallStatis = rollcallStatisService.getArrangementRollcall(id)
		def workbook = rollcallExporterService.exportRollcallForm(template, term, rollcallStatis)
		response.setContentType("application/excel")
		response.setHeader("Content-disposition", "attachment;filename=${workbook.getSheetName(0)}.xls")
		workbook.write(response.outputStream)
		return
	}
}
