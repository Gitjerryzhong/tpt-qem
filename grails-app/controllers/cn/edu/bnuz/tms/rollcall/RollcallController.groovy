package cn.edu.bnuz.tms.rollcall

import grails.converters.JSON
import grails.web.JSONBuilder

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.rollcall.LeaveService;
import cn.edu.bnuz.tms.rollcall.RollcallService;
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.TeacherService
import cn.edu.bnuz.tms.teaching.ArrangementService
import cn.edu.bnuz.tms.teaching.TermService



class RollcallController {
	ArrangementService arrangementService
	RollcallService rollcallService
	TermService termService
	SecurityService securityService
	TeacherService teacherService
	LeaveService leaveService

	/**
	 * ��ȡ������
	 * @param arrangementId ��ѧ����ID
	 * @param week �ܴ�
	 * @return ��������������
	 */
	def form(String arrangementId, int week) {
		def teacherId =  securityService.userId
		def term = termService.currentTerm;
		def arrangements = arrangementService.getTeacherArrangements(teacherId, term)
		// ֻ�б���ӵ�еİ��Ųſ��Ե���
		if(!arrangements.any { it.id == arrangementId }) {
			render status:HttpStatus.NOT_FOUND
		} else {
			def statis = rollcallService.getStudentRollcallStatis(arrangementId)
			def view = teacherService.getSetting(teacherId, 'rollcall.view')
			def settings = teacherService.getSetting(teacherId, 'rollcall.settings')
			def form = rollcallService.getFormForRollcall(teacherId, arrangementId, week)
			def students = arrangementService.getRollcallStudents(arrangementId)
			def leaveRequests = leaveService.getLeaveRequests(arrangementId, week);
			def arrangement = arrangements.find{it.id == arrangementId}
			return [
				arrangementId: arrangementId,
				arrangement: arrangement,
				term: term,
				week: week,
				form: form ? (form as JSON).toString() : 'null',
				view: view ? "'$view'": 'null',
				settings: settings ?: 'null',
				arrangements: arrangements as JSON,
				students: students as JSON,
				leaveRequests: leaveRequests as JSON,
				statis: statis as JSON
			]
		}
	}

	/**
	 * ���������������ͬʱ�ṩ���������ݣ����������
	 * @return ����formId��/��itemId���Լ�ѧ��ͳ������
	 */
	def createForm() {
		def teacherId = securityService.userId

		def form = rollcallService.createForm(teacherId, params.arrangementId, params.int("week"))

		if(form) {
			if(params.studentId && params.type) { // ����form��ͬʱ����item
				def result = rollcallService.createItem(form.id, params.studentId, params.int("type"))
				if(result) {
					render(contentType:"text/json") {
						formId = form.id
						itemId = result.itemId
						statis = result.statis
					}
				} else {
					render status:HttpStatus.BAD_REQUEST
				}
			} else { // ���ڡ�ȫ�ڡ���û��item
				render(contentType:"text/json") { 
					formId = form.id 
				}
			}
		} else {
			render status:HttpStatus.BAD_REQUEST
		}
	}

	/**
	 * ����������
	 * @return ѧ��ͳ������
	 */
	def createItem() {
		def formId = params.long("formId")
		def studentId = params.studentId
		def type = params.int("type")
		def result = rollcallService.createItem(formId,	studentId, type)
		if(result) {
			render result as JSON
		} else {
			render status:HttpStatus.BAD_REQUEST
		} 
	}

	/**
	 * ���µ�����
	 * @param id ������id 
	 * @return ѧ��ͳ������
	 */
	def updateItem(long id) {
		def type = request.JSON.type
		def result = rollcallService.updateItem(id, type)
		if(result) {
			render result as JSON
		} else {
			render status:HttpStatus.BAD_REQUEST
		} 
	}

	/**
	 * ɾ��������
	 * @param id ������ID
	 * @return ѧ��ͳ������
	 */
	def deleteItem(long id) {
		def result = rollcallService.deleteItem(id)
		if(result) {
			render result as JSON
		} else {
			render status:HttpStatus.BAD_REQUEST
		} 
	}
	
	/**
	 * �����б�
	 */
	def lockList() {
		def departmentId = securityService.departmentId
		def term = termService.currentTerm
		def week = params.int('week') ?: term.currentWeek
		def forms = rollcallService.getLockList(departmentId, week, term)
		return [
			forms: forms,
			term: term,
			week: week
		]
	}
	
	/**
	 * ����ָ�����ڱ��״̬���ɰ�ID���ܸ���
	 */
	def lock() {
		if(params.formId) {
			rollcallService.updateStatus(params.int('formId'), params.int('status'))
			render status:HttpStatus.OK
		} else if(params.week) {
			def departmentId = securityService.departmentId
			def term = termService.currentTerm
			rollcallService.updateWeekStatus(departmentId, params.int("week"), term, params.int('status'))
			render status:HttpStatus.OK
		} else {
			render status:HttpStatus.BAD_REQUEST
		}
	}
}
