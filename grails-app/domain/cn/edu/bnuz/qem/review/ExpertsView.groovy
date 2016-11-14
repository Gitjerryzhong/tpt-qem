package cn.edu.bnuz.qem.review

import cn.edu.bnuz.qem.organization.Experts

class ExpertsView {
	Experts expert
	
	String view
	/**
	 * 蛍方參key1:value1,key2:value2,。。。。
	 */
	String scoreArray
	int totalScore
	boolean commit
	int result
	static belongsTo =[review:Review]
	static mapping={
		table	'qem_experts_view'		
	}
    static constraints = {
		view(maxsize:1500)
		scoreArray(nullable:true)
		totalScore(nullable:true)
    }
}
