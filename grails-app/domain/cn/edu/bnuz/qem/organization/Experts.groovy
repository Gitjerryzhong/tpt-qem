package cn.edu.bnuz.qem.organization

import cn.edu.bnuz.tms.organization.Teacher

class Experts{
	Teacher teacher
	
	String discipline
	
	String direction

	static mapping={
		table	'qem_experts'
		discipline length:32
		direction length:32
	}
    static constraints = {
		teacher		unique:true
		discipline	nullable:true
		direction	nullable:true
    }
}
