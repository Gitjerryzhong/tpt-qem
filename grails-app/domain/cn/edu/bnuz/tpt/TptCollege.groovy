package cn.edu.bnuz.tpt

import cn.edu.bnuz.tms.organization.Department

class TptCollege {
	String name
	String department_id
	static mapping = {
		sort "name"
		department_id length:2
	}
    static constraints = {
		department_id		nullable: true
    }
}
