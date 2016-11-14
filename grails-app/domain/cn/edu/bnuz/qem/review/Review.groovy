package cn.edu.bnuz.qem.review

class Review {
	/**
	 * 状态：新建、待分配专家、待评审、待上会、评审结束
	 */	
	/**
	 * 新建
	 */
	public static final int STATUS_NEW = 0

	/**
	 * 已分配专家
	 */
	public static final int STATUS_EXPERT = 1
	
	/**
	 * 专家已评审
	 */
	public static final int STATUS_ASSESSED = 2
		
	/**
	 * 已上会
	 */
	public static final int STATUS_MEETING = 3
	
	/**
	 * 评审通过
	 */
	public static final int STATUS_PASS = 4
	
	/**
	 * 评审不通过
	 */
	public static final int STATUS_REJECTED = 5
	
	/**
	 * 建议立项，需修改
	 */
	public static final int STATUS_UPDATETOPASS = 6
	
	String detail

	String result

	String additionalDetail	
	
	String experts
	int status
	
	static hasMany = [
		expertView: ExpertsView
	]
	
	static mapping={
		table	'qem_review'
		experts length:60
	}
	
    static constraints = {
		detail(maxSize:1500,nullable:true)
		result  nullable:true
		experts nullable:true
		additionalDetail nullable:true
//		attachments nullable:true
		additionalDetail(maxSize:1500)
    }
}
