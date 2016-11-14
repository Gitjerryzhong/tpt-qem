import org.apache.commons.logging.LogFactory
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import cn.edu.bnuz.tms.security.SecurityService


class TrackingFilters {
	def logger = LogFactory.getLog("user-tracking")
	SecurityService securityService
	def filters = {
		all(controller:'*', action:'*') {
			before = {
				if(controllerName != "login" && controllerName != "assets") {
//					x-forwarded-for
					def remoteAddr = request.getHeader("X-Real-IP") ?: request.remoteAddr
					def userId = securityService.userId

					def userName = securityService.userName
					def data = params.findAll {key, value -> !(key in [
							'controller',
							'action',
							'_',
							'format'
						])}
					logger.info("${remoteAddr} ${userId}/${userName} ${controllerName}/${actionName?:''} ${data?:''}")
				}
			}
		}
	}
}
