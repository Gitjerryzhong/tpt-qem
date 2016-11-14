package cn.edu.bnuz.tms.material

import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.teaching.Course
import cn.edu.bnuz.tms.teaching.CourseClass

/**
 * ��ѧ�����ύ���
 * @author ����
 * @since 0.4
 */
class TeachingMaterial {
	/**
	 * ѧ�ڲ���
	 */
	TermMaterial termMaterial
	
	/**
	 * �ύ��
	 */
	Teacher	teacher
	
	/**
	 * �γ̰�
	 */
	CourseClass courseClass
	
	/**
	 * �γ�
	 */
	Course course
	
	/**
	 * �����
	 */
	Teacher checker
	
	/**
	 * ȷ��ʱ��
	 */
	Date comfirmDate
	
	/**
	 * ״̬��1���ύ��2���⽻
	 */
	int status
	
	static mapping = {
		table 		'edu_teaching_material'
	}
	
	static constraints = {
		courseClass		nullable:true
		course			nullable:true
		checker 		nullable:true
		comfirmDate		nullable:true
	}
}
