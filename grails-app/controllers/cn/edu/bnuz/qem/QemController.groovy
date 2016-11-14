package cn.edu.bnuz.qem

import java.util.List;

import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.qem.project.QemAudit
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.QemProject
import grails.converters.JSON
import cn.edu.bnuz.qem.project.QemType
import cn.edu.bnuz.qem.project.QemParentType
import cn.edu.bnuz.qem.review.Review;
import cn.edu.bnuz.tms.organization.DepartmentService
import cn.edu.bnuz.tms.security.SecurityService;
import grails.converters.JSON
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tpt.util.WriteTable

import org.springframework.http.HttpStatus

class QemController {
	DepartmentService departmentService
	SecurityService securityService
	ProjectService projectService
	CollegeService collegeService	
	ExportService exportService
    def index() {
		render (view:"index",model:[counts:projectService.remindCounts()])
	}
	def showNotice(){
		def  activeNotices = Notice.findAll ("from Notice as n order by n.id desc")
		render ([notices:activeNotices,ifNotEnding:projectService.ifNotEnding()] as JSON)
	}
	def getSelections(){
//		def createAble=checkActionAble(Notice.PROJECT_REQUEST)
//		在前台已经做过检验，只有在有效时段并相应通知是项目申报才可以点申报按钮，这里就不再重复检验
//		if(createAble){
			def departments =departmentService.listForSection()
			def project= projectService.lastProject()
			def qemTypes = QemType.findAllByActived(false)
			render ([departments:departments,
					qemTypes:qemTypes,
					project:project,
					myDepartmentId:securityService.departmentId,
					createAble:true]as JSON)			
//		}else{
//			render ([createAble:createAble]as JSON)
//		}
		
	}
	
	def editProject(){
//		def createAble=checkActionAble(Notice.PROJECT_REQUEST)
//		if(createAble){
			def departments =departmentService.listForSection()
//			def majors= projectService.majorForList()
			def qemTypes = QemType.getAll()
			def projectId=params.long("projectId") ?: 0
			def project =null
			if(projectId) project= QemProject.get(projectId)
//			println projectId
			if(project){
				render([project:[
					id:project.id,
					currentTitle:project.currentTitle,
				currentDegree:project.currentDegree,
				specailEmail:project.specailEmail,
				discipline:project.discipline,
				direction:project.direction,
				departmentId:project.department.id,
				qemTypeId:project.qemType.id,
				projectName:project.projectName,
				expectedGain:project.expectedGain,
				commit:project.isSubmit,
				collegeStatus:project.collegeStatus,
				phoneNum:project.phoneNum,
				position:project.position,
				majorId:project.major,
				otherLinks:project.otherLinks,
				projectLevel:project.projectLevel.toString(),
				status:project.review.status],
			departments:departments,
			qemTypes:qemTypes,
//			majors:majors,
			myDepartmentId:securityService.departmentId,
			createAble:true,
			fileList:getFileNames(project.id.toString())] as JSON)
			}
//		}else{
//			render status:HttpStatus.BAD_REQUEST
//		}
		
	}
	
