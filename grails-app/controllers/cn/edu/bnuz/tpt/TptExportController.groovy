package cn.edu.bnuz.tpt

import org.springframework.web.context.request.RequestScope;

import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tpt.TptAdminService
import cn.edu.bnuz.tpt.util.TestZip;
import grails.converters.JSON

import org.springframework.http.HttpStatus

class TptExportController {
	TptAdminService tptAdminService
	SecurityService securityService
	TptReportService tptReportService

    def index() {	
	}
	def getBns(){
		render([bns:tptAdminService.bns] as JSON)
	}
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
		render([download:photoZip(TptRequest.STATUS_APPLYING)] as JSON)
		
	}
	private String photoZip(int status){
		//		TptNotice notice= TptNotice.first()
				def bn =tptAdminService.getCurrentBn()
				def users= tptAdminService.requestUsers(bn, status)
//				System.out.println users?.size()>0
//				for(String user:users){
//					System.out.println status+":"+user
//				}
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
				def users= tptAdminService.requestUsers(bn)
				if(users?.size()>0){
					def basePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()
					def zipfile=basePath+"/"+bn+"_paper.zip"
					File file= new File(zipfile)
					file?.delete()
			//		ZipCompressor zca = new ZipCompressor(zipfile);
			//		zca.compressExe(basePath,"**/paper_*.*");
					TestZip zip = new TestZip(zipfile)
					zip.setUsers(users)
					zip.setIncludePrefix("paper_")
					zip.compressExe(basePath+"/"+bn)
					render([download:"/tms/tptUpload/"+bn+"_paper.zip"] as JSON)
				}
	}
	def export(Long id){
//		TptNotice notice= TptNotice.first()
//		def bn =tptAdminService.getCurrentBn()
		def report=tptAdminService.export(id.toString())
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
	def exportPaperMtlRgn(Long id){
//		TptNotice notice= TptNotice.first()
//		def bn =tptAdminService.getCurrentBn()
				
		def report=tptAdminService.exportMtlRgn(id.toString())
//		def entries =report.items
//		entries.eachWithIndex { entry, i ->
//			System.out.print(i + ":")
//			System.out.println(entry.collegeName)
//			entry.value.eachWithIndex { item, index ->
//				System.out.print item.collegeName
//			}
//		}
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
	def downloadPhotos(Long id){
		println id
		def status=[TptRequest.STATUS_APPLYING,TptRequest.STATUS_CHECKED,TptRequest.STATUS_PAPERUPLOAD,TptRequest.STATUS_PAPERCHECKED]
		def users= tptAdminService.requestUsers(id.toString(), status)
		if(!users) {
			render status: HttpStatus.NOT_FOUND
		}
		def filename="photo_"+id+securityService.departmentId
		def basePath= grailsApplication.config.tms.tpt.uploadPath+"/"+id
		println basePath
		response.setHeader("Content-disposition", "filename=\"${filename}.zip\"")
		response.contentType = "application/zip"
		response.outputStream << tptReportService.exportPhotos(users,basePath,"photo_")
		response.outputStream.flush()
	}
	def downloadPapers(Long id){
		def status=[TptRequest.STATUS_APPLYING,TptRequest.STATUS_CHECKED,TptRequest.STATUS_PAPERUPLOAD,TptRequest.STATUS_PAPERCHECKED]		
		def users= tptAdminService.requestUsers(id.toString(), status)		
		if(!users) {
			render status: HttpStatus.NOT_FOUND
		}
		def filename="paper_"+id+securityService.departmentId
		def basePath= grailsApplication.config.tms.tpt.uploadPath+"/"+id
		response.setHeader("Content-disposition", "filename=\"${filename}.zip\"")
		response.contentType = "application/zip"
		response.outputStream << tptReportService.exportPhotos(users,basePath,"paper")
		response.outputStream.flush()
	}
	def downloadAll(Long id){
		def status=[TptRequest.STATUS_APPLYING,TptRequest.STATUS_CHECKED,TptRequest.STATUS_PAPERUPLOAD,TptRequest.STATUS_PAPERCHECKED]
		def users= tptAdminService.requestUsers(id.toString(), status)
//		users.each { item ->
//			println item
//		}
		if(!users) {
			render status: HttpStatus.NOT_FOUND
		}
		def filename="allIn_"+id+securityService.departmentId
		def basePath= grailsApplication.config.tms.tpt.uploadPath+"/"+id
		response.setHeader("Content-disposition", "filename=\"${filename}.zip\"")
		response.contentType = "application/zip"
		response.outputStream << tptReportService.exportPhotos(users,basePath,"*")
		response.outputStream.flush()
	}
}
