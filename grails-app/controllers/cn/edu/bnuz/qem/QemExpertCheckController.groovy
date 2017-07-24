package cn.edu.bnuz.qem

import grails.converters.JSON

import org.springframework.http.HttpStatus

import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.Attention
import cn.edu.bnuz.qem.project.QemStage
import cn.edu.bnuz.qem.project.QemAudit
import cn.edu.bnuz.qem.project.QemTaskAudit
import cn.edu.bnuz.qem.review.ExpertsView
import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.qem.organization.Experts

class QemExpertCheckController {
	ExpertService expertService
	SecurityService securityService
	ExportService exportService
    def index() { }
	def projectsForCheck(){	
		def status=params.int("type")?:0	
		def requests
		switch(status){
			case 0: requests=expertService.requestList()
					break
			case 1: requests=expertService.reviewedProjectList()
					break
		}		
		def total =expertService.getCounts()
		def total1=expertService.getCountsTask()
//		println total1
		render ([requests: requests, pager: [ total: total,total1:total1]] as JSON)
	}
	def reviewSave(){
		def exp_review= new ReviewCommand(request.JSON)
		if(exp_review.projectId){
			def project=QemProject.get(exp_review.projectId)
			if(!project){
				render status: HttpStatus.BAD_REQUEST
				return
			}			
			def expertsReview
			if(exp_review.id){
				expertsReview = ExpertsView.get(exp_review.id)
				expertsReview.setView(exp_review.content)
				expertsReview.setScoreArray(exp_review.scoreList)
				expertsReview.setTotalScore(exp_review.totalScore)
				expertsReview.setCommit(exp_review.commit?true:false)
				expertsReview.setResult(exp_review.result)
			}else{
				expertsReview = new ExpertsView([
				expert:expertService.expert,
				view:exp_review.content,
				scoreArray:exp_review.scoreList,
				totalScore:exp_review.totalScore,
				commit:exp_review.commit?true:false,
				result:exp_review.result,
				review:project.review
				])
			}
			expertsReview.save(flush:true)
			
			if(exp_review.commit){ //如果提交，创建操作日志	
				def action
				switch(exp_review.result){			
					case 0: action=QemAudit.ACTION_BEGIN_YES
							break
					case 1: action=QemAudit.ACTION_BEGIN_NO
							break
					case 2: action=QemAudit.ACTION_BEGIN_NONE
				}
				def qemAudit = new QemAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:action,
					content:null,
					date: new Date(),
					form:project])
				qemAudit.save(flush:true)
			}
			render([expertReview:[
				projectId:exp_review.projectId,
				id:expertsReview.id,
				scoreList:expertsReview.scoreArray,
				result:expertsReview.result,
				totalScore:expertsReview.totalScore,
				commit:expertsReview.commit,
				content:expertsReview.view]]as JSON)
		}else{
		
		render status: HttpStatus.NOT_FOUND
		}
	}
	
	def getReview(){
		long id=params.long("id")
		if(id) {
			def expertsReview=expertService.getExpertView(id)
			if(expertsReview){
				render ([collegeAudit:QemProject.get(id)?.collegeAudit,
					expertReview:[
					id:expertsReview.id,
					scoreList:expertsReview.scoreArray,
					result:expertsReview.result,
					totalScore:expertsReview.totalScore,
					commit:expertsReview.commit,
					content:expertsReview.view]]as JSON)
			}else render ([collegeAudit:QemProject.get(id)?.collegeAudit] as JSON)
		}
		else render status: HttpStatus.OK
//		def review = QemProject.get(id)?.review?
	}
	def getReviewByStage(){
		long id=params.long("id")
		if(id) {
			def stage = QemStage.get(id)
			def expertsReview=expertService.getExpertViewByStage(id)
			if(expertsReview){
				render ([collegeAudit:stage.collegeAudit,
					expertReview:[
					id:expertsReview.id,
					scoreList:expertsReview.scoreArray,
					result:expertsReview.result,
					totalScore:expertsReview.totalScore,
					commit:expertsReview.commit,
					content:expertsReview.view]]as JSON)
			}else render ([collegeAudit:stage.collegeAudit] as JSON)
		}
		else render status: HttpStatus.BAD_REQUEST
	}
	def download(long id){
		def qemProject=QemProject.get(id)
		if(qemProject){
//			def basePath= grailsAttributes.getApplicationContext().getResource("/qemUpload/").getFile().toString()
			def basePath= grailsApplication.config.tms.qem.uploadPath
			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(qemProject.projectName+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(qemProject,basePath)
			response.outputStream.flush()

		}
	}
	def downloadTask(long id){
		def qemTask=QemTask.get(id)
		if(qemTask){
			def basePath= grailsAttributes.getApplicationContext().getResource("/qemUpload/").getFile().toString()
//

			response.setContentType("application/zip")
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(qemTask.projectName+".zip", "UTF-8")+"\"")
			response.outputStream << exportService.download(qemTask,basePath)
			response.outputStream.flush()

		}
	}
	def taskForCheck(){
		def status=params.int("type")?:0
		def requests
		def requests0=expertService.taskList()
		def requests1=expertService.reviewedTaskList()
		switch(status){
			case 0: requests=requests0
					break
			case 1: requests=requests1
					break
		}
		def total =expertService.getCounts()
		def total1=expertService.getCountsTask()
		render ([requests: requests, pager: [ total: total,total1:total1]] as JSON)
	}
	def reviewTaskSave(){
		def exp_review= new ReviewCommand(request.JSON)
		if(exp_review.projectId){
			def project=QemTask.get(exp_review.projectId)
			def stage = QemStage.get(exp_review.stageId)
			if(!project || !stage){
				render status: HttpStatus.BAD_REQUEST
				return
			}
			def expertsReview
			if(exp_review.id){
				expertsReview = ExpertsView.get(exp_review.id)
				expertsReview.setView(exp_review.content)
				expertsReview.setScoreArray(exp_review.scoreList)
				expertsReview.setTotalScore(exp_review.totalScore)
				expertsReview.setCommit(exp_review.commit?true:false)
				expertsReview.setResult(exp_review.result)
			}else{
				expertsReview = new ExpertsView([
				expert:expertService.expert,
				view:exp_review.content,
				scoreArray:exp_review.scoreList?:"",
				totalScore:exp_review.totalScore?:0,
				commit:exp_review.commit?true:false,
				result:exp_review.result,
				review:stage.review
				])
			}
			
			if(!expertsReview.save(flush:true)){
				expertsReview.errors.each{
					println it
				}
			}
			
			if(exp_review.commit){ //如果提交，创建操作日志
				stage.setStatus(QemStage.S_REVIEW)
				stage.save()
				def action
				switch(exp_review.result){
					case 0: action=QemTaskAudit.ACTION_BEGIN_YES
							break
					case 1: action=QemTaskAudit.ACTION_BEGIN_NO
							break
					case 2: action=QemTaskAudit.ACTION_BEGIN_NONE
				}
				def qemAudit = new QemTaskAudit([
					userId:securityService.userId,
					userName:securityService.userName,
					action:action,
					content:null,
					date: new Date(),
					objectId:project.id,
					src:project.class.name])
				qemAudit.save(flush:true)
			}
			render([expertReview:[
				projectId:exp_review.projectId,
				id:expertsReview.id,
				scoreList:expertsReview.scoreArray,
				result:expertsReview.result,
				totalScore:expertsReview.totalScore,
				commit:expertsReview.commit,
				content:expertsReview.view]]as JSON)
		}
		
		render status: HttpStatus.NOT_FOUND
	}
	
	def showAttention(){
		def attention= expertService.attentionView()
		List<String> fileNames=new ArrayList<String>()
		if( attention){
			def filePath= grailsApplication.config.tms.qem.uploadPath+"/attentionFile"
			
			def file = new File(filePath+"/"+attention.publishDate.format("yyyyMMdd"))
			if(file.isDirectory()){ //如果申报书子目录
				for(File f:file.listFiles()){
					if(f.name.indexOf("del_")==-1) fileNames.add(file.name+"___"+f.name)
				}
			}else if(file.name.indexOf("del_")==-1){
				fileNames.add(file.name)
			}
		}
		def total =expertService.getCounts()
		def total1=expertService.getCountsTask()
		render([attention:attention,fileList:fileNames, pager: [ total: total,total1:total1]] as JSON)
	}
	def downloadAttentAtt(Long id){
		def attention=Attention.get(id)
		def basePath= grailsApplication.config.tms.qem.uploadPath+"/attentionFile/"+attention.publishDate.format("yyyyMMdd")
		response.setContentType("application/zip")
		response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(attention.title+".zip", "UTF-8")+"\"")
		response.outputStream << exportService.download(basePath)
		response.outputStream.flush()
	}
	def relatedTask(long id){
		render([taskList:expertService.relatedTaskList(id)]as JSON)
	}
	class ReviewCommand{
		Long projectId			//项目id或任务书id
		Long id
		String scoreList
		String stageId
		Integer result
		Integer totalScore
		Boolean commit
		String content
		public String toString(){
			return id+"/"+scoreList+"/"+result+"/"+totalScore+"/"+content
		}
		public setResult(String r){
			result=Integer.parseInt(r)
		}		
	}
}
