package cn.edu.bnuz.qem

import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.MajorType
import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.qem.organization.Experts
import cn.edu.bnuz.qem.review.ExpertsView

class ProjectAdminService {
	def bn
	/**
	 * 6-27改成只用于查询专家评审汇总
	 * @param offset
	 * @param max
	 * @return
	 */
	def requestList(int offset,int max){
		def results = QemProject.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.discipline as discipline,
	qp.position as position,
	qp.phoneNum as phoneNum,
	m.shortName as departmentName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.commitDate as commitDate,	
	qp.groupId as groupId,
	rv.experts as experts,
	SUM(CASE ev.result WHEN 0 THEN 1 ELSE 0 END) as pass,
	SUM(CASE ev.result WHEN 1 THEN 1 ELSE 0 END) as ng,
	SUM(CASE ev.result WHEN 2 THEN 1 ELSE 0 END) as waiver,
	AVG(ev.totalScore) as avgScore,
	GROUP_CONCAT(tc.name,'###[',ev.view,']') as view
)
from QemProject qp join qp.teacher t join qp.department m join qp.qemType qt join qp.review rv left join rv.expertView ev join ev.expert ex join ex.teacher tc 
where qp.collegeStatus=1 and qp.bn=:bn and ev.commit=true 
group by qp
''',[bn:getCurrentBn()] ,[max: max,offset:offset]
		
		return results
	}
	/**
	 * 6-27修改：原来是依据评审状态统计，0以上表示formeeting，4以上表示reviewed，4以下表示未评审，已不记得这样统计的原因
	 * @return
	 */
	def requestCounts(){
		def results = QemProject.executeQuery '''
select new map(
		SUM(CASE WHEN rv.experts is null or rv.experts='' THEN 1 ELSE 0 END) as total,
		SUM(CASE WHEN rv.status<4 and rv.id in(select distinct ev.review.id from ExpertsView ev) THEN 1 ELSE 0 END) as noreview,
		SUM(CASE WHEN rv.status>=4 and rv.id in(select distinct ev.review.id from ExpertsView ev) THEN 1 ELSE 0 END) as reviewed,
		SUM(CASE WHEN rv.id in(select distinct ev.review.id from ExpertsView ev) THEN 1 ELSE 0 END) as formeeting
)
from QemProject qp join qp.review rv 
where  qp.collegeStatus=1 and qp.bn=:bn 
''',[bn:getCurrentBn()]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
//	def requestCounts(){
//		def results = QemProject.executeQuery '''
//select new map(
//		count( qp.id) as total,
//		SUM(CASE WHEN rv.status<4 and rv.id in(select distinct ev.review.id from ExpertsView ev) THEN 1 ELSE 0 END) as noreview,
//		SUM(CASE WHEN rv.status>=4 and rv.id in(select distinct ev.review.id from ExpertsView ev) THEN 1 ELSE 0 END) as reviewed,
//		SUM(CASE WHEN rv.status>0 and rv.id in(select distinct ev.review.id from ExpertsView ev) THEN 1 ELSE 0 END) as formeeting
//)
//from QemProject qp join qp.review rv 
//where  qp.collegeStatus=1 and qp.bn=:bn 
//''',[bn:getCurrentBn()]
//		if(results) {
//			return results[0]
//		} else {
//			return null
//		}
//	}
	def myjorTypes(){
		def results = MajorType.executeQuery '''
select distinct mt.majorTypeName from MajorType mt
'''
	}
	def requestUngroup(){
		def results = QemProject.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qp.phoneNum as phoneNum,
	d.shortName as departmentName,
	qp.discipline as discipline,
	qp.major as majorTypeName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.commitDate as commitDate,
	qp.groupId as groupId
)
from QemProject qp join qp.teacher t join qp.department d join qp.qemType qt
where qp.collegeStatus=1 and qp.bn=:bn 
'''	,[bn:getCurrentBn()]			
				return results
	}
	
	def requestGroups(){
		def results = QemProject.executeQuery '''
select distinct qp.groupId
from QemProject qp 
where qp.collegeStatus=1 and qp.bn=:bn
order by groupId
'''	,[bn:getCurrentBn()]			
		return results
	}
	def requestForExperts(){
		def results = QemProject.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	m.shortName as departmentName,
	qp.major as majorTypeName,
	qp.discipline as discipline,
	rv.experts as experts,
	qp.groupId as groupId
)
from QemProject qp join qp.teacher t join qp.qemType qt join qp.department m join qp.review rv
where qp.collegeStatus=1 and qp.bn=:bn  
''',[bn:getCurrentBn()]
		if(results){
			results.each {item->
				if(item.experts) item.experts= getExpertsName(item.experts)
			}
		}
		return results
	}
	def requestNoExperts(){
		def results = QemProject.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	m.name as departmentName,
	qp.discipline as majorTypeName,
	rv.experts as experts,
	qp.groupId as groupId
)
from QemProject qp join qp.teacher t join qp.qemType qt join qp.department m join qp.review rv
where qp.collegeStatus=1 and qp.bn=:bn and rv.experts is null
''',[bn:getCurrentBn()]
		
		return results
	}
	def getExperts(String department,String majorType){
		def results =Experts.executeQuery '''
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
where (ex.discipline='ALL' or ex.discipline like :majorType) and dp.name<>:department
''',[majorType:'%'+majorType+'%',department:department]
		
		return results
	}
	def getExperts(){
		def results =Experts.executeQuery '''
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
		
		return results
	}
	def getCounts(String expert){
		def results = QemProject.executeQuery '''
select count(*)
from QemProject qp join qp.review rv
where qp.collegeStatus=1 and qp.bn=:bn and rv.experts like :expert
''',[expert:'%'+expert+'%',bn:getCurrentBn()]
	if(results){
		return results[0]
	}else return null
		
	}
	def getExpertsName(String str){
		def array=str.split(";")
		def results =Experts.executeQuery '''
select GROUP_CONCAT(tc.id,'|',tc.name)
from Experts ex join ex.teacher tc
where tc.id in :experts
''',[experts:array]
		if(results)
			return results[0]
		else return null
	}
	def isExpert(Teacher teacher){
		def results =Experts.executeQuery '''
select ex
from Experts ex 
where ex.teacher.id =:id
''',[id:teacher.id]
				if(results)
					return true
				else return false

	}
	def requestList(){
		def results = ExpertsView.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qp.phoneNum as phoneNum,
	m.shortName as major,
	qp.projectLevel as projectLevel,
	qt.name as type,	
	rv.result as result,
	rv.detail as content,
	rv.status as status,
	qp.commitDate as commitDate,
	SUM(CASE ev.result WHEN 0 THEN 1 ELSE 0 END) as pass,
	SUM(CASE ev.result WHEN 1 THEN 1 ELSE 0 END) as ng,
	SUM(CASE ev.result WHEN 2 THEN 1 ELSE 0 END) as waiver
)
from QemProject qp join qp.teacher t join qp.department m join qp.qemType qt join qp.review  rv join rv.expertView ev 
where qp.collegeStatus=1 and qp.bn=:bn 
group by qp
''',[bn:getCurrentBn()]
		
		return results
	}
	def export(){
		def results = QemProject.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qp.phoneNum as phoneNum,
	m.name as major,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.commitDate as commitDate,	
	qp.groupId as groupId,
	rv.experts as experts,
	GROUP_CONCAT(ev.expert.teacher.id) as over,
	SUM(CASE ev.result WHEN 0 THEN 1 ELSE 0 END) as pass,
	SUM(CASE ev.result WHEN 1 THEN 1 ELSE 0 END) as ng,
	SUM(CASE ev.result WHEN 2 THEN 1 ELSE 0 END) as waiver,
	AVG(ev.totalScore) as avgScore,
	GROUP_CONCAT('###',ev.view) as view
)
from QemProject qp join qp.teacher t join qp.department m join qp.qemType qt join qp.review rv join rv.expertView ev
where qp.collegeStatus=1 and qp.bn=:bn and ev.commit=true
group by qp
''',[bn:getCurrentBn()]
		
		return results
	}
	def exportReqs() {
		def results=QemProject.executeQuery '''
select new map(
	qp.id as id,
	tc.id as userId,
	tc.name as userName,
	tc.sex as sex,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.specailEmail as specailEmail,
	qp.discipline	as discipline,
	qp.direction	as direction,
	dp.name			as departmentName,
	qp.major		as majorName,
	qt.name			as qemTypeName,
	pt.parentTypeName as parentType,
	qp.projectName	as projectName,
	qp.expectedGain as expectedGain,
	qp.projectLevel	as projectLevel,
	qp.commitDate	as commitDate,
	qp.isSubmit		as commit,
	qp.collegeStatus	as collegeStatus,
	qp.specialEdit	as specialEdit,
	qp.position		as position,
	qp.phoneNum		as phoneNum,
	qp.collegeAudit as collegeAudit,
	rv.status		as reviewStatus
)
from QemProject qp join qp.qemType qt join qt.parentType pt join qp.department dp join qp.teacher tc join qp.review rv 
where qp.collegeStatus=1 and qp.bn=:bn
''',[bn:getCurrentBn()]
		return [
			items: results
		]
	}
	def setCurrentBn(String bn){
		this.bn=bn
	}
	def getCurrentBn(){
//		def notice=Notice.last()
//		return notice?.bn?:""
		return bn
	}
	def requestListAll(String bn){
		def results = QemProject.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qp.phoneNum as phoneNum,
	qp.collegeAudit as collegeAudit,
	qp.discipline as discipline,
	qp.major as major,
	m.shortName as departmentName,
	qp.projectLevel as projectLevel,
	qp.bn as bn,
	qt.name as type,
	rv.status as status,	
	qp.commitDate as commitDate
)
from QemProject qp join qp.teacher t join qp.department m join qp.qemType qt join qp.review rv
where qp.collegeStatus=1  and qp.bn=:bn
''',[bn:bn]
		
		return results
	}
	def getProject(long form_id){
		def results=QemProject.executeQuery '''
