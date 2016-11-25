package cn.edu.bnuz.qem

import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.update.UpdateTask
import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.tms.security.SecurityService;

class CollegeService {
	SecurityService securityService
	def requestList(int offset,int max){
		def results = QemProject.executeQuery '''
select new map(
    t.name	as userName,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qp.phoneNum as phoneNum,
	qp.major as major,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.commitDate as commitDate
)
from QemProject qp join qp.teacher t join qp.qemType qt
where qp.collegeStatus=0 and qp.bn=:bn and qp.department.id=:id and qp.isSubmit=true
''', [id: securityService.departmentId,bn:getCurrentBn()], [max: max,offset:offset]
		
		return results
	}
	
	def requestList(int offset,int max,int status){
		def results = QemProject.executeQuery '''
select new map(
    t.name	as userName,
	qp.id as id,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qp.phoneNum as phoneNum,
	qp.major as major,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.commitDate as commitDate,
	rv.status as status
)
from QemProject qp join qp.teacher t join qp.qemType qt join qp.review rv
where qp.collegeStatus=:status and qp.department.id=:id and qp.isSubmit=true
''', [status:status,id: securityService.departmentId], [max: max,offset:offset]
		
		return results
	}
	def requestList(int offset,int max,int status,String bn){
		def results = QemProject.executeQuery '''
select new map(
    t.name	as userName,
	qp.id as id,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qp.phoneNum as phoneNum,
	qp.major as major,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.commitDate as commitDate,
	rv.status as status
)
from QemProject qp join qp.teacher t join qp.qemType qt join qp.review rv
where qp.collegeStatus=:status and qp.department.id=:id and qp.isSubmit=true and qp.bn=:bn
''', [status:status,id: securityService.departmentId,bn:bn], [max: max,offset:offset]
		
		return results
	}
	def requestListU(int offset,int max,int status,String bn){
		def results = QemProject.executeQuery '''
select new map(
    t.name	as userName,
	qp.id as id,
	qp.projectName as projectName,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	qp.position as position,
	qp.phoneNum as phoneNum,
	qp.major as major,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.commitDate as commitDate,
	rv.status as status
)
from QemProject qp join qp.teacher t join qp.qemType qt join qp.review rv
where qp.department.id=:id and qp.isSubmit=true and qp.bn=:bn and rv.status=:status
''', [status:status,id: securityService.departmentId,bn:bn], [max: max,offset:offset]
		
		return results
	}
	Notice getCurrentNotice(){
		Notice.last()
	}
	def requestCounts(String bn){
				def results = QemProject.executeQuery '''
select new map(
		SUM(CASE WHEN qp.collegeStatus=0 THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN qp.collegeStatus=1 THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN qp.collegeStatus=2 THEN 1 ELSE 0 END) as t2,
		SUM(CASE WHEN qp.collegeStatus=3 THEN 1 ELSE 0 END) as t7,
		SUM(CASE WHEN qp.collegeStatus=6 THEN 1 ELSE 0 END) as t3,
		SUM(CASE WHEN rv.status=4 THEN 1 ELSE 0 END) as t4,
		SUM(CASE WHEN rv.status=5 THEN 1 ELSE 0 END) as t5,
		SUM(CASE WHEN rv.status=6 THEN 1 ELSE 0 END) as t6
)
from QemProject qp join qp.review rv
where  qp.department.id=:id and qp.isSubmit=true and qp.bn=:bn
''', [id: securityService.departmentId,bn:bn]
		return results[0]
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
	qp.bn			as bn,
	qp.otherLinks	as otherLinks,
	rv.status		as reviewStatus
)
from QemProject qp join qp.qemType qt join qp.department dp join qp.teacher tc join qp.review rv
where qp.id=:id 
''',[id:form_id]
		if(results)return results[0]
	}
	def checkingNext(long id){
		def results = QemProject.executeQuery '''
select r.id from QemProject r where r.isSubmit=true and r.collegeStatus=0 and r.id> :id and r.department.id=:departmentid
''', [id: id,departmentid:securityService.departmentId], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def checkingPrev(long id){
		def results = QemProject.executeQuery '''
select r.id from QemProject r where r.isSubmit=true and r.collegeStatus=0 and r.id< :id and r.department.id=:departmentid order by id desc
''', [id: id,departmentid:securityService.departmentId], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def getAudits(long form_id){
		QemProject.get(form_id)?.audits
	}
	def export() {
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
	qp.collegeAudit	as collegeAudit,
	qp.specialEdit	as specialEdit,
	qp.position		as position,
	qp.phoneNum		as phoneNum,
	rv.status		as reviewStatus
)
from QemProject qp join qp.qemType qt join qp.department dp join qp.teacher tc join qp.review rv
where qp.department.id=:id and qp.isSubmit=true and qp.bn=:bn
''',[id:securityService.departmentId,bn:getCurrentBn()]
		return [
			items: results
		]
	}
	def export(String bn) {
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
	qp.collegeAudit	as collegeAudit,
	qp.specialEdit	as specialEdit,
	qp.position		as position,
	qp.phoneNum		as phoneNum,
	rv.status		as reviewStatus
)
from QemProject qp join qp.qemType qt join qp.department dp join qp.teacher tc join qp.review rv
where qp.department.id=:id and qp.isSubmit=true and qp.bn=:bn
''',[id:securityService.departmentId,bn:bn]
		return [
			items: results
		]
	}
	def getCurrentBn(){
		def notice=Notice.last()
		return notice?.bn?:""
	}
	def taskList(){
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	m.shortName	as departmentName,
	qt.name as type,
	qp.sn	as sn,
	qp.fundingProvince+qp.fundingUniversity+qp.fundingCollege as budget,
	SUBSTRING(qp.beginYear,1,7)	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.endDate		as endDate,
	qp.runStatus		as runStatus,
	qp.hasMid		as hasMid,
	''				as memo,
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t join qp.qemType qt join qp.department m
where  qp.status=10 and qp.department.id=:id 
''',[id:securityService.departmentId]			
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
		SUM(CASE WHEN qp.status >0 THEN 1 ELSE 0 END) as t5,
		SUM(CASE WHEN qp.runStatus=3 THEN 1 ELSE 0 END) as t6,
		SUM(CASE WHEN qp.runStatus=4 THEN 1 ELSE 0 END) as t7
)
from QemTask qp
where  qp.department.id=:id 
''',[id:securityService.departmentId]	
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def checkingNext_T(long id ,int runStatus){
		def results = QemTask.executeQuery '''
