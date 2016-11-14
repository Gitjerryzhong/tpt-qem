package cn.edu.bnuz.tpt
import grails.converters.JSON
import org.springframework.http.HttpStatus
import cn.edu.bnuz.qem.project.MajorType
import org.springframework.http.HttpStatus
class TptCoPrjViewController {
	TptCoProjectService tptCoProjectService
    def index() {}
	/**
	 * ��ѯЭ������
	 * @return
	 */
	def getProjectDetail(){
		def id = params.long("coProjectId") ?: 0
		def projectDetail = null
		if(id) projectDetail = tptCoProjectService.getProjectDetail(id)
		render ([projectDetail:projectDetail] as JSON)
	}
	/**
	 * Э������б�
	 * @return
	 */
	def projectList(){
		def projectList = tptCoProjectService.getProjectList()
		render ([projectList:projectList] as JSON)
	}
	/**
	 * �ṩרҵ����������
	 */
	def getProjectItems(){
		def id = params.long("coProjectId") ?: 0
		def projectItems =null
		if(id) projectItems = tptCoProjectService.getProjectItem(id)
		def majors= tptCoProjectService.getMajor()
		render ([projectItems:projectItems,majors:majors] as JSON)
	}
}
