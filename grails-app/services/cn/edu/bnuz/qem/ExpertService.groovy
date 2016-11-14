package cn.edu.bnuz.qem

import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.QemStage
import cn.edu.bnuz.qem.organization.Experts
import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.tms.security.SecurityService;
class ExpertService {
	SecurityService securityService
	def requestList(){
		//and qp.id not in (:ids)
		def hasReview=getReviewedProject()
		if(!hasReview) hasReview=[new Long(0)]
		def results = QemProject.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.projectLevel as projectLevel,
	qt.name as type,
	m.name as departmentName,
	rv.experts as experts,
	qp.commitDate as commitDate,
	qp.groupId as groupId,
	(CASE WHEN t.id in(select tt.id from QemTask qt join qt.teacher tt) THEN 1 ELSE 0 END) as otherElse
)
from QemProject qp join qp.teacher t join qp.qemType qt join qp.department m join qp.review rv
where qp.collegeStatus=1 and  rv.experts like :userId and qp.id not in(:ids)
''', [userId:"%;"+securityService.userId+"%",ids:hasReview]
		
		return results
	}
	def reviewedProjectList(){
		
		def results = QemProject.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.projectLevel as projectLevel,
	qt.name as type,
	m.name as departmentName,
	ev.result as result,
	qp.commitDate as commitDate,
	qp.groupId as groupId
)
from QemProject qp join qp.teacher t join qp.qemType qt join qp.department m join qp.review rv join rv.expertView ev
where qp.collegeStatus=1 and  ev.expert.teacher.id=:me and ev.commit=true
''', [me:securityService.userId]
		
		return results
	}
	def getCounts(){
		def hasReview=getReviewedProject()
		if(!hasReview) hasReview=[new Long(0)]
		def results = QemProject.executeQuery '''
select count(*)
from QemProject qp join qp.review rv
where qp.collegeStatus=1 and rv.experts like :expert and qp.id not in(:ids)
''',[expert:'%'+securityService.userId+'%',ids:hasReview]
	def total=["${results[0]}","${(hasReview-[0]).size()}"]
	return total
//	if(results){
//		return results[0]
//	}else return null
		
	}
	def getExpert(){
		def results = Experts.executeQuery '''
select exp
from Experts exp join exp.teacher tc
where tc.id=:me
''',[me:securityService.userId]
	}
	def getExpertView(long id){
		def results = QemProject.executeQuery '''
select ev
from QemProject qp join qp.review rv join rv.expertView ev
where qp.id=:id and ev.expert.teacher.id=:me
''',[id:id,me:securityService.userId]
	if(results) return results[0]
	}
	def getExpertViewByStage(long id){
		def results = QemStage.executeQuery '''
select ev
from QemStage qp join qp.review rv join rv.expertView ev
where qp.id=:id and ev.expert.teacher.id=:me
''',[id:id,me:securityService.userId]
	if(results) return results[0]
	}
	
	def getReviewedProject(){
		def results = QemProject.executeQuery '''
select qp.id
from QemProject qp join qp.review rv join rv.expertView ev
where ev.expert.teacher.id=:me and ev.commit=true
''',[me:securityService.userId]
	}
	def getCurrentBn(){
		def notice=Notice.last()
		return notice.bn?:" "
	}
	def taskList(){
		//and qp.id not in (:ids)
		def hasReview=getReviewedTask()
		if(!hasReview) hasReview=[new Long(0)]
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.projectLevel as projectLevel,
	qt.name as type,
	st.id as stageId,
	m.name as departmentName,
	rv.experts as experts
)
from QemTask qp join qp.teacher t join qp.qemType qt join qp.department m join qp.stage st join st.review rv
where  rv.experts like :userId and st.id not in(:ids)
''', [userId:"%;"+securityService.userId+"%",ids:hasReview]
		
		return results
	}
	def getReviewedTask(){
		def results = QemStage.executeQuery '''
select qp.id
from QemStage  qp join qp.review rv join rv.expertView ev
where ev.expert.teacher.id=:me and ev.commit=true
''',[me:securityService.userId]
	}
	def reviewedTaskList(){
		
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.projectLevel as projectLevel,
	qt.name as type,
	st.id as stageId,
	m.name as departmentName,
	ev.result as result
)
from QemTask qp join qp.teacher t join qp.qemType qt join qp.department m join qp.stage st join st.review rv join rv.expertView ev
where  ev.expert.teacher.id=:me and ev.commit=true
''', [me:securityService.userId]
		
		return results
	}
	def getCountsTask(){
		def result = QemTask.executeQuery '''
select 	new map(
		SUM(CASE WHEN  st.currentStage<3 THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN  st.currentStage=3 THEN 1 ELSE 0 END) as t2
) 
from QemTask qp join qp.stage st join st.review rv join rv.expertView ev 
where ev.expert.teacher.id=:me and ev.commit=true
''',[me:securityService.userId]
		def result1 = QemTask.executeQuery '''
select new map(
		SUM(CASE WHEN  st.currentStage<3 THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN  st.currentStage=3 THEN 1 ELSE 0 END) as t2
)
from QemTask qp join qp.stage st join st.review rv 
where rv.experts like :expert
''',[expert:"%;"+securityService.userId+"%"]
	if(result1[0].t1==null)return [0,0,0,0]
	else 
	return ["${result1[0]?.t1-result[0]?.t1}","${result[0]?.t1}","${result1[0]?.t2-result[0]?.t2}","${result[0]?.t2}"]
	}
	def relatedTaskList(long projectid){
		println QemProject.get(projectid).teacher.id
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	m.shortName	as departmentName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qt.name as type,
	qp.sn	as sn,
	SUBSTRING(qp.beginYear,1,4)	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	date_format(qp.endDate,'%Y-%m')		as endDate,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.runStatus		as runStatus,	
	qp.hasMid		as hasMid,
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t join qp.department m join qp.qemType qt
where t.id=:teacherId
''',[teacherId:QemProject.get(projectid).teacher.id]
				return results
	}
}
