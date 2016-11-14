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
class QemUpdateAdminController {
	SecurityService securityService
	ExportService exportService
	UpdateAdminService	updateAdminService
    def index() {
		def taskList=updateAdminService.taskUpdateList()
//		def taskCounts=updateAdminService.taskUpdateCounts()
		render (view:"index",model:[taskList:taskList])
//		render (view:"taskMenagement",model:[id:id])
	}
	def historyRequestList(){
		def taskList=updateAdminService.taskUpdateList()
		def taskCounts=updateAdminService.taskUpdateCounts()
		render ([taskList:taskList,taskCounts:taskCounts] as JSON)
	}
	/**
	 * 变更单详情
	 * @return
	 */
	def updateDetail(){
		def form_id=params.int('id')?:0
		def form = UpdateTask.get(form_id)
		if(form){
			def nextId= updateAdminService.checkingNext(form_id,form.status)
			def prevId= updateAdminService.checkingPrev(form_id,form.status)
			def task=QemTask.get(form.taskId)
			render([form:form,
					pager:[nextId:nextId,prevId:prevId],
					teacherName: Teacher.get(form.teacherId)?.name,
					type:QemType.get(form.qemTypeId)?.name,
					commitDate:form.commitDate.format("yyyy-MM-dd"),
					task:task,
					origTeacherName:task?.teacher.name,
					origType:task?.qemType.name,
					fileList:getFileNames(form.taskId.toString(),form.commitDate.format("yyyyMMdd"))] as JSON)
		}else{
			render status: HttpStatus.BAD_REQUEST
		}
		
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
}
