package cn.edu.bnuz.tpt
import grails.converters.JSON
import org.springframework.http.HttpStatus
import cn.edu.bnuz.qem.project.MajorType
import org.springframework.http.HttpStatus
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
}
