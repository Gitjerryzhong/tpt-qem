package cn.edu.bnuz.tpt

import java.util.Date;

import groovy.sql.Sql
import grails.transaction.Transactional
import cn.edu.bnuz.tpt.TptStudent
import cn.edu.bnuz.tpt.Error
import cn.edu.bnuz.tpt.ErrorItem
import cn.edu.bnuz.tms.organization.*
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tpt.teaching.*
import cn.edu.bnuz.tpt.util.WriteTable;

@Transactional
class TptAdminService {
	SecurityService securityService
	TptCoProjectService tptCoProjectService
	def messageSource
	def dataSource_es
    def studentSave(UserForm userForm) {
		def error=new ArrayList<String>()	
		def users=userForm?.users?.split("\n") 			
			
		for(int i=0;i<users?.length;i++){
			def errorItem=add2List(users[i],i,userForm.projectId)
			if(errorItem) error.push(errorItem)
		}
//		往自助打印系统推送名单
		def result=tptCoProjectService.studentSave(userForm)
		return [error:error,results:result]

    }
	def getStudents(){
		def students = TptRequest.executeQuery '''
select t from TptStudent t, TptTeacherDept ttd 
where t.department.id=ttd.department.id and ttd.teacher.id=:id 
''', [id: securityService.userId]
		return students
	}
	def deleteUserList(){
		Student.executeUpdate '''
update Student s,TptStudent t, TptTeacherDept ttd  set s.enabled=false
where s.id=t.id and t.department.id=ttd.department.id and ttd.teacher.id=:id 
''', [id: securityService.userId]
		def deleteCount = TptRequest.executeUpdate '''
delete from TptStudent t, TptTeacherDept ttd 
where t.department.id=ttd.department.id and ttd.teacher.id=:id 
''', [id: securityService.userId]
		return deleteCount
	}
	def reloadUserlist(){
		def students = Student.executeQuery '''
select new map(
		s.id as id,
		s.name as name,
		s.sex as sex,
		m.name as majorName,
		adc.name as adminClassName,
		c.name	as projectName
) 
 from Student s join s.adminClass adc join s.major m, TptStudent t left join t.tptCoCountry c, TptTeacherDept ttd 
where s.id=t.id and t.department.id=ttd.department.id and ttd.teacher.id=:id 
''', [id: securityService.userId]
		return students
		
	}
	private add2List(String userStr,int i , long projectId){
		if(userStr==null) return
		String item
		def userInfo = userStr.split("\t")
		if(userInfo.length==2){
			UserForm user=new UserForm([
				id:	userInfo[0],
				name: userInfo[1],
				projectId: projectId
			])
			def std= TptStudent.get(userInfo[0])
			if(std){ //如果已经存在，则不再重复加入，并报错
//				item=new ErrorItem("tpt.duplicated",[i,user.id,user.name])
				item=messageSource.getMessage("tpt.duplicated", ["${i+1}","${user.id}","${user.name}"].toArray(), Locale.CHINA)
				return item
			}
			Student student=checkUser(user)
			
			/*分析查询结果，将结果分别保存在error对象和userList对象中*/
			if(student==null){
//				item=new ErrorItem("tpt.userNotFound",[i,user.id,user.name])
				item=messageSource.getMessage("tpt.userNotFound", ["${i+1}","${user.id}","${user.name}"].toArray(), Locale.CHINA)
			}else{
				/*如果学生信息正确：添加到userList中；允许该学生登录；保存到tptStudent中*/
				user.setPassword(student.password)
				student.setEnabled(true)
				student.save()
				TptStudent tptStudent=new TptStudent()
				tptStudent.id=user.id
				tptStudent.name=user.name
				tptStudent.tptCoCountry = TptCoCountry.get(projectId)
				tptStudent.operator = securityService.userId
				tptStudent.setDepartment(student.adminClass.department)
				tptStudent.save()
				
			}
		}
		return item
	}
	/**
	 * 从Student表中查询本部门的学生，如果存在该学号，返回结果
	 * @param userForm
	 * @return
	 */
	private Student checkUser(UserForm userForm){
		def results = Student.executeQuery '''
select s from Student s, TptTeacherDept ttd 
where s.id=:stdId and s.adminClass.department.id=ttd.department.id and ttd.teacher.id=:id 
''', [stdId:userForm.id,id: securityService.userId]
				if(results) {
					return results[0]
				} else {
					return null
				}
		
	}
	/**
	 * 检查学生是否2+2双学位专业
	 * @param userForm
	 * @return
	 */
	private boolean isDaulDegree(UserForm userForm){
		def results = Student.executeQuery '''
select s from Student s, Subject m 
where s.id=:stdId and s.major.id=m.id and m.isDualDegree=true 
''', [stdId:userForm.id]
						if(results) {
							return true
						} else {
							return false
						}
	}
	/**
	 * 查询本部门的申请单
	 */
	def requestList(int offset,int max,String bn){
		def results = TptRequest.executeQuery '''
select r from TptRequest r,TptStudent s, TptTeacherDept ttd 
where r.status=1 and r.bn=:bn and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id
''', [bn:bn,id: securityService.userId], [max: max,offset:offset]
		
		return results
	}
	
