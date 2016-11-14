package cn.edu.bnuz.tms.graduate

import cn.edu.bnuz.tms.organization.Department


class Internship {
	Department department
	Integer grade
	Long materialTerm
	
	static mapping = {
		table 		'grd_internship'
	}
}
