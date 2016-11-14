package cn.edu.bnuz.tms.system

class AnnourcementAttachment {
	UploadedFile file
	
	static belongsTo = [
		annourcement : Annourcement
	]
	
	static mapping = {
		table 'sys_annourcement_attachment'
	}
}
