package cn.edu.bnuz.qem

import java.text.SimpleDateFormat
import java.util.Date;

class NoticeForm {
	int id
	String title
	String content
	String start	
	String end
	String workType
	String	noTowLine
	private Date toDate(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		if(dateStr!=null){
			Date date=sdf.parse(dateStr)
			return date
		}else{
			return null
		}
	}
	def Date getStartDate(){
		return toDate(start)
	}
	def Date getEndDate(){
		return toDate(end)
	}
}
