package cn.edu.bnuz.tpt

import grails.transaction.Transactional
import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tpt.teaching.GradeMajor
import cn.edu.bnuz.tpt.teaching.Subject
import groovy.sql.Sql
import cn.edu.bnuz.tpt.Error
import cn.edu.bnuz.tpt.ErrorItem

@Transactional
class TptCoProjectService {
	def dataSource_es
	SecurityService securityService
	TptLogService tptLogService
	/**
	 * 保存协议
	 * @param project
	 * @return
	 */
    def save(CoProject project) {
		def tptCoProject = new TptCoProject(
		name:project.name,
//		beginYear:project.beginYear,
//		effeYears:project.effeYears,
//		effeYearStr:project.effeYearStr,
		effective:project.actived,
		ifTowDegree:project.ifTowDegree,
		tptCoType:TptCoType.get(project.coTypeId),
		tptCoCountry:TptCoCountry.get(project.coCountryId),
		memo:project.memo
		)
		tptCoProject.save(flush:true)
		tptCoProject.errors.each {
			println it
		}
//		插入日志
		tptLogService.saveLog(tptCoProject, TptLog.ACTION_INSERT, tptCoProject.name)
		
		return [id:tptCoProject.id,name:tptCoProject.name,effective:tptCoProject.effective,
			coTypeName:tptCoProject.tptCoType.name,coCountryName:tptCoProject.tptCoCountry.name,
			ifTowDegree:tptCoProject.ifTowDegree,memo:tptCoProject.memo]
    }
	/**
	 * 保存协议明细
	 * @param project
	 * @return
	 */
	def saveItem(CoProjectItem item) {
		def tptCoPrjItem = new TptCoPrjItem(
		project:TptCoProject.get(item.projectId),
		major:Subject.get(item.majorsId),
		collegeNameEn:item.collegeNameEn,
		collegeNameCn:item.collegeNameCn,
		beginYear:item.beginYear,
		effeYears:item.effeYears,
		effeYearStr:item.effeYearStr,
		coDegreeOrMajor:item.coDegreeOrMajor,
		memo:item.memo
		)
		tptCoPrjItem.save(flush:true)
		tptCoPrjItem.errors.each {
			println it
		}
//		推送到bnuep_print 
		saveMajorProject(tptCoPrjItem)
//		插入日志
		tptLogService.saveLog(tptCoPrjItem, TptLog.ACTION_INSERT, "${tptCoPrjItem.major.name}-----${tptCoPrjItem.collegeNameCn}")
		
		return [id:tptCoPrjItem.id,collegeNameEn:tptCoPrjItem.collegeNameEn,collegeNameCn:tptCoPrjItem.collegeNameCn,
			majorName:tptCoPrjItem.major.name,beginYear:tptCoPrjItem.beginYear,effeYearStr:tptCoPrjItem.effeYearStr,
			effeYears:tptCoPrjItem.effeYears,memo:tptCoPrjItem.memo]
	}
	
