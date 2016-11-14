package cn.edu.bnuz.tpt
import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.tms.security.SecurityService;
class TptMentorCheckController {
	SecurityService securityService
	TptMentorCheckService tptMentorCheckService
	TptReportService tptReportService
	
	
    def index() { }
	def showRequests(){
		def offset = params.int("offset") ?: 0
		def max = params.int("max") ?: 10
		def bn=tptMentorCheckService.getCurrentBn()
		def status =params.int("type")?: 4
		def requests= tptMentorCheckService.requestList(offset, max, bn,status)
		def total3 =tptMentorCheckService.requestCounts(bn)
		render ([requests: requests, pager: [offset: offset, max: max, total: total3]] as JSON)


	}
	def downloadPapers(Long id){
		def user= tptMentorCheckService.requestUser(id)
		if(!user) {
			render status: HttpStatus.NOT_FOUND
		}else{
			def filename="paper_${user[0]}"
			def basePath= grailsApplication.config.tms.tpt.uploadPath+"/"+tptMentorCheckService.currentBn
			response.setHeader("Content-disposition", "filename=\"${filename}.zip\"")
			response.contentType = "application/zip"
			response.outputStream << tptReportService.exportPhotos(user,basePath,"paper")
			response.outputStream.flush()
		}
	}
	
	def auditSave(){
		def audit=new AuditForm(request.JSON)
		def offset=audit.nextId
		def max = audit.prevId
		def tptRequest=TptRequest.get(audit.form_id)
		if( tptRequest.allowStatus(TptRequest.STATUS_PAPERCHECKED)){
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
		tptRequest.save(flush:true)
//		def bn=tptMentorCheckService.getCurrentBn()
//		def requests= tptMentorCheckService.requestList(Integer.parseInt(offset), Integer.parseInt(max), bn,4)
//		def total3 =tptMentorCheckService.requestCounts(bn)
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
		}
		render status: HttpStatus.OK
	}
}
