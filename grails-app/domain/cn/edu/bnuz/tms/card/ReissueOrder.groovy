package cn.edu.bnuz.tms.card

import cn.edu.bnuz.tms.organization.Teacher;

class ReissueOrder {
	/**
	 * ������
	 */
	Teacher creator
	
	/**
	 * �޸�ʱ��
	 */
	Teacher modifier
	
	/**
	 * ����ʱ��
	 */
	java.util.Date dateCreated
	
	/**
	 * �޸�ʱ��
	 */
	java.util.Date dateModified
	
	static hasMany = [items : ReissueItem]
	
	static mapping = {
		table 	'aff_card_reissue_order'
	}
	
	static constraints = {
		modifier     nullable: true
		dateModified nullable: true
	}
}
