package cn.edu.bnuz.tms.feedback

class IssueSupport {
	String userId
	
	static belongsTo = [
		issue : Issue
	]
	
	static mapping = {
		table 		'fbk_issue_support'
		userId		length: 10
	}
	
	static constraints = {
		userId 	unique: 'issue'
	}
}
