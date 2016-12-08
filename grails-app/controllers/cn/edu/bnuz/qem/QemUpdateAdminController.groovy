package cn.edu.bnuz.qem
import java.util.List;

import cn.edu.bnuz.tms.organization.Teacher;
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.qem.project.QemTask;
import cn.edu.bnuz.qem.project.QemTaskAudit
import cn.edu.bnuz.qem.project.QemType;
import cn.edu.bnuz.qem.update.UpdateTask
import grails.converters.JSON

import org.springframework.http.HttpStatus
class QemUpdateAdminController {
	SecurityService securityService
	ExportService exportService
	AttachService attachService
	UpdateAdminService	updateAdminService
    def index() {
		def taskList=updateAdminService.updateList()
		render (view:"index",model:[taskList:taskList])
	}
	def historyRequestList(){
		def taskList=updateAdminService.taskUpdateList()
		def taskCounts=updateAdminService.taskUpdateCounts()
		render ([taskList:taskList,taskCounts:taskCounts] as JSON)
	}
	/**
	 * 变更单详情
	 * @return
	 */
	def updateDetail(){
//		updateAdminService.moveAttch("","")
		def form_id=params.int('id')?:0
		def updateView = UpdateTask.get(form_id)
		if(updateView){
			def isMyUpdate = updateView.updateTypes=='1;'?true:false
			def fileList
			def declarations
			def task=QemTask.get(updateView.taskId)
			def audits=QemTaskAudit.findAllBySrcAndObjectId(updateView.class.name,updateView.id)
			audits.each {
				println it
			}
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${task.teacher.id}/${updateView.taskId}/${updateView.commitDate.format('yyyyMMdd')}"
			if(isMyUpdate) {
				def departmentId=Teacher.get(updateView.userId)?.department.id
				filePath = grailsApplication.config.tms.qem.uploadPath+"/update/${departmentId}/${updateView.taskId}/${updateView.commitDate.format('yyyyMMdd')}"
			}
//			println updateView.userId
			fileList = attachService.getFileNames_Qem(filePath)
			def filePath_old= grailsApplication.config.tms.qem.uploadPath+"/task/${task.teacher.id}/${updateView.taskId}"
			declarations = attachService.getFileNames_Qem(filePath_old)
			render([updateView:updateView,
					teacherName: Teacher.get(updateView.teacherId)?.name,
//					commitDate:updateView.commitDate.format("yyyy-MM-dd"),
					task:task,
					origTeacherName:task?.teacher.name,
					type:task?.qemType.name,
					fileList:fileList,
					audits:audits,
					declarations:declarations] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
		
	}
	/**
	 * 审核
	 * @return
	 */
	def audit(){
		def audit=new AuditForm(request.JSON)
		def updateTask=UpdateTask.get(audit.form_id)
		if(updateTask.flow==UpdateTask.F_UNIVERSITY && updateTask.auditStatus ==UpdateTask.AU_NONE){
			int check= Integer.parseInt(audit.check)
			def oldData
			switch(check){
			case 20: oldData=updateAdminService.doUpdate(updateTask,grailsApplication.config.tms.qem.uploadPath)
//					 println "${oldData as JSON}"
					 break;
			case 21: updateTask.setAuditStatus(UpdateTask.AU_NG)
					 break;
			case 26: updateTask.setFlow(UpdateTask.F_COLLEGE)
					 updateTask.setAuditStatus(UpdateTask.AU_BG)
					 break;
			}
			updateTask.save(flush:true)
			def content=oldData?"${audit.content};${oldData as JSON}":audit.content
			QemTaskAudit qemAudit=new QemTaskAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:audit.check,
				content:content,
				date:new Date(),
				objectId:updateTask.id,
				src:updateTask.class.name])
			qemAudit.save(flush:true)
			def taskList=updateAdminService.updateList()
			render ([taskList:taskList]as JSON)
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
