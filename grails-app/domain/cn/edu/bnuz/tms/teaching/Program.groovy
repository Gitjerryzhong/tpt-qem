package cn.edu.bnuz.tms.teaching



class Program {
	String id
	int grade
	
	static hasMany = [
		courses: ProgramCourse	
	]
	
	static belongsTo = [
		major: Major
	]
	
	static mapping = {
		table 	'edu_program'
		id 		generator: 'assigned', length: 8
	}
}
