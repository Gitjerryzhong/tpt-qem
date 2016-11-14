package cn.edu.bnuz.tms.material

import cn.edu.bnuz.tms.organization.Department

/**
 * ��ѧ����
 * @author ����
 * @since 0.4
 */
class Material {
	/**
	 * ѧԺ�����Ϊ�գ���ʾУ�����ϡ���Ϊ�գ���ʾԺ�����ϡ� 
	 */
	Department department
	
	/**
	 * ��ѧ��������
	 */
	String name
	
	/**
	 * ����
	 */
	MaterialCategory category
	
	/**
	 * ˵��
	 */
	String description
	
	/**
	 * ���͡�
	 * 0.��ѧ��
	 * 1.���γ̰�
	 * 2.���γ�
	 */
	int type
	
	/**
	 * �ύ��ʽ��
	 * 1.ֽ�ʰ�
	 * 2.���Ӱ棨�����ύ��
	 * 3.���Ӱ棨����ϵͳ��
	 * 4.���Ӱ棨�����ύ���ݲ�֧��
	 */
	int form
	
	static mapping = {
		table 		'edu_material'
		name 		length:50
		category 	length:50
	}
	
	static constraints = {
		department		nullable:true
		description 	nullable:true
	}
}
