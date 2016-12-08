package cn.edu.bnuz.qem
import java.util.List

import cn.edu.bnuz.qem.update.UpdateTask
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.tms.security.SecurityService;
import grails.converters.JSON

import org.springframework.http.HttpStatus
class QemTaskUpdateController {
	
	TaskService taskService
	SecurityService securityService
    def index() { }
	def contractList(){
		def taskList=taskService.taskList()
		render ([taskList:taskList] as JSON)
	}
	/***
	 * 上传文件
	 * @return
	 */
	def uploadFiles(){
		def dateStr=new Date().format("yyyyMMdd")
//		处理申请单修改状态下文件上传
		def updateId = params.updateId
		if(updateId){
			def updateView= UpdateTask.get(updateId)
			dateStr=updateView.commitDate.format("yyyyMMdd")
		}
		def taskId=params.taskId
		def isDeclaration =params.isDeclaration
		java.util.List<String> fileList = getFileNames(taskId,dateStr)
		if(!fileList) fileList=new java.util.ArrayList<String>()
		def f = request.getFile('file')
		if(!f.empty) {
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${securityService.userId}/${taskId}/${dateStr}"
			if(isDeclaration) filePath+="/"+isDeclaration
			File dir = new File(filePath)
			if(!dir.isDirectory()){
				dir.mkdirs()
			}
			def filename=f.originalFilename
			fileList.add(filename)
			f.transferTo( new File(filePath+"/"+filename) )
			render ( [fileList:fileList] as JSON)
		}
	}
	/**
	 * 获取上传的申报书
	 */
	def fileList(){
		def dateStr=new Date().format("yyyyMMdd")
		def taskId=params.taskId
		java.util.List<String> fileList = getFileNames(taskId,dateStr)
		render ([fileList:fileList] as JSON)
	}
	/***
	 * 用于获取指定项目变更单和指定日期的所有附件
	 * @param projectId
	 * @return
	 */
	private List<String> getFileNames(String taskId,String dateStr){
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${securityService.userId}/${taskId}/${dateStr}"
		
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
		return fileNames
	}
	/***
	 * 用于获取指定项目原有附件
	 * @param projectId
	 * @return
	 */
	private List<String> getFileNames(String taskId){
		def filenames = getFileNames_Old(taskId,-1)
		return filenames
	}
	/***
	 * 用于获取指定项目的所有附件
	 * @param projectId
	 * @return
	 */
	private List<String> getFileNames_Old(String taskId,int stageIndex){
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/task/"+securityService.userId+"/"+taskId
		def stageDir="ALL"
		if(stageIndex!=-1)
			stageDir=message(code:"qem.stageIndex.${stageIndex}",args:[])
		def con_dir100=message(code:"qem.stageIndex.${100}",args:[])
		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(dir.isDirectory()){
			for(File file: dir.listFiles()){
				if(file.isDirectory()){ //如果是子目录，如果是ALL表示只读取申报书，否则按阶段读取附件
					if((stageDir=="ALL" && file.name==con_dir100) || file.name==stageDir)
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
	 * 删除附件
	 */
	def delAttach(){
		def dateStr=new Date().format("yyyyMMdd")
//		处理申请单修改状态下文件上传
		def updateId = params.updateId
		if(updateId){
			def updateView= UpdateTask.get(updateId)
			dateStr=updateView.commitDate.format("yyyyMMdd")
		}

		def filename = params.filename
		def taskId=params.taskId
		def isDeclaration =params.isDeclaration
		if(filename){
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${securityService.userId}/${taskId}/${dateStr}/${isDeclaration}"
//			println filePath+"/"+filename
			File file = new File(filePath+"/"+filename)
			def date = new Date()
			file?.renameTo(filePath+"/del_"+filename+".${date.time}")
			render status:HttpStatus.OK
		}else render status:HttpStatus.BAD_REQUEST
	}
	/**
	 * 提交变更申请
	 * @return
	 */
	def updateCommit(){
		def updation= new UpdateTask(request.JSON)
		if(updation){
				updation.setCommitDate(new Date())
				updation.setUserId(securityService.userId)
				updation.setUserName(securityService.userName)
				updation.setFlow(1)
				updation.setAuditStatus(0)
				if(!updation?.save(flush:true)){
					updation.errors.each {
						println it
					}
					render status: HttpStatus.BAD_REQUEST
				}else{
					render status: HttpStatus.OK
				}
			}else 	render status: HttpStatus.BAD_REQUEST 
	}
	/**
	 * 提交变更申请
	 * @return
	 */
	def editCommit(){
		def updation= request.JSON
		if(updation?.id && updation.updateTypes){
			def task = UpdateTask.get(updation.id)
			if(!task) return
			def updateTypes=updation.updateTypes.split(";")
			updateTypes.each {item->
				def itemValue=item.toInteger()
//				println itemValue
				switch(itemValue){			
				case 2:
						task.setExpectedMid(updation.expectedMid)
						task.setExpectedEnd(updation.expectedEnd)
						break;
				case 3:
						task.setProjectName(updation.projectName)
						break;
				case 4:
						task.setProjectContent(updation.projectContent)
						break;
				case 6:
						task.setExpectedGain(updation.expectedGain)
						break;
				case 7:
						task.setMembers(updation.members)
						break;
				case 8:task.setOthers(updation.others)
						break;
				}
			}
			task?.setMemo(updation.memo)
			task?.setUpdateTypes(updation.updateTypes)
			task?.setFlow(1)
			task?.setAuditStatus(0)
			if(!task?.save(flush:true)){
				task.errors.each {
					println it
				}
				render status: HttpStatus.BAD_REQUEST
			}else{
				render status: HttpStatus.OK
			}
		}else 	render status: HttpStatus.BAD_REQUEST
	}
	def getUpdateList(){
		render ([updateList:taskService.updateList()] as JSON)
	}
	def updateDetail(){
		def updateId=params.long("id")
		def updateView
		def task
		def fileList
		def declarations
		if(updateId){
			updateView = UpdateTask.get(updateId)
			if(updateView.userId == securityService.userId){
				task = QemTask.get(updateView.taskId)
			}
			fileList = getFileNames("${updateView.taskId}",updateView.commitDate.format("yyyyMMdd"))
			declarations = getFileNames("${updateView.taskId}")
		}
		render ([updateView:updateView,task:task,fileList: fileList,declarations:declarations] as JSON)
	}
	private doUpdate(updation){
		def updateTypes=updateTask.updateTypes.split(";")
		updateTypes.each {item->
			def itemValue=item.toInteger()
//			println itemValue
			switch(itemValue){
			case 1:oldData=[teacherId:task.teacher.id,
					currentTitle:task.currentTitle,
					currentDegree:task.currentDegree,
					specailEmail:task.specailEmail,
					phoneNum:task.phoneNum,
					position:task.position]
				   task.setTeacher(Teacher.get(updateTask.teacherId))
				   task.setCurrentTitle(updateTask.currentTitle)
				   task.setCurrentDegree(updateTask.currentDegree)
				   task.setSpecailEmail(updateTask.specailEmail)
				   task.setPhoneNum(updateTask.phoneNum)
				   task.setPosition(updateTask.position)
					break;
			case 2:oldData=[expectedMid:task.expectedMid,
					expectedEnd:task.expectedEnd]
					task.setExpectedMid(updateTask.expectedMid)
					task.setExpectedEnd(updateTask.expectedEnd)
					if(!task.delay)task.setDelay(1)
					else task.setDelay(task.delay+1)
					break;
			case 3:oldData=[projectName:task.projectName]
					task.setProjectName(updateTask.projectName)
					break;
			case 4:oldData=[projectContent:task.projectContent]
					task.setProjectContent(updateTask.projectContent)
					break;
			case 5:oldData=[status:task.status,
					runStatus:task.runStatus]
					task.setStatus(QemTask.STATUS_EXCEPTION_NG)
					task.setRunStatus(QemTask.S_NG)
					break;
			case 6:oldData=[expectedGain:task.expectedGain]
					task.setExpectedGain(updateTask.expectedGain)
					break;
			case 7:oldData=[members:task.members]
					task.setMembers(updateTask.members)
					break;
			case 8:task.setMemo("${task.memo};${updateTask.others}")
					break;
			}
		}
	}
}
