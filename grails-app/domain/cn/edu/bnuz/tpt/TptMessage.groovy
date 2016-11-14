package cn.edu.bnuz.tpt

class TptMessage {
	String srcId
	String desId
	int type
	boolean readed
	Date submitDate
	String content
	
	static mapping={
		table 			'tpt_Message'
		srcId			length: 10
		desId			length: 10
	}
    static constraints = {
		desId 	nullable: true
    }
}
