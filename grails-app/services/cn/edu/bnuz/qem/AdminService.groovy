package cn.edu.bnuz.qem

import grails.transaction.Transactional
import cn.edu.bnuz.qem.project.Attention
import cn.edu.bnuz.qem.project.QemParentType
import cn.edu.bnuz.qem.project.QemType
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.qem.organization.Experts
class AdminService {
	SecurityService securityService
    def getTypes() {
		def results = QemType.executeQuery '''
select new map(
	qt.id as id,
	qt.name as name,
	qt.parentType.id as parentTypeId,
	qpt.parentTypeName as parentTypeName,
	qt.cycle as cycle,
	qt.actived as actived,
	qt.downLoadUrl	as downLoadUrl
)
from QemType qt join qt.parentType qpt 
'''
		return [
			items: results
		]
	}
	def getTeachers(String departmentId) {
		def results = Teacher.executeQuery '''
select new map(
	tc.id as id,
	tc.name as name,
	dp.id as departmentId	
)
from Teacher tc join tc.department dp 
'''
	}
	def loadTeachers() {
		def results = Teacher.executeQuery '''
select new map(
	tc.id as id,
	tc.name as name,
	tc.sex as sex,
	tc.email as email,
	tc.longPhone as longPhone,
	tc.officeAddress as officeAddress,
	tc.external as external,
	dp.name as departmentName	
)
from Teacher tc join tc.department dp 
'''
	}
	def loadExperts() {
		def results = Experts.executeQuery '''
select new map(
	tc.id as id,
	tc.name as name,
	tc.sex as sex,
	tc.email as email,
	tc.longPhone as longPhone,
	tc.officeAddress as officeAddress,
	tc.external as external,
	dp.name as departmentName,
	ex.discipline as majorTypes
)
from Experts ex join ex.teacher tc join tc.department dp 
'''
	}
	def saveAttention(def attentionForm){
		Attention attention=new Attention([
			title:attentionForm.title,
			content:attentionForm.content,
			publishDate:new Date(),
			author:securityService.userName])
		if(!attention.save(flush:true)){
			attention.errors.each{
				println it
			}
		}
	}
	def updateAttention(def attentionForm){
		Attention attention=Attention.get(attentionForm.id)
		attention.setTitle(attentionForm.title)
		attention.setContent(attentionForm.content)
		if(!attention.save(flush:true)){
			attention.errors.each{
				println it
			}
		}
	}
}
