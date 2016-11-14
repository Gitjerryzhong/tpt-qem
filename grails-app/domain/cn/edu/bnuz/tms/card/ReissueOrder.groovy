package cn.edu.bnuz.tms.card

import cn.edu.bnuz.tms.organization.Teacher;

class ReissueOrder {
	/**
	 * 创建人
	 */
	Teacher creator
	
	/**
	 * 修改时间
	 */
	Teacher modifier
	
	/**
	 * 创建时间
	 */
	java.util.Date dateCreated
	
	/**
	 * 修改时间
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
