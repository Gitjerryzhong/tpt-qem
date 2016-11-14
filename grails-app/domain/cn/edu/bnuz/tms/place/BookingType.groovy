package cn.edu.bnuz.tms.place

class BookingType {
	String name
	boolean isAdminDept // 是否为行政部门借用类型
	
	static hasMany = [
		auths: BookingAuth
	]
	
	static mapping = {
		table 	'aff_booking_type'
		name	length:50
	}
}
