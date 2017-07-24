package cn.edu.bnuz.tpt
import java.util.List;

import org.springframework.web.context.request.RequestScope;

import cn.edu.bnuz.tms.organization.*
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tpt.UserForm
import cn.edu.bnuz.tpt.TptAdminService
import cn.edu.bnuz.tpt.util.TestZip;
import grails.converters.JSON

import org.springframework.http.HttpStatus
class TptAdminController {
	TptAdminService tptAdminService
	SecurityService securityService
	TptReportService tptReportService
	TptCoProjectService tptCoProjectService
    def index() { 	
		tptAdminService.checkAndInitRole()
	}
	def createUsers(){
		def users=new UserForm(request.JSON)
		def result=tptAdminService.studentSave(users)
		render([errors:result] as JSON)
	}
	def deleteUsers(){
		def deleteCount=tptAdminService.deleteUserList()
		render([count:String.valueOf(deleteCount)] as JSON)
	}
	def show(){
		def userList=tptAdminService.reloadUserlist()
		render([userList:userList] as JSON)
	}
	def saveNotice(){
		def notice=new NoticeForm(request.JSON)
		if(notice.id==0){
			TptNotice tptNotice=new TptNotice([
				title:notice.title,
				content:notice.content,
				start:notice.getStartDate(),
				end:notice.getEndDate(),
				bn:new Date().format("yyyyMM"),
				publishDate:new Date(),
				publisher:securityService.userId])
			if(!tptNotice.save(flush:true)){
				tptNotice.errors.each{
					println it
				}
				render([errors:tptNotice.errors] as JSON)
			}
		}else{
			TptNotice tptNotice=TptNotice.get(notice.id)
			tptNotice.setTitle(notice.title)
			tptNotice.setContent(notice.content)
			tptNotice.setStart(notice.getStartDate())
			tptNotice.setEnd(notice.getEndDate())
			tptNotice.setBn(notice.getStartDate().format("yyyy"))
			if(!tptNotice.save(flush:true)){
				tptNotice.errors.each{
					println it
				}
				render([errors:tptNotice.errors] as JSON)
			}
		}
		noticeList()
	}
	def showNotice(){
		TptNotice notice= TptNotice.findByPublisher(securityService.userId)
		render([notice:[
				id:notice?notice.id:0,
				title:notice?.title,
				content:notice?.content,
				start:notice?notice.start:new Date().format("yyyy-MM-dd"),
				end:notice?notice.end:new Date().format("yyyy-MM-dd"),
				bn:notice?.bn]] as JSON)
	}
	def noticeList(){
//		def  activeNotices = TptNotice.findAll ("from TptNotice as n where n.publisher='${securityService.userId}' order by n.id desc")
		render ([notices:tptAdminService.noticeList()] as JSON)
	}
	def deleteNotice(){
		 def id =params.int("id")
		 if(tptAdminService.delNotice(id)) noticeList()
		 else render status:HttpStatus.BAD_REQUEST
	}
	def saveCollege(){
		def college
		def id = params.int("collegeId") ?: 0
		if(id){
			college=TptCollege.get(id)
//			System.out.println(request.JSON?.name)
			college.setName(request.JSON?.name)
		}else{
			college=new TptCollege(request.JSON)
			college.department_id = securityService.departmentId
		}
		if(!college.save(flush:true)){
			college.errors.each{
				println it
			}
		render([errors:college.errors] as JSON)
		}		
		showCollege()
	}
	def showCollege(){
//		def colleges= TptCollege.findAllByDepartment_id(securityService.departmentId)
		render([colleges:tptAdminService.collegeList()] as JSON)
	}
	def saveMentors(){
		def mentors=request.JSON
		if(mentors){
			mentors.each {item->
//				println Teacher.get(item)?.name
				TptMentor mentor=new TptMentor([teacher:Teacher.get(item)])
				mentor.save(flush:true)
			}
		}
		showMentor()
	}
	def showMentor(){
		render([mentorList:tptAdminService.mentorList()] as JSON)
	}
	def loadTeachers(){
		def max =params.int("max")?:10
		def offset = params.int("offset")?:0
		def teachername = params.teachername
		if(teachername) render( [teachers:tptAdminService.loadTeacherByName(teachername)] as JSON)
		else render( [teachers:tptAdminService.loadTeachers(offset,max)] as JSON)
	}
	