select new map(
	qp.id as id,
	tc.id as userId,
	tc.name as userName,
	tc.sex as sex,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.specailEmail as specailEmail,
	qp.discipline	as discipline,
	qp.direction	as direction,
	dp.name			as departmentName,
	qp.major		as majorName,
	qt.name			as qemTypeName,
	qp.projectName	as projectName,
	qp.expectedGain as expectedGain,
	qp.projectLevel	as projectLevel,
	qp.commitDate	as commitDate,
	qp.isSubmit		as commit,
	qp.collegeStatus	as collegeStatus,
	qp.specialEdit	as specialEdit,
	qp.position		as position,
	qp.phoneNum		as phoneNum,
	rv.status		as reviewStatus
)
from QemProject qp join qp.qemType qt join qp.department dp join qp.teacher tc join qp.review rv
where qp.id=:id 
''',[id:form_id]
		if(results)return results[0]
	}
	def getTask(Long projectId){
		def results=QemTask.executeQuery '''
select new map(
		qp.sn	as sn,
		qp.fundingProvince as fundingProvince,
		qp.fundingUniversity as fundingUniversity,
		qp.fundingCollege as fundingCollege
)
from QemTask qp 
where qp.projectId=:projectId
		''',[projectId:projectId]
		if(results)return results[0]
	}
	def getBns(){
		def results=Notice.executeQuery '''
select distinct n.bn
from Notice n where workType='REQ' order by n.bn desc
		'''
		return results
	}
	def exportForAttach(String bn){
		def results = QemProject.executeQuery '''
select new map(
	qp.projectName as projectName,
	qp.teacher.id	as teacherId,
	qp.id as projectId,
	0 as taskId
)
from QemProject qp
where qp.collegeStatus=1 and qp.bn=:bn
''', [bn:bn]
	}
	
}
