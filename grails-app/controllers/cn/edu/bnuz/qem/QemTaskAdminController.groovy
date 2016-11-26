package cn.edu.bnuz.qem

import grails.converters.JSON
import cn.edu.bnuz.qem.project.QemProject

import java.util.List;

import cn.edu.bnuz.tms.organization.DepartmentService;
import cn.edu.bnuz.tms.organization.Teacher;

import org.springframework.http.HttpStatus

import cn.edu.bnuz.qem.organization.Experts
import cn.edu.bnuz.qem.project.QemType;
import cn.edu.bnuz.qem.project.QemTask;
import cn.edu.bnuz.qem.project.QemStage;
import cn.edu.bnuz.qem.review.Review;
import cn.edu.bnuz.qem.project.QemTaskAudit
import cn.edu.bnuz.tms.security.SecurityService;

class QemTaskAdminController {
	DepartmentService departmentService
	SecurityService securityService
	TaskAdminService taskAdminService
	ExportService exportService
	ReportService reportService
	CollegeExportService	collegeExportService
    def index() { 
//		def taskList=taskAdminService.taskList()
//		def taskCounts=taskAdminService.taskCounts()
//		render (view:"index",model:[taskList:taskList,taskCounts:taskCounts])
	}
	/**
	 * 取任务列表
	 */
	def showTasks(){
		def taskList=taskAdminService.taskList()
//		def taskCounts=taskAdminService.taskCounts()
		render ([taskList:taskList,taskCounts:null] as JSON)
	}
	/**
	 * 取合同审核列表
	 */
	def showConstracts(){
		def taskList=taskAdminService.contractList()
//		def taskCounts=taskAdminService.taskCounts()
		render ([taskList:taskList,taskCounts:null] as JSON)
	}
	/**
	 * 取年度检查列表
	 */
	def annualTasks(){
		def taskList=taskAdminService.annualList()
		def taskCounts=taskAdminService.annualCounts()
		render ([taskList:taskList,taskCounts:taskCounts] as JSON)
	}
	/**
	 * 取中期检查列表
	 */
	def midTasks(){
		def taskList=taskAdminService.midList()
//		def taskCounts=taskAdminService.midCounts()
		render ([taskList:taskList,taskCounts:null] as JSON)
	}
	/**
	 * 取结题检查列表
	 */
	def endTasks(){
		def taskList=taskAdminService.endList()
//		def taskCounts=taskAdminService.endCounts()
		render ([taskList:taskList,taskCounts:null] as JSON)
	}
	/**
	 * 确认任务书
	 * @return
	 */
	def confirmTask(){
		def task = QemTask.get(params.id)
		if(task?.runStatus==QemTask.S_SUBMIT){
			task.setRunStatus(QemTask.S_CONFIRM)
			task.setStatus(QemTask.STATUS_ACTIVE)
			task.save(flush:true)
			//创建日志
			QemTaskAudit qemAudit=new QemTaskAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:QemTaskAudit.ACTION_CONFIRM_TASK,
				date:new Date(),
				objectId:task?.id,
				src:task?.class.name])
			qemAudit.save(flush:true)
		}
		showTasks()
	}
	def taskDetail(){
		//		println params.id
		def form_id=params.int('id')?:0
		def task = QemTask.get(form_id)
		if(task){
			def nextId= taskAdminService.checkingNext(form_id,task.status)
			def prevId= taskAdminService.checkingPrev(form_id,task.status)
			render([task:task,
				taskType:task.qemType.name,
				userName:task.teacher.name,
				pager: [nextId:nextId,prevId:prevId],
				fileList:getFileNames(task)] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
//	确认要安排专家评审的阶段检测
	def confirmAll(){
		def type=params.int("type")?:0
//		println type
		def projectids=new ProjectIds(request.JSON)
		if(type && projectids.ids){
			taskAdminService.confirmTasks(projectids.ids,type)
			projectids.ids.each {item->
				//创建评审				
				if(type){
					def review=new Review().save(flush:true)
					def task=QemTask.get(item)
					task.setRunStatus(task.confirm())
					def stage=QemStage.findByTaskAndCurrentStage(task,new Integer(type))
					stage.setStatus(QemStage.S_CONFIRM)
					stage.setReview(review)
					if(!stage.save(flush:true)){
						stage.errors.each {
							println it
						}
					}
				}
				//创建日志
				QemTaskAudit qemAudit=new QemTaskAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:QemTaskAudit.ACTION_CONFIRM_TASK,
					date:new Date(),
					objectId:item,
					src:QemTask.class.name])
				qemAudit.save(flush:true)
			}
		}else{
			render status: HttpStatus.OK
		}
		switch(type){
			case 1: annualTasks()
					break
			case 2: midTasks()
					break
			case 3: endTasks()
					break
		}
		
	}
	def taskForExperts(){
		def taskList=taskAdminService.confirmedList()
		def taskCounts=taskAdminService.confirmCounts()
		def experts=taskAdminService.getExperts()
		render ([taskList:taskList,taskCounts:taskCounts,experts:experts] as JSON)
	}
	/**
	 * 项目阶段报告详情
	 * @return
	 */
	def stageDetail(){
		def task = QemTask.get(params.id)
		if(task){
			def nextId= taskAdminService.checkingStageNext(task.id,task.runStatus)
			def prevId= taskAdminService.checkingStageNext(task.id,task.runStatus)
			render([task:[
						id:task.id,
						projectName:task.projectName,
						sn:task.sn,
						buget:task.fundingProvince+task.fundingUniversity+task.fundingCollege,
						userName:task.teacher.name,
						departmentName:task.department.name,
						qemTypeName:task.qemType.name,
						projectLevel:task.projectLevel,
						collegeAudit:task.collegeAudit,
						beginYear:task.beginYear,
						expectedMid:task.expectedMid,
						expectedEnd:task.expectedEnd,
						projectContent:task.projectContent,
						expectedGain:task.expectedGain,
						members:task.members,
						status:task.status,
						otherLinks:task.otherLinks,
						runStatus:task.runStatus],
					stages:task.stage,
					pager: [nextId:nextId,prevId:prevId],
				fileList:getFileNames(task)] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
	private List<String> getFileNames(def task){
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
			fileNames=fileNames+getFileNames(project)
		}
		return fileNames
	}
	
	/**
	 * 分配专家
	 * @return
	 */
	def doAssigned(){
		def cmd = new AssignCommand(request.JSON)
		def rules = cmd.rules
//		println request.JSON.check1
		def projectids= cmd.projectIds
		if(projectids.ids){
			def reviews=taskAdminService.reviewForExpert(projectids.ids)
			def maxExpert = rules.check2?rules.value2:10
			reviews.each {item->
//				println item.departmentName
			 
				def referenceExperts=""
				def expertNum=0
				def experts
				if(rules.check1){//如果要求专家回避本学院的项目评审，并要求该相关专业的评审资格
					def department = item.departmentName
					experts= taskAdminService.getExperts(department)
				}else{
					experts= taskAdminService.getExperts()
				}
				experts.each {expert->
				
					if(expertNum < maxExpert){
						if(rules.check3){
							def expertCount=taskAdminService.getCounts(expert.id)
							if(expertCount<rules.value3){
									referenceExperts+=";"+expert.id
									expertNum++
							}
						}else {
							referenceExperts+=";"+expert.id
							expertNum++
						}
						
					}
				}
				def review = Review.get(item.id)			
				review.setExperts(referenceExperts)			
				review.save(flush:true)							
			
			}
		}
		taskForExperts()
	}
	/**
	 * 调整专家
	 * @return
	 */
	def doUpdateExpert(){
		def id=request.JSON.id
		def experts=request.JSON.experts
		if(id) {
			Review review =Review.get(id)
			review.setExperts(experts)
			review.save(flush:true)
		}
		render status: HttpStatus.OK
	}
	/**
	 * 返回教师姓名
	 * @return
	 */
	def teacherName(){
		def teacher = Teacher.get(params.id)
		render ([name:teacher?.name] as JSON)
		
	}
	/**
	 * 返回部门和项目类型
	 * @return
	 */
	def selections(){
		def departments =departmentService.listForSection()
		def qemTypes = QemType.getAll()
		render ([departments:departments,qemTypes:qemTypes] as JSON)
	}
	def createTask(){
		def taskCmmd=new TaskFormCommand(request.JSON)
		if(taskCmmd){
			if(taskAdminService.createTask(taskCmmd)) render status: HttpStatus.OK	
			else{
				render status: HttpStatus.BAD_REQUEST
			}
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
		
	}
	def reviewedTaskList(){
		def taskList=taskAdminService.reviewedList()
		render ([taskList:taskList] as JSON)
	}
	/**
	 * 下载附件
	 */
	def downloadAttch_T(Long id){
//		println id
		def task=QemTask.get(id)
		if(task){
//			def basePath= grailsAttributes.getApplicationContext().getResource("/qemUpload/").getFile().toString()
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(task.projectName+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(task,basePath)
			response.outputStream.flush()

		}
	}
	/**
	 * 导出excel
	 * @return
	 */
	def exportTask(long id){
//		println id
		def report=taskAdminService.taskList()
		if(report) {
			def template = grailsApplication.mainContext.getResource("excel/project-summary.xls").getFile()
			def workbook = collegeExportService.exportReport_T_Org(template, report, id)
			response.setContentType("application/excel")
			//转化为中文文件名
			
			def filename=message(code:"qem.fileName.ctask",args:[id?'All_Type':'All_College'])+".xls"
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(filename, "UTF-8")+"\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	/**
	 * 导出报表
	 * @return
	 */
	def report(long id){
//		println id
		def report
		switch(id){
			case 1: report=reportService.reportByCollege()
					break
			case 2: report=reportService.reportByType()
					break
		}		
		
		if(report) {
			def template = grailsApplication.mainContext.getResource("excel/qem_report_${id}.xls").getFile()
			def workbook = reportService.exportReport(template, report)
			response.setContentType("application/excel")
			//转化为中文文件名
			
			def filename=message(code:"qem.report.filename.${id}",args:[])+".xls"
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(filename, "UTF-8")+"\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	/**
	 * 导出所有附件
	 * @return
	 */
	def exportAttach(){
		def report=taskAdminService.exportForAttach()
		if(report) {
			def basePath= grailsApplication.config.tms.qem.uploadPath
			//转化为中文文件名
			def filename=message(code:"qem.fileName.adminAttachT")
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

	/**
	 * 合同审核
	 * @return
	 */
	def auditTaskSave(){
		def audit=new AuditForm(request.JSON)
		def qemTask=QemTask.get(audit.form_id)
//		println audit.form_id
//		qemTask.setCollegeAudit(audit.content)
		int check= Integer.parseInt(audit.check)
		switch(check){
		case 20: qemTask.setRunStatus(qemTask.passAction())
				 qemTask.setStatus(QemTask.STATUS_ACTIVE)
				 break;
		case 21: qemTask.setRunStatus(qemTask.ngAction())
				 qemTask.setStatus(QemTask.STATUS_EXCEPTION_NG)
				 break;
		case 26: qemTask.setRunStatus(qemTask.bkAction())
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
			def taskList=taskAdminService.taskList()
			def taskCounts=taskAdminService.taskCounts()
			render([none:true,taskList:taskList,taskCounts:taskCounts] as JSON)
		}

	}
	/**
	 * 阶段审核
	 * @return
	 */
	def auditStageSave(){
		def type=params.int("type")?:0
				println type
		def audit=new AuditForm(request.JSON)
		def qemTask=QemTask.get(audit.form_id)
		int check= Integer.parseInt(audit.check)
		switch(check){
		case 20: 
				 def afterAction=qemTask.passAction()
				 qemTask.setRunStatus(afterAction)
//				 如果中期通过，标志上已中期，如果结题通过，修改建设情况为已结题
				 if(afterAction==QemTask.S_MID_PASS)
				 	qemTask.setHasMid(true)
				 else if(afterAction==QemTask.S_END_PASS)
				 	qemTask.setStatus(QemTask.STATUS_ENDING)
				def stage=QemStage.findByTaskAndCurrentStage(qemTask,new Integer(type))
					stage?.setStatus(QemStage.S_REVIEW_PASS)
					stage?.save(flush:true)
				 break;
		case 21: qemTask.setRunStatus(qemTask.ngAction())
				 qemTask.setStatus(QemTask.STATUS_EXCEPTION_NG)
				 def stage=QemStage.findByTaskAndCurrentStage(qemTask,new Integer(type))
				 stage?.setStatus(QemStage.S_REVIEW_NG)
				 break;
		case 26: qemTask.setRunStatus(qemTask.bkAction())
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
			stageDetail(Long.parseLong(audit.nextId))
		}else if(audit.prevId!=null && !audit.prevId.equals("null")){
			stageDetail(Long.parseLong(audit.prevId))
		}else{
//			def taskList=taskAdminService.taskList()
//			def taskCounts=taskAdminService.taskCounts()
			switch(type){
			case 1: annualTasks()
					break
			case 2: midTasks()
					break
			case 3: endTasks()
					break
		}
		}

	}
	/**
	 * 备注
	 * @return
	 */
	def updateMemo(){
		def id=request.JSON.id
		def memo=request.JSON.memo
		if(id) {
			QemTask task =QemTask.get(id)
			task.setMemo(memo)
			task.save(flush:true)
		}
		render status: HttpStatus.OK
	}
	/**
	 * 应用专家组
	 * @return
	 */
	def applyGroup(){
		def ids=request.JSON.ids
		def experts=request.JSON.expertGroup
		ids.each {item->
			def task= QemTask.get(item)
			QemStage stage=QemStage.findByTaskAndStatus(task,2)
			stage?.review?.setExperts(experts)
			stage?.save(flush:true)
//			println stage?.review?.experts
//			project?.review.setExperts(experts)
//			project.save()
		}
		taskForExperts()
	}
}
class AssignCommand{
	Rules rules
	ProjectIds projectIds
}
