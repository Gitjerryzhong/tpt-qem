package cn.edu.bnuz.tpt

class TptRequest {
	/**
	 * 未提交
	 */
	public static final int STATUS_NEW = 0

	/**
	 * 申请中
	 */
	public static final int STATUS_APPLYING = 1
	
	/**
	 * 已审核照片、证书、成绩
	 */
	public static final int STATUS_CHECKED = 2
		
	/**
	 * 照片、证书、成绩审核不通过
	 */
	public static final int STATUS_REJECTED = 3
	
	/**
	 * 论文审核中
	 */
	public static final int STATUS_PAPERUPLOAD = 4
	
	/**
	 * 论文已审核通过
	 */
	public static final int STATUS_PAPERCHECKED = 5
	
	/**
	 * 论文审核不通过
	 */
	public static final int STATUS_PAPERREJECTED = 7
	
	/**
	 * 已关闭
	 */
	public static final int STATUS_CLOSED = 6
	String userId
	String userName
	String contact
	String phoneNumber
	String email
	TptCollege foreignCollege
	String collegeName
	String foreignMajor
	String photoUrl
	String resultsUrl
	String certificateUrl
	String paperUrl
	Date dateCreate
	String bn
	boolean allIn
	TptMentor mentor
	static hasMany = [
		audits: TptAudit
	]
	int status
	

	
   static mapping = {
		table 			'tpt_request'
		userId			length: 10
		userName		length: 50
		phoneNumber 	length: 50
		foreignCollege	length: 50
		foreignMajor	length: 50
		bn				length: 10
		photoUrl		length: 50
		resultsUrl		length: 50
		certificateUrl	length: 50
		paperUrl		length: 50
	}
	
	static constraints = {
		email(email:true, blank:false)
		photoUrl			nullable: true
		resultsUrl		nullable: true
		certificateUrl     	nullable: true
		paperUrl 	nullable: true
		allIn 	nullable: true
		collegeName	nullable:true
		foreignCollege	nullable:true
		mentor	nullable:true
	}
	
	boolean allowStatus(int status) {
		switch(this.status) {
			case STATUS_NEW:
				return status == STATUS_APPLYING || // 提交
					   status == STATUS_CLOSED      // 关闭
			case STATUS_APPLYING:
				return status == STATUS_NEW ||      // 撤销
					   status == STATUS_CHECKED ||  // 审核[通过]
					   status == STATUS_REJECTED || // 审核[不通过]
					   status == STATUS_CLOSED      // 关闭
			case STATUS_CHECKED:
				return status == STATUS_PAPERUPLOAD || // 上传论文
					   status == STATUS_CLOSED      // 关闭
			case STATUS_REJECTED:
				return status == STATUS_APPLYING || // 提交
					   status == STATUS_CLOSED      // 关闭		
			case STATUS_PAPERUPLOAD:
			   	return status == STATUS_PAPERCHECKED || // 论文审核通过
			   		  status == STATUS_PAPERREJECTED  ||     // 论文审核不通过
					  status == STATUS_CLOSED      // 关闭
			case STATUS_PAPERREJECTED:
						return status == STATUS_PAPERUPLOAD  // 上传论文
			default:
				return false
		}
	}
}
