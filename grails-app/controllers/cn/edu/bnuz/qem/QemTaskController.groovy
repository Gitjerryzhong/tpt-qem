package cn.edu.bnuz.qem

import java.util.Date;
import java.util.List;

import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.qem.project.QemAudit
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.QemStage
import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.qem.project.QemTaskAudit
import cn.edu.bnuz.tms.security.SecurityService;
import grails.converters.JSON
import cn.edu.bnuz.qem.review.Review;

import org.springframework.http.HttpStatus

class QemTaskController {
	
	SecurityService securityService
	TaskService taskService
	ExportService exportService
    def index() {
		
	}
	/***
	 * 显示任务信息
	 * @param id 项目id
	 * @return
	 */
	def showTask(long id){
//		println id
//		def task = securityService.userId.equals(project?.teacher.id)?project?.qemTask:null
		def isUpdateRequest=params.boolean("update")?:false
		println isUpdateRequest
		def task = QemTask.get(id)
		def stages = task?.stage
		def havingStage = []
		stages?.each {item->
			havingStage+=["${item.currentStage}"]
		}
		if(task?.teacher.id==securityService.userId){
			def project =QemProject.get(task.projectId)
			def auditContent
			if(project){
				auditContent=project?.review?.detail
			}
			render (view:"taskMenagement",model:[
					task:[
						id:task.id,
						projectName:task.projectName,
						sn:task.sn,
						fundingProvince:task.fundingProvince,
						fundingUniversity:task.fundingUniversity,
						fundingCollege:task.fundingCollege,
						userName:task.teacher.name,
						departmentName:task.department.name,
						qemTypeName:task.qemType.name,
						projectLevel:task.projectLevel,
						beginYear:task.beginYear,
						expectedMid:task.expectedMid,
						expectedEnd:task.expectedEnd,
						projectContent:task.projectContent,
						expectedGain:task.expectedGain,
						memberstr:task.members,
						status:task.status,
						havingStage:havingStage,
						otherLinks:task.otherLinks,
						runStatus:task.runStatus,
						contractAudit:task.contractAudit,
						auditContent:auditContent
						],
					notice:taskService.getLastCheckNotice(),
					isUpdate:[isUpdateRequest],
					fileList:getFileNames(task.id.toString())
				])			
		}
		
	}
	def getTask(){
		def id=params.long("id")?:0
				def task = QemTask.get(id)
				def stages = task?.stage
				def havingStage = []
				stages?.each {item->
					havingStage+=["${item.currentStage}"]
				}
				if(task?.teacher.id==securityService.userId){
					def project =QemProject.get(task.projectId)
					def auditContent
					if(project){
						auditContent=project?.review?.detail
					}
					render ([task:[
								id:task.id,
								projectName:task.projectName,
								sn:task.sn,
								fundingProvince:task.fundingProvince,
								fundingUniversity:task.fundingUniversity,
								fundingCollege:task.fundingCollege,
								userName:task.teacher.name,
								departmentName:task.department.name,
								qemTypeName:task.qemType.name,
								projectLevel:task.projectLevel,
								beginYear:task.beginYear,
								expectedMid:task.expectedMid,
								expectedEnd:task.expectedEnd,
								projectContent:task.projectContent,
								expectedGain:task.expectedGain,
								memberstr:task.members,
								status:task.status,
								havingStage:havingStage,
								otherLinks:task.otherLinks,
								runStatus:task.runStatus,
								contractAudit:task.contractAudit,
								auditContent:auditContent
								],
							fileList:getFileNames(task.id.toString())
						] as JSON)
				}
				
			}
	/***
	 * 撤回任务书
	 * @return
	 */
	def cancelTask(){
		def taskId=params.long("id") ?: 0
		def task = QemTask.get(taskId)
		if(task?.teacher.id.equals(securityService.userId)){
			if( task.runStatus==QemTask.S_SUBMIT){
				task.setRunStatus(QemTask.S_NEW)
				task.save(flush:true)
				QemTaskAudit qemAudit=new QemTaskAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:QemTaskAudit.ACTION_CANCEL_TASK,
					date:new Date(),
					objectId:task.id,
					src:task.class.name])
				qemAudit.save(flush:true)
				render ([
							task:[
								id:task.id,
								projectName:task.projectName,
								sn:task.sn,
								fundingProvince:task.fundingProvince,
								fundingUniversity:task.fundingUniversity,
								fundingCollege:task.fundingCollege,
								userName:task.teacher.name,
								departmentName:task.department.name,
								qemTypeName:task.qemType.name,
								projectLevel:task.projectLevel,
								beginYear:task.beginYear,
								expectedMid:task.expectedMid,
								expectedEnd:task.expectedEnd,
								projectContent:task.projectContent,
								expectedGain:task.expectedGain,
								memberstr:task.members,
								status:task.status,
								otherLinks:task.otherLinks,
								runStatus:task.runStatus
								],
							fileList:getFileNames(task.id.toString())] as JSON)
			}else{
				render status: HttpStatus.BAD_REQUEST
			}
		}else{
		render status: HttpStatus.BAD_REQUEST
		}
	}
	/***
	 * 提交任务书
	 * @return
	 */
	def applyTask(){
		def taskId=params.long("id") ?: 0
//		println taskId
		def task = QemTask.get(taskId)
		if(task?.teacher.id.equals(securityService.userId)){
			if( task.runStatus==QemTask.S_NEW ||task.runStatus==QemTask.S_BK_C){
				task.setRunStatus(QemTask.S_SUBMIT)
				task.save(flush:true)
				QemTaskAudit qemAudit=new QemTaskAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:QemTaskAudit.ACTION_SUBMIT_TASK,
					date:new Date(),
					src:task.class.name,
					objectId: task.id])
				qemAudit.save(flush:true)
					render ([
							task:[
								id:task.id,
								projectName:task.projectName,
								sn:task.sn,
								fundingProvince:task.fundingProvince,
								fundingUniversity:task.fundingUniversity,
								fundingCollege:task.fundingCollege,
								userName:task.teacher.name,
								departmentName:task.department.name,
								qemTypeName:task.qemType.name,
								projectLevel:task.projectLevel,
								beginYear:task.beginYear,
								expectedMid:task.expectedMid,
								expectedEnd:task.expectedEnd,
								projectContent:task.projectContent,
								expectedGain:task.expectedGain,
								memberstr:task.members,
								status:task.status,
								otherLinks:task.otherLinks,
								runStatus:task.runStatus
								],
							fileList:getFileNames(task.id.toString())] as JSON)
//				render ([fileList:getFileNames(task.id.toString())]as JSON)
			}else{
				render status: HttpStatus.BAD_REQUEST
			}
		}else{
		render status: HttpStatus.BAD_REQUEST
		}
	}
	/**
	 * 删除附件
	 * @return
	 */
	def removeAtt(){
		def filename=params.filename
		def projectId=params.projectId
		def project=QemTask.get(projectId)
		if(filename && (project?.runStatus==QemTask.S_NEW || project?.runStatus==QemTask.S_BK_C)){
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/task/"+securityService.userId+"/"+projectId
			if(filename.indexOf("___")!=-1){
				def filenames=filename.split ("___")
				File file=new File(filePath+"/"+filenames[0]+"/"+filenames[1])
				file?.renameTo(filePath+"/"+filenames[0]+"/del_"+filenames[1]+"."+Math.round(Math.random()*100))
			}else{
				File file=new File(filePath+"/"+filename)
				file?.renameTo(filePath+"/del_"+filename)
			}
			render ([fileList:getFileNames(projectId)]as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
	/***
	 * 获取指定项目的任务书实体，与showTask配合使用
	 * @return
	 */
//	def getTask(){
//		println params.id
//		def project = QemProject.get(params.id)
//		def task = securityService.userId.equals(project?.teacher.id)?project?.qemTask:null
//		if(task){
//			render([task:[
//				projectId:params.id,
//				id:task.id,
//				sn:task.sn,
//				cycle:task.cycle,
//				budget:task.fundingProvince+task.fundingUniversity+task.fundingCollege,
//				beginYear:task.beginYear,
//				expectedMid:task.expectedMid,
//				expectedEnd:task.expectedEnd,
//				projectContent:task.projectContent,
//				memberstr:task.members,
//				status:task.status,
//				expectedGain:task.expectedGain],
//				workType:Notice.last().workType,
//				fileList:getFileNames(project.id.toString())] as JSON)
//		}else{
//			render status: HttpStatus.BAD_REQUEST
//		}
//	}
	/***
	 * 保存任务书
	 * @return
	 */
	def saveTask(){
		def taskCommand=new TaskCommand(request.JSON)
//		println taskCommand.id
		def task = QemTask.get(taskCommand.id)
//		println task?.teacher.id
		if(task?.teacher.id.equals(securityService.userId)){
//			println task?.teacher.id
				task.setMembers(taskCommand.memberstr)
				if(taskCommand.projectContent && taskCommand.projectContent!='null')
					task.setProjectContent(taskCommand.projectContent)
				if(taskCommand.expectedGain && taskCommand.expectedGain!='null')
				task.setExpectedGain(taskCommand.expectedGain)
	//			qemTask.setStatus(QemTask.STATUS_SUBMIT)
				if(!task.save(flush:true)){
					task.errors.each {
						println it
					}					
				}
				render ([fileList:getFileNames(task?.id.toString())]as JSON)
		}else{
				render status: HttpStatus.BAD_REQUEST
		}
	}
	/***
	 * 查询阶段报告内容，若要查询的是当前应检查的阶段又未创建，则新建一个实体
	 * @return
	 */
	def createStage(){
		def taskId=params.long("taskId")
		def stageNum= params.int("stageNum")?:0
//		println stageNum	
		def task =QemTask.get(taskId)			
		if(task?.teacher.id.equals(securityService.userId) && stageNum){
			def currentStage=getCurrentStage(task)			
			def stage = QemStage.findByTaskAndCurrentStage(task, stageNum)
			if(!stage && stageNum==currentStage){	//如果当前进行阶段是所查询阶段，且在数据库中还未创建，则创建当前阶段实体
				stage = new QemStage(currentStage:currentStage)
				stage.task=task
				stage.submitYear = new Date().format("yyyy")
//				stage.review=new Review(status:Review.STATUS_NEW).save()
				if(!stage.save(flush:true)){
					stage.errors.each {
						println it
					}
				}
//			设置流程状态为新阶段的开始
				switch(currentStage){
					case QemStage.STAGE_ANNUAL: task.setRunStatus(QemTask.S_ANNUAL_START)
												break;
					case QemStage.STAGE_MIDDLE: task.setRunStatus(QemTask.S_MID_START)
												break;
					case QemStage.STAGE_ENDING: task.setRunStatus(QemTask.S_END_START)
												break;
				}
				task?.save(flush:true)
			}

			render ([stage:[
				id:				stage?.id,
				sn:				task?.sn,
				members:		task?.members,
				taskId:			task?.id,
				projectName:	task?.projectName,
				currentStage:	stage?.currentStage,
				fundingProvince:	stage?.fundingProvince,
				fundingUniversity:	stage?.fundingUniversity,
				fundingCollege:		stage?.fundingCollege,
				finishDate:			stage?.finishDate,
				progressText:		stage?.progressText?:"",
				unfinishedReson:	stage?.unfinishedReson?:"",
				memo:				stage?.memo?:"",
				endAudit:			stage?.endAudit,
				status:				stage?.status],
			fileList:getFileNames(task.id.toString(),currentStage)] as JSON)
		}else{
			render status:HttpStatus.BAD_REQUEST
		}
		
		
	}
	/***
	 * 保存阶段报告
	 * @return
	 */
	def saveStage(){
		def stageCommand = new StageCommand(request.JSON)
		def task =QemTask.get(stageCommand.taskId)
		def qemStage = securityService.userId.equals(task?.teacher.id)? QemStage.get(stageCommand.id):null
		if(qemStage){
			qemStage.setFinishDate( Date.parse("yyyy-MM-dd" , stageCommand.finishDate))
			qemStage.setProgressText(stageCommand.progressText)
			qemStage.setUnfinishedReson(stageCommand.unfinishedReson)
			qemStage.setMemo(stageCommand.memo)
			qemStage.save(flush:true)
			render status: HttpStatus.OK
			
		}else{
			render status:HttpStatus.BAD_REQUEST
		}
	}
	/***
	 * 提交阶段报告
	 * @return
	 */
	def applyStage(){
		def stageCommand = new StageCommand(request.JSON)
		def task =QemTask.get(stageCommand.taskId)
		def stage = securityService.userId.equals(task?.teacher.id)? QemStage.get(stageCommand.id):null
		if(stage && !stage.status){
			stage.setFinishDate( Date.parse("yyyy-MM-dd" , stageCommand.finishDate))
			stage.setProgressText(stageCommand.progressText)
			stage.setUnfinishedReson(stageCommand.unfinishedReson)
			stage.setMemo(stageCommand.memo)
			stage.setStatus(QemStage.S_SUBMIT)
			stage.save(flush:true)
			def currentStage=getCurrentStage(task)
			switch(currentStage){
				case QemStage.STAGE_ANNUAL: task.setRunStatus(QemTask.S_ANNUAL_SUBMIT)
											break;
				case QemStage.STAGE_MIDDLE: task.setRunStatus(QemTask.S_MID_SUBMIT)
											break;
				case QemStage.STAGE_ENDING: task.setRunStatus(QemTask.S_END_SUBMIT)
											break;
			}
			
			task.save(flush:true)
			QemTaskAudit qemAudit=new QemTaskAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:QemTaskAudit.ACTION_SUBMIT_STAGE,
				date:new Date(),
				src:task.class.name,
				objectId: task.id])
			qemAudit.save(flush:true)
			render ([stage:[
				id:				stage?.id,
				sn:				task?.sn,
				members:		task?.members,
				taskId:			task?.id,
				projectName:	task?.projectName,
				currentStage:	stage?.currentStage,
				fundingProvince:	stage?.fundingProvince,
				fundingUniversity:	stage?.fundingUniversity,
				fundingCollege:		stage?.fundingCollege,
				finishDate:			stage?.finishDate,
				progressText:		stage?.progressText?:"",
				unfinishedReson:	stage?.unfinishedReson?:"",
				memo:				stage?.memo?:"",
				status:				stage?.status],
					fileList:getFileNames(task.id.toString(),currentStage),
					runStatus:task?.runStatus] as JSON)
			
		}else{
			render status:HttpStatus.BAD_REQUEST
		}
			
	}
	def cancelStage(){
		def taskId=params.long("taskId")?: 0
		def id  =params.long("id")?:0
		def task =QemTask.get(taskId)
		def stage = securityService.userId.equals(task?.teacher.id)? QemStage.get(id):null
		if(stage?.status == QemStage.S_SUBMIT){
//			设置流程状态为新阶段的开始
			def currentStage=getCurrentStage(task)
			switch(currentStage){
				case QemStage.STAGE_ANNUAL: task.setRunStatus(QemTask.S_ANNUAL_START)
											break;
				case QemStage.STAGE_MIDDLE: task.setRunStatus(QemTask.S_MID_START)
											break;
				case QemStage.STAGE_ENDING: task.setRunStatus(QemTask.S_END_START)
											break;
			}
			
			task.save(flush:true)
			
			stage.setStatus(QemStage.S_NEW)
			stage.save(flush:true)
			QemTaskAudit qemAudit=new QemTaskAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:QemTaskAudit.ACTION_CANCEL_STAGE,
				date:new Date(),
				src:task.class.name,
				objectId: task.id])
			qemAudit.save(flush:true)
			render ([stage:[
				id:				stage?.id,
				sn:				task?.sn,
				members:		task?.members,
				taskId:			task?.id,
				projectName:	task?.projectName,
				currentStage:	stage?.currentStage,
				fundingProvince:	stage?.fundingProvince,
				fundingUniversity:	stage?.fundingUniversity,
				fundingCollege:		stage?.fundingCollege,
				finishDate:			stage?.finishDate,
				progressText:		stage?.progressText?:"",
				unfinishedReson:	stage?.unfinishedReson?:"",
				memo:				stage?.memo?:"",
				status:				stage?.status],
					fileList:getFileNames(task.id.toString(),currentStage),
					runStatus:task?.runStatus] as JSON)			
		}else{
		render status: HttpStatus.BAD_REQUEST
		}
	}
	/***
	 * 上传文件
	 * @return
	 */
	def uploadFiles(){
		
		def taskId=params.taskId
		def isDeclaration =params.isDeclaration
		java.util.List<String> fileList = getFileNames(taskId)
		if(!fileList) fileList=new java.util.ArrayList<String>()
		def f = request.getFile('file')
		if(!f.empty) {
			//计算文件前缀
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/task/"+securityService.userId+"/"+taskId
			if(isDeclaration) filePath+="/"+isDeclaration
			File dir = new File(filePath)
			if(!dir.isDirectory()){
				dir.mkdirs()
			}
			def filename=f.originalFilename
//			申报书增加时间戳
			def sbs=message(code:"qem.stageIndex.${100}",args:[])
			def fileType=filename.substring(filename.lastIndexOf("."))
			if(sbs==isDeclaration){
				filename=filename.substring(0,filename.lastIndexOf("."))+"${new Date().format('yyyyMMddHHmm')}${fileType}"
			}			
			fileList.add("${sbs}___${filename}")
			f.transferTo( new File(filePath+"/"+filename) )
			render ( [fileList:fileList] as JSON)
		}
	}
	
	/***
	 * 删除附件
	 */
	def delAttach(){
		def filename = params.filename
		def taskId=params.taskId
		def isDeclaration =params.isDeclaration
		if(filename){
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/task/${securityService.userId}/${taskId}/${isDeclaration}"
//			println filePath+"/"+filename
			File file = new File(filePath+"/"+filename)
			def date = new Date()
			file?.renameTo(filePath+"/del_"+filename+".${date.time}")
			render status:HttpStatus.OK
		}else render status:HttpStatus.BAD_REQUEST
	}
	/**
	 * 下载附件
	 * @return
	 */
	def download(){
		def taskId=params.taskId
		def fileType =params.fileType
		if(taskId){
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/task/${securityService.userId}/${taskId}/${fileType}"
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
	/***
	 * 用于获取指定项目的所有附件
	 * @param projectId
	 * @return
	 */
	private List<String> getFileNames(String taskId){
		def filenames = getFileNames(taskId,-1)
		return filenames
	}
	private List<String> getFileNames(String taskId,int stageIndex){
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/task/"+securityService.userId+"/"+taskId
		def stageDir="ALL"
		if(stageIndex!=-1)
			stageDir=message(code:"qem.stageIndex.${stageIndex}",args:[])
		def con_dir100=message(code:"qem.stageIndex.${100}",args:[])
		def con_dir0=message(code:"qem.stageIndex.${0}",args:[])
		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(dir.isDirectory()){
			for(File file: dir.listFiles()){
				if(file.isDirectory()){ //如果是子目录，如果是ALL表示只读取合同和申报书，否则按阶段读取附件
					if((stageDir=="ALL" && ( file.name==con_dir100|| file.name==con_dir0)) || file.name==stageDir)
						for(File f:file.listFiles()){
							if(f.name.indexOf("del_")==-1) fileNames.add(file.name+"___"+f.name)
						}
				}else if(file.name.indexOf("del_")==-1){
					fileNames.add(file.name)
				}
			}
		}
		return fileNames
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
class TaskCommand{
	String id
	String sn
	Integer cycle
	Integer budget
	String beginYear
	Integer expectedMid
	Integer expectedEnd
	String projectContent
	String memberstr
	String expectedGain
	Integer status
	Long projectId
}
class StageCommand{
	Long id
	Long taskId
	String finishDate
	String progressText
	String unfinishedReson
	String memo
}

