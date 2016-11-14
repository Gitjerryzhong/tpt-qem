package cn.edu.bnuz.tpt

import java.util.List;

import cn.edu.bnuz.tms.security.SecurityService;
import grails.transaction.Transactional

@Transactional
class TptMentorCheckService {
	SecurityService securityService
    def getCurrentBn(){
		def results=TptNotice.executeQuery '''
select t from TptNotice t, TptTeacherDept ttd 
where ttd.department.id=:id
order by t.id desc
''', [id: securityService.departmentId]	
		if(results) {
			return results[0].bn
		}
	}
	
	def requestList(int offset,int max,String bn,int status){
		def results = TptRequest.executeQuery '''
select new map(
   r.id as id,
   r.userId as userId,
   r.userName as userName,
   r.email as email,
   r.phoneNumber as  phoneNumber,
   r.dateCreate as dateCreate,
   r.status as status,
   std.sex as sex,
   adc.name as adminClassName
) 
from TptRequest r left join r.mentor m left join m.teacher t,Student std join std.adminClass adc
where r.status =:status and r.bn=:bn  and r.userId=std.id and t.id=:userId
''', [bn:bn,status:status,userId: securityService.userId], [max: max,offset:offset]
				return results
			}
	def requestCounts(String bn){
		def results = TptRequest.executeQuery '''
select r.status,count(r.id) 
from TptRequest r left join r.mentor m left join m.teacher t
where r.status >=4 and r.bn=:bn  and t.id=:userId
group by r.status
''', [bn:bn,userId: securityService.userId]
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
	def requestUser(id){
		TptRequest.executeQuery '''
select r.userId from TptRequest r left join r.mentor m left join m.teacher t
where  r.id = :id and t.id=:userId
''', [id:id,userId: securityService.userId]				
	}
}
