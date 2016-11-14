package cn.edu.bnuz.tpt

import grails.transaction.Transactional
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tpt.TptLog
@Transactional
class TptLogService {
	SecurityService securityService
    def saveLog(def object, def action, def content) {
		TptLog log=new TptLog([
			userId:securityService.userId,
			action:action,
			date:new Date(),
			content:content,
			objectId:object?.id,
			src:object?.class.name])
		log.save(flush:true)
    }
}
