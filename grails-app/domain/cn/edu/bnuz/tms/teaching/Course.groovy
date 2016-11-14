package cn.edu.bnuz.tms.teaching

import cn.edu.bnuz.tms.organization.Department


class Course {
	String id
	String name
	int credit
	String weekHours
	
	/**
	 * �γ����:ѡ�޿�/���޿�/ʵ������
	 */
	
	String type
	/**
	 * �γ�����:
	 *    �������޿�/����ѡ�޿�/����ѡ�޿�1/ǿ��Ӣ��
	 *         ѧ�ƻ�����/רҵ���ɿ�/רҵ�����/���Կγ�/ʵ����ѧ
	 *         רҵ���޿�/רҵѡ�޿�/רҵʵ����
	 *         ˫ѧλ��/���޿�
	 */
	String property
	
	@Override
	String toString() {
		"Course[$id, name:$name]"
	}
	
	static belongsTo = [department : Department]
	
	static mapping = {
		table 		'edu_course'
		id 			generator:'assigned', length:8
		type 		length:20
		property	length:20
		weekHours	length:9
	}
	
	static constraints = {
		name 			nullable:false, blank:false
		credit 			nullable:true
		type 			nullable:true
		property		nullable:true
		weekHours 		nullable:true
	}
}
