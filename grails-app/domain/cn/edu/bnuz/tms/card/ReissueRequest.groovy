package cn.edu.bnuz.tms.card

import cn.edu.bnuz.tms.organization.Student

class ReissueRequest {
	/**
	 * 未提交
	 */
	public static final int STATUS_NEW = 0

	/**
	 * 申请中
	 */
	public static final int STATUS_APPLYING = 1

	/**
	 * 制作中
	 */
	public static final int STATUS_MAKING = 2
	
	/**
	 * 不批准
	 */
	public static final int STATUS_REJECTED = 3

	/**
	 * 制作完成
	 */
	public static final int STATUS_FINISHED = 4
	
	Student student
	
	/**
	 * 事由
	 */
	String reason
	
	/**
	 * 创建时间
	 */
	Date dateCreated
	
	/**
	 * 修改时间
	 */
	Date dateModified
	
	/**
	 * 0：未提交
	 * 1：申请中
	 * 2：制作中
	 * 3：不批准
	 * 4：制作完成
	 */
	int status
	
	static mapping = {
		table 	'aff_card_reissue_request'
	}
	
	boolean allowStatus(int status) {
		switch(this.status) {
			case STATUS_NEW:
				return status == STATUS_APPLYING    // 提交
			case STATUS_APPLYING:
				return status == STATUS_NEW ||      // 撤销 
					   status == STATUS_MAKING ||   // 制作
					   status == STATUS_REJECTED    // 不批准
			case STATUS_MAKING:
				return status == STATUS_APPLYING || // 删除订单项
				       status == STATUS_FINISHED    // 入库
			case STATUS_REJECTED:
				return status == STATUS_APPLYING    // 提交 
			case STATUS_FINISHED:
				return status == STATUS_MAKING      // 取消入库
			default:
				return false
		}
	}
	
	boolean allowUpdate() {
		return status == STATUS_NEW || status == STATUS_REJECTED
	}
	
	boolean allowDelete() {
		return status == STATUS_NEW || status == STATUS_REJECTED
	}
}
