package cn.edu.bnuz.tms.place

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher

class BookingAuth implements Serializable {
	BookingType type
	Department department
	Teacher checker
	
	static mapping = {
		table 	'aff_booking_auth'
		id 		composite: ['type', 'department', 'checker']
	}
}
