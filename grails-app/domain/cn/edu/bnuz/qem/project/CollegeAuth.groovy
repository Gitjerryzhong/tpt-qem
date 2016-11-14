package cn.edu.bnuz.qem.project

import java.io.Serializable;

import cn.edu.bnuz.tms.organization.Department;
import cn.edu.bnuz.tms.organization.Teacher;

class CollegeAuth implements Serializable{
	Department department
	Teacher checker
	
	static mapping = {
		table 	'qem_college_auth'
		id 		composite: ['department', 'checker']
	}    
}
