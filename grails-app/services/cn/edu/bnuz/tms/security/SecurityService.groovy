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

	/** ע���authenticationTrustResolver */
	def authenticationTrustResolver

	/** ע���grailsApplication */
	def grailsApplication

	/** ע���password encoder */
	def passwordEncoder

	/** ע���{@link FilterInvocationSecurityMetadataSource} */
	def objectDefinitionSource

	/** ע���userDetailsService */
	def userDetailsService

	/** ע���userCache */
	def userCache

	/**
	 * ��ȡ��ǰ��¼�û��İ�ȫ���塣���δ��Ȩ��AnonymousAuthenticationFilter���ڻ״̬���򷵻�
	 * �����û���ȱʡΪ��anonymousUser����
	 *
	 * @return the principal �û���ȫ����
	 */
	def getPrincipal() {
		getAuthentication()?.principal
	}

	/**
	 * ��ȡ��ǰ��¼�û��������֤��<code>Authentication</code>�������δ��Ȩ��AnonymousAuthenticationFilter���ڻ״̬���򷵻�
	 * �������û���֤��AnonymousAuthenticationToken��ȱʡ�û���Ϊ'anonymousUser'����
	 *
	 * @return �����֤
	 */
	Authentication getAuthentication() {
		SCH.context?.authentication
	}

	/**
	 * ��ȡ��ǰ��¼�������֤��ص�domainʵ����
	 * @return �û�
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
	 * �жϵ�ǰ�û��Ƿ�Ϊ��ʦ
	 * @return �Ƿ�Ϊ��ʦ
	 */
	boolean isTeacher() {
		if(!isLoggedIn())
			return false
		return principal instanceof TeacherDetails
	}
	
	/**
	 * �жϵ�ǰ�û��Ƿ�Ϊѧ��
	 * @return �Ƿ�Ϊѧ��
	 */
	boolean isStudent() {
		if(!isLoggedIn())
			return false
		return principal instanceof StudentDetails
	}
	
	/**
	 * �жϵ�ǰ�û��Ƿ����ָ����ɫ
	 * @param role ��ɫ
	 * @return �Ƿ�ӵ��ָ����ɫ
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
	 * ��⵱ǰ�û��Ƿ��¼��
	 * @return �������֤�Ҳ��������û�������<code>true</code>
	 */
	boolean isLoggedIn() {
		def authentication = SCH.context.authentication
		return authentication && !authenticationTrustResolver.isAnonymous(authentication)
	}

	/**
	 * Ϊ�������û����¹���һ����֤����ע�ᵽsecurity context�С��������û���֤��Ϣ���ϻ�����Ϣ����֮��
	 *
	 * @param username ��¼��
	 * @param password ���루��ѡ��
	 */
	void reauthenticate(String username, String password = null) {
		SpringSecurityUtils.reauthenticate username, password
	}

	/**
	 * ��������Ƿ���Ajax���ô�����
	 * @param request ����
	 * @return �����Ajax�������򷵻�<code>true</code>
	 */
	boolean isAjax(HttpServletRequest request) {
		SpringSecurityUtils.isAjax request
	}

	/**
	 * ���༭��������ɾ��Requestmap����ã�����ջ������ã������������ݡ�
	 */
	void clearCachedRequestmaps() {
		objectDefinitionSource?.reset()
	}
}

