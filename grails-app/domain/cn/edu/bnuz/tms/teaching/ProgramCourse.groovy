package cn.edu.bnuz.tms.teaching

class ProgramCourse {
	Program program
	Course course
	int term
	
	static mapping = {
		table 'edu_program_course'
	}
}
