package cn.edu.bnuz.qem

import java.util.List;

import cn.edu.bnuz.qem.project.*
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tms.organization.Department
import grails.converters.JSON

import org.springframework.http.HttpStatus

class QemCollegeCheckController {
	CollegeService collegeService
	SecurityService securityService
	CollegeExportService collegeExportService
	ExportService exportService
	/**
	 * 立项申请通知
	 * @return
	 */
    def index() {
		def notices = Notice.findAll("from Notice where workType='REQ' order by id desc")
		render (view:"index",model:[notices:notices])
	}
	def showRequests(){
		def offset = params.int("offset") ?: 0
		def max = params.int("max") ?: 10
		def status =params.int("type")?: 0
		def bn =params.bn
		def requests
		if(status<7) //7对应4同意，8对应5不同意，9对应6须修改
			requests= collegeService.requestList(offset, max, status,bn)
		else{
			requests= collegeService.requestListU(offset, max, status-3,bn)
		}
		def total =collegeService.requestCounts(bn)
		render ([requests: requests, pager: [offset: offset, max: max, total: total]] as JSON)

	}
	def checkActionAble(String action){
		Notice notice= Notice.last()
		def today =new Date()-15
		return (today.after(notice.start) && today.before(notice.end) && notice.workType?.equals(action))
	}
	def getRequestDetail(){
		
		def form_id = params.int("form_id")
		if(form_id){
			showDetail(form_id)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
	def showDetail(long form_id){
		def nextId= collegeService.checkingNext(form_id)
		def prevId= collegeService.checkingPrev(form_id)	
		def project=collegeService.getProject(form_id)
		def total3 =collegeService.requestCounts(project?.bn)
		render([form:project,			
			pager: [offset: 0, max: 10, total: total3,nextId:nextId,prevId:prevId],
			fileList:getFileNames(project),
			audits:collegeService.getAudits(form_id)] as JSON)
	}
	def auditSave(){
		def audit=new AuditForm(request.JSON)
		def qemProject=QemProject.get(audit.form_id)
//		println audit.form_id 
		qemProject.setCollegeAudit(audit.content)
		int check= Integer.parseInt(audit.check)
		switch(check){
		case 20: qemProject.setCollegeStatus(QemProject.STATUS_CHECKED)
				 break;
		case 21: qemProject.setCollegeStatus(QemProject.STATUS_REJECTED)
				 break;
		case 40: qemProject.setCollegeStatus(QemProject.STATUS_CLOSED)
				 break;
		}
		def total3 =collegeService.requestCounts(qemProject?.bn)
		def qemAudit = new QemAudit([
			userId:securityService.userId,
			userName:securityService.userName,
			action:audit.check,
			content:audit.content,
			date: new Date(),
			form:qemProject])
		if(!qemAudit.save(flush:true)){
			qemAudit.errors.each{
				println it
			}
		render([errors:qemAudit.errors] as JSON)
		}
		if(audit.nextId!=null && !audit.nextId.equals("null")){
			showDetail(Long.parseLong(audit.nextId))
		}else if(audit.prevId!=null && !audit.prevId.equals("null")){
			showDetail(Long.parseLong(audit.prevId))
		}else{
			render([none:true,pager: [offset: 0, max: 10, total: total3]] as JSON)
		}

	}
	def export(String id){
		def report=collegeService.export(id)
		if(report) {
			def template = grailsApplication.mainContext.getResource("excel/qem-summary.xls").getFile()
			def workbook = collegeExportService.exportReport(template, report)
			response.setContentType("application/excel")
			//转化为中文文件名
			def filename=message(code:"qem.fileName.cproject",args:[Department.get(securityService.departmentId)?.shortName])+".xls" 
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(filename, "UTF-8")+"\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
//	导出指定批次的已通过项目附件
	def exportAttach(String id){
		def report=collegeService.exportForAttach(id)
		if(report) {
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition",
				 "attachment;filename=\""+ 
				 java.net.URLEncoder.encode(Department.get(securityService.departmentId)?.shortName+
					 "_"+id+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.downloadGroups(report,basePath)
			response.outputStream.flush()
//			report.each {item->
//				println item.projectName+"/"+item.teacherId+"/"+item.projectId+"/"+item.taskId
//			}
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
//	导出指定批次的全部附件
	def exportAttach_All(String id){
		def report=collegeService.projectes(id)
		if(report) {
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition",
				 "attachment;filename=\""+
				 java.net.URLEncoder.encode(Department.get(securityService.departmentId)?.shortName+
					 "_"+id+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.downloadGroups(report,basePath)
			response.outputStream.flush()
//			report.each {item->
//				println item.projectName+"/"+item.teacherId+"/"+item.projectId+"/"+item.taskId
//			}
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
//	导出已立项项目所有附件
	def exportAttachAll(){
		def report=collegeService.exportForAttach()
		if(report) {
			def basePath= grailsApplication.config.tms.qem.uploadPath
			//转化为中文文件名
			def filename=message(code:"qem.fileName.cAttachT",args:[Department.get(securityService.departmentId)?.shortName])
			response.setContentType("application/zip")
			response.setHeader("Content-disposition",
				 "attachment;filename=\""+
				 java.net.URLEncoder.encode(filename+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.downloadGroups(report,basePath)
			response.outputStream.flush()
//			report.each {item->
//				println item.projectName+"/"+item.teacherId+"/"+item.projectId+"/"+item.taskId
//			}
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	def exportTask(){
		def report=collegeService.taskList()
		if(report) {
			def template = grailsApplication.mainContext.getResource("excel/project-summary.xls").getFile()
			def workbook = collegeExportService.exportReport_T(template, report)
			response.setContentType("application/excel")
			//转化为中文文件名
			def filename=message(code:"qem.fileName.ctask",args:[Department.get(securityService.departmentId)?.shortName])+".xls" 
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(filename, "UTF-8")+"\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	def cancel(){
		def id = params.long("cancelId") ?: 0
		if(id) {
			def project=QemProject.get(id)
			if(project.allowAction(QemProject.ACTION_COLLEGE_CANCEL)){
				project.setCollegeStatus(0)
				project.save(flush:true)
				QemAudit qemAudit=new QemAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:QemAudit.ACTION_CHECK_CANCEL,
					date:new Date(),
					form:project])
				qemAudit.save(flush:true)				
			}
		}
		
		showRequests()
	}
	def downloadAttch(Long id){
		def qemProject=QemProject.get(id)
		if(qemProject && securityService.departmentId==qemProject.department.id){
//			def basePath= grailsAttributes.getApplicationContext().getResource("/qemUpload/").getFile().toString()
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(qemProject.projectName+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(qemProject,basePath)
			response.outputStream.flush()

		}
	}
	def downloadAttch_T(Long id){
		def task=QemTask.get(id)
		if(task && securityService.departmentId==task.department.id){
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(task.projectName+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(task,basePath)
			response.outputStream.flush()

		}
	}
	def contractList(){
		def taskList=collegeService.contractList()
//		def taskCounts=collegeService.taskCounts()
		render ([taskList:taskList] as JSON)
	}
	def taskList(){
		def taskList=collegeService.allTaskList([QemTask.S_NEW,
			QemTask.STATUS_ACTIVE,
			QemTask.STATUS_ENDING,
			QemTask.STATUS_EXCEPTION_NG,
			QemTask.STATUS_EXCEPTION_PAUSE])
//		def taskCounts=collegeService.taskCounts()
		render ([taskList:taskList] as JSON)
	}
	def taskDetail(long id){
		def form_id=id
		println form_id
		def task = QemTask.get(form_id)
		if(task){
			def nextId= collegeService.checkingNext_T(form_id,task.runStatus)
			def prevId= collegeService.checkingPrev_T(form_id,task.runStatus)
			render([task:task,
				taskType:task.qemType.name,
				userName:task.teacher.name,
				pager: [nextId:nextId,prevId:prevId],
				fileList:getFileNames_T(task)] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
	/**
	 * 合同审核
	 * @return
	 */
	def auditTaskSave(){
		def audit=new AuditForm(request.JSON)
		def qemTask=QemTask.get(audit.form_id)
		def isContract=false
		if(qemTask.runStatus in[QemTask.S_SUBMIT,QemTask.S_BK]) {isContract=true}
		if(qemTask.runStatus in[QemTask.S_ANNUAL_SUBMIT,
								QemTask.S_MID_SUBMIT,
								QemTask.S_END_SUBMIT,
								QemTask.S_ANNUAL_BK,
								QemTask.S_MID_BK,
								QemTask.S_END_BK,
								QemTask.S_SUBMIT,
								QemTask.S_BK] ){
			def stage 
			def currentStage=getCurrentStage(qemTask)
			qemTask.stage.each {item->
				if(item?.currentStage==currentStage)
				  stage=item
			}
			qemTask.setCollegeAudit(audit.content)
			int check= Integer.parseInt(audit.check)
			switch(check){
			case 20: qemTask.setRunStatus(qemTask.passAction())
					 stage?.setStatus(QemStage.S_C_PASS)
					 stage?.setCollegeAudit(audit.content)
					 break;
			case 21: qemTask.setRunStatus(qemTask.ngAction())
					stage?.setStatus(QemStage.S_C_NG)
					stage?.setCollegeAudit(audit.content)
					 break;
			case 26: qemTask.setRunStatus(qemTask.bkAction())
					 stage?.setStatus(QemStage.S_NEW)
					 stage?.setCollegeAudit(audit.content)
					 break;
			}
			
			QemTaskAudit qemAudit=new QemTaskAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:audit.check,
				content:audit.content,
				date:new Date(),
				objectId:qemTask.id,
				src:qemTask.class.name])
			qemAudit.save(flush:true)
			if(audit.nextId!=null && !audit.nextId.equals("null")){
				taskDetail(Long.parseLong(audit.nextId))
			}else if(audit.prevId!=null && !audit.prevId.equals("null")){
				taskDetail(Long.parseLong(audit.prevId))
			}else{
				def taskList
				if(isContract) taskList=collegeService.contractList()
				else taskList=collegeService.taskList()
//				def taskCounts=collegeService.taskCounts()
				render([none:true,taskList:taskList,taskCounts:null] as JSON)
			}
		}else render status: HttpStatus.BAD_REQUEST
	
	

	}
	/**
	 * 学院检查
	 * @return
	 */
	def mid(){}
	def end(){}
	/**
	 * 取年度检查列表
	 */
	def annualTasks(){
		def taskList=collegeService.annualList()
		def taskCounts=collegeService.stageCounts()
		render ([taskList:taskList,taskCounts:taskCounts] as JSON)
	}
	/**
	 * 取中期检查列表
	 */
	def midTasks(){
		def taskList=collegeService.midList()
		def taskCounts=collegeService.stageCounts()
		render ([taskList:taskList,taskCounts:taskCounts] as JSON)
	}
	/**
	 * 取结题检查列表
	 */
	def endTasks(){
		def taskList=collegeService.endList()
		def taskCounts=collegeService.stageCounts()
		render ([taskList:taskList,taskCounts:taskCounts] as JSON)
	}
	/**
	 * 项目阶段报告详情
	 * @return
	 */
	def stageDetail(){
		def task = QemTask.get(params.id)
		if(task){
			def nextId= collegeService.checkingNext_T(task.id,task.runStatus)
			def prevId= collegeService.checkingPrev_T(task.id,task.runStatus)
			render([task:task,
					buget:task.fundingProvince+task.fundingUniversity+task.fundingCollege,
					userName:task.teacher.name,
					qemTypeName:task.qemType.name,
					pager: [nextId:nextId,prevId:prevId],
					stages:task.stage,
				fileList:getFileNames_T(task)] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
	def check(Long id){
		def offset = params.int("offset") ?: 0
		def max = params.int("max") ?: 10
		def status =params.int("type")?: 0
		def requests= collegeService.requestList(offset, max, status,id.toString())	
//		println id.toString()			
		def total =collegeService.requestCounts(id.toString())
		render (view:"check",model:[requests: requests, pager: [offset: offset, max: max, total: total],bn:id.toString()])
	}
	private List<String> getFileNames(Map project){
		//		Notice notice= Notice.last()
//		def filePath= grailsAttributes.getApplicationContext().getResource("/qemUpload/").getFile().toString()+"/"+project.userId+"/"+project.id
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/"+project.userId+"/"+project.id
		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(dir.isDirectory()){
			for(File file: dir.listFiles()){
//				fileNames.add("/tms/qemUpload/"+project.userId+"/"+project.id+"/"+file.name)
//				fileNames.add("/"+file.name)
				if(file.isDirectory()){ //如果申报书子目录
					for(File f:file.listFiles()){
						if(f.name.indexOf("del_")==-1) fileNames.add(file.name+"___"+f.name)
					}
				}else if(file.name.indexOf("del_")==-1){
					fileNames.add(file.name)
				}
			}
		}else{
			return null
		}
		return fileNames
	}
	/***
	 * 用于获取指定项目的所有附件
	 * @param projectId
	 * @return
	 */
	private List<String> getFileNames_T(def task){
		def filePath
		if(task instanceof QemTask)
			filePath= grailsApplication.config.tms.qem.uploadPath+"/task/"+task.teacher.id+"/"+task.id
		else if(task instanceof QemProject){
			filePath= grailsApplication.config.tms.qem.uploadPath+"/"+task.teacher.id+"/"+task.id
		}

		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(dir.isDirectory()){
			for(File file: dir.listFiles()){
				if(file.isDirectory()){ //如果申报书子目录
					for(File f:file.listFiles()){
						if(f.name.indexOf("del_")==-1) fileNames.add(file.name+"___"+f.name)
					}
				}else if(file.name.indexOf("del_")==-1){
					fileNames.add(file.name)
				}
			}
		}
		if(task instanceof QemTask && task.projectId){
			def project=QemProject.get(task.projectId)
			fileNames=fileNames+getFileNames_T(project)
		}
		return fileNames
	}
	private String getCurrentYear(){
		def  notice= Notice.last()
		return notice?.bn
	}
	/***
	 * 获取当前应检查阶段报告
	 * @param qemTask 当前任务
	 * @return
	 */
	private getCurrentStage(QemTask qemTask){
		def currentStage
		def currentYear=new Date().format("yyyy")
		switch(currentYear){
			case qemTask.expectedMid:
				currentStage=QemStage.STAGE_MIDDLE
				break
			case qemTask.expectedEnd:
				currentStage=QemStage.STAGE_ENDING
				break
			default :
					if(qemTask.beginYear<currentYear && qemTask.expectedMid>currentYear){
						currentStage=QemStage.STAGE_ANNUAL
					}else{
						return -1
					}
		}
		return currentStage
	}
}

