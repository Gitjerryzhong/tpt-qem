package cn.edu.bnuz.qem

import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.tms.security.SecurityService;
class TaskService {
	SecurityService securityService
//    def taskList(){
//		def results=QemProject.executeQuery '''
//select new map(
//	qp.id 			as projectId,
//	dp.name			as departmentName,
//	qt.name			as qemTypeName,
//	qp.projectName	as projectName,
//	qp.projectLevel	as projectLevel,
//	qp.commitDate	as commitDate,
//	ts.id			as id,
//	ts.status		as status
//)
//from QemProject qp join qp.qemType qt join qp.department dp join qp.qemTask ts
//where qp.teacher.id=:userId 
//''',[userId:securityService.userId]
//			return results			
//	}
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
}
