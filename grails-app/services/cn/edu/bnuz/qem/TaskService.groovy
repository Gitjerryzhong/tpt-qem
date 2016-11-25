package cn.edu.bnuz.qem

import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.qem.update.*
class TaskService {
	SecurityService securityService
	def getStage(long taskId,Integer currentStage){
		def results=QemTask.executeQuery '''
select st
from QemTask qt join qt.stage st 
where qt.id=:taskId and st.currentStage =:currentStage
''',[taskId:taskId,currentStage:currentStage]
		if(results)
			return results
		else return null
	}
	def getLastCheckNotice(){
		def results=Notice.executeQuery '''
select nt
from Notice nt
where nt.workType='CHE' order by id desc
''',[max:1]
		return results[0]
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
where  t.id=:userid 
''',[userid:securityService.userId]			
				return results
	}
	def updateList(){
		def results = UpdateTask.executeQuery '''
select new map(
	ut.id as id,
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
from UpdateTask ut,QemTask qp join qp.qemType qt
where  ut.taskId=qp.id and ut.userId=:userid 
''',[userid:securityService.userId]			
						return results
	}
}
