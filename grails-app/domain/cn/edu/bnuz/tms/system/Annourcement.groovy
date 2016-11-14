package cn.edu.bnuz.tms.system

import cn.edu.bnuz.tms.organization.Teacher


class Annourcement {
	String title
	String content
	Date dateCreated
	Date lastUpdated
	Teacher creator
	int status
	
	static hasMany = [
		attachments : AnnourcementAttachment
	]
	
	static mapping = {
		table 'sys_announcement'
		content type:'text'
	}
}
