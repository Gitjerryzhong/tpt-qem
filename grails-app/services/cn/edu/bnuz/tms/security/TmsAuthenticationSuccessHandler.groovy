package cn.edu.bnuz.tms.security

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.apache.commons.logging.LogFactory
import grails.plugin.springsecurity.web.authentication.AjaxAwareAuthenticationSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.security.web.savedrequest.SavedRequest

import cn.edu.bnuz.tms.system.Menu
import cn.edu.bnuz.tms.system.NavigateService

class TmsAuthenticationSuccessHandler extends AjaxAwareAuthenticationSuccessHandler {
	private static final LOG = LogFactory.getLog("user-tracking")

	@Autowired
	NavigateService navigateService

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws ServletException, IOException {
		def remoteAddr = request.getHeader("X-Real-IP") ?: request.remoteAddr
		LOG.info("${remoteAddr} ${authentication.principal.id}/${authentication.principal.name} login/success")
		
		// set teacher's session timeout
		if(authentication.principal instanceof TeacherDetails) {
			request.session.maxInactiveInterval = 3600
		}
		
		// set menu
		List<String> roles = authentication.getAuthorities().collect { it.authority }
		List<Menu> menus = navigateService.getMenus(roles)
		request.session.setAttribute('menus', menus)

		super.onAuthenticationSuccess(request, response, authentication)
	}
}
