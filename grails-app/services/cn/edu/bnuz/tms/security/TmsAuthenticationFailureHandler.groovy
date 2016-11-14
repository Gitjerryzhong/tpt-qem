package cn.edu.bnuz.tms.security

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.apache.commons.logging.LogFactory
import grails.plugin.springsecurity.web.authentication.AjaxAwareAuthenticationFailureHandler
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class TmsAuthenticationFailureHandler extends AjaxAwareAuthenticationFailureHandler {
	private static final LOG = LogFactory.getLog("user-tracking")
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
	throws IOException, ServletException {
		def userId = request.parameterMap[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY]
		def password = request.parameterMap[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY]
		def remoteAddr = request.getHeader("X-Real-IP") ?: request.remoteAddr
		LOG.info("${remoteAddr} ${userId}:${password} login/failure")
		
		super.onAuthenticationFailure(request, response, exception);
	}
}
