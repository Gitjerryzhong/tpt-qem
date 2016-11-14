import grails.plugin.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder
import cn.edu.bnuz.tms.security.TmsAuthenticationSuccessHandler
import cn.edu.bnuz.tms.security.TmsAuthenticationFailureHandler
import cn.edu.bnuz.tms.security.TmsDaoAuthenticationProvider
import cn.edu.bnuz.tms.security.TmsUserDetailsService

// Place your Spring DSL code here
beans = {
	passwordEncoder(PlaintextPasswordEncoder)
	userDetailsService(TmsUserDetailsService)
	
	// for double password
	daoAuthenticationProvider(TmsDaoAuthenticationProvider) {
		def conf = SpringSecurityUtils.securityConfig
		userDetailsService = ref('userDetailsService')
		passwordEncoder = ref('passwordEncoder')
		userCache = ref('userCache')
		saltSource = ref('saltSource')
		preAuthenticationChecks = ref('preAuthenticationChecks')
		postAuthenticationChecks = ref('postAuthenticationChecks')
		authoritiesMapper = ref('authoritiesMapper')
		hideUserNotFoundExceptions = conf.dao.hideUserNotFoundExceptions
	}
	
	authenticationSuccessHandler(TmsAuthenticationSuccessHandler) {
		def conf = SpringSecurityUtils.securityConfig
		requestCache = ref('requestCache')
		redirectStrategy = ref('redirectStrategy')
		defaultTargetUrl = conf.successHandler.defaultTargetUrl
		alwaysUseDefaultTargetUrl = conf.successHandler.alwaysUseDefault
		targetUrlParameter = conf.successHandler.targetUrlParameter
		ajaxSuccessUrl = conf.successHandler.ajaxSuccessUrl
		useReferer = conf.successHandler.useReferer
	}
	
	authenticationFailureHandler(TmsAuthenticationFailureHandler) {
		def conf = SpringSecurityUtils.securityConfig
		redirectStrategy = ref('redirectStrategy')
		defaultFailureUrl = conf.failureHandler.defaultFailureUrl
		useForward = conf.failureHandler.useForward
		ajaxAuthenticationFailureUrl = conf.failureHandler.ajaxAuthFailUrl
		exceptionMappings = conf.failureHandler.exceptionMappings
	}	
}
