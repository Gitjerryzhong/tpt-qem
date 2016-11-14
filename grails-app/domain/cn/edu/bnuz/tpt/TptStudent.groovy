package cn.edu.bnuz.tpt

import cn.edu.bnuz.tms.organization.Department;

class TptStudent {
	String id
	String name
	Department department
	String operator
	Date operate_time
	TptCoPrjItem tptCoPrjItem
	TptCoCountry tptCoCountry
	static mapping={
		table 'tpt_student'
		id generator: 'assigned', length: 10
		name length: 50
	}
    static constraints = {
		tptCoCountry	nullable:true
		operator	nullable:true
		tptCoPrjItem 	nullable:true
		operate_time	nullable:true
    }
}
