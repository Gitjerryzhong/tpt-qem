package cn.edu.bnuz.tms.card

import cn.edu.bnuz.tms.card.ReissueRequest;

class ReissueItem {
	ReissueRequest request
	Date dateReceived 
	
	static belongsTo = [
		order : ReissueOrder
	]
	
	static mapping = {
		table 	'aff_card_reissue_item'
	}
	
	static constraints = {
		dateReceived nullable: true
	}
}
