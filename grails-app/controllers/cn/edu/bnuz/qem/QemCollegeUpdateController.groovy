package cn.edu.bnuz.qem
import java.util.List;

import cn.edu.bnuz.tms.organization.Teacher;
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.qem.project.QemTask;
import cn.edu.bnuz.qem.project.QemType;
import cn.edu.bnuz.qem.update.UpdateTask
import grails.converters.JSON

import org.springframework.http.HttpStatus
class QemCollegeUpdateController {
	CollegeService collegeService
	SecurityService securityService
	ExportService exportService
    def index() { }
	def contractList(){
		def taskList=collegeService.taskList()
		def taskCounts=collegeService.taskUpdateCounts()
		render ([taskList:taskList,taskCounts:taskCounts] as JSON)
	}
	def historyRequestList(){
		def taskList=collegeService.taskUpdateList()
		def taskCounts=collegeService.taskUpdateCounts()
		render ([taskList:taskList,taskCounts:taskCounts] as JSON)
	}
	def requestList(){
		render([updateList:collegeService.myUpdateList()] as JSON)
	}
	/**
	 * 准备变更表单
	 * @return
	 */
	def updateForm(){
		//		println params.id
		def form_id=params.int('id')?:0
//		println form_id
		def task = QemTask.get(form_id)
		if(task){
			render([task:[
					taskId:task.id,
					projectName:task.projectName,
					teacherId:task.teacher.id,
					sn:task.sn,
					userName:task.teacher.name,
					currentDegree:task.currentDegree,
					position:task.position,
					currentTitle:task.currentTitle,
					phoneNum:task.phoneNum,
					specailEmail:task.specailEmail,
					members:task.members,
					qemTypeId:task.qemType.id,
					projectLevel:task.projectLevel,
					beginYear:task.beginYear,
					expectedMid:task.expectedMid,
					expectedEnd:task.expectedEnd,
					expectedGain:task.projectContent,
					expectedGain:task.expectedGain],
				qemTypes:QemType.findAll("from QemType as t order by t.name"),
				teachers:Teacher.findAllById(task.teacher.id),				
				fileList:getFileNames_T(task)] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
	/***
	 * 用于获取指定项目的所有附件
	 * @param projectId
	 * @return
	 */
	private List<String> getFileNames_T(QemTask task){
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/task/"+task.teacher.id+"/"+task.id
		
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
	/**
	 * 返回教师姓名
	 * @return
	 */
//	def teacherName(){
//		def teacherName
//		def teacher = Teacher.get(params.id)
//		if(teacher?.department?.id==securityService.departmentId) 
//			teacherName=teacher?.name
//		render ([name:teacherName] as JSON)
//		
//	}
	/**
	 * 返回教师姓名
	 * @return
	 */
	def getTeachers(){
		def teachers
		def teacherName = params.teacherName
		if(teacherName){
			teachers=Teacher.findAllByDepartmentAndNameLike(Department.get(securityService.departmentId),"${teacherName}%")
		}
		render ([teachers:teachers] as JSON)
		
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
				updation.setFlow(2)
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
	def updateDetail(){
		def form_id=params.int('id')?:0
		def form = UpdateTask.get(form_id)
		if(form?.departmentId==securityService.departmentId){
			def task=QemTask.get(form.taskId)
			render([form:form,
					teacherName: Teacher.get(form.teacherId)?.name,
					type:QemType.get(form.qemTypeId)?.name,
					commitDate:form.commitDate.format("yyyy-MM-dd"),
					task:task,
					origTeacherName:task?.teacher.name,
					origType:task?.qemType.name,
					fileList:getFileNames(form.taskId.toString(),form.commitDate.format("yyyyMMdd"))] as JSON)
		}
	}
	/***
	 * 上传文件
	 * @return
	 */
	def uploadFiles(){
		def dateStr=new Date().format("yyyyMMdd")
		def taskId=params.taskId
		def isDeclaration =params.isDeclaration
		java.util.List<String> fileList = getFileNames(taskId,dateStr)
		if(!fileList) fileList=new java.util.ArrayList<String>()
		def f = request.getFile('file')
		if(!f.empty) {
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${securityService.departmentId}/${taskId}/${dateStr}"
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
	 * 下载指定变更单下的附件
	 * @param id
	 * @return
	 */
	def downloadAttch(Long id){
		def form=UpdateTask.get(id)
		println form.class.name
		if(form && securityService.departmentId==form.departmentId){
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(form.projectName+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(form,basePath)
			response.outputStream.flush()

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
			def dateStr=new Date().format("yyyyMMdd")
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${securityService.departmentId}/${taskId}/${dateStr}/${isDeclaration}"
//			println filePath+"/"+filename
			File file = new File(filePath+"/"+filename)
			def date = new Date()
			file?.renameTo(filePath+"/del_"+filename+".${date.time}")
			render status:HttpStatus.OK
		}else render status:HttpStatus.BAD_REQUEST
	}
	
	/***
	 * 用于获取指定项目变更单和指定日期的所有附件
	 * @param projectId
	 * @return
	 */
	private List<String> getFileNames(String taskId,String dateStr){
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/"+taskId+"/"+dateStr
		
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
	def getLeaderName(){
		def leaderId=params.id
		if(leaderId) {
			def teacher =Teacher.get(leaderId)
			if(teacher.department.id == securityService.departmentId) 
				render ([name:teacher.name] as JSON)
		}else render ([name:null] as JSON)
	}
}
