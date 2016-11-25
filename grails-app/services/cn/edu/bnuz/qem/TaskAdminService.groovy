package cn.edu.bnuz.qem

import java.util.List;

import cn.edu.bnuz.qem.organization.Experts
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.QemStage
import cn.edu.bnuz.qem.project.QemTaskAudit
import cn.edu.bnuz.qem.review.Review
import cn.edu.bnuz.tms.organization.Teacher;
import cn.edu.bnuz.tms.security.SecurityService

class TaskAdminService {
	SecurityService securityService
	def taskList(){
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	m.shortName	as shortName,
	m.name	as departmentName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qt.name as type,
	pt.parentTypeName as parentType,
	qp.sn	as sn,
	qp.fundingProvince+qp.fundingUniversity+qp.fundingCollege as budget,
	qp.fundingProvince as fundingProvince,
	qp.fundingUniversity as fundingUniversity,
	qp.fundingCollege as fundingCollege,
	SUBSTRING(qp.beginYear,1,4)	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	date_format(qp.endDate,'%Y-%m')		as endDate,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.runStatus		as runStatus,	
	qp.memo			as memo,
	qp.hasMid		as hasMid,
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t join qp.department m join qp.qemType qt join qt.parentType pt
'''			
				return results
	}
	def contractList(){
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	m.shortName	as shortName,
	m.name	as departmentName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qt.name as type,
	pt.parentTypeName as parentType,
	qp.sn	as sn,
	qp.fundingProvince+qp.fundingUniversity+qp.fundingCollege as budget,
	qp.fundingProvince as fundingProvince,
	qp.fundingUniversity as fundingUniversity,
	qp.fundingCollege as fundingCollege,
	SUBSTRING(qp.beginYear,1,4)	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	date_format(qp.endDate,'%Y-%m')		as endDate,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.runStatus		as runStatus,	
	qp.memo			as memo,
	qp.hasMid		as hasMid,
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t left join qp.department m left join qp.qemType qt left join qt.parentType pt
'''			
				return results
	}
	def taskCounts(){
		def results = QemTask.executeQuery '''
select new map(
		SUM(CASE WHEN qp.runStatus=0 THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN qp.runStatus=1 THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN qp.runStatus=201 THEN 1 ELSE 0 END) as t2,
		SUM(CASE WHEN qp.runStatus=202 THEN 1 ELSE 0 END) as t3,
		SUM(CASE WHEN qp.runStatus=203 THEN 1 ELSE 0 END) as t4,
		SUM(CASE WHEN qp.runStatus=2 THEN 1 ELSE 0 END) as t5,
		SUM(CASE WHEN qp.runStatus=3 THEN 1 ELSE 0 END) as t6,
		SUM(CASE WHEN qp.runStatus=4 THEN 1 ELSE 0 END) as t7
)
from QemTask qp
'''
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def confirmTasks(List<Long> ids,int type){
		def confirmStatus=2
		def fromStatus=1
		def status =10
		switch(type){
			case 1: confirmStatus=11
					fromStatus=10
					break
			case 2: confirmStatus=21
					fromStatus=20
					break
			case 3: confirmStatus=31
					fromStatus=30
					break
		}
		QemTask.executeUpdate '''
update QemTask qt  set qt.runStatus=:confirmStatus,qt.status=:status
where qt.runStatus=:fromStatus and qt.id in(:ids)
''',[confirmStatus:confirmStatus,status:status,fromStatus:fromStatus,ids:ids]
	}
	def annualList(){
		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	m.name	as departmentName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.sn	as sn,
	qp.fundingProvince+qp.fundingUniversity+qp.fundingCollege as budget,
	qp.beginYear	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.runStatus		as runStatus,	
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t join qp.department m join qp.qemType qt
where qp.beginYear<:currentYear and qp.expectedMid > :currentYear
''',[currentYear:currentYear]			
				return results
	}
	def annualCounts(){
		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
		SUM(CASE WHEN qp.runStatus<10 THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN qp.runStatus=10 THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN qp.runStatus>10 THEN 1 ELSE 0 END) as t2
)
from QemTask qp
where qp.beginYear<:currentYear and qp.expectedMid > :currentYear
''',[currentYear:currentYear]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def midList(){
		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	m.name	as departmentName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.sn	as sn,
	qp.fundingProvince+qp.fundingUniversity+qp.fundingCollege as budget,
	qp.beginYear	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.runStatus		as runStatus,	
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t join qp.department m join qp.qemType qt
where qp.expectedMid = :currentYear
''',[currentYear:currentYear]			
				return results
	}
	def midCounts(){
		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
		SUM(CASE WHEN qp.runStatus<20 THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN qp.runStatus=20 THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN qp.runStatus>20 THEN 1 ELSE 0 END) as t2
)
from QemTask qp
where  qp.expectedMid = :currentYear
''',[currentYear:currentYear]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def endList(){
		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	m.name	as departmentName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.sn	as sn,
	qp.fundingProvince+qp.fundingUniversity+qp.fundingCollege as budget,
	qp.beginYear	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.runStatus		as runStatus,	
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t join qp.department m join qp.qemType qt
where  qp.expectedEnd = :currentYear
''',[currentYear:currentYear]			
				return results
	}
	def endCounts(){
		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
		SUM(CASE WHEN qp.runStatus<30 THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN qp.runStatus=30 THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN qp.runStatus>30 THEN 1 ELSE 0 END) as t2
)
from QemTask qp
where qp.expectedEnd = :currentYear
''',[currentYear:currentYear]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def confirmedList(){
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	m.shortName	as departmentName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.sn	as sn,
	s.status	as status,
	rv.experts	as experts,
	rv.id		as reviewId
)
from QemTask qp join qp.teacher t join qp.department m join qp.qemType qt join qp.stage s join s.review rv
where  s.status=2
'''			
				return results
	}
	def confirmCounts(){
		def results = QemTask.executeQuery '''
select new map(
		SUM(CASE WHEN rv.experts is null THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN rv.experts is not null THEN 1 ELSE 0 END) as t1
)
from QemStage st join st.review rv
where st.status=2
'''
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def reviewForExpert(List<Long> ids){
		def results = QemTask.executeQuery '''
select new map(
	rv.id as id,
	m.name	as departmentName,
	s.status	as status,
	rv.experts	as experts
)
from QemTask qp join qp.department m join qp.stage s join s.review rv
where  qp.id in(:ids) and s.status=2
''',[ids:ids]
	}
	def getExperts(String department){
		def results =Experts.executeQuery '''
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
from Experts ex join ex.teacher tc join tc.department dp
where  dp.name<>:department
''',[department:department]
		
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
	dp.name as departmentName
)
from Experts ex join ex.teacher tc join tc.department dp
'''		
		return results
	}
	def getCounts(String expert){
		def results = QemStage.executeQuery '''
select count(*)
from QemStage qs join qs.review rv
where qs.status=2 and rv.experts like :expert
''',[expert:'%'+expert+'%']
	if(results){
		return results[0]
	}else return null
		
	}
	def createTask(TaskFormCommand cmmd){
		def teacher=Teacher.get(cmmd.teacherId)
		if(teacher){
			QemTask task=new QemTask([
				teacher:teacher,
				department:cmmd.departmentId,
				qemType:cmmd.qemTypeId,
				projectLevel:cmmd.projectLevel,
				projectName:cmmd.projectName,
				sn:cmmd.sn,
				beginYear:cmmd.beginYear,
				expectedMid: cmmd.expectedMid,
				expectedEnd: cmmd.expectedEnd,
				fundingProvince:cmmd.fundingProvince?:0,
				fundingUniversity:cmmd.fundingUniversity?:0,
				fundingCollege:cmmd.fundingCollege?:0,
				currentDegree:"/",
				currentTitle:"/-",
				phoneNum:"/",
				specailEmail:"/",
				status:QemTask.S_NEW,
				runStatus:QemTask.S_NEW
				])
			if(!task.save(flush:true)){
				task.errors.each{
					println it
				}
				return false
			}
			//创建日志
			QemTaskAudit qemAudit=new QemTaskAudit([
				userId:securityService.userId,
				userName:securityService.userName,
				action:QemTaskAudit.ACTION_NEW_TASK,
				date:new Date(),
				objectId:task?.id,
				src:task?.class.name])
			if(!qemAudit.save(flush:true)){
				qemAudit.errors.each{
					println it
				}
				return false
			}
		}else{
			return false
		}
		return true
	}
	def reviewedList(){
		def results = QemTask.executeQuery '''
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
	s.currentStage as currentStage,
	rv.experts as experts,
	SUM(CASE ev.result WHEN 0 THEN 1 ELSE 0 END) as pass,
	SUM(CASE ev.result WHEN 1 THEN 1 ELSE 0 END) as ng,
	SUM(CASE ev.result WHEN 2 THEN 1 ELSE 0 END) as waiver,
	AVG(ev.totalScore) as avgScore,
	GROUP_CONCAT(ev.view) as view
)
from QemTask qp join qp.teacher t join qp.department m join qp.qemType qt join qp.stage s join s.review rv left join rv.expertView ev
where s.status=30 
group by qp
'''
		
		return results
	}
	def checkingNext(long id ,int status){
		def results = QemTask.executeQuery '''
select r.id from QemTask r where r.status=:status and r.id> :id
''', [status:status,id: id], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def checkingPrev(long id ,int status){
		def results = QemTask.executeQuery '''
select r.id from QemTask r where r.status=:status  and r.id< :id order by id desc
''', [status:status,id: id], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def checkingStageNext(long id ,int runStatus){
		def results = QemTask.executeQuery '''
select r.id from QemTask r where r.runStatus=:runStatus and r.id> :id
''', [runStatus:runStatus,id: id], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def checkingStagePrev(long id ,int runStatus){
		def results = QemTask.executeQuery '''
select r.id from QemTask r where r.runStatus=:runStatus  and r.id< :id order by id desc
''', [runStatus:runStatus,id: id], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def exportForAttach(){
		def results = QemTask.executeQuery '''
select new map(
	qp.projectName as projectName,
	qp.teacher.id	as teacherId,
	qp.projectId as projectId,
	qp.id as taskId
)
from QemTask qp
'''
	}
}