	private saveMajorProject(TptCoPrjItem item){
//		计算出有效年级
		def grade=[[ID:"${item.beginYear+item.major.id}".toString(),NAME:item.project.tptCoCountry.name]]
		def i=1;
		while((item.effeYears>>i) >0){
			if((item.effeYears>>i) & 1) 
				grade+=[ID:"${item.beginYear+i}${item.major.id}".toString(),NAME:item.project.tptCoCountry.name]
			i++;
		}

		Sql sql = new Sql(dataSource_es)
		def majorProjects = sql.rows"""
select * from T_TMS_PROJECT
"""
		grade=grade-majorProjects
//		批量插入Oracle数据库
		def tb_project =sql.dataSet("T_TMS_PROJECT")
		tb_project.withBatch(){
			grade.each {
				tb_project.add(it)
				println it
			}
		}
	}
	def updateItem(CoProjectItem item){
		def tptCoPrjItem = TptCoPrjItem.get(item.id)
//		插入日志
		tptLogService.saveLog(tptCoPrjItem, TptLog.ACTION_UPDATE, "${tptCoPrjItem.major.name}--${tptCoPrjItem.collegeNameCn}--${tptCoPrjItem.collegeNameEn}--${tptCoPrjItem.effeYearStr}")

		tptCoPrjItem.setMajor(Subject.get(item.majorsId))
		tptCoPrjItem.setCollegeNameCn(item.collegeNameCn)
		tptCoPrjItem.setCollegeNameEn(item.collegeNameEn)
		tptCoPrjItem.setBeginYear(item.beginYear)
		tptCoPrjItem.setEffeYears(item.effeYears)
		tptCoPrjItem.setEffeYearStr(item.effeYearStr)
		tptCoPrjItem.setCoDegreeOrMajor(item.coDegreeOrMajor)
		tptCoPrjItem.setMemo(item.memo)
		tptCoPrjItem.save(flush:true)
		return [id:tptCoPrjItem.id,collegeNameEn:tptCoPrjItem.collegeNameEn,collegeNameCn:tptCoPrjItem.collegeNameCn,
			majorName:tptCoPrjItem.major.name,beginYear:tptCoPrjItem.beginYear,effeYearStr:tptCoPrjItem.effeYearStr,
			effeYears:tptCoPrjItem.effeYears,memo:tptCoPrjItem.memo]
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
from GradeMajor g join g.subject s join g.department d
where s.isDualDegree = true
'''
	}
	
	def getProjectItem(def id){
		def result = TptCoPrjItem.executeQuery'''
select new map(
		i.id	as id,
		m.name	as majorName,
		m.id	as majorsId,
		d.name	as departmentName,
		i.beginYear	as beginYear,
		i.effeYearStr	as effeYearStr,
		i.effeYears	as effeYears,
		i.collegeNameEn	as collegeNameEn,
		i.collegeNameCn	as collegeNameCn,
		i.coDegreeOrMajor	as coDegreeOrMajor,
		i.memo			as memo
		

)
from TptCoPrjItem i join i.major m join m.department d
where i.project.id=:id
''', [id: id]
	}
	def getProjectList(){
		def result = TptCoProject.executeQuery'''
select new map(
		p.id	as coProjectId,
		p.name	as projectName,
		t.name	as coTypeName,
		c.name	as coProTypeName,
		CASE p.effective WHEN 1 THEN 'Yes' ELSE 'No' END	as effective,
		CASE p.ifTowDegree WHEN 1 THEN 'Yes' ELSE 'No' END	as ifTowDegree,
		m.name	as majorName,
		d.name	as departmentName,
		i.id	as id,
		i.beginYear	as beginYear,
		i.effeYears	as effeYears,
		i.effeYearStr	as effeYearStr,
		i.collegeNameEn	as collegeNameEn,
		i.collegeNameCn	as collegeNameCn,
		i.coDegreeOrMajor	as coDegreeOrMajor,
		i.memo			as memo
)
from TptCoProject p join p.tptCoType t join p.tptCoCountry c left join p.tptCoPrjItem i left join i.major m left join m.department d
'''
	}
	def getProjectDetail(def id){
		def result = TptCoProject.executeQuery'''
select new map(
		p.id	as id,
		p.name	as name,
		t.name	as coTypeName,
		c.name	as coCountryName,
		p.effective 	as effective,
		p.ifTowDegree 	as ifTowDegree,
		p.memo			as memo

)
from TptCoProject p join p.tptCoType t join p.tptCoCountry c
where p.id=:id
''', [id: id]
		return result[0]
	}
	def importStudent(def students){
		Sql sql = new Sql(dataSource_es)
		def tb_xsmd
	}
	def listStudent(def id,def co_project){
		Sql sql = new Sql(dataSource_es)
		def students = sql.rows"""
select * from T_TMS_XSMD 
where (:id is null or xh like :xh )and(:co_project is null or kind = :co_project)
""",[id:id,xh:id+'%',co_project:co_project]
		return students
	}
	def findCourseByProject(){
		Sql sql = new Sql(dataSource_es)
		def course= sql.rows """
select JXJHH,LBMC,count(*) as c from zfxfzb.cgxmkcdmb group by JXJHH,LBMC
"""
		return course
	}
	def studentSave(UserForm userForm) {
		def error=new Error()
		def users=userForm?.users?.split("\n")
		def userList=[]
		Sql sql = new Sql(dataSource_es)
//		清空临时表
		sql.execute """
		TRUNCATE TABLE T_TMS_XSMD_TEMP
"""
//		批量插入临时表。这里先生成名单对象数组
		for(int i=0;i<users?.length;i++){
			if(users[i]){
				def userInfo = users[i].split("\t")
				if(userInfo.length==2){
					userList = userList+[ID:userInfo[0],NAME:userInfo[1],PROJECT:userForm.projectName]
				}
			}
		}
		def tb_xsmd =sql.dataSet("T_TMS_XSMD_TEMP")
		tb_xsmd.withBatch(){
			userList.each {
//				println it
				tb_xsmd.add(it)
			}
		}
//		查询是否有重复
		def duplicateList= sql.rows """
select t.* from T_TMS_XSMD_TEMP t JOIN T_TMS_XSMD x on t.ID=x.XH
"""
//		查询是否有姓名和学号不相符
		def unmatchList= sql.rows """
select t.* from T_TMS_XSMD_TEMP t JOIN zfxfzb.xsjbxxb x on t.id=x.XH where t.name<>x.XM
"""
//		查询学生所在专业是否可申请该项目
		def prjMatchList= sql.rows """
select tmp.*,prj.name as matchname,zydmb.sfcg from T_TMS_XSMD_TEMP tmp 
join zfxfzb.xsjbxxb xsb on tmp.id=xsb.XH 
JOIN zfxfzb.zydmb on xsb.ZYDM =zydmb.ZYDM 
left join T_TMS_PROJECT prj on prj.id=concat(xsb.DQSZJ,xsb.ZYDM) and  tmp.PROJECT=prj.name
"""
		def prjUnMatchList = prjMatchList.grep{
			it.MATCHNAME ==null
		}
		def notDualList = prjMatchList.grep{
			it.SFCG =='0'
		}
//		将有问题名单删除
//		if(duplicateList || unmatchList || prjMatchList)
		sql.execute """
delete from T_TMS_XSMD_TEMP t where t.ID in
(select t.ID from T_TMS_XSMD_TEMP t JOIN T_TMS_XSMD x on t.ID=x.XH union
select t.ID from T_TMS_XSMD_TEMP t JOIN zfxfzb.xsjbxxb x on t.id=x.XH where t.name<>x.XM union 
 select tmp.ID from T_TMS_XSMD_TEMP tmp join zfxfzb.xsjbxxb xsb on tmp.id=xsb.XH 
        JOIN zfxfzb.zydmb on xsb.ZYDM =zydmb.ZYDM 
        left join T_TMS_PROJECT prj on prj.id=concat(xsb.DQSZJ,xsb.ZYDM) and  tmp.PROJECT=prj.name 
        where zydmb.sfcg=0 or prj.name is null
)
"""
		def succesList = sql.rows """
select t.* from T_TMS_XSMD_TEMP t 
"""
//		将名单导入T_TMS_XSMD表
		sql.execute """
insert into T_TMS_XSMD (MDID,XH,XM,ADDAUTHOR,ADDDATE,INPROJECT,KIND)
select T_TMS_XSMD_ID_SEQ.nextval,t.ID,t.NAME,${securityService.userId},sysdate,1,${userForm.projectName} from T_TMS_XSMD_TEMP t
"""
		return [duplicateList:duplicateList,unmatchList:unmatchList,prjUnMatchList:prjUnMatchList,notDualList:notDualList,successCount:succesList.size()]
	}
//	删除名单
	def deleteStudent(def item){
		Sql sql = new Sql(dataSource_es)
		sql.execute """
update T_TMS_XSMD set DELAUTHOR= ${securityService.userId},DELDATE=sysdate,INPROJECT=0 where MDID=:id 
""",[id:item.id]
//		插入日志
		tptLogService.saveLog(item, TptLog.ACTION_DELETE, item.xh)
	}
//	更新项目
	def editStudent(def item){
		Sql sql = new Sql(dataSource_es)
		sql.execute """
update T_TMS_XSMD set KIND=:kind where MDID=:id 
""",[id:item.id,kind:item.kind]
//		插入日志
		tptLogService.saveLog(item, TptLog.ACTION_UPDATE, "XH:${item.xh},XM:${item.xm},KIND:${item.oldKind}")
	}
//	查询学生所在年级专业可申请的项目
	def getProjects(def studentId){
		Sql sql = new Sql(dataSource_es)
		sql.rows """
select prj.name from zfxfzb.xsjbxxb xsb 
 JOIN zfxfzb.zydmb on xsb.ZYDM =zydmb.ZYDM 
 LEFT JOIN T_TMS_PROJECT prj on prj.id=concat(xsb.DQSZJ,xsb.ZYDM) 
 where xsb.xh=:id
""",[id:studentId]
	}
}