select r.id from QemTask r where r.runStatus=:runStatus and r.id> :id and r.department.id=:departmentid
''', [runStatus:runStatus,id: id,departmentid:securityService.departmentId], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def checkingPrev_T(long id,int runStatus){
		def results = QemTask.executeQuery '''
select r.id from QemTask r where r.runStatus=:runStatus  and r.id< :id and r.department.id=:departmentid order by id desc
''', [runStatus:runStatus,id: id,departmentid:securityService.departmentId], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def annualList(){
//		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.sn	as sn,
	SUBSTRING(qp.beginYear,1,4)	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.runStatus		as runStatus,	
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t join qp.qemType qt
where qp.department.id=:deptId and qp.beginYear<:currentYear and qp.expectedMid > :currentYear
''',[deptId:securityService.departmentId,currentYear:getCurrentYear()]			
				return results
	}
	def midList(){
//		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.sn	as sn,
	qp.fundingProvince+qp.fundingUniversity+qp.fundingCollege as budget,
	SUBSTRING(qp.beginYear,1,4)	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.runStatus		as runStatus,	
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t join qp.qemType qt
where qp.department.id=:deptId and qp.expectedMid = :currentYear 
''',[deptId:securityService.departmentId,currentYear:getCurrentYear()]			
				return results
	}
	def endList(){
//		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
	qp.id as id,
    t.name	as userName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.sn	as sn,
	qp.fundingProvince+qp.fundingUniversity+qp.fundingCollege as budget,
	SUBSTRING(qp.beginYear,1,4)	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	qp.projectContent	as projectContent,
	qp.members		as memberstr,
	qp.status		as status,
	qp.runStatus		as runStatus,	
	qp.expectedGain	as expectedGain	
)
from QemTask qp join qp.teacher t  join qp.qemType qt
where  qp.department.id=:deptId and qp.expectedEnd = :currentYear
''',[deptId:securityService.departmentId,currentYear:getCurrentYear()]			
				return results
	}
	def stageCounts(){
//		def currentYear=new Date().format("yyyy")
		def results = QemTask.executeQuery '''
select new map(
		SUM(CASE WHEN qp.beginYear<:currentYear and qp.expectedMid > :currentYear THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN qp.expectedMid = :currentYear THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN qp.expectedEnd = :currentYear THEN 1 ELSE 0 END) as t2
)
from QemTask qp
where  qp.department.id=:id 
''',[currentYear:getCurrentYear(),id:securityService.departmentId]	
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	
	private String getCurrentYear(){
		return new Date().format("yyyy")
	}
	//项目变更统计数
	def taskUpdateCounts(){
		def result1 = QemTask.executeQuery '''
select count(*)
from QemTask qp
where  qp.department.id=:id and qp.status=10
''',[id:securityService.departmentId]
		def result2 = UpdateTask.executeQuery '''
select count(*)
from UpdateTask qp
where  qp.departmentId=:id 
''',[id:securityService.departmentId]
		return [t1:result1[0],t2:result2[0]]
	}
	//本部门项目变更单
	def taskUpdateList(){
		def result = UpdateTask.executeQuery '''
select  new map(
	qp.id as id,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	t.name	as teacherName,
	qp.sn	as sn,
	qp.beginYear	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	qp.projectContent	as projectContent,
	qp.commitDate		as commitDate,
	qp.status		as status,
	qp.updateType	as updateType
)
from UpdateTask qp, QemType qt, Teacher t
where  qp.departmentId=:id and qp.qemTypeId=qt.id and qp.teacherId=t.id
''',[id:securityService.departmentId]
		return result
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
where qp.collegeStatus=1 and qp.department.id=:id and qp.bn=:bn
''', [id: securityService.departmentId,bn:bn]
	}
	def projectes(String bn){
		def results = QemProject.executeQuery '''
select new map(
	qp.projectName as projectName,
	qp.teacher.id	as teacherId,
	qp.id as projectId,
	0 as taskId
)
from QemProject qp
where qp.isSubmit=true and qp.department.id=:id and qp.bn=:bn
''', [id: securityService.departmentId,bn:bn]
	}
	def exportForAttach(){
		def results = QemTask.executeQuery '''
select new map(
	qp.projectName as projectName,
	qp.teacher.id	as teacherId,
	qp.projectId as projectId,
	qp.id as taskId
)
from QemTask qp where qp.department.id=:id
''', [id: securityService.departmentId]
	}
	def updateList(){
		def results = UpdateTask.executeQuery '''
select new map(
	ut.id as id,
	ut.userId as userId,
	ut.userName as userName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.sn	as sn,
	SUBSTRING(qp.beginYear,1,7)	as beginYear,
	ut.updateTypes as updateTypes,
	ut.commitDate as commitDate,
	ut.flow as flow,
	ut.auditStatus as auditStatus
)
from UpdateTask ut,QemTask qp join qp.qemType qt join qp.department d
where  ut.updateTypes<>'1;' and ut.taskId=qp.id and d.id=:departmentId 
''',[departmentId:securityService.departmentId]			
						return results
	}
	def myUpdateList(){
		def results = UpdateTask.executeQuery '''
select new map(
	ut.id as id,
	ut.userId as userId,
	ut.userName as userName,
	qp.projectName as projectName,
	qp.projectLevel as projectLevel,
	qt.name as type,
	qp.sn	as sn,
	t.name  as teacherName,
	SUBSTRING(qp.beginYear,1,7)	as beginYear,
	qp.expectedEnd	as expectedEnd,
	ut.updateTypes as updateTypes,
	ut.commitDate as commitDate,
	ut.flow as flow,
	ut.auditStatus as auditStatus
)
from UpdateTask ut,QemTask qp join qp.qemType qt join qp.department d join qp.teacher t 
where  ut.updateTypes='1;' and ut.taskId=qp.id and d.id=:departmentId 
''',[departmentId:securityService.departmentId]			
						return results
	}
}