	def deleteMentor(){
		def id = params.mentorId
		if(id){
			def mentor = TptMentor.findByTeacher(Teacher.get(id))
			mentor?.delete(flush:true)
		}
		showMentor()
	}
	def setMentor(){
		def requests=request.JSON.requests
		def mentorId = request.JSON.mentorId
		if(requests && mentorId){
			requests.each {item->
//				println item.userName
				TptRequest tptRequest = TptRequest.get(item.id)
				tptRequest.setMentor(TptMentor.findByTeacher(Teacher.get(mentorId)))
				tptRequest.save(flush:true)
			}
		}
		render status:HttpStatus.OK
	}
	def check(){}
	
	def showRequests(){
		def offset = params.int("offset") ?: 0
		def max = params.int("max") ?: 10
		def status =params.int("type")?: 1
		def bn=tptAdminService.getCurrentBn()
		def requests= tptAdminService.requestList(offset, max, bn,status)
		def total3 =tptAdminService.requestCounts(bn)
		render ([requests: requests, pager: [offset: offset, max: max, total: total3]] as JSON)

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
		def tptRequest = TptRequest.get(form_id)
		def nextId= tptAdminService.checkingNext(form_id,tptRequest.status)
		def prevId= tptAdminService.checkingPrev(form_id,tptRequest.status)
		def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+tptRequest?.bn+"/"+tptRequest.userId
		def imgPath="/tms/picture/show?bn="+tptRequest?.bn+"&id="+tptRequest.userId+"&filename="
		def fileNames = getFileNames(filePath)
		def total3 =tptAdminService.requestCounts(tptRequest?.bn)
		render([form:[
			formId:tptRequest?.id,
				contact:tptRequest?.contact,
				phoneNumber:tptRequest?.phoneNumber,
				email:tptRequest?.email,
//				foreignCollege:tptRequest?.foreignCollege,
				collegeName:tptRequest?.collegeName?:tptRequest?.foreignCollege?.name,
				foreignMajor:tptRequest?.foreignMajor,
				status:tptRequest?.status,
				allIn:tptRequest?.allIn,
				userId:tptRequest?.userId,
				userName:tptRequest.userName,
				nextId:nextId,
				prevId:prevId],
			imgSrc:[
				photo:getFileName(fileNames,"photo_",imgPath),
				cert:pdfFilter(getFileName(fileNames,"certi_",imgPath)),
				trans1:pdfFilter(getFileName(fileNames,"trans_1",imgPath)),
				trans2:pdfFilter(getFileName(fileNames,"trans_2",imgPath)),
				trans3:pdfFilter(getFileName(fileNames,"trans_3",imgPath))],
			pager: [offset: 0, max: 10, total: total3],
			paperFile:checkPaperFile(fileNames,"paper_"),
			paperExchFile:checkPaperFile(fileNames,"paper_exch"),
			audits:tptRequest?.audits] as JSON)
	}
	def cancel(){
		def form_id= params.int("formId")
		def tptRequest = TptRequest.get(form_id)
		if(tptRequest){
			if(tptRequest?.status in[TptRequest.STATUS_CHECKED,
									TptRequest.STATUS_REJECTED]){
				tptRequest.setStatus(TptRequest.STATUS_APPLYING)
			}else if(tptRequest?.status in[TptRequest.STATUS_PAPERCHECKED,
									TptRequest.STATUS_PAPERREJECTED]){
				tptRequest.setStatus(TptRequest.STATUS_PAPERUPLOAD)
			}
			tptRequest.save(flush:true)
			def tptAudit = new TptAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:TptAudit.ACTION_APPROVE_CANCEL,
				content:null,
				date: new Date(),
				form:tptRequest])
			tptAudit.save(flush:true)
			render([status:tptRequest.status,
					audits:tptRequest?.audits] as JSON)
		}else render status:HttpStatus.BAD_REQUEST
	}
	private List<String> getFileNames(String filePath){
		
		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(!dir.isInvalid()){
			for(File file: dir.listFiles()){
				fileNames.add(file.name)
			}
		}
		return fileNames
	}
	private String getFileName(List<String> fileNames,String pre,String imgPath){
		for(String filename:fileNames){
			if(filename?.indexOf(pre)==0){
				return imgPath+filename
			}
		}
		return "/tms/tptUpload/none.jpg";
	}
	def showImage(){
		def form_id = params.int("form_id")
		def tptRequest = TptRequest.get(form_id)
		String pre=params.filename
//		def imgPath="/tms/tptUpload/"+tptRequest?.bn+"/"+tptRequest.userId+"/"
		def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+tptRequest?.bn+"/"+tptRequest.userId
		def imgPath="/tms/picture/show?bn="+tptRequest?.bn+"&id="+tptRequest.userId+"&filename="
		def fileNames = getFileNames(filePath)
		String filename= getFileName(fileNames,pre,imgPath)
		def map=[filename: filename]
		def type=filename.substring(filename.lastIndexOf("."))
		if(type?.toLowerCase().equals(".pdf"))
		{
			render(view:"showPdf",model:map)
		}else{
		render(view:"showImage",model:map)
		}
	}
	def auditSave(){
		def audit=new AuditForm(request.JSON)
		def tptRequest=TptRequest.get(audit.form_id)
//		System.out.println(audit.form_id)
		if( tptRequest.allowStatus(TptRequest.STATUS_CHECKED)){
			int check= Integer.parseInt(audit.check)
			switch(check){
			case 20: tptRequest.setStatus(TptRequest.STATUS_CHECKED)
					 break;
			case 21: tptRequest.setStatus(TptRequest.STATUS_REJECTED)
					 break;
			case 40: tptRequest.setStatus(TptRequest.STATUS_CLOSED)
					 break;
			}			
		}else if( tptRequest.allowStatus(TptRequest.STATUS_PAPERCHECKED)){
			int check= Integer.parseInt(audit.check)
			switch(check){
			case 30: tptRequest.setStatus(TptRequest.STATUS_PAPERCHECKED)
					 break;
			case 31: tptRequest.setStatus(TptRequest.STATUS_PAPERREJECTED)
					 break;
			case 40: tptRequest.setStatus(TptRequest.STATUS_CLOSED)
					 break;
			}			
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
		def total3 =tptAdminService.requestCounts(tptRequest?.bn)
		def tptAudit = new TptAudit([
			userId:securityService.userId,
			userName:securityService.userName,
			action:audit.check,
			content:audit.content,
			date: new Date(),
			form:tptRequest])
		if(!tptAudit.save(flush:true)){
			tptAudit.errors.each{
				println it
			}
		render([errors:tptAudit.errors] as JSON)
		}
		if(audit.nextId!=null && !audit.nextId.equals("null")){
			showDetail(Long.parseLong(audit.nextId))
		}else if(audit.prevId!=null && !audit.prevId.equals("null")){
			showDetail(Long.parseLong(audit.prevId))
		}else{
			render([none:true,pager: [offset: 0, max: 10, total: total3]] as JSON)
		}

	}
	def searchDetail(){
//		System.out.println(params.studentId)
		def bn=tptAdminService.getCurrentBn()
		def tptRequest = TptRequest.findByUserIdAndBn(params.studentId,bn)	
//		def filePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()+"/"+tptRequest?.bn+"/"+tptRequest.userId
//		def imgPath="/tms/tptUpload/"+tptRequest?.bn+"/"+tptRequest.userId+"/"
		def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+tptRequest?.bn+"/"+tptRequest.userId
		def imgPath="/tms/picture/show?bn="+tptRequest?.bn+"&id="+tptRequest.userId+"&filename="
//		println imgPath
		def fileNames = getFileNames(filePath)
//		filePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()+"/"+tptRequest?.bn+"/"+tptRequest?.userId
//		imgPath="/tms/tptUpload/"+tptRequest?.bn+"/"+tptRequest.userId+"/"
		render([form:tptRequest,
			imgSrc:[
				photo:getFileName(fileNames,"photo_",imgPath),
				cert:pdfFilter(getFileName(fileNames,"certi_",imgPath)),
				trans1:pdfFilter(getFileName(fileNames,"trans_1",imgPath)),
				trans2:pdfFilter(getFileName(fileNames,"trans_2",imgPath)),
				trans3:pdfFilter(getFileName(fileNames,"trans_3",imgPath))],
			paperFile:checkPaperFile(fileNames,"paper_"),
			paperExchFile:checkPaperFile(fileNames,"paper_exch"),
			audits:tptRequest?.audits] as JSON)
	}
	
/***
 * 不再使用
 * 
 */
	def downloadPhoto(){
//		TptNotice notice= TptNotice.first()
		def basePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()
		def zipfile=basePath+"/"+tptAdminService.getCurrentBn()+"_photo.zip"
		File file= new File(zipfile)
		file?.delete()
//		ZipCompressor zca = new ZipCompressor(zipfile);
//        zca.compressExe(basePath,"**/photo_*.jpg");
		TestZip zip = new TestZip(zipfile)
		zip.setIncludePrefix("photo_")
		zip.compressExe(basePath+"/"+tptAdminService.getCurrentBn())
		render([download:"/tms/tptUpload/"+tptAdminService.getCurrentBn()+"_photo.zip"] as JSON)
	}

	def downloadPhotoByStatus(){	
		render([download:photoZip(TptRequest.STATUS_CHECKED),downloadEnd:photoZip(TptRequest.STATUS_PAPERCHECKED)] as JSON)
		
	}
	private String photoZip(int status){
//		TptNotice notice= TptNotice.first()
		def bn =tptAdminService.getCurrentBn()
		def users= tptAdminService.requestUsers(bn, status)
//		System.out.println users?.size()>0
//		for(String user:users){
//			System.out.println status+":"+user
//		}
		if(users?.size()>0){
			def basePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()
			def zipfile=basePath+"/"+bn+status+"_photo.zip"
			File file= new File(zipfile)
			file?.delete()
	
			TestZip zip = new TestZip(zipfile)
			zip.setUsers(users)
			zip.setIncludePrefix("photo_")
			zip.compressExe(basePath+"/"+bn)
			return "/tms/tptUpload/"+bn+status+"_photo.zip"
		}else{
			return null;
		}
	}
	def downloadPaper(){		
//		TptNotice notice= TptNotice.first()
		def bn =tptAdminService.getCurrentBn()
		def basePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()
		def zipfile=basePath+"/"+bn+"_paper.zip"		
		File file= new File(zipfile)
		file?.delete()
//		ZipCompressor zca = new ZipCompressor(zipfile);
//		zca.compressExe(basePath,"**/paper_*.*");
		TestZip zip = new TestZip(zipfile)
		zip.setIncludePrefix("paper_")
		zip.compressExe(basePath+"/"+bn)
		render([download:"/tms/tptUpload/"+bn+"_paper.zip"] as JSON)
	}
	def rmCollege(){
		def id = params.int("collegeId") ?: 0
//		System.out.println(id)
		def college=TptCollege.get(id)
		college.delete(flush:true)
		showCollege()
	}
	def export(){
		TptNotice notice= TptNotice.first()
		def report=tptAdminService.export(notice?.bn)
		if(report) {
			def template = grailsApplication.mainContext.getResource("excel/tpt-request-items.xls").getFile()
			def workbook = tptReportService.exportReport(template, report)
			response.setContentType("application/excel")
			response.setHeader("Content-disposition", "attachment;filename=\"requests.xls\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	def exportPaperMtlRgn(){
		TptNotice notice= TptNotice.first()
		def report=tptAdminService.exportMtlRgn(notice?.bn)
		if(report) {
			def template = grailsApplication.mainContext.getResource("excel/tpt-paperMtlRgn.xls").getFile()
			def workbook = tptReportService.exportMtlRgn(template, report)
			response.setContentType("application/excel")
			response.setHeader("Content-disposition", "attachment;filename=\"paperMtlRgn.xls\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
	
	def searchName(){
		def username = params.studentName
		def bn=tptAdminService.getCurrentBn()

		def requests= tptAdminService.requestList(username, bn)

		def total3 =tptAdminService.requestCounts(bn)

		render ([requests: requests, pager: [offset: 0, max: 0, total: total3]] as JSON)
	}
	
	def downloadMaterial(long id){
		def tptRequest=TptRequest.get(id)
		if(tptRequest){
			def basePath= grailsApplication.config.tms.tpt.uploadPath+"/${tptRequest.bn}/${tptRequest.userId}"
			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode("${tptRequest.userId}${tptRequest.userName}.zip", "UTF-8")+"\"")
			response.outputStream << tptReportService.download(basePath)
			response.outputStream.flush()
		}
	}
	
	def getProList(){
		render ([proList:TptCoCountry.getAll(),
			course:tptAdminService.findCourseByProject(),
			majors: tptAdminService.getMajor()] as JSON)
	}
	private boolean checkPaperFile(List<String> fileNames,String pre){
		for(String filename:fileNames){
			if(filename?.indexOf(pre)==0){
				if(pre=='paper_exch') return true
				else if(filename?.indexOf('paper_exch')==-1) return true
			}
		}
		return false
	}
	private String pdfFilter(String filename){
		def type = filename.substring(filename.lastIndexOf(".")).toLowerCase()
		if(".pdf"==type){
			return "/tms/tptUpload/pdf.png"
		}else return filename
	}
	
	def showPaperMtg(){
		def id = params.long("id")?:0
		if(id){
			def paper=tptAdminService.getPaperMtg(id)
			render ([paper:paper]as JSON)
		}else render status:HttpStatus.BAD_REQUEST
	}
	def updatePaperMtg(){
		def id=params.long("id")?:0
		def scort=params.scort
		def projectId = params.long("projectId")?:0
		if(id && scort) {
			def templatePath =grailsAttributes.getApplicationContext().getResource("/template/").getFile().toString()
			def filePath= grailsApplication.config.tms.tpt.uploadPath
			tptAdminService.writePaperForm(id,scort,templatePath,filePath,projectId)
			render status:HttpStatus.OK
		}else render status:HttpStatus.BAD_REQUEST
		
	}


	def deleteStudent(){
		if(params.xh){
			T_ZZ_XSMD item=new T_ZZ_XSMD(
				xh:params.xh)
			tptCoProjectService.deleteStudent(item)
		}
		show()
	}
	def editStudent(){
		def student= new T_ZZ_XSMD(request.JSON)
				tptCoProjectService.editStudent(student)
		render status:HttpStatus.OK
	}
	def getProjects(){
		render ([proList:tptCoProjectService.getProjects(params.studentId)] as JSON)
	}
//	def getMentorOpinion(){
//		def result = tptAdminService.mentorOpinion("2016")
//		result.each {item->
//			println "studentId:${item.userId}, studentName:${item.userName}, status:${item.status}, mentorId:${item.mentorId}, maxdate:${item.maxdate}, content:${item.auditContent}"
//		}
//	}
}

