package cn.edu.bnuz.tms.security

import javax.servlet.http.HttpServletRequest

import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder as SCH

import cn.edu.bnuz.tms.organization.Student
import cn.edu.bnuz.tms.organization.Teacher

/**
 */
class SecurityService {

	static transactional = false

	/** 注入的authenticationTrustResolver */
	def authenticationTrustResolver

	/** 注入的grailsApplication */
	def grailsApplication

	/** 注入的password encoder */
	def passwordEncoder

	/** 注入的{@link FilterInvocationSecurityMetadataSource} */
	def objectDefinitionSource

	/** 注入的userDetailsService */
	def userDetailsService

	/** 注入的userCache */
	def userCache

	/**
	 * 获取当前登录用户的安全主体。如果未授权且AnonymousAuthenticationFilter处于活动状态，则返回
	 * 匿名用户（缺省为“anonymousUser”）
	 *
	 * @return the principal 用户安全主体
	 */
	def getPrincipal() {
		getAuthentication()?.principal
	}

	/**
	 * 获取当前登录用户的身份认证（<code>Authentication</code>）。如果未授权且AnonymousAuthenticationFilter处于活动状态，则返回
	 * 匿名的用户认证（AnonymousAuthenticationToken，缺省用户名为'anonymousUser'）。
	 *
	 * @return 身份认证
	 */
	Authentication getAuthentication() {
		SCH.context?.authentication
	}

	/**
	 * 获取当前登录的身份认证相关的domain实例。
	 * @return 用户
	 */
	Object getCurrentUser() {
		if (!isLoggedIn()) {
			return null
		}
		switch(principal) {
			case TeacherDetails:
				return Teacher.get(principal.id)
			case StudentDetails:
				return Student.get(principal.id)
			default:
				return null
		}
	}
	
	String getUserId() {
		return principal?.id
	}
	
	String getUserName() {
		return principal?.name
	}
	
	String getDepartmentId() {
		return principal?.departmentId
	}

	/**
	 * 判断当前用户是否为教师
	 * @return 是否为教师
	 */
	boolean isTeacher() {
		if(!isLoggedIn())
			return false
		return principal instanceof TeacherDetails
	}
	
	/**
	 * 判断当前用户是否为学生
	 * @return 是否为学生
	 */
	boolean isStudent() {
		if(!isLoggedIn())
			return false
		return principal instanceof StudentDetails
	}
	
	/**
	 * 判断当前用户是否具有指定角色
	 * @param role 角色
	 * @return 是否拥有指定角色
	 */
	boolean hasRole(String role) {
		if(!authentication) {
			return false;
		}
		return authentication.authorities.find {
			it.authority == role
		} != null
	}

	/**
	 * 检测当前用户是否登录。
	 * @return 如果已认证且不是匿名用户，返回<code>true</code>
	 */
	boolean isLoggedIn() {
		def authentication = SCH.context.authentication
		return authentication && !authenticationTrustResolver.isAnonymous(authentication)
	}

	/**
	 * 为给定的用户重新构建一下认证，且注册到security context中。常用于用户认证信息或认缓存信息更新之后。
	 *
	 * @param username 登录名
	 * @param password 密码（可选）
	 */
	void reauthenticate(String username, String password = null) {
		SpringSecurityUtils.reauthenticate username, password
	}

	/**
	 * 检测请求是否由Ajax调用触发。
	 * @param request 请求
	 * @return 如果由Ajax激发，则返回<code>true</code>
	 */
	boolean isAjax(HttpServletRequest request) {
		SpringSecurityUtils.isAjax request
	}

	/**
	 * 当编辑、创建或删除Requestmap后调用，以清空缓存配置，加载最新数据。
	 */
	void clearCachedRequestmaps() {
		objectDefinitionSource?.reset()
	}
}

