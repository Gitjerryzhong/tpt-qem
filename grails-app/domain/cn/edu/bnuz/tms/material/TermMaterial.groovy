package cn.edu.bnuz.tms.material

import org.apache.commons.lang.builder.HashCodeBuilder

import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.teaching.Term;


class TermMaterial {
	Term term
	Material material
	Department department
	
	static mapping = {
		table 	'edu_term_material'
	}
}
