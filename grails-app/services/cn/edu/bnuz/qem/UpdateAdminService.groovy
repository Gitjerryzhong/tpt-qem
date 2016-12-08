package cn.edu.bnuz.qem

import grails.transaction.Transactional
import cn.edu.bnuz.qem.project.QemTask;
import cn.edu.bnuz.qem.update.UpdateTask
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tms.organization.Teacher;

@Transactional
class UpdateAdminService {
	SecurityService securityService
	def messageSource
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
	qp.expectedEnd as expectedEnd,
	ut.updateTypes as updateTypes,
	ut.commitDate as commitDate,
	ut.flow as flow,
	ut.auditStatus as auditStatus,
	d.name as departmentName
)
from UpdateTask ut,QemTask qp join qp.qemType qt join qp.department d
where ut.taskId=qp.id  
'''			
						return results
	}
	/**
	 * 同意变更
	 */
	def doUpdate(UpdateTask updateTask,String base){
		def oldData=[:]
		def task = QemTask.get(updateTask.taskId)
		if (!task) return false
		def updateTypes=updateTask.updateTypes.split(";")
		updateTypes.each {item->
			def itemValue=item.toInteger()
//			println itemValue
			switch(itemValue){
			case 1:oldData+=[teacherId:task.teacher.name,
					currentTitle:task.currentTitle,
					currentDegree:task.currentDegree,
					specailEmail:task.specailEmail,
					phoneNum:task.phoneNum,
					position:task.position]
				   task.setTeacher(Teacher.get(updateTask.teacherId))
				   task.setCurrentTitle(updateTask.currentTitle)
				   task.setCurrentDegree(updateTask.currentDegree)
				   task.setSpecailEmail(updateTask.specailEmail)
				   task.setPhoneNum(updateTask.phoneNum)
				   task.setPosition(updateTask.position)
					break;
			case 2:oldData+=[expectedMid:task.expectedMid,
					expectedEnd:task.expectedEnd]
					task.setExpectedMid(updateTask.expectedMid)
					task.setExpectedEnd(updateTask.expectedEnd)
					if(!task.delay)task.setDelay(1)
					else task.setDelay(task.delay+1)
					break;
			case 3:oldData+=[projectName:task.projectName]
					task.setProjectName(updateTask.projectName)
					break;
			case 4:oldData+=[projectContent:task.projectContent]
					task.setProjectContent(updateTask.projectContent)
					break;
			case 5:oldData+=[status:task.status,
					runStatus:task.runStatus]
					task.setStatus(QemTask.STATUS_EXCEPTION_NG)
					task.setRunStatus(QemTask.S_NG)
					break;
			case 6:oldData+=[expectedGain:task.expectedGain]
					task.setExpectedGain(updateTask.expectedGain)
					break;
			case 7:oldData+=[members:task.members]
					task.setMembers(updateTask.members)
					break;
			case 8:task.setMemo("${task.memo};${updateTask.others}")
					break;
			}
		}
		def src= "${base}/update/${task.teacher.id}/${updateTask.taskId}/${updateTask.commitDate.format('yyyyMMdd')}"
		if(updateTask.updateTypes=='1;') {
			def departmentId=Teacher.get(updateTask.userId)?.department.id
			src = "${base}/update/${departmentId}/${updateTask.taskId}/${updateTask.commitDate.format('yyyyMMdd')}"
		}
		def dest= "${base}/task/${task.teacher.id}/${updateTask.taskId}"
		
		task.save(flush:true)
		updateTask.setAuditStatus(UpdateTask.AU_PASS)
		updateTask.save(flush:true)
		moveAttch(src,dest)
		return oldData
	}
	/**
	 * 将附件复制到目标文件夹，在文件尾加上时间戳+变更字样
	 * @param src
	 * @param dest
	 * @return
	 */
	def moveAttch(src,dest){
		def fileSrc=new File(src)
		def addon=messageSource.getMessage("qem.updateAttchText", ["${new Date().format('yyyyMMdd')}"].toArray(), Locale.CHINA)
		if(fileSrc.isDirectory()){
			for(File file: fileSrc.listFiles()){
				if(file.isDirectory()){ //如果是子目录
					def fileDest = new File("${dest}/${file.name}")
					if(!fileDest.exists()) fileDest.mkdirs()
					for(File f:file.listFiles()){
						if(f.name.indexOf("del_")==-1) {
							def point=f.name.lastIndexOf(".")
							def type=f.name.substring(point)
							def filename=f.name.substring(0,point)
							fileDest = new File("${dest}/${file.name}/${filename}${addon}${type}")
							fileDest.withPrintWriter {printWriter ->
								f.eachLine {line ->
									printWriter.println(line)
								}
							}
						}
					}
				}else if(file.name.indexOf("del_")==-1){
					def  fileDest = new File("${dest}/${file.name}")
					fileDest.withPrintWriter {printWriter ->
					file.eachLine {line ->
						printWriter.println(line)
					}
				}
				}
			}
		}
	}
	/**
	 * 查询变更日志
	 */
	def findUpdateAdminAudit(def taskId){
		def results = UpdateTask.executeQuery '''
select new map(
	au.date as date,
	au.content as content
)
from UpdateTask ut,QemTask qp,QemTaskAudit au
where ut.taskId=qp.id  and ut.taskId = :id and au.objectId=ut.id and au.src=:objectName and au.content like '%{%}%'
'''	,[id:taskId,objectName:UpdateTask.class.name]
		return results
	}
}
