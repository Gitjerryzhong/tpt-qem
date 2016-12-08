package cn.edu.bnuz.qem

import cn.edu.bnuz.tms.organization.Teacher;
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.QemTaskAudit
import grails.converters.JSON

import org.springframework.http.HttpStatus

import java.util.List

import cn.edu.bnuz.qem.update.UpdateTask

class QemUpdateCollegeCheckController {
	CollegeService collegeService
	SecurityService securityService
	AttachService attachService
	ExportService exportService
    def index() { }
	def requestList(){
		render([updateList:collegeService.updateList()] as JSON)
	}
	def updateDetail(){
		def updateId=params.long("id")
		def isMyUpdate = params.boolean("isMine")
		def updateView
		def task
		def fileList
		def declarations
		def newLeader
		if(updateId){
			updateView = UpdateTask.get(updateId)
			task = QemTask.get(updateView.taskId)
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${task.teacher.id}/${updateView.taskId}/${updateView.commitDate.format('yyyyMMdd')}"
			if(isMyUpdate) filePath = grailsApplication.config.tms.qem.uploadPath+"/update/${securityService.departmentId}/${updateView.taskId}/${updateView.commitDate.format('yyyyMMdd')}"
//			println filePath
			fileList = attachService.getFileNames_Qem(filePath)
			def filePath_old= grailsApplication.config.tms.qem.uploadPath+"/task/${task.teacher.id}/${updateView.taskId}"
			declarations = attachService.getFileNames_Qem(filePath_old)
//			fileList = getFileNames("${updateView.taskId}",updateView.commitDate.format("yyyyMMdd"))
//			如果不是本部门的申请，认为是非法获取信息
			if(task?.department.id != securityService.departmentId) {
				task=null
				updateView =null
				fileList=null
			}
			if(updateView.teacherId) {
				def teacher =Teacher.get(updateView.teacherId)
					if(teacher.department.id == securityService.departmentId) {
						newLeader=teacher.name
					}
			}
		}
		render ([updateView:updateView,task:task,fileList: fileList,declarations:declarations,taskLeader:task?.teacher.name,newLeader:newLeader] as JSON)
	}
	/**
	 * 撤销申请
	 * @return
	 */
	def cancel(){
		def updateId=params.long("id")
		def isMyUpdate = params.boolean("isMine")
		def updateView
		def task
		def fileList
		def declarations
		def newLeader
		if(updateId){
			updateView = UpdateTask.get(updateId)
			if(updateView.flow ==UpdateTask.F_UNIVERSITY && updateView.auditStatus==UpdateTask.AU_NONE){
				updateView.setFlow(UpdateTask.F_COLLEGE)
				updateView.save(flush:true)
				task = QemTask.get(updateView.taskId)
				def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${task.teacher.id}/${updateView.taskId}/${updateView.commitDate.format('yyyyMMdd')}"
				if(isMyUpdate) filePath = grailsApplication.config.tms.qem.uploadPath+"/update/${securityService.departmentId}/${updateView.taskId}/${updateView.commitDate.format('yyyyMMdd')}"
				fileList = attachService.getFileNames_Qem(filePath)
				def filePath_old= grailsApplication.config.tms.qem.uploadPath+"/task/${task.teacher.id}/${updateView.taskId}"
				declarations = attachService.getFileNames_Qem(filePath_old)
	//			如果不是本部门的申请，认为是非法获取信息
				if(task?.department.id != securityService.departmentId) {
					task=null
					updateView =null
					fileList=null
				}
				if(updateView.teacherId) {
					def teacher =Teacher.get(updateView.teacherId)
						if(teacher.department.id == securityService.departmentId) {
							newLeader=teacher.name
						}
				}
			}
			render ([updateView:updateView,task:task,fileList: fileList,declarations:declarations,taskLeader:task?.teacher.name,newLeader:newLeader] as JSON)
		}
	}
	/**
	 * 审核
	 * @return
	 */
	def audit(){
		def audit=new AuditForm(request.JSON)
		def updateTask=UpdateTask.get(audit.form_id)
		if(updateTask.flow==UpdateTask.F_COLLEGE && (updateTask.auditStatus==UpdateTask.AU_NONE || updateTask.auditStatus==UpdateTask.AU_BG)){
			int check= Integer.parseInt(audit.check)
			switch(check){
			case 20: updateTask.setFlow(UpdateTask.F_UNIVERSITY)
					 updateTask.setAuditStatus(UpdateTask.AU_NONE)
					 break;
			case 21: updateTask.setAuditStatus(UpdateTask.AU_NG)
					 break;
			case 26: updateTask.setFlow(UpdateTask.F_PERSON)
					 updateTask.setAuditStatus(UpdateTask.AU_BG)
					 break;
			}
			updateTask.save(flush:true)
			QemTaskAudit qemAudit=new QemTaskAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:audit.check,
				content:audit.content,
				date:new Date(),
				objectId:updateTask.id,
				src:updateTask.class.name])
			qemAudit.save(flush:true)
			render status: HttpStatus.OK
		}else render status: HttpStatus.BAD_REQUEST
	}
	/**
	 * 下载附件
	 * @return
	 */
	def downloadT(){
		def taskId=params.taskId
		def fileType =params.fileType
		if(taskId){
			def task = QemTask.get(taskId) 
			if(task?.department.id != securityService.departmentId) { return}
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/task/${task.teacher.id}/${taskId}/${fileType}"
//			println filePath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition",
				 "attachment;filename=\""+
				 java.net.URLEncoder.encode("${fileType}.zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(filePath)
			response.outputStream.flush()
		}else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	def downloadU(){
		def updateId=params.taskId
		def fileType =params.fileType
		def isMine = params.int("isMine")
		if(updateId){
			def updateView = UpdateTask.get(updateId)
			def task = QemTask.get(updateView.taskId)
			if(task?.department.id != securityService.departmentId) { return}
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${task.teacher.id}/${updateView.taskId}/${updateView.commitDate.format('yyyyMMdd')}/${fileType}"
			if(isMine)filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${securityService.departmentId}/${updateView.taskId}/${updateView.commitDate.format('yyyyMMdd')}/${fileType}"
			println filePath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition",
				 "attachment;filename=\""+
				 java.net.URLEncoder.encode("${fileType}.zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(filePath)
			response.outputStream.flush()
		}else {
			render status: HttpStatus.NOT_FOUND
		}
	}
}
