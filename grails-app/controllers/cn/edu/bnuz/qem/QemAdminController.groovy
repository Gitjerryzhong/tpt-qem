package cn.edu.bnuz.qem

import cn.edu.bnuz.qem.organization.Experts
import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.qem.project.Attention
import cn.edu.bnuz.qem.project.QemParentType
import cn.edu.bnuz.qem.project.QemType
import cn.edu.bnuz.tms.security.SecurityService
import grails.converters.JSON
import cn.edu.bnuz.tms.organization.Teacher

import org.springframework.http.HttpStatus

class QemAdminController {
	SecurityService securityService
	AdminService adminService
	ProjectAdminService projectAdminService
	ExportService exportService
	def index(){}
	def saveNotice(){
		def notice=new NoticeForm(request.JSON)
		if(notice.id==0){
			def bn = ""
			if(notice.workType==Notice.PROJECT_REQUEST){
				def basebn = new Date().format("yyyyMM")
				def i=1
				bn = basebn
				while(true){
					if(!Notice.findByBn(bn)) break
					bn=basebn+"-"+i.toString()
					i++
				}
			}
			Notice qemNotice=new Notice([
				title:notice.title,
				content:notice.content,
				start:notice.startDate,
				end:notice.endDate,
				publishDate:new Date(),
				workType:notice.workType,
				noTowLine:notice.noTowLine,
				bn:bn,
				author:securityService.userName])
			if(!qemNotice.save(flush:true)){
				qemNotice.errors.each{
					println it
				}
				render([errors:qemNotice.errors] as JSON)
			}
		}else{
			Notice qemNotice=Notice.get(notice.id)
			qemNotice.setTitle(notice.title)
			qemNotice.setContent(notice.content)
			qemNotice.setStart(notice.startDate)
			qemNotice.setEnd(notice.endDate)
			qemNotice.setWorkType(notice.workType)
			qemNotice.setNoTowLine(notice.noTowLine)
			if(!qemNotice.save(flush:true)){
				qemNotice.errors.each{
					println it
				}
				render([errors:qemNotice.errors] as JSON)
			}
		}
		noticeList()
	}
	def showNotice(){		
		Notice notice= Notice.last()
		render([notice:[
				id:notice?.id,
				title:notice?.title,
				content:notice?.content,
				start:notice?.start,
				end:notice?.end,
				workType:notice?.workType]] as JSON)
	}
	def saveParentType(){
		def parentType
		def id = params.int("parentTypeId") ?: 0
		if(id){
			parentType=QemParentType.get(id)
			parentType.setParentTypeName(request.JSON?.parentTypeName)
		}else{
			parentType=new QemParentType(request.JSON)
		}
		if(!parentType.save(flush:true)){
			parentType.errors.each{
				println it
			}
		render([errors:parentType.errors] as JSON)
		}
		showParentType()
	}
	def saveType(){
		def type
		def json =request.JSON
		def id = params.int("typeId") ?: 0
		if(id){
			type=QemType.get(id)
			type.setName(json.name)
			type.setCycle(json.cycle)
			type.setActived(json.actived)
			type.setParentType(QemParentType.get(json.parentTypeId))
			if(!type.downLoadUrl?.equals(json.downLoadUrl)){
				def filePath= grailsAttributes.getApplicationContext().getResource("/template/").getFile().toString()
				File file=new File(filePath+"/"+type.downLoadUrl)
				if(file?.isFile())file.delete()
				type.setDownLoadUrl(json.downLoadUrl)
			}
		}else{
			type=new QemType([
				name:json.name,
				parentType:QemParentType.get(json.parentTypeId),
				cycle:json.cycle,
				actived:json.actived,
				downLoadUrl:json.downLoadUrl])
		}
		if(!type.save(flush:true)){
			type.errors.each{
				println it
			}
		render([errors:type.errors] as JSON)
		}
		showType()
	}
	def showParentType(){
		def parentType= QemParentType.list()
		render([parentTypes:parentType] as JSON)
	}
	def showType(){
		def entries= adminService.getTypes().items
//		def parentType= QemParentType.list()
//		def type = QemType.list()
		render([types:entries] as JSON)
	}
	def rmParentType(){
		def id = params.int("parentTypeId") ?: 0
		def parentType=QemParentType.get(id)
		parentType.delete(flush:true)
		showParentType()
	}
	def rmType(){
		def id = params.int("typeId") ?: 0
		def type=QemType.get(id)
		type.delete(flush:true)
		showType()
	}
	
