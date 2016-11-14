package cn.edu.bnuz.tms.system

class UploadedFile {
	String fileName
	String localPath
	Date dateCreated
	
	static mapping = {
		table 'sys_uploaded_file'
	}
}
