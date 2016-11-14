package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Department;
import groovy.transform.ToString;

class Major {
	String id
	String name
	
	@Override
	String toString() {
		"Major[$name]"
	}
	
	static belongsTo = [department : Department]
	
	static mapping = {
		table 		'edu_major'
		id 			generator:'assigned', length:4 
		name		length:50
	}
}
