package cn.edu.bnuz.qem

import grails.transaction.Transactional
import cn.edu.bnuz.qem.update.UpdateTask
import cn.edu.bnuz.tms.security.SecurityService;

@Transactional
class UpdateAdminService {
	SecurityService securityService
    //项目变更单
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
	qp.commitDate 		as commitDate,
	qp.status		as status,
	qp.updateType	as updateType
)
from UpdateTask qp, QemType qt, Teacher t
where  qp.qemTypeId=qt.id and qp.teacherId=t.id
''',[:]
		return result
	}
	//项目变更统计数
	def taskUpdateCounts(){
		def result = UpdateTask.executeQuery '''
select new map(
		SUM(CASE WHEN qp.status=0 THEN 1 ELSE 0 END) as t0,
		SUM(CASE WHEN qp.status=1 THEN 1 ELSE 0 END) as t1,
		SUM(CASE WHEN qp.status=2 THEN 1 ELSE 0 END) as t2,
		SUM(CASE WHEN qp.status=3 THEN 1 ELSE 0 END) as t3,
		SUM(CASE WHEN qp.status=4 THEN 1 ELSE 0 END) as t4
)
from UpdateTask qp
''',[:]
		return result[0]
	}
	def checkingNext(long id ,int status){
		def results = UpdateTask.executeQuery '''
select r.id from UpdateTask r where r.status=:status and r.id> :id 
''', [status:status,id: id], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def checkingPrev(long id,int status){
		def results = UpdateTask.executeQuery '''
select r.id from UpdateTask r where r.status=:status  and r.id< :id order by id desc
''', [status:status,id: id], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
}
