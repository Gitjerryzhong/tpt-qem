package cn.edu.bnuz.tms.system

import grails.plugin.springsecurity.SpringSecurityUtils
import cn.edu.bnuz.tms.orm.usertype.RoleListUserType
import cn.edu.bnuz.tms.system.RoleList;

class WhatIsNew {
	String label
	java.sql.Date date
	RoleList roles
	String uri
		
	static mapping  = {
		table 'sys_what_is_new'
		id name:'label', generator:'assigned', type:'string', length:50
		roles type:RoleListUserType
	}
	
	static constraints = {
		roles nullable: true
		uri nullable: true
	}
	
	boolean hasRoles() {
		roles && SpringSecurityUtils.ifAnyGranted(roles.toString())
	}
}