	private List<String> getFileNames(String projectId){
//		Notice notice= Notice.last()
//		def filePath= grailsAttributes.getApplicationContext().getResource("/qemUpload/").getFile().toString()+"/"+securityService.userId+"/"+projectId
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/"+securityService.userId+"/"+projectId
		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(dir.isDirectory()){
			for(File file: dir.listFiles()){
//				fileNames.add("/tms/qemUpload/"+securityService.userId+"/"+projectId+"/"+file.name)
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
	def checkActionAble(String action){
		Notice notice= Notice.last()
		def today =new Date()
		return (today.after(notice.start) && today.before(notice.end) && notice.workType?.equals(action))		
	}
	def saveProject(){
//		在前台已经做过检验，只有在有效时段并相应通知是项目申报才可以点申报按钮，这里就不再重复检验
//		if(checkActionAble(Notice.PROJECT_REQUEST)){
			def projectCommand =new ProjectCommand(request.JSON)
//			println projectCommand.position
			def qemProject
			if(projectCommand?.id==0){
				def project=projectService.create(projectCommand)
				render([projectId:project?.id]as JSON)
			}else {			
				def project=projectService.update(projectCommand)
				if(project && Boolean.parseBoolean(projectCommand.commit)){  //如果要提交，并且允许提交（如果不允许，projectService.update会返回null，添加操作日志
					QemAudit qemAudit=new QemAudit([
						userId:securityService.userId,
						userName:securityService.userName,
						action:QemAudit.ACTION_COMMIT,
						date:new Date(),
						form:project])
					qemAudit.save(flush:true)
					render([projectId:project?.id]as JSON)
				}else{
					render([projectId:project?.id]as JSON)
				}
			}
//		}else{
//			render status:HttpStatus.BAD_REQUEST
//		}
	}
	def getTypes(){
		render ([qemParentTypes:QemParentType.getAll(),
				qemTypes:QemType.findAllByActived(false)]as JSON)
	}
	def download(Long id){
		def qemType=QemType.get(id)
		if(qemType){
			def basePath= grailsAttributes.getApplicationContext().getResource("/template/").getFile().toString()
			def filename =qemType.downLoadUrl
//
			def type=filename.substring(filename.lastIndexOf("."))
			response.setContentType("application/word")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(qemType.name+type, "UTF-8")+"\"")
			def inf = new FileInputStream(basePath+"/"+filename)
			def out = response.getOutputStream();
			int b;
			while((b=inf.read())!= -1)
			{
				out.write(b);
			}			  
			inf.close();
			out.close();		

		}
	}
	def downloadAttch(Long id){
		def qemProject=QemProject.get(id)
		if(qemProject && securityService.userId==qemProject.teacher.id){
//			def basePath= grailsAttributes.getApplicationContext().getResource("/qemUpload/").getFile().toString()
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(qemProject.projectName+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(qemProject,basePath)
			response.outputStream.flush()

		}
	}
	def uploadFiles(){
		
		def projectId=params.projectId
		def isDeclaration =params.isDeclaration
//		println isDeclaration==false
		java.util.List<String> fileList = getFileNames(projectId)
		if(!fileList) fileList=new java.util.ArrayList<String>()
		def f = request.getFile('file')
		if(!f.empty) {
			//计算文件前缀
//			def pre=fileList?.size()+1			
//			Notice notice= Notice.last()
//			def filePath= grailsAttributes.getApplicationContext().getResource("/qemUpload/").getFile().toString()+"/"+securityService.userId+"/"+projectId
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/"+securityService.userId+"/"+projectId
			if(isDeclaration) filePath+="/"+isDeclaration
			File dir = new File(filePath)
			if(!dir.isDirectory()){				
				dir.mkdirs()
			}
			def filename=f.originalFilename
//			def filename=pre+"_"+f.originalFilename
//			fileList.add("/tms/qemUpload/"+securityService.userId+"/"+projectId+"/"+filename)
			fileList.add(projectId+"/"+filename)
			f.transferTo( new File(filePath+"/"+filename) )
			render ( [fileList:fileList] as JSON)
		}
	}
	
	def showMyProjects(){
		def projects=projectService.projects()
//		def createAble=checkActionAble(Notice.PROJECT_REQUEST)
		render([projects:projects,createAble:true] as JSON)
	}
	private String getCurrentYear(){
		def  notice= Notice.last()
		return notice?.bn
	}
	def getAudit(){	
		def form_id = params.int("form_id")
		if(form_id){			
			render([
				audits:projectService.getAudits(form_id)] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}		
	}
	def showDetail(long id){
		def project = QemProject.get(id)
		if(securityService.userId.equals(project?.teacher.id)){
			render (view:"projectDetail",model:[project:[
					id:project.id,
					userId:project.teacher.id,
					name:project.teacher.name,
					currentTitle:project.currentTitle,
				currentDegree:project.currentDegree,
				specailEmail:project.specailEmail,
				discipline:project.discipline,
				direction:project.direction,
				departmentName:project.department.name,
				qemTypeName:project.qemType.name,
				projectName:project.projectName,
				expectedGain:project.expectedGain,
				commit:project.isSubmit,
				collegeStatus:project.collegeStatus,
				phoneNum:project.phoneNum,
				position:project.position,
				majorId:project.major,
				projectLevel:project.projectLevel.toString(),
				otherLinks:project.otherLinks,
				status:project.review.status,
				view:project.review.detail],
				notice:project.notice,
				fileList:getFileNames(project.id.toString())])
		}else{
		render status: HttpStatus.BAD_REQUEST
		}
	}
	def showTask(long id){
//		println id
		render (view:"taskMenagement",model:[id:id])
	}
	def projectDetail(){
//		println params.id
		def project = QemProject.get(params.id)
		if(securityService.userId.equals(project?.teacher.id)){
			render([project:[
				id:project.id,
				userId:project.teacher.id,
				name:project.teacher.name,
				currentTitle:project.currentTitle,
			currentDegree:project.currentDegree,
			specailEmail:project.specailEmail,
			discipline:project.discipline,
			direction:project.direction,
			departmentName:project.department.name,
			qemTypeName:project.qemType.name,
			projectName:project.projectName,
			expectedGain:project.expectedGain,
			commit:project.isSubmit,
			collegeStatus:project.collegeStatus,
			phoneNum:project.phoneNum,
			position:project.position,
			majorId:project.major,
			projectLevel:project.projectLevel.toString(),
			otherLinks:project.otherLinks,
			status:project.review.status,
			view:project.review.detail],
//			audits:project.audits,
			fileList:getFileNames(project.id.toString())] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}


	def cancel(){
		def projectId=params.long("projectId") ?: 0
		if(projectId){
			def qemProject =QemProject.get(projectId)
			if(qemProject.allowAction(QemProject.ACTION_CANCEL)){
				qemProject.setIsSubmit(false)
				qemProject.save(flush:true)
				QemAudit qemAudit=new QemAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:QemAudit.ACTION_CANCEL,
					date:new Date(),
					form:qemProject])
				qemAudit.save(flush:true)
				render status: HttpStatus.OK
			}else{
				render status: HttpStatus.BAD_REQUEST
			}
		}else{
		render status: HttpStatus.BAD_REQUEST
		}
	}
	def apply(){
		def projectId=params.long("projectId") ?: 0
		def projectCommand =new ProjectCommand(request.JSON)
		if(projectCommand?.id){
			projectCommand.commit="true"
			def qemProject =QemProject.get(projectCommand?.id)
			if(qemProject.allowAction(QemProject.ACTION_SUBMIT)){
				projectCommand.collegeStatus=0
				projectService.update(projectCommand)
				QemAudit qemAudit=new QemAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:QemAudit.ACTION_COMMIT,
					date:new Date(),
					form:qemProject])
				qemAudit.save(flush:true)
				render status: HttpStatus.OK
			}else{
				render status: HttpStatus.BAD_REQUEST
			}
		}else if(projectId){
			def qemProject =QemProject.get(projectId)
			if(qemProject.allowAction(QemProject.ACTION_SUBMIT)){
				qemProject.setCollegeStatus(0)
				qemProject.setIsSubmit(true)
				qemProject.save(flush:true)
				QemAudit qemAudit=new QemAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:QemAudit.ACTION_COMMIT,
					date:new Date(),
					form:qemProject])
				qemAudit.save(flush:true)
				render status: HttpStatus.OK
			}
		}else{
		render status: HttpStatus.BAD_REQUEST
		}
	}
	def deleteItem(){
		def projectId=params.long("projectId") ?: 0
		if(projectId){
			def qemProject =QemProject.get(projectId)
			if(qemProject.allowAction(QemProject.ACTION_SUBMIT)){
				qemProject.delete(flush:true)			
				showMyProjects()
			}else{
				render status: HttpStatus.BAD_REQUEST
			}
		}else{
		render status: HttpStatus.BAD_REQUEST
		}
	}
	def showMyTasks(){
		def taskList = projectService.taskList()
		render ([taskList:taskList] as JSON)
	}
	def getBns(){
		def bns = projectService.getBns()
		render ([bns:bns] as JSON)
	}
	def qemPublic(){
		
		def bn=params.bn?:new Date().format("yyyy")
		render ([qemPublic:projectService.projectsPublic(bn)] as JSON)
	}
	def removeAtt(){
		def filename=params.filename
		def projectId=params.projectId
		def project=QemProject.get(projectId)
		if(filename && !(project?.isSubmit)){
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/"+securityService.userId+"/"+projectId
			if(filename.indexOf("___")!=-1){
				def filenames=filename.split ("___")
				File file=new File(filePath+"/"+filenames[0]+"/"+filenames[1])
				file?.renameTo(filePath+"/"+filenames[0]+"/del_"+filenames[1])
			}else{
				File file=new File(filePath+"/"+filename)
				file?.renameTo(filePath+"/del_"+filename)
			}
			render ([fileList:getFileNames(projectId)]as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
	def getNoticeAtt(){
		def noticePath=params.publishDate
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/noticeFile/"+noticePath
		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(dir.isDirectory()){
			for(File file: dir.listFiles()){
				if(file.name.indexOf("del_")==-1){
					fileNames.add(file.name)
				}
			}
		}
		render ([fileList:fileNames]as JSON)
	}
	def downloadNoticeAtt(Long id){
			Notice notice=Notice.get(id)
			def basePath= grailsApplication.config.tms.qem.uploadPath+"/noticeFile/"+notice.publishDate.format("yyyyMMdd")
			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(notice.title+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(basePath)
			response.outputStream.flush()
	}
}
