package cn.edu.bnuz.qem.project

class Notice {
//	static hasMany =[types:QemType]
	/**
	 * ��Ŀ����
	 */
	public static final String PROJECT_REQUEST = "REQ"
	/**
	 * ��Ŀ���
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
	
	String noTowLine	//0�����ƣ�1У���������ƣ�2ʡ����������
	
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
