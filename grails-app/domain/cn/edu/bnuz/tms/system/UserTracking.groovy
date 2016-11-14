package cn.edu.bnuz.tms.system

class UserTracking {
	String ip
	String userId
	String userName
	String controller
	String action
	String content
	Date dateCreated
	
	static mapping = {
		table 		'sys_user_tracking'
		ip 			length:15
		userId 		length:10
		userName 	length:50
		controller	length:50
		action		length:50
		content		length:256
	}
	
	static constraints = {
		userName	nullable:true
	}
}
