package cn.edu.bnuz.tpt

import java.util.Date;

class TptMessageCommand {
	String srcId
	String desId
	String type
	boolean readed=false
	Date submitDate=new Date()
	String content
}