	def shielding(){
		def id = params.int("typeId") ?: 0
		def type=QemType.get(id)
		type.setActived(!type.actived)
		type.save(flush:true)
		render status: HttpStatus.OK
	}
	
	def uploadTemplate(){
		def f = request.getFile('file')
		if(!f.empty) {
			def filename=f.originalFilename
			def type=filename.substring(filename.lastIndexOf("."))
			def newName=new Date().time+type
			def filePath= grailsAttributes.getApplicationContext().getResource("/template/").getFile().toString()
			f.transferTo( new File(filePath+"/"+newName) )
			render ( [filename:newName] as JSON)
		}
		
	}
	
	def uploadNoticePack(){
		def isDeclaration =params.dir
		def f = request.getFile('file')
		if(!f.empty) {
			def filename=f.originalFilename
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/noticeFile"
			if(isDeclaration) filePath+="/"+isDeclaration
			File dir = new File(filePath)
			if(!dir.isDirectory()){
				dir.mkdirs()
			}
			f.transferTo( new File(filePath+"/"+filename) )
			render ( [filename:filename] as JSON)
		}
		
	}
	
	def loadTeachers(){
		render( [teachers:adminService.loadTeachers(),majorTypes:projectAdminService.myjorTypes()] as JSON)
	}
	def addExperts(){
		def experts=new ExpertMajorType(request.JSON)
		if(experts){
			experts.experts.each {item->
//				println Teacher.get(item.id).name
				Experts expert=new Experts([teacher:Teacher.get(item.id),discipline:item.types])
				expert.save(flush:true)
			}
		}
		render status: HttpStatus.OK
	}
	def listExperts(){
		render ([experts:adminService.loadExperts()] as JSON)
	}
	def deleteExpert(){
		def id = params.expertId
		if(id){
			def expert = Experts.findByTeacher(Teacher.get(id))
			expert?.delete(flush:true)
		}
		listExperts()
	}
	def deleteNotice(){
		def id = params.noticeId
		if(id){
			def notice = Notice.get(id)
			notice?.delete(flush:true)
		}
		noticeList()
	}
	def noticeList(){
		def  activeNotices = Notice.findAll ("from Notice as n order by n.id desc")
		render ([notices:activeNotices] as JSON)
	}
	def saveAttention(){
		def attentionForm=new MyAttention(request.JSON)
		if(!attentionForm.id){
			adminService.saveAttention(attentionForm)
		}else{
			adminService.updateAttention(attentionForm)
		}
		attentionList()
	}
	def attentionList(){
		def attentionList = Attention.findAll ("from Attention as a order by a.id desc")
		render ([attentions:attentionList] as JSON)
	}
	def showAttention(){
		def id = params.id
		def attention=Attention.get(id)
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/attentionFile"
		List<String> fileNames=new ArrayList<String>()
		def file = new File(filePath+"/"+attention.publishDate.format("yyyyMMdd"))
		if(file.isDirectory()){ //如果申报书子目录
			for(File f:file.listFiles()){
				if(f.name.indexOf("del_")==-1) fileNames.add(file.name+"___"+f.name)
			}
		}else if(file.name.indexOf("del_")==-1){
			fileNames.add(file.name)
		}
		render([attention:attention,fileList:fileNames] as JSON)
	}
	def uploadAttentionPack(){
		def isDeclaration =params.dir
		def f = request.getFile('file')
		if(!f.empty) {
			def filename=f.originalFilename
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/attentionFile"
			if(isDeclaration) filePath+="/"+isDeclaration
			File dir = new File(filePath)
			if(!dir.isDirectory()){
				dir.mkdirs()
			}
			f.transferTo( new File(filePath+"/"+filename) )
			render ( [filename:filename] as JSON)
		}
		
	}
	def downloadAttentAtt(Long id){
		def attention=Attention.get(id)
		def basePath= grailsApplication.config.tms.qem.uploadPath+"/attentionFile/"+attention.publishDate.format("yyyyMMdd")
		response.setContentType("application/zip")
		response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(attention.title+".zip", "UTF-8")+"\"")
		response.outputStream << exportService.download(basePath)
		response.outputStream.flush()
	}
	
}
class ExpertMajorType{
	List<MyExpert> experts
}
class MyExpert {
	String id;
	String name;
	String types;
}
class MyAttention{
	Integer id
	String title
	String content
}
