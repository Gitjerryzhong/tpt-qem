package cn.edu.bnuz.tms.place

class BookingType {
	String name
	boolean isAdminDept // �Ƿ�Ϊ�������Ž�������
	
	static hasMany = [
		auths: BookingAuth
	]
	
	static mapping = {
		table 	'aff_booking_type'
		name	length:50
	}
}
