package cn.edu.bnuz.tpt

import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tpt.util.WriteTable;
import grails.converters.JSON

import org.springframework.http.HttpStatus

class TptController {
	SecurityService securityService
	TptService tptService
//	def filePath
//	def imgPath
//	def tptNotice= TptNotice.first()
	def today =new Date()
    def index() { 
//		TptNotice tptNotice= TptNotice.first()	
//		def bn = tptService.getCurrentBn()
//		def filePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()+"/"+bn+"/"+securityService.userId 
//		def imgPath="tptUpload/"+bn+"/"+securityService.userId+"/"
	}
	def showNotice(){
//		TptNotice tptNotice= TptNotice.first()
		TptNotice tptNotice= tptService.getCurrentNotice(params.int("id"))
		def boolean inTime=false
		def theDay=new Date()
		if(tptNotice.start<theDay && theDay<tptNotice.end) inTime=true
		def tptRequest 
		if(tptNotice==tptService.getCurrentNotice())tptRequest=tptService.getRequestByBn(tptNotice.bn)
		def historyRequest = TptRequest.findByUserIdAndStatus(securityService.userId,TptRequest.STATUS_PAPERCHECKED)
		render([notice:[
			id:tptNotice.id,
			title:tptNotice.title,
			content:tptNotice.content,
			start:tptNotice.start,
			end:tptNotice.end,
			bn:tptNotice.bn],inTime:inTime,myRequest:tptRequest,succeed:historyRequest?true:false] as JSON)
	}
	def noticeList(){
		def  activeNotices = tptService.getNoticeList()
		render ([notices:activeNotices] as JSON)
	}
	def saveContact(){
//		System.out.println(request.JSON)
//		TptNotice tptNotice= TptNotice.first()
		TptNotice tptNotice= tptService.getCurrentNotice()
		def today =new Date()
		def ending = tptNotice?.end+15
		if(today.before(tptNotice.start) || today.after(ending)){
			render status: HttpStatus.BAD_REQUEST
			return
		}
		RequestForm requestForm=new RequestForm(request.JSON)
		def TptRequest tptRequest =TptRequest.findByUserIdAndBn(securityService.userId,tptService.getCurrentBn())
		if(!tptRequest){
			tptRequest=new TptRequest([
				contact:requestForm.contact,
				phoneNumber:requestForm.phoneNumber,
				email:requestForm.email,
//				foreignCollege:TptCollege.get(requestForm.foreignCollege), //学院不再维护国外大学表2016-09-07
				collegeName:requestForm.collegeName?:tptRequest?.foreignCollege?.name,
				foreignMajor:requestForm.foreignMajor,
				allIn:false,
				bn:tptService.getCurrentBn()])
			tptRequest.dateCreate=new Date();
			tptRequest.userId = securityService.userId
			tptRequest.userName = securityService.userName
			if(!tptRequest.save(flush:true)){
				tptRequest.errors.each{
					println it
				}
			}
		}else{
				if(tptRequest.status!=0 && tptRequest.status!=3){
				render status: HttpStatus.BAD_REQUEST
			}else{
	//			System.out.println(tptRequest.contact)		
				tptRequest?.setContact(requestForm.contact)
				if(requestForm.foreignCollege) tptRequest?.setForeignCollege(TptCollege.get(requestForm.foreignCollege))
				tptRequest?.setCollegeName(requestForm.collegeName)
				tptRequest?.setForeignMajor(requestForm.foreignMajor)
				tptRequest?.setPhoneNumber(requestForm.phoneNumber)
				tptRequest?.setEmail(requestForm.email)
				if(!tptRequest?.save(flush:true)){
					tptRequest.errors.each{
						println it
					}
				}
			}
			
		}
		requestForm.setFormId(tptRequest.id)
		render([tptForm:requestForm] as JSON)
	}
	def getColleges(){
		def colleges
		//		15级以前的学生不关联项目和协议
		if(securityService.userId<'15')
		 colleges= tptService.colleges
		else colleges= tptService.coColleges
		render ([colleges:colleges,username:securityService.userName] as JSON)
	}
	def showRequest(){
		def bn = tptService.getCurrentBn()
		TptRequest tptRequest=TptRequest.findByUserIdAndBn(securityService.userId,bn)
		TptNotice tptNotice= tptService.findNoticeByBn(bn)
		def today =new Date()
		def ending = tptNotice?.end+15
		if((tptRequest==null || tptRequest.status<1) &&( today.before(tptNotice.start) || today.after(ending))){
			render([form:[status:-1]] as JSON)
			return
		}	
		def colleges
//		15级以前的学生不关联项目和协议
		if(securityService.userId<'15') 
		 colleges= tptService.colleges	
		else colleges= tptService.coColleges
//		拼出绝对路径和web相对路径
//		def filePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()+"/"+bn+"/"+securityService.userId
		def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+bn+"/"+securityService.userId
//		def imgPath="tptUpload/"+bn+"/"+securityService.userId+"/"
		def imgPath=bn+"/"+securityService.userId+"/"
		def fileNames = getFileNames(filePath)
		if(tptRequest!=null){
		render([form:[
				formId:tptRequest?.id,
				contact:tptRequest?.contact,
				phoneNumber:tptRequest?.phoneNumber,
				email:tptRequest?.email,
				collegeName:tptRequest?.collegeName?:tptRequest?.foreignCollege?.name,
				foreignMajor:tptRequest?.foreignMajor,
				photoUrl:tptRequest?.photoUrl,
				resultsUrl:tptRequest?.resultsUrl,
				certificateUrl:tptRequest?.certificateUrl,
				paperUrl:tptRequest?.paperUrl,
				status:tptRequest?.status,
				allIn:tptRequest?.allIn],
			imgSrc:[
				photo:getFileName(fileNames,"photo_"),
				cert:pdfFilter(getFileName(fileNames,"certi_")),
				trans1:pdfFilter(getFileName(fileNames,"trans_1")),
				trans2:pdfFilter(getFileName(fileNames,"trans_2")),
				trans3:pdfFilter(getFileName(fileNames,"trans_3"))],
			colleges:colleges,
			end:ending,
			audits:tptRequest?.audits,
			username:securityService.userName,
			paperFile:checkPaperFile(fileNames,"paper_"),
			paperExchFile:checkPaperFile(fileNames,"paper_exch")
			] as JSON)
		}else{
		if(today.before(tptNotice.start) || today.after(tptNotice.end)){
			render([form:[status:-1]] as JSON)
			return
		}
		render([
		imgSrc:[
			photo:getFileName(fileNames,"photo_"),
			cert:pdfFilter(getFileName(fileNames,"certi_")),
			trans1:pdfFilter(getFileName(fileNames,"trans_1")),
			trans2:pdfFilter(getFileName(fileNames,"trans_2")),
			trans3:pdfFilter(getFileName(fileNames,"trans_3"))],
		colleges:colleges,
		username:securityService.userName
		] as JSON)
		}
	}
	/**
	 * 很无奈，在上传文件时无法实时给后台传参数，因此无法判断上传的是什么文件，只好硬性分开5个操作了
	 * 参数放在访问URL中，只有第一次访问能获取到，后面的访问action都似乎没有运行
	 * 日后一定要处理这个问题。
	 * 2016-01-12 发现可以用formData参数传参：参考qem/projectDetail.js
	 * @return
	 */
	def uploadPic1(){
		upload(1)
	}
	private upload(int picType){
//		TptNotice tptNotice= TptNotice.first()
		TptNotice tptNotice= tptService.getCurrentNotice()
		def today =new Date()
		def ending = tptNotice?.end+15
		if(today.before(tptNotice.start) || today.after(ending)){
			render status: HttpStatus.BAD_REQUEST
			return
		}
//		def bn = tptService.getCurrentBn()
		def bn = tptNotice.bn
//		改成查看最新批次的申请单2016-09-05	
		TptRequest tptRequest=TptRequest.findByUserIdAndBn(securityService.userId,bn)
		if(tptRequest!=null && tptRequest.status!=0 && tptRequest.status!=3){
			render status: HttpStatus.BAD_REQUEST
		}else{
		def f = request.getFile('file')
//		拼出绝对路径和web相对路径
		
		def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+bn+"/"+securityService.userId
//		def imgPath="tptUpload/"+bn+"/"+securityService.userId+"/"
		def imgPath=bn+"/"+securityService.userId+"/"
		
		def fileNames = getFileNames(filePath)
		if(!f.empty) {
//			System.out.println(filePath)
			File dir= new File(filePath)
			if(!dir.isDirectory())
				dir.mkdirs()
			def filename=f.originalFilename
			def type=filename.substring(filename.lastIndexOf(".")).toLowerCase()
			switch(picType){
			case 1: filename="photo_"+securityService.userName+"_"+ tptService.getSfzh()+"_"+Math.round(Math.random()*100)+type
					updateFileName(fileNames,"photo_",filename)
					break;
			case 2: filename="certi_"+securityService.userId+"_"+Math.round(Math.random()*100)+type
					updateFileName(fileNames,"certi_",filename)
					break;
			case 3: filename="trans_1_"+securityService.userId+"_"+Math.round(Math.random()*100)+type
					updateFileName(fileNames,"trans_1_",filename)
					break;
			case 4: filename="trans_2_"+securityService.userId+"_"+Math.round(Math.random()*100)+type
					updateFileName(fileNames,"trans_2_",filename)
					break;
			case 5: filename="trans_3_"+securityService.userId+"_"+Math.round(Math.random()*100)+type
					updateFileName(fileNames,"trans_3_",filename)
					break;
			case 6: filename="/paper_"+securityService.userId+"_"+securityService.userName+type					
					break;
			}
			f.transferTo( new File(filePath+"/"+filename) )
		}
		fileNames = getFileNames(filePath)
		render ([success:true,
			imgSrc:[
				photo:getFileName(fileNames,"photo_"),
				cert:pdfFilter(getFileName(fileNames,"certi_")),
				trans1:pdfFilter(getFileName(fileNames,"trans_1")),
				trans2:pdfFilter(getFileName(fileNames,"trans_2")),
				trans3:pdfFilter(getFileName(fileNames,"trans_3"))]] as JSON)
	
		}
	}
	def uploadPic2(){
		upload(2)	
	}
	def uploadPic3(){
		upload(3)
	}
	def uploadPic4(){
		upload(4)
	}
	def uploadPic5(){
		upload(5)
	}
	def uploadPaper(){
//		TptNotice tptNotice= TptNotice.first()
		TptNotice tptNotice= tptService.getCurrentNotice()
		def ending = tptNotice?.end+15		
		if(ending.after(new Date())){//超期不允许上传
			def TptRequest tptRequest= TptRequest.findByUserId(securityService.userId)			
			if(tptRequest?.allowStatus(TptRequest.STATUS_PAPERUPLOAD)){ //检查当前状态是否允许上传论文				
				def f = request.getFile('file')
				if(!f.empty) {
		//			System.out.println(filePath)	
//					拼出绝对路径和web相对路径
					def bn = tptService.getCurrentBn()
					def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+bn+"/"+securityService.userId
//					def imgPath="tptUpload/"+bn+"/"+securityService.userId+"/"
					def imgPath=bn+"/"+securityService.userId+"/"
					def filename=f.originalFilename
					def type=filename.substring(filename.lastIndexOf(".")).toLowerCase()			
					filename="/paper_"+securityService.userId+"_"+securityService.userName+type	
					File file= new File(filePath+"/"+filename)
					if(file?.isFile()){
//						file.delete()
						println file.name
						file?.renameTo(filePath+"/bak_"+Math.round(Math.random()*100)+filename)
					}
					f.transferTo( new File(filePath+"/"+filename) )
//					tptRequest.setStatus(TptRequest.STATUS_PAPERUPLOAD)
//					TptAudit tptAudit=new TptAudit([
//						userId:securityService.userId,
//						userName:securityService.userName,
//						action:TptAudit.ACTION_UPLOAD,
//						date:new Date(),
//						form:tptRequest])
//					if(!tptAudit.save(flush:true)){
//						tptAudit.errors.each{
//							println it
//						}
//					}
					render status:HttpStatus.OK
				}else{
					render status: HttpStatus.BAD_REQUEST
				}
			}else{
					render status: HttpStatus.BAD_REQUEST
			}
		}else{
				render (status: HttpStatus.BAD_REQUEST ,text:[error: message(code: "tpt.error.expire")] as JSON)
		}
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
	private boolean check_photo(List<String> filenames){
		for(String filename:filenames){
			if(filename?.indexOf("photo_")==0){
				return true
			}
		}
		return false;
	}
	private String getFileName(List<String> fileNames,String pre){
		def bn = tptService.getCurrentBn()
		def imgPath="picture/show?bn="+bn+"&id="+securityService.userId+"&filename="
		for(String filename:fileNames){
			if(filename?.indexOf(pre)==0){				
				return imgPath+filename
			}
		}
		return "tptUpload/none.jpg";
	}
	private void updateFileName(List<String> fileNames,String pre,String newFileName){		
		def bn = tptService.getCurrentBn()
//		def filePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()+"/"+bn+"/"+securityService.userId
		def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+bn+"/"+securityService.userId
		for(String filename:fileNames){
			if(filename?.indexOf(pre)==0){
				File file=new File(filePath+"/"+filename)
				file?.renameTo(filePath+"/bak_"+filename)
//				filename=newFileName
//				println "afterUpdate:"+filename
			}
		}
	}
	def showImage(){
		def pre=params.filename
		def bn = tptService.getCurrentBn()
//		def filePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()+"/"+bn+"/"+securityService.userId
		def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+bn+"/"+securityService.userId
		def fileNames = getFileNames(filePath)
		def filename="/tms/"+getFileName(fileNames,pre)
		def map=[filename: filename]
		def type=filename.substring(filename.lastIndexOf("."))
		if(type?.equals(".pdf"))
		{
			render(view:"showPdf",model:map)
		}else{
		render(view:"showImage",model:map)
		}
	}
	def apply(){
//		TptNotice tptNotice= TptNotice.first()
		TptNotice tptNotice= tptService.getCurrentNotice()
		def today =new Date()
		def ending = tptNotice?.end+15
		if(today.before(tptNotice.start) || today.after(ending)){
			render status: HttpStatus.BAD_REQUEST
			return
		}
		def bn = tptService.getCurrentBn()
		def TptRequest tptRequest= TptRequest.findByUserIdAndBn(securityService.userId,bn)
		
//		def filePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()+"/"+bn+"/"+securityService.userId
		def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+bn+"/"+securityService.userId
		def filenames = getFileNames(filePath)
		if(tptRequest?.allowStatus(TptRequest.STATUS_APPLYING) && filenames?.size()>=3 && check_photo(filenames)){
			def colleges = TptCollege.list()
			tptRequest.setStatus(TptRequest.STATUS_APPLYING)
			tptRequest.allIn = true
			TptAudit tptAudit=new TptAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:TptAudit.ACTION_COMMIT,
				date:new Date(),
				form:tptRequest])
			if(!tptAudit.save(flush:true)){
				tptAudit.errors.each{
					println it
				}
				render status: HttpStatus.BAD_REQUEST			
			}else{
			render ([status: HttpStatus.OK,
			form:[
				formId:tptRequest?.id,
				contact:tptRequest?.contact,
				phoneNumber:tptRequest?.phoneNumber,
				email:tptRequest?.email,
				collegeName:tptRequest?.collegeName?:tptRequest?.foreignCollege?.name,
				foreignMajor:tptRequest?.foreignMajor,
				status:tptRequest?.status,
				allIn:tptRequest?.allIn],
			colleges: colleges,
			audits:tptRequest?.audits] as JSON)
			}
			
		}else{
		render status: HttpStatus.BAD_REQUEST
		}
	}
	def writePaperForm(){
//		TptNotice tptNotice= TptNotice.first()
		TptNotice tptNotice=tptService.getCurrentNotice()
		def ending = tptNotice?.end+15
		def today =new Date()
		if(today.after(ending)){//超期不允许上传
			render (status: HttpStatus.BAD_REQUEST ,text:[error: message(code: "tpt.error.expire")] as JSON)
			return
		}
		PaperExchForm paperExchForm=new PaperExchForm(request.JSON)
		
		def TptRequest tptRequest= TptRequest.findByUserId(securityService.userId)
		Map<String, String> map = new HashMap<String, String>();
		map.put("studentID", securityService.userId)
		map.put("name", securityService.userName)
		map.put("major", securityService.currentUser.major?.name)
		map.put("foreignCollege", tptRequest?.collegeName?:tptRequest?.foreignCollege?.name)
		map.put("f_c_major", tptRequest?.foreignMajor)
		map.put("masterCollege", paperExchForm.masterCollege?:"1")
		map.put("master_result", paperExchForm.master_result)
		map.put("paperName", paperExchForm.paperName)
		map.put("abstrct", paperExchForm.content)
		map.put("abstrct_en", paperExchForm.content_en)
		map.put("paperName_en", paperExchForm.paperName_en)
		map.put("bn", tptNotice.bn)
		
//		保存到数据库
		TptPaperMtlRgn tptPaperMtlRgn = TptPaperMtlRgn.findByStudentID(securityService.userId)
		if(tptPaperMtlRgn){
			tptPaperMtlRgn.setMasterCollege(paperExchForm.masterCollege?:"1")
			tptPaperMtlRgn.setMaster_result(paperExchForm.master_result)
			tptPaperMtlRgn.setPaperName(paperExchForm.paperName)
			tptPaperMtlRgn.setAbstrct(paperExchForm.content)
			tptPaperMtlRgn.setAbstrct_en(paperExchForm.content_en)
			tptPaperMtlRgn.setPaperName_en(paperExchForm.paperName_en)
			
		}else{
			tptPaperMtlRgn=new TptPaperMtlRgn(map)
			tptPaperMtlRgn.abstrct=paperExchForm.content
			tptPaperMtlRgn.type =paperExchForm.type
		}
		if(!tptPaperMtlRgn.save(flush:true)){
			def messageStr=""
			tptPaperMtlRgn.errors.each{
				messageStr+= it.toString()+"."
			}
			render (status: HttpStatus.BAD_REQUEST ,text:[error: message(code: "tpt.error.mtlrgn")+messageStr] as JSON)
			return
		}
//		如果数据保存成功，生成word文档，保存到指定位置
		def bn = tptService.getCurrentBn()
		def templateFile =grailsAttributes.getApplicationContext().getResource("/template/").getFile().toString()
//		def filePath= grailsAttributes.getApplicationContext().getResource("/tptUpload/").getFile().toString()+"/"+bn+"/"+securityService.userId
		def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+bn+"/"+securityService.userId
		if("1".equals(paperExchForm.type))
			templateFile= templateFile+"/undergraduate.doc"
		else if("2".equals(paperExchForm.type))
			templateFile= templateFile+"/master.doc"
		else if("3".equals(paperExchForm.type))
			templateFile= templateFile+"/course.doc"
		def destFile= filePath+"/paper_exch"+securityService.userId+".doc"
//		System.out.println(securityService.currentUser.major.name)
		WriteTable wt=new WriteTable()
		try{
			wt.write(templateFile, destFile, map)
		}catch(Exception e){
			render (status: HttpStatus.BAD_REQUEST ,text:[error: message(code: "tpt.error.mtlrgn2doc")+e.message] as JSON)
			return
		}
		render status:HttpStatus.OK
	}
	def help(){}
	def finishPaper(){
		def TptRequest tptRequest= TptRequest.findByUserId(securityService.userId)
		if(tptRequest?.allowStatus(TptRequest.STATUS_PAPERUPLOAD)){
			tptRequest.setStatus(TptRequest.STATUS_PAPERUPLOAD)
			TptAudit tptAudit=new TptAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:TptAudit.ACTION_UPLOAD,
				date:new Date(),
				form:tptRequest])
			if(!tptAudit.save(flush:true)){
				tptAudit.errors.each{
					println it
				}
			}
			def colleges = TptCollege.list()
			def filePath= grailsApplication.config.tms.tpt.uploadPath+"/"+tptRequest?.bn+"/"+securityService.userId
			def fileNames = getFileNames(filePath)
			render ([form:[
						formId:tptRequest?.id,
						contact:tptRequest?.contact,
						phoneNumber:tptRequest?.phoneNumber,
						email:tptRequest?.email,
						collegeName:tptRequest?.collegeName?:tptRequest?.foreignCollege?.name,
						foreignMajor:tptRequest?.foreignMajor,
						status:tptRequest?.status,
						allIn:tptRequest?.allIn],
					colleges: colleges,
					paperFile:checkPaperFile(fileNames,"paper_"),
					paperExchFile:checkPaperFile(fileNames,"paper_exch"),
					audits:tptRequest?.audits] as JSON)
		}else{
			render (status: HttpStatus.BAD_REQUEST ,text:[error: message(code: "tpt.error.illegal")] as JSON)
		}
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

}

