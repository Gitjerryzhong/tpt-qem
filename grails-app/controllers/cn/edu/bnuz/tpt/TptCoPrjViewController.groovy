package cn.edu.bnuz.tpt
import grails.converters.JSON
import org.springframework.http.HttpStatus
import cn.edu.bnuz.qem.project.MajorType
import org.springframework.http.HttpStatus
import cn.edu.bnuz.tpt.util.Excel
class TptCoPrjViewController {
	TptCoProjectService tptCoProjectService
    def index() {}
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
	/**
	 * 协议汇总列表
	 * @return
	 */
	def projectList(){
		def projectList = tptCoProjectService.getProjectList()
		render ([projectList:projectList] as JSON)
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
}
