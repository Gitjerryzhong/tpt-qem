package cn.edu.bnuz.qem

import java.util.List;

import org.springframework.http.HttpStatus

import cn.edu.bnuz.qem.project.QemAudit
import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.QemTaskAudit
import cn.edu.bnuz.qem.review.Review
import cn.edu.bnuz.tms.security.SecurityService;
import grails.converters.JSON
import static java.util.Calendar.YEAR

class QemProjectAdminController {
	SecurityService securityService
	ProjectAdminService projectAdminService
	AdminService adminService
	CollegeExportService collegeExportService
	ExportService exportService
    def index() {
		render (view:"index",model:[bns:projectAdminService.getBns()])
	}
	/**
	 * 取申请列表
	 * @return
	 */
	def showRequests(){
		def offset = params.int("offset") ?: 0
		def max = params.int("max") ?: 10
		def type =  params.int("type") ?: 0
//		def bn = params.bn
//		projectAdminService.setCurrentBn(bn)
		getRequests(offset,max,type)
	}
	/**
	 * 创建分组
	 * @return
	 */
	def createGroup(){
		def projectids=new ProjectIds(request.JSON)
		def groupId =(int)Math.random()*10000
		projectids.ids.each {item->
			def project= QemProject.get(item)
			project.setGroupId(groupId.toString())
			project.save()
		}
		getRequests(0,10,1)
	}
	/**
	 * 移出分组
	 * @return
	 */
	def moveOut(){
		def projectids=new ProjectIds(request.JSON)
		projectids.ids.each {item->
			def project= QemProject.get(item)
			project.setGroupId(null)
			project.save()
		}
		getRequests(0,10,1)
	}
	/***
	 * 根据前台要求取出申请列表
	 * @param offset 分页偏移量
	 * @param max 分页最大行数
	 * @param type 取什么样的申请列表
	 * @return
	 */
	def getRequests(int offset,int max,int type ){
		def requests
		if(type==0) requests=projectAdminService.requestList()
		else if(type==1) requests=projectAdminService.requestUngroup()
		else if(type==3) requests=projectAdminService.requestList(offset,max)
		def majorTypes=projectAdminService.myjorTypes()
		def total =projectAdminService.requestCounts()
		def groups = projectAdminService.requestGroups()
		render ([requests: requests, pager: [offset: offset, max: max, total: total],majorTypes:majorTypes,groups:groups] as JSON)
	}
	/**
	 * 获取专家意见汇总
	 * @return
	 */
	def expertViews(){
		render ([requestList: projectAdminService.requestList(0,1000)] as JSON)
	}
	def expertRequest(){
		def bn = params.bn
		println bn
		projectAdminService.setCurrentBn(bn)
		def requests = projectAdminService.requestForExperts();
		def experts = projectAdminService.getExperts()
		def total =projectAdminService.requestCounts()
		render ([requests: requests,experts:experts,total:total] as JSON)
	}
//	def getAllExperts(){
//		render([] as JSON)
//	}
	/**
	 * 分配专家
	 * @return
	 */
	def doAssigned(){
		def rules = new Rules(request.JSON)
//		println request.JSON.check1
		def requests = projectAdminService.requestNoExperts();
		def maxExpert = rules.check2?rules.value2:10		
		requests.each {item->
			println item.projectName
			 
			def referenceExperts=""
			def expertNum=0
			def experts
			if(rules.check1){//如果要求专家回避本学院的项目评审，并要求该相关专业的评审资格
				def department = item.departmentName
				def majorType = item.majorTypeName
				experts= projectAdminService.getExperts(department, majorType)
			}else{
				experts= projectAdminService.getExperts()
			}
			experts.each {expert->
				
				if(expertNum < maxExpert){
					if(rules.check3){
						def expertCount=projectAdminService.getCounts(expert.id)
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
//			if(item.id==5)	{
//				println referenceExperts
//				println maxExpert + ":" +expertNum}
			QemProject qemProject =QemProject.get(item.id)
			qemProject.review.setExperts(referenceExperts)
			qemProject.review.setStatus(Review.STATUS_EXPERT)
			qemProject.review.save(flush:true)
								
			
		}

		expertRequest()
	}
	/**
	 * 调整专家
	 * @return
	 */
	def doUpdateExpert(){
		def id=request.JSON.id
		def experts=request.JSON.experts
		if(id) {
			QemProject project =QemProject.get(id)
			project?.review.setExperts(experts)
			project?.review.save(flush:true)
		}
		render status: HttpStatus.OK
	}
	/**
	 * 专家评审汇总登记
	 */
	def finalView(){
		def id=request.JSON.id
		def result=request.JSON.result
		def content=request.JSON.content
		def budget0 = request.JSON.budget0
		def budget1 = request.JSON.budget1
		def budget2 = request.JSON.budget2
		String sn = request.JSON.sn
//		println sn
		if(id){
			QemProject project =QemProject.get(id)
			project?.review.setResult(result)
			project?.review.setDetail(content)
			project?.review.setStatus(Review.STATUS_PASS+Integer.parseInt(result))
			project?.save(flush:true)
			def audit = new QemAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:"3"+result,
				content:content,
				date: new Date(),
				form:project])
			audit.save(flush:true)
			if(result!="1"){
				def today= new Date()
				def task
				task= QemTask.findByProjectId(id)
				if (task){
					task.setSn(sn)				
					task.setFundingProvince(budget0?:0)
					task.setFundingUniversity(budget1?:0)
					task.setFundingCollege(budget2?:0)	
					task.save(flush:true)			
				}else{
				task=new QemTask([
					projectId:project?.id,
					teacher:project?.teacher,
					currentTitle:project?.currentTitle,
					currentDegree:project?.currentDegree,
					specailEmail:project?.specailEmail,
					department:project?.department,
					position:project?.position,
					phoneNum:project?.phoneNum,
					qemType:project?.qemType,
					projectLevel:project?.projectLevel,
					projectName:project?.projectName,
					sn:sn.toString(),
					otherLinks:project?.otherLinks,
					beginYear:today.format("yyyy-MM-dd"),
					expectedMid: today[YEAR]+Math.round(project?.qemType.cycle/2),
					expectedEnd: today[YEAR]+project?.qemType.cycle,
					fundingProvince:budget0?:0,
					fundingUniversity:budget1?:0,
					fundingCollege:budget2?:0,
					status:QemTask.S_NEW,
					runStatus:QemTask.S_NEW
					
					])
					if(!task.save(flush:true)){
						task.errors.each{
							println it
						}
					}
				}
				//创建日志
				QemTaskAudit qemAudit=new QemTaskAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:QemTaskAudit.ACTION_NEW_TASK,
					date:new Date(),
					objectId:task?.id,
					src:task?.class.name])
				if(!qemAudit.save(flush:true)){
					qemAudit.errors.each{
						println it
					}
				}
			}
//			project?.qemTask.save(flush:true)			
			project?.review.save(flush:true)
			
			def total =projectAdminService.requestCounts()
			render ([isReviewed:Review.STATUS_PASS+Integer.parseInt(result),total:total] as JSON)
		}else{
			render status:HttpStatus.BAD_REQUEST
		}
		
	}
	/**
	 * 导出专家评审情况汇总
	 */
	def export(){
		def report=projectAdminService.export()
		if(report) {
			def template = grailsApplication.mainContext.getResource("excel/qem-review.xls").getFile()
			def workbook = exportService.exportReport(template, report)
			response.setContentType("application/excel")
			response.setHeader("Content-disposition", "attachment;filename=\"qem-review.xls\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	/**
	 * 导出项目申报汇总
	 */
	def exportReqs(){
		def report=projectAdminService.exportReqs()
		if(report) {
			def template = grailsApplication.mainContext.getResource("excel/qem-summary.xls").getFile()
			def workbook = collegeExportService.exportReport(template, report)
			response.setContentType("application/excel")
			response.setHeader("Content-disposition", "attachment;filename=\"college-summary.xls\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	private List<String> getFileNames(QemTask project){
		//		Notice notice= Notice.last()
		def filePath= grailsAttributes.getApplicationContext().getResource("/qemUpload/").getFile().toString()+"/task/"+project?.teacher.id+"/"+project?.id
		println filePath
		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(dir.isDirectory()){
			for(File file: dir.listFiles()){
				fileNames.add("/tms/qemUpload/task/"+project?.teacher.id+"/"+project?.id+"/"+file.name)
			}
		}
		return fileNames
	}
	private List<String> getFileNamesForProject(def project){
		//		Notice notice= Notice.last()
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/"+project?.teacher.id+"/"+project?.id
		println filePath
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
	def requestListAll(){
		def bn= params.bn
		if(bn){
			def list = projectAdminService.requestListAll(bn)
			render ([requestList:list,bn:bn] as JSON)
		}else{
			render ([requestList:[],bn:bn] as JSON)
		}
	}
	def getRequestDetail(){
		
		def form_id = params.int("form_id")
		if(form_id){
			def project=projectAdminService.getProject(form_id)
			QemProject pro=QemProject.get(form_id)
		render([form:project,			
			fileList:getFileNamesForProject(pro),
			audits:pro?.audits] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
	def listExperts(){
		render ([experts:adminService.loadExperts()] as JSON)
	}
	def applyGroup(){
		def ids=request.JSON.ids
		def experts=request.JSON.expertGroup
		ids.each {item->
			def project= QemProject.get(item)
			project?.review.setExperts(experts)
			project.save()
		}
		def requests = projectAdminService.requestForExperts();
		render ([requests: requests] as JSON)
	}
	def getTaskInfo(){
		def projectId = params.int("projectId")
//		println projectId
		if(projectId){
			render ([task:projectAdminService.getTask(projectId)]as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
	}
//	导出指定批次的所有附件
	def exportAttach(String id){
		def report=projectAdminService.exportForAttach(id)
		if(report) {
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition",
				 "attachment;filename=\""+
				 java.net.URLEncoder.encode(message(code:"qem.fileName.adminAttach")+
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
	def downloadAttch(Long id){
		def qemProject=QemProject.get(id)
		if(qemProject){
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(qemProject.projectName+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(qemProject,basePath)
			response.outputStream.flush()

		}
	}
}

class ProjectIds{
	List<Long> ids
	public getIds(){
		List<Long> myIds=[]
		ids.each {item->
			myIds << new Long(item)
		}
		return myIds
	}
}
class Rules{
	Boolean check1
	Boolean check2
	Boolean check3
	String value1
	String value2
	String value3
	int getValue1(){
		if(value1)
			return Integer.parseInt(value1)
	}
	int getValue2(){
		if(value2)
			return Integer.parseInt(value2)
	}
	int getValue3(){
		if(value3)
			return Integer.parseInt(value3)
	}
}
