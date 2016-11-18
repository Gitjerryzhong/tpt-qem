package cn.edu.bnuz.qem
import java.util.List
import cn.edu.bnuz.qem.update.UpdateTask
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
	 * �ϴ��ļ�
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
	 * ��ȡ�ϴ����걨��
	 */
	def fileList(){
		def dateStr=new Date().format("yyyyMMdd")
		def taskId=params.taskId
		java.util.List<String> fileList = getFileNames(taskId,dateStr)
		render ([fileList:fileList] as JSON)
	}
	/***
	 * ���ڻ�ȡָ����Ŀ�������ָ�����ڵ����и���
	 * @param projectId
	 * @return
	 */
	private List<String> getFileNames(String taskId,String dateStr){
		def filePath= grailsApplication.config.tms.qem.uploadPath+"/update/${securityService.userId}/${taskId}/${dateStr}"
		
		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(dir.isDirectory()){
			for(File file: dir.listFiles()){
				if(file.isDirectory()){ //����걨����Ŀ¼
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
	 * ɾ������
	 */
	def delAttach(){
		def dateStr=new Date().format("yyyyMMdd")
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
	 * �ύ�������
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
}
