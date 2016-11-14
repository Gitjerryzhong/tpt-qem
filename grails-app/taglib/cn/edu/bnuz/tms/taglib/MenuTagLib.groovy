package cn.edu.bnuz.tms.taglib

import cn.edu.bnuz.tms.security.SecurityService
import cn.edu.bnuz.tms.system.Menu

class MenuTagLib {
	static namespace = 't'
	SecurityService securityService
	def menu = { attrs, body ->
		List<Menu> menus = session.getAttribute("menus");
		out << '<ul class="nav navbar-nav">'
		menus.each {create it}		
		out << '</ul>'
	}
	
	private create(Menu menu) {
		if(menu.submenus) {
			if(menu.label == "_user_") {
				out << '<li class="dropdown user-info">' 
				out << '<a href="#" class="dropdown-toggle" data-toggle="dropdown">'
				out << securityService.principal.name 
			} else {
				out << '<li class="dropdown">' 
				out << '<a href="#" class="dropdown-toggle" data-toggle="dropdown">'
				out << message(code:'menu.' + menu.label)
			}
			out << ' <b class="caret"></b></a>'
			out << '<ul class="dropdown-menu">'
			menu.submenus.each { create(it) }
			out << '</ul>'
			out << '</li>'
		} else {
			if(menu.url) {
				out << '<li><a href="' 
				if(menu.url.startsWith("http://")) {
					out << menu.url << '" target="_blank'
				} else {
					out << createLink(uri: menu.url)
				}   
				out << '">' << message(code:'menu.' + menu.label) << '</a></li>'
			}
		}
	}
}

/*
<ul class="nav">
	<li><a href="${createLink(uri: '/')}">首页</a></li>
	<li class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown">活动 <b class="caret"></b></a>
		<ul class="dropdown-menu">
			<g:each var="c" in="${session.activities}">
			<li><a href="${createLink(uri: c[1])}">${c[0]}</a></li>
			</g:each>
		</ul>
	</li>
	<li class="dropdown">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown">查询<b class="caret"></b></a>
		<ul class="dropdown-menu">
			<g:each var="c" in="${session.queries}">
			<li><a href="${createLink(uri: [1])}">${c[0]}</a></li>
			</g:each>
		</ul>
	</li>
	<li><a href="${createLink(uri: '/j_spring_security_logout')}">退出</a></li>
</ul>
*/