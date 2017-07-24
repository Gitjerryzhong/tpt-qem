package cn.edu.bnuz.tpt

import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tms.teaching.SectionService;
import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class TptService {
	def dataSource_es
	SecurityService securityService
    def getSfzh() {
		Sql sql =  new Sql(dataSource_es)
		def sfzh=sql.rows """
select SFZH
from ZFXFZB.XSJBXXB
where XH = :userId
""", [userId:securityService.userId ]
	return sfzh?.get(0)?.SFZH
    }
	
	def getPhotoName(){
		def photoName= "photo_"+securityService.userName+"_"+getSfzh()
		if (photoName==null) return ""
		else return photoName
		
	}
	
	def HWPFDocument replaceDoc(String srcPath, Map<String, String> map) {
		try {
// 读取word模板
			FileInputStream fis = new FileInputStream(new File(srcPath));
			HWPFDocument doc = new HWPFDocument(fis);
// 读取word文本内容
			Range bodyRange = doc.getRange();
// 替换文本内容
			for (Map.Entry<String, String> entry : map.entrySet()) {
				bodyRange.replaceText("${" + entry.getKey() + "}",
						entry.getValue());
			}
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	def getCurrentBn(){
		def results=TptNotice.executeQuery '''
select t.bn from TptNotice t, TptTeacherDept ttd 
where t.publisher= ttd.teacher.id and ttd.department.id=:id
order by t.id desc
''', [id: securityService.departmentId]	
		if(results) {
			return results[0]
		}
	}
	def getCurrentNotice(){
		def results=TptNotice.executeQuery '''
select t from TptNotice t, TptTeacherDept ttd 
where t.publisher= ttd.teacher.id and ttd.department.id=:id
order by t.id desc
''', [id: securityService.departmentId]	
		if(results) {
			return results[0]
		}
	}
	def getRequestByBn(String bn){
		def results=TptRequest.executeQuery '''
select r from TptRequest r 
where r.bn= :bn and r.userId=:userId
''', [userId: securityService.userId,bn:bn]
		if(results) {
			return results[0]
		}
	}
	def getCurrentNotice(def id){
		def results=TptNotice.executeQuery '''
select t from TptNotice t, TptTeacherDept ttd 
where t.publisher= ttd.teacher.id and ttd.department.id=:deptId and t.id=:id
''', [deptId: securityService.departmentId,id:id]	
		if(results) {
			return results[0]
		}
	}
	def findNoticeByBn(def bn){
		def results=TptNotice.executeQuery '''
select t from TptNotice t, TptTeacherDept ttd 
where t.publisher= ttd.teacher.id and ttd.department.id=:deptId and t.bn=:bn
''', [deptId: securityService.departmentId,bn:bn]	
		if(results) {
			return results[0]
		}
	}
	def getNoticeList(){
		def results=TptNotice.executeQuery '''
select t from TptNotice t, TptTeacherDept ttd 
where t.publisher= ttd.teacher.id and ttd.department.id=:id
order by t.id desc
''', [id: securityService.departmentId]	, [max: 1]
	}
	def getColleges(){
		TptCollege.executeQuery '''
select t.name from TptCollege t
where t.department_id in
(select ttd.department.id from  TptTeacherDept ttd where ttd.teacher.id in
(select td.teacher.id from TptTeacherDept td where td.department.id=:id))
''', [id: securityService.departmentId]				
	}
	def getCoColleges(){
		def results = TptStudent.executeQuery'''
select distinct i.collegeNameEn as name
from TptStudent s join s.tptCoPrjItem i join i.project p
where s.id=:userId
''', [userId: securityService.userId]	
		if(results?.size()) return results
		results = TptStudent.executeQuery'''
select distinct i.collegeNameEn as name
from TptStudent s ,TptCoProject p join p.tptCoPrjItem i join i.major m join m.department d
where s.id=:userId  and s.tptCoCountry = p.tptCoCountry and d.id=:deptId
''', [userId: securityService.userId,deptId:securityService.departmentId]
		return results 
	}
}
