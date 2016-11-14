package cn.edu.bnuz.qem

import grails.transaction.Transactional
import cn.edu.bnuz.qem.project.QemAudit
import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.qem.project.QemType
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.qem.review.Review
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.teaching.Major
import cn.edu.bnuz.tms.security.SecurityService;

class ProjectService {
	SecurityService securityService
    QemProject create(ProjectCommand pjc) {
		def notice=Notice.get(pjc.noticeId)
		def today =new Date()
		if (today.after(notice?.start) && today.before(notice?.end) && notice?.workType?.equals("REQ")){
		QemProject qp=new QemProject(
			teacher:Teacher.load(securityService.userId),
			currentTitle:pjc.currentTitle,
			currentDegree:pjc.currentDegree,
			specailEmail:pjc.specailEmail,
			discipline:pjc.discipline,
			direction:pjc.direction,
			department:Department.get(pjc.departmentId),
			major:pjc.majorId,
			qemType:QemType.get(pjc.qemTypeId),
			position:pjc.position,
			phoneNum:pjc.phoneNum,
			projectName:pjc.projectName,
			expectedGain:pjc.expectedGain,
			projectLevel:pjc.projectLevel,
			otherLinks:pjc.otherLinks,
			commitDate:new Date(),
			isSubmit:false,
			collegePass:false,
			collegeStatus:0,
			specialEdit:false,
			bn:notice.bn,
			notice:notice)
		qp.review=new Review(status:Review.STATUS_NEW).save()
		qp.save(flush:true)
		}
    }
	QemProject update(ProjectCommand pjc) {
		def qp= QemProject.get(pjc.id)
		if(qp)println qp.position
		if(Boolean.parseBoolean(pjc.commit) && !qp.allowAction(QemProject.ACTION_SUBMIT)) return null
		qp.setCurrentTitle(pjc.currentTitle)
		qp.setCurrentDegree(pjc.currentDegree)
		qp.setSpecailEmail(pjc.specailEmail)
		qp.setDiscipline(pjc.discipline)
		qp.setDirection(pjc.direction)
		qp.setDepartment(Department.get(pjc.departmentId))
		qp.setQemType(QemType.get(pjc.qemTypeId))
		qp.setProjectName(pjc.projectName)
		qp.setProjectLevel(Integer.parseInt(pjc.projectLevel))
		qp.setCollegeStatus(Integer.parseInt(pjc.collegeStatus?:"0"))
		qp.setExpectedGain(pjc.expectedGain)		
		qp.setIsSubmit(Boolean.parseBoolean(pjc.commit))
		qp.setPosition(pjc.position)
		qp.setPhoneNum(pjc.phoneNum)
		qp.setMajor(pjc.majorId)
		qp.setOtherLinks(pjc.otherLinks)
		qp.save(flush:true)
		return qp
	}
	QemProject projectForEdit(){
		return QemProject.findByTeacherAndIsSubmit(Teacher.load(securityService.userId),false)
	}
	QemProject projectForNotice(){
		def notice=Notice.last()
		return QemProject.findByTeacherAndNotice(Teacher.load(securityService.userId),notice)
	}
	def taskList(){
		def results=QemTask.executeQuery '''
select new map(
	dp.name			as departmentName,
	qt.name			as qemTypeName,
	qp.projectName	as projectName,
	qp.projectLevel	as projectLevel,
	qp.beginYear	as beginYear,
	qp.expectedMid	as expectedMid,
	qp.expectedEnd	as expectedEnd,
	qp.id			as id,
	qp.sn			as sn,
	qp.status		as status,
	qp.runStatus	as runStatus
)
from QemTask qp join qp.qemType qt join qp.department dp
where qp.teacher.id=:userId 
''',[userId:securityService.userId]
			return results			
	}
	def projects(){
		def results=QemProject.executeQuery '''
select new map(
	qp.id as id,
	tc.name as name,
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
where tc.id=:userId 
''',[userId:securityService.userId]
		return results
	}	
	def majorForList(){
		Department.executeQuery '''
SELECT new map(
		dp.id as departmentId,
		mj.id as id,
		mj.name as name) FROM Department dp join dp.majors mj where enabled = true
'''
	}
	def lastProject(){
		def result=QemProject.executeQuery '''
SELECT new map(
		qp.currentTitle	as currentTitle,
		qp.currentDegree	as currentDegree,
		qp.specailEmail		as specailEmail,
		qp.discipline		as discipline,
		qp.direction		as direction,
		qp.position			as position,
		qp.phoneNum			as phoneNum,
		qp.major			as majorId
)
FROM QemProject qp  join qp.teacher tc where tc.id=:userId order by qp.id desc 
''',[userId:securityService.userId],[max:1]
		return result[0]
	}
	def getAudits(long form_id){
		QemAudit.executeQuery '''
SELECT qa FROM QemAudit qa  where qa.form.id = :form_id
''',[form_id:form_id]
	}
	def getBns(){
		QemProject.executeQuery '''
SELECT distinct qp.bn FROM QemProject qp  
'''
	}
	def projectsPublic(String bn){
		def results=QemTask.executeQuery '''
select new map(	
	tc.name 		as name,
	qp.currentTitle as currentTitle,
	qp.currentDegree as currentDegree,
	dp.name			as departmentName,
	qt.name			as qemTypeName,
	qp.projectName	as projectName,
	qp.projectLevel	as projectLevel,
	qp.beginYear	as commitDate,
	qp.sn			as sn
)
from QemTask qp join qp.qemType qt join qp.teacher tc join qp.department dp ,QemProject qr
where qp.projectId=qr.id and qr.bn=:bn 
''',[bn:bn]
		return results
	}
	def remindCounts(){
				def results = QemTask.executeQuery '''
select new map(
		SUM(CASE WHEN qp.runStatus=0 or qp.runStatus=203 THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN qp.beginYear<:currentYear and qp.expectedMid >= :currentYear THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN qp.expectedEnd = :currentYear THEN 1 ELSE 0 END) as t2
)
from QemTask qp
where qp.teacher.id=:userId 
''',[currentYear:new Date().format("yyyy"),userId:securityService.userId]	
		def map=results[0]
		def results1 = QemProject.executeQuery '''
select count(*)
from QemProject qp join qp.review rv
where qp.teacher.id=:userId and (qp.collegeStatus=2 or rv.status=6)
''',[userId:securityService.userId]
		map['t3']=results1[0]
		return map
	}
	def ifNotEnding(){
		def result=QemTask.executeQuery '''
SELECT new map(
		SUM(CASE WHEN qp.projectLevel=1 THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN qp.projectLevel=2 THEN 1 ELSE 0 END) as t1
)
FROM QemTask qp  join qp.teacher tc where tc.id=:userId and qp.status in(0,10) 
''',[userId:securityService.userId]
	return result[0]
	}
}
