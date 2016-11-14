package cn.edu.bnuz.tms.feedback

class IssueHistory {
	int type
	String title
	String content
	Date dateCreated
	
	static belongsTo = [
		issue : Issue
	]
	
	static mapping = {
		table 		'fbk_issue_history'
		title		length: 50
		content		length: 512
	}
}
