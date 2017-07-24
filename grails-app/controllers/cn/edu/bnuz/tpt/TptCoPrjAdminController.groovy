package cn.edu.bnuz.tpt
import grails.converters.JSON
import org.springframework.http.HttpStatus
import cn.edu.bnuz.qem.project.MajorType
import org.springframework.http.HttpStatus
import cn.edu.bnuz.tpt.util.Excel
class TptCoPrjAdminController {
	TptCoProjectService tptCoProjectService
    def index() { }
	/**
	 * 协议类别
	 * @return
	 */
	def saveCoType(){
		def coType
		def id = params.int("coTypeId") ?: 0
		if(id){
			coType=TptCoType.get(id)
//			System.out.println(request.JSON?.name)
			coType.setName(request.JSON?.name)
		}else{
			coType=new TptCoType(request.JSON)
		}
		if(!coType.save(flush:true)){
			coType.errors.each{
				println it
			}
		}
		showCoType()
	}
	def showCoType(){
		def coTypes= TptCoType.getAll()
		render([coTypes:coTypes] as JSON)
	}
	def rmCoType(){
		def id = params.int("coTypeId") ?: 0
		def coType=TptCoType.get(id)
		coType.delete(flush:true)
		showCoType()
	}
	/**
	 * 项目类别
	 * @return
	 */
	def saveCoCountry(){
		def coType
		def id = params.int("coTypeId") ?: 0
		if(id){
			coType=TptCoCountry.get(id)
//			System.out.println(request.JSON?.name)
			coType.setName(request.JSON?.name)
		}else{
			coType=new TptCoCountry(request.JSON)
		}
		if(!coType.save(flush:true)){
			coType.errors.each{
				println it
			}
		}
		showCoCountry()
	}
	def showCoCountry(){
		def coTypes= TptCoCountry.getAll()
		render([coTypes:coTypes] as JSON)
	}
	def rmCoCountry(){
		def id = params.int("coTypeId") ?: 0
		def coType=TptCoCountry.get(id)
		coType.delete(flush:true)
		showCoType()
	}
	def saveProject(){
		def coProject = new CoProject(request.JSON)
		println coProject.toString()
		def tptCoProject = tptCoProjectService.save(coProject)
		render ([tptCoProject:tptCoProject] as JSON)
	}
	/**
	 * 提供协议类型和项目类型下拉框数据
	 * @return
	 */
	def getBase(){
		def coTypes= TptCoType.getAll()
		def coCountrys = TptCoCountry.getAll()
		render([coTypes:coTypes,coCountrys:coCountrys] as JSON)
	}
	/**
	 * 提供专业下拉框数据
	 */
	def getProjectItems(){
		def id = params.long("coProjectId") ?: 0
		def projectItems =null
		if(id) projectItems = tptCoProjectService.getProjectItem(id)
		def majors= tptCoProjectService.getMajor()
		render ([projectItems:projectItems,majors:majors] as JSON)
	}
	/**
	 * 协议汇总列表
	 * @return
	 */
	def projectList(){
		def projectList = tptCoProjectService.getProjectList()
		render ([projectList:projectList] as JSON)
	}
	/**
	 * 查询协议详情
	 * @return
	 */
	def getProjectDetail(){
		def id = params.long("coProjectId") ?: 0
		def projectDetail = null
		if(id) projectDetail = tptCoProjectService.getProjectDetail(id)
		render ([projectDetail:projectDetail] as JSON)
	}
	def saveProjectItem(){
		def coProjectItem = new CoProjectItem(request.JSON)
		println coProjectItem.toString()
		def tptCoProjectItem
		if(coProjectItem.id) tptCoProjectItem=tptCoProjectService.updateItem(coProjectItem)
		else tptCoProjectItem = tptCoProjectService.saveItem(coProjectItem)
		render ([tptCoProjectItem:tptCoProjectItem] as JSON)
	}
	def studentList(){
		if(params.studentId!=null || params.co_Country!=null){
			render([studnetList:tptCoProjectService.listStudent(params.studentId,params.co_Country),
				proList:TptCoCountry.getAll()] as JSON)
		}else{
			render status:HttpStatus.BAD_REQUEST
		}
		
	}
	def getProList(){
		render ([proList:TptCoCountry.getAll(),
			course:tptCoProjectService.findCourseByProject(),
			majors: tptCoProjectService.getMajor()] as JSON)
	}
	def createUsers(){
		def users=new UserForm(request.JSON)
		def results=tptCoProjectService.studentSave(users)
//		println results.successCount
		render ([results:results] as JSON)
	}
	def deleteStudent(){
		if(params.studentId!=null || params.co_Country!=null){
			if(params.id){
				T_ZZ_XSMD item=new T_ZZ_XSMD(
					id:params.long("id")?:0,
					xh:params.xh)
				tptCoProjectService.deleteStudent(item)
			}
			render([studnetList:tptCoProjectService.listStudent(params.studentId,params.co_Country)] as JSON)
		}else{
			render status:HttpStatus.BAD_REQUEST
		}
	}
	def editStudent(){
		def student= new T_ZZ_XSMD(request.JSON)
				tptCoProjectService.editStudent(student)
		render status:HttpStatus.OK
	}
	def getProjects(){
		render ([proList:tptCoProjectService.getProjects(params.studentId)] as JSON)
	}
	/**
	 * 按协议名称聚合
	 * @return
	 */
	def projectIntegrate(){
		def projectList = tptCoProjectService.projectIntegrate()
		render ([projectList:projectList] as JSON)
	}
	/**
	 * 导出excel
	 */
	def export(long id){
		def report
		switch(id){
			case 0: report=tptCoProjectService.projectIntegrate()
					break;
			case 1: report=tptCoProjectService.getProjectList()
					def currentYear=new Date().format("yyyy") as int
					report=report.grep{
						((it.effeYears >> (currentYear-it.beginYear)) & 1 )==1
					}
					break;
			case 2: report=tptCoProjectService.getProjectList()
					break;
		}
		if(report) {
			//转化为中文文件名
			def filename=message(code:"tpt.fileType.${id}",args:[])
			def template = grailsApplication.mainContext.getResource("excel/tpt-coprj-summary.xls").getFile()
			def excel = new Excel()
			excel.title=filename
			def workbook = excel.exportReport(template, report, id)
			response.setContentType("application/excel")
			
			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode("${filename}.xls", "UTF-8")+"\"")
			workbook.write(response.outputStream)
		} else {
			render status: HttpStatus.NOT_FOUND
		}
	}
//	def exportData(){
//		def data=request.JSON
//		//转化为中文文件名
//			def filename=message(code:"tpt.fileType.3",args:[])
//			def template = grailsApplication.mainContext.getResource("excel/tpt-coprj-summary.xls").getFile()
//			def excel = new Excel()
//			excel.title=filename
//			def workbook = excel.exportReport(template, data, 3)
//			response.setContentType("application/excel")
//			
//			response.setHeader("Content-disposition", "attachment;filename=\""+ java.net.URLEncoder.encode("${filename}.xls", "UTF-8")+"\"")
//			workbook.write(response.outputStream)
//		render status: HttpStatus.OK
//	}
}