	def requestList(String username,String bn){
				def results = TptRequest.executeQuery '''
select new map(
   r.id as id,
   r.userId as userId,
   r.userName as userName,
   r.dateCreate as dateCreate,
   r.status as status,
   std.sex as sex,
   adc.name as adminClassName
)  from TptRequest r,TptStudent s, TptTeacherDept ttd ,Student std join std.adminClass adc
where r.status>=1 and r.bn=:bn and r.userName=:username and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id and r.userId=std.id
''', [username:username,bn:bn,id: securityService.userId]
				
				return results
			}
	def requestList(int offset,int max,String bn,int status){
		def results = TptRequest.executeQuery '''
select new map(
   r.id as id,
   r.userId as userId,
   r.userName as userName,
   r.dateCreate as dateCreate,
   r.status as status,
   t.name as mentorName,
   std.sex as sex,
   adc.name as adminClassName
) 
from TptRequest r left join r.mentor m left join m.teacher t,TptStudent s, TptTeacherDept ttd ,Student std join std.adminClass adc
where r.status =:status and r.bn=:bn and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id and r.userId=std.id
''', [bn:bn,status:status,id: securityService.userId], [max: max,offset:offset]
				return results
			}
	
	def requestCount(String bn){
		def results = TptRequest.executeQuery '''
select count(r.id) from TptRequest r,TptStudent s, TptTeacherDept ttd 
where r.status=1 and r.bn=:bn and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id
''', [bn:bn,id: securityService.userId]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	
	def requestCount(String bn,int status){
				def results = TptRequest.executeQuery '''
select count(r.id) from TptRequest r,TptStudent s, TptTeacherDept ttd 
where r.status=:status and r.bn=:bn and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id
''', [bn:bn,status:status,id: securityService.userId]
				if(results) {
					return results[0]
				} else {
					return null
				}
			}
	def requestCounts(String bn){
				def results = TptRequest.executeQuery '''
select r.status,count(r.id) from TptRequest r,TptStudent s, TptTeacherDept ttd 
where r.bn=:bn and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id 
group by r.status
''', [bn:bn,id: securityService.userId]
				if(results) {
					def statusCount = new int[8]
					for(int i=0;i<results.size();i++){
						statusCount[results[i][0]] = results[i][1]
					}
					return statusCount
				} else {
					return null
				}
			}
	
	def checkingNext(long id,int status){
		def results = TptRequest.executeQuery '''
select r.id from TptRequest r where r.status=:status and r.id> :id
''', [id: id,status:status], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def checkingPrev(long id,int status){
		def results = TptRequest.executeQuery '''
select r.id from TptRequest r where r.status=:status and r.id< :id order by id desc
''', [id: id,status:status], [max: 1]
		if(results) {
			return results[0]
		} else {
			return null
		}
	}
	def export(String bn) {
		def results = TptRequest.executeQuery '''
select new map(
  tp.userId	as userId,
	tp.userName as userName,
	tp.contact as contact,
	tp.phoneNumber as phoneNumber,
	tp.email	as email,
	tp.collegeName	as collegeName,
	tp.foreignMajor as foreignMajor,
	tp.dateCreate as dateCreate,
	tp.bn as bn,
	tp.status as status
)
from TptRequest tp
,TptStudent s, TptTeacherDept ttd 
where tp.status >0 and tp.bn=:bn and tp.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id
''', [bn: bn,id:securityService.userId]
		return [
			items: results
		]
	}
	def exportMtlRgn(String bn) {
		def results = TptRequest.executeQuery '''
select new map(
  tp.studentID	as userId,
	tp.name as userName,
	tp.type as type,
	tp.major as major,
	tp.foreignCollege	as collegeName,
	tp.f_c_major as foreignMajor,
	tp.masterCollege as masterCollege,
	tp.master_result as master_result,
	tp.paperName as paperName,
	tp.paperName_en as paperName_en,
	tr.email as email,
	tr.phoneNumber as phoneNumber
)
from TptRequest tr,TptPaperMtlRgn tp,TptStudent s, TptTeacherDept ttd 
where tr.userId=tp.studentID and tr.bn=tp.bn and tp.bn=:bn and tp.studentID = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id 
''', [bn: bn,id:securityService.userId]
		return [
			items: results
		]
	}
	def requestUsers(String bn,int status){
		TptRequest.executeQuery '''
select r.userId from TptRequest r,TptStudent s, TptTeacherDept ttd 
where r.status=:status and r.bn=:bn and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id
''', [bn:bn,status:status,id: securityService.userId]				
	}
	def requestUsers(String bn,List status){
		TptRequest.executeQuery '''
select r.userId from TptRequest r,TptStudent s, TptTeacherDept ttd 
where r.status in(:status) and r.bn=:bn and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id
''', [bn:bn,status:status,id: securityService.userId]				
	}
	def requestUsers(String bn){
		TptRequest.executeQuery '''
select r.userId from TptRequest r,TptStudent s, TptTeacherDept ttd 
where r.status in(4,5)  and r.bn=:bn and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id
''', [bn:bn,id: securityService.userId]				
	}
	def getCurrentBn(){
		def results=TptNotice.executeQuery '''
select t from TptNotice t, TptTeacherDept ttd 
where t.publisher= ttd.teacher.id and ttd.department.id=:id
order by t.id desc
''', [id: securityService.departmentId]	
		if(results) {
			return results[0].bn
		}
	}
	def checkAndInitRole(){
		Teacher me= Teacher.get(securityService.userId)
		def dprole=TptTeacherDept.findByTeacher(me)
		if(!dprole){
			new TptTeacherDept([teacher:me,department:Department.get(securityService.departmentId)]).save()
		}
	}
	def getBns(){
		TptRequest.executeQuery '''
select distinct r.bn from TptRequest r,TptStudent s, TptTeacherDept ttd 
where r.status>0  and r.userId = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:id
order by r.bn
''', [id: securityService.userId]
	}
	def findCourseByProject(){
		Sql sql = new Sql(dataSource_es)
		def course= sql.rows """
select JXJHH,LBMC,count(*) as c from zfxfzb.cgxmkcdmb group by JXJHH,LBMC
"""
		return course
	}
	def getMajor(){
		def result = GradeMajor.executeQuery'''
select new map(
		s.id	as id,
		g.id	as gmId,
		g.grade	as grade,
		s.name	as majorName,
		d.name	as departmentName
)
from GradeMajor g join g.subject s join g.department d where d.id=:departmentId
''',[departmentId:securityService.departmentId]
	}
	def getRemoteStudent(String stdId){
		
	}
	def noticeList(){
		def result = TptNotice.executeQuery'''
select new map(
		n.id	as id,
		n.title	as title,
		n.content	as content,
		n.start	as start,
		n.end	as end,
		n.publisher as publisher,
		n.publishDate as publishDate,
		CASE WHEN n.bn in (select distinct t.bn from TptRequest t) THEN 1 ELSE 0 END as enableDel
)
from TptNotice n where n.publisher=:publisher
''',[publisher:securityService.userId]
	}
	def collegeList(){
		def result = TptCollege.executeQuery'''
select new map(
		n.id	as id,
		n.name	as name,
		CASE WHEN n.id in (select distinct t.foreignCollege.id from TptRequest t) THEN 1 ELSE 0 END as enableDel
)
from TptCollege n where n.department_id=:departmentId
''',[departmentId:securityService.departmentId]
	}
	def mentorList(){
		def result = TptMentor.executeQuery'''
select new map(
	tc.id as id,
	tc.name as name,
	tc.sex as sex,
	tc.email as email,
	tc.longPhone as longPhone,
	tc.officeAddress as officeAddress,
	tc.external as external
)
from TptMentor m join m.teacher tc 
where tc.department.id=:departmentId
''',[departmentId:securityService.departmentId]
	}
	def delNotice(int id){
		def notice=TptNotice.get(id)
		if(notice){
			if(notice.publisher==securityService.userId && !TptRequest.findByBn(notice.bn)){
				notice.delete(flush:true)
				return true
			}				
		}
		return false
	}
	def loadTeachers(int offset,int max) {
		def results = Teacher.executeQuery '''
select new map(
	tc.id as id,
	tc.name as name,
	tc.sex as sex,
	tc.email as email,
	tc.longPhone as longPhone,
	tc.officeAddress as officeAddress,
	tc.external as external
)
from Teacher tc
 where tc.department.id=:departmentId
''',[departmentId:securityService.departmentId], [max: max,offset:offset]
	}
	def isMentor(Teacher teacher){
		return TptMentor.findByTeacher(teacher)!=null
	}
	def loadTeacherByName(String name){
		def results = Teacher.executeQuery '''
select new map(
	tc.id as id,
	tc.name as name,
	tc.sex as sex,
	tc.email as email,
	tc.longPhone as longPhone,
	tc.officeAddress as officeAddress,
	tc.external as external
)
from Teacher tc
 where tc.department.id=:departmentId and tc.name=:name
''',[departmentId:securityService.departmentId,name:name]
	}
	def getPaperMtg(id){
		def results = TptRequest.executeQuery '''
select new map(
	tp.id	as id,
	tp.studentID	as userId,
	tp.name as userName,
	tp.type as type,
	tp.major as major,
	tp.bn as bn,
	tp.foreignCollege	as collegeName,
	tp.f_c_major as foreignMajor,
	tp.masterCollege as masterCollege,
	tp.master_result as master_result,
	tp.paperName as paperName,
	tp.paperName_en as paperName_en,
	tp.abstrct as abstrct,
	tp.abstrct_en as abstrct_en,
	tr.email as email,
	tr.phoneNumber as phoneNumber
)
from TptRequest tr,TptPaperMtlRgn tp,TptStudent s, TptTeacherDept ttd 
where tr.id=:id and tr.userId=tp.studentID and tr.bn=tp.bn and tp.studentID = s.id and s.department.id=ttd.department.id and ttd.teacher.id=:userid 
''', [id: id,userid:securityService.userId]
		if(results) return results[0]
		else return null
	}
	
	def writePaperForm(id,scort,templateBase,uploadBase,projectId){
		TptPaperMtlRgn tptPaperMtlRgn = TptPaperMtlRgn.get(id)
//		更新成绩互认表成绩
		def old_result =tptPaperMtlRgn?.master_result
		tptPaperMtlRgn?.setMaster_result(scort)
		tptPaperMtlRgn?.save(flush:true)
		if (!tptPaperMtlRgn) return false
//		为word准备替换的map
		Map<String, String> map = new HashMap<String, String>();
		map.put("studentID", tptPaperMtlRgn.studentID)
		map.put("name", tptPaperMtlRgn.name)
		map.put("major", tptPaperMtlRgn.major)
		map.put("foreignCollege", tptPaperMtlRgn.foreignCollege)
		map.put("f_c_major", tptPaperMtlRgn.f_c_major)
		map.put("masterCollege", tptPaperMtlRgn.masterCollege)
		map.put("master_result", scort)
		map.put("paperName", tptPaperMtlRgn.paperName)
		map.put("abstrct", tptPaperMtlRgn.abstrct)
		map.put("abstrct_en", tptPaperMtlRgn.abstrct_en)
		map.put("paperName_en", tptPaperMtlRgn.paperName_en)
		map.put("bn", tptPaperMtlRgn.bn)

//		如果数据保存成功，生成word文档，保存到指定位置
		def templateFile =templateBase
		def filePath= uploadBase+"/"+tptPaperMtlRgn.bn+"/"+tptPaperMtlRgn.studentID
		if("1".equals(tptPaperMtlRgn.type))
			templateFile= templateFile+"/undergraduate.doc"
		else if("2".equals(tptPaperMtlRgn.type))
			templateFile= templateFile+"/master.doc"
		else if("3".equals(tptPaperMtlRgn.type))
			templateFile= templateFile+"/course.doc"
		def destFile= "paper_exch"+tptPaperMtlRgn.studentID+".doc"
//		将原文件备份
		File file=new File(filePath+"/"+destFile)
		file?.renameTo(filePath+"/bak_"+destFile)
		WriteTable wt=new WriteTable()
		try{
			wt.write(templateFile, filePath+"/"+destFile, map)
		}catch(Exception e){
			return false
		}
//		日志
		TptAudit tptAudit=new TptAudit([
			userId:securityService.userId,
			userName:securityService.userName,
			action:TptAudit.ACTION_UPDATESCORT,
			date:new Date(),
			content:"from ${old_result} To ${scort}",
			form:TptRequest.get(projectId)])
		if(!tptAudit.save(flush:true)){
			tptAudit.errors.each{
				println it
			}
		}
		return true
	}
}
