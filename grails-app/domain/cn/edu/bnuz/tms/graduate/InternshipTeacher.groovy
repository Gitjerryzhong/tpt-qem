package cn.edu.bnuz.tms.graduate

import cn.edu.bnuz.tms.organization.Teacher

class InternshipTeacher {
	Internship internship
	Teacher teacher
	
	static mapping = {
		table 		'grd_internship_teacher'
	}	
}
