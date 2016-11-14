package cn.edu.bnuz.qem.project

class Notice {
//	static hasMany =[types:QemType]
	/**
	 * 项目申请
	 */
	public static final String PROJECT_REQUEST = "REQ"
	/**
	 * 项目检查
	 */
	public static final String PROJECT_CHECK = "CHE"
	String title
	
	String workType
	
	Date start
	
	Date end
	
	String content
	
	Date publishDate
	
	String author
	
	String bn
	
	String noTowLine	//0不限制，1校级在研限制，2省级在研限制
	
	static mapping={
		table	'qem_notice'
		workType	length: 5
		columns {
			noTowLine defaultValue:'0'
		}
	}

    static constraints = {
		bn	nullable:true
		content(maxSize:1500,nullable: true)
    }
}
