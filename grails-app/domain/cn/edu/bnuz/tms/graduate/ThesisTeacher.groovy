package cn.edu.bnuz.tms.graduate

import cn.edu.bnuz.tms.organization.Teacher

class ThesisTeacher implements Serializable {
	Thesis thesis
	Teacher teacher
	
	static mapping = {
		table 		'grd_thesis_teacher'
	}
}
